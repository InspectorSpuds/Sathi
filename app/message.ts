export class CustomMessage {
    eventType: string;
    messageId: string;
    messageBody: string;
    postDate: string;
    constructor(eventType: string, messageId: string, messageBody: string, postDate: string) {
        this.eventType = eventType;
        this.messageId = messageId;
        this.messageBody = messageBody;
        this.postDate = postDate;
    }
    toString() {
        return "Upcoming " + this.eventType + "\n" 
            + "On " + this.postDate + "\n" 
            + "What's happening: " + "\n"
            + this.messageBody;
    }
}