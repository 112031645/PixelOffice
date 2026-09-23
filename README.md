# Cloud Framework - Spring Cloud Alibaba 微服务框架

基于 Spring Cloud Alibaba 的企业级微服务框架，集成服务注册发现、配置中心、流量防护、分布式事务、消息队列、对象存储、链路追踪、监控告警等完整能力。

---

## 一、项目架构

```
                            ┌─────────────────────┐
                            │   Higress API 网关    │  (cloud-getway)
                            │   路由 / 限流 / 鉴权    │
                            └──────────┬──────────┘
                                       │
                    ┌──────────────────┼──────────────────┐
                    │                  │                  │
            ┌───────▼───────┐  ┌───────▼───────┐  ┌───────▼───────┐
            │cloud-user-svc │  │cloud-order-svc│  │  (更多服务...)  │
            │  用户服务 8081 │  │  订单服务 8082 │  │               │
            └───────┬───────┘  └───────┬───────┘  └───────────────┘
                    │                  │
                    │   Dubbo RPC      │
                    └────────┬─────────┘
                             │
        ┌────────────────────┼────────────────────┐
        │                    │                    │
   ┌────▼────┐         ┌─────▼─────┐       ┌──────▼──────┐
   │  Nacos  │         │  Seata    │       │  Sentinel   │
   │注册/配置 │         │分布式事务  │       │  流量防护    │
   └─────────┘         └───────────┘       └─────────────┘
        │
   ┌────▼──────────────────────────────────────┐
   │  MySQL  │  Redis  │  RocketMQ  │  MinIO    │
   │  数据    │  缓存   │  消息队列   │ 对象存储   │
   └───────────────────────────────────────────┘
        │
   ┌────▼───────────────┐    ┌──────────────────┐
   │ Prometheus         │    │  SkyWalking       │
   │ (指标采集) ──► Grafana│    │  链路追踪         │
   └────────────────────┘    └──────────────────┘
```

### 模块说明

| 模块 | 类型 | 说明 |
|------|------|------|
| `cloud-common` | jar 库 | 统一返回体、全局异常、Redis、Knife4j 等公共组件 |
| `cloud-api` | jar 库 | Dubbo RPC 接口契约（UserRpcService / OrderRpcService） |
| `cloud-modules` | pom 聚合 | 业务服务模块聚合 |
| ├ `cloud-user-service` | Spring Boot | 用户服务（MySQL/Redis/RocketMQ/MinIO/Dubbo Provider） |
| ├ `cloud-order-service` | Spring Boot | 订单服务（Seata 分布式事务/Dubbo Consumer） |
| └ `test-module` | 测试 | 集成测试模块（JUnit5 + RestAssured） |
| `cloud-visual` | pom 聚合 | 运维监控模块 - 各中间件启动配置 |
| `cloud-getway` | pom 聚合 | 网关模块 - Higress 路由配置 |

---

## 二、框架技术栈（名称 / 版本 / 端口号）

### 核心框架

| 技术 | 版本 | 端口 | 说明 |
|------|------|------|------|
| Java | 17 | - | 运行环境 |
| Spring Boot | 3.2.5 | - | 核心框架 |
| Spring Cloud | 2023.0.1 | - | 微服务框架 |
| Spring Cloud Alibaba | 2023.0.1.0 | - | 阿里巴巴微服务生态 |
| Dubbo | 3.3.0 | 动态 | RPC 内部通信 |

### 网关 & 服务治理

| 技术 | 版本 | 端口 | 说明 |
|------|------|------|------|
| Higress | 1.3.x | 80 / 443 | 云原生 API 网关 |
| Nacos | v2.3.0 | 8848 / 9848 | 注册中心 / 配置中心 |
| Sentinel | 1.8.6 | 8858 | 流量防护（熔断/限流/降级） |
| Seata | 2.0.0 | 8091 / 7091 | 分布式事务（AT 模式） |

### 存储中间件

| 技术 | 版本 | 端口 | 说明 |
|------|------|------|------|
| MySQL | 8.0.33 | 3306 | 关系型数据库 |
| Redis | 7.2 | 6379 | 缓存 |
| RocketMQ | 5.1.4 | 9876 / 10911 | 消息队列 |
| MinIO | RELEASE.2024-01-01 | 9000 / 9001 | 对象存储 |

