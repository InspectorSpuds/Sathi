import { App } from '@slack/bolt';


const app:App = new App({
    signingSecret: process.env.SLACK_SIGNING_SECRET,
    token: process.env.SLACK_BOT_TOKEN,
});


app.command('/sathi-schedule', async ({ command, ack, say }) => {
    // Acknowledge command request
    await ack();
  
    await say("Beans");
});

(async () => {
    // Start the app
    await app.start(process.env.PORT || 3000);

    Console.log("App running!...");
  })();