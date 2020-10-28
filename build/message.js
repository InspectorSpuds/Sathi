"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.AnnouncementManager = exports.CustomMessage = exports.EventType = void 0;
var EventType;
(function (EventType) {
    EventType["MEETING"] = "meeting";
    EventType["EVENT"] = "event";
    EventType["INTERNSHIP"] = "internship";
})(EventType = exports.EventType || (exports.EventType = {}));
var CustomMessage = /** @class */ (function () {
    function CustomMessage(eventType, messageId, messageBody, postDate) {
        this.eventType = eventType;
        this.messageId = messageId;
        this.messageBody = messageBody;
        this.postDate = postDate;
    }
    CustomMessage.prototype.toString = function () {
        return "Upcoming " + this.eventType + "\n"
            + "On " + this.postDate + "\n"
            + "What's happening: " + "\n"
            + this.messageBody;
    };
    return CustomMessage;
}());
exports.CustomMessage = CustomMessage;
var AnnouncementManager = /** @class */ (function () {
    function AnnouncementManager() {
        this.announcements = new Map();
        this.userIds = new Map();
    }
    return AnnouncementManager;
}());
exports.AnnouncementManager = AnnouncementManager;
