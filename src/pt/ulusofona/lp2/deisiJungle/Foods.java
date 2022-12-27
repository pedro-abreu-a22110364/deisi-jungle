package pt.ulusofona.lp2.deisiJungle;

public abstract class Foods {
    char identifier;
    String nome;

    String foodImage;

    int energyCarnivoros;
    int energyHerbivoros;


    public Foods(char identifier, String nome, String foodImage, int energyCarnivoros, int energyHerbivoros) {
        this.identifier = identifier;
        this.nome = nome;
        this.foodImage = foodImage;
        this.energyCarnivoros = energyCarnivoros;
        this.energyHerbivoros = energyHerbivoros;
    }

    public abstract char getIdentifier();
    public abstract String getNome();
    public abstract String getFoodImage();
    public abstract int getEnergyCarnivoros();
    public abstract int getEnergyHerbivoros();
}
