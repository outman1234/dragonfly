package org.outman.dragonfly.common.enums;

public enum ParticipantType {

    /**
     *
     */
    STARTER(0,"发起者"),

    /**
     *
     */
    PARTNER(1,"参与者");

    private final Integer code;

    private final String description;


    ParticipantType(Integer code, String description) {
        this.code = code;
        this.description = description;
    }
}
