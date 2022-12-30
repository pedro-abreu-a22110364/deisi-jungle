package pt.ulusofona.lp2.deisiJungle;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.Random;

public class GameManager {

    int minPlayers = 2;
    int maxPlayers = 4;
    int jungleSize;
    int nrPlays = 0;

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

    ArrayList<Player> alPlayer = new ArrayList<>();
    ArrayList<Specie> alSpecies = createDefaultSpecies();
    ArrayList<Food> alFoods = createDefaultFoods();
    ArrayList<House> alHouses = new ArrayList<>();
    ArrayList<Food> gameFoods = new ArrayList<>();
    HashMap<Integer,Player> hmPlayers = new HashMap<>(); //HashMap with id player as key
    HashMap<Integer,Integer> hmKeyIdValuePos = new HashMap<>();

    InitializationError errorTemp;

    public GameManager(){

    }

    public ArrayList<House> getAlHouses() {
        return alHouses;
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

        for (Food alFood : alFoods) {
            foods[count][0] = alFood.getIdentifier() + "" ;
            foods[count][1] = alFood.getNome() + "";
            foods[count][2] = alFood.getFoodImage() + "";

            count++;
        }
        return foods;
    }

    public InitializationError createInitialJungle(int jungleSize, String[][] playersInfo){
        this.jungleSize = jungleSize;
        int nrOfTarzans = 0;

        //Validate number of players
        if(playersInfo == null || playersInfo.length < minPlayers || playersInfo.length > maxPlayers || jungleSize < playersInfo.length * 2) {
            errorTemp = new InitializationError("Invalid number of players");
            return errorTemp;
        }

        //Validate incorrect ids and names
        for (String[] strings : playersInfo) {
            if (strings[0] == null || !strings[0].matches("[0-9]+") || strings[1] == null || strings[1].equals("")) {
                errorTemp = new InitializationError("Incorrect id or name");
                return errorTemp;
            }
        }
        //Validate repeated ids
        for (int x = 0; x < playersInfo.length; x++) {
            for (int y = x + 1; y < playersInfo.length; y++) {
                if(Objects.equals(playersInfo[x][0], playersInfo[y][0])) {
                    errorTemp = new InitializationError("Repeated ids found");
                    return errorTemp;
                }
            }
        }
        //Validate incorrect species
        for (String[] strings : playersInfo) {
            if(strings[2] == null || !((strings[2].equals("E")) || (strings[2].equals("L")) || (strings[2].equals("T")) || (strings[2].equals("P")) || (strings[2].equals("Z")) || (strings[2].equals("M")) || (strings[2].equals("G")) || (strings[2].equals("Y")) || (strings[2].equals("X")))) {
                errorTemp = new InitializationError("Incorrect specie found");
                return errorTemp;
            }
        }
        //Creating players and adding them to the HashMaps
        for (String[] playerInfo : playersInfo) {
            for (Specie  specie : alSpecies) {
                if(playerInfo[2].charAt(0) == specie.getIdentifier()) {

                    if(playerInfo[2].equals(String.valueOf('Z')) && nrOfTarzans == 1) {return new InitializationError("There is already a tarzan player");}

                    if(playerInfo[2].equals(String.valueOf('Z')) && nrOfTarzans < 1) {
                        nrOfTarzans ++;
                    }

                    Player player = new Player(Integer.parseInt(playerInfo[0]), playerInfo[1], specie, specie.getInitalEnergy());
                    alPlayer.add(player);
                    hmPlayers.put(player.getIdentifier(),player);
                    hmKeyIdValuePos.put(player.getIdentifier(),1);
                }
            }
        }

        for (int i = 1; i <= jungleSize; i++) {
            House house = new House(i);
            if (house.getPosition() == 1) {
                for (Player player : alPlayer) {
                    house.getPlayers().add(player);
                }
            }
            alHouses.add(house);
        }

        orderByPosition = new int[hmKeyIdValuePos.size()];
        orderByID = new int[hmKeyIdValuePos.size()];
        orderOfPlay = idOrderOfPlay();

        return null;
    }

