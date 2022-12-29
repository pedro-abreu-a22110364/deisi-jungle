package pt.ulusofona.lp2.deisiJungle;

public abstract class Foods {
    char identifier;
    String nome;

    String foodImage;
    int energyCarnivoros;

    int energyOmnivoros;
    int energyHerbivoros;

    int position;


    public Foods(char identifier, String nome, String foodImage, int energyCarnivoros, int energyHerbivoros, int energyOmnivoros) {
        this.identifier = identifier;
        this.nome = nome;
        this.foodImage = foodImage;
        this.energyCarnivoros = energyCarnivoros;
        this.energyOmnivoros = energyOmnivoros;
        this.energyHerbivoros = energyHerbivoros;
    }


    public Foods(char identifier, String nome, String foodImage, int energyCarnivoros, int energyHerbivoros, int energyOmnivoros, int position) {
        this.identifier = identifier;
        this.nome = nome;
        this.foodImage = foodImage;
        this.energyCarnivoros = energyCarnivoros;
        this.energyOmnivoros = energyOmnivoros;
        this.energyHerbivoros = energyHerbivoros;
        this.position = position;
    }

    public abstract char getIdentifier();
    public abstract String getNome();
    public abstract String getFoodImage();
    public abstract int getEnergyCarnivoros();
    public abstract int getEnergyOmnivoros();

    public abstract int getEnergyHerbivoros();
    public abstract int getPosition();

    public abstract String getFoodType();


}
