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
    //Variavel para contar quantidade de vezes que o bubble sort tem de ser feito
    int maxNumOfBSRepetions = 3;

    //Variaveis com informação sobre o jogo
    boolean gameFinished = false;
    int[] orderOfPlay;
    int[] orderByPosition;
    int[] orderByID;
    ArrayList<Specie> alSpecies = createDefaultSpecies();
    HashMap<Integer,Player> hmPlayers = new HashMap<>(); //HashMap with id player as key
    HashMap<Integer,Integer> hmKeyIdValuePos = new HashMap<>();

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
        if(playersInfo == null || playersInfo.length < minPlayers || playersInfo.length > maxPlayers || jungleSize < playersInfo.length * 2) {return false;}
        //Validate incorrect ids and names
        for (String[] strings : playersInfo) {
            if (strings[0] == null || !strings[0].matches("[0-9]+") || strings[1] == null || strings[1].equals("")) {return false;}
        }
        //Validate repeated ids
        for (int x = 0; x < playersInfo.length; x++) {
            for (int y = x + 1; y < playersInfo.length; y++) {
                if(Objects.equals(playersInfo[x][0], playersInfo[y][0])) {return false;}
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

                        if(playerInfo[1].isEmpty()) {return false;}

                        if(playerInfo[2].equals(String.valueOf('Z')) && nrOfTarzans == 1) {return false;}

                        if(playerInfo[2].equals(String.valueOf('Z')) && nrOfTarzans < 1) {
                            nrOfTarzans ++;
                        }

                        Player player = new Player(Integer.parseInt(playerInfo[0]), playerInfo[1], specie, initialEnergy);
                        hmPlayers.put(player.getIdentifier(),player);
                        hmKeyIdValuePos.put(player.getIdentifier(),1);
                    }
                }
            }
            else {
                return false;
            }
        }
        orderByPosition = new int[hmKeyIdValuePos.size()];
        orderByID = new int[hmKeyIdValuePos.size()];
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
        if(!checkWinner()) {return checkWinner();}
        if(hmPlayers.get(idPlayerPlaying).getEnergy() - energyMoveCost < 0) {
            if(idPlayerPlaying == orderOfPlay[orderOfPlay.length - 1]) {
                //Jogador do inicio
                if(checkWinner()){playerPlaying = 0;
                    idPlayerPlaying = orderOfPlay[0];
                }
                checkWinner();
                return false;
            }
            //Próximo jogador
            if(checkWinner()){playerPlaying++;
                idPlayerPlaying = orderOfPlay[playerPlaying];
            }
            checkWinner();
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
            if(checkWinner()) {playerPlaying = 0;
                idPlayerPlaying = orderOfPlay[0];
            }
            checkWinner();
            return true;
        }

        if(checkWinner()) {playerPlaying++;
            idPlayerPlaying = orderOfPlay[playerPlaying];
        }
        checkWinner();
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

        getRanking();

        if(hmPlayers.size() == 4) {
            alGameResults.add("#" + hmPlayers.get(orderByID[3]).getRank() + " " + hmPlayers.get(orderByID[3]).getName() + ", " + hmPlayers.get(orderByID[3]).getSpecie().getName() + ", " + hmPlayers.get(orderByID[3]).getPosition());
            alGameResults.add("#" + hmPlayers.get(orderByID[2]).getRank() + " " + hmPlayers.get(orderByID[2]).getName() + ", " + hmPlayers.get(orderByID[2]).getSpecie().getName() + ", " + hmPlayers.get(orderByID[2]).getPosition());
            alGameResults.add("#" + hmPlayers.get(orderByID[1]).getRank() + " " + hmPlayers.get(orderByID[1]).getName() + ", " + hmPlayers.get(orderByID[1]).getSpecie().getName() + ", " + hmPlayers.get(orderByID[1]).getPosition());
            alGameResults.add("#" + hmPlayers.get(orderByID[0]).getRank() + " " + hmPlayers.get(orderByID[0]).getName() + ", " + hmPlayers.get(orderByID[0]).getSpecie().getName() + ", " + hmPlayers.get(orderByID[0]).getPosition());
        }

        if(hmPlayers.size() == 3) {
            alGameResults.add("#" + hmPlayers.get(orderByID[2]).getRank() + " " + hmPlayers.get(orderByID[2]).getName() + ", " + hmPlayers.get(orderByID[2]).getSpecie().getName() + ", " + hmPlayers.get(orderByID[2]).getPosition());
            alGameResults.add("#" + hmPlayers.get(orderByID[1]).getRank() + " " + hmPlayers.get(orderByID[1]).getName() + ", " + hmPlayers.get(orderByID[1]).getSpecie().getName() + ", " + hmPlayers.get(orderByID[1]).getPosition());
            alGameResults.add("#" + hmPlayers.get(orderByID[0]).getRank() + " " + hmPlayers.get(orderByID[0]).getName() + ", " + hmPlayers.get(orderByID[0]).getSpecie().getName() + ", " + hmPlayers.get(orderByID[0]).getPosition());
        }

        if(hmPlayers.size() == 2) {
            alGameResults.add("#" + hmPlayers.get(orderByID[1]).getRank() + " " + hmPlayers.get(orderByID[1]).getName() + ", " + hmPlayers.get(orderByID[1]).getSpecie().getName() + ", " + hmPlayers.get(orderByID[1]).getPosition());
            alGameResults.add("#" + hmPlayers.get(orderByID[0]).getRank() + " " + hmPlayers.get(orderByID[0]).getName() + ", " + hmPlayers.get(orderByID[0]).getSpecie().getName() + ", " + hmPlayers.get(orderByID[0]).getPosition());
        }

        for (String alGameResult : alGameResults) {
            System.out.println(alGameResult);
        }

        return alGameResults;
    }

    public boolean checkWinner()
    {
        if(!checkNoEnergy()) {
            if(checkSamePosition()) {
                winner = checkPlayerWithSmallestIDInSamePosition(checkSamePositionReturnPosition());
            } else {
                winner = checkPlayerWithBiggestPosition();
            }
            gameFinished = true;
            return false;
        }
        else {
            return true;
        }
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
        hmKeyIdValuePos.put(idPlayerPlaying,jungleSize);
    }

    public void moveCurrentPlayerAdd (int nrSquares) {
        hmPlayers.get(idPlayerPlaying).setPosition(hmPlayers.get(idPlayerPlaying).getPosition() + nrSquares);
        hmPlayers.get(idPlayerPlaying).removeEnergy(energyMoveCost);
        hmKeyIdValuePos.put(idPlayerPlaying,hmKeyIdValuePos.get(idPlayerPlaying) + nrSquares);
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

    public int checkPlayerWithSmallestIDInSamePosition(int position)
    {
        int playerID = Integer.MAX_VALUE;
        for (Player player : hmPlayers.values()) {
            if(player.getPosition() == position)
            {
                if(player.getIdentifier() < playerID){
                    playerID = player.getIdentifier();
                }
            }
        }
        return playerID;
    }

    public void getRanking() {

        sortArrayByPosition();
        int count = 0,rank = hmPlayers.size();

        while (count != maxNumOfBSRepetions) {
            sortArrayByPositionWithEqualID();
            count++;
        }

        for (int i : orderByID) {
            hmPlayers.get(i).setRank(rank);
            rank--;
        }

    }

    public boolean checkNoEnergy(){
        for (Player player : hmPlayers.values()) {
            if(player.getEnergy() - energyMoveCost >= 0)
            {
                return true;
            }
        }
        return false;
    }

    public boolean checkSamePosition()
    {
        int position;
        for (Player player : hmPlayers.values()) {
            position = player.getPosition();
            for (Player value : hmPlayers.values()) {
                if(value.getPosition() == position && player.getIdentifier() != value.getIdentifier())
                {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean checkSamePosition(HashMap<Integer, Player> hmPlayersTemp)
    {
        int position;
        for (Player player : hmPlayersTemp.values()) {
            position = player.getPosition();
            for (Player value : hmPlayersTemp.values()) {
                if(value.getPosition() == position && player.getIdentifier() != value.getIdentifier())
                {
                    return true;
                }
            }
        }
        return false;
    }

    public int checkSamePositionReturnPosition() //Retorna em que posição à mais que um player
    {
        int position;
        for (Player player : hmPlayers.values()) {
            position = player.getPosition();
            for (Player value : hmPlayers.values()) {
                if(position == value.getPosition() && player.getIdentifier() != value.getIdentifier())
                {
                    return position;
                }
            }
        }
        return 0;
    }

    public int checkSamePositionReturnPosition(HashMap<Integer, Player> hmPlayersTemp) //Retorna em que posição à mais que um player
    {
        int position;
        for (Player player : hmPlayersTemp.values()) {
            position = player.getPosition();
            for (Player value : hmPlayersTemp.values()) {
                if(position == value.getPosition() && player.getIdentifier() != value.getIdentifier())
                {
                    return position;
                }
            }
        }
        return 0;
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

    public void sortArrayByPosition () {
        int count = 0, lastOrdered = orderByPosition.length;
        boolean allInOrder = false;

        for (Integer value : hmKeyIdValuePos.values()) {
            orderByPosition[count] = value;
            count++;
        }

        count = 0;

        for (Integer integer : hmKeyIdValuePos.keySet()) {
            orderByID[count] = integer;
            count++;
        }

        while(!allInOrder) {
            allInOrder = true;

            for (int i = 0;i < lastOrdered -1;i++) {
                if (orderByPosition[i] > orderByPosition[i+1]) {
                    allInOrder = false;

                    int temp = orderByPosition[i];
                    int temp2 = orderByID[i];

                    orderByPosition[i] = orderByPosition[i+1];
                    orderByID[i] = orderByID[i+1];

                    orderByPosition[i+1] = temp;
                    orderByID[i+1] = temp2;
                }
            }
            lastOrdered--;
        }

    }

    public void sortArrayByPositionWithEqualID () {

        for (int i = 0;i < orderByPosition.length -1;i++) {
            if (orderByPosition[i] == orderByPosition[i + 1]) {
                if(orderByID[i] < orderByID[i+1]) {
                    int temp = orderByID[i];
                    orderByID[i] = orderByID[i+1];
                    orderByID[i+1] = temp;
                }
            }
        }
    }
}