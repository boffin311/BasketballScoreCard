package com.example.ankit.basketballscorecard.database.teamB;

public class TeamBList {
    String Name;

    public void setPlayer(int player) {
        Player = player;
    }

    public int getPlayer() {

        return Player;
    }

    int Player;
    int SumB;
    int IdB;
    int FoulB;

    public void setSumB(int sumB) {
        SumB = sumB;
    }

    public void setIdB(int idB) {
        IdB = idB;
    }

    public void setFoulB(int foulB) {
        FoulB = foulB;
    }

    public int getSumB() {
        return SumB;
    }

    public int getIdB() {
        return IdB;
    }

    public int getFoulB() {
        return FoulB;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getName() {

        return Name;
    }
}
