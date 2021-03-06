package io.github.noahzu.androidnewteclearn;

import io.github.noahzu.annotation.EventDispatchAnnotation;
import io.github.noahzu.annotation.EventReceiveAnnotation;

@EventDispatchAnnotation
public class EventDispatcher {
    @EventReceiveAnnotation(eventId = 1)
    protected void onReceiveMessage1(MessagePacket packet) {
        System.out.println("onReceiveMessage 1");
        System.out.println(packet.getEventId());
    }

    @EventReceiveAnnotation(eventId = 1)
    protected void onReceiveMessage11(MessagePacket packet) {
        System.out.println("onReceiveMessage 11");
        System.out.println(packet.getEventId());
    }

    @EventReceiveAnnotation(eventId = 2)
    protected void onReceiveMessage2(MessagePacket packet) {
        System.out.println("onReceiveMessage 2");
        System.out.println(packet.getEventId());
    }
    @EventReceiveAnnotation(eventId = 2)
    protected void onReceiveMessage22(MessagePacket packet) {
        System.out.println("onReceiveMessage 2");
        System.out.println(packet.getEventId());
    }

    @EventReceiveAnnotation(eventId = 3)
    protected void onReceiveMessage3(MessagePacket packet) {
        System.out.println("onReceiveMessage 3");
        System.out.println(packet.getEventId());
    }

    @EventReceiveAnnotation(eventId = 4)
    protected void onReceiveMessage4(MessagePacket packet) {
        System.out.println("onReceiveMessage 4");
        System.out.println(packet.getEventId());
    }

    @EventReceiveAnnotation(eventId = 5)
    protected void onReceiveMessage5(MessagePacket packet) {
        System.out.println("onReceiveMessage 5");
        System.out.println(packet.getEventId());
    }

    @EventReceiveAnnotation(eventId = 5)
    protected void onReceiveMessage55(MessagePacket packet) {
        System.out.println("onReceiveMessage 5");
        System.out.println(packet.getEventId());
    }

    @EventReceiveAnnotation(eventId = 6)
    protected void onReceiveMessage6(MessagePacket packet) {
        System.out.println("onReceiveMessage 6");
        System.out.println(packet.getEventId());
    }

    @EventReceiveAnnotation(eventId = -1)
    protected void onProcessDefaultMessage(MessagePacket packet) {
        System.out.println("????????????"+packet.getEventId()+"????????????????????????????????????");
    }
    @EventReceiveAnnotation(eventId = -1)
    protected void onProcessDefault2Message(MessagePacket packet) {
        System.out.println("????????????"+packet.getEventId()+"????????????????????????????????????");
    }
}
