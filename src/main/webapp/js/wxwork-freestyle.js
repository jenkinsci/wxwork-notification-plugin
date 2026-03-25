/**
 * WxWork FreeStyle 配置页面联动脚本
 *
 * 功能：
 * 1. type 下拉切换 → imageUrl 行显示/隐藏（仅 image 类型时显示）
 * 2. 通知时机 checkbox → 对应内容 textarea 行显示/隐藏
 */
(function () {
    /**
     * 在整个 document 范围内查找与某元素对应的内容行
     * Jenkins 的 <f:section> 会将各字段渲染在独立的 section 内，
     * 因此需要从 document 级别检索目标行。
     */
    function findRows(cssClass) {
        return document.querySelectorAll('.' + cssClass);
    }

    /**
     * 初始化 type 下拉 → imageUrl 行联动
     */
    function initTypeSelect() {
        document.querySelectorAll('[data-wxwork-type]').forEach(function (select) {
            function toggle() {
                var show = (select.value === 'image');
                findRows('wxwork-image-url-row').forEach(function (row) {
                    row.style.display = show ? '' : 'none';
                });
            }
            select.addEventListener('change', toggle);
            toggle();
        });
    }

    /**
     * 初始化通知时机 checkbox → 内容行联动
     */
    function initNotifyCheckboxes() {
        var mapping = [
            { attr: 'data-wxwork-notify-success',  rowClass: 'wxwork-success-content-row'  },
            { attr: 'data-wxwork-notify-failure',  rowClass: 'wxwork-failure-content-row'  },
            { attr: 'data-wxwork-notify-unstable', rowClass: 'wxwork-unstable-content-row' },
            { attr: 'data-wxwork-notify-recovery', rowClass: 'wxwork-recovery-content-row' },
        ];

        mapping.forEach(function (item) {
            document.querySelectorAll('[' + item.attr + ']').forEach(function (checkbox) {
                function toggle() {
                    findRows(item.rowClass).forEach(function (row) {
                        row.style.display = checkbox.checked ? '' : 'none';
                    });
                }
                checkbox.addEventListener('change', toggle);
                toggle();
            });
        });
    }

    function init() {
        initTypeSelect();
        initNotifyCheckboxes();
    }

    if (document.readyState === 'loading') {
        document.addEventListener('DOMContentLoaded', init);
    } else {
        init();
    }
})();
