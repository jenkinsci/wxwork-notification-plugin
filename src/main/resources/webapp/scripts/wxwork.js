function sendRobotTest(el) {
    let url = el.getAttribute("data-request-url")
    let method = el.getAttribute("data-request-method")
    let loadingBox = el.nextSibling
    let infoBox = loadingBox.nextSibling
    // send ajax request with requestUrl
    let requestUrl = url + "/" + method
    showLoading(loadingBox)

    new Ajax.Request(requestUrl, {
        parameters: findProperty(el), onComplete: function (response) {
            closeLoading(loadingBox)
            applyErrorMessage(infoBox, response);
            try {
                layoutUpdateCallback.call();
                let headerScript = response.getResponseHeader("script");
                geval(headerScript);
            } catch (e) {
                applyErrorMessage(infoBox, "failed to evaluate " + s + "\n" + e.message);
            }
        }
    })
}

function showLoading(el) {
    el.style.display = "block"
}

function closeLoading(el) {
    el.style.display = "none"
}

function findProperty(el) {
    let id = findPreviousFormItem(el, 'id').value;
    let name = findPreviousFormItem(el, 'name').value;
    let webhook = findPreviousFormItem(el, 'webhook').value;

    return {
        id: id, name: name, webhook: webhook
    }
}
