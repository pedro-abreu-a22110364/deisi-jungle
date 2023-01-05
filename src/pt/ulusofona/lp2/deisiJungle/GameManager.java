package pt.ulusofona.lp2.deisiJungle;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;

public class GameManager {

    int minPlayers = 2;
    int maxPlayers = 4;
    int jungleSize;
    int nrPlays = 0;
    int nrPlaysMushrooms = 1;
    int distanceTotal = 0;

    //Variaveis com informação de players
    int winner = 0;
    int idPlayerPlaying;
    int playerPlaying;
    int posTemp = 1;

    Player playerMoving = new Player();

    //Variavel para contar quantidade de vezes que o bubble sort tem de ser feito
    int maxNumOfBSRepetions = 3;

    //Variaveis com informação sobre o jogo
    boolean gameFinished = false;

    int idPlayerPlayingTemp = 0;
    int playerPlayingTemp = 0;

    int[] orderByPosition;
    int[] orderByID;

    ArrayList<Player> alPlayer = new ArrayList<>();
    ArrayList<Player> alPlayerTemp = new ArrayList<>();
    ArrayList<Specie> alSpecies = createDefaultSpecies();
    ArrayList<Food> alFoods = createDefaultFoods();
    ArrayList<House> alHouses = new ArrayList<>();
    ArrayList<Food> gameFoods = new ArrayList<>();

    public GameManager(){

    }

    public ArrayList<Player> getAlPlayer() {
        return alPlayer;
    }

    public ArrayList<Food> getGameFoods() {
        return gameFoods;
    }

    public int getDistanceTotal() {
        return distanceTotal;
    }

    public int getPosTemp() {
        return posTemp;
    }

