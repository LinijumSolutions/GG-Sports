package com.linijumsolutions.gg_sports.models;

/**
 * Created by lukas on 17/05/15.
 */
public class FriendsMapping {
    private int mappingId;
    private int firstUserId;
    private int secondUserId;

    public int getMappingId() {
        return mappingId;
    }

    public void setMappingId(int mappingId) {
        this.mappingId = mappingId;
    }

    public int getFirstUserId() {
        return firstUserId;
    }

    public void setFirstUserId(int firstUserId) {
        this.firstUserId = firstUserId;
    }

    public int getSecondUserId() {
        return secondUserId;
    }

    public void setSecondUserId(int secondUserId) {
        this.secondUserId = secondUserId;
    }
}
