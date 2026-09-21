# PixelOffice

<p align="center">
  <img src="https://placehold.co/800x400/1a1a2e/ffd166/png?text=Pixel+Cat&font=oswald" alt="PixelOffice 像素猫猫" width="800">
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
  <img src="https://placehold.co/900x500/1e1e2e/8be9fd/png?text=PixelOffice+Game+Preview&font=montserrat" alt="PixelOffice 游戏内办公室全景" width="900">
</p>

> 上图为游戏内办公室全景，包含开发部、项目部、财务部、总经办、档案室、休息区等完整区域

## 🛠️ Technology Stack

### 🎨 架构总览

```mermaid
flowchart LR
    ROOT["🏢 PixelOffice<br/>技术架构"]

    ROOT --> BE["🧩 后端基础<br/>Spring Cloud Alibaba"]
    ROOT --> AI["🤖 AI 模块<br/>ai-service"]
    ROOT --> FE["🎨 前端<br/>Vue3 + Vite"]
    ROOT --> TEST["🧪 测试体系"]
    ROOT --> OPS["📡 运维 & 可观测"]
    ROOT --> CICD["🚀 CI/CD 流水线"]
    ROOT --> FALL["🛟 降级方案"]
    ROOT --> PIT["⚠️ 避坑要点"]

    classDef cRoot fill:#FF6B9D,stroke:#C2185B,stroke-width:3px,color:#FFFFFF,font-weight:bold
    classDef cBlue fill:#42A5F5,stroke:#1565C0,stroke-width:2px,color:#FFFFFF
    classDef cPurple fill:#AB47BC,stroke:#6A1B9A,stroke-width:2px,color:#FFFFFF
    classDef cCyan fill:#26C6DA,stroke:#00838F,stroke-width:2px,color:#FFFFFF
    classDef cGreen fill:#66BB6A,stroke:#2E7D32,stroke-width:2px,color:#FFFFFF
    classDef cOrange fill:#FFA726,stroke:#E65100,stroke-width:2px,color:#FFFFFF
    classDef cPink fill:#EC407A,stroke:#AD1457,stroke-width:2px,color:#FFFFFF
    classDef cBrown fill:#8D6E63,stroke:#4E342E,stroke-width:2px,color:#FFFFFF
    classDef cRed fill:#EF5350,stroke:#B71C1C,stroke-width:2px,color:#FFFFFF

    class ROOT cRoot
    class BE cBlue
    class AI cPurple
    class FE cCyan
    class TEST cGreen
    class OPS cOrange
    class CICD cPink
    class FALL cBrown
    class PIT cRed
