package org.lungen.data.wiktionary;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.nio.charset.StandardCharsets.*;

public class RuWiktionaryCollector {

    private static final String URL = "https://ru.wiktionary.org";
    private static final Logger log = LoggerFactory.getLogger("data.wiktionary");

    private void convertAddPartOfSpeech(File fileWords, Charset fileWordsCharset,
                                        File fileOutput, Charset fileOutputCharset) {

        List<String> words = getWords(fileWords, fileWordsCharset);
        Connection connection = connect();

        try (PrintWriter writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(fileOutput), fileOutputCharset))) {
            writer.println("Слово, Часть речи");
            words.forEach(w -> {
                RuWiktionaryWordInfo wordInfo = getWordInfo(connection, w);
                writer.println(wordInfo.getWord() + ", " + wordInfo.getPartOfSpeech().ordinal());
            });
        } catch (Exception e) {
            log.error("Cannot write to file: " + fileOutput, e);
        }
        log.info("Written words to output: " + fileOutput.getAbsolutePath());
    }

    public List<String> getWords(File fileWords, Charset fileWordsCharset) {
        List<String> words = new ArrayList<>();
        try {
            words = Files.readAllLines(fileWords.toPath(), fileWordsCharset);
            log.info("Read words from file: " + words.size());
        } catch (IOException e) {
            log.error("Cannot read words from file: " + fileWords.getAbsolutePath(), e);
        }
        return words;
    }

    public Connection connect() {
        return Jsoup.connect(URL)
                .proxy("genproxy.amdocs.com", 8080)
                .followRedirects(true)
                .validateTLSCertificates(false);
    }

    public RuWiktionaryWordInfo getWordInfo(Connection connection, String word) {
        try {
            Document doc = connection.url(URL + "/wiki/" + word).get();
            doc.charset(Charset.forName("utf-8"));
            Element body = doc.body();

//            System.out.println("--------------------------------");
//            System.out.println(body);
//            System.out.println("--------------------------------");

            String titleAttr = "title";
            String cssQuery = "h1 ~ p";

            Elements paragraphs = body.select(cssQuery);
//            paragraphs.stream().forEach(p -> {
//                System.out.println(p);
//            });
//            System.out.println("---------------------------");

            Predicate<Element> ruParagraphFilter = p -> {
                Element prev = p;
                while (prev != null && !prev.tagName().equals("h1")) {
                    prev = prev.previousElementSibling();
                }
                return prev != null && (prev.text().equals("Русский") || prev.text().equals("Русский[править]"));
            };
            RuWiktionaryPartOfSpeech partOfSpeech = paragraphs.stream()
                    .filter(ruParagraphFilter)
                    .map(e -> e.getElementsByAttribute(titleAttr).stream())
                    .flatMap(Function.identity())
                    .map(e -> RuWiktionaryPartOfSpeech.getByRuName(e.attr(titleAttr)))
                    .filter(e -> e != RuWiktionaryPartOfSpeech.NA)
                    .findAny()
                    .orElse(RuWiktionaryPartOfSpeech.NA);

            if (partOfSpeech == RuWiktionaryPartOfSpeech.NA) {
                partOfSpeech = paragraphs.stream()
                        .filter(ruParagraphFilter)
                        .map(p -> Arrays.stream(p.text().split(", ")))
                        .flatMap(Function.identity())
                        .map(s -> {
                            String[] split = s.split(" ");
                            String[] array = split.length > 1 ? new String[] {split[0], split[1]} : new String[] {split[0]};
                            return Arrays.stream(array);
                        })
                        .flatMap(Function.identity())
                        .map(RuWiktionaryPartOfSpeech::getByRuName)
                        .filter(e -> e != RuWiktionaryPartOfSpeech.NA)
                        .findAny()
                        .orElse(RuWiktionaryPartOfSpeech.NA);
            }

            RuWiktionaryWordInfo wordInfo = new RuWiktionaryWordInfo(word, partOfSpeech);
            log.info("Collected: " + wordInfo);
            return wordInfo;

        } catch (IOException e) {
            log.error("Cannot collect info for word: " + word, e);
            return new RuWiktionaryWordInfo(word, RuWiktionaryPartOfSpeech.NA);
        }
    }

    public static void main(String[] args) {
        File fileInput = new File("C:/Work/ML/russian/zaliznyak-win.txt");
        File fileOutput = new File("C:/Work/ML/russian/words.txt");
        new RuWiktionaryCollector().convertAddPartOfSpeech(fileInput, Charset.forName("windows-1251"), fileOutput, UTF_8);
    }
}
