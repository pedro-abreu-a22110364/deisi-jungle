package pt.ulusofona.lp2.deisiJungle;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class TestGameManager {

    @Test
    public void testCreateInitialJungleWrongSpecie () {
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

        String[][] foods = new String[1][2];

        foods[0][0] = "e";
        foods[0][1] = "5";

        GameManager game = new GameManager();

        assertNull(game.createInitialJungle(8, players, foods));

        assertNull(game.createInitialJungle(8, players));
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

        System.out.println(Arrays.toString(game.getWinnerInfo()));

        assertEquals("3",game.getWinnerInfo()[0]);
    }

}