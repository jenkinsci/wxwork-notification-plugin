/**
 * <p>企业微信群机器人配置页面脚本</p>
 * <p>用于处理机器人配置界面的测试功能</p>
 *
 * @author wxwork-notification-plugin
 */

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

    const resultBox = container?.querySelector('.wxwork-robot-test-result');
    const loadingBox = container?.querySelector('.wxwork-robot-test-loading');

    if (!resultBox || !loadingBox) {
        console.error('找不到结果显示元素');
        return;
    }

    const requestUrl = `${url}/${method}${buildTestQuery(container)}`;

    // 禁用按钮，防止重复点击
    button.disabled = true;

    try {
        loadingBox.style.display = "block";
        resultBox.innerHTML = '';

        const response = await fetch(requestUrl, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
            }
        });

        const responseText = await response.text();

        if (response.ok) {
            resultBox.innerHTML = responseText;
        } else {
            resultBox.innerHTML = `<span style="color:#d32f2f;">测试失败: ${response.status} ${response.statusText || '请求异常'}</span>`;
        }
    } catch (error) {
        console.error('发送测试请求失败:', error);
        resultBox.innerHTML = `<span style="color:#d32f2f;">网络错误: ${error.message || '请求失败'}</span>`;
    } finally {
        loadingBox.style.display = "none";
        button.disabled = false;
    }
};

// 使用事件委托监听测试按钮点击
document.addEventListener('click', (event) => {
    const button = event.target.closest('.wxwork-robot-test-button');
    if (button && !button.disabled) {
        event.preventDefault();
        sendTestMessage(button);
    }
});
