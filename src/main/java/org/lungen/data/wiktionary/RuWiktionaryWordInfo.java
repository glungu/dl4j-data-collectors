package org.lungen.data.wiktionary;

public class RuWiktionaryWordInfo {

    private final String word;
    private final RuWiktionaryPartOfSpeech partOfSpeech;

    public RuWiktionaryWordInfo(String word, RuWiktionaryPartOfSpeech partOfSpeech) {
        this.word = word;
        this.partOfSpeech = partOfSpeech;
    }

    public String getWord() {
        return word;
    }

    public RuWiktionaryPartOfSpeech getPartOfSpeech() {
        return partOfSpeech;
    }

    @Override
    public String toString() {
        return "RuWiktionaryWordInfo {" +
                '\'' + word + '\'' + ',' +
                '\'' + partOfSpeech.getRuName() + '\'' +
                '}';
    }
}
