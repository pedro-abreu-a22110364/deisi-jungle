package pt.ulusofona.lp2.deisiJungle;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class TestGameManager {

    @Test
    public void testCreateInitialJungleWrongSpecie () {
        String[][] players = new String[2][3];

        players[0][0] = "1";
        players[0][1] = "Pedro";
        players[0][2] = "A";
        players[1][0] = "2";
        players[1][1] = "Gui";
        players[1][2] = "L";

        String[][] foods = new String[1][2];

        foods[0][0] = "z";
        foods[0][1] = "5";

        GameManager game = new GameManager();

        assertEquals("Incorrect specie",game.createInitialJungle(8,players,foods).getMessage());
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
    public void testMoveTarzanToMushroom () {
        String[][] players = new String[2][3];

        players[0][0] = "1";
        players[0][1] = "Pedro";
        players[0][2] = "Z";
        players[1][0] = "2";
        players[1][1] = "Gui";
        players[1][2] = "L";

        String[][] foods = new String[1][2];

        foods[0][0] = "m";
        foods[0][1] = "5";

        GameManager game = new GameManager();

        game.createInitialJungle(8,players,foods);

        ArrayList<Player> player = game.getAlPlayer();

        ArrayList<Food> food = game.getAlFoods();

        game.moveCurrentPlayer(4,false);
    }

}