Events = function () {
    window.console = window.console || {};
    console.log || (console.log = opera.postError);
    if (!window.console) {
        window.console = {log: function () {
        } };
    }
    var subscribe, stack, one, remove, publish, __this;
    stack = {};
    __this = this;
    subscribe = function (key, eventfn) {
        if (stack[key] == null) {
            stack[key] = [];
        }
        console.log("订阅事件：事件key：%s,函数：%o.", key, eventfn);
        return stack[key].push(eventfn)
    };

    one = function (key, eventfn) {
        console.log("移除所有关于key：%s的事件绑定", key);
        remove(key);
        return subscribe(key, eventfn);
    };

    remove = function (key) {
        console.log("移除相关事件订阅key:%s", key)
        if (stack[key] != null) {
            stack[key].length = 0;
        }
        return;
    };

    publish = function () {
        var key = Array.prototype.shift.call(arguments);
        console.log("发布相关事件key:%s，相关参数:%o", key, arguments);
        if (stack[key] == null) {
            stack[key] = [];
        }
        for (var _i = 0, _len = stack[key].length; _i < _len; _i++) {
            var fn = stack[key][_i];
            if (fn.apply(__this, arguments) === false) {
                return false;
            }
        }
    }
    return {
        subscribe: subscribe,
        one: one,
        remove: remove,
        publish: publish
    }
}