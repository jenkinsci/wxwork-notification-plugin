# 安装

## 方式一：通过 Jenkins 插件管理器安装

1. 打开 Jenkins，进入 `管理 Jenkins -> 插件管理`。
2. 切换到 `Available plugins` 标签页。
3. 搜索 `WXWork Notification` 或 `企业微信`。
4. 勾选插件并执行安装。

## 方式二：手动安装

1. 从 [Jenkins Plugins](https://plugins.jenkins.io/wxwork-notification/) 下载最新 `.hpi` 文件。
2. 打开 `管理 Jenkins -> 插件管理 -> 高级`。
3. 上传 `.hpi` 文件并安装。

## 版本要求

- Jenkins >= `2.479.3`
- Java `17` 或 `21`

## 安装后建议

1. 安装完成后重启 Jenkins，确保 Pipeline 步骤与 FreeStyle 扩展点都已正确加载。
2. 进入 [前置配置](/guide/configuration) 先配置企业微信群机器人。
3. 在 Pipeline 项目中，可通过 `Pipeline Syntax` 搜索 `wxwork` 验证步骤是否可用。

## 验证是否安装成功

- 在 `系统配置` 中可以看到企业微信群机器人相关全局配置项。
- 在 FreeStyle 项目中可以看到“发送企业微信通知”的构建步骤和构建后操作。
- 在 Pipeline 中可以调用 `wxwork(...)` 步骤发送消息。
