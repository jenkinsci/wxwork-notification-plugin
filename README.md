# Jenkins 企业微信机器人通知

### Introduction

WXWork for jenkins

### Quick start

- Jenkinsfile

```groovy
#!groovy

node {

    stage('Send WXWork') {
        wxwork(
                robot: 'ID',
                type: 'TEXT',
                text: [
                        'hello',
                        'world'
                ]
        )
    }

}
```

### Doc

[文档点这里！](https://github.com/nekoimi/wxwork-plugin/wiki)

### LICENSE

Licensed under MIT, see [LICENSE](LICENSE)

