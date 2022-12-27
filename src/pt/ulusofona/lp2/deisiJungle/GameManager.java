package pt.ulusofona.lp2.deisiJungle;

import org.junit.runners.model.InitializationError;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.Random;

//Classe Specie passar a herança, classe Food(nova) também herança, classe casa, que contenha informação de cada casa
public class GameManager {

    int minPlayers = 2;
    int maxPlayers = 4;
    int jungleSize;

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
    ArrayList<Foods> alFoods = createDefaultFoods();

    HashMap<Integer,Player> hmPlayers = new HashMap<>(); //HashMap with id player as key
    HashMap<Integer,Integer> hmKeyIdValuePos = new HashMap<>();

    public GameManager(){

    }

    public String[][] getSpecies() {
        String[][] species = new String[alSpecies.size()][7];
        int count = 0;

        for (Specie specie : alSpecies) {
            species[count][0] = specie.getIdentifier() + "";
            species[count][1] = specie.getName();
            species[count][2] = specie.getSpecieImage() + "";
            species[count][3] = specie.getInitalEnergy() + "";
            species[count][4] = specie.getNeededEnergy() + "";
            species[count][5] = specie.getEnergyRecovery() + "";
            species[count][6] = specie.getMinSpeed() + ".." + specie.getMaxSpeed();

            count++;
        }
        return species;
    }

    public String[][] getFoodTypes(){
        String[][] foods = new String[alFoods.size()][3];
        int count = 0;

        for (Foods alFood : alFoods) {
            foods[count][0] = alFood.getIdentifier() + "" ;
            foods[count][1] = alFood.getNome() + "";
            foods[count][2] = alFood.getFoodImage() + "";
            count++;
        }

        return foods;
    }

