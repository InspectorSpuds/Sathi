package hello;

import com.slack.api.Slack;
import com.slack.api.bolt.App;
import com.slack.api.bolt.jetty.SlackAppServer;
import com.slack.api.methods.SlackApiException;
import com.slack.api.model.block.element.BlockElement;
import org.apache.log4j.BasicConfigurator;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import static com.slack.api.model.block.Blocks.*;
import static com.slack.api.model.block.composition.BlockCompositions.markdownText;
import static com.slack.api.model.block.composition.BlockCompositions.plainText;
import static com.slack.api.model.block.element.BlockElements.button;


//make sure this program runs in java 1.0.8 ie. Java version 8
public class MyApp {
    public static void main(String[] args) throws Exception {
        // App expects env variables (SLACK_BOT_TOKEN, SLACK_SIGNING_SECRET)
        BasicConfigurator.configure(); //log4j is what catches server messages, apparently the vanilla tutorial program doesn't have it set up
        var app = new App(); // `new App()` does the same
        Slack slack = Slack.getInstance();
        appCommandAssigns(app, slack);
        SlackAppServer server = new SlackAppServer(app);
        server.start(); // http://localhost:3000/slack/
    }

    static void echoMessage(String id, String text) { //you need the channel ID to make the method function, text can be overloaded with other messages in the future
        // you can get this instance via ctx.client() in a Bolt app
        List<BlockElement> list = new ArrayList<BlockElement>();
        list.add(button(b -> b.actionId("button-action").text(plainText(pt -> pt.text("Get a fun result"))).value("ping")));
        var client = Slack.getInstance().methods();
        var logger = LoggerFactory.getLogger("my-awesome-slack-app");
        try {
            //Call the chat.postMessage method using the built-in WebClient
            var result = client.chatPostMessage(r -> r
                            // The token you used to initialize your app
                            .token(System.getenv("SLACK_BOT_TOKEN"))
                            .channel(id)
                            .text(text).blocks(asBlocks(
                                    section(section -> section.text(markdownText(":wave: pong"))),
                            actions(actions -> actions
                                    .elements(list)
                            )

                            ))
                    // You could also use a blocks[] array to send richer content
            );
            // Print result, which includes information about the message (like TS)
            logger.info("result {}", result);
        } catch (IOException | SlackApiException e) {
            logger.error("error: {}", e.getMessage(), e);
        }
    }

    static void appCommandAssigns(App app, Slack slack) {
        /*
         */

        app.command("/announcements", (req, ctx) -> {
            //return ctx.ack(res -> res.responseType("in_channel").text(":wave: pong"));
            List<BlockElement> list = new ArrayList<BlockElement>();
            list.add(button(b -> b.actionId("button-action").text(plainText(pt -> pt.text("Get a fun result"))).value("ping")));
            String token = System.getenv("SLACK_BOT_TOKEN");
            echoMessage(ctx.getChannelId(), "LOUIE WAS A GREYHOUND IN A SHEEP'S SKIN");
            return ctx.ack(asBlocks(
                    section(section -> section.text(markdownText(":wave: pong")))

            ));
        });

        app.blockAction("button_action", (req, ctx) -> {
            String value = req.getPayload().getActions().get(0).getValue(); // "button's value"
            System.out.println("RRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRR"+req.toString()+" "+ctx.toString());
            if (req.getPayload().getResponseUrl() != null) {
                // Post a message to the same channel if it's a block in a message
                ctx.respond("You've sent " + value + " by clicking the button!");
            }
            return ctx.ack();
        });

        //add "/slack/events" to the end of ever ngrok link you use so that the bot can tunnel properly



        // /assignments 'insert date here' 'msg'
        // message date picker must have data and time
        //must send reminder a day before and actually
        //
    }
}
