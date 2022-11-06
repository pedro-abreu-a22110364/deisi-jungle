package pt.ulusofona.lp2.deisiJungle;

import java.awt.*;

public class Specie {
    char identifier;
    String nome;
    Image specieImage;

    public Specie(char identifier, String nome) {
        this.identifier = identifier;
        this.nome = nome;
        //this.specieImage = specieImage;
    }

    public char getIdentifier()
    {
        return identifier;
    }

    public String getName()
    {
        return nome;
    }

    public Image getSpecieImage()
    {
        return specieImage;
    }


}

