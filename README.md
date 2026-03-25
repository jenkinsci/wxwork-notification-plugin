# 企业微信机器人通知插件 (WXWork Notification Plugin)

<p align="center">
  <a href="https://plugins.jenkins.io/wxwork-notification/">
    <img src="https://img.shields.io/badge/Jenkins-Plugin-orange?style=flat-square&logo=jenkins" alt="Jenkins Plugin">
  </a>
  <a href="LICENSE">
    <img src="https://img.shields.io/badge/License-MIT-green?style=flat-square" alt="License">
  </a>
  <img src="https://img.shields.io/badge/Java-17%2F21-blue?style=flat-square&logo=java" alt="Java">
  <img src="https://img.shields.io/badge/Jenkins-%3E%3D2.479.3-red?style=flat-square&logo=jenkins" alt="Jenkins">
</p>

<p align="center">
  <strong>在 Jenkins Pipeline 中轻松发送企业微信机器人消息</strong>
</p>

<p align="center">
  <a href="#功能特性">功能特性</a> •
  <a href="#安装">安装</a> •
  <a href="#快速开始">快速开始</a> •
  <a href="#freestyle-项目使用">FreeStyle</a> •
  <a href="#参数说明">参数说明</a> •
  <a href="#使用示例">使用示例</a> •
  <a href="#常见问题">常见问题</a>
</p>

---

## 📋 目录

