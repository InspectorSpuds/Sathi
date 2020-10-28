// leaving in here for easy reference back, and in case we refer back to using @slack/web-api

import { WebClient, WebAPICallResult } from '@slack/web-api';
import { CustomMessage, EventType } from './message'

const token = process.env.SLACK_TOKEN;

const web = new WebClient(token);

interface ChatPostMessageResult extends WebAPICallResult {
    channel: string,
    ts: string,
    message: {
        text: string
    }
}

const msg : CustomMessage = new CustomMessage(EventType.EVENT, "da92d90a2d90", "Test event body!", "02/10/2021");

console.log(msg.toString());