/**
 * <p>企业微信群机器人配置页面脚本</p>
 * <p>用于处理机器人配置界面的测试功能</p>
 *
 * @author wxwork-notification-plugin
 */

// 显示加载动画
const showLoading = (el) => {
    if (el) {
        el.style.display = "block";
    }
};

// 隐藏加载动画
const hideLoading = (el) => {
    if (el) {
        el.style.display = "none";
    }
};

/**
 * 构建测试查询参数
 * @param {HTMLElement} container - 配置容器元素
 * @returns {string} 查询字符串
 */
const buildTestQuery = (container) => {
    const idInput = container?.querySelector('input[name="id"]');
    const nameInput = container?.querySelector('input[name="name"]');
    const webhookInput = container?.querySelector('input[name="webhook"]');

    const params = new URLSearchParams();
    if (idInput?.value) {
        params.append('id', idInput.value);
    }
    if (nameInput?.value) {
        params.append('name', nameInput.value);
    }
    if (webhookInput?.value) {
        params.append('webhook', webhookInput.value);
    }

    return `?${params.toString()}`;
};

/**
 * 发送企业微信群机器人测试消息
 * @param {HTMLElement} button - 测试按钮元素
 */
const sendTestMessage = async (button) => {
    const container = button?.closest('.wxwork-robot-config-container');
    const url = button?.getAttribute("data-request-url");
    const method = button?.getAttribute("data-request-method");

    if (!container || !url || !method) {
        console.error('测试参数不完整');
        return;
    }

    const loadingBox = button?.nextSibling;
    const infoBox = loadingBox?.nextSibling;

    if (!loadingBox || !infoBox) {
        console.error('找不到结果显示元素');
        return;
    }

    const requestUrl = `${url}/${method}${buildTestQuery(container)}`;

    try {
        showLoading(loadingBox);

        const response = await fetch(requestUrl, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
            }
        });

        const responseText = await response.text();

        if (response.ok) {
            infoBox.innerHTML = responseText;
        } else {
            infoBox.innerHTML = `测试失败: ${response.status} ${response.statusText || '测试异常!'}`;
        }
    } catch (error) {
        console.error('发送测试请求失败:', error);
        infoBox.innerHTML = `网络错误: ${error.message || '请求失败!'}`;
    } finally {
        hideLoading(loadingBox);
    }
};

// 使用事件委托监听测试按钮点击
document.addEventListener('click', (event) => {
    const button = event.target.closest('.wxwork-robot-test-button');
    if (button) {
        event.preventDefault();
        sendTestMessage(button);
    }
});