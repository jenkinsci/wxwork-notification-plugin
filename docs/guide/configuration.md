# 前置配置

以下配置适用于 FreeStyle 和 Pipeline 两种模式，使用前需先完成。

## 1. 配置企业微信群机器人

在 Jenkins 系统配置中添加企业微信群机器人：

`管理 Jenkins -> 系统 -> 企业微信群机器人 -> 新增`

![](../images/stage1-1.png)

![](../images/stage1-2.png)

![](../images/stage1-3.png)

填写机器人信息：

- **ID**：机器人唯一标识，FreeStyle 和 Pipeline 都通过该 ID 引用
- **名称**：机器人显示名称
- **Webhook URL**：企业微信群机器人的 Webhook 地址

![](../images/stage1-4.png)

配置完成后建议先执行连接测试，再保存配置。

## 2. 配置用户手机号（可选）

如果要使用 `@构建执行人` 功能，需要在 Jenkins 用户信息里配置企业微信手机号：

`点击用户名 -> 配置 -> 企业微信手机号`

![](../images/stage2-1.png)

注意事项：

- 手机号必须和企业微信成员绑定手机号一致
- 否则 `@构建执行人` 不会生效
