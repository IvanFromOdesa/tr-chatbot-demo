const express = require('express');
const path = require('path');
const {sendJson} = require('./util');
const multer = require("multer");

const router = express.Router();
const upload = multer({ storage: multer.memoryStorage() });

router.get("/init", (req, res) => {
    sendJson(res, '../json/kb/kb.json', 500);
});

router.get("/qa", (req, res) => {
    sendJson(res, '../json/kb/qa.json', 500);
});

router.get("/pdf", (req, res) => {
    sendJson(res, '../json/kb/pdf.json', 500);
});

router.get("/preview/pdf/:name", (req, res) => {
    const filePath = path.join(__dirname, '../public/preview.png');
    res.sendFile(filePath, (err) => {
        if (err) {
            res.status(404).send("File not found");
        }
    });
});

router.get("/download/pdf/:name", (req, res) => {
    const filePath = path.join(__dirname, '../public/dummy.pdf');
    res.download(filePath, "dummy.pdf", (err) => {
        if (err) {
            res.status(500).send("Error downloading the file.");
        }
    });
});

router.post("/upload/json", (req, res) => {
    console.log(`Req body: ${JSON.stringify(req.body, null, 2)}`);
    return res.status(204).send();
});

router.post("/upload/pdf", upload.array("files"), (req, res) => {
    try {
        const files = req.files;
        const translate = req.body.translate === "true";

        if (!files || files.length === 0) {
            return res.status(400).json({ error: "No files uploaded" });
        }

        console.log(`Received ${files.length} PDF(s). Translate: ${translate}`);

        return res.status(204).send();
    } catch (error) {
        console.error("Error processing PDFs:", error);
        res.status(500).json({ error: "Internal server error" });
    }
});

module.exports = router;