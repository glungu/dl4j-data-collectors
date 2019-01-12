package org.lungen.data.bugzilla;

import org.lungen.data.util.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * CSVParserBugzillaSplitter
 *
 * @author lungen.tech@gmail.com
 */
public class BugzillaResolveTimeSplitter {

    private static final Logger log = LoggerFactory.getLogger("splitter");
    private static final Random rand = new Random(7);

    public BugzillaResolveTimeSplitter(File dataFile) {
        try {
            List<String> lines = readStats(dataFile, true, false);

            int numTrain = 9500;
            int numTest = 450;

            File trainFile = new File(dataFile.getParent(), dataFile.getName().split("\\.")[0] + ".train.new.csv");
            File testFile = new File(dataFile.getParent(), dataFile.getName().split("\\.")[0] + ".test.new.csv");

            split(lines, numTrain, numTest, trainFile, testFile);

            readStats(trainFile, false, true);
            readStats(testFile, false, true);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<String> readStats(File dataFile, boolean hasHeaders, boolean labeled) throws IOException {
        List<String> lines = Files.readAllLines(dataFile.toPath());
        if (hasHeaders) {
            lines.remove(0);
        }

        StringBuilder stats = new StringBuilder();
        lines.stream()
                .mapToInt(l -> Integer.valueOf(l.substring(l.lastIndexOf(',') + 1)))
                .filter(i -> i >= 0)
                .mapToObj(i -> labeled ? BugzillaResolveTime.values()[i] : BugzillaResolveTime.fromHours(i))
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet().stream()
                .sorted(Comparator.comparing(Map.Entry::getKey))
                .forEach(e -> stats.append(e.getKey()).append(":").append(e.getValue()).append(", "));
        log.info(stats.toString());
        return lines;
    }

    private void split(List<String> lines, int numTrain, int numTest, File trainFile, File testFile) {
        Map<BugzillaResolveTime, List<Pair<String, BugzillaResolveTime>>> timeListMap = lines.stream()
                .map(l -> {
                    int index = l.lastIndexOf(',');
                    String desciption = l.substring(0, index);
                    Integer hours = Integer.valueOf(l.substring(index + 1));
                    BugzillaResolveTime time = hours >= 0 ? BugzillaResolveTime.fromHours(hours) : null;
                    return new Pair<>(desciption, time);
                })
                .filter(p -> p.getValue() != null)
                .collect(Collectors.groupingBy(Pair::getValue));

        List<Pair<String, BugzillaResolveTime>> trainFinal = new ArrayList<>();
        List<Pair<String, BugzillaResolveTime>> testFinal = new ArrayList<>();

        timeListMap.keySet().stream().sorted().forEach(k -> {
            List<Pair<String, BugzillaResolveTime>> pairs = timeListMap.get(k);
            if (pairs.size() >= numTest + numTrain) {
                log.info("[" + k + "] Total records: " + pairs.size() + ". Shuffling...");
                Collections.shuffle(pairs, rand);
                List<Pair<String, BugzillaResolveTime>> trainList = pairs.subList(0, numTrain);
                List<Pair<String, BugzillaResolveTime>> testList = pairs.subList(0, numTest);
                trainFinal.addAll(trainList);
                testFinal.addAll(testList);
            } else {
                log.info("[" + k + "] Total records: " + pairs.size() + ". Not enough data.");
            }
        });

        Collections.shuffle(trainFinal, rand);
        Collections.shuffle(testFinal, rand);

        writeDataFile(trainFinal, trainFile);
        writeDataFile(testFinal, testFile);
    }

    private void writeDataFile(List<Pair<String, BugzillaResolveTime>> trainFinal, File outputFile) {
        try (PrintWriter w = new PrintWriter(new FileOutputStream(outputFile))) {
            trainFinal.forEach(pair -> w.println(pair.getKey() + "," + pair.getValue().ordinal()));
        } catch (Exception e) {
            log.error("Cannot write to file: " + outputFile.getAbsolutePath(), e);
        }
    }

    public static void main(String[] args) {
        new BugzillaResolveTimeSplitter(new File("C:/DATA/Projects/DataSets/Bugzilla/bugzilla-processed-description.csv"));
    }

}
