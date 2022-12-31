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
    int nrBananas = 0;

    ArrayList<Food> eatenFoods = new ArrayList<>();

    public Player(int identifier, String name, Specie specie, int energy) {
        this.identifier = identifier;
        this.name = name;
        this.specie = specie;
        this.energy = energy;
    }

    public Player(int identifier, String name, Specie specie, int energy, int rank, int position) {
        this.identifier = identifier;
        this.name = name;
        this.specie = specie;
        this.energy = energy;
        this.rank = rank;
        this.position = position;
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

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public void removeEnergy(int energy) {
        this.energy -= energy;
    }

    public void addEnergy(int energy) {
        this.energy += energy;
    }

    public void percentageEnergy(int percentage) {
        this.energy += (int) (this.energy * (percentage * 0.01));
    }

    public void percentageEnergyNegative(int percentage) {
        this.energy -= (int) (this.energy * (percentage * 0.01));
    }

    public void halfEnergy () {
        this.energy = this.energy/2;
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

    public int getNrBananas() {
        return nrBananas;
    }

    public void comerBananas() {
        nrBananas++;
    }

    public boolean countEatenBananas() {
        int count = 0;
        for (Food eatenFood : eatenFoods) {
            if (eatenFood.getIdentifier() == 'b') {
                count++;
            }
        }
        return count >= 1;
    }
}