package org.lungen.data.wiktionary;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.lungen.data.wiktionary.RuWiktionaryPartOfSpeech.*;

public class RuWiktionarySplitter {

    private static final Logger log = LoggerFactory.getLogger("splitter");

    public RuWiktionarySplitter(File dataFile, Charset charset) {
        try {
            List<String> lines = Files.readAllLines(dataFile.toPath());
            List<RuWiktionaryWordInfo> nouns = new ArrayList<>();
            List<RuWiktionaryWordInfo> adjec = new ArrayList<>();
            List<RuWiktionaryWordInfo> verbs = new ArrayList<>();
            lines.stream().skip(1).forEach(l -> {
                String[] split = l.split(",");
                String word = split[0].trim();
                int label = Integer.valueOf(split[1].trim());
                if (label == NOUN.ordinal()) {
                    nouns.add(new RuWiktionaryWordInfo(word, NOUN));
                } else if (label == ADJECTIVE.ordinal()) {
                    adjec.add(new RuWiktionaryWordInfo(word, ADJECTIVE));
                } else if (label == VERB.ordinal()) {
                    verbs.add(new RuWiktionaryWordInfo(word, VERB));
                }
            });

            Random rnd = new Random(7);
            Collections.shuffle(nouns, rnd);
            Collections.shuffle(adjec, rnd);
            Collections.shuffle(verbs, rnd);
            List<RuWiktionaryWordInfo> nounsTrain = nouns.subList(0, 10000);
            List<RuWiktionaryWordInfo> nounsTest = nouns.subList(10000, 10500);
            List<RuWiktionaryWordInfo> adjecTrain = adjec.subList(0, 10000);
            List<RuWiktionaryWordInfo> adjecTest = adjec.subList(10000, 10500);
            List<RuWiktionaryWordInfo> verbsTrain = verbs.subList(0, 10000);
            List<RuWiktionaryWordInfo> verbsTest = verbs.subList(10000, 10500);

            List<RuWiktionaryWordInfo> dataTrain = new ArrayList<>();
            dataTrain.addAll(nounsTrain);
            dataTrain.addAll(adjecTrain);
            dataTrain.addAll(verbsTrain);
            Collections.shuffle(dataTrain, rnd);

            List<RuWiktionaryWordInfo> dataTest = new ArrayList<>();
            dataTest.addAll(nounsTest);
            dataTest.addAll(adjecTest);
            dataTest.addAll(verbsTest);
            Collections.shuffle(dataTest, rnd);

            String nameTrain = dataFile.getName().split("\\.")[0] + ".train.csv";
            String nameTest = dataFile.getName().split("\\.")[0] + ".test.csv";
            try (PrintWriter w = new PrintWriter(new FileOutputStream(new File(dataFile.getParent(), nameTrain)))) {
                dataTrain.forEach(wordInfo -> {
                    w.println(wordInfo.getWord() + ", " + wordInfo.getPartOfSpeech().ordinal());
                });
            } catch (Exception e) {
                log.error("Cannot write to file: " + nameTrain, e);
            }
            try (PrintWriter w = new PrintWriter(new FileOutputStream(new File(dataFile.getParent(), nameTest)))) {
                dataTest.forEach(wordInfo -> {
                    w.println(wordInfo.getWord() + ", " + wordInfo.getPartOfSpeech().ordinal());
                });
            } catch (Exception e) {
                log.error("Cannot write to file: " + nameTest, e);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new RuWiktionarySplitter(new File("C:/Work/ML/russian/words.txt"), StandardCharsets.UTF_8);
    }
}
