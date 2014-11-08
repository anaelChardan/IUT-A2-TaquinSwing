package TaquinTests;

import Taquin.Token;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.assertEquals;

/**
 * Created by Ananas on 08/11/14.
 */
public class TokenTest {
    Token token;


    @Test
    public void testTokenConstruct() {
        token = new Token(0,0,1, Color.blue);
        assertEquals(token.getBackColor(),Color.blue);
        assertEquals(token.getIndColumn(),0);
        assertEquals(token.getIndRow(),0);
        assertEquals(token.getNumber(),1);


    }

    @Test
    public void testGetBackColorIsWellPlaced() {
        token = new Token(0,0,1, Color.blue);
        token.setWellLocated(true);
        assertEquals(token.getBackColor(),Color.yellow);
    }

}
