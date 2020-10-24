"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
var web_api_1 = require("@slack/web-api");
var message_1 = require("./message");
var token = process.env.SLACK_TOKEN;
var web = new web_api_1.WebClient(token);
var msg = new message_1.CustomMessage("testEvent", "da92d90a2d90", "Test event body!", "02/10/2021");
console.log(msg.toString());
