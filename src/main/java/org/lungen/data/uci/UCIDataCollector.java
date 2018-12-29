package org.lungen.data.uci;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.lungen.data.util.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UCIDataCollector {

    private static final String URL = "https://archive.ics.uci.edu/ml/machine-learning-databases/synthetic_control-mld/synthetic_control.data";
    private static final Logger log = LoggerFactory.getLogger("data.uci");

    private static File baseDir = new File("C:/Work/ML/uci/");
    private static File baseTrainDir = new File(baseDir, "train");
    private static File featuresDirTrain = new File(baseTrainDir, "features");
    private static File labelsDirTrain = new File(baseTrainDir, "labels");
    private static File baseTestDir = new File(baseDir, "test");
    private static File featuresDirTest = new File(baseTestDir, "features");
    private static File labelsDirTest = new File(baseTestDir, "labels");


    public static String[] downloadUCIDataLines() {
        try {
            Document document = Jsoup.connect(URL)
                    .proxy("genproxy.amdocs.com", 8080)
                    .followRedirects(true)
                    .validateTLSCertificates(false)
                    .url(URL)
                    .get();
            document.outputSettings().prettyPrint(false);
            log.info(document.body().html());

            String[] lines = document
                    .body()
                    .html()
                    .split("\n");
            log.info("Loaded lines: " + lines.length);
            return lines;
        } catch (IOException e) {
            log.error("Cannot read data", e);
            return new String[0];
        }
    }

    //This method downloads the data, and converts the "one time series per line" format into a suitable
    //CSV sequence format that DataVec (CsvSequenceRecordReader) and DL4J can read.
    private static void downloadUCIData() throws Exception {
        File[] files = baseDir.listFiles();
        if (baseDir.isDirectory() && files != null && files.length > 0) {
            //Data already exists, don't download it again
            return;
        }

        String[] lines = downloadUCIDataLines();

        //Create directories
        baseDir.mkdir();
        baseTrainDir.mkdir();
        featuresDirTrain.mkdir();
        labelsDirTrain.mkdir();
        baseTestDir.mkdir();
        featuresDirTest.mkdir();
        labelsDirTest.mkdir();

        int lineCount = 0;
        List<Pair<String, Integer>> contentAndLabels = new ArrayList<>();
        for (String line : lines) {
            String transposed = line.replaceAll(" +", "\n");

            //Labels: first 100 examples (lines) are label 0, second 100 examples are label 1, and so on
            contentAndLabels.add(new Pair<>(transposed, lineCount++ / 100));
        }

        //Randomize and do a train/test split:
        Collections.shuffle(contentAndLabels, new Random(12345));

        int nTrain = 450;   //75% train, 25% test
        int trainCount = 0;
        int testCount = 0;
        for (Pair<String, Integer> p : contentAndLabels) {
            //Write output in a format we can read, in the appropriate locations
            File outPathFeatures;
            File outPathLabels;
            if (trainCount < nTrain) {
                outPathFeatures = new File(featuresDirTrain, trainCount + ".csv");
                outPathLabels = new File(labelsDirTrain, trainCount + ".csv");
                trainCount++;
            } else {
                outPathFeatures = new File(featuresDirTest, testCount + ".csv");
                outPathLabels = new File(labelsDirTest, testCount + ".csv");
                testCount++;
            }

            FileUtils.writeStringToFile(outPathFeatures, p.getFirst());
            FileUtils.writeStringToFile(outPathLabels, p.getSecond().toString());
        }
    }

    public static void main(String[] args) {
        try {
            downloadUCIData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
