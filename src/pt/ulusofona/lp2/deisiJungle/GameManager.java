package pt.ulusofona.lp2.deisiJungle;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class GameManager {

    int minPlayers = 2;
    int maxPlayers = 4;
    int jungleSize;
    //Variaveis de energia
    int initialEnergy;
    int energyMoveCost = 2;
    //Variaveis com informação de players
    int winner = 0;
    int idPlayerPlaying;
    int playerPlaying;

    //Variaveis com informação sobre o jogo
    boolean gameFinished = false;
    int[] orderOfPlay;
    ArrayList<Specie> alSpecies = createDefaultSpecies();
    HashMap<Integer,Player> hmPlayers = new HashMap<>(); //HashMap with id player as key
    HashMap<Integer,Player> hmRankings = new HashMap<>();

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

        //Validate number of players
        if(playersInfo == null || playersInfo.length < minPlayers || playersInfo.length > maxPlayers || jungleSize < playersInfo.length * 2) {
            return false;
        }
        //Validate incorrect ids and names
        for (String[] strings : playersInfo) {
            if (strings[0] == null || !strings[0].matches("[0-9]+") || strings[1] == null || strings[1].equals("")) {
                return false;
            }
        }
        //Validate repeated ids
        for (int x = 0; x < playersInfo.length; x++) {
            for (int y = x + 1; y < playersInfo.length; y++) {
                if(Objects.equals(playersInfo[x][0], playersInfo[y][0])) {
                    return false;
                }
            }
        }
        //Validate incorrect species
        for (String[] strings : playersInfo) {
            if(strings[2] == null || !((strings[2].equals("E")) || (strings[2].equals("L")) || (strings[2].equals("T")) || (strings[2].equals("P")) || (strings[2].equals("Z")))) {
                return false;
            }
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

                        Player player = new Player(Integer.parseInt(playerInfo[0]), playerInfo[1], specie, initialEnergy);
                        hmPlayers.put(player.getIdentifier(),player);
                    }
                }
            }
            else {
                return false;
            }
        }
        orderOfPlay = idOrderOfPlay();
        return true;
    }

    public int[] getPlayerIds(int squareNr) {
        int count = 0;
        int numberPlayers = 0;

        if (squareNr < 1 || squareNr > jungleSize) {
            return new int[0];
        }

        for (Player player : hmPlayers.values()) {
            if(player.getPosition() == squareNr)
            {
               numberPlayers++;
            }
        }
        int[] arrayID = new int[numberPlayers];

        for (Player player : hmPlayers.values()) {
            if(player.getPosition() == squareNr)
            {
                arrayID[count] = player.getIdentifier();
                count++;
            }
        }
        if(count == 0)
        {
            return new int[0];
        }
            return arrayID;
    }

    public String[] getSquareInfo(int squareNr) {
        String[] strSquareInfo = new String[3];
        String playersInSquare = "";

        if(squareNr < 1 || squareNr > jungleSize){
            return null;
        }

        if(squareNr == jungleSize)
        {
            strSquareInfo[0] = "finish.png";
            strSquareInfo[1] = "Meta";
        }
        else{
            strSquareInfo[0] = "blank.png";
            strSquareInfo[1] = "Vazio";
        }

        strSquareInfo[2] = playersInSquare;

        for (Player player : hmPlayers.values()) {
            if(player.getPosition() == squareNr)
            {
                playersInSquare += player.getIdentifier() + ",";
            }
        }
        if (!playersInSquare.equals("")) {
            strSquareInfo[2] = playersInSquare.substring(0, playersInSquare.length() - 1);
        }

        return strSquareInfo;
    }

    public String[] getPlayerInfo(int playerId) {
        String[] strPlayerInfo = new String[4];
        if(hmPlayers.containsKey(playerId))
        {
                strPlayerInfo[0] = String.valueOf(hmPlayers.get(playerId).getIdentifier());
                strPlayerInfo[1] = hmPlayers.get(playerId).getName();
                strPlayerInfo[2] = String.valueOf(hmPlayers.get(playerId).getSpecie().getIdentifier());
                strPlayerInfo[3] = String.valueOf(hmPlayers.get(playerId).getEnergy());
        }


        return strPlayerInfo;
    }

    public String[] getCurrentPlayerInfo() {
        return getPlayerInfo(hmPlayers.get(idPlayerPlaying).getIdentifier());
    }

    public String[][] getPlayersInfo() {
        String[][] strPlayerInfo = new String[hmPlayers.size()][4];
        int count = 0;
        for (Player player : hmPlayers.values()) {
            if(player != null){
                strPlayerInfo[count][0] = String.valueOf(player.getIdentifier());
                strPlayerInfo[count][1] = player.getName();
                strPlayerInfo[count][2] = String.valueOf(player.getSpecie().getIdentifier());
                strPlayerInfo[count][3] = String.valueOf(player.getEnergy());
                count++;
            }
        }
        return strPlayerInfo;
    }

    public boolean moveCurrentPlayer(int nrSquares,boolean bypassValidations) {
        if ((nrSquares < 1 || nrSquares > 6) && !bypassValidations) {
            if(idPlayerPlaying == orderOfPlay[orderOfPlay.length - 1]){
                playerPlaying = 0;
                idPlayerPlaying = orderOfPlay[0];
                return false;
            }
            playerPlaying++;
            idPlayerPlaying = orderOfPlay[playerPlaying];
            return false;
        }
        //Verifica se o jogo já acabou
        if(gameFinished) {return false;}
        //Verifica se todos os players n tem energia
        if(checkNoEnergy()) {
            if(checkSamePosition()) {
                winner = getPlayerWithLowestID();
            } else {
                winner = checkPlayerWithBiggestPosition();
            }
            gameFinished = true;
            return false;
        }

        if(hmPlayers.get(idPlayerPlaying).getEnergy() - 2 < 0) {
            if(idPlayerPlaying == orderOfPlay[orderOfPlay.length - 1]) {
                //Jogador do inicio
                if(!checkSamePosition()) { playerPlaying = 0;
                    idPlayerPlaying = orderOfPlay[0];
                }
                return false;
            }
            //Próximo jogador
            if(checkSamePosition()){playerPlaying++;
                idPlayerPlaying = orderOfPlay[playerPlaying];
            }
            return false;
        }

        if(hmPlayers.get(idPlayerPlaying).getPosition() + nrSquares >= jungleSize){
            moveCurrentPlayerFinal();
            winner = idPlayerPlaying;
            gameFinished = true;
            return true;
        }
        moveCurrentPlayerAdd (nrSquares);

        if(idPlayerPlaying == orderOfPlay[orderOfPlay.length - 1]){
            playerPlaying = 0;
            idPlayerPlaying = orderOfPlay[0];
            return true;
        }
        playerPlaying++;
        idPlayerPlaying = orderOfPlay[playerPlaying];
        return true;
    }

    public String[] getWinnerInfo() {
        if (!gameFinished)
        {
            return null;
        }

        if(winner == 0)
        {
            return null;
        }

        if(hmPlayers.containsKey(winner))
        {
            return getPlayerInfo(hmPlayers.get(winner).getIdentifier());
        }

        return null;
    }

    public ArrayList<String> getGameResults() {
        ArrayList<String> alGameResults = new ArrayList<>();

        getRankingSorted();

        for (Player value : hmRankings.values()) {
            alGameResults.add("#" + value.getRank() + " " + value.getName() + ", " + value.getSpecie().getName() + ", " + value.getPosition());
        }

        for (String alGameResult : alGameResults) {
            System.out.println(alGameResult);
        }

        return alGameResults;
    }

    public JPanel getAuthorsPanel() {
        return null;
    }

    public String whoIsTaborda() {
        return "wrestling";
    }

    public ArrayList<Specie> createDefaultSpecies() {
        ArrayList<Specie> alSpecies = new ArrayList<>(); //Creating the list to return it later

        //Creating the objects
        Specie elefante = new Specie('E', "Elefante","elephant.png");
        Specie leao = new Specie('L', "Leão","lion.png");
        Specie tartaruga = new Specie('T', "Tartaruga","turtle.png");
        Specie passaro = new Specie('P', "Pássaro","bird.png");
        Specie tarzan = new Specie('Z', "Tarzan","tarzan.png");

        //Adding objects to list
        alSpecies.add(elefante);
        alSpecies.add(leao);
        alSpecies.add(tartaruga);
        alSpecies.add(passaro);
        alSpecies.add(tarzan);

        //Returning the list back to "main"
        return alSpecies;
    }


    public void moveCurrentPlayerFinal () {
        hmPlayers.get(idPlayerPlaying).setPosition(jungleSize);
        hmPlayers.get(idPlayerPlaying).removeEnergy(energyMoveCost);
    }

    public void moveCurrentPlayerAdd (int nrSquares) {
        hmPlayers.get(idPlayerPlaying).setPosition(hmPlayers.get(idPlayerPlaying).getPosition() + nrSquares);
        hmPlayers.get(idPlayerPlaying).removeEnergy(energyMoveCost);
    }

    public int checkPlayerWithBiggestPosition()
    {
        int position = 0;
        int playerID = 0;
        for (Player player : hmPlayers.values()) {
            if(player.getPosition() > position)
            {
                playerID = player.getIdentifier();
                position = player.getPosition();
            }
        }
        hmPlayers.get(playerID).setRank(1);
        return playerID;
    }

    public int checkPlayerWithSmallestPosition()
    {
        int position = 21;
        int playerID = 0;
        for (Player player : hmPlayers.values()) {
            if(player.getPosition() < position)
            {
                playerID = player.getIdentifier();
                position = player.getPosition();
            }
        }
        return playerID;
    }

    public void getRankingSorted() {
        HashMap<Integer,Player> hmPlayersTemp = hmPlayers;

        if (hmPlayersTemp.size() == 1) {
            hmRankings.put(1,hmPlayersTemp.get(checkPlayerWithBiggestPosition())); //add top 1
            hmRankings.get(1).setRank(1);
        }
        if (hmPlayersTemp.size() == 2) {
            hmRankings.put(1,hmPlayersTemp.get(checkPlayerWithBiggestPosition())); //add top 1
            hmRankings.get(1).setRank(1);
            hmPlayersTemp.remove(checkPlayerWithBiggestPosition());

            hmRankings.put(2,hmPlayersTemp.get(checkPlayerWithBiggestPosition())); //add top 2
            hmRankings.get(2).setRank(2);
        }
        if (hmPlayersTemp.size() == 3) {
            hmRankings.put(1,hmPlayersTemp.get(checkPlayerWithBiggestPosition())); //add top 1
            hmRankings.get(1).setRank(1);
            hmPlayersTemp.remove(checkPlayerWithBiggestPosition());

            hmRankings.put(2,hmPlayersTemp.get(checkPlayerWithBiggestPosition())); //add top 2
            hmRankings.get(2).setRank(2);

            hmRankings.put(3,hmPlayersTemp.get(checkPlayerWithSmallestPosition())); //add top 3
            hmRankings.get(3).setRank(3);
        }
        if (hmPlayersTemp.size() == 4) {
            hmRankings.put(1,hmPlayersTemp.get(checkPlayerWithBiggestPosition())); //add top 1
            hmRankings.get(1).setRank(1);
            hmPlayersTemp.remove(checkPlayerWithBiggestPosition());

            hmRankings.put(4,hmPlayersTemp.get(checkPlayerWithSmallestPosition())); //add top 4
            hmRankings.get(4).setRank(4);
            hmPlayersTemp.remove(checkPlayerWithSmallestPosition());

            hmRankings.put(2, hmPlayersTemp.get(checkPlayerWithBiggestPosition())); //add top 2
            hmRankings.get(2).setRank(2);

            hmRankings.put(3, hmPlayersTemp.get(checkPlayerWithSmallestPosition())); //add top 3
            hmRankings.get(3).setRank(3);


        }
    }

    public int getPlayerWithLowestID()
    {
        int id = Integer.MAX_VALUE;
        for (Player player : hmPlayers.values()) {
            if(player.getIdentifier() < id)
            {
                id = player.getIdentifier();
            }
        }
        return id;
    }

    public boolean checkNoEnergy(){
        for (Player player : hmPlayers.values()) {
            if(player.getEnergy() >= 2)
            {
                return false;
            }
        }
        return true;
    }

    public boolean checkSamePosition()
    {
        int position = hmPlayers.get(checkPlayerWithBiggestPosition()).getPosition();
        for (Player player : hmPlayers.values()) {
            if(player.getPosition() == position)
            {
                return true;
            }
        }
        return false;
    }

   //Bubble Sort
    public int[] idOrderOfPlay () {
        int[] idOrderOfPlay = new int[hmPlayers.size()];
        int count = 0, lastOrdered = idOrderOfPlay.length;
        boolean allInOrder = false;

        for (Integer integer : hmPlayers.keySet()) {
            idOrderOfPlay[count] = integer;
            count++;
        }

        while(!allInOrder) {
            allInOrder = true;

            for (int i = 0;i < lastOrdered -1;i++) {
                if (idOrderOfPlay[i] > idOrderOfPlay[i+1]) {
                    allInOrder = false;

                    int temp = idOrderOfPlay[i];
                    idOrderOfPlay[i] = idOrderOfPlay[i+1];
                    idOrderOfPlay[i] = temp;
                }
            }
            lastOrdered--;
        }
        playerPlaying = 0;
        idPlayerPlaying = idOrderOfPlay[0];
        return idOrderOfPlay;
    }
}