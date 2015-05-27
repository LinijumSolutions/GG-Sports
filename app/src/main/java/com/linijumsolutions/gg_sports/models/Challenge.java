package com.linijumsolutions.gg_sports.models;

import java.util.Date;

public class Challenge {
    private int id;
    private Date startTime;
    private Date endTime;
    private ChallengeType challengeType;
    private SportType sportType;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        id = id;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public ChallengeType getChallengeType() {
        return challengeType;
    }

    public void setChallengeType(ChallengeType challengeType) {
        this.challengeType = challengeType;
    }

    public SportType getSportType() {
        return sportType;
    }
}
