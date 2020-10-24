"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.CustomMessage = void 0;
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
