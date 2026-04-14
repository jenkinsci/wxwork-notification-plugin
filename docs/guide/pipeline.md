# Pipeline 项目

Pipeline 通过 `wxwork` 步骤发送通知，支持脚本式和声明式两种写法。

## 快速开始

```groovy
wxwork(
    robot: 'my-robot',
    type: 'text',
    text: ['构建成功！']
)
```

## 参数说明

| 参数 | 类型 | 必填 | 默认值 | 说明 |
| --- | --- | :---: | --- | --- |
| `robot` | String | 是 | - | 机器人 ID，需与系统配置中的 ID 完全一致 |
| `type` | String | 否 | `text` | `text` / `markdown` / `markdown_v2` / `image` |
| `text` | `List<String>` | 条件 | - | `text`、`markdown`、`markdown_v2` 类型必填 |
| `imageUrl` | String | 条件 | - | `image` 类型必填，支持环境变量展开 |
| `atMe` | Boolean | 否 | `false` | 是否 `@` 当前构建执行人 |
| `atAll` | Boolean | 否 | `false` | 是否 `@` 所有人 |
| `at` | `List<String>` | 否 | `[]` | 指定手机号列表 |

说明：

- `text` 需要传入字符串列表，列表中的每一项会按消息内容逐行组织
- `imageUrl` 一般填写工作目录下的相对路径，也支持可展开的环境变量

## 消息类型对比

| 类型 | 说明 | 适用场景 |
| --- | --- | --- |
| `text` | 纯文本消息 | 简单通知、告警 |
| `markdown` | 基础 Markdown | 标题、列表、链接等基础排版 |
| `markdown_v2` | 更完整的 Markdown | 表格、代码块、构建摘要 |
| `image` | 图片消息 | 截图、二维码、报告图片 |

## 使用示例

### 文本消息

```groovy
node {
    stage('发送通知') {
        wxwork(
            robot: 'my-robot',
            type: 'text',
            atMe: true,
            at: ['13800138000', '13900139000'],
            text: [
                '构建成功',
                "项目: ${env.JOB_NAME}",
                "构建号: #${env.BUILD_NUMBER}",
                "详情: ${env.BUILD_URL}"
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
        '## 构建成功',
        '',
        "- 项目: ${env.JOB_NAME}",
        "- 分支: ${env.BRANCH_NAME}",
        "- 构建号: #${env.BUILD_NUMBER}",
        '',
        "[查看详情](${env.BUILD_URL})"
    ]
)
```

### Markdown V2 消息

```groovy
wxwork(
    robot: 'my-robot',
    type: 'markdown_v2',
    text: [
        '# 构建报告',
        '',
        '| 项目 | 值 |',
        '|------|------|',
        "| 项目名 | ${env.JOB_NAME} |",
        "| 构建号 | #${env.BUILD_NUMBER} |",
        "| 状态 | SUCCESS |",
        '',
        '```',
        'test summary here',
        '```',
        '',
        "[查看详情](${env.BUILD_URL})"
    ]
)
```

### 图片消息

```groovy
wxwork(
    robot: 'my-robot',
    type: 'image',
    imageUrl: 'report.png'
)
```

`imageUrl` 通常使用相对于当前工作目录的路径，例如构建过程中生成的 `report.png`。

### 声明式 Pipeline

```groovy
pipeline {
    agent any

    stages {
        stage('构建') {
            steps {
                echo '执行构建'
            }
        }
    }

    post {
        success {
            wxwork(
                robot: 'my-robot',
                type: 'markdown',
                text: [
                    '构建成功',
                    "项目: ${env.JOB_NAME}",
                    "构建号: #${env.BUILD_NUMBER}",
                    "[查看详情](${env.BUILD_URL})"
                ]
            )
        }
        failure {
            wxwork(
                robot: 'my-robot',
                type: 'markdown',
                atMe: true,
                text: [
                    '构建失败',
                    "项目: ${env.JOB_NAME}",
                    "构建号: #${env.BUILD_NUMBER}",
                    "[查看详情](${env.BUILD_URL})"
                ]
            )
        }
        unstable {
            wxwork(
                robot: 'my-robot',
                type: 'markdown',
                text: [
                    '构建不稳定',
                    "项目: ${env.JOB_NAME}",
                    "构建号: #${env.BUILD_NUMBER}",
                    "[查看详情](${env.BUILD_URL})"
                ]
            )
        }
    }
}
```

## 最佳实践

### 1. 在 `post` 中统一发送结果通知

如果你只关心最终构建结果，建议将通知逻辑放在声明式 Pipeline 的 `post` 中，便于统一维护成功、失败、不稳定消息。

### 2. 将通知封装到共享库

多个流水线复用同一套通知格式时，可封装到 Jenkins Shared Library，统一机器人、模板和 `@` 策略。

### 3. 使用变量输出上下文信息

常见可用变量包括：

```groovy
text: [
    "项目: ${env.JOB_NAME}",
    "构建号: ${currentBuild.number}",
    "构建地址: ${env.BUILD_URL}",
    "自定义参数: ${params.MY_PARAM}"
]
```
