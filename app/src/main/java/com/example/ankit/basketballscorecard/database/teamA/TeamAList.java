package com.example.ankit.basketballscorecard.database.teamA;

public class TeamAList {
    String Name;
    int Sum,Player;

    public void setPlayer(int player) {
        Player = player;
    }

    public int getPlayer() {

        return Player;
    }

    int id;
int Foul;

    public void setFoul(int foul) {
        Foul = foul;
    }

    public Integer getFoul() {

        return Foul;
    }

    public void setSum(int sum) {
        Sum = sum;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {

        return id;
    }


    public Integer getSum() {

        return Sum;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getName() {

        return Name;
    }
}
