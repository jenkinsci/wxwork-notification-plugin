# Jenkins 企业微信机器人通知

### Introduction

WXWork for jenkins

### Quick start

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
                'world'
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
                        '> content'
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

- 详细使用参考文档：[文档点这里！](https://github.com/nekoimi/wxwork-plugin/wiki)

- 企业微信机器人开发文档: [企业微信官方文档](https://developer.work.weixin.qq.com/document/path/91770?version=4.0.8.6027&platform=win)

### LICENSE

Licensed under MIT, see [LICENSE](LICENSE)

