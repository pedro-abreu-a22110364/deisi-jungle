package pt.ulusofona.lp2.deisiJungle;

public class Agua extends Food{

    public Agua(char identifier, String nome, String foodImage, int energyCarnivoros, int energyHerbivoros, int energyOmnivoros) {
        super(identifier, nome, foodImage, energyCarnivoros, energyHerbivoros, energyOmnivoros);
    }

    public Agua(char identifier, String nome, String foodImage, int energyCarnivoros, int energyHerbivoros, int energyOmnivoros, int position) {
        super(identifier, nome, foodImage, energyCarnivoros, energyHerbivoros, energyOmnivoros, position);
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

    @Override
    public int getEnergyOmnivoros() {
        return energyOmnivoros;
    }

    @Override
    public int getPosition() {
        return position;
    }

    @Override
    public String getFoodType() {
        return "Agua";
    }
}
