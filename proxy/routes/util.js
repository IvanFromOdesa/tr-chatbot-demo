const path = require('path');

function sendJson(res, jsonPath, sleepDur) {
    function _send() {
        res.header("Content-Type", 'application/json');
        res.sendFile(path.join(__dirname, jsonPath));
    }

    if (sleepDur) {
        this.setTimeout(function () {
            _send();
        }, sleepDur);
    } else {
        _send();
    }
}

function makeId(length) {
    let result = '';
    const characters = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
    const charactersLength = characters.length;
    let counter = 0;
    while (counter < length) {
        result += characters.charAt(Math.floor(Math.random() * charactersLength));
        counter += 1;
    }
    return result;
}

module.exports = {sendJson, makeId};
