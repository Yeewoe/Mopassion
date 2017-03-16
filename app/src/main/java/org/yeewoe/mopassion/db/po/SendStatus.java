package org.yeewoe.mopassion.db.po;

public enum SendStatus {

    SUCCESS,
    SENDING,
    FAILURE;

    public static SendStatus parse(int value) {
        switch (value) {
            case 0:
                return SUCCESS;
            case 1:
                return SENDING;
            case 2:
                return FAILURE;
            default:
                return null;
        }
    }
}
