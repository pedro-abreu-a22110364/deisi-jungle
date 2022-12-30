package pt.ulusofona.lp2.deisiJungle;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class TestGameManager {

    @Test
    public void testCreateInitialJungle () {
        String[][] players = new String[2][3];

        players[0][0] = "1";
        players[0][1] = "Pedro";
        players[0][2] = "E";
        players[1][0] = "2";
        players[1][1] = "Gui";
        players[1][2] = "L";

        String[][] foods = new String[1][2];

        foods[0][0] = "e";
        foods[0][1] = "2";

        GameManager game = new GameManager();

        game.createInitialJungle(4,players,foods);

        ArrayList<House> casas = game.getAlHouses();

        for (House casa : casas) {
            System.out.println(casa.getPosition());
        }
    }
}