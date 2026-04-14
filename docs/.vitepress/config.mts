import { defineConfig } from "vitepress";

export default defineConfig({
    lang: "zh-CN",
    title: "企业微信通知插件",
    description: "Jenkins 企业微信群机器人通知插件文档",
    base: "/wxwork-notification-plugin/",
    cleanUrls: true,
    themeConfig: {
        nav: [
            { text: "Release", link: "https://github.com/jenkinsci/wxwork-notification-plugin/releases" }
        ],
        sidebar: [
            {
                text: "快速开始",
                items: [
                    { text: "首页", link: "/" },
                    { text: "安装", link: "/guide/install" },
                    { text: "前置配置", link: "/guide/configuration" }
                ]
            },
            {
                text: "使用说明",
                items: [
                    { text: "FreeStyle 项目", link: "/guide/freestyle" },
                    { text: "Pipeline 项目", link: "/guide/pipeline" },
                    { text: "常见问题", link: "/guide/faq" }
                ]
            },
            {
                text: "附录",
                items: [{ text: "企业微信群机器人文档", link: "https://developer.work.weixin.qq.com/document/path/99110" }]
            }
        ],
        socialLinks: [{ icon: "github", link: "https://github.com/jenkinsci/wxwork-notification-plugin" }],
        search: {
            provider: "local"
        },
        footer: {
            message: "Released under the MIT License.",
            copyright: "Copyright © wxwork-notification-plugin contributors"
        }
    }
});
