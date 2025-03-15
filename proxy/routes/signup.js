const express = require('express');
const {sendJson} = require('./util');
const router = express.Router();

router.post("/", (req, res) => {
    // TODO: validate the req.body
    res.redirect("/public/bundles/entry/login.html");
})

router.get("/init", (req, res) => {
    sendJson(res, '../json/signup/signup.json', 500);
});

module.exports = router;