    public ArrayList<Specie> createDefaultSpecies() {
        ArrayList<Specie> alSpecies = new ArrayList<>(); //Creating the list to return it later

        //Creating the objects
        Elefante elefante = new Elefante('E', "Elefante","elephant.png", "Herbivoro",180, 4, 10, 1,6);
        Leao leao = new Leao('L', "Leao","lion.png","Carnivoro",80, 2, 10, 4,6);
        Tartaruga tartaruga = new Tartaruga('T', "Tartaruga","turtle.png", "Omnivoro",150,1,5,1,3);
        Passaro passaro = new Passaro('P', "Passaro","bird.png","Omnivoro",70,4,50,5,6);
        Tarzan tarzan = new Tarzan('Z', "Tarzan","tarzan.png", "Omnivoro",70,2,20,1,6);

        Mario mario = new Mario('M',"Mario","mario.png","Omnivoro",180,5,5,2,5);
        Ghost ghost = new Ghost('G',"PacMan","pacman.png","Omnivoro",120,1,10,1,3);
        Pikachu pikachu = new Pikachu('Y',"Pikachu","pikachu.png","Omnivoro",90,2,20,5,6);
        Zelda zelda = new Zelda('X',"Zelda","zelda.png","Omnivoro",120,3,15,3,5);

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
        alfood.add(agua);
        alfood.add(cogumelo);

        //Returning the list back to "main"
        return alfood;
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

    public void createInitialJungle(int jungleSize, String[][] playersInfo) throws InvalidInitialJungleException {
        cleaningArrays();

        this.jungleSize = jungleSize;
        int nrOfTarzans = 0;
        gameFoods = new ArrayList<>();
        alHouses = new ArrayList<>();


        //Validate number of players
        if(playersInfo == null || playersInfo.length < minPlayers || playersInfo.length > maxPlayers || jungleSize < playersInfo.length * 2) {
            throw new InvalidInitialJungleException("Invalid number of players", true, false);
        }

        //Validate incorrect ids and names
        for (String[] strings : playersInfo) {
            if (strings[0] == null || !strings[0].matches("[0-9]+") || strings[1] == null || strings[1].equals("")) {
                throw new InvalidInitialJungleException("Incorrect id or name", true, false);
            }
        }
        //Validate repeated ids
        for (int x = 0; x < playersInfo.length; x++) {
            for (int y = x + 1; y < playersInfo.length; y++) {
                if(Objects.equals(playersInfo[x][0], playersInfo[y][0])) {
                    throw new InvalidInitialJungleException("Repeated ids found", true, false);
                }
            }
        }
        //Validate incorrect species
        for (String[] strings : playersInfo) {
            if(strings[2] == null || !((strings[2].equals("E")) || (strings[2].equals("L")) || (strings[2].equals("T")) || (strings[2].equals("P")) || (strings[2].equals("Z")) || (strings[2].equals("M")) || (strings[2].equals("G")) || (strings[2].equals("Y")) || (strings[2].equals("X")))) {
                throw new InvalidInitialJungleException("Incorrect specie", true, false);
            }
        }
        //Creating players and adding them to the HashMaps
        for (String[] playerInfo : playersInfo) {
            for (Specie  specie : alSpecies) {
                if(playerInfo[2].charAt(0) == specie.getIdentifier()) {

                    if(playerInfo[2].equals(String.valueOf('Z')) && nrOfTarzans == 1) {
                        throw new InvalidInitialJungleException("There is already a tarzan player", true, false);
                    }

                    if(playerInfo[2].equals(String.valueOf('Z')) && nrOfTarzans < 1) {
                        nrOfTarzans ++;
                    }

                    Player player = new Player(Integer.parseInt(playerInfo[0]), playerInfo[1], specie, specie.getInitalEnergy(),0,1);
                    alPlayer.add(player);
                }
            }
        }

        if (!alPlayerTemp.isEmpty()) {
            alPlayer = alPlayerTemp;
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

        alPlayer.sort(Comparator.comparing(Player::getIdentifier));
        orderByPosition = new int[alPlayer.size()];
        orderByID = new int[alPlayer.size()];

        playerMoving = alPlayer.get(0);
        idPlayerPlaying = alPlayer.get(0).getIdentifier();
    }

    public void createInitialJungle(int jungleSize,String[][] playersInfo, String[][] foodsInfo) throws InvalidInitialJungleException {
        createInitialJungle(jungleSize, playersInfo);

        //Validate incorrect foods and incorrect positions for them
        for (String[] strings : foodsInfo) {
            if(strings[0] == null || !((strings[0].equals("e")) || (strings[0].equals("a")) || (strings[0].equals("b")) || (strings[0].equals("c")) || (strings[0].equals("m")))) {
                throw new InvalidInitialJungleException("Incorrect food found", false, true);
            }
            if(!(strings[1].matches("[0-9]+")) || Integer.parseInt(strings[1]) <= 1 || Integer.parseInt(strings[1]) >= jungleSize) {
                throw new InvalidInitialJungleException("Ilegal position for food", false, true);
            }
        }

        foodCreatingAndPlacing(foodsInfo);
    }

    public void foodCreatingAndPlacing (String[][] foodsInfo) {

        for (String[] strings : foodsInfo) {
            switch (strings[0]) {
                case "e" -> {
                    Erva erva = new Erva('e', "Erva", "grass.png", -20, 20, 20, Integer.parseInt(strings[1]));
                    gameFoods.add(erva);
                    for (House alHouse : alHouses) {
                        if (alHouse.getPosition() == Integer.parseInt(strings[1])) {
                            alHouse.colocarComida(erva);
                        }
                    }
                }
                case "a" -> {
                    Agua agua = new Agua('a', "Agua", "water.png", 15, 15, 20, Integer.parseInt(strings[1]));
                    gameFoods.add(agua);
                    for (House alHouse : alHouses) {
                        if (alHouse.getPosition() == Integer.parseInt(strings[1])) {
                            alHouse.colocarComida(agua);
                        }
                    }
                }
                case "b" -> {
                    Banana banana = new Banana('b', "Banana", "bananas.png", 40, 40, 40, 3, Integer.parseInt(strings[1]));
                    gameFoods.add(banana);
                    for (House alHouse : alHouses) {
                        if (alHouse.getPosition() == Integer.parseInt(strings[1])) {
                            alHouse.colocarComida(banana);
                        }
                    }
                }
                case "c" -> {
                    Carne carne = new Carne('c', "Carne", "meat.png", 50, 0, 50, Integer.parseInt(strings[1]));
                    gameFoods.add(carne);
                    for (House alHouse : alHouses) {
                        if (alHouse.getPosition() == Integer.parseInt(strings[1])) {
                            alHouse.colocarComida(carne);
                        }
                    }
                }
                case "m" -> {
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
        }
    }

    public void cleaningArrays() {
        alPlayer = new ArrayList<>();
        alHouses= new ArrayList<>();
        gameFoods= new ArrayList<>();
    }

    public int[] getPlayerIds(int squareNr) {
        int count = 0;
        int numberPlayers = 0;

        if (squareNr < 1 || squareNr > jungleSize) {
            return new int[0];
        }

        for (Player player : alPlayer) {
            if(player.getPosition() == squareNr) {
                numberPlayers++;
            }
        }
        int[] arrayID = new int[numberPlayers];

        for (Player player : alPlayer) {
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
                            strSquareInfo[1] = "Carne : + 50 energia : " + nrPlays + " jogadas";

                            if (nrPlays > 12) {
                                strSquareInfo[0] = "meat.png";
                                strSquareInfo[1] = "Carne toxica";
                            }
                        }
                        case 'm' -> {
                            strSquareInfo[0] = "mushroom.png";
                            strSquareInfo[1] = "Cogumelo Magico : +- " + house.getFood().getEnergyOmnivoros() + "% energia";
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

        for (Player player : alPlayer) {
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
        for (Player player : alPlayer) {
            if (player.getIdentifier() == playerId) {
                strPlayerInfo[0] = String.valueOf(player.getIdentifier());
                strPlayerInfo[1] = player.getName();
                strPlayerInfo[2] = String.valueOf(player.getSpecie().getIdentifier());
                strPlayerInfo[3] = String.valueOf(player.getEnergy());
                strPlayerInfo[4] = player.getSpecie().getMinSpeed() + ".." + player.getSpecie().getMaxSpeed() ;
            }
        }
        return strPlayerInfo;
    }

    public String[] getCurrentPlayerInfo() {
        int playerTemp = 0;
        for (Player player : alPlayer) {
            if (player.getIdentifier() == idPlayerPlaying) {
                playerTemp = idPlayerPlaying;
            }
        }
        return getPlayerInfo(playerTemp);
    }

    public String[][] getPlayersInfo() {
        String[][] strPlayerInfo = new String[alPlayer.size()][4];
        int count = 0;
        for (Player player : alPlayer) {
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
        String[][] strFoodInfo = new String[gameFoods.size()][2];
        int count = 0;
        for (Food food : gameFoods) {
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

        Player playerTemp = new Player();
        for (Player player : alPlayer) {
            if (player.getIdentifier() == idPlayerPlaying) {
                playerTemp = player;
            }
        }

        strPlayerEnergyInfo[0] = playerTemp.getSpecie().getNeededEnergy() * nrPositions + "";
        strPlayerEnergyInfo[1] = playerTemp.getSpecie().getEnergyRecovery() + "";

        return strPlayerEnergyInfo;
    }

    public MovementResult moveCurrentPlayer(int nrSquares,boolean bypassValidations) {
        for (Player player : alPlayer) {
            if (player.getIdentifier() == idPlayerPlaying) {
                playerMoving = player;
            }
        }

        int temp = invalidMove(nrSquares,bypassValidations);
        switch (temp) { case 1, 2, 3, 4, 5 -> {return new MovementResult(MovementResultCode.INVALID_MOVEMENT,null);}}

        //Verifica se todos os players n tem energia
        if (nrSquares < 0) {
            if(playerMoving.getEnergy() - playerMoving.getSpecie().getNeededEnergy() * (-1 * nrSquares) < 0) {
                posTemp = playerMoving.getPosition() + nrSquares;
                chacingTurnAndAddingNrPlays();
                return new MovementResult(MovementResultCode.NO_ENERGY,null);
            }
        } else if (nrSquares > 0) {
            if (playerMoving.getEnergy() - playerMoving.getSpecie().getNeededEnergy() * nrSquares < 0) {
                posTemp = playerMoving.getPosition() + nrSquares;
                chacingTurnAndAddingNrPlays();
                return new MovementResult(MovementResultCode.NO_ENERGY,null);
            }
        }

        if (nrSquares == 0) {
            playerMoving.addEnergy(playerMoving.getSpecie().getEnergyRecovery());
            eatMoreThan200();

            for (House house : alHouses) {
                if (playerMoving.getPosition() == house.getPosition()) {
                    if (house.getFood() != null) {
                        temp = switchCase(house);
                        switch (temp) {
                            case 1, 8, 6, 2 -> {return new MovementResult(MovementResultCode.CAUGHT_FOOD,"Apanhou " + house.getFood().getNome());}
                            case 3, 7 -> {return new MovementResult(MovementResultCode.VALID_MOVEMENT, null);}
                            case 4, 10, 9, 5 -> {return new MovementResult(MovementResultCode.CAUGHT_FOOD,"Apanhou " + house.getFood().getFoodType());}}
                    }
                }
            }

            chacingTurnAndAddingNrPlays();

            return new MovementResult(MovementResultCode.VALID_MOVEMENT,null);
        }

        moveCurrentPlayerAdd(nrSquares);

        for (House house : alHouses) {
            if (playerMoving.getPosition() == house.getPosition()) {
                if (house.getFood() != null) {
                    temp = switchCase(house);
                    switch (temp) {
                        case 1, 8, 6, 2 -> {return new MovementResult(MovementResultCode.CAUGHT_FOOD,"Apanhou " + house.getFood().getNome());}
                        case 3, 7 -> {return new MovementResult(MovementResultCode.VALID_MOVEMENT, null);}
                        case 4, 10, 9, 5 -> {return new MovementResult(MovementResultCode.CAUGHT_FOOD,"Apanhou " + house.getFood().getFoodType());}}
                }
            }
        }

        if(playerMoving.getPosition() >= jungleSize) {
            moveCurrentPlayerFinal();
            winner = idPlayerPlaying;gameFinished = true;nrPlays++;nrPlaysMushrooms++;
            chacingTurnAndAddingNrPlays();
            return new MovementResult(MovementResultCode.VALID_MOVEMENT,null);
        }

        chacingTurnAndAddingNrPlays();
        return new MovementResult(MovementResultCode.VALID_MOVEMENT,null);
    }

    public int invalidMove (int nrSquares, boolean bypassValidations) {
        if ((nrSquares < -6 || nrSquares > 6) && !bypassValidations) {
            chacingTurnAndAddingNrPlays();
            return 1;
        }

        //Verifica se o jogo já acabou
        if(winner == playerMoving.getIdentifier()) {
            chacingTurnAndAddingNrPlays();
            nrPlays++;
            nrPlaysMushrooms++;
            return 2;
        }

        //Verifica se o move é válido consoante o animal
        if (playerMoving.getSpecie().getIdentifier() == 'L' && nrSquares != -6 && nrSquares != -5 && nrSquares != -4 && nrSquares != 0 && nrSquares != 4 && nrSquares != 5 && nrSquares != 6 && !bypassValidations) {
            chacingTurnAndAddingNrPlays();
            return 3;
        }
        if (playerMoving.getSpecie().getIdentifier() == 'T' && nrSquares != -3 && nrSquares != -2 && nrSquares != -1 && nrSquares != 0 && nrSquares != 1 && nrSquares != 2 && nrSquares != 3 && !bypassValidations) {
            chacingTurnAndAddingNrPlays();
            return 4;
        }
        if (playerMoving.getSpecie().getIdentifier() == 'P' && nrSquares != -6 && nrSquares != -5 && nrSquares != 0 && nrSquares != 5 && nrSquares != 6 && !bypassValidations) {
            chacingTurnAndAddingNrPlays();
            return 5;
        }
        return 0;
    }

    public void moveCurrentPlayerFinal () {
        posTemp = playerMoving.getPosition();
        playerMoving.setPosition(jungleSize);
    }

    public void moveCurrentPlayerAdd (int nrSquares) {
        posTemp = playerMoving.getPosition();
        playerMoving.setPosition(playerMoving.getPosition() + nrSquares);
        increaseDistance(nrSquares);

        if (nrSquares < 0) {
            playerMoving.removeEnergy(playerMoving.getSpecie().getNeededEnergy() * ((-1) * nrSquares));
        } else {
            playerMoving.removeEnergy(playerMoving.getSpecie().getNeededEnergy() * nrSquares);
        }

        if (playerMoving.getPosition() <= 0) {
            posTemp = playerMoving.getPosition();
            playerMoving.setPosition(1);
        }
    }

    public void eatMoreThan200() {
        if (playerMoving.getEnergy() > 200) {
            playerMoving.setEnergy(200);
        }
    }

    public void chacingTurnAndAddingNrPlays() {
        if(idPlayerPlaying == alPlayer.get(alPlayer.size() - 1).getIdentifier()) {
            playerPlaying = 0;
            idPlayerPlaying = alPlayer.get(0).getIdentifier();
        } else {
            playerPlaying++;
            idPlayerPlaying = alPlayer.get(playerPlaying).getIdentifier();
        }
        nrPlays++;
        nrPlaysMushrooms++;
    }

    public void increaseDistance (int nrSquares) {
        if (nrSquares < 0) {
            playerMoving.increseDistance(nrSquares * (-1));
            distanceTotal += (nrSquares * (-1));
        } else if (nrSquares > 0) {
            playerMoving.increseDistance(nrSquares);
            distanceTotal += nrSquares;
        }
    }

    public int switchCase (House house) {
        switch (house.getFood().getIdentifier()) {
            case 'e' -> {
                caseErva();
                playerMoving.addEatenFoods(house.getFood());
                chacingTurnAndAddingNrPlays();return 1;
            } case 'a' -> {
                caseAgua();
                playerMoving.addEatenFoods(house.getFood());
                chacingTurnAndAddingNrPlays();return 2;
            } case 'b' -> {
                if (((Banana) house.getFood()).getQuantidade() <= 0) {
                    chacingTurnAndAddingNrPlays();return 3;}

                if (playerMoving.getNrBananas() >= 1) {

                    playerMoving.removeEnergy(40);
                    ((Banana) house.getFood()).removeQuantidade();
                    playerMoving.comerBananas();
                    playerMoving.addEatenFoods(house.getFood());

                    chacingTurnAndAddingNrPlays();return 4;}

                playerMoving.addEnergy(40);
                ((Banana) house.getFood()).removeQuantidade();
                playerMoving.comerBananas();
                playerMoving.addEatenFoods(house.getFood());

                eatMoreThan200();
                chacingTurnAndAddingNrPlays();return 5;
            } case 'c' -> {
                if (nrPlays >= 12) {
                    playerMoving.halfEnergy();
                    playerMoving.addEatenFoods(house.getFood());
                    chacingTurnAndAddingNrPlays();return 6;}

                if (Objects.equals(playerMoving.getSpecie().getSpecieType(), "Herbivoro")) {
                    chacingTurnAndAddingNrPlays();return 7;}

                playerMoving.addEnergy(50);
                playerMoving.addEatenFoods(house.getFood());

                eatMoreThan200();
                chacingTurnAndAddingNrPlays();return 8;
            } case 'm' -> {
                if (nrPlaysMushrooms % 2 == 0) {
                    playerMoving.percentageEnergy(house.getFood().getEnergyOmnivoros());
                    playerMoving.addEatenFoods(house.getFood());
                    eatMoreThan200();
                    chacingTurnAndAddingNrPlays();return 9;
                } else {
                    playerMoving.percentageEnergyNegative(house.getFood().getEnergyOmnivoros());
                    playerMoving.addEatenFoods(house.getFood());
                    chacingTurnAndAddingNrPlays();return 10;
                }
            }
        }
        return 0;
    }

    public void caseErva () {
        if (Objects.equals(playerMoving.getSpecie().getSpecieType(), "Carnivoro")) {
            playerMoving.removeEnergy(20);
        } else {
            playerMoving.addEnergy(20);
            eatMoreThan200();
        }
    }

    public void caseAgua () {
        if (Objects.equals(playerMoving.getSpecie().getSpecieType(), "Omnivoro")) {
            playerMoving.percentageEnergy(20);
            eatMoreThan200();
        } else {
            playerMoving.addEnergy(15);
            eatMoreThan200();
        }
    }

    public String[] getWinnerInfo() {

        sortArrayByPosition();

        for (Player player : alPlayer) {
            if (player.getIdentifier() == winner) {
                return getPlayerInfo(winner);
            }
        }

        if (alPlayer.size() == 2) {
            if (orderByPosition[1] - orderByPosition[0] >= jungleSize/2) {
                return getPlayerInfo(orderByID[0]);
            }
        }

        if (alPlayer.size() == 3) {
            if (orderByPosition[2] - orderByPosition[1] >= jungleSize/2) {
                return getPlayerInfo(orderByID[1]);
            }
        }

        if (alPlayer.size() == 4) {
            if (orderByPosition[3] - orderByPosition[2] >= jungleSize/2) {
                return getPlayerInfo(orderByID[2]);
            }
        }

        return null;
    }

    public ArrayList<String> getGameResults() {
        Player playerPos0 = new Player();
        Player playerPos1 = new Player();
        Player playerPos2 = new Player();
        Player playerPos3 = new Player();
        ArrayList<String> alGameResults = new ArrayList<>();

        getRanking();

        for (Player player : alPlayer) {
            if (player.getIdentifier() == orderByID[0]) {
                playerPos0 = player;
            } else if (player.getIdentifier() == orderByID[1]) {
                playerPos1 = player;
            } else if (player.getIdentifier() == orderByID[2]) {
                playerPos2 = player;
            } else if (player.getIdentifier() == orderByID[3]) {
                playerPos3 = player;
            }
        }

        if(alPlayer.size() == 4) {
            if (orderByPosition[3] != jungleSize) {
                if (orderByPosition[3] - orderByPosition[2] >= jungleSize/2) {
                    alGameResults.add("#1 " + playerPos2.getName() + ", " + playerPos2.getSpecie().getName() + ", " + playerPos2.getPosition() + ", " + playerPos2.getDistance() + ", " + playerPos2.getEatenFoods().size());
                    alGameResults.add("#2 " + playerPos3.getName() + ", " + playerPos3.getSpecie().getName() + ", " + playerPos3.getPosition() + ", " + playerPos3.getDistance() + ", " + playerPos3.getEatenFoods().size());
                    alGameResults.add("#" + playerPos1.getRank() + " " + playerPos1.getName() + ", " + playerPos1.getSpecie().getName() + ", " + playerPos1.getPosition() + ", " + playerPos1.getDistance() + ", " + playerPos1.getEatenFoods().size());
                    alGameResults.add("#" + playerPos0.getRank() + " " + playerPos0.getName() + ", " + playerPos0.getSpecie().getName() + ", " + playerPos0.getPosition() + ", " + playerPos0.getDistance() + ", " + playerPos0.getEatenFoods().size());
                    return alGameResults;
                }
            }

            alGameResults.add("#" + playerPos3.getRank() + " " + playerPos3.getName() + ", " + playerPos3.getSpecie().getName() + ", " + playerPos3.getPosition() + ", " + playerPos3.getDistance() + ", " + playerPos3.getEatenFoods().size());
            alGameResults.add("#" + playerPos2.getRank() + " " + playerPos2.getName() + ", " + playerPos2.getSpecie().getName() + ", " + playerPos2.getPosition() + ", " + playerPos2.getDistance() + ", " + playerPos2.getEatenFoods().size());
            alGameResults.add("#" + playerPos1.getRank() + " " + playerPos1.getName() + ", " + playerPos1.getSpecie().getName() + ", " + playerPos1.getPosition() + ", " + playerPos1.getDistance() + ", " + playerPos1.getEatenFoods().size());
            alGameResults.add("#" + playerPos0.getRank() + " " + playerPos0.getName() + ", " + playerPos0.getSpecie().getName() + ", " + playerPos0.getPosition() + ", " + playerPos0.getDistance() + ", " + playerPos0.getEatenFoods().size());
        }

        if(alPlayer.size() == 3) {
            if (orderByPosition[2] != jungleSize) {
                if (orderByPosition[2] - orderByPosition[1] >= jungleSize/2) {
                    alGameResults.add("#1 " + playerPos1.getName() + ", " + playerPos1.getSpecie().getName() + ", " + playerPos1.getPosition() + ", " + playerPos1.getDistance() + ", " + playerPos1.getEatenFoods().size());
                    alGameResults.add("#2 " + playerPos2.getName() + ", " + playerPos2.getSpecie().getName() + ", " + playerPos2.getPosition() + ", " + playerPos2.getDistance() + ", " + playerPos2.getEatenFoods().size());
                    alGameResults.add("#" + playerPos0.getRank() + " " + playerPos0.getName() + ", " + playerPos0.getSpecie().getName() + ", " + playerPos0.getPosition() + ", " + playerPos0.getDistance() + ", " + playerPos0.getEatenFoods().size());
                    return alGameResults;
                }
            }

            alGameResults.add("#" + playerPos2.getRank() + " " + playerPos2.getName() + ", " + playerPos2.getSpecie().getName() + ", " + playerPos2.getPosition() + ", " + playerPos2.getDistance() + ", " + playerPos2.getEatenFoods().size());
            alGameResults.add("#" + playerPos1.getRank() + " " + playerPos1.getName() + ", " + playerPos1.getSpecie().getName() + ", " + playerPos1.getPosition() + ", " + playerPos1.getDistance() + ", " + playerPos1.getEatenFoods().size());
            alGameResults.add("#" + playerPos0.getRank() + " " + playerPos0.getName() + ", " + playerPos0.getSpecie().getName() + ", " + playerPos0.getPosition() + ", " + playerPos0.getDistance() + ", " + playerPos0.getEatenFoods().size());
        }

        if(alPlayer.size() == 2) {
            if (orderByPosition[1] != jungleSize) {
                if (orderByPosition[1] - orderByPosition[0] >= jungleSize/2) {
                    alGameResults.add("#1 " + playerPos0.getName() + ", " + playerPos0.getSpecie().getName() + ", " + playerPos0.getPosition() + ", " + playerPos0.getDistance() + ", " + playerPos0.getEatenFoods().size());
                    alGameResults.add("#2 " + playerPos1.getName() + ", " + playerPos1.getSpecie().getName() + ", " + playerPos1.getPosition() + ", " + playerPos1.getDistance() + ", " + playerPos1.getEatenFoods().size());
                    return alGameResults;
                }
            }

            alGameResults.add("#" + playerPos1.getRank() + " " + playerPos1.getName() + ", " + playerPos1.getSpecie().getName() + ", " + playerPos1.getPosition() + ", " + playerPos1.getDistance() + ", " + playerPos1.getEatenFoods().size());
            alGameResults.add("#" + playerPos0.getRank() + " " + playerPos0.getName() + ", " + playerPos0.getSpecie().getName() + ", " + playerPos0.getPosition() + ", " + playerPos0.getDistance() + ", " + playerPos0.getEatenFoods().size());
        }

        return alGameResults;
    }

    public void getRanking() {

        sortArrayByPosition();
        int count = 0,rank = alPlayer.size();

        while (count != maxNumOfBSRepetions) {
            sortArrayByPositionWithEqualID();
            count++;
        }

        for (int i : orderByID) {
            for (Player player : alPlayer) {
                if (player.getIdentifier() == i) {
                    player.setRank(rank);
                    rank--;
                }
            }
        }
    }

    public void sortArrayByPosition () {
        int count = 0, lastOrdered = orderByPosition.length;
        boolean allInOrder = false;

        for (Player player : alPlayer) {
            orderByPosition[count] = player.getPosition();
            orderByID[count] = player.getIdentifier();
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

    public boolean saveGame(File file){
        try {
            //Check if files does not exists and if not, creates it
            if (!file.exists()) {file.createNewFile();}
            // Open the file for writing
            FileWriter writer = new FileWriter(file.getName());
            BufferedWriter bw = new BufferedWriter(writer);
            //Cleans File first.
            bw.write("");
            bw.write("Players\n");
            for (Player player : alPlayer) {
                bw.write(player.getIdentifier() + "," + player.getName() + "," + player.getSpecie().getIdentifier() + "," + player.getEnergy() + "," + player.getRank() + "," + player.getPosition());
                bw.newLine();
            }//--------------------------//
            bw.write("Food\n");
            for (Food food : gameFoods) {
                if(food.getIdentifier() == 'c'){bw.write(food.getIdentifier() + "," + food.getPosition());}
                else if(food.getIdentifier() == 'b'){bw.write(food.getIdentifier() + "," + food.getPosition() + "," + ((Banana) food).getQuantidade());}
                else{bw.write(food.getIdentifier() + "," + food.getPosition());}
                bw.newLine();
            }//--------------------------//
            bw.write("Eaten Foods\n");
            for (Player player : alPlayer) {
                for (Food eatenFood : player.getEatenFoods()) {
                    bw.write(player.getIdentifier() + "," + eatenFood.getIdentifier() + "," + eatenFood.getPosition());
                    bw.newLine();
                }
            }//--------------------------//
            bw.write("GameManager\n");
            bw.write(gameFinished + "," + jungleSize + "," + idPlayerPlaying + "," + playerPlaying + "," + nrPlays);
            // Close the writer to save the changes
            bw.close();
            return true;
        } catch (IOException e) {return false;}
    }

    public boolean loadGame(File file) throws InvalidInitialJungleException{
        gameFoods = new ArrayList<>();
        try {alPlayerTemp = new ArrayList<>();
            // Check if the file exists
            if (!file.exists()) {return false;}
            // Open the file for reading
            FileReader reader = new FileReader(file);
            BufferedReader br = new BufferedReader(reader);
            String[] arrPlayer, arrEatenFoods, arrFood, arrGameManager;
            // Read the contents of the file line by line
            String line;
            //Players
            while ((line = br.readLine()) != null) {
                if(line.equals("Food")) {break;} if(!line.equals("Players")) {arrPlayer = line.split(",");for (Specie alSpecy : alSpecies) {if(alSpecy.getIdentifier() == arrPlayer[2].charAt(0)) {
                            Player player = new Player(Integer.parseInt(arrPlayer[0]),arrPlayer[1],alSpecy,Integer.parseInt(arrPlayer[3]),Integer.parseInt(arrPlayer[4]),Integer.parseInt(arrPlayer[5]));
                            alPlayerTemp.add(player);}}}
            }
            //Foods
            while ((line = br.readLine()) != null) {if(line.equals("Eaten Foods")){break;}
                arrFood = line.split(",");
                switch (arrFood[0].charAt(0)) {
                    case 'c': for (House house : alHouses) {if (house.getPosition() == Integer.parseInt(arrFood[1])) {
                                Carne carne = new Carne('c', "Carne", "meat.png", 50, 0, 50, Integer.parseInt(arrFood[1]));
                                gameFoods.add(carne);
                                house.food = carne; }}break;
                    case 'b': for (House house : alHouses) {if (house.getPosition() == Integer.parseInt(arrFood[1])) {
                                Banana banana = new Banana('b', "Cacho de Bananas", "bananas.png", 40, 40, 40, Integer.parseInt(arrFood[2]), Integer.parseInt(arrFood[1]));
                                gameFoods.add(banana);
                                house.food = banana; }}break;
                    case 'a': for (House house : alHouses) {if (house.getPosition() == Integer.parseInt(arrFood[1])) {
                                Agua agua = new Agua('a', "Agua", "water.png", 15, 20, 20, Integer.parseInt(arrFood[1]));
                                gameFoods.add(agua);
                                house.food = agua; }}break;
                    case 'e': for (House house : alHouses) { if (house.getPosition() == Integer.parseInt(arrFood[1])) {
                                Erva erva = new Erva('e', "erva", "grass.png", 20, 20, 20, Integer.parseInt(arrFood[1]));
                                gameFoods.add(erva);
                                house.food = erva; }}break;
                    case 'm': for (House house : alHouses) { if (house.getPosition() == Integer.parseInt(arrFood[1])) {
                                Random r = new Random();
                                int low = 10, high = 51;
                                int result = r.nextInt(high - low) + low;
                                CogumelosMagicos cogumelo = new CogumelosMagicos('m', "Cogumelos magicos", "mushroom.png", result, result, result, Integer.parseInt(arrFood[1]));
                                gameFoods.add(cogumelo);
                                house.food = cogumelo; }}break;
                }
            }
            //EatenFoods
            while ((line = br.readLine()) != null) {if(line.equals("GameManager")) {break;}
                arrEatenFoods = line.split(",");
                for (Food gameFood : gameFoods) {
                    if(gameFood.getIdentifier() == arrEatenFoods[1].charAt(0) && gameFood.getPosition() == Integer.parseInt(arrEatenFoods[2])) {
                        for (Player player : alPlayerTemp) {
                            if (player.getIdentifier() == Integer.parseInt(arrEatenFoods[0])) {
                                player.addEatenFoods(gameFood);
                            }}}}}

            //Gamemanager
            while ((line = br.readLine()) != null) { arrGameManager = line.split(",");
                jungleSize = Integer.parseInt(arrGameManager[1]);
                gameFinished = Boolean.parseBoolean(arrGameManager[0]);
                idPlayerPlayingTemp = Integer.parseInt(arrGameManager[2]);
                playerPlayingTemp = Integer.parseInt(arrGameManager[3]);
                nrPlays = Integer.parseInt(arrGameManager[4]);}
            startGame();
            br.close();
            return true;
        } catch (IOException e) {return false;}
    }

    public void startGame() throws InvalidInitialJungleException {
        alSpecies = createDefaultSpecies();
        alFoods = createDefaultFoods();
        alPlayer = alPlayerTemp;
        if(gameFoods.size() > 0){createInitialJungle(jungleSize, getPlayersInfo(),getFoodsInfo());}
        else {createInitialJungle(jungleSize,getPlayersInfo());}
        idPlayerPlaying = idPlayerPlayingTemp;
        playerPlaying = playerPlayingTemp;
    }

    public JPanel getAuthorsPanel() {
        JPanel jPanel = new JPanel();

        jPanel.setBackground(Color.YELLOW);

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
}