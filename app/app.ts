import { WebClient, WebAPICallResult } from '@slack/web-api';
import { CustomMessage } from './message'

const token = process.env.SLACK_TOKEN;

const web = new WebClient(token);

interface ChatPostMessageResult extends WebAPICallResult {
    channel: string,
    ts: string,
    message: {
        text: string
    }
}

const msg : CustomMessage = new CustomMessage("testEvent", "da92d90a2d90", "Test event body!", "02/10/2021");

console.log(msg.toString());