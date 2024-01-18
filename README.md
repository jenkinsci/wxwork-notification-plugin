# 企业微信机器人Jenkins插件

### Introduction

企业微信机器人Jenkins插件

### Quick start
- 必要的配置
    - 配置企业微信机器人：需要在 jenkins 系统配置中配置企业微信机器人，支持配置多个机器人
    - 配置用户的手机号码：需要在 jenkins 用户信息页面，配置企业微信成员手机号码，用于在企业微信中 @ 成员

- Jenkinsfile

```groovy
#!groovy

node {

    stage('文本消息') {
        
        wxwork(
            robot: 'ID',
            type: 'text', 
            atMe: true,                 // 是否"@"我自己(当前构建任务执行人)
            atAll: false,               // 是否"@"所有人
            at: [                       // 企业微信成员手机号列表
                'mobile1', 'mobile2'
            ],
            text: [
                'hello',
                'world',
                'env: ${ENV_NAME}'      // 使用环境变量
            ]
        )
        
    }

    stage('Markdown消息') {

        wxwork(
                robot: 'ID',
                type: 'markdown',
                text: [
                        '# hello world',
                        '- ppppppppp',
                        '- ppppppppp',
                        '> content',
                        'env: ${ENV_NAME}'      // 使用环境变量
                ]
        )

    }

    stage('图片消息') {

        wxwork(
                robot: 'ID',
                type: 'image',
                imageUrl: 'image.png'  // 当前项目图片文件相对路径
        )

    }
    
}
```

- 企业微信机器人开发文档: [企业微信官方文档](https://developer.work.weixin.qq.com/document/path/91770?version=4.0.8.6027&platform=win)

### LICENSE

Licensed under MIT, see [LICENSE](LICENSE)

