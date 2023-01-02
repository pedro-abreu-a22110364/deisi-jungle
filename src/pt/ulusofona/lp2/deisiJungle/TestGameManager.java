package pt.ulusofona.lp2.deisiJungle;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class TestGameManager {

    @Test
    public void testCreateInitialJungleNormal () {
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

        assertNull(game.createInitialJungle(8, players, foods));
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

        assertEquals("Invalid number of players",game.createInitialJungle(8, players, foods).getMessage());
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

        assertEquals("Incorrect id or name",game.createInitialJungle(8, players, foods).getMessage());
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

        assertEquals("Repeated ids found",game.createInitialJungle(8, players, foods).getMessage());
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

        assertEquals("Incorrect specie",game.createInitialJungle(8, players, foods).getMessage());
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

        assertEquals("Incorrect food found",game.createInitialJungle(8, players, foods).getMessage());
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

        assertEquals("Ilegal position for food",game.createInitialJungle(8, players, foods).getMessage());
    }

    @Test
    public void testMoveTarzanToGrass () {
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
    public void testMoveToTwoBananas () {
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
    public void testGetWinnerInfo () {
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
    }

    @Test
    public void testGetWinnerInfo3Players () {
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
    public void testGetWinnerInfoCombinedWithGetGameResultsFinishedInDistance () {
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

        game.createInitialJungle(13, players, foods);
        //jungleSize / 2 = 6.5

        game.moveCurrentPlayer(7,true);

        game.moveCurrentPlayer(2,true);

        game.moveCurrentPlayer(3,true);

        game.moveCurrentPlayer(4,true);

        assertEquals("Ricardo",game.getWinnerInfo()[1]);

        assertEquals("#1 Ricardo, Tartaruga, 4, 3, 0",game.getGameResults().get(0));
    }

    @Test
    public void testGetWinnerInfoCombinedWithGetGameResultsFinishedInFlag () {
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
    public void testEnergyAfterTwoBananas () {
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
    public void testGetSpecies () {
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
    public void testGetFoods () {
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
    public void testGetPlayerIds () {
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
    public void testGetPlayerIdsOutsideMapAndEmptyPlace () {
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
    public void testGetSquareInfoToolTips () {
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
}