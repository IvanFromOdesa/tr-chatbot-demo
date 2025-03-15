const express = require('express');
const cors = require('cors');

const app = express();

const homeRoutes = require('./routes/home');
const loginRoutes = require('./routes/login');
const signupRoutes = require('./routes/signup');
const kbRoutes = require('./routes/kb');
const chatRoutes = require('./routes/chat');

const sessionMsgAlert = require('./routes/sessionMsgAlert');
const preferencesRoutes = require('./routes/preferences');

const {urlencodedParser, jsonParser} = require("./config/bodyParser");

app.use(urlencodedParser);
app.use(jsonParser);
app.use(cors());

app.use("/api/init", homeRoutes);
app.use("/api/login", loginRoutes);
app.use("/api/signup", signupRoutes);
app.use("/public", express.static('public'));
app.use("/api/preferences", preferencesRoutes);
app.use("/api/session-msg", sessionMsgAlert);
app.use("/api/knowledge-base", kbRoutes);
app.use("/api/chat", chatRoutes);

module.exports = app;