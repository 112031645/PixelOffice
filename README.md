# PixelOffice
<p align="center">
  <img src="https://via.placeholder.com/800x400?text=PixelOffice+Cover" alt="PixelOffice Banner">
</p>

<p align="center">
  <strong>🏢 复古像素风 IT 企业经营模拟项目</strong>
</p>

<p align="center">
  <a href="#features">✨ Features</a> •
  <a href="#game-preview">🖼️ Preview</a> •
  <a href="#technology-stack">🛠️ Technology Stack</a> •
  <a href="#quick-start">🚀 Quick Start</a> •
  <a href="#structure">📁 Project Structure</a> •
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
  <img src="https://img.remit.ee/i/beis5ddP13GC" alt="beis5ddP13GC.png"  wdith='900' />
</p>

> 上图为游戏内办公室全景，包含开发部、项目部、财务部、总经办、档案室、休息区等完整区域

## 🛠️ Technology Stack
```mermaid
mindmap
  root((Spring Cloud Alibaba + AI<br/>微服务技术方案)):::rootStyle
    后端基础(Spring Cloud Alibaba):::backend
      核心框架
        Spring Boot 3.2.x
        Spring Cloud 2023.0.x Leyton
        Spring Cloud Alibaba 2023.0.1.0
      微服务核心组件
        Nacos：注册中心 + 配置中心
        Spring Cloud Gateway：API网关
        OpenFeign：远程服务调用
        Sentinel：限流、熔断、降级
        Seata：分布式事务
      ORM & 数据库
        MySQL8.0 + HikariCP连接池
        MyBatis-Plus 3.5.x
        Sharding-JDBC：分库分表
      缓存 & 消息队列
        Redis7.x：分布式锁、会话缓存、小规模向量
        RocketMQ5.x：异步解耦、AI任务队列、ETL消息触发
      其他中间件
        XXL-Job：分布式定时任务
        MinIO：对象存储
      安全 & 工具包
        Sa-Token / SpringSecurity+OAuth2+JWT
        Lombok、Hutool、MapStruct
        Knife4j接口文档、Validation参数校验
      接口规范
        RESTful 风格设计
        统一返回结果封装(code、msg、data)
        全局统一异常处理器
        请求参数统一校验
        接口版本控制(URI / Header)
        分页、排序统一规范
        Feign内部接口与对外API隔离
        敏感字段自动脱敏
    AI模块(独立 ai-service 微服务):::ai
      AI编排框架：LangChain4j
      大模型接入方案
        公有大模型API：通义千问/文心一言/豆包
        私有化部署：Ollama、vLLM、Qwen/Llama3/GLM
        轻量AI能力：PaddleOCR图片识别
      RAG知识库组件
        文档解析：Apache Tika、POI
        向量数据库
          Milvus（生产）
          Redis Vector（小规模知识库）
        文本切分、Embedding向量生成
      AI服务能力
        对话问答、摘要、翻译
        文档向量化任务
        OCR识别
        SSE流式输出
      AI配套
        Redis：对话会话记忆
        Nacos：动态管理Prompt模板、模型参数
        Sentinel：AI接口限流，控制调用成本
    前端技术栈:::frontend
      基础框架：Vue3 + Vite
      UI库：Element Plus
      状态/路由：Pinia + Vue Router
      HTTP：Axios
      AI前端能力
        SSE流式对话（打字机效果）
        文件上传（知识库文档导入）
        富文本AI辅助编辑
      代码规范：ESLint + Prettier
    测试体系:::test
      基础测试
        JUnit5 + Mockito + AssertJ
        TestContainers集成测试
        REST Assured接口自动化
        JMeter压测
        Arthas线上诊断
      AI专项测试
        Prompt提示词单元测试
        向量检索召回效果测试
        提示词注入安全测试
        模型超时熔断降级验证
    运维 & 可观测性<br>PlumeLog / ELK / ETL:::ops
      容器编排：Docker + K8s（开发Docker Compose）
      指标监控：Prometheus + Grafana
      链路追踪：SkyWalking
      日志体系
        PlumeLog：日志采集、埋点、操作审计
        ELK：业务检索、安全审计、全量日志存储
      ETL数据处理
        数据源：MySQL、业务日志、RocketMQ
        抽取、清洗、转换、加载
        数据同步，支撑报表/AI知识库
      告警：AlertManager（钉钉/企业微信机器人）
      AI额外监控项
        Token消耗统计、模型调用成功率
        Milvus查询耗时
        GPU使用率&显存（私有化模型）
    CI/CD流水线:::cicd
      代码仓库：GitLab/Gitee
      CI工具：Jenkins（备选GitLab CI）
      制品仓库：Nexus3(Maven) + Harbor(Docker镜像)
      代码质量：SonarQube
      流水线步骤
        代码提交触发
        Sonar代码扫描
        Maven编译+单元测试(Mock模型)
        构建Docker镜像推送Harbor
        K8s部署测试环境
        人工审批 → 生产部署
      版本规范：GitFlow分支管理
    备选降级方案（资源不足）:::backup
      不使用K8s：Jar包 + systemd托管
      不部署Milvus：Redis Vector承载小规模知识库
      无GPU：仅调用公有大模型API
      轻量日志：仅PlumeLog，简化ELK部署
    避坑要点:::warn
      Spring全家桶版本严格对齐
      AI接口强制限流，控制token成本
      Prompt放Nacos配置中心，禁止硬编码
      敏感业务优先私有化模型，禁止公网API
      长文本AI任务异步化（RocketMQ）
      安全审计日志不可篡改，独立存储

classDef rootStyle fill:#2c3e50,color:#fff,stroke:#1abc9c,stroke-width:2px
classDef backend fill:#409EFF,color:#fff
classDef ai fill:#67C23A,color:#fff
classDef frontend fill:#E6A23C,color:#fff
classDef test fill:#F56C6C,color:#fff
classDef ops fill:#9B59B6,color:#fff
classDef cicd fill:#17A2B8,color:#fff
classDef backup fill:#8492A6,color:#fff
classDef warn fill:#E74C3C,color:#fff

## 📁 Project Structure
