centos 7.*

关闭防火墙:

systemctl disable firewalld
systemctl stop firewalld
# 关闭 selinux
setenforce 0

#安装 docker
# http://www.runoob.com/docker/centos-docker-install.html

master:
# https://www.kubernetes.org.cn/4256.html
#

sudo yum-config-manager --add-repo http://mirrors.aliyun.com/docker-ce/linux/centos/docker-ce.repo


cat <<EOF > /etc/yum.repos.d/kubernetes.repo
[kubernetes]
name=Kubernetes
baseurl=https://mirrors.aliyun.com/kubernetes/yum/repos/kubernetes-el7-x86_64
enabled=1
gpgcheck=1
repo_gpgcheck=1
gpgkey=https://mirrors.aliyun.com/kubernetes/yum/doc/yum-key.gpg https://mirrors.aliyun.com/kubernetes/yum/doc/rpm-package-key.gpg
EOF


yum install -y --setopt=obsoletes=0 docker-ce-17.03.1.ce-1.el7.centos docker-ce-selinux-17.03.1.ce-1.el7.centos

# 安装
yum install -y kubelet kubeadm kubectl ipvsadm

yum install -y docker kubelet kubeadm kubectl kubernetes-cni

systemctl enable docker && systemctl start docker
systemctl enable kubelet && systemctl start kubelet

docker pull warrior/etcd-amd64:3.0
docker tag warrior/etcd-amd64:3.0 gcr.io/google_containers/etcd-amd64:3.0

docker pull warrior/pause-amd64:3.0.17
docker tag warrior/pause-amd64:3.0.17 gcr.io/google_containers/pause-amd64:3.0.17

node:

yum install -y docker kubelet kubeadm kubectl kubernetes-cni

systemctl enable docker && systemctl start docker
systemctl enable kubelet && systemctl start kubelet