- [功能特性](#功能特性)
- [安装](#安装)
- [快速开始](#快速开始)
  - [前置配置](#前置配置)
  - [基础使用](#基础使用)
- [FreeStyle 项目使用](#freestyle-项目使用)
  - [Build Step（构建步骤）](#build-step构建步骤)
  - [Post-build Action（构建后操作）](#post-build-action构建后操作)
  - [FreeStyle 字段说明](#freestyle-字段说明)
- [参数说明](#参数说明)
- [使用示例](#使用示例)
  - [文本消息](#文本消息)
  - [Markdown 消息](#markdown-消息)
  - [图片消息](#图片消息)
  - [声明式 Pipeline](#声明式-pipeline)
- [最佳实践](#最佳实践)
- [常见问题](#常见问题)
- [许可证](#许可证)

---

## ✨ 功能特性

- 🚀 **简单易用**: 一行代码即可发送企业微信消息
- 🖥️ **FreeStyle 支持**: 支持 FreeStyle 项目，通过 Build Step 和 Post-build Action 配置通知
- 📝 **多种消息类型**: 支持文本、Markdown、图片等多种格式
- 👥 **智能@功能**: 支持@构建执行人、@所有人、指定手机号
- 🔧 **灵活配置**: 支持全局配置多个机器人，按需使用
- 💬 **环境变量支持**: 支持在消息中使用 Jenkins 环境变量和构建参数
- 🎨 **富文本消息**: Markdown 格式支持标题、列表、代码块、表格等
- 🔒 **HTTPS 支持**: 内置 SSL 证书处理，确保通信安全

---

## 📦 安装

### 方式一：通过 Jenkins 插件管理器安装

1. 打开 Jenkins → 管理 Jenkins → 插件管理
2. 切换到 **Available plugins** 标签页
3. 搜索 **"WXWork Notification"** 或 **"企业微信"**
4. 勾选插件并点击 **Install**

### 方式二：手动安装

1. 下载最新的 `.hpi` 文件：[Jenkins Plugins](https://plugins.jenkins.io/wxwork-notification/)
2. 打开 Jenkins → 管理 Jenkins → 插件管理 → 高级
3. 上传 `.hpi` 文件并安装

---

## 🚀 快速开始

### 前置配置

在开始使用之前，需要完成以下配置：

#### 1. 配置企业微信机器人

在 Jenkins 系统配置中添加企业微信机器人：

**管理 Jenkins → 系统 → 企业微信机器人 → 新增**

![全局设置](docs/images/stage1-1.png)

![系统设置](docs/images/stage1-2.png)

![配置机器人](docs/images/stage1-3.png)

填写机器人信息：
- **ID**: 机器人的唯一标识（在 Pipeline 中使用）
- **名称**: 机器人显示名称
- **Webhook URL**: 企业微信机器人的 Webhook 地址

![测试机器人](docs/images/stage1-4.png)

配置完成后点击 **测试** 验证连接，然后 **保存**。

#### 2. 配置用户手机号（可选）

如需使用 `@我` 功能，需要在 Jenkins 用户设置中配置手机号：

**点击用户名 → 配置 → 企业微信手机号**

![配置手机号](docs/images/stage2-1.png)

> ⚠️ **注意**: 手机号必须与企业微信成员的手机号一致

---

### 基础使用

在 Jenkinsfile 中使用 `wxwork` 步骤发送消息：

```groovy
wxwork(
    robot: 'my-robot',           // 机器人ID（在系统配置中定义）
    type: 'text',                // 消息类型：text/markdown/markdown_v2/image
    text: ['构建成功！']           // 消息内容
)
```

---

## 📖 参数说明

| 参数 | 类型 | 必填 | 默认值 | 说明 |
|------|------|------|--------|------|
| `robot` | String | ✅ | - | 机器人ID，在系统配置中定义的标识 |
| `type` | String | ❌ | `text` | 消息类型：`text`、`markdown`、`markdown_v2`、`image` |
| `text` | List<String> | 条件必填 | - | 文本/Markdown消息内容数组（`text`/`markdown`/`markdown_v2` 类型必填） |
| `imageUrl` | String | 条件必填 | - | 图片相对路径（`image` 类型必填） |
| `atMe` | Boolean | ❌ | `false` | 是否@当前构建执行人 |
| `atAll` | Boolean | ❌ | `false` | 是否@所有人 |
| `at` | List<String> | ❌ | `[]` | 要@的成员手机号列表 |

### 消息类型对比

| 类型 | 说明 | 适用场景 |
|------|------|----------|
| `text` | 纯文本消息 | 简单通知、告警 |
| `markdown` | 简单 Markdown | 基础格式化消息 |
| `markdown_v2` | 完整 Markdown | 复杂排版、表格、代码块 |
| `image` | 图片消息 | 发送截图、二维码 |

---

## 🖥️ FreeStyle 项目使用

FreeStyle 项目可通过两种方式集成企业微信通知，均无需编写 Jenkinsfile：

| 方式 | 触发时机 | 适用场景 |
|------|----------|----------|
| **Build Step（构建步骤）** | 构建过程中，在指定阶段执行 | 构建开始通知、阶段进度播报 |
| **Post-build Action（构建后操作）** | 构建结束后，按结果条件触发 | 成功/失败/不稳定/恢复通知 |

---

### Build Step（构建步骤）

在 **项目配置 → 构建步骤 → 增加构建步骤** 中选择 **发送企业微信通知**。

![Build Step 基础配置](docs/freestyle-images/build-steps-1.png)

![Build Step 高级配置](docs/freestyle-images/build-steps-2.png)

**配置说明：**

- **机器人**：选择在系统配置中预设的机器人
- **消息类型**：选择 `text`、`markdown`、`markdown_v2` 或 `image`；选择 `image` 时图片地址输入框才会显示
- **内容**：消息正文，支持 Jenkins 环境变量（如 `${JOB_NAME}`、`${BUILD_NUMBER}`）
- **@ 构建执行人**：勾选后会 @ 触发当次构建的用户（需在用户设置中配置手机号）
- **@ 所有人**：勾选后 @ 群内所有成员，请谨慎使用
- **@ 用户（手机号）**：多个手机号用英文逗号分隔，如 `13800000000,13900000000`
- **图片地址**：仅 `image` 类型生效，支持环境变量，图片大小建议不超过 2MB

---

### Post-build Action（构建后操作）

在 **项目配置 → 构建后操作 → 增加构建后操作步骤** 中选择 **发送企业微信通知**。

![Post-build Action 基础配置](docs/freestyle-images/post-build-action-1.png)

![Post-build Action 消息内容配置](docs/freestyle-images/post-build-action-2.png)

**通知时机说明：**

| 选项 | 触发条件 |
|------|----------|
| 成功时通知 | 构建结果为 `SUCCESS` |
| 失败时通知 | 构建结果为 `FAILURE` |
| 不稳定时通知 | 构建结果为 `UNSTABLE`（如测试失败） |
| 恢复时通知 | 上次构建为失败/不稳定，本次构建成功（故障恢复提醒） |

> 💡 **勾选某一时机后，对应的消息内容输入框才会展示**，避免配置界面过于冗杂。

**恢复通知说明：**

「恢复时通知」与「成功时通知」是两个独立的条件，可以同时勾选：
- 仅勾选「恢复时通知」：只在故障恢复时发送，正常每次成功不通知
- 同时勾选两者：每次成功都发，恢复时使用「恢复消息」内容，其余成功使用「成功消息」内容
- **恢复消息**：若未填写，自动回退使用「成功消息」内容

---

### FreeStyle 字段说明

| 字段 | Builder | Notifier | 说明 |
|------|:-------:|:--------:|------|
| 机器人 | ✅ | ✅ | 系统配置中预设的机器人 |
| 消息类型 | ✅ | ✅ | text / markdown / markdown_v2 / image |
| 成功时通知 | — | ✅ | 构建结果为 SUCCESS 时发送 |
| 失败时通知 | — | ✅ | 构建结果为 FAILURE 时发送 |
| 不稳定时通知 | — | ✅ | 构建结果为 UNSTABLE 时发送 |
| 恢复时通知 | — | ✅ | 上次失败/不稳定、本次成功时发送 |
| 内容 | ✅ | — | 构建中发送的消息内容 |
| 成功消息 | — | ✅ | 构建成功时的消息内容 |
| 失败消息 | — | ✅ | 构建失败时的消息内容 |
| 不稳定消息 | — | ✅ | 构建不稳定时的消息内容 |
| 恢复消息 | — | ✅ | 恢复时的消息内容，留空则使用成功消息 |
| @ 构建执行人 | ✅ | ✅ | @ 触发当次构建的用户 |
| @ 所有人 | ✅ | ✅ | @ 群内全体成员 |
| @ 用户（手机号） | ✅ | ✅ | 逗号分隔的手机号列表 |
| 图片地址 | ✅ | ✅ | 仅 image 类型生效，支持环境变量 |

所有消息内容字段均支持 Jenkins 环境变量，常用变量：

```
${JOB_NAME}      项目名称
${BUILD_NUMBER}  构建编号
${BUILD_URL}     构建详情链接
${NODE_NAME}     构建节点名称
${GIT_BRANCH}    Git 分支（需 Git 插件）
${GIT_COMMIT}    Git 提交 SHA（需 Git 插件）
```

---

## 💡 使用示例

### 文本消息

```groovy
node {
    stage('发送通知') {
        wxwork(
            robot: 'my-robot',
            type: 'text',
            atMe: true,                              // @我自己
            atAll: false,                            // 不@所有人
            at: ['13800138000', '13900139000'],      // @指定手机号
            text: [
                '🎉 构建成功！',
                "📦 项目: ${env.JOB_NAME}",
                "🔢 构建号: ${env.BUILD_NUMBER}",
                "👤 执行人: ${env.BUILD_USER}",
                "⏱️ 耗时: ${currentBuild.durationString}",
                "🔗 链接: ${env.BUILD_URL}"
            ]
        )
    }
}
```

### Markdown 消息

```groovy
wxwork(
    robot: 'my-robot',
    type: 'markdown',
    text: [
        '## 🎉 构建成功',
        '',
        '- **项目**: ' + env.JOB_NAME,
        '- **分支**: ' + env.BRANCH_NAME,
        '- **构建号**: #' + env.BUILD_NUMBER,
        '- **执行人**: ' + env.BUILD_USER,
        '',
        '> 点击查看详情：[' + env.BUILD_URL + '](' + env.BUILD_URL + ')'
    ]
)
```

### Markdown V2 消息（富文本）

支持完整的 Markdown 语法：

```groovy
wxwork(
    robot: 'my-robot',
    type: 'markdown_v2',
    text: [
        "# 📋 构建报告\n## 基本信息",
        "",
        "| 项目 | 值 |",
        "|------|------|",
        "| **项目名** | ${env.JOB_NAME} |",
        "| **构建号** | #${env.BUILD_NUMBER} |",
        "| **状态** | ✅ 成功 |",
        "| **执行人** | ${env.BUILD_USER} |",
        "",
        "### 📊 代码统计",
        "- 新增: +120 行",
        "- 删除: -45 行",
        "- 变更: 5 个文件",
        "",
        "### 📝 变更日志",
        "```",
        "feat: 添加用户登录功能",
        "fix: 修复内存泄漏问题",
        "docs: 更新 API 文档",
        "```",
        "",
        "---",
        "",
        "[点击查看构建详情](${env.BUILD_URL})"
    ]
)
```

### 图片消息

```groovy
wxwork(
    robot: 'my-robot',
    type: 'image',
    imageUrl: 'screenshots/build-result.png'  // 相对工作目录的路径
)
```

### 声明式 Pipeline

```groovy
pipeline {
    agent any
    
    stages {
        stage('构建') {
            steps {
                echo '执行构建...'
                // 构建步骤
            }
        }
        
        stage('测试') {
            steps {
                echo '执行测试...'
                // 测试步骤
            }
        }
    }
    
    post {
        success {
            wxwork(
                robot: 'my-robot',
                type: 'markdown',
                text: ['✅ 构建成功', "项目: ${env.JOB_NAME}"]
            )
        }
        
        failure {
            wxwork(
                robot: 'my-robot',
                type: 'markdown',
                atMe: true,
                text: [
                    '❌ 构建失败',
                    "项目: ${env.JOB_NAME}",
                    "构建号: #${env.BUILD_NUMBER}",
                    "点击查看: ${env.BUILD_URL}"
                ]
            )
        }
        
        always {
            // 清理操作
        }
    }
}
```

---

## 🎯 最佳实践

### 1. 在 Post 阶段发送通知

```groovy
post {
    success {
        wxwork(
            robot: 'my-robot',
            type: 'text',
            text: ['✅ 构建成功']
        )
    }
    failure {
        wxwork(
            robot: 'my-robot',
            type: 'text',
            atMe: true,
            text: ['❌ 构建失败，请检查']
        )
    }
    unstable {
        wxwork(
            robot: 'my-robot',
            type: 'text',
            text: ['⚠️ 构建不稳定']
        )
    }
}
```

### 2. 使用共享库封装

创建共享库 `vars/sendWxworkNotification.groovy`：

```groovy
def call(Map config = [:]) {
    def robot = config.robot ?: 'default-robot'
    def type = config.type ?: 'markdown'
    def status = config.status ?: currentBuild.currentResult
    
    def emoji = [
        'SUCCESS': '✅',
        'FAILURE': '❌',
        'UNSTABLE': '⚠️',
        'ABORTED': '🚫'
    ][status] ?: 'ℹ️'
    
    wxwork(
        robot: robot,
        type: type,
        atMe: status == 'FAILURE',
        text: [
            "${emoji} 构建${status}",
            "",
            "| 项目 | ${env.JOB_NAME} |",
            "| 构建号 | #${env.BUILD_NUMBER} |",
            "| 分支 | ${env.BRANCH_NAME ?: 'N/A'} |",
            "| 耗时 | ${currentBuild.durationString} |",
            "",
            "[查看详情](${env.BUILD_URL})"
        ]
    )
}
```

使用：

```groovy
@Library('my-shared-library') _

pipeline {
    agent any
    stages {
        // ...
    }
    post {
        always {
            sendWxworkNotification(
                robot: 'my-robot',
                status: currentBuild.currentResult
            )
        }
    }
}
```

### 3. 发送测试报告截图

```groovy
stage('生成报告') {
    steps {
        // 生成 Allure 或 HTML 报告
        allure includeProperties: false, jdk: '', results: [[path: 'allure-results']]
    }
}

stage('发送报告') {
    steps {
        // 截图
        sh 'wkhtmltoimage --width 800 report.html report.png'
        
        wxwork(
            robot: 'my-robot',
            type: 'image',
            imageUrl: 'report.png'
        )
    }
}
```

---

## ❓ 常见问题

### Q: 插件安装后找不到 `wxwork` 步骤？

**A:** 
1. 确保插件已成功安装并重启 Jenkins
2. 检查 Jenkins 版本 >= 2.479.3
3. 在 Pipeline Syntax 生成器中搜索 `wxwork`

### Q: 消息发送失败，提示"机器人配置找不到"？

**A:**
1. 检查 `robot` 参数是否与系统配置中的 **ID** 完全一致（区分大小写）
2. 确认已在 **系统配置 → 企业微信机器人** 中添加了机器人
3. 检查 Webhook URL 是否正确

### Q: `@我` 功能不生效？

**A:**
1. 确保已在用户配置中填写了**企业微信手机号**
2. 确认手机号与企业微信中的成员手机号一致
3. 检查 `atMe: true` 参数已正确设置

### Q: Markdown V2 表格显示不正常？

**A:**
- 确保表格语法格式正确（包含表头分隔符 `|---|`）
- 每行以 `\n` 换行
- 示例：
  ```groovy
  text: [
      "| 列1 | 列2 |",
      "|------|------|",
      "| A | B |"
  ]
  ```

### Q: 图片消息发送失败？

**A:**
1. 确认 `imageUrl` 是相对工作目录的路径
2. 确保图片文件存在且格式正确（支持 JPG、PNG）
3. 检查图片大小（建议不超过 2MB）

### Q: 如何在消息中使用环境变量？

**A:**
所有消息内容都支持使用 Jenkins 环境变量：

```groovy
text: [
    "项目: ${env.JOB_NAME}",           // 环境变量
    "构建号: ${currentBuild.number}",   // 全局变量
    "参数: ${params.MY_PARAM}"          // 构建参数
]
```

### Q: 支持多个机器人同时发送吗？

**A:** 可以，多次调用 `wxwork` 步骤即可：

```groovy
parallel (
    "Robot A": {
        wxwork(robot: 'robot-a', type: 'text', text: ['Hello A'])
    },
    "Robot B": {
        wxwork(robot: 'robot-b', type: 'text', text: ['Hello B'])
    }
)
```

### Q: FreeStyle 项目中配置了通知但没有发送？

**A:**
1. 检查 Post-build Action 中对应的通知时机是否已勾选（如"失败时通知"）
2. 确认构建结果与勾选的时机匹配
3. 查看 Jenkins 构建日志，搜索 `WXWORK:` 前缀的日志行确认执行情况

### Q: FreeStyle 的「恢复时通知」不触发？

**A:**
1. 需要当前构建结果为 `SUCCESS`，且上一次构建结果为 `FAILURE` 或 `UNSTABLE`
2. 首次构建（没有上一次构建记录）不会触发恢复通知
3. 检查 Jenkins 是否能访问到上一次构建的结果（`build.getPreviousBuild()`）

### Q: FreeStyle 消息内容中环境变量不展开？

**A:**
- 消息内容中的 `${JOB_NAME}` 等变量是在**构建执行时**由 Jenkins 展开的，而不是在配置页面保存时
- 配置页面显示的只是变量占位符文本，构建日志中发出的实际消息才会包含真实值
- 若变量仍未展开，检查该变量在当前构建环境中是否存在（如 `${GIT_BRANCH}` 需要安装 Git 插件）

---

## 📄 许可证

本项目采用 [MIT 许可证](LICENSE) 开源。

---

## 🤝 贡献

欢迎提交 Issue 和 Pull Request！

- 问题反馈：[GitHub Issues](https://github.com/jenkinsci/wxwork-notification-plugin/issues)
- 插件主页：[Jenkins Plugins](https://plugins.jenkins.io/wxwork-notification/)

---

<p align="center">
  Made with ❤️ by <a href="https://github.com/nekoimi">nekoimi</a>
</p>
