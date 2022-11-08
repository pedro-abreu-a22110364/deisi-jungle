package pt.ulusofona.lp2.deisiJungle;

import org.junit.Test;

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

}
