package pt.ulusofona.lp2.deisiJungle;

import java.awt.*;

public class Specie {
    char identifier;
    String nome;
    String specieImage;

    public Specie(char identifier, String nome, String specieImage) {
        this.identifier = identifier;
        this.nome = nome;
        this.specieImage = specieImage;
    }

    public char getIdentifier()
    {
        return identifier;
    }

    public String getName()
    {
        return nome;
    }

    public String getSpecieImage()
    {
        return specieImage;
    }
}

