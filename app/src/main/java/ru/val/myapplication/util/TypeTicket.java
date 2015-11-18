package ru.val.myapplication.util;


public enum TypeTicket {
    FOUR(4),
    EIGHT(8),
    TWELVE(12),
    SIXTEEN(16);

    private int mMaxCount;

    TypeTicket(int maxCount){
        mMaxCount = maxCount;
    }

    public int getMaxCount(){
        return mMaxCount;
    }
}
