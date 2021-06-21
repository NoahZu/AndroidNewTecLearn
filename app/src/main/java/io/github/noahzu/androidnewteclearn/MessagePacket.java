package io.github.noahzu.androidnewteclearn;

public class MessagePacket {
    int mEventId;
    String mEventContent;

    public MessagePacket(int eventId,String eventContent) {
        this.mEventId = eventId;
        this.mEventContent = eventContent;
    }

    public int getEventId() {
        return mEventId;
    }

    String getMessageContent() {
        return this.mEventContent;
    }
}
