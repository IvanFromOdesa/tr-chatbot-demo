const express = require('express');
const {sendJson} = require('./util');

const router = express.Router();

router.get("/", (req, res) => {
    sendJson(res, '../json/sessionMsg.json')
});

module.exports = router;