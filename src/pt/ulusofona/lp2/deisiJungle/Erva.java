package pt.ulusofona.lp2.deisiJungle;

public class Erva extends Foods{
    public Erva(char identifier, String nome, String foodImage, int energyCarnivoros, int energyHerbivoros) {
        super(identifier, nome, foodImage, energyCarnivoros, energyHerbivoros);
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
