package pt.ulusofona.lp2.deisiJungle;

public class Carne extends Food{

    int spoilTime;

    public Carne(char identifier, String nome, String foodImage, int energyCarnivoros, int energyHerbivoros, int energyOmnivoros, int spoilTime) {
        super(identifier, nome, foodImage, energyCarnivoros, energyHerbivoros, energyOmnivoros);
        this.spoilTime = spoilTime;
    }

    public Carne(char identifier, String nome, String foodImage, int energyCarnivoros, int energyHerbivoros, int energyOmnivoros, int spoilTime, int position) {
        super(identifier, nome, foodImage, energyCarnivoros, energyHerbivoros, energyOmnivoros, position);
        this.spoilTime = spoilTime;
    }

    @Override
    public char getIdentifier() {
        return identifier;
    }

    @Override
    public String getNome() {
        return nome;
    }

    @Override
    public String getFoodImage() {
        return foodImage;
    }

    @Override
    public int getEnergyCarnivoros() {
        return energyCarnivoros;
    }

    @Override
    public int getEnergyOmnivoros() {
        return energyOmnivoros;
    }

    @Override
    public int getEnergyHerbivoros() {
        return energyHerbivoros;
    }

    @Override
    public int getPosition() {
        return position;
    }

    public int getSpoilTime() {
        return spoilTime;
    }

    public void addSpoilTime() {
        this.spoilTime++;
    }

    @Override
    public String getFoodType() {
        return "Carne";
    }

}
