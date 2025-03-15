const express = require('express');
const {sendJson} = require("./util");

const router = express.Router();

router.get("/", (req, res) => {
    sendJson(res, '../json/home/anonymous.json', 500);
});

router.get("/employee", (req, res) => {
    sendJson(res, '../json/home/employee.json', 500);
});

router.get("/visitor", (req, res) => {
    sendJson(res, '../json/home/visitor.json', 500);
});

module.exports = router;