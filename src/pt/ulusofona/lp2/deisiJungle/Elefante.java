package pt.ulusofona.lp2.deisiJungle;

public class Elefante extends Specie{

    public Elefante(char identifier, String nome, String specieImage, String specieType, int initalEnergy, int neededEnergy, int energyRecovery, int minSpeed, int maxSpeed) {
        super(identifier, nome, specieImage, specieType, initalEnergy, neededEnergy, energyRecovery, minSpeed, maxSpeed);
    }

    @Override
    public char getIdentifier() {
        return identifier;
    }

    @Override
    public String getName() {
        return nome;
    }

    @Override
    public String getSpecieImage() {
        return specieImage;
    }

    @Override
    public int getInitalEnergy() {
        return initalEnergy;
    }

    @Override
    public int getNeededEnergy() {
        return neededEnergy;
    }

    @Override
    public int getEnergyRecovery() {
        return energyRecovery;
    }

    @Override
    public int getMinSpeed() {
        return minSpeed;
    }

    @Override
    public int getMaxSpeed() {
        return maxSpeed;
    }

    @Override
    public String getSpecieType() {
        return specieType;
    }


}
