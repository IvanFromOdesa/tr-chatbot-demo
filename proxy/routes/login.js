const express = require('express');
const {sendJson} = require('./util');

const router = express.Router();
router.use(express.urlencoded({ extended: true }));

router.post("/", (req, res) => {
    const { username, password } = req.body;

    if (username === "ik@mail.com" && password === "12345678") {
        res.redirect("/public/bundles/entry/homeAnonymous.html");
    } else {
        res.redirect("/public/bundles/entry/login.html?error");
    }
});

router.get("/init", (req, res) => {
    sendJson(res, '../json/login/login.json', 500);
});

module.exports = router;