package com.wikia.meownjik.shibenitsa;

import com.wikia.meownjik.shibenitsa.businesslogic.Game;
import com.wikia.meownjik.shibenitsa.businesslogic.Languages;

import org.junit.Assert;
import org.junit.Test;

public class GameTest {
    @Test
    public void testVictoriousGameScenario() {
        Game game = new Game(Languages.RUSSIAN, "Виселица", 5);

        Assert.assertFalse(game.isVictory());
        Assert.assertFalse(game.isFailure());

        game.tryLetter("а");
        Assert.assertEquals("*******а", game.getHiddenWord());
        Assert.assertEquals(5, game.getTrialsLeft());

        game.tryLetter("о");
        Assert.assertEquals("*******а", game.getHiddenWord());
        Assert.assertEquals(4, game.getTrialsLeft());

        game.tryLetter("и");
        Assert.assertEquals("*и***и*а", game.getHiddenWord());
        Assert.assertEquals(4, game.getTrialsLeft());

        game.tryLetter("е");
        Assert.assertEquals("*и*е*и*а", game.getHiddenWord());
        Assert.assertEquals(4, game.getTrialsLeft());

        Assert.assertFalse(game.isVictory());
        Assert.assertFalse(game.isFailure());

        game.tryLetter("в");
        Assert.assertEquals("ви*е*и*а", game.getHiddenWord());
        Assert.assertEquals(4, game.getTrialsLeft());

        game.tryLetter("н");
        Assert.assertEquals("ви*е*и*а", game.getHiddenWord());
        Assert.assertEquals(3, game.getTrialsLeft());

        game.tryLetter("с");
        Assert.assertEquals("висе*и*а", game.getHiddenWord());
        Assert.assertEquals(3, game.getTrialsLeft());

        game.tryLetter("л");
        Assert.assertEquals("висели*а", game.getHiddenWord());
        Assert.assertEquals(3, game.getTrialsLeft());

        game.tryLetter("ц");
        Assert.assertEquals("виселица", game.getHiddenWord());
        Assert.assertEquals(3, game.getTrialsLeft());

        Assert.assertTrue(game.isVictory());
    }
}
