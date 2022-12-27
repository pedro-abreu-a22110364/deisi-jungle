package pt.ulusofona.lp2.deisiJungle;

public class Banana extends Foods{
    int quantidade;

    public Banana(char identifier, String nome, String foodImage, int energyCarnivoros, int energyHerbivoros, int quantidade) {
        super(identifier, nome, foodImage, energyCarnivoros, energyHerbivoros);
        this.quantidade = quantidade;
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

    public int getQuantidade() {
        return quantidade;
    }
}
