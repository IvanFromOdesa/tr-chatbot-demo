const express = require('express');
const router = express.Router();

router.post("/lang", (req, res) => {
    setTimeout(() => {
        res.status(200).send();
    }, 500);
});

module.exports = router;