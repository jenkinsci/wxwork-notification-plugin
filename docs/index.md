---
layout: home
title: 企业微信群机器人通知插件
titleTemplate: false

hero:
  name: 企业微信群机器人通知插件
  tagline: 在 FreeStyle 和 Pipeline 项目中快速发送企业微信群机器人通知，适用于构建开始、结果汇总、失败告警和恢复提醒。
  image:
    src: /logo.png
    alt: 企业微信群机器人通知插件
  actions:
    - theme: brand
      text: 开始使用
      link: /guide/install
    - theme: alt
      text: 前置配置
      link: /guide/configuration
    - theme: alt
      text: GitHub
      link: https://github.com/jenkinsci/wxwork-notification-plugin

features:
  - title: 双模式支持
    details: 同时支持 FreeStyle 项目和 Pipeline 项目，既能通过界面配置，也能通过 Jenkinsfile 进行脚本化调用。
  - title: 多种消息类型
    details: 支持 text、markdown、markdown_v2、image 四种消息类型，满足简单通知到富文本播报的不同场景。
  - title: 灵活提醒能力
    details: 支持 @构建执行人、@所有人、指定手机号提醒，并支持成功、失败、不稳定、恢复等结果通知。
  - title: 适合 Jenkins 场景
    details: 消息内容可直接使用 Jenkins 环境变量和构建参数，便于输出构建号、分支、链接和测试结果。
---

## 快速上手

1. 阅读 [安装](/guide/install) 完成插件部署。
2. 进入 [前置配置](/guide/configuration) 添加企业微信群机器人并配置用户手机号。
3. 按任务类型选择：
   - FreeStyle：参考 [FreeStyle 项目](/guide/freestyle) 在页面中添加“发送企业微信通知”步骤
   - Pipeline：参考 [Pipeline 项目](/guide/pipeline) 在 Jenkinsfile 中调用 `wxwork(...)`
4. 遇到异常时查阅 [常见问题](/guide/faq)。

## 适用场景

- 构建开始、阶段进度、构建结果通知
- 失败告警与恢复提醒
- 通过 Markdown 输出构建摘要、测试结果、发布信息
- 发送截图、二维码等图片消息

## 相关链接

- [插件主页](https://plugins.jenkins.io/wxwork-notification/)
- [源码仓库](https://github.com/jenkinsci/wxwork-notification-plugin)
- [Issue 列表](https://github.com/jenkinsci/wxwork-notification-plugin/issues)
- [企业微信群机器人官方文档](https://developer.work.weixin.qq.com/document/path/99110)
