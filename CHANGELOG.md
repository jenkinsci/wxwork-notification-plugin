# Changelog

所有重要的变更都将记录在此文件中。

格式基于 [Keep a Changelog](https://keepachangelog.com/zh-CN/1.0.0/)，
并且本项目遵循 [语义化版本](https://semver.org/lang/zh-CN/)规范。

## [Unreleased]

### Added
- 完善 README.md 文档，添加更友好的使用说明
- 完善 AGENTS.md 文档，为 AI 智能体提供开发指导

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
