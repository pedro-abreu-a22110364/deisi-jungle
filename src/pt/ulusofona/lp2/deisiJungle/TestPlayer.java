package pt.ulusofona.lp2.deisiJungle;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestPlayer {
    @Test
    public void testPlayerConstructor()
    {
        Player player = new Player(11,"Jose",new Specie('O',"Ola","ola.png"),10);

        assertEquals(11,player.getIdentifier());
        assertEquals("Jose",player.getName());
        assertEquals(new Specie('O',"Ola","ola.png").getName(),player.getSpecie().getName());
        assertEquals(10,player.getEnergy());
    }

    @Test
    public void testPlayerRemoveEnergy()
    {
        Player player = new Player(11,"Jose",new Specie('O',"Ola","ola.png"),10);

        player.removeEnergy(5);

        assertEquals(5,player.getEnergy());
    }

    @Test
    public void testPlayerSetRankAndPosition()
    {
        Player player = new Player(11,"Jose",new Specie('O',"Ola","ola.png"),10);

        player.setRank(3);
        player.setPosition(43);

        assertEquals(3,player.getRank());
        assertEquals(43,player.getPosition());
    }
}
