export enum EventType {
    MEETING = 'meeting',
    EVENT = 'event',
    INTERNSHIP = 'internship'
}

export class CustomMessage {
    eventType: EventType;
    messageId: string;
    messageBody: string;
    postDate: string;
    constructor(eventType: EventType, messageId: string, messageBody: string, postDate: string) {
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

export class AnnouncementManager {
    announcements: Map<string, CustomMessage>;
    userIds: Map<string, Set<string>>;

    constructor() {
        this.announcements = new Map();
        this.userIds = new Map();
    }
}