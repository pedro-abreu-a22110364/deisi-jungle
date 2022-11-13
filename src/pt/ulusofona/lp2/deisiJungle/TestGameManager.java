package pt.ulusofona.lp2.deisiJungle;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class TestGameManager {
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
    public void testGetGameResultsWith4Players()
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
        manager.moveCurrentPlayer(3,true);//Joga Pedro
        manager.moveCurrentPlayer(11,true);//Joga Gui
        manager.moveCurrentPlayer(7,true);//Joga Tomas
        manager.moveCurrentPlayer(19,true);//Joga Ricardo
        ArrayList<String> strs = manager.getGameResults();

        assertEquals("#1 Ricardo, Elefante, 20",strs.get(0));
    }

    @Test
    public void testGetGameResultsWith3Players()
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
                }
        };
        manager.createInitialJungle(20, 10, playerInfo);
        manager.moveCurrentPlayer(3,true);//Joga Pedro
        manager.moveCurrentPlayer(11,true);//Joga Gui
        manager.moveCurrentPlayer(7,true);//Joga Tomas
        ArrayList<String> strs = manager.getGameResults();

        assertEquals("#1 Guilherme, Tartaruga, 12",strs.get(0));
    }

    @Test
    public void testMoveCurrentPlayerWithoutEnergy()
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
                }
        };
        manager.createInitialJungle(20, 1, playerInfo);
        manager.moveCurrentPlayer(3,true);//Joga Pedro
        manager.moveCurrentPlayer(11,true);//Joga Gui
        manager.moveCurrentPlayer(7,true);//Joga Tomas
        ArrayList<String> strs = manager.getGameResults();

        assertEquals("#1 Pedro, Tarzan, 1",strs.get(0));
    }

    @Test
    public void testMoveCurrentPlayerFullTurn()
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
                }
        };
        manager.createInitialJungle(20, 10, playerInfo);
        manager.moveCurrentPlayer(3,false);//Joga Pedro
        manager.moveCurrentPlayer(11,false);//Joga Gui
        manager.moveCurrentPlayer(7,false);//Joga Tomas
        ArrayList<String> strs = manager.getGameResults();

        assertEquals("#1 Pedro, Tarzan, 4",strs.get(0));
    }

    @Test
    public void testMoveCurrentPlayerMoraThan1Turn()
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
                }
        };
        manager.createInitialJungle(20, 10, playerInfo);
        manager.moveCurrentPlayer(3,true);//Joga Pedro
        manager.moveCurrentPlayer(11,true);//Joga Gui
        manager.moveCurrentPlayer(7,true);//Joga Tomas
        manager.moveCurrentPlayer(12,true);//Joga Pedro
        manager.moveCurrentPlayer(17,true);//Joga Gui
        manager.moveCurrentPlayer(4,true);//Joga Tomas
        ArrayList<String> strs = manager.getGameResults();

        assertEquals("#1 Guilherme, Tartaruga, 20",strs.get(0));
    }

    @Test
    public void testMoveCurrentPlayerMoraThan1TurnWithoutEnergy()
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
        manager.createInitialJungle(20, 6, playerInfo);
        manager.moveCurrentPlayer(3,false);//Joga Pedro
        manager.moveCurrentPlayer(6,false);//Joga Gui
        manager.moveCurrentPlayer(1,false);//Joga Pedro
        manager.moveCurrentPlayer(8,false);//Joga Gui
        manager.moveCurrentPlayer(6,false);//Joga Pedro
        manager.moveCurrentPlayer(2,false);//Joga Gui
        ArrayList<String> strs = manager.getGameResults();

        assertEquals("#1 Pedro, Tarzan, 11",strs.get(0));
        assertEquals("#2 Guilherme, Tartaruga, 9",strs.get(1));
    }
}