    public InitializationError createInitialJungle(int jungleSize,String[][] playersInfo, String[][] foodsInfo)
    {
        createInitialJungle(jungleSize, playersInfo);
        if (errorTemp != null) {
            if (Objects.equals(errorTemp.getMessage(), "Invalid number of players")) {
                return new InitializationError("Invalid number of players");
            } else if (Objects.equals(errorTemp.getMessage(), "Incorrect id or name")) {
                return new InitializationError("Incorrect id or name");
            } else if (Objects.equals(errorTemp.getMessage(), "Repeated ids found")) {
                return new InitializationError("Repeated ids found");
            } else if (Objects.equals(errorTemp.getMessage(), "Incorrect specie found")) {
                return new InitializationError("Incorrect specie found");
            }
        }

        //Validate incorrect foods and incorrect positions for them
        for (String[] strings : foodsInfo) {
            if(strings[0] == null || !((strings[0].equals("e")) || (strings[0].equals("a")) || (strings[0].equals("b")) || (strings[0].equals("c")) || (strings[0].equals("m")))) {
                return new InitializationError("Incorrect food found");
            }
            if(!(strings[1].matches("[0-9]+")) || Integer.parseInt(strings[1]) <= 1 || Integer.parseInt(strings[1]) >= jungleSize) {
                return new InitializationError("Ilegal position for food");
            }
        }

        for (String[] strings : foodsInfo) {

            if (strings[0].equals("e")) {
                Erva erva = new Erva('e', "Erva", "grass.png", -20, 20, 20, Integer.parseInt(strings[1]));
                gameFoods.add(erva);

                for (House alHouse : alHouses) {
                    if (alHouse.getPosition() == Integer.parseInt(strings[1])) {
                        alHouse.colocarComida(erva);
                    }
                }
            } else if (strings[0].equals("a")) {
                Agua agua = new Agua('a', "Agua", "water.png", 15, 15, 20, Integer.parseInt(strings[1]));
                gameFoods.add(agua);

                for (House alHouse : alHouses) {
                    if (alHouse.getPosition() == Integer.parseInt(strings[1])) {
                        alHouse.colocarComida(agua);
                    }
                }
            } else if (strings[0].equals("b")) {
                Banana banana = new Banana('b', "Banana", "bananas.png", 40, 40, 40, 3, Integer.parseInt(strings[1]));
                gameFoods.add(banana);

                for (House alHouse : alHouses) {
                    if (alHouse.getPosition() == Integer.parseInt(strings[1])) {
                        alHouse.colocarComida(banana);
                    }
                }
            } else if (strings[0].equals("c")) {
                Carne carne = new Carne('c', "Carne", "meat.png", 50, 0, 50, 12, Integer.parseInt(strings[1]));
                gameFoods.add(carne);

                for (House alHouse : alHouses) {
                    if (alHouse.getPosition() == Integer.parseInt(strings[1])) {
                        alHouse.colocarComida(carne);
                    }
                }
            } else if (strings[0].equals("m")) {
                Random r = new Random();
                int low = 10;
                int high = 51;
                int result = r.nextInt(high - low) + low;

                CogumelosMagicos cogumelosMagicos = new CogumelosMagicos('m', "Cogumelos Magicos", "mushroom.png", result, result, result, Integer.parseInt(strings[1]));
                gameFoods.add(cogumelosMagicos);

                for (House alHouse : alHouses) {
                    if (alHouse.getPosition() == Integer.parseInt(strings[1])) {
                        alHouse.colocarComida(cogumelosMagicos);
                    }
                }
            }
        }
        return null;
    }

