# FreeStyle 项目

FreeStyle 项目通过 UI 配置通知，无需编写代码。支持两种集成方式：

| 方式 | 触发时机 | 适用场景 |
|------|----------|----------|
| Build Step（构建步骤） | 构建过程中 | 构建开始通知、阶段播报 |
| Post-build Action（构建后操作） | 构建结束后 | 成功/失败/不稳定/恢复通知 |

## Build Step（构建步骤）

路径：`项目配置 -> 构建步骤 -> 增加构建步骤 -> 发送企业微信通知`

![](../freestyle-images/build-steps-1.png)

![](../freestyle-images/build-steps-2.png)

字段说明：

| 字段 | 说明 |
|------|------|
| 机器人 | 选择系统配置中的机器人 |
| 消息类型 | `text` / `markdown` / `markdown_v2` / `image` |
| 内容 | 消息正文，支持 Jenkins 环境变量 |
| @ 构建执行人 | 勾选后 @ 触发构建的用户 |
| @ 所有人 | 勾选后 @ 群内所有成员 |
| @ 用户（手机号） | 逗号分隔手机号列表 |
| 图片地址 | 仅 `image` 类型生效 |

## Post-build Action（构建后操作）

路径：`项目配置 -> 构建后操作 -> 增加构建后操作步骤 -> 发送企业微信通知`

![](../freestyle-images/post-build-action-1.png)

![](../freestyle-images/post-build-action-2.png)

通知时机：

| 时机 | 触发条件 |
|------|----------|
| 成功时通知 | 构建结果 `SUCCESS` |
| 失败时通知 | 构建结果 `FAILURE` |
| 不稳定时通知 | 构建结果 `UNSTABLE` |
| 恢复时通知 | 上次失败/不稳定，本次成功 |

恢复通知与成功通知是独立条件，可以按需组合勾选。

## 常用环境变量

```text
${JOB_NAME}      项目名称
${BUILD_NUMBER}  构建编号
${BUILD_URL}     构建详情链接
${NODE_NAME}     构建节点名称
${GIT_BRANCH}    Git 分支（需 Git 插件）
${GIT_COMMIT}    Git 提交 SHA（需 Git 插件）
```
