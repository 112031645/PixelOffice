# ============================================================
# Higress 云原生 API 网关启动项
# 版本: 1.3.x
# 端口: 80(HTTP), 443(HTTPS), 15020(健康检查)
# ============================================================
#
# 【启动方式 - Helm（K8s 环境）】
#   1. 添加 Helm 仓库：
#      helm repo add higress.io https://higress.io/helm-charts
#   2. 创建命名空间：
#      kubectl create namespace higress-system
#   3. 安装 Higress（使用本地 Nacos 作为注册中心）：
#      helm install higress higress.io/higress -n higress-system \
#        --set global.local=true \
#        --set higress-console.namespace=higress-system
#   4. 查看控制台：
#      kubectl get svc -n higress-system
#
# 【启动方式 - Docker Compose（本地开发）】
#   参考 higress 目录下的 docker-compose.yml
#
# 控制台地址: http://127.0.0.1:8080  账号/密码: admin/admin
#
