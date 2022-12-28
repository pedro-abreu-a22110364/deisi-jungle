package pt.ulusofona.lp2.deisiJungle;

public class CogumelosMagicos extends Foods {

    public CogumelosMagicos(char identifier, String nome, String foodImage, int energyCarnivoros, int energyHerbivoros, int energyOmnivoros) {
        super(identifier, nome, foodImage, energyCarnivoros, energyHerbivoros, energyOmnivoros);
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
    public int getPosition() {
        return position;
    }
}
