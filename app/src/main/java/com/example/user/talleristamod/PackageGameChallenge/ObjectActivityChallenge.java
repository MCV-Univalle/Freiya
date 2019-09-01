package com.example.user.talleristamod.PackageGameChallenge;

public class ObjectActivityChallenge {


    public String challengeName, challengeDescription, challengeId;
    private String joinCode;
    private String stateA, creator;
    private String copyA;

    public ObjectActivityChallenge() {}

    public ObjectActivityChallenge(String challengeName, String challengeDescription, String challengeId, String joinCode,String stateA) {
        this.challengeName = challengeName;
        this.challengeDescription = challengeDescription;
        this.challengeId = challengeId;
        this.joinCode = joinCode;
        this.stateA = stateA;
    }

    public ObjectActivityChallenge(String challengeName, String challengeDescription, String joinCode,String stateA, String creator, String copyA) {
        this.challengeName = challengeName;
        this.challengeDescription = challengeDescription;
        this.joinCode = joinCode;
        this.stateA = stateA;
        this.copyA = copyA;
        this.creator = creator;
    }

    public String getChallengeName() {
        return challengeName;
    }

    public void setChallengeName(String challengeName) {
        this.challengeName = challengeName;
    }

    public String getChallengeDescription() {
        return challengeDescription;
    }

    public void setChallengeDescription(String challengeDescription) {
        this.challengeDescription = challengeDescription;
    }

    public String getChallengeId() {
        return challengeId;
    }

    public void setChallengeId(String challengeId) {
        this.challengeId = challengeId;
    }

    public String getJoinCode() {
        return joinCode;
    }

    public void setJoinCode(String joinCode) {
        this.joinCode = joinCode;
    }

    public String getStateA() {
        return stateA;
    }

    public void setStateA(String stateA) {
        this.stateA = stateA;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getCopyA() {
        return copyA;
    }

    public void setCopyA(String copyA) {
        this.copyA = copyA;
    }
}
