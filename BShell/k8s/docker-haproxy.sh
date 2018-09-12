# 拉取haproxy镜像
docker pull haproxy:1.7.8-alpine
mkdir /etc/haproxy
cat > /etc/haproxy/haproxy.cfg << EOF
global
  log 127.0.0.1 local0 err
  maxconn 50000
  uid 99
  gid 99
  #daemon
  nbproc 1
  pidfile haproxy.pid

defaults
  mode http
  log 127.0.0.1 local0 err
  maxconn 50000
  retries 3
  timeout connect 5s
  timeout client 30s
  timeout server 30s
  timeout check 2s

listen admin_stats
  mode http
  bind 0.0.0.0:1080
  log 127.0.0.1 local0 err
  stats refresh 30s
  stats uri     /haproxy-status
  stats realm   Haproxy\ Statistics
  stats auth    will:will
  stats hide-version
  stats admin if TRUE

frontend k8s-https
  bind 0.0.0.0:8443
  mode tcp
  #maxconn 50000
  default_backend k8s-https

backend k8s-https
  mode tcp
  balance roundrobin
  server master4 192.168.134.4:6443 weight 1 maxconn 1000 check inter 2000 rise 2 fall 3
  server master5 192.168.134.5:6443 weight 1 maxconn 1000 check inter 2000 rise 2 fall 3
EOF

# 启动haproxy
docker run -d --name my-haproxy \
-v /etc/haproxy:/usr/local/etc/haproxy:ro \
-p 8443:8443 \
-p 1080:1080 \
--restart always \
haproxy:1.7.8-alpine

# 查看日志
docker logs my-haproxy

# 浏览器查看状态
http://192.168.134.4:1080/haproxy-status
http://192.168.134.5:1080/haproxy-status

# 拉取keepalived镜像
docker pull osixia/keepalived:1.4.4

# 启动
# 载入内核相关模块
lsmod | grep ip_vs
modprobe ip_vs

# 启动keepalived
# eth1为本次实验11.11.11.0/24网段的所在网卡
docker run --net=host --cap-add=NET_ADMIN \
-e KEEPALIVED_INTERFACE=eth1 \
-e KEEPALIVED_VIRTUAL_IPS="#PYTHON2BASH:['192.168.134.114']" \
-e KEEPALIVED_UNICAST_PEERS="#PYTHON2BASH:['192.168.134.4','192.168.134.5']" \
-e KEEPALIVED_PASSWORD=hello \
--name k8s-keepalived \
--restart always \
-d osixia/keepalived:1.4.4

# 查看日志
# 会看到两个成为backup 一个成为master
docker logs k8s-keepalived

# 此时会配置 192.168.134.114 到其中一台机器
# ping测试
ping -c4 192.168.134.114

# 如果失败后清理后，重新实验
docker rm -f k8s-keepalived
ip a del 192.168.134.114/32 dev eth1
