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

    public ArrayList<Species> createDefaultSpecies() {
        ArrayList<Species> alSpecies = new ArrayList<>(); //Creating the list to return it later

        //Creating the objects
        Species elefante = new Species('E', "Elefante");
        Species leao = new Species('L', "Leão");
        Species tartaruga = new Species('T', "Tartaruga");
        Species passaro = new Species('P', "Pássaro");
        Species tarzan = new Species('Z', "Tarzan");

        //Adding objects to list
        alSpecies.add(elefante);
        alSpecies.add(leao);
        alSpecies.add(tartaruga);
        alSpecies.add(passaro);
        alSpecies.add(tarzan);

        //Returning the list back to "main"
        return alSpecies;
    }



}

