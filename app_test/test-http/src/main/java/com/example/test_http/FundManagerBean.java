package com.example.test_http;

public class FundManagerBean {
    private String name;
    private String avatar;
    private String education;
    private String betaCode;
    private String position;
    private String companyname;
    private String score;

    @Override
    public String toString() {
        return "ManagerBean{" +
                "name='" + name + '\'' +
                ", avatar='" + avatar + '\'' +
                ", education='" + education + '\'' +
                ", betaCode='" + betaCode + '\'' +
                ", position='" + position + '\'' +
                ", companyname='" + companyname + '\'' +
                ", score='" + score + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getBetaCode() {
        return betaCode;
    }

    public void setBetaCode(String betaCode) {
        this.betaCode = betaCode;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getCompanyname() {
        return companyname;
    }

    public void setCompanyname(String companyname) {
        this.companyname = companyname;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
}
