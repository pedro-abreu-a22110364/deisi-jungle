package pt.ulusofona.lp2.deisiJungle;

import org.junit.Test;

import java.util.ArrayList;
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
        assertEquals("Pedro", matriz[0][1]);
        assertEquals("Z", matriz[0][2]);
        assertEquals("10", matriz[0][3]);
    }

    @Test
    public void testGetSquareInfo()
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
        String[] strs = manager.getSquareInfo(1);

        assertEquals("1,2",strs[2]);
    }

    @Test
    public void testGetPlayerInfo()
    {
        GameManager manager = new GameManager();
        String[][] playerInfo = new String[][]{
                {
                        "1","Pedro","Z","10"
                },
                {
                        "2","Guilherme","T","10"
                },
                {
                        "3","Tomas","L","10"
                },
                {
                        "4","Ricardo","E","10"
                }
        };
        manager.createInitialJungle(20, 10, playerInfo);
        String[] strs = manager.getPlayerInfo(2);

        assertEquals("T",strs[2]);
    }

    @Test
    public void testGetCurrentPlayerInfo()
    {
        GameManager manager = new GameManager();
        String[][] playerInfo = new String[][]{
                {
                        "1","Pedro","Z","10"
                },
                {
                        "2","Guilherme","T","10"
                },
                {
                        "3","Tomas","L","10"
                },
                {
                        "4","Ricardo","E","10"
                }
        };
        manager.createInitialJungle(20, 10, playerInfo);
        manager.moveCurrentPlayer(3,false);
        String[] strs = manager.getCurrentPlayerInfo();

        assertEquals("2",strs[0]);
    }

    @Test
    public void testGetGameResults()
    {
        GameManager manager = new GameManager();
        String[][] playerInfo = new String[][]{
                {
                        "1","Pedro","Z","10"
                },
                {
                        "2","Guilherme","T","10"
                },
                {
                        "3","Tomas","L","10"
                },
                {
                        "4","Ricardo","E","10"
                }
        };
        manager.createInitialJungle(20, 10, playerInfo);
        manager.moveCurrentPlayer(11,true);//Joga Pedro
        manager.moveCurrentPlayer(11,true);//Joga Gui
        manager.moveCurrentPlayer(17,true);//Joga Tomas
        manager.moveCurrentPlayer(17,true);//Joga Ricardo
        manager.getGameResults();
    }
}