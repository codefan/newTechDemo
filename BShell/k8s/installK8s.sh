# https://www.kubernetes.org.cn/4256.html
centos 7.*

关闭防火墙:

systemctl disable firewalld
systemctl stop firewalld
# 关闭 selinux
setenforce 0

#安装 docker
# http://www.runoob.com/docker/centos-docker-install.html

#移除旧的版本：

yum remove docker \
                  docker-client \
                  docker-client-latest \
                  docker-common \
                  docker-latest \
                  docker-latest-logrotate \
                  docker-logrotate \
                  docker-selinux \
                  docker-engine-selinux \
                  docker-engine

#安装一些必要的系统工具：
yum install -y yum-utils device-mapper-persistent-data lvm2

#添加软件源信息：
yum-config-manager --add-repo http://mirrors.aliyun.com/docker-ce/linux/centos/docker-ce.repo

#更新 yum 缓存：

yum makecache fast

#安装 Docker-ce：
yum -y install docker-ce

#启动 Docker 后台服务
systemctl enable docker.service
systemctl enable docker && systemctl start docker
#sudo systemctl start docker

#测试运行 hello-world
docker run hello-world

### 安装 k8s之前的配置

## 配置免密登录

#生成密钥对
ssh-keygen -t rsa
#将公钥加入到客户机器的认证keys文件中 /etc/ssh/sshd_config : AuthorizedKeysFile
cat ~/.ssh/id_rsa.pub >> ~/.ssh/authorized_keys


#安装 kubernetes
common:


# https://www.kubernetes.org.cn/4256.html
#

cat <<EOF > /etc/yum.repos.d/kubernetes.repo

[kubernetes]
name=Kubernetes
baseurl=https://mirrors.aliyun.com/kubernetes/yum/repos/kubernetes-el7-x86_64
enabled=1
gpgcheck=1
repo_gpgcheck=1
gpgkey=https://mirrors.aliyun.com/kubernetes/yum/doc/yum-key.gpg https://mirrors.aliyun.com/kubernetes/yum/doc/rpm-package-key.gpg
EOF

# 安装
yum install -y kubelet kubeadm kubectl ipvsadm
yum install -y kubernetes-cni

systemctl enable kubelet && systemctl start kubelet

# https://cr.console.aliyun.com/
# https://cr.console.aliyun.com/cn-qingdao/mirrors

tee /etc/docker/daemon.json <<-'EOF'
{
  “registry-mirrors“: [“https://59pug3vw.mirror.aliyuncs.com“]
}
EOF
systemctl daemon-reload
systemctl restart docker


# 配置转发相关参数，否则可能会出错
cat <<EOF >  /etc/sysctl.d/k8s.conf
net.bridge.bridge-nf-call-ip6tables = 1
net.bridge.bridge-nf-call-iptables = 1
vm.swappiness=0
EOF

sysctl --system

sysctl -p /etc/sysctl.d/k8s.conf


master:

pull_k8s_images_from_aliyun.sh


systemctl enable kubelet && systemctl start kubelet

kubeadm init --kubernetes-version=1.11.0

# master-5
#  kubeadm join 192.168.134.5:6443 --token ymxs4l.7cdya573bany7r59
# --discovery-token-ca-cert-hash sha256:fac85ad8f57904eb891907d9b0fd922e1c58147cb20b408c149253c1baebcffe

cp /etc/kubernetes/kubelet.conf ~/
chown $(id -u):$(id -g) ~/kubelet.conf
export KUBECONFIG=~/kubelet.conf

kubectl get nodes

node:

swapoff -a && sysctl -w vm.swappiness=0
sed '/swap.img/d' -i /etc/fstab

kubeadm join --token ymxs4l.7cdya573bany7r59  192.168.134.5:6443 --discovery-token-ca-cert-hash sha256:fac85ad8f57904eb891907d9b0fd922e1c58147cb20b408c149253c1baebcffe

## 安装flannel网络（Master节点操作）

mkdir -p /etc/cni/net.d/

cat << EOF > /etc/cni/net.d/10-flannel.conf
{
"name”: ”cbr0”,
”type”: ”flannel”,
”delegate”: {
  ”isDefaultGateway”: true
  }
}
EOF

mkdir /usr/share/oci-umount/oci-umount.d -p

mkdir /run/flannel/

cat << EOF > /run/flannel/subnet.env
FLANNEL_NETWORK=10.244.0.0/16
FLANNEL_SUBNET=10.244.1.0/24
FLANNEL_MTU=1450
FLANNEL_IPMASQ=true

EOF



kubectl apply -f https://raw.githubusercontent.com/coreos/flannel/v0.9.1/Documentation/kube-flannel.yml
