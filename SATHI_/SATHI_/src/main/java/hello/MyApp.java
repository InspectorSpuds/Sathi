package hello;
import com.slack.api.bolt.App;
import com.slack.api.bolt.jetty.SlackAppServer;
import com.slack.api.model.block.element.BlockElement;
import com.slack.api.model.block.element.BlockElements;
import com.slack.api.webhook.WebhookResponse;
import org.apache.log4j.BasicConfigurator;
import com.slack.api.Slack;
import org.jetbrains.annotations.NotNull;

import java.util.*;

import static com.slack.api.model.block.Blocks.*;
import static com.slack.api.model.block.composition.BlockCompositions.markdownText;
import static com.slack.api.model.block.composition.BlockCompositions.plainText;
import static com.slack.api.model.block.element.BlockElements.asElements;
import static com.slack.api.model.block.element.BlockElements.button;


//make sure this program runs in java 1.0.8 ie. Java version 8
public class MyApp {
    public static void main(String[] args) throws Exception {
        // App expects env variables (SLACK_BOT_TOKEN, SLACK_SIGNING_SECRET)
        App app = new App();
        BasicConfigurator.configure(); //log4j is what catches server messages, apparently the vanilla tutorial program doesn't have it set up
        app.command("/ishan", (req, ctx) -> { //change the command to /GIG or etc., you will need to add your url and command on the webpage
            return ctx.ack(":wave: PENS PENCILS HOCKEYSTICK HANDLE BROOMSTICK HANDLE PEBBLES SMALL GLASS JAR");
        });

        /*app.command("/announcements", (req, ctx) -> {
            // Post a message via response_url
            WebhookResponse result = ctx.respond(res -> res
                    .responseType("ephemeral") // or "in_channnel"
                    .text("Hi there!") // blocks, attachments are also available
            );
            return ctx.ack(); // ack() here doesn't post a message
        });*/
        /*app.blockAction("button-action", (req, ctx) -> {
            String value = req.getPayload().getActions().get(0).getValue(); // "button's value"
            if (req.getPayload().getResponseUrl() != null) {
                // Post a message to the same channel if it's a block in a message
                ctx.respond("You've sent " + value + " by clicking the button!");
            }
            return ctx.ack();
            asElements(
            //button(b -> b.actionId("button_action").text(plainText(pt -> pt.text("Ping"))).value("ping"))
                                    //button() creates a button
                                    //button().text(plainText())

                            )
        });*/
        app.command("/announcements", (req, ctx) -> {
            List list = new ArrayList<BlockElement>();
            list.add(button(b -> b.actionId("button_action").text(plainText(pt -> pt.text("Ping"))).value("ping")));


            //return ctx.ack(res -> res.responseType("in_channel").text(":wave: pong"));
            return ctx.ack(asBlocks(
                    section(section -> section.text(markdownText(":wave: pong"))),
                    actions(actions -> actions
                            .elements(list)
                    )

            ));
        });

        app.blockAction("button_action", (req, ctx) -> { //catches the button actionId and returns a response
            String value = req.getPayload().getActions().get(0).getValue(); // "button's value"
            if (req.getPayload().getResponseUrl() != null) {
                // Post a message to the same channel if it's a block in a message
                ctx.respond("You've sent " + value + " by clicking the button!");
            }
            return ctx.ack();
        });

        SlackAppServer server = new SlackAppServer(app);
        server.start(); // http://localhost:3000/slack/events

    }

}
