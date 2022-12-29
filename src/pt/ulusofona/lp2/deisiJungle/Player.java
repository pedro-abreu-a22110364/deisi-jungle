package pt.ulusofona.lp2.deisiJungle;

import java.util.ArrayList;

public class Player {
    int identifier;
    String name;
    Specie specie;
    int energy; //Mudar tipo de variavel consoante o que é pedido
    int rank; //Mudar de classe caso necessário
    int position = 1;
    int distance = 0;

    ArrayList<Food> eatenFoods = new ArrayList<>();

    public Player(int identifier, String name, Specie specie, int energy) {
        this.identifier = identifier;
        this.name = name;
        this.specie = specie;
        this.energy = energy;
    }

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

    public void removeEnergy(int energy) {
        this.energy -= energy;
    }

    public int getDistance() {
        return distance;
    }

    public void increseDistance(int distance){
        this.distance += distance;
    }

    public ArrayList<Food> getEatenFoods() {
        return eatenFoods;
    }
}