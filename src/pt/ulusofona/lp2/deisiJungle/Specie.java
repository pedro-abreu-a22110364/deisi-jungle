package pt.ulusofona.lp2.deisiJungle;

/*
        Specie elefante = new Specie('E', "Elefante","elephant.png");
        Specie leao = new Specie('L', "Leão","lion.png");
        Specie tartaruga = new Specie('T', "Tartaruga","turtle.png");
        Specie passaro = new Specie('P', "Pássaro","bird.png");
        Specie tarzan = new Specie('Z', "Tarzan","tarzan.png");

        Specie mario = new Specie('M',"Mario","mario.png");
        Specie ghost = new Specie('G',"PacMan","pacman.png");
        Specie pikachu = new Specie('Y',"Pikachu","pikachu.png");
        Specie zelda = new Specie('X',"Zelda","zelda.png");

 */
public abstract class Specie {
    char identifier;
    String nome;
    String specieImage;
    String specieType;
    // New specs
    int initalEnergy;
    int neededEnergy;
    int energyRecovery;
    int minSpeed;
    int maxSpeed;


    public Specie(char identifier, String nome, String specieImage, String specieType, int initalEnergy, int neededEnergy, int energyRecovery, int minSpeed, int maxSpeed) {
        this.identifier = identifier;
        this.nome = nome;
        this.specieImage = specieImage;
        this.specieType = specieType;
        this.initalEnergy = initalEnergy;
        this.neededEnergy = neededEnergy;
        this.energyRecovery = energyRecovery;
        this.minSpeed = minSpeed;
        this.maxSpeed = maxSpeed;
    }

    public abstract char getIdentifier();
    public abstract String getName();
    public abstract String getSpecieImage();
    public abstract int getInitalEnergy();
    public abstract int getNeededEnergy();
    public abstract int getEnergyRecovery();
    public abstract int getMinSpeed();
    public abstract int getMaxSpeed();
    public abstract String getSpecieType();
    public abstract String getSpecieClass();

}