### 接口规范 & 工具

| 技术 | 版本 | 端口 | 说明 |
|------|------|------|------|
| Knife4j (OpenAPI3) | 4.5.0 | 随服务 | 接口文档 |
| MyBatis-Plus | 3.5.5 | - | ORM 框架 |
| Druid | 1.2.22 | - | 数据库连接池 |
| Hutool | 5.8.27 | - | 工具类库 |
| XXL-Job | 2.4.0 | 8080 | 分布式任务调度 |

### 测试

| 技术 | 版本 | 说明 |
|------|------|------|
| JUnit5 | 5.x (Boot 管理) | 单元测试 |
| Mockito | 5.x (Boot 管理) | Mock 框架 |
| RestAssured | 5.4.0 | 接口测试 |
| JMeter | 5.x | 性能测试（外部工具） |
| Selenium | 4.x | 自动化测试（外部工具） |

### 运维 & 监控

| 技术 | 版本 | 端口 | 说明 |
|------|------|------|------|
| Docker | 24+ | - | 容器化 |
| Kubernetes | 1.28+ | - | 容器编排 |
| Prometheus | v2.48.0 | 9090 | 指标采集 |
| Grafana | 10.2.0 | 3000 | 可视化监控 |
| SkyWalking | 9.6.0 | 11800 / 12800 / 8080 | 链路追踪 |

### 服务端口

| 服务 | 端口 |
|------|------|
| cloud-user-service | 8081 |
| cloud-order-service | 8082 |

---

## 三、项目结构

```
cloud-framework/
├── pom.xml                          # 父 POM（BOM 统一管理版本）
├── cloud-common/                    # 公共模块
├── cloud-api/                       # Dubbo 接口契约
├── cloud-modules/                   # 业务服务聚合
│   ├── cloud-user-service/          # 用户服务
│   ├── cloud-order-service/         # 订单服务
│   └── test-module/                 # 集成测试
├── cloud-visual/                    # 运维监控模块（中间件启动配置）
│   ├── nacos/                       # Nacos 启动配置
│   ├── xxl-job/                     # XXL-Job 调度中心配置
│   ├── seata/                       # Seata 配置
│   ├── sentinel/                    # Sentinel 控制台 + 流控规则
│   ├── rocketmq/                    # RocketMQ Broker 配置
│   ├── minio/                       # MinIO 启动配置
│   ├── prometheus/                  # Prometheus 采集配置
│   └── grafana/                     # Grafana 数据源 + 仪表盘
├── cloud-getway/                    # 网关模块
│   └── higress/                     # Higress 路由配置
├── nacos-config/                    # Nacos 配置中心 yaml（需导入 Nacos）
├── sql/                             # 数据库初始化脚本
├── docker/                          # Prometheus 配置
├── k8s/                             # K8s 部署 YAML
└── docker-compose.yml               # 中间件一键启动
```

---

## 四、Nacos 配置说明

项目采用「**bootstrap.yml 只放 Nacos 连接 + 配置集引用，具体数据放 Nacos 控制台**」的分层设计。

### bootstrap.yml（项目内）

每个服务的 `bootstrap.yml` 只包含：
- Nacos 地址、namespace、group、账号密码
- `shared-configs`：共享配置（common / mysql / redis / rocketmq / minio / seata）
- `extension-configs`：本服务独有配置

### Nacos 控制台配置（nacos-config/ 目录）

需将以下文件导入 Nacos 控制台，Group 为 `CLOUD_GROUP`：

| data-id | 说明 |
|---------|------|
| `common.yaml` | 日志/Sentinel/Dubbo 公共配置 |
| `mysql.yaml` | MySQL 数据源（Druid 连接池） |
| `redis.yaml` | Redis 配置 |
| `rocketmq.yaml` | RocketMQ 配置 |
| `minio.yaml` | MinIO 对象存储 |
| `seata.yaml` | Seata 分布式事务 |
| `cloud-user-service.yaml` | 用户服务特有配置 |
| `cloud-order-service.yaml` | 订单服务特有配置 |

---

## 五、Docker 启动中间件

项目根目录提供 `docker-compose.yml`，一键启动所有基础中间件。

### 启动全部中间件

