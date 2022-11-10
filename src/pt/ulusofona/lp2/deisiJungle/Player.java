package pt.ulusofona.lp2.deisiJungle;

import java.util.ArrayList;
//import java.util.List;

public class Player {
    int identifier;
    String name;
    Specie specie;


    int energy; //Mudar tipo de variavel consoante o que é pedido
    int rank; //Mudar de classe caso necessário
    int position = 1;
    boolean winner = false;

    public int getPosition() {
        return position;
    }
    public void setPosition(int position) {
        this.position = position;
    }
    public int getIdentifier(){
        return identifier;
    }
    public String getName(){
        return name;
    }
    public Specie getSpecie() {
        return specie;
    }
    public int getRank(){
        return rank;
    }
    public void setRank(int rank) {
        this.rank = rank;
    }
    public int getEnergy(){
        return energy;
    }
    public boolean isWinner() {
        return winner;
    }

    public Player() {
    }

    public Player(int identifier, String name) {
        this.identifier = identifier;
        this.name = name;
    }

    public Player(int identifier, String name, Specie specie) {
        this.identifier = identifier;
        this.name = name;
        this.specie = specie;
    }

    public Player(int identifier, String name, Specie specie, int energy) {
        this.identifier = identifier;
        this.name = name;
        this.specie = specie;
        this.energy = energy;
    }

    public void removeEnergy(int energy) {
        this.energy -= energy;
    }


}