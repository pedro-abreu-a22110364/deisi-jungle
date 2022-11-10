package pt.ulusofona.lp2.deisiJungle;

import org.junit.Test;

import java.util.Objects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class TestGame {
    @Test
    public void testCreateJungle()
    {
        GameManager manager = new GameManager();
        String[][] playerInfo = new String[][]{
                {
                    "1","Pedro","Z","10"
                },
                {
                    "2","Guilherme","T","10"
                }
        };
        manager.createInitialJungle(20, 10, playerInfo);

    }

    @Test
    public void testGetPlayersInfo()
    {
        GameManager manager = new GameManager();
        String[][] playerInfo = new String[][]{
                {
                        "1","Pedro","Z","10"
                },
                {
                        "2","Guilherme","T","10"
                }
        };
        manager.createInitialJungle(20, 10, playerInfo);
        String[][] matriz = manager.getPlayersInfo();

        assertEquals("1", matriz[0][0]);
    }

}
