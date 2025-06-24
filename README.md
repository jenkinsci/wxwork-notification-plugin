# 企业微信机器人Jenkins插件

### Introduction

企业微信机器人Jenkins插件

### Quick start
- 必要的配置 
  - 配置说明：[参考文档](docs/settings.md)

- 调用说明
  - 函数：wxwork()
  - 参数：
    - robot：字符串，机器人ID，在 jenkins 系统配置中配置的企业微信机器人ID
    - type：字符串，企业微信消息类型（text - 文本消息 / markdown - Markdown格式消息 / image - 图片消息）
    - atMe：Boolean，消息是否"@"我自己(当前构建任务执行人)
    - atAll：Boolean，消息是否"@"所有人
    - at：Array<String>：需要被"@"到的人的企业微信成员手机号列表
    - text：Array<String>：消息内容数组
    - imageUrl：字符串，当前项目图片文件的相对路径（只有`type`为`image`时有效）


- Jenkinsfile （脚本写法）

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
                "env: ${ENV_NAME}",                         // 使用环境变量
                "params: ${params.name}",                   // 使用构建参数变量
                "displayName: ${currentBuild.displayName}", // 使用全局变量
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
                        "env: ${ENV_NAME}",                         // 使用环境变量
                        "params: ${params.name}",                   // 使用构建参数变量
                        "displayName: ${currentBuild.displayName}", // 使用全局变量
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

- Jenkinsfile （声明式写法）

```groovy
pipeline {
    agent any
    stages {
        stage('Hello') {
            steps {
                echo 'Hello World'
                
                // 发送消息
                wxwork(
                    robot: 'test',
                    type: 'text',
                    text: [
                        "stage: Hello",
                        "displayName: ${currentBuild.displayName}",
                        "absoluteUrl: ${currentBuild.absoluteUrl}"
                    ]
                )
            }
        }
        
        stage('build') {
            steps {
                script {
                    println "build number: ${currentBuild.number}";
    			    println "current result: ${currentBuild.currentResult}";
    			    println "build URL: ${currentBuild.absoluteUrl}";
    			    
                    // 发送消息
                    wxwork(
                        robot: 'test',
                        type: 'text',
                        text: [
                            "stage: build",
                            "displayName: ${currentBuild.displayName}",
                            "absoluteUrl: ${currentBuild.absoluteUrl}"
                        ]
                    )
                }
            }
        }
        
        
        stage('some-stage') {
            steps {
                script {
                    println "build number: ${currentBuild.number} - some stage";
                    
                    // err
                    sh "fsdfsdf"
    			    
                    // 发送消息
                    wxwork(
                        robot: 'test',
                        type: 'text',
                        text: [
                            "stage: some-stage",
                            "displayName: ${currentBuild.displayName}",
                            "absoluteUrl: ${currentBuild.absoluteUrl}"
                        ]
                    )
                }
            }
        }
        
    }
    
    post {
        always {
            // 无论什么情况都发送消息
            wxwork(
                robot: 'test',
                type: 'text',
                text: [
                    "post-always",
                    "displayName: ${currentBuild.displayName}",
                    "absoluteUrl: ${currentBuild.absoluteUrl}"
                ]
            )
        }
        
        
        failure {
            script {
                // 状态异常
                wxwork(
                    robot: 'test',
                    type: 'text',
                    text: [
                        "post-failure",
                        "displayName: ${currentBuild.displayName}",
                        "absoluteUrl: ${currentBuild.absoluteUrl}"
                    ]
                )
            }
        }
    }
}
```

### LICENSE

Licensed under MIT, see [LICENSE](LICENSE)

