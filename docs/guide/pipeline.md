# Pipeline 项目

Pipeline 通过 `wxwork` 步骤发送通知，支持脚本式和声明式。

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
|------|------|:----:|--------|------|
| `robot` | String | 是 | - | 机器人 ID（与系统配置一致） |
| `type` | String | 否 | `text` | `text`/`markdown`/`markdown_v2`/`image` |
| `text` | List\<String\> | 条件 | - | 非 `image` 类型必填 |
| `imageUrl` | String | 条件 | - | `image` 类型必填 |
| `atMe` | Boolean | 否 | `false` | 是否 @ 构建执行人 |
| `atAll` | Boolean | 否 | `false` | 是否 @ 所有人 |
| `at` | List\<String\> | 否 | `[]` | 指定手机号列表 |

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
        "- 项目: ${env.JOB_NAME}",
        "- 分支: ${env.BRANCH_NAME}",
        "- 构建号: #${env.BUILD_NUMBER}",
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
                text: ['构建失败', "[查看详情](${env.BUILD_URL})"]
            )
        }
    }
}
```
