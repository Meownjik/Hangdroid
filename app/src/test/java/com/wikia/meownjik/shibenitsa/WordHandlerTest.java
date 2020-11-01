package com.wikia.meownjik.shibenitsa;

import com.wikia.meownjik.shibenitsa.businesslogic.Languages;
import com.wikia.meownjik.shibenitsa.businesslogic.WordHandler;

import org.junit.Assert;
import org.junit.Test;

public class WordHandlerTest {
    @Test
    public void testLengthValidation() {
        WordHandler wordHandler = new WordHandler(Languages.RUSSIAN);

        Assert.assertTrue(wordHandler.validateLength("А"));
        Assert.assertTrue(wordHandler.validateLength("тест"));
        Assert.assertTrue(wordHandler.validateLength("Тест"));
        Assert.assertTrue(wordHandler.validateLength("Тест-тест+1"));
        Assert.assertTrue(wordHandler.validateLength("Тест-тест 1234567890 тест"));

        Assert.assertFalse(wordHandler.validateLength("йцукенгшщзхъэждлорпавыф"));
        Assert.assertFalse(wordHandler.validateLength(""));
        Assert.assertFalse(wordHandler.validateLength(" "));
        Assert.assertFalse(wordHandler.validateLength("123546789-0()<>|"));
    }

    @Test
    public void testWordHiding() {
        WordHandler wordHandler = new WordHandler(Languages.RUSSIAN);

        Assert.assertEquals("****", wordHandler.hide("Вася"));
        Assert.assertEquals("*****!", wordHandler.hide("Дурак!"));
        Assert.assertEquals("*** ****", wordHandler.hide("Это тест"));
    }

    @Test
    public void testWordUnhiding() {
        WordHandler wordHandler = new WordHandler(Languages.RUSSIAN);

        Assert.assertEquals("в***", wordHandler
                .unhide("Вася", "****", "в"));
        Assert.assertEquals("в*с*",
                wordHandler.unhide("Вася", "в***", "с"));
        Assert.assertEquals("а*а*а *а*а*а*а",
                wordHandler.unhide("Абаба галамага",
                        wordHandler.hide("Абаба галамага"), "а"));
    }
}
