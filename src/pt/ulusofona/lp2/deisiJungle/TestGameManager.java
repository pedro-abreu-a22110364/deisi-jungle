package pt.ulusofona.lp2.deisiJungle;

import org.junit.Test;

import java.io.File;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class TestGameManager {


    @Test
    public void testCreateInitialJungleDoubleTarzan () {
        String[][] players = new String[2][3];

        players[0][0] = "2";
        players[0][1] = "Pedro";
        players[0][2] = "Z";
        players[1][0] = "1";
        players[1][1] = "Gui";
        players[1][2] = "Z";

        String[][] foods = new String[1][2];

        foods[0][0] = "e";
        foods[0][1] = "5";

        GameManager game = new GameManager();

        try {
            game.createInitialJungle(8, players, foods);
            fail("Deveria ter lançado uma exception");
        } catch (InvalidInitialJungleException e) {
            assertEquals("There is already a tarzan player", e.getMessage());
            assertTrue(e.isInvalidPlayer());
        }

        //assertEquals("",game.createInitialJungle(8, players, foods).getMessage());
    }

    @Test
    public void testCreateInitialJungleNormal () throws InvalidInitialJungleException {
        String[][] players = new String[3][3];

        players[0][0] = "1";
        players[0][1] = "Pedro";
        players[0][2] = "Z";
        players[1][0] = "2";
        players[1][1] = "Gui";
        players[1][2] = "L";
        players[2][0] = "3";
        players[2][1] = "Ricardo";
        players[2][2] = "P";

        String[][] foods = new String[3][2];

        foods[0][0] = "e";
        foods[0][1] = "5";
        foods[1][0] = "a";
        foods[1][1] = "4";
        foods[2][0] = "m";
        foods[2][1] = "6";

        GameManager game = new GameManager();

        game.createInitialJungle(8, players, foods);
    }

    @Test
    public void testCreateInitialJungleMinPlayers () {
        String[][] players = new String[1][3];

        players[0][0] = "1";
        players[0][1] = "Pedro";
        players[0][2] = "Z";

        String[][] foods = new String[1][2];

        foods[0][0] = "e";
        foods[0][1] = "5";

        GameManager game = new GameManager();

        try {
            game.createInitialJungle(8, players, foods);
            fail("Deveria ter lançado uma exception");
        } catch (InvalidInitialJungleException e) {
            assertEquals("Invalid number of players", e.getMessage());
            assertTrue(e.isInvalidPlayer());
        }
    }

    @Test
    public void testCreateInitialJungleIncorrectFormatID () {
        String[][] players = new String[2][3];

        players[0][0] = "h";
        players[0][1] = "Pedro";
        players[0][2] = "Z";
        players[1][0] = "2";
        players[1][1] = "Gui";
        players[1][2] = "G";

        String[][] foods = new String[1][2];

        foods[0][0] = "e";
        foods[0][1] = "5";

        GameManager game = new GameManager();

        try {
            game.createInitialJungle(8, players, foods);
            fail("Deveria ter lançado uma exception");

        } catch (InvalidInitialJungleException e) {
            assertEquals("Incorrect id or name",e.getMessage());
            assertTrue(e.isInvalidPlayer());

        }

    }

    @Test
    public void testCreateInitialJungleRepeatedIDs () {
        String[][] players = new String[2][3];

        players[0][0] = "1";
        players[0][1] = "Pedro";
        players[0][2] = "Z";
        players[1][0] = "1";
        players[1][1] = "Gui";
        players[1][2] = "L";

        String[][] foods = new String[1][2];

        foods[0][0] = "a";
        foods[0][1] = "4";

        GameManager game = new GameManager();

        try {
            game.createInitialJungle(8, players, foods);
            fail("Deveria ter lançado uma exception");
        } catch (InvalidInitialJungleException e) {
            assertEquals("Repeated ids found",e.getMessage());
            assertTrue(e.isInvalidPlayer());
        }

    }

    @Test
    public void testCreateInitialJungleIncorrectSpecie () {
        String[][] players = new String[2][3];

        players[0][0] = "1";
        players[0][1] = "Pedro";
        players[0][2] = "Z";
        players[1][0] = "2";
        players[1][1] = "Gui";
        players[1][2] = "O";

        String[][] foods = new String[1][2];

        foods[0][0] = "a";
        foods[0][1] = "3";

        GameManager game = new GameManager();

        try {
            game.createInitialJungle(8, players, foods);
            fail("Deveria ter lançado uma exception");
        } catch (InvalidInitialJungleException e) {
            assertEquals("Incorrect specie",e.getMessage());
            assertTrue(e.isInvalidPlayer());
        }
    }

    @Test
    public void testCreateInitialJungleIncorrectFood () {
        String[][] players = new String[2][3];

        players[0][0] = "1";
        players[0][1] = "Pedro";
        players[0][2] = "Z";
        players[1][0] = "2";
        players[1][1] = "Gui";
        players[1][2] = "T";

        String[][] foods = new String[1][2];

        foods[0][0] = "o";
        foods[0][1] = "3";

        GameManager game = new GameManager();

        try {
            game.createInitialJungle(8, players, foods);
            fail("Deveria ter lançado uma exception");
        } catch (InvalidInitialJungleException e) {
            assertEquals("Incorrect food found",e.getMessage());
            assertTrue(e.isInvalidFood());

        }
    }

    @Test
    public void testCreateInitialJungleIllegalFoodPosition () {
        String[][] players = new String[2][3];

        players[0][0] = "1";
        players[0][1] = "Pedro";
        players[0][2] = "Z";
        players[1][0] = "2";
        players[1][1] = "Gui";
        players[1][2] = "T";

        String[][] foods = new String[1][2];

        foods[0][0] = "a";
        foods[0][1] = "10";

        GameManager game = new GameManager();

        try {
            game.createInitialJungle(8, players, foods);
            fail("Deveria ter lançado uma exception");
        } catch (InvalidInitialJungleException e) {
            assertEquals("Ilegal position for food",e.getMessage());
            assertTrue(e.isInvalidFood());

        }
    }

    @Test
    public void testMoveLionInvalid () throws InvalidInitialJungleException {
        String[][] players = new String[2][3];

        players[0][0] = "2";
        players[0][1] = "Pedro";
        players[0][2] = "L";
        players[1][0] = "1";
        players[1][1] = "Gui";
        players[1][2] = "Z";

        String[][] foods = new String[1][2];

        foods[0][0] = "e";
        foods[0][1] = "5";

        GameManager game = new GameManager();

        game.createInitialJungle(8, players, foods);

        ArrayList<Player> player = game.getAlPlayer();

        game.moveCurrentPlayer(2,false);

        game.moveCurrentPlayer(2,false);

        assertEquals(3,game.invalidMove(3,false));

        game.moveCurrentPlayerAdd(-10);

        assertEquals(1,player.get(1).getPosition());
    }

    @Test
    public void testMoveTarzanToGrass () throws InvalidInitialJungleException {
        String[][] players = new String[2][3];

        players[0][0] = "1";
        players[0][1] = "Pedro";
        players[0][2] = "Z";
        players[1][0] = "2";
        players[1][1] = "Gui";
        players[1][2] = "L";

        String[][] foods = new String[1][2];

        foods[0][0] = "e";
        foods[0][1] = "5";

        GameManager game = new GameManager();

        game.createInitialJungle(8,players,foods);

        game.moveCurrentPlayer(4,false);

        ArrayList<Player> player = game.getAlPlayer();

        assertEquals(70-8+20,player.get(0).getEnergy());
    }

    @Test
    public void testMoveCarnivoroToGrassAndNoOmnivoroToWater () throws InvalidInitialJungleException {
        String[][] players = new String[2][3];

        players[0][0] = "1";
        players[0][1] = "Pedro";
        players[0][2] = "L";
        players[1][0] = "2";
        players[1][1] = "Gui";
        players[1][2] = "L";

        String[][] foods = new String[2][2];

        foods[0][0] = "e";
        foods[0][1] = "5";
        foods[1][0] = "a";
        foods[1][1] = "6";

        GameManager game = new GameManager();

        game.createInitialJungle(10,players,foods);

        ArrayList<Player> player = game.getAlPlayer();

        game.moveCurrentPlayer(4,true);

        assertEquals(80-28,player.get(0).getEnergy());

        game.moveCurrentPlayer(5,true);

        assertEquals(80-10+15,player.get(1).getEnergy());
    }

    @Test
    public void testMoveToTwoBananas () throws InvalidInitialJungleException {
        String[][] players = new String[2][3];

        players[0][0] = "1";
        players[0][1] = "Pedro";
        players[0][2] = "Z";
        players[1][0] = "2";
        players[1][1] = "Gui";
        players[1][2] = "L";

        String[][] foods = new String[2][2];

        foods[0][0] = "b";
        foods[0][1] = "5";
        foods[1][0] = "b";
        foods[1][1] = "7";

        GameManager game = new GameManager();

        game.createInitialJungle(10,players,foods);

        game.moveCurrentPlayer(4,false);

        game.moveCurrentPlayer(0,false);

        game.moveCurrentPlayer(2,false);

        ArrayList<Player> player = game.getAlPlayer();

        assertEquals(70-12+40-40,player.get(0).getEnergy());
    }

    @Test
    public void testGetWinnerInfo () throws InvalidInitialJungleException {
        String[][] players = new String[2][3];

        players[0][0] = "1";
        players[0][1] = "Pedro";
        players[0][2] = "Z";
        players[1][0] = "2";
        players[1][1] = "Gui";
        players[1][2] = "L";

        String[][] foods = new String[1][2];

        foods[0][0] = "e";
        foods[0][1] = "5";

        GameManager game = new GameManager();

        game.createInitialJungle(7, players, foods);

        game.moveCurrentPlayer(4,false);

        assertEquals("2",game.getWinnerInfo()[0]);

        game.moveCurrentPlayer(6,true);

        assertEquals(2,game.invalidMove(1,false));
    }

    @Test
    public void testGetWinnerInfoNull () throws InvalidInitialJungleException {
        String[][] players = new String[2][3];

        players[0][0] = "1";
        players[0][1] = "Pedro";
        players[0][2] = "Z";
        players[1][0] = "2";
        players[1][1] = "Gui";
        players[1][2] = "L";

        String[][] foods = new String[1][2];

        foods[0][0] = "e";
        foods[0][1] = "5";

        GameManager game = new GameManager();

        game.createInitialJungle(7, players, foods);

        game.moveCurrentPlayer(2,false);

        assertNull(game.getWinnerInfo());
    }

    @Test
    public void testGetWinnerInfo3Players () throws InvalidInitialJungleException {
        String[][] players = new String[3][3];

        players[0][0] = "1";
        players[0][1] = "Pedro";
        players[0][2] = "Z";
        players[1][0] = "2";
        players[1][1] = "Gui";
        players[1][2] = "L";
        players[2][0] = "3";
        players[2][1] = "Ricardo";
        players[2][2] = "T";

        String[][] foods = new String[2][2];

        foods[0][0] = "e";
        foods[0][1] = "5";
        foods[1][0] = "b";
        foods[1][1] = "8";

        GameManager game = new GameManager();

        game.createInitialJungle(11, players, foods);
        //jungleSize / 2 = 5.5

        game.moveCurrentPlayer(7,true);

        assertEquals("3",game.getWinnerInfo()[0]);
    }

    @Test
    public void testGetWinnerInfoCombinedWithGetGameResultsFinishedInDistance () throws InvalidInitialJungleException {
        String[][] players = new String[4][3];

        players[0][0] = "1";
        players[0][1] = "Pedro";
        players[0][2] = "Z";
        players[1][0] = "2";
        players[1][1] = "Gui";
        players[1][2] = "L";
        players[2][0] = "3";
        players[2][1] = "Ricardo";
        players[2][2] = "T";
        players[3][0] = "4";
        players[3][1] = "Tomas";
        players[3][2] = "Y";

        String[][] foods = new String[2][2];

        foods[0][0] = "e";
        foods[0][1] = "5";
        foods[1][0] = "b";
        foods[1][1] = "8";

        GameManager game = new GameManager();

        game.createInitialJungle(13, players, foods);
        //jungleSize / 2 = 6.5

        game.moveCurrentPlayer(7,true);

        game.moveCurrentPlayer(2,true);

        game.moveCurrentPlayer(3,true);

        game.moveCurrentPlayer(0,true);

        game.moveCurrentPlayer(4,true);

        assertEquals("Ricardo",game.getWinnerInfo()[1]);

        assertEquals("#1 Ricardo, Tartaruga, 4, 3, 0",game.getGameResults().get(0));
    }

    @Test
    public void testGetWinnerInfoCombinedWithGetGameResultsFinishedInFlag () throws InvalidInitialJungleException {
        String[][] players = new String[3][3];

        players[0][0] = "1";
        players[0][1] = "Pedro";
        players[0][2] = "Z";
        players[1][0] = "2";
        players[1][1] = "Gui";
        players[1][2] = "L";
        players[2][0] = "3";
        players[2][1] = "Ricardo";
        players[2][2] = "T";

        String[][] foods = new String[2][2];

        foods[0][0] = "e";
        foods[0][1] = "5";
        foods[1][0] = "b";
        foods[1][1] = "8";

        GameManager game = new GameManager();

        game.createInitialJungle(12, players, foods);
        //jungleSize / 2 = 6

        game.moveCurrentPlayer(7,true);

        game.moveCurrentPlayer(1,true);

        game.moveCurrentPlayer(2,true);

        game.moveCurrentPlayer(4,true);

        assertEquals("Pedro",game.getWinnerInfo()[1]);

        assertEquals("#1 Pedro, Tarzan, 12, 11, 1",game.getGameResults().get(0));
    }
    @Test
    public void testGetGameResults4PlayersFinishedInFlag () throws InvalidInitialJungleException {
        String[][] players = new String[4][3];

        players[0][0] = "1";
        players[0][1] = "Pedro";
        players[0][2] = "Z";
        players[1][0] = "2";
        players[1][1] = "Gui";
        players[1][2] = "L";
        players[2][0] = "3";
        players[2][1] = "Ricardo";
        players[2][2] = "T";
        players[3][0] = "4";
        players[3][1] = "Tomas";
        players[3][2] = "T";

        String[][] foods = new String[2][2];

        foods[0][0] = "e";
        foods[0][1] = "5";
        foods[1][0] = "b";
        foods[1][1] = "8";

        GameManager game = new GameManager();

        game.createInitialJungle(12, players, foods);
        //jungleSize / 2 = 6

        game.moveCurrentPlayer(7,true);

        game.moveCurrentPlayer(1,true);

        game.moveCurrentPlayer(3,true);

        game.moveCurrentPlayer(4,true);

        game.moveCurrentPlayer(4,true);

        assertEquals("Pedro",game.getWinnerInfo()[1]);

        assertEquals("#1 Pedro, Tarzan, 12, 11, 1",game.getGameResults().get(0));
    }

    @Test
    public void testGetGameResults2PlayersFinishedInFlag () throws InvalidInitialJungleException {
        String[][] players = new String[2][3];

        players[0][0] = "1";
        players[0][1] = "Pedro";
        players[0][2] = "Z";
        players[1][0] = "2";
        players[1][1] = "Gui";
        players[1][2] = "L";

        String[][] foods = new String[1][2];

        foods[0][0] = "a";
        foods[0][1] = "7";

        GameManager game = new GameManager();

        game.createInitialJungle(10, players, foods);
        //jungleSize / 2 = 6

        game.moveCurrentPlayer(5,true);

        game.moveCurrentPlayer(2,true);

        game.moveCurrentPlayer(4,true);

        game.getAuthorsPanel();
        game.whoIsTaborda();

        assertEquals("#1 Pedro, Tarzan, 10, 9, 0",game.getGameResults().get(0));
    }

    @Test
    public void testGetGameResultsDistance3Players () throws InvalidInitialJungleException {
        String[][] players = new String[3][3];

        players[0][0] = "1";
        players[0][1] = "Pedro";
        players[0][2] = "Z";
        players[1][0] = "2";
        players[1][1] = "Gui";
        players[1][2] = "L";
        players[2][0] = "3";
        players[2][1] = "Ricardo";
        players[2][2] = "T";

        String[][] foods = new String[2][2];

        foods[0][0] = "e";
        foods[0][1] = "5";
        foods[1][0] = "b";
        foods[1][1] = "8";

        GameManager game = new GameManager();

        game.createInitialJungle(12, players, foods);
        //jungleSize / 2 = 6

        game.moveCurrentPlayer(3,true);

        game.moveCurrentPlayer(1,true);

        game.moveCurrentPlayer(0,true);

        game.moveCurrentPlayer(7,true);

        assertEquals("#1 Gui, Leao, 2, 1, 0",game.getGameResults().get(0));
    }

    @Test
    public void testGetGameResultsDistance2Players () throws InvalidInitialJungleException {
        String[][] players = new String[2][3];

        players[0][0] = "1";
        players[0][1] = "Pedro";
        players[0][2] = "Z";
        players[1][0] = "2";
        players[1][1] = "Gui";
        players[1][2] = "L";

        String[][] foods = new String[1][2];

        foods[0][0] = "e";
        foods[0][1] = "5";

        GameManager game = new GameManager();

        game.createInitialJungle(12, players, foods);
        //jungleSize / 2 = 6

        game.moveCurrentPlayer(3,true);

        game.moveCurrentPlayer(1,true);

        game.moveCurrentPlayer(7,true);

        assertEquals("#1 Gui, Leao, 2, 1, 0",game.getGameResults().get(0));
    }

    @Test
    public void testEnergyAfterTwoBananas () throws InvalidInitialJungleException {
        String[][] players = new String[2][3];

        players[0][0] = "1";
        players[0][1] = "Pedro";
        players[0][2] = "Z";
        players[1][0] = "2";
        players[1][1] = "Gui";
        players[1][2] = "L";

        String[][] foods = new String[2][2];

        foods[0][0] = "b";
        foods[0][1] = "5";
        foods[1][0] = "b";
        foods[1][1] = "8";

        GameManager game = new GameManager();

        game.createInitialJungle(12, players, foods);

        game.moveCurrentPlayer(4,true);

        game.moveCurrentPlayer(1,true);

        game.moveCurrentPlayer(3,true);

        game.moveCurrentPlayer(1,true);

        String[] strings = game.getCurrentPlayerInfo();

        assertEquals("56",strings[3]);
    }

    @Test
    public void testGetSpecies () throws InvalidInitialJungleException {
        String[][] players = new String[2][3];

        players[0][0] = "1";
        players[0][1] = "Pedro";
        players[0][2] = "M";
        players[1][0] = "2";
        players[1][1] = "Gui";
        players[1][2] = "Y";

        String[][] foods = new String[1][2];

        foods[0][0] = "c";
        foods[0][1] = "5";

        GameManager game = new GameManager();

        game.createInitialJungle(12, players, foods);

        game.moveCurrentPlayer(2,true);

        game.moveCurrentPlayer(4,true);

        String[][] species = game.getSpecies();

        assertEquals("Elefante",species[0][1]);

        assertEquals("Leao",species[1][1]);

        assertEquals("Tartaruga",species[2][1]);

        assertEquals("Passaro",species[3][1]);

        assertEquals("Tarzan",species[4][1]);

        assertEquals("Mario",species[5][1]);

        assertEquals("PacMan",species[6][1]);

        assertEquals("Pikachu",species[7][1]);

        assertEquals("Zelda",species[8][1]);
    }

    @Test
    public void testGetFoods () throws InvalidInitialJungleException {
        String[][] players = new String[2][3];

        players[0][0] = "1";
        players[0][1] = "Pedro";
        players[0][2] = "G";
        players[1][0] = "2";
        players[1][1] = "Gui";
        players[1][2] = "X";

        String[][] foods = new String[1][2];

        foods[0][0] = "c";
        foods[0][1] = "5";

        GameManager game = new GameManager();

        game.createInitialJungle(12, players, foods);

        game.moveCurrentPlayer(2,true);

        game.moveCurrentPlayer(4,true);

        String[][] foodsType = game.getFoodTypes();

        assertEquals("Erva",foodsType[0][1]);

        assertEquals("Bananas",foodsType[1][1]);

        assertEquals("Carne",foodsType[2][1]);

        assertEquals("Agua",foodsType[3][1]);

        assertEquals("Cogumelos magicos",foodsType[4][1]);
    }

    @Test
    public void testGetPlayerIds () throws InvalidInitialJungleException {
        String[][] players = new String[2][3];

        players[0][0] = "1";
        players[0][1] = "Pedro";
        players[0][2] = "G";
        players[1][0] = "2";
        players[1][1] = "Gui";
        players[1][2] = "X";

        String[][] foods = new String[1][2];

        foods[0][0] = "c";
        foods[0][1] = "5";

        GameManager game = new GameManager();

        game.createInitialJungle(12, players, foods);

        game.moveCurrentPlayer(2,true);

        game.moveCurrentPlayer(1,true);

        int[] ids = game.getPlayerIds(3);

        assertEquals(1,ids[0]);

        game.moveCurrentPlayer(3,true);

        game.moveCurrentPlayer(1,true);

        ids = game.getPlayerIds(3);

        assertEquals(2,ids[0]);
    }

    @Test
    public void testGetPlayerIdsOutsideMapAndEmptyPlace () throws InvalidInitialJungleException {
        String[][] players = new String[2][3];

        players[0][0] = "1";
        players[0][1] = "Pedro";
        players[0][2] = "G";
        players[1][0] = "2";
        players[1][1] = "Gui";
        players[1][2] = "X";

        String[][] foods = new String[1][2];

        foods[0][0] = "c";
        foods[0][1] = "5";

        GameManager game = new GameManager();

        game.createInitialJungle(12, players, foods);

        game.moveCurrentPlayer(2,true);

        game.moveCurrentPlayer(1,true);

        int[] ids = game.getPlayerIds(15);

        game.moveCurrentPlayer(1,true);

        ids = game.getPlayerIds(3);

        game.moveCurrentPlayer(1,true);

        ids = game.getPlayerIds(3);

        assertEquals(2,ids[0]);
    }

    @Test
    public void testGetSquareInvalidPos () throws InvalidInitialJungleException {
        String[][] players = new String[2][3];

        players[0][0] = "1";
        players[0][1] = "Pedro";
        players[0][2] = "G";
        players[1][0] = "2";
        players[1][1] = "Gui";
        players[1][2] = "X";

        String[][] foods = new String[2][2];

        foods[0][0] = "e";
        foods[0][1] = "3";
        foods[1][0] = "a";
        foods[1][1] = "4";

        GameManager game = new GameManager();

        game.createInitialJungle(15, players, foods);

        assertNull(game.getSquareInfo(-1));
    }

    @Test
    public void testGetSquareInfoToolTips () throws InvalidInitialJungleException {
        String[][] players = new String[2][3];

        players[0][0] = "1";
        players[0][1] = "Pedro";
        players[0][2] = "G";
        players[1][0] = "2";
        players[1][1] = "Gui";
        players[1][2] = "X";

        String[][] foods = new String[5][2];

        foods[0][0] = "e";
        foods[0][1] = "3";
        foods[1][0] = "a";
        foods[1][1] = "4";
        foods[2][0] = "b";
        foods[2][1] = "5";
        foods[3][0] = "c";
        foods[3][1] = "6";
        foods[4][0] = "c";
        foods[4][1] = "9";

        GameManager game = new GameManager();

        game.createInitialJungle(15, players, foods);

        assertEquals("Vazio",game.getSquareInfo(1)[1]);

        assertEquals("Meta",game.getSquareInfo(15)[1]);

        assertEquals("Erva : +- 20 energia",game.getSquareInfo(3)[1]);

        assertEquals("Agua : + 15U|20% energia",game.getSquareInfo(4)[1]);

        assertEquals("Bananas : 3 : + 40 energia",game.getSquareInfo(5)[1]);

        assertEquals("Carne : + 50 energia : 0 jogadas",game.getSquareInfo(6)[1]);

        game.moveCurrentPlayer(0,true);
        game.moveCurrentPlayer(0,true);
        game.moveCurrentPlayer(0,true);
        game.moveCurrentPlayer(0,true);
        game.moveCurrentPlayer(0,true);
        game.moveCurrentPlayer(0,true);
        game.moveCurrentPlayer(0,true);
        game.moveCurrentPlayer(0,true);
        game.moveCurrentPlayer(0,true);
        game.moveCurrentPlayer(0,true);
        game.moveCurrentPlayer(0,true);
        game.moveCurrentPlayer(0,true);
        game.moveCurrentPlayer(0,true);

        assertEquals("Carne toxica",game.getSquareInfo(9)[1]);
    }

    @Test
    public void testMoveCurrentPlayerCompleteGrassAndWater () throws InvalidInitialJungleException {
        String[][] players = new String[2][3];

        players[0][0] = "1";
        players[0][1] = "Pedro";
        players[0][2] = "T";
        players[1][0] = "2";
        players[1][1] = "Gui";
        players[1][2] = "T";

        String[][] foods = new String[2][2];

        foods[0][0] = "e";
        foods[0][1] = "3";
        foods[1][0] = "a";
        foods[1][1] = "4";

        GameManager game = new GameManager();

        game.createInitialJungle(11,players,foods);

        ArrayList<Player> player = game.getAlPlayer();
        //1 - 150, 2 - 150

        game.moveCurrentPlayer(2,true);

        assertEquals(150 - 2 + 20,player.get(0).getEnergy());

        game.moveCurrentPlayer(3,true);

        assertEquals(176,player.get(1).getEnergy());
        //1 - 168, 2 - 176

        game.moveCurrentPlayer(0,true);

        assertEquals(168 + 5 + 20,player.get(0).getEnergy());

        game.moveCurrentPlayer(0,true);

        assertEquals(200,player.get(1).getEnergy());
    }

    @Test
    public void testMoveCurrentPlayerCompleteBananasAndMeat () throws InvalidInitialJungleException {
        String[][] players = new String[2][3];

        players[0][0] = "1";
        players[0][1] = "Pedro";
        players[0][2] = "T";
        players[1][0] = "2";
        players[1][1] = "Gui";
        players[1][2] = "T";

        String[][] foods = new String[2][2];

        foods[0][0] = "b";
        foods[0][1] = "3";
        foods[1][0] = "c";
        foods[1][1] = "4";

        GameManager game = new GameManager();

        game.createInitialJungle(11,players,foods);

        ArrayList<Player> player = game.getAlPlayer();
        //1 - 150, 2 - 150

        game.moveCurrentPlayer(2,true);

        assertEquals(150 - 2 + 40,player.get(0).getEnergy());

        game.moveCurrentPlayer(3,true);

        assertEquals(150 - 3 + 50,player.get(1).getEnergy());
        //1 - 188, 2 - 197

        game.moveCurrentPlayer(0,true);

        assertEquals(188 + 5 - 40,player.get(0).getEnergy());

        game.moveCurrentPlayer(1,true);

        game.moveCurrentPlayer(0,true);
        game.moveCurrentPlayer(0,true);
        game.moveCurrentPlayer(0,true);
        game.moveCurrentPlayer(0,true);
        game.moveCurrentPlayer(0,true);
        game.moveCurrentPlayer(0,true);
        game.moveCurrentPlayer(0,true);
        game.moveCurrentPlayer(0,true);
        game.moveCurrentPlayer(0,true);

        game.moveCurrentPlayer(-1,true);

        assertEquals(99,player.get(1).getEnergy());
    }

    @Test
    public void testMoveCurrentPlayerNoEnergy () throws InvalidInitialJungleException {
        String[][] players = new String[2][3];

        players[0][0] = "1";
        players[0][1] = "Pedro";
        players[0][2] = "T";
        players[1][0] = "2";
        players[1][1] = "Gui";
        players[1][2] = "T";

        String[][] foods = new String[2][2];

        foods[0][0] = "b";
        foods[0][1] = "3";
        foods[1][0] = "c";
        foods[1][1] = "4";

        GameManager game = new GameManager();

        game.createInitialJungle(11,players,foods);

        ArrayList<Player> player = game.getAlPlayer();

        player.get(0).removeEnergy(150);
        player.get(1).removeEnergy(149);

        game.moveCurrentPlayer(2,false);

        game.moveCurrentPlayer(-2,false);

        assertEquals(0,player.get(0).getEnergy());
        assertEquals(1,player.get(1).getEnergy());
    }

    @Test
    public void testMoveCurrentPlayerPassaroAndTartarugaIllegal () throws InvalidInitialJungleException {
        String[][] players = new String[2][3];

        players[0][0] = "1";
        players[0][1] = "Pedro";
        players[0][2] = "T";
        players[1][0] = "2";
        players[1][1] = "Gui";
        players[1][2] = "P";

        String[][] foods = new String[2][2];

        foods[0][0] = "b";
        foods[0][1] = "3";
        foods[1][0] = "c";
        foods[1][1] = "4";

        GameManager game = new GameManager();

        game.createInitialJungle(11, players, foods);

        ArrayList<Player> player = game.getAlPlayer();

        game.moveCurrentPlayer(4,false);

        game.moveCurrentPlayer(4,false);

        assertEquals(150,player.get(0).getEnergy());
        assertEquals(70,player.get(1).getEnergy());

        game.moveCurrentPlayer(-8,false);

        assertEquals(150,player.get(0).getEnergy());
    }

    @Test
    public void testMoveCurrentPlayerBackwards () throws InvalidInitialJungleException {
        String[][] players = new String[2][3];

        players[0][0] = "1";
        players[0][1] = "Pedro";
        players[0][2] = "T";
        players[1][0] = "2";
        players[1][1] = "Gui";
        players[1][2] = "P";

        String[][] foods = new String[2][2];

        foods[0][0] = "b";
        foods[0][1] = "3";
        foods[1][0] = "c";
        foods[1][1] = "4";

        GameManager game = new GameManager();

        game.createInitialJungle(11, players, foods);

        ArrayList<Player> player = game.getAlPlayer();

        game.moveCurrentPlayer(-4,false);

        assertEquals(1,player.get(0).getPosition());
    }

    @Test
    public void testSaveAndLoad () throws InvalidInitialJungleException {
        String[][] players = new String[2][3];

        players[0][0] = "1";
        players[0][1] = "Pedro";
        players[0][2] = "M";
        players[1][0] = "2";
        players[1][1] = "Gui";
        players[1][2] = "Y";

        String[][] foods = new String[5][2];

        foods[0][0] = "e";
        foods[0][1] = "3";
        foods[1][0] = "a";
        foods[1][1] = "4";
        foods[2][0] = "b";
        foods[2][1] = "5";
        foods[3][0] = "c";
        foods[3][1] = "6";
        foods[4][0] = "m";
        foods[4][1] = "7";

        GameManager game = new GameManager();

        game.createInitialJungle(11,players,foods);

        game.moveCurrentPlayer(2,true);

        File ficheiro = new File("save.txt");

        game.saveGame(ficheiro);

        players[0][0] = "1";
        players[0][1] = "Pedro";
        players[0][2] = "T";
        players[1][0] = "2";
        players[1][1] = "Gui";
        players[1][2] = "T";

        foods[0][0] = "b";
        foods[0][1] = "3";
        foods[1][0] = "c";
        foods[1][1] = "4";

        game.createInitialJungle(17,players,foods);

        game.loadGame(ficheiro);

        ArrayList<Player> player = game.getAlPlayer();

        assertEquals(3,player.get(0).getPosition());
    }

    @Test
    public void testCogumelosMagicosImpar () throws InvalidInitialJungleException {
        String[][] players = new String[2][3];

        players[0][0] = "1";
        players[0][1] = "Pedro";
        players[0][2] = "Z";
        players[1][0] = "2";
        players[1][1] = "Gui";
        players[1][2] = "Y";

        String[][] foods = new String[1][2];

        foods[0][0] = "m";
        foods[0][1] = "4";

        GameManager game = new GameManager();

        game.createInitialJungle(11,players,foods);

        ArrayList<Food> food = game.getGameFoods();

        ArrayList<Player> player = game.getAlPlayer();

        int energy1 = food.get(0).getEnergyOmnivoros();
        int energy2 = food.get(0).getEnergyCarnivoros();
        int energy3 = food.get(0).getEnergyHerbivoros();

        assertEquals(energy1,energy2);
        assertEquals(energy1,energy3);
        assertEquals(energy2,energy3);

        assertEquals("Cogumelo Magico : +- " + energy1 + "% energia",game.getSquareInfo(4)[1]);

        game.moveCurrentPlayer(3,true);

        int energyTemp = (int) (64 - (64 * (energy1 * 0.01))) +1;

        assertEquals(energyTemp,player.get(0).getEnergy());

        game.moveCurrentPlayer(0,true);

        game.moveCurrentPlayer(0,true);

        int energyTemp2 = (int) ((energyTemp + 20) - ((energyTemp + 20) * (energy1 * 0.01))) +1;

        assertEquals(energyTemp2,player.get(0).getEnergy());
    }

    @Test
    public void testCogumelosMagicosPar () throws InvalidInitialJungleException {
        String[][] players = new String[2][3];

        players[0][0] = "1";
        players[0][1] = "Pedro";
        players[0][2] = "T";
        players[1][0] = "2";
        players[1][1] = "Gui";
        players[1][2] = "Z";

        String[][] foods = new String[1][2];

        foods[0][0] = "m";
        foods[0][1] = "4";

        GameManager game = new GameManager();

        game.createInitialJungle(11,players,foods);

        ArrayList<Food> food = game.getGameFoods();

        ArrayList<Player> player = game.getAlPlayer();

        int energy1 = food.get(0).getEnergyOmnivoros();

        game.moveCurrentPlayer(0,true);

        game.moveCurrentPlayer(3,true);

        int energyTemp = (int) ((70 - 6) + (64 * (energy1 * 0.01)));

        assertEquals(energyTemp,player.get(1).getEnergy());

        game.moveCurrentPlayer(0,true);

        game.moveCurrentPlayer(0,true);

        int energyTemp2 = (int) ((energyTemp + 20) + ((energyTemp + 20) * (energy1 * 0.01)));

        assertEquals(energyTemp2,player.get(1).getEnergy());
    }

    @Test
    public void testGetCurrentPlayerEnergyInfo () throws InvalidInitialJungleException {
        String[][] players = new String[2][3];

        players[0][0] = "1";
        players[0][1] = "Pedro";
        players[0][2] = "T";
        players[1][0] = "2";
        players[1][1] = "Gui";
        players[1][2] = "T";

        String[][] foods = new String[1][2];

        foods[0][0] = "m";
        foods[0][1] = "4";

        GameManager game = new GameManager();

        game.createInitialJungle(11, players, foods);

        String[] strings1 = game.getCurrentPlayerEnergyInfo(3);
        String[] strings2 = game.getCurrentPlayerEnergyInfo(-3);

        assertEquals("3",strings1[0]);
        assertEquals("5",strings2[1]);
    }
}