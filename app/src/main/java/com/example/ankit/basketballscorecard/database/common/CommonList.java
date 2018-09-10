package com.example.ankit.basketballscorecard.database.common;

public class CommonList {
    String Name;
    int score;
    int PAR_ID;
    int hours;

    public void setHours(int hours) {
        this.hours = hours;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getHours() {

        return hours;
    }

    public int getMin() {
        return min;
    }

    int min;

    public void setPAR_ID(int PAR_ID) {
        this.PAR_ID = PAR_ID;
    }

    public int getPAR_ID() {

        return PAR_ID;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getName() {

        return Name;
    }

    public int getScore() {
        return score;
    }
}
