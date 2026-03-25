# Changelog

所有重要的变更都将记录在此文件中。

格式基于 [Keep a Changelog](https://keepachangelog.com/zh-CN/1.0.0/)，
并且本项目遵循 [语义化版本](https://semver.org/lang/zh-CN/)规范。

## [Unreleased]

### Added
- ✨ **新增 FreeStyle 项目支持**
  - `WxWorkBuilder`：Build Step，在构建过程中的任意阶段发送通知
  - `WxWorkNotifier`：Post-build Action，根据构建结果（成功/失败/不稳定/恢复）有选择性地发送通知
  - `recoveryContent`：恢复场景独立消息字段，未配置时自动回退到成功消息内容，向下兼容
  - 消息类型下拉选项添加可读标题（title）
- ✨ **新增 UI 联动交互**
  - 消息类型切换为非 `image` 时，自动隐藏图片地址输入框
  - 通知时机 checkbox 取消勾选时，自动隐藏对应消息内容 textarea
- ✨ **新增字段帮助提示（? 图标）**，覆盖 robot、type、at、atMe、atAll、imageUrl、content、notifyRecovery 等关键字段
- 完善 README.md 文档，添加更友好的使用说明
- 完善 AGENTS.md 文档，为 AI 智能体提供开发指导

### Changed
- 🔧 提取 `FreeStyleJobHelper` 公共工具类，消除 `WxWorkBuilder` 与 `WxWorkNotifier` 之间的重复代码
- 🎨 优化 FreeStyle 配置页面默认消息内容，统一包含项目名、构建编号、分支、提交记录、详情链接及状态 emoji
- 🔧 优化机器人配置页面 JavaScript 代码
  - 使用 `URLSearchParams` 替代手动拼接查询字符串
  - 添加完整的错误处理和参数验证
  - 使用事件委托替代 `DOMContentLoaded` 监听
  - 优化函数命名，提升代码可读性
  - 优化 CSS class 名称，避免与其他插件冲突
- 🎨 将 `config.jelly` 内联脚本提取到 `wxwork-robot-config.js`，使用 `st:adjunct` 标签引用
- 🎨 移除内联 `onclick` 事件处理器，改用 JavaScript 事件绑定

### Fixed
- 🐛 修复 FreeStyle 模式下 `atMe` 双重处理问题 — 删除手动将执行人手机号加入 at 列表的冗余逻辑，由 Transfer 层统一处理
- 🐛 修复 FreeStyle 配置页面默认消息内容中 Jenkins 变量占位符（`${JOB_NAME}` 等）被 Jelly EL 求值为空字符串的问题
- 🐛 修复文件名重复引用导致的 Jenkins 页面视图冲突问题 — 将 `config.js` 重命名为 `wxwork-robot-config.js`，避免与其他配置文件命名冲突

### Security
- 🔒 提取内联脚本块和事件处理器（JENKINS-74391）— 将内联 JavaScript 提取到外部文件，符合 CSP 合规性要求
- 🔒 修复 SSL 证书验证被禁用的安全漏洞 — 移除 `TrustAllTrustManager` 和自定义 SSLContext，使用 Java 默认的证书验证机制，防止中间人攻击

## [1.0.2] - 2025-06-24

### Added
- ✨ **新增 Markdown V2 消息支持** - 支持完整的 Markdown 语法，包括表格、代码块、引用等复杂格式

### Changed
- 🔧 **升级 Jenkins 基线版本至 2.479.3** - 迁移到 Jakarta EE 9
- 📦 升级 plugin-pom 至 6.2138.v03274d462c13
- 📦 升级 Lombok 至 1.18.42
- 📦 升级 BOM 至 2.479.x 最新版本

### Fixed
- 更新 README.md 文档

## [1.0.1] - 2024-08-13

### Added
- 📚 完善配置和使用文档
- 🧪 添加一键测试功能，可在系统配置中测试机器人连接

### Changed
- 🔧 升级 Jenkins 最低支持版本至 2.401.3
- 📦 升级 plugin-pom 至 4.87
- 📦 升级 Lombok 至 1.18.34
- 📦 升级 token-macro 至 400.v35420b_922dcb_
- 🎨 优化对象属性 Builder 模式实现

### Fixed
- 🐛 修复新版 Jenkins 机器人配置测试失效的问题
- 🐛 修复 NullPointerException 当获取执行人手机号时
- 🔒 修复 CSRF 和权限验证问题
- 🔒 修复可能的 SSL 安全问题

## [1.0.0] - 2022-11-14

### Added
- 🎉 **初始版本发布**
- ✨ 支持文本消息发送
- ✨ 支持 Markdown 消息发送
- ✨ 支持图片消息发送
- ✨ 支持 @指定成员（通过手机号）
- ✨ 支持 @所有人
- ✨ 支持 @当前构建执行人
- ✨ 支持环境变量和构建参数
- ✨ 支持配置多个机器人
- ✨ Pipeline 步骤支持（脚本式和声明式）
- ✨ 全局配置页面
- ✨ 用户手机号扩展属性配置

### Changed
- 🏗️ 使用工厂模式构建消息对象
- 🏗️ 使用策略模式处理不同消息类型
- 🏗️ 使用单例模式管理发送器和全局配置
- 📝 完善中文文档

## [0.9.0] - 2022-07-24

### Added
- 🎨 完善 Pipeline 步骤实现
- 🔧 添加 HTTP 客户端实现（支持 HTTPS）
- 📝 添加使用文档

### Changed
- ♻️ 重构消息发送逻辑
- ♻️ 优化代码结构

## [0.1.0] - 2022-07-15

### Added
- 🎉 项目初始化
- 🏗️ 搭建基础架构
- 📦 配置 Maven 构建
- 🔧 集成 Jenkins 插件框架

---

## 版本号说明

本插件使用语义化版本控制 (SemVer)：

- **主版本号 (Major)**：不兼容的 API 修改
- **次版本号 (Minor)**：向下兼容的功能性新增
- **修订号 (Patch)**：向下兼容的问题修正

## 类型说明

- `Added` - 新增功能
- `Changed` - 变更
- `Deprecated` - 弃用
- `Removed` - 移除
- `Fixed` - 修复
- `Security` - 安全相关

## 参考链接

- [GitHub Releases](https://github.com/jenkinsci/wxwork-notification-plugin/releases)
- [Jenkins Plugin Page](https://plugins.jenkins.io/wxwork-notification/)
- [语义化版本](https://semver.org/lang/zh-CN/)
- [Keep a Changelog](https://keepachangelog.com/zh-CN/1.0.0/)
