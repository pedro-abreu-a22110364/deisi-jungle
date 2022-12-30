package pt.ulusofona.lp2.deisiJungle;

public class Banana extends Food{

    int quantidade;

    public Banana(char identifier, String nome, String foodImage, int energyCarnivoros, int energyHerbivoros, int energyOmnivoros, int quantidade) {
        super(identifier, nome, foodImage, energyCarnivoros, energyHerbivoros, energyOmnivoros);
        this.quantidade = quantidade;
    }

    public Banana(char identifier, String nome, String foodImage, int energyCarnivoros, int energyHerbivoros, int energyOmnivoros, int quantidade, int position) {
        super(identifier, nome, foodImage, energyCarnivoros, energyHerbivoros, energyOmnivoros, position);
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
    public int getEnergyOmnivoros() {
        return energyOmnivoros;
    }

    @Override
    public int getEnergyHerbivoros() {
        return energyHerbivoros;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void removeQuantidade() {
        this.quantidade--;
    }

    @Override
    public int getPosition() {
        return position;
    }

    @Override
    public String getFoodType() {
        return "Banana";
    }
}
