# PixelOffice

<p align="center">
  <img src="https://s1.aigei.com/src/img/png/96/96f6053e8a7847f0a3480c601dfb6b2c.png?e=2051020800&token=P7S2Xpzfz11vAkASLTkfHN7Fw-oOZBecqeJaxypL:iXLAPUb3Tc4j1Kw9kspNqymCrP8=" alt="PixelOffice 像素猫猫" width="50" style="display:block;>
</p>

<p align="center">
  <strong>🏢 复古像素风 IT 企业经营模拟项目</strong>
</p>

<p align="center">
  📖 About &nbsp;•&nbsp; ✨ Features &nbsp;•&nbsp; 🖼️ Preview &nbsp;•&nbsp; 🛠️ Tech Stack &nbsp;•&nbsp; 📁 Structure
</p>

---

## 📖 About

PixelOffice 是一款像素画风的互联网公司经营模拟项目。
玩家扮演公司管理者，从零搭建办公室，统筹开发部、项目部、销售部、财务部等多部门协同运转。

招聘程序员、项目经理、销售等岗位员工，承接业务需求，管控研发进度，规划办公布局。
不断扩充团队，搭建档案室、休息区等配套设施，处理各类职场随机事件，打造属于你的互联网团队。

## ✨ Core Features

- 🧱 **Retro Pixel Art** 复古像素美术风格，完整办公室场景，多职能部门划分
- 👨‍💻 **Staff Management** 员工招聘、入职、岗位分配，多职业角色
- 📊 **Department Collaboration** 开发 / 项目 / 销售 / 财务 / 宣传 / 招聘 多部门联动
- 📋 **Project Lifecycle** 业务需求承接，研发进度管控，项目交付
- 🛋️ **Office Layout** 自由规划办公区域，搭建休息区、档案室等配套空间
- ⚡ **Random Workplace Events** 丰富职场随机事件，考验经营决策能力

## 🖼️ Game Preview

<p align="center">
  <img src="http://nons.fukit.cn/autoupload/fr/xU_2dj8Ti0JtPFC98KYMwaG2Ii_s56f9h50SjiHRctKyl5f0KlZfm6UsKj-HyTuv/20260921/IUtk/1479X861/e421082d-298c-4c01-bfc6-2f8626741523.png" alt="PixelOffice 游戏内办公室全景" width="900">
</p>

> 上图为游戏内办公室全景，包含开发部、项目部、财务部、总经办、档案室、休息区等完整区域

## 🛠️ Technology Stack
```mermaid
mindmap
  root((PixelOffice<br/>技术架构))
    %% 1. 后端基础
    (🧩 后端基础)
      (核心框架)
        Spring Boot 3.2.x
        Spring Cloud 2023.0.x
        Spring Cloud Alibaba 2023.0.1.0
      (微服务核心组件)
        Nacos 注册配置中心
        Gateway API 网关
        OpenFeign 远程调用
        Sentinel 限流熔断
        Seata 分布式事务
      (ORM & 数据库)
        MySQL 8.0 + HikariCP
        MyBatis-Plus 3.5.x
        Sharding-JDBC 分库分表
      (缓存 & 消息队列)
        Redis 7.x
        RocketMQ 5.x
      (中间件 & 安全)
        XXL-Job / MinIO
        Sa-Token / OAuth2 + JWT
        Knife4j / Validation
      (接口规范)
        RESTful / 统一返回封装
        全局异常 / 参数校验
        版本控制 / 分页排序
        Feign内外隔离 / 脱敏

    %% 2. AI 模块
    (🤖 AI 模块)
      (AI 编排框架)
        LangChain4j
      (大模型接入方案)
        公有大模型 API（通义/文心/豆包）
        私有化部署（Ollama/vLLM/Qwen）
        轻量能力（PaddleOCR）
      (RAG 知识库)
        文档解析（Tika/POI）
        向量数据库（Milvus/Redis）
        文本切分/Embedding
      (AI 服务能力)
        对话问答/摘要/翻译
        文档向量化/OCR
        SSE 流式输出
      (AI 配套)
        Redis 会话记忆
        Nacos Prompt 管理
        Sentinel 接口限流

    %% 3. 前端技术栈
    (🎨 前端技术栈)
      (基础框架)
        Vue3 + Vite
      (UI 与状态)
        Element Plus
        Pinia + Vue Router
        Axios
      (AI 前端能力)
        SSE 流式对话
        文件上传（知识库）
        富文本 AI 辅助编辑
      (代码规范)
        ESLint + Prettier

    %% 4. 测试体系
    (🧪 测试体系)
      (基础测试)
        JUnit5 + Mockito + AssertJ
        TestContainers / REST Assured
        JMeter / Arthas
      (AI 专项测试)
        Prompt 提示词单元测试
        向量检索召回效果测试
        提示词注入安全测试
        模型超时熔断降级验证

    %% 5. 运维 & 可观测性
    (📡 运维 & 可观测性)
      (容器编排)
        Docker + K8s
      (指标与链路)
        Prometheus + Grafana
        SkyWalking
      (日志体系)
        PlumeLog（审计）
        ELK（检索/存储）
      (ETL 数据处理)
        MySQL / 日志 / RocketMQ
        抽取/清洗/转换/加载
      (告警与 AI 监控)
        AlertManager（机器人）
        Token消耗 / 调用成功率
        Milvus耗时 / GPU显存

    %% 6. CI/CD
    (🚀 CI/CD 流水线)
      (代码与工具)
        GitLab / Gitee
        Jenkins
      (制品与质量)
        Nexus3 + Harbor
        SonarQube
      (流水线步骤)
        提交触发 → 代码扫描
        编译测试 → 构建镜像
        部署测试环境 → 审批上线
      (版本规范)
        GitFlow

    %% 7. 降级与避坑
    (🛟 降级方案 & ⚠️ 避坑要点)
      (资源不足降级)
        Jar包 + systemd
        Redis Vector 替代 Milvus
        仅调公有API / 仅PlumeLog
      (避坑要点)
        版本严格对齐
        接口强制限流控成本
        Prompt 进 Nacos 禁硬编码
        敏感业务私有化
        长文本任务异步化
        审计日志独立存储
