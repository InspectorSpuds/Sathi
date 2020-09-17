package hello;
import com.slack.api.bolt.App;
import com.slack.api.bolt.jetty.SlackAppServer;
import org.apache.log4j.BasicConfigurator;
//make sure this program runs in java 1.0.8 ie. Java version 8
public class MyApp {
    public static void main(String[] args) throws Exception {
        // App expects env variables (SLACK_BOT_TOKEN, SLACK_SIGNING_SECRET)
        App app = new App();
        BasicConfigurator.configure(); //log4j is what catches server messages, apparently the vanilla tutorial program doesn't have it set up
        app.command("/hello", (req, ctx) -> {
            System.out.println(req);
            return ctx.ack(":wave: Hello!");
        });

        SlackAppServer server = new SlackAppServer(app);
        server.start(); // http://localhost:3000/slack/events
    }
}