    public int[] getPlayerIds(int squareNr) {
        int count = 0;
        int numberPlayers = 0;

        if (squareNr < 1 || squareNr > jungleSize) {
            return new int[0];
        }

        for (Player player : hmPlayers.values()) {
            if(player.getPosition() == squareNr) {
               numberPlayers++;
            }
        }
        int[] arrayID = new int[numberPlayers];

        for (Player player : hmPlayers.values()) {
            if(player.getPosition() == squareNr) {
                arrayID[count] = player.getIdentifier();
                count++;
            }
        }
        if(count == 0) {
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

        for (House house : alHouses) {
            if (house.getPosition() == squareNr) {
                strSquareInfo[0] = "blank.png";
                strSquareInfo[1] = "Vazio";

                if (house.getFood() != null) {
                    switch (house.getFood().getIdentifier()) {
                        case 'e' -> {
                            strSquareInfo[0] = "grass.png";
                            strSquareInfo[1] = "Erva : +- 20 energia";
                        }
                        case 'a' -> {
                            strSquareInfo[0] = "water.png";
                            strSquareInfo[1] = "Agua : + 15U|20% energia";
                        }
                        case 'b' -> {
                            strSquareInfo[0] = "bananas.png";
                            strSquareInfo[1] = "Bananas : " + ((Banana) house.getFood()).getQuantidade() + " : + 40 energia";
                        }
                        case 'c' -> {
                            strSquareInfo[0] = "meat.png";
                            strSquareInfo[1] = "Carne : +- 50 energia : " + nrPlays + " jogadas";

                            if (nrPlays >= 12) {
                                strSquareInfo[0] = "meat.png";
                                strSquareInfo[1] = "Carne toxica";
                            }
                        }
                        case 'm' -> {
                            strSquareInfo[0] = "mushroom.png";
                            strSquareInfo[1] = "Cogumelo Magico: +- " + house.getFood().getEnergyOmnivoros() + "% energia";
                        }
                    }
                }
            }
        }

        if(squareNr == jungleSize) {
            strSquareInfo[0] = "finish.png";
            strSquareInfo[1] = "Meta";
        }

        //string with player identifiers
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

    public String[][] getFoodsInfo() {
        String[][] strFoodInfo = new String[alFoods.size()][2];
        int count = 0;
        for (Food food : alFoods) {
            if(food != null){
                strFoodInfo[count][0] = String.valueOf(food.getIdentifier());
                strFoodInfo[count][1] = String.valueOf(food.getPosition());

                count++;
            }
        }
        return strFoodInfo;
    }

    public String[] getCurrentPlayerEnergyInfo(int nrPositions){
        String[] strPlayerEnergyInfo = new String[2];

        if (nrPositions < 0) {
            nrPositions = nrPositions * (-1);
        }

        strPlayerEnergyInfo[0] = hmPlayers.get(idPlayerPlaying).getSpecie().getNeededEnergy() * nrPositions + "";
        strPlayerEnergyInfo[1] = hmPlayers.get(idPlayerPlaying).getSpecie().getEnergyRecovery() + "";

        return strPlayerEnergyInfo;
    }

    public MovementResult moveCurrentPlayer(int nrSquares,boolean bypassValidations) {
        if ((nrSquares < -6 || nrSquares > 6) && !bypassValidations) {
            if(idPlayerPlaying == orderOfPlay[orderOfPlay.length - 1]){
                playerPlaying = 0;
                idPlayerPlaying = orderOfPlay[0];
                nrPlays++;
                return new MovementResult(MovementResultCode.INVALID_MOVEMENT,"nrSquares ultrapassa os limites");
            }
            playerPlaying++;
            idPlayerPlaying = orderOfPlay[playerPlaying];
            nrPlays++;
            return new MovementResult(MovementResultCode.INVALID_MOVEMENT,"nrSquares ultrapassa os limites");
        }
        //Verifica se o jogo já acabou
        if(gameFinished) {
            nrPlays++;
            return new MovementResult(MovementResultCode.INVALID_MOVEMENT,"jogo já acabou");
        }

        //Verifica se todos os players n tem energia
        if(hmPlayers.get(idPlayerPlaying).getEnergy() - hmPlayers.get(idPlayerPlaying).getSpecie().getNeededEnergy() < 0) {
            if(idPlayerPlaying == orderOfPlay[orderOfPlay.length - 1]) {
                //Jogador do inicio
                if(checkWinner()) {
                    playerPlaying = 0;
                    idPlayerPlaying = orderOfPlay[0];
                }
                checkWinner();
                nrPlays++;
                return new MovementResult(MovementResultCode.NO_ENERGY,"energia insuficiente");
            }
            //Próximo jogador
            if(checkWinner()){
                playerPlaying++;
                idPlayerPlaying = orderOfPlay[playerPlaying];
            }
            checkWinner();
            nrPlays++;
            return new MovementResult(MovementResultCode.NO_ENERGY,"energia insuficiente");
        }

        if (nrSquares == 0) {
            if(idPlayerPlaying == orderOfPlay[orderOfPlay.length - 1]) {
                hmPlayers.get(idPlayerPlaying).addEnergy(hmPlayers.get(idPlayerPlaying).getSpecie().getEnergyRecovery());
                if (hmPlayers.get(idPlayerPlaying).getEnergy() > 200) {
                    hmPlayers.get(idPlayerPlaying).setEnergy(200);
                }
                idPlayerPlaying = orderOfPlay[0];
                nrPlays++;
                return new MovementResult(MovementResultCode.VALID_MOVEMENT,"efetuou movimento");
            }
            hmPlayers.get(idPlayerPlaying).addEnergy(hmPlayers.get(idPlayerPlaying).getSpecie().getEnergyRecovery());
            if (hmPlayers.get(idPlayerPlaying).getEnergy() > 200) {
                hmPlayers.get(idPlayerPlaying).setEnergy(200);
            }
            playerPlaying++;
            idPlayerPlaying = orderOfPlay[playerPlaying];
            nrPlays++;
            return new MovementResult(MovementResultCode.VALID_MOVEMENT,"efetuou movimento");
        }

        moveCurrentPlayerAdd(nrSquares);

        for (House house : alHouses) {
            if (hmPlayers.get(idPlayerPlaying).getPosition() == house.getPosition()) {
                if (house.getFood() != null) {
                    switch (house.getFood().getIdentifier()) {
                        case 'e' -> {
                            if (Objects.equals(hmPlayers.get(idPlayerPlaying).getSpecie().getSpecieType(), "Carnivoro")) {
                                hmPlayers.get(idPlayerPlaying).removeEnergy(20);
                            } else {
                                hmPlayers.get(idPlayerPlaying).addEnergy(20);
                                if (hmPlayers.get(idPlayerPlaying).getEnergy() > 200) {
                                    hmPlayers.get(idPlayerPlaying).setEnergy(200);
                                }
                            }
                            if(idPlayerPlaying == orderOfPlay[orderOfPlay.length - 1]) {
                                idPlayerPlaying = orderOfPlay[0];
                                nrPlays++;
                            } else {
                                playerPlaying++;
                                idPlayerPlaying = orderOfPlay[playerPlaying];
                            }
                            nrPlays++;
                            return new MovementResult(MovementResultCode.CAUGHT_FOOD,"Apanhou " + house.getFood().getNome());
                        }
                        case 'a' -> {
                            if (Objects.equals(hmPlayers.get(idPlayerPlaying).getSpecie().getSpecieType(), "Omnivoro")) {
                                hmPlayers.get(idPlayerPlaying).percentageEnergy(20);
                                if (hmPlayers.get(idPlayerPlaying).getEnergy() > 200) {
                                    hmPlayers.get(idPlayerPlaying).setEnergy(200);
                                }
                            } else {
                                hmPlayers.get(idPlayerPlaying).addEnergy(15);
                                if (hmPlayers.get(idPlayerPlaying).getEnergy() > 200) {
                                    hmPlayers.get(idPlayerPlaying).setEnergy(200);
                                }
                            }
                            if(idPlayerPlaying == orderOfPlay[orderOfPlay.length - 1]) {
                                idPlayerPlaying = orderOfPlay[0];
                                nrPlays++;
                            } else {
                                playerPlaying++;
                                idPlayerPlaying = orderOfPlay[playerPlaying];
                            }
                            nrPlays++;
                            return new MovementResult(MovementResultCode.CAUGHT_FOOD,"Apanhou " + house.getFood().getNome());
                        }
                        case 'b' -> {
                            if (((Banana) house.getFood()).getQuantidade() <= 0) {
                                if(idPlayerPlaying == orderOfPlay[orderOfPlay.length - 1]) {
                                    idPlayerPlaying = orderOfPlay[0];
                                    nrPlays++;
                                } else {
                                    playerPlaying++;
                                    idPlayerPlaying = orderOfPlay[playerPlaying];
                                }
                                nrPlays++;
                                return new MovementResult(MovementResultCode.CAUGHT_FOOD,"Apanhou " + house.getFood().getFoodType());
                            }

                            if (hmPlayers.get(idPlayerPlaying).countEatenBananas()) {
                                hmPlayers.get(idPlayerPlaying).removeEnergy(40);
                                if(idPlayerPlaying == orderOfPlay[orderOfPlay.length - 1]) {
                                    idPlayerPlaying = orderOfPlay[0];
                                    nrPlays++;
                                } else {
                                    playerPlaying++;
                                    idPlayerPlaying = orderOfPlay[playerPlaying];
                                }
                                nrPlays++;
                                return new MovementResult(MovementResultCode.CAUGHT_FOOD,"Apanhou " + house.getFood().getFoodType());
                            }

                            hmPlayers.get(idPlayerPlaying).addEnergy(40);
                            if (hmPlayers.get(idPlayerPlaying).getEnergy() > 200) {
                                hmPlayers.get(idPlayerPlaying).setEnergy(200);
                            }
                            ((Banana) house.getFood()).removeQuantidade();
                            if(idPlayerPlaying == orderOfPlay[orderOfPlay.length - 1]) {
                                idPlayerPlaying = orderOfPlay[0];
                                nrPlays++;
                            } else {
                                playerPlaying++;
                                idPlayerPlaying = orderOfPlay[playerPlaying];
                            }
                            nrPlays++;
                            return new MovementResult(MovementResultCode.CAUGHT_FOOD,"Apanhou " + house.getFood().getFoodType());
                        }
                        case 'c' -> {
                            if (nrPlays >= 12) {
                                hmPlayers.get(idPlayerPlaying).halfEnergy();
                                if(idPlayerPlaying == orderOfPlay[orderOfPlay.length - 1]) {
                                    idPlayerPlaying = orderOfPlay[0];
                                    nrPlays++;
                                } else {
                                    playerPlaying++;
                                    idPlayerPlaying = orderOfPlay[playerPlaying];
                                }
                                nrPlays++;
                                return new MovementResult(MovementResultCode.CAUGHT_FOOD,"Apanhou " + house.getFood().getNome());
                            }

                            if (Objects.equals(hmPlayers.get(idPlayerPlaying).getSpecie().getSpecieType(), "Herbivoro")) {
                                if(idPlayerPlaying == orderOfPlay[orderOfPlay.length - 1]) {
                                    idPlayerPlaying = orderOfPlay[0];
                                    nrPlays++;
                                } else {
                                    playerPlaying++;
                                    idPlayerPlaying = orderOfPlay[playerPlaying];
                                }
                                nrPlays++;
                                return new MovementResult(MovementResultCode.CAUGHT_FOOD,"Apanhou " + house.getFood().getNome());
                            }
                            hmPlayers.get(idPlayerPlaying).addEnergy(50);
                            if (hmPlayers.get(idPlayerPlaying).getEnergy() > 200) {
                                hmPlayers.get(idPlayerPlaying).setEnergy(200);
                            }
                            if(idPlayerPlaying == orderOfPlay[orderOfPlay.length - 1]) {
                                idPlayerPlaying = orderOfPlay[0];
                                nrPlays++;
                            } else {
                                playerPlaying++;
                                idPlayerPlaying = orderOfPlay[playerPlaying];
                            }
                            nrPlays++;
                            return new MovementResult(MovementResultCode.CAUGHT_FOOD,"Apanhou " + house.getFood().getNome());
                        }
                        case 'm' -> {
                            if (nrPlays % 2 == 0) {
                                hmPlayers.get(idPlayerPlaying).percentageEnergy(house.getFood().getEnergyOmnivoros());
                                if (hmPlayers.get(idPlayerPlaying).getEnergy() > 200) {
                                    hmPlayers.get(idPlayerPlaying).setEnergy(200);
                                }
                                if(idPlayerPlaying == orderOfPlay[orderOfPlay.length - 1]) {
                                    idPlayerPlaying = orderOfPlay[0];
                                    nrPlays++;
                                } else {
                                    playerPlaying++;
                                    idPlayerPlaying = orderOfPlay[playerPlaying];
                                }
                                nrPlays++;
                                return new MovementResult(MovementResultCode.CAUGHT_FOOD,"Apanhou " + house.getFood().getNome());
                            }
                            hmPlayers.get(idPlayerPlaying).percentageEnergy(-1 * (house.getFood().getEnergyOmnivoros()));
                            if(idPlayerPlaying == orderOfPlay[orderOfPlay.length - 1]) {
                                idPlayerPlaying = orderOfPlay[0];
                                nrPlays++;
                            } else {
                                playerPlaying++;
                                idPlayerPlaying = orderOfPlay[playerPlaying];
                            }
                            nrPlays++;
                            return new MovementResult(MovementResultCode.CAUGHT_FOOD,"Apanhou " + house.getFood().getNome());
                        }
                    }
                }
            }
        }

        if(hmPlayers.get(idPlayerPlaying).getPosition() >= jungleSize){
            moveCurrentPlayerFinal();
            winner = idPlayerPlaying;
            gameFinished = true;
            nrPlays++;
            return new MovementResult(MovementResultCode.VALID_MOVEMENT,"efetuou movimento");
        }

        if(idPlayerPlaying == orderOfPlay[orderOfPlay.length - 1]){
            if(checkWinner()) {
                playerPlaying = 0;
                idPlayerPlaying = orderOfPlay[0];
            }
            checkWinner();
            nrPlays++;
            return new MovementResult(MovementResultCode.VALID_MOVEMENT,"efetuou movimento");
        }

        if(checkWinner()) {
            playerPlaying++;
            idPlayerPlaying = orderOfPlay[playerPlaying];
        }
        checkWinner();
        nrPlays++;
        return new MovementResult(MovementResultCode.VALID_MOVEMENT,"efetuou movimento");
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
            alGameResults.add("#" + hmPlayers.get(orderByID[3]).getRank() + " " + hmPlayers.get(orderByID[3]).getName() + ", " + hmPlayers.get(orderByID[3]).getSpecie().getName() + ", " + hmPlayers.get(orderByID[3]).getPosition() + ", " + hmPlayers.get(orderByID[3]).getDistance() + ", " + hmPlayers.get(orderByID[3]).getEatenFoods().size());
            alGameResults.add("#" + hmPlayers.get(orderByID[2]).getRank() + " " + hmPlayers.get(orderByID[2]).getName() + ", " + hmPlayers.get(orderByID[2]).getSpecie().getName() + ", " + hmPlayers.get(orderByID[2]).getPosition() + ", " + hmPlayers.get(orderByID[2]).getDistance() + ", " + hmPlayers.get(orderByID[2]).getEatenFoods().size());
            alGameResults.add("#" + hmPlayers.get(orderByID[1]).getRank() + " " + hmPlayers.get(orderByID[1]).getName() + ", " + hmPlayers.get(orderByID[1]).getSpecie().getName() + ", " + hmPlayers.get(orderByID[1]).getPosition() + ", " + hmPlayers.get(orderByID[1]).getDistance() + ", " + hmPlayers.get(orderByID[1]).getEatenFoods().size());
            alGameResults.add("#" + hmPlayers.get(orderByID[0]).getRank() + " " + hmPlayers.get(orderByID[0]).getName() + ", " + hmPlayers.get(orderByID[0]).getSpecie().getName() + ", " + hmPlayers.get(orderByID[0]).getPosition() + ", " + hmPlayers.get(orderByID[0]).getDistance() + ", " + hmPlayers.get(orderByID[0]).getEatenFoods().size());
        }

        if(hmPlayers.size() == 3) {
            alGameResults.add("#" + hmPlayers.get(orderByID[2]).getRank() + " " + hmPlayers.get(orderByID[2]).getName() + ", " + hmPlayers.get(orderByID[2]).getSpecie().getName() + ", " + hmPlayers.get(orderByID[2]).getPosition() + ", " + hmPlayers.get(orderByID[2]).getDistance() + ", " + hmPlayers.get(orderByID[2]).getEatenFoods().size());
            alGameResults.add("#" + hmPlayers.get(orderByID[1]).getRank() + " " + hmPlayers.get(orderByID[1]).getName() + ", " + hmPlayers.get(orderByID[1]).getSpecie().getName() + ", " + hmPlayers.get(orderByID[1]).getPosition() + ", " + hmPlayers.get(orderByID[1]).getDistance() + ", " + hmPlayers.get(orderByID[1]).getEatenFoods().size());
            alGameResults.add("#" + hmPlayers.get(orderByID[0]).getRank() + " " + hmPlayers.get(orderByID[0]).getName() + ", " + hmPlayers.get(orderByID[0]).getSpecie().getName() + ", " + hmPlayers.get(orderByID[0]).getPosition() + ", " + hmPlayers.get(orderByID[0]).getDistance() + ", " + hmPlayers.get(orderByID[0]).getEatenFoods().size());
        }

        if(hmPlayers.size() == 2) {
            alGameResults.add("#" + hmPlayers.get(orderByID[1]).getRank() + " " + hmPlayers.get(orderByID[1]).getName() + ", " + hmPlayers.get(orderByID[1]).getSpecie().getName() + ", " + hmPlayers.get(orderByID[1]).getPosition() + ", " + hmPlayers.get(orderByID[1]).getDistance() + ", " + hmPlayers.get(orderByID[1]).getEatenFoods().size());
            alGameResults.add("#" + hmPlayers.get(orderByID[0]).getRank() + " " + hmPlayers.get(orderByID[0]).getName() + ", " + hmPlayers.get(orderByID[0]).getSpecie().getName() + ", " + hmPlayers.get(orderByID[0]).getPosition() + ", " + hmPlayers.get(orderByID[0]).getDistance() + ", " + hmPlayers.get(orderByID[0]).getEatenFoods().size());
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
        Elefante elefante = new Elefante('E', "Elefante","elephant.png", "Herbivoro",180, 4, 10, 1,6);
        Leao leao = new Leao('L', "Leão","lion.png","Carnivoro",80, 2, 10, 4,6);
        Tartaruga tartaruga = new Tartaruga('T', "Tartaruga","turtle.png", "Omnivoro",150,1,5,1,3);
        Passaro passaro = new Passaro('P', "Pássaro","bird.png","Omnivoro",70,4,50,5,6);
        Tarzan tarzan = new Tarzan('Z', "Tarzan","tarzan.png", "Omnivoro",70,2,20,1,6);

        Mario mario = new Mario('M',"Mario","mario.png","Omnivoro",100,2,20,2,5);
        Ghost ghost = new Ghost('G',"PacMan","pacman.png","Herbivoro",100,2,20,1,3);
        Pikachu pikachu = new Pikachu('Y',"Pikachu","pikachu.png","Herbivoro",100,2,20,4,6);
        Zelda zelda = new Zelda('X',"Zelda","zelda.png","Omnivoro",100,2,20,3,5);

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

    public ArrayList<Food> createDefaultFoods() {
        ArrayList<Food> alfood = new ArrayList<>(); //Creating the list to return it later

        Erva erva = new Erva('e', "Erva", "grass.png", 20,20,20);
        Banana banana = new Banana('b', "Bananas", "bananas.png", 40, 40,40, 3);
        Carne carne = new Carne('c', "Carne", "meat.png", 50,0,50, 0);
        Agua agua = new Agua('a', "Agua", "water.png", 15,20,20);

        Random r = new Random();
        int low = 10;
        int high = 51;
        int result = r.nextInt(high-low) + low;

        CogumelosMagicos cogumelo = new CogumelosMagicos('m', "Cogumelos magicos", "mushroom.png", result, result, result);

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
        hmKeyIdValuePos.put(idPlayerPlaying,jungleSize);
    }

    public void moveCurrentPlayerAdd (int nrSquares) {
        hmPlayers.get(idPlayerPlaying).setPosition(hmPlayers.get(idPlayerPlaying).getPosition() + nrSquares);
        hmPlayers.get(idPlayerPlaying).removeEnergy(hmPlayers.get(idPlayerPlaying).getSpecie().getNeededEnergy());

        if (hmPlayers.get(idPlayerPlaying).getPosition() <= 0) {
            hmPlayers.get(idPlayerPlaying).setPosition(1);
        }

        hmKeyIdValuePos.put(idPlayerPlaying,hmPlayers.get(idPlayerPlaying).getPosition());
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

    public boolean saveGame(File file){
        try {
            // Check if the file already exists
            if (!file.exists()) {
                // If the file doesn't exist, create it
                file.createNewFile();
            }

            // Open the file for writing
            FileWriter writer = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(writer);

            //Cleans File first.
            bw.write("");


            // Write some content to the file
            bw.write("Players");
            for (Player player : hmPlayers.values()) {
                bw.write(player.getIdentifier() + "," + player.getName() + "," + player.getSpecie().getIdentifier() + "," +
                        player.getEnergy() + "," + player.getRank() + "," + player.getPosition());
                bw.newLine();
            }

            bw.write("Food");
            for (Food food : gameFoods) {
                if(food.getIdentifier() == 'c'){
                    bw.write(food.getIdentifier() + "," + food.getPosition() + "," + ((Carne) food).getSpoilTime());

                }
                else if(food.getIdentifier() == 'b'){
                    bw.write(food.getIdentifier() + "," + food.getPosition() + "," + ((Banana) food).getQuantidade());

                }
                else{
                    bw.write(food.getIdentifier() + "," + food.getPosition());
                }
                bw.newLine();
            }

            bw.write("Eaten Foods");
            for (Player player : hmPlayers.values()) {
                for (Food eatenFood : player.getEatenFoods()) {
                    bw.write(player.getIdentifier() + "," + eatenFood.getIdentifier() + "," + eatenFood.getPosition());
                    bw.newLine();
                }
            }

            bw.write("GameManager");
            bw.write(gameFinished + "," + jungleSize + "," + idPlayerPlaying + "," + playerPlaying);


            // Close the writer to save the changes
            bw.close();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public boolean loadGame(File file){
        try {
            // Check if the file exists
            if (!file.exists()) {
                return false;
            }

            // Open the file for reading
            FileReader reader = new FileReader(file);
            BufferedReader br = new BufferedReader(reader);
            String[] arrPlayer = new String[6];
            String[] arrEatenFoods = new String[2];
            String[] arrFood = new String[3];
            String[] arrGameManager = new String[4];
            // Read the contents of the file line by line
            String line;

            //Players
            while ((line = br.readLine()) != null)
            {
                if(line.equals("EatenFoods"))
                {
                    break;
                }
                if(!line.equals("Players"))
                {
                    hmPlayers = new HashMap<>();
                    arrPlayer = line.split(",");
                    for (Specie alSpecy : alSpecies) {
                        if(alSpecy.getIdentifier() == arrPlayer[2].charAt(1))
                        {
                            Player player = new Player(Integer.parseInt(arrPlayer[0]),arrPlayer[1],alSpecy,Integer.parseInt(arrPlayer[3]),Integer.parseInt(arrPlayer[4]),Integer.parseInt(arrPlayer[5]));
                            hmPlayers.put(player.getIdentifier(), player);
                        }
                    }
                }
            }


            //Foods
            while ((line = br.readLine()) != null)
            {
                if(line.equals("Eaten Foods")){
                    break;
                }
                arrFood = line.split(",");
                if(arrFood[0].charAt(1) == 'c'){
                    for (House house : alHouses) {
                        if(house.getPosition() == Integer.parseInt(arrFood[1])){
                            Carne carne = new Carne('c', "Carne", "meat.png", 50,0,50, Integer.parseInt(arrFood[2]), Integer.parseInt(arrFood[1]));
                            gameFoods.add(carne);
                            house.food = carne;
                        }
                    }
                }else if(arrFood[0].charAt(1) == 'b'){
                    for (House house : alHouses) {
                        if(house.getPosition() == Integer.parseInt(arrFood[1])){
                            Banana banana = new Banana('b', "Cacho de Bananas", "bananas.png", 40, 40,40, Integer.parseInt(arrFood[2]), Integer.parseInt(arrFood[1]));
                            gameFoods.add(banana);
                            house.food = banana;
                        }
                    }
                } else if (arrFood[0].charAt(1) == 'a') {
                    for (House house : alHouses) {
                        if(house.getPosition() == Integer.parseInt(arrFood[1])){
                            Agua agua = new Agua('a', "Agua", "water.png", 15,20,20, Integer.parseInt(arrFood[1]));
                            gameFoods.add(agua);
                            house.food = agua;
                        }
                    }
                }else if(arrFood[0].charAt(1) == 'e'){
                    for (House house : alHouses) {
                        if(house.getPosition() == Integer.parseInt(arrFood[1])){
                            Erva erva = new Erva('e', "erva", "grass.png", 20,20,20,Integer.parseInt(arrFood[1]));
                            gameFoods.add(erva);
                            house.food = erva;
                        }
                    }

                }else if(arrFood[0].charAt(1) == 'm'){
                    for (House house : alHouses) {
                        if(house.getPosition() == Integer.parseInt(arrFood[1])){
                            Random r = new Random();
                            int low = 10;
                            int high = 51;
                            int result = r.nextInt(high-low) + low;
                            CogumelosMagicos cogumelo = new CogumelosMagicos('m', "Cogumelos magicos", "mushroom.png", result, result,result,Integer.parseInt(arrFood[1]));
                            gameFoods.add(cogumelo);
                            house.food = cogumelo;
                        }
                    }
                }
            }

            //EatenFoods
            while ((line = br.readLine()) != null)
            {
                if(line.equals("GameManager"))
                {
                    break;
                }
                arrEatenFoods = line.split(",");
                for (Food gameFood : gameFoods) {
                    if(gameFood.getIdentifier() == arrEatenFoods[1].charAt(1) && gameFood.getPosition() == Integer.parseInt(arrEatenFoods[2]))
                    {
                        hmPlayers.get(Integer.parseInt(arrEatenFoods[0])).eatenFoods.add(gameFood);
                    }
                }
            }

            //Gamemanager
            while ((line = br.readLine()) != null)
            {
                arrGameManager = line.split(",");
                jungleSize = Integer.parseInt(arrGameManager[1]);
                gameFinished = Boolean.parseBoolean(arrGameManager[0]);
                idPlayerPlaying = Integer.parseInt(arrGameManager[2]);
                playerPlaying = Integer.parseInt(arrGameManager[3]);
            }

            alSpecies = createDefaultSpecies();
            alFoods = createDefaultFoods();
            if(gameFoods.size() > 0){
                createInitialJungle(jungleSize, getPlayersInfo(),getFoodsInfo());
            }else{
                createInitialJungle(jungleSize,getPlayersInfo());
            }

            // Close the reader
            br.close();
            return true;
        } catch (IOException e) {
            return false;
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