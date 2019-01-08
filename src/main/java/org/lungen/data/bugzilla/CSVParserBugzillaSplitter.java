package org.lungen.data.bugzilla;

import org.lungen.data.wiktionary.RuWiktionarySplitter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * CSVParserBugzillaSplitter
 *
 * @author lungen.tech@gmail.com
 */
public class CSVParserBugzillaSplitter {

    private static final Logger log = LoggerFactory.getLogger("splitter");

    public CSVParserBugzillaSplitter(File dataFile, Charset charset) {
        try {
            List<String> lines = Files.readAllLines(dataFile.toPath());

            Random rnd = new Random(7);
            Collections.shuffle(lines, rnd);

            List<String> dataTrain = lines.subList(0, 40000);
            List<String> dataTest = lines.subList(40000, lines.size());

            String nameTrain = dataFile.getName().split("\\.")[0] + ".train.csv";
            String nameTest = dataFile.getName().split("\\.")[0] + ".test.csv";
            try (PrintWriter w = new PrintWriter(new FileOutputStream(new File(dataFile.getParent(), nameTrain)))) {
                dataTrain.forEach(w::println);
            } catch (Exception e) {
                log.error("Cannot write to file: " + nameTrain, e);
            }
            try (PrintWriter w = new PrintWriter(new FileOutputStream(new File(dataFile.getParent(), nameTest)))) {
                dataTest.forEach(w::println);
            } catch (Exception e) {
                log.error("Cannot write to file: " + nameTest, e);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new CSVParserBugzillaSplitter(new File("C:/DATA/Projects/DataSets/Bugzilla/bugzilla-processed-description.csv"), StandardCharsets.UTF_8);
    }

}
