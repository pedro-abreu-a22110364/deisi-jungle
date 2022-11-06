package pt.ulusofona.lp2.deisiJungle;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Species {
    char identifier;
    String nome;
    Image specieImage;

    public Species(char identifier, String nome) {
        this.identifier = identifier;
        this.nome = nome;
        //this.specieImage = specieImage;
    }

    public char getIdentifier()
    {
        return identifier;
    }

    public String getNome()
    {
        return nome;
    }

    public Image getSpecieImage()
    {
        return specieImage;
    }


}

