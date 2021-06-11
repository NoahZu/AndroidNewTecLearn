package io.github.noahzu.anni;

import javax.lang.model.element.ExecutableElement;

public class EventAnnotatedClass {

    private ExecutableElement mAnnotatedMethodElement;
    private int mEventId;



    public EventAnnotatedClass(ExecutableElement element) {
        this.mAnnotatedMethodElement = element;


        EventReceive annotation = element.getAnnotation(EventReceive.class);
        mEventId = annotation.eventId();

        if (mEventId < 0) {
            throw new IllegalArgumentException("eventId() 不合法");
        }

    }
}
