package pt.ulusofona.lp2.deisiJungle;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GameManager {

    int minPlayers = 2;
    int maxPlayers = 4;
    int jungleSize;
    int initialEnergy;
    ArrayList<Specie> alSpecies = createDefaultSpecies();
    //ArrayList<Player> alPlayers = new ArrayList<>();

    HashMap<Integer,Player> hmPlayers = new HashMap<>(); //

    Player winner = new Player();
    Player nowPlaying;
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
        this.initialEnergy = initialEnergy;
        this.jungleSize = jungleSize;

        int nrOfTarzans = 0;

        if(playersInfo == null) {
            return false;
        }

        for (String[] playerInfo : playersInfo) {
            if(hmPlayers.size() < 4){
                for (Specie  specie : alSpecies) {
                    if(playerInfo[2].charAt(0) == specie.getIdentifier()) {
                        if(playerInfo[1].isEmpty()) {
                            return false;
                        }
                        if(playerInfo[2].equals(String.valueOf('Z')) && nrOfTarzans == 1) {
                            return false;
                        }

                        if(playerInfo[2].equals(String.valueOf('Z')) && nrOfTarzans < 1) {
                            nrOfTarzans ++;
                        }
                        Player player = new Player(Integer.parseInt(playerInfo[0]), playerInfo[1], specie);
                        hmPlayers.put(player.getIdentifier(),player);
                    }
                }
            }
            else {
                return false;
            }
        }

        if(hmPlayers.size() < minPlayers || hmPlayers.size() > maxPlayers) {
            return false;
        }

        if(jungleSize <= hmPlayers.size()) {
            return false;
        }


        return true;
    }

    public int[] getPlayerIds(int squareNr) {
        int[] arrayID = new int[hmPlayers.size()];
        int count = 0;
        for (Player player : hmPlayers.values()) {
            if(player.getPosition() == squareNr)
            {
                arrayID[count] = player.getIdentifier();
                count++;
            }
        }
        return arrayID;
    }

    public String[] getSquareInfo(int squareNr) {
        return new String[5];
    }

    public String[] getPlayerInfo(int playerId) {
        String[] strPlayerInfo = new String[4];
        for (Map.Entry<Integer, Player> playerEntry : hmPlayers.entrySet()) {
            if(hmPlayers.containsKey(playerId))
            {
                if(playerEntry.getKey() == playerId && playerEntry != null){
                        strPlayerInfo[0] = String.valueOf(playerEntry.getValue().getIdentifier());
                        strPlayerInfo[1] = playerEntry.getValue().getName();
                        strPlayerInfo[2] = String.valueOf(playerEntry.getValue().getSpecie().getIdentifier());
                        strPlayerInfo[3] = String.valueOf(playerEntry.getValue().getEnergy());
                }
            }
        }






        return strPlayerInfo;
    }

    public String[] getCurrentPlayerInfo() {
        return new String[5];
    }

    public String[][] getPlayersInfo() {
        String[][] strPlayersInfo = new String[hmPlayers.size()][4];
        int count = 0;
        for (Player player : hmPlayers.values()) {
            if(player != null){
                strPlayersInfo[count] = getPlayerInfo(player.getIdentifier());
                count++;
            }
        }
        return strPlayersInfo;
    }

    public boolean moveCurrentPlayer(int nrSquares,boolean bypassValidations) {
        if ((nrSquares < 1 || nrSquares > 6) && bypassValidations) {
            return false;
        }
        if(nowPlaying.getPosition() + nrSquares > jungleSize){
            return false;
        }

        nowPlaying.setPosition(nowPlaying.getPosition() + nrSquares);

        return true;
    }

    public String[] getWinnerInfo() {
        return getPlayerInfo(winner.getIdentifier());
    }

    public ArrayList<String> getGameResults() {
        return new ArrayList<String>();
    }

    public JPanel getAuthorsPanel() {
        return new JPanel();
    }

    public String whoIsTaborda() {
        return "wrestling";
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

    public boolean createPlayer(int identifier, String name, char specie){
        if(hmPlayers.size() >= maxPlayers)
        {
            return false;
        }

        for (Specie specy : alSpecies) {
            if(specy.getIdentifier() == specie)
            {
                Player player = new Player(identifier, name, specy);
                return true;
            }
        }
        return false;
    }


}