```bash
cd cloud-framework
docker compose up -d
```

### 单独启动某个服务

```bash
# 只启动 Nacos + MySQL + Redis
docker compose up -d nacos mysql redis
```

### 中间件访问地址

| 中间件 | 地址 | 账号/密码 |
|--------|------|-----------|
| Nacos | http://localhost:8848/nacos | nacos / nacos |
| MySQL | localhost:3306 | root / 123456 |
| Redis | localhost:6379 | 无 |
| RocketMQ Console | http://localhost:8081 | - |
| MinIO Console | http://localhost:9001 | minioadmin / minioadmin |
| Seata Console | http://localhost:7091 | - |
| Sentinel | http://localhost:8858 | sentinel / sentinel |
| Prometheus | http://localhost:9090 | - |
| Grafana | http://localhost:3000 | admin / admin123 |
| SkyWalking UI | http://localhost:8080 | - |

### 初始化数据库

```bash
# docker-compose 已自动挂载 sql/init.sql，首次启动会自动执行
# 如需手动执行：
mysql -h 127.0.0.1 -P 3306 -uroot -p123456 < sql/init.sql
```

### 导入 Nacos 配置

1. 登录 Nacos 控制台 http://localhost:8848/nacos
2. 命名空间选择 `public`，Group 选择 `CLOUD_GROUP`
3. 逐个导入 `nacos-config/` 目录下的 yaml 文件

### 停止并清理

```bash
docker compose down
# 清理数据卷（谨慎操作）
docker compose down -v
```

---

## 六、项目启动

### 前置条件

- JDK 17+
- Maven 3.8+
- Docker（中间件）
- 已启动中间件并导入 Nacos 配置

### 1. 编译安装

```bash
cd cloud-framework
mvn clean install -DskipTests
```

### 2. 启动用户服务

```bash
cd cloud-modules/cloud-user-service
mvn spring-boot:run
# 或
java -jar target/cloud-user-service.jar
```

启动成功后访问：http://localhost:8081/doc.html

### 3. 启动订单服务

```bash
cd cloud-modules/cloud-order-service
mvn spring-boot:run
# 或
java -jar target/cloud-order-service.jar
```

启动成功后访问：http://localhost:8082/doc.html

### 4. 验证服务注册

登录 Nacos 控制台 → 服务管理 → 服务列表，应能看到：
- `cloud-user-service`
- `cloud-order-service`

### 5. 测试接口

```bash
# 查询用户
curl http://localhost:8081/user/1

# 创建订单（触发 Seata 分布式事务）
curl -X POST http://localhost:8082/order \
  -H "Content-Type: application/json" \
  -d '{"userId":1,"amount":100.00,"remark":"测试订单"}'
```

---

## 七、本地非 Docker 启动中间件

如需本地二进制方式启动（不使用 Docker），各中间件启动配置见 `cloud-visual/` 目录：

| 中间件 | 配置文件 | 启动说明 |
|--------|----------|----------|
| Nacos | `cloud-visual/nacos/application.properties` | standalone 模式启动 |
| XXL-Job | `cloud-visual/xxl-job/application.properties` | 启动 xxl-job-admin |
| Seata | `cloud-visual/seata/registry.conf` | 注册中心指向 Nacos |
| Sentinel | `cloud-visual/sentinel/` | java -jar 启动 dashboard |
| RocketMQ | `cloud-visual/rocketmq/broker.conf` | 先起 NameServer 再起 Broker |
| MinIO | `cloud-visual/minio/minio.env` | minio server 启动 |
| Prometheus | `cloud-visual/prometheus/prometheus.yml` | prometheus --config.file |
| Grafana | `cloud-visual/grafana/` | grafana-server 启动 |

---

## 八、网关接入（Higress）

Higress 路由配置见 `cloud-getway/higress/higress.yaml`。

### K8s 部署

```bash
# 安装 Higress
helm repo add higress.io https://higress.io/helm-charts
helm install higress higress.io/higress -n higress-system --create-namespace

# 应用路由配置
kubectl apply -f cloud-getway/higress/higress.yaml
```

### 路由规则

| 路径 | 转发到 |
|------|--------|
| `/user/**` | cloud-user-service:8081 |
| `/order/**` | cloud-order-service:8082 |