    public InitializationError createInitialJungle(int jungleSize,String[][] playersInfo, String[][] foodsInfo)
    {
        this.jungleSize = jungleSize;
        int nrOfTarzans = 0;

        //Validate number of players
        if(playersInfo == null || playersInfo.length < minPlayers || playersInfo.length > maxPlayers || jungleSize < playersInfo.length * 2) {return new InitializationError("Invalid Number Of Players");}
        //Validate incorrect ids and names
        for (String[] strings : playersInfo) {
            if (strings[0] == null || !strings[0].matches("[0-9]+") || strings[1] == null || strings[1].equals("")) {return new InitializationError("Incorrect id or name");}
        }
        //Validate repeated ids
        for (int x = 0; x < playersInfo.length; x++) {
            for (int y = x + 1; y < playersInfo.length; y++) {
                if(Objects.equals(playersInfo[x][0], playersInfo[y][0])) {return new InitializationError("Repeated ids found");}
            }
        }
        //Validate incorrect species
        for (String[] strings : playersInfo) {
            if(strings[2] == null || !((strings[2].equals("E")) || (strings[2].equals("L")) || (strings[2].equals("T")) || (strings[2].equals("P")) || (strings[2].equals("Z")) || (strings[2].equals("M")) || (strings[2].equals("G")) || (strings[2].equals("Y")) || (strings[2].equals("X")))) {
                return new InitializationError("Incorrect specie found");
            }
        }

        for (String[] playerInfo : playersInfo) {
            if(hmPlayers.size() < 4){
                for (Specie  specie : alSpecies) {
                    if(playerInfo[2].charAt(0) == specie.getIdentifier()) {

                        if(playerInfo[1].isEmpty()) {return new InitializationError("Name invalid") ;}

                        if(playerInfo[2].equals(String.valueOf('Z')) && nrOfTarzans == 1) {return new InitializationError("There is already a tarzan player");}

                        if(playerInfo[2].equals(String.valueOf('Z')) && nrOfTarzans < 1) {
                            nrOfTarzans ++;
                        }

                        Player player = new Player(Integer.parseInt(playerInfo[0]), playerInfo[1], specie, specie.getInitalEnergy());
                        hmPlayers.put(player.getIdentifier(),player);
                        hmKeyIdValuePos.put(player.getIdentifier(),1);
                    }
                }
            }
            else {
                return new InitializationError("Incorrect number of players");
            }
        }
        orderByPosition = new int[hmKeyIdValuePos.size()];
        orderByID = new int[hmKeyIdValuePos.size()];
        orderOfPlay = idOrderOfPlay();
        return null;
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
        String[] strPlayerInfo = new String[5];
        if(hmPlayers.containsKey(playerId))
        {
                strPlayerInfo[0] = String.valueOf(hmPlayers.get(playerId).getIdentifier());
                strPlayerInfo[1] = hmPlayers.get(playerId).getName();
                strPlayerInfo[2] = String.valueOf(hmPlayers.get(playerId).getSpecie().getIdentifier());
                strPlayerInfo[3] = String.valueOf(hmPlayers.get(playerId).getEnergy());
                strPlayerInfo[4] = hmPlayers.get(playerId).getSpecie().getMinSpeed() + ".." + hmPlayers.get(playerId).getSpecie().getMaxSpeed() ;
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
        if(hmPlayers.get(idPlayerPlaying).getEnergy() - hmPlayers.get(idPlayerPlaying).getSpecie().getNeededEnergy() < 0) {
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
        JPanel jPanel = new JPanel();

        jPanel.setBackground(Color.LIGHT_GRAY);

        JLabel intro1 = new JLabel("Trabalho desenvolvido no âmbito");
        JLabel intro2 = new JLabel("da cadeira de LP2 por:");
        JLabel partition = new JLabel("---------------------------------------------------------------------------------");
        JLabel student1 = new JLabel("Guilherme Simão, a22106142");
        JLabel student2 = new JLabel("Pedro Abreu, a22110364");

        jPanel.add(intro1);
        jPanel.add(intro2);
        jPanel.add(partition);
        jPanel.add(student1);
        jPanel.add(student2);

        return jPanel;
    }

    public String whoIsTaborda() {
        return "wrestling";
    }

    public ArrayList<Specie> createDefaultSpecies() {
        ArrayList<Specie> alSpecies = new ArrayList<>(); //Creating the list to return it later

        //Creating the objects
        Elefante elefante = new Elefante('E', "Elefante","Carnivoro","elephant.png",180, 4, 10, 1,6);
        Leao leao = new Leao('L', "Leão","lion.png","Carnivoro",80, 2, 10, 4,6);
        Tartaruga tartaruga = new Tartaruga('T', "Tartaruga","Carnivoro","turtle.png",150,1,5,1,3);
        Passaro passaro = new Passaro('P', "Pássaro","Carnivoro","bird.png",70,4,50,5,6);
        Tarzan tarzan = new Tarzan('Z', "Tarzan","Carnivoro","tarzan.png",70,2,20,1,6);

        Mario mario = new Mario('M',"Mario","Carnivoro","mario.png",100,2,20,2,6);
        Ghost ghost = new Ghost('G',"PacMan","Carnivoro","pacman.png",100,2,20,2,2);
        Pikachu pikachu = new Pikachu('Y',"Pikachu","Carnivoro","pikachu.png",100,2,20,2,2);
        Zelda zelda = new Zelda('X',"Zelda","Carnivoro","zelda.png",100,2,20,2,2);

        //Adding objects to list
        alSpecies.add(elefante);
        alSpecies.add(leao);
        alSpecies.add(tartaruga);
        alSpecies.add(passaro);
        alSpecies.add(tarzan);

        alSpecies.add(mario);
        alSpecies.add(ghost);
        alSpecies.add(pikachu);
        alSpecies.add(zelda);

        //Returning the list back to "main"
        return alSpecies;
    }

    public ArrayList<Foods> createDefaultFoods() {
        ArrayList<Foods> alfood = new ArrayList<>(); //Creating the list to return it later

        Erva erva = new Erva('e', "erva", "grass.png", 20, 20);
        Banana banana = new Banana('b', "Cacho de Bananas", "bananas.png", 40, 40, 3);
        Carne carne = new Carne('c', "Carne", "meat.png", 50,0, 12);

        Random r = new Random();
        int low = 10;
        int high = 51;
        int result = r.nextInt(high-low) + low;

        CogumelosMagicos cogumelo = new CogumelosMagicos('m', "Cogumelos magicos", "mushroom.png", result, result);
        Agua agua = new Agua('a', "Agua", "water.png", 15,20);

        //Adding objects to list
        alfood.add(erva);
        alfood.add(banana);
        alfood.add(carne);
        alfood.add(cogumelo);
        alfood.add(agua);

        //Returning the list back to "main"
        return alfood;
    }

    public void moveCurrentPlayerFinal () {
        hmPlayers.get(idPlayerPlaying).setPosition(jungleSize);
        hmPlayers.get(idPlayerPlaying).removeEnergy(hmPlayers.get(idPlayerPlaying).getSpecie().getNeededEnergy());
        hmKeyIdValuePos.put(idPlayerPlaying,jungleSize);
    }

    public void moveCurrentPlayerAdd (int nrSquares) {
        hmPlayers.get(idPlayerPlaying).setPosition(hmPlayers.get(idPlayerPlaying).getPosition() + nrSquares);
        hmPlayers.get(idPlayerPlaying).removeEnergy(hmPlayers.get(idPlayerPlaying).getSpecie().getNeededEnergy());
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

    public boolean checkNoEnergy(){
        for (Player player : hmPlayers.values()) {
            if(player.getEnergy() - player.getSpecie().getNeededEnergy() >= 0)
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