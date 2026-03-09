function setText(id, value) {
    document.getElementById(id).innerText = value ?? '-';
}


function updateCard(prefix, data) {
    setText(`${prefix}-deviceId`, data.deviceId);
    setText(`${prefix}-utc`, data.utcTime);
    setText(`${prefix}-lat`, data.latitude);
    setText(`${prefix}-lon`, data.longitude);
    setText(`${prefix}-fix`, data.fixQuality);
    setText(`${prefix}-sat`, data.satelliteCount);
    setText(`${prefix}-battery`, data.battery);
}


function loadLatest() {
    fetch('/api/devices/latest')
        .then(res => res.json())
        .then(res => {
            if (!res.success || !res.data) return;
            res.data.forEach(d => {
                if (d.deviceType === 'PERSONNEL') {
                    updateCard('personnel', d);
                } else if (d.deviceType === 'VEHICLE') {
                    updateCard('vehicle', d);
                }
            });
        });
}


function connectWebSocket() {
    const socket = new SockJS('/ws');
    const stompClient = Stomp.over(socket);
    stompClient.connect({}, function () {
        stompClient.subscribe('/topic/rtk/latest', function (message) {
            const data = JSON.parse(message.body);
            if (data.deviceType === 'PERSONNEL') {
                updateCard('personnel', data);
            } else if (data.deviceType === 'VEHICLE') {
                updateCard('vehicle', data);
            }
        });
    });
}


function convertDevice(type) {
    fetch(`/api/devices/${type}/convert`)
        .then(res => res.json())
        .then(res => {
            if (!res.success) {
                alert(res.message);
                return;
            }
            const hk = `N: ${res.data.hkN}, E: ${res.data.hkE}, PD: ${res.data.hkpD}`;
            setText(`${type}-hk`, hk);
        })
        .catch(err => alert('轉換失敗：' + err));
}


window.onload = function () {
    loadLatest();
    connectWebSocket();
};