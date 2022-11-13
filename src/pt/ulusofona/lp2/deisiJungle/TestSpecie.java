package pt.ulusofona.lp2.deisiJungle;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestSpecie {
    @Test
    public void testSpecieConstructor()
    {
        Specie specie = new Specie('O',"Ola","ola.png");

        assertEquals('O',specie.getIdentifier());
        assertEquals("Ola",specie.getName());
        assertEquals("ola.png",specie.getSpecieImage());
    }
}
