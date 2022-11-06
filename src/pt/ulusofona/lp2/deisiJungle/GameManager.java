package pt.ulusofona.lp2.deisiJungle;

import javax.swing.*;
import java.util.ArrayList;

public class GameManager {

    int minPlayers = 2;
    int maxPlayers = 4;
    ArrayList<Specie> alSpecies = createDefaultSpecies();
    ArrayList<Player> alPlayers;

    public GameManager(){

    }

    public String[][] getSpecies() {
        String[][] species = new String[alSpecies.size()][3];
        int count = 0;

        for (Specie specie : alSpecies) {
            species[count][0] = specie.getIdentifier() + "";
            species[count][1] = specie.getName();
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
        String[] strPlayerInfo = new String[4];
        for (Player player : alPlayers) {
            if(player != null){
                if(player.getIdentifier() == playerId)
                {
                    strPlayerInfo[0] = String.valueOf(player.getIdentifier());
                    strPlayerInfo[1] = player.getName();
                    strPlayerInfo[2] = String.valueOf(player.getSpecie().getIdentifier());
                    strPlayerInfo[3] = String.valueOf(player.getEnergy());
                }
            }
        }
        return strPlayerInfo;
    }

    public String[] getCurrentPlayerInfo() {
        return new String[5];
    }

    public String[][] getPlayersInfo() {
        String[][] strPlayersInfo = new String[alPlayers.size()][4];
        int count = 0;
        for (Player player : alPlayers) {
            if(player != null){
                strPlayersInfo[count][0] = String.valueOf(player.getIdentifier());
                strPlayersInfo[count][1] = player.getName();
                strPlayersInfo[count][2] = String.valueOf(player.getSpecie().getIdentifier());
                strPlayersInfo[count][3] = String.valueOf(player.getEnergy());
                count++;
            }
        }
        return strPlayersInfo;
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

    public ArrayList<Specie> createDefaultSpecies() {
        ArrayList<Specie> alSpecies = new ArrayList<>(); //Creating the list to return it later

        //Creating the objects
        Specie elefante = new Specie('E', "Elefante");
        Specie leao = new Specie('L', "Leão");
        Specie tartaruga = new Specie('T', "Tartaruga");
        Specie passaro = new Specie('P', "Pássaro");
        Specie tarzan = new Specie('Z', "Tarzan");

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
