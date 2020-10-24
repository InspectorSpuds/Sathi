"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
var web_api_1 = require("@slack/web-api");
var token = process.env.SLACK_TOKEN;
var web = new web_api_1.WebClient(token);
console.log("Test");
