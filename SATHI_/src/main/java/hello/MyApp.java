package hello;
import com.slack.api.bolt.App;
import com.slack.api.bolt.jetty.SlackAppServer;
import com.slack.api.methods.response.chat.ChatPostMessageResponse;
import org.apache.log4j.BasicConfigurator;
import com.slack.api.Slack;
import com.slack.api.methods.response.chat.ChatPostMessageResponse;


//make sure this program runs in java 1.0.8 ie. Java version 8
public class MyApp {
    public static void main(String[] args) throws Exception {
        // App expects env variables (SLACK_BOT_TOKEN, SLACK_SIGNING_SECRET)
        Slack slack = Slack.getInstance();
        String token = System.getenv("SLACK_TOKEN");
        App app = new App();
        BasicConfigurator.configure(); //log4j is what catches server messages, apparently the vanilla tutorial program doesn't have it set up
        app.command("/ishan", (req, ctx) -> {//change the command to whatever works for you
            return ctx.ack("PENS PENCILS HOCKEYSTICK HANDLE BROOMSTICK HANDLE PEBBLES SMALL GLASS JAR");
        });

        SlackAppServer server = new SlackAppServer(app);
        server.start(); // http://localhost:3000/slack/events

    }
}
