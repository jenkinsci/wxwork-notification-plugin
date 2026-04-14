# 企业微信机器人通知插件

Jenkins 插件 `wxwork-notification-plugin`，用于在 FreeStyle 和 Pipeline 项目中向企业微信机器人发送构建通知。

适合用于构建开始通知、结果汇总、失败告警、恢复提醒，以及通过 Markdown 或图片输出更清晰的构建信息。

## 文档导航

- [安装](/guide/install)
- [前置配置](/guide/configuration)
- [FreeStyle 项目](/guide/freestyle)
- [Pipeline 项目](/guide/pipeline)
- [常见问题](/guide/faq)

## 功能特性

- 同时支持 FreeStyle 项目和 Pipeline 项目
- 支持 `text`、`markdown`、`markdown_v2`、`image` 四种消息类型
- 支持 `@构建执行人`、`@所有人`、指定手机号提醒
- 支持多机器人全局管理，按任务自由选择
- 消息内容支持 Jenkins 环境变量与构建参数展开
- FreeStyle 支持成功、失败、不稳定、恢复四种结果通知
- `markdown_v2` 可用于表格、代码块等更完整的富文本排版

## 推荐阅读顺序

1. 先阅读 [安装](/guide/install) 完成插件部署。
2. 再阅读 [前置配置](/guide/configuration) 配置机器人与用户手机号。
3. 根据任务类型继续阅读 [FreeStyle 项目](/guide/freestyle) 或 [Pipeline 项目](/guide/pipeline)。
4. 遇到异常时查阅 [常见问题](/guide/faq)。

## 适用场景

- 构建开始、阶段进度、构建结果通知
- 失败告警与恢复提醒
- 通过 Markdown 输出构建摘要、测试结果、发布信息
- 发送截图、二维码等图片消息

## 快速开始

1. 先完成 [安装](/guide/install)。
2. 在 [前置配置](/guide/configuration) 中配置机器人。
3. 根据任务类型选择：
   - FreeStyle：在页面中添加“发送企业微信通知”步骤
   - Pipeline：在 Jenkinsfile 中调用 `wxwork(...)`

## 相关链接

- [插件主页](https://plugins.jenkins.io/wxwork-notification/)
- [源码仓库](https://github.com/jenkinsci/wxwork-notification-plugin)
- [Issue 列表](https://github.com/jenkinsci/wxwork-notification-plugin/issues)
- [企业微信机器人官方文档](https://developer.work.weixin.qq.com/document/path/99110)
