package pt.ulusofona.lp2.deisiJungle;

public class Carne extends Foods{

    int spoilTime;

    public Carne(char identifier, String nome, String foodImage, int energyCarnivoros, int energyHerbivoros, int spoilTime) {
        super(identifier, nome, foodImage, energyCarnivoros, energyHerbivoros);
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
    public int getEnergyHerbivoros() {
        return energyHerbivoros;
    }

}
