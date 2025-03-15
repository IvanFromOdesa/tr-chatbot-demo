const express = require('express');
const {makeId} = require("./util");
const router = express.Router();

let conversation = {
    "startedAt": 1741495759.012089000,
    "chatInteractions": [],
    "status": 'not_yet_completed'
};

router.get('/', (req, res) => {
    const prompt = req.query.prompt;

    if (!prompt) {
        return res.status(400).json({ error: "Prompt is required" });
    }

    if (!conversation.startedAt) {
        conversation.startedAt = Date.now();
    }

    const startTime = Date.now();

    const commonData = {
        question: prompt,
        questionLanguage: {
            alias: "en-US",
            name: "English",
            helpText: "Please select the language",
            imagePath: "/lang-icons/en.webp"
        },
        askedAt: Date.now(),
        id: 'chat-interaction-' + makeId(10)
    };

    const setResponse = (chunk) => {
        const timeToProcess = (Date.now() - startTime) / 1000;
        return {...commonData, answer: chunk, timeToProcess};
    };

    const r1 = setResponse('Lorem Ipsum is simply dummy text of the printing and typesetting industry.' +
        ' Lorem Ipsum has been the industry\'s standard dummy text ever since the 1500s,');

    const r2 = setResponse(' when an unknown printer took a galley of type and scrambled it to make a type specimen book.' +
        ' It has survived not only five centuries, but also the leap into electronic typesetting,');

    const r3 = setResponse(' remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets' +
        ' containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker' +
        ' including versions of Lorem Ipsum.');

    res.setHeader('Content-Type', 'text/event-stream');
    res.setHeader('Cache-Control', 'no-cache');
    res.setHeader('Connection', 'keep-alive');
    res.setHeader('Transfer-Encoding', 'chunked');

    let index = 0;
    const interval = setInterval(() => {
        const responses = [r1, r2, r3];
        if (index < responses.length) {
            res.write(`data:${JSON.stringify(responses[index])}\n\n`);
            index ++;
        } else {
            clearInterval(interval);
            conversation.chatInteractions.push(
                {
                    ...commonData,
                    answer: r1.answer + r2.answer + r3.answer,
                    timeToProcess: r1.timeToProcess + r2.timeToProcess + r3.timeToProcess
                }
            );
            res.end();
        }
    }, 1000);
});

router.get('/conversation/current', (req, res) => {
    res.json(conversation);
});

router.post('/feedback', (req, res) => {
    if (req.query.res === '0') {
        conversation.status = 'completed_explicitly';
    } else {
        conversation.status = 'not_yet_completed';
    }
    res.json(conversation.status);
});

module.exports = router;