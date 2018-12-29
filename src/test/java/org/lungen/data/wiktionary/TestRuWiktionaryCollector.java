package org.lungen.data.wiktionary;

import java.io.File;
import java.nio.charset.Charset;
import java.util.List;
import java.util.stream.IntStream;

import org.jsoup.Connection;
import org.junit.Assert;
import org.junit.Test;

public class TestRuWiktionaryCollector {

    @Test
    public void testExampleWords() {
        RuWiktionaryCollector collector = new RuWiktionaryCollector();
        Connection connection = collector.connect();
        Assert.assertEquals(RuWiktionaryPartOfSpeech.NOUN, collector.getWordInfo(connection, "книга").getPartOfSpeech());
        Assert.assertEquals(RuWiktionaryPartOfSpeech.ADJECTIVE, collector.getWordInfo(connection, "красивый").getPartOfSpeech());
        Assert.assertEquals(RuWiktionaryPartOfSpeech.ADVERB, collector.getWordInfo(connection, "быстро").getPartOfSpeech());
        Assert.assertEquals(RuWiktionaryPartOfSpeech.PRONOUN, collector.getWordInfo(connection, "оно").getPartOfSpeech());
        Assert.assertEquals(RuWiktionaryPartOfSpeech.PREPOSITION, collector.getWordInfo(connection, "на").getPartOfSpeech());
        Assert.assertEquals(RuWiktionaryPartOfSpeech.VERB, collector.getWordInfo(connection, "делать").getPartOfSpeech());
        Assert.assertEquals(RuWiktionaryPartOfSpeech.ADJECTIVE, collector.getWordInfo(connection, "абсолютный").getPartOfSpeech());
        Assert.assertEquals(RuWiktionaryPartOfSpeech.PARTICIPLE, collector.getWordInfo(connection, "бастующий").getPartOfSpeech());
        Assert.assertEquals(RuWiktionaryPartOfSpeech.INTERJECTION, collector.getWordInfo(connection, "ау").getPartOfSpeech());
        Assert.assertEquals(RuWiktionaryPartOfSpeech.ADJECTIVE, collector.getWordInfo(connection, "ближний").getPartOfSpeech());
        Assert.assertEquals(RuWiktionaryPartOfSpeech.PARTICIPLE, collector.getWordInfo(connection, "бывший").getPartOfSpeech());
        Assert.assertEquals(RuWiktionaryPartOfSpeech.ADJECTIVE, collector.getWordInfo(connection, "авгиев").getPartOfSpeech());
    }

    @Test
    public void testFromFile() {
        File file = new File("C:/Work/ML/russian/zaliznyak-win.txt");
        RuWiktionaryCollector processor = new RuWiktionaryCollector();
        Connection connection = processor.connect();
        List<String> words = processor.getWords(file, Charset.forName("windows-1251"));
        IntStream.range(0, 100)
                .mapToObj(i -> processor.getWordInfo(connection, words.get(i)))
                .forEach(System.out::println);

    }

    @Test
    public void special() {
        RuWiktionaryCollector collector = new RuWiktionaryCollector();
        Connection connection = collector.connect();
        Assert.assertEquals(RuWiktionaryPartOfSpeech.INTERJECTION, collector.getWordInfo(connection, "аллилуйя").getPartOfSpeech());
    }
}
