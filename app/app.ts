import { WebClient, WebAPICallResult } from '@slack/web-api';

const token = process.env.SLACK_TOKEN;

const web = new WebClient(token);

interface ChatPostMessageResult extends WebAPICallResult {
    channel: string,
    ts: string,
    message: {
        text: string
    }
}

console.log("Test");