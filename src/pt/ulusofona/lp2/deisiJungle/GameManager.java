package pt.ulusofona.lp2.deisiJungle;

import javax.swing.*;
import java.util.ArrayList;

public class GameManager {

    int minPlayers = 2;
    int maxPlayers = 4;
    ArrayList<Species> alSpecies = createDefaultSpecies();

    public GameManager(){

    }

    public String[][] getSpecies() {
        String[][] species = new String[alSpecies.size()][3];
        int count = 0;

        for (Species specie : alSpecies) {
            species[count][0] = specie.getIdentifier() + "";
            species[count][1] = specie.getNome();
            species[count][2] = specie.getSpecieImage() + "";
            count++;
        }

        return species;
    }

    public boolean createInitialJungle(int jungleSize,int initialEnergy,String[][] playersInfo) {
        return true;
    }

    public int[] getPlayerIds(int squareNr) {
        return new int[5];
    }

    public String[] getSquareInfo(int squareNr) {
        return new String[5];
    }

    public String[] getPlayerInfo(int playerId) {
        return new String[5];
    }

    public String[] getCurrentPlayerInfo() {
        return new String[5];
    }

    public String[][] getPlayersInfo() {
        return new String[5][];
    }

    public boolean moveCurrentPlayer(int nrSquares,boolean bypassValidations) {
        return true;
    }

    public String[] getWinnerInfo() {
        return new String[5];
    }

    public ArrayList<String> getGameResults() {
        return new ArrayList<String>();
    }

    public JPanel getAuthorsPanel() {
        return new JPanel();
    }

    public String whoIsTaborda() {
        return "";
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
