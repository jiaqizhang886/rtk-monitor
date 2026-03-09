function setText(id, value) {
    const el = document.getElementById(id);
    if (el) {
        el.innerText = value ?? '-';
    }
}

function updateCard(prefix, data) {
    setText(`${prefix}-rawMessage`, data.rawMessage);
    setText(`${prefix}-deviceId`, data.deviceId);
    setText(`${prefix}-deviceType`, data.deviceType);
    setText(`${prefix}-utcTime`, data.utcTime);

    setText(`${prefix}-latDm`, data.latDm);
    setText(`${prefix}-latDir`, data.latDir);
    setText(`${prefix}-lonDm`, data.lonDm);
    setText(`${prefix}-lonDir`, data.lonDir);

    setText(`${prefix}-latitude`, data.latitude);
    setText(`${prefix}-longitude`, data.longitude);

    setText(`${prefix}-fixQuality`, data.fixQuality);
    setText(`${prefix}-satelliteCount`, data.satelliteCount);
    setText(`${prefix}-hdop`, data.hdop);
    setText(`${prefix}-altitude`, data.altitude);
    setText(`${prefix}-altitudeUnit`, data.altitudeUnit);
    setText(`${prefix}-geoidHeight`, data.geoidHeight);
    setText(`${prefix}-geoidUnit`, data.geoidUnit);
    setText(`${prefix}-dgpsAge`, data.dgpsAge);
    setText(`${prefix}-checksum`, data.checksum);
    setText(`${prefix}-battery`, data.battery);
    setText(`${prefix}-signal`, data.signal);
    setText(`${prefix}-reserve1`, data.reserve1);
    setText(`${prefix}-reserve2`, data.reserve2);
    setText(`${prefix}-reserve3`, data.reserve3);
    setText(`${prefix}-receivedAt`, data.receivedAt);
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