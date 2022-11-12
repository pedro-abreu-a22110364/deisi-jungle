package pt.ulusofona.lp2.deisiJungle;

public class Player {
    int identifier;
    String name;
    Specie specie;
    int energy; //Mudar tipo de variavel consoante o que é pedido
    int rank; //Mudar de classe caso necessário
    int position = 1;

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