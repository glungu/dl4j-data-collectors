package org.lungen.data.wiktionary;

import java.util.Arrays;

enum RuWiktionaryPartOfSpeech {
    NOUN("существительное"),
    ADJECTIVE("прилагательное"),
    VERB("глагол"),
    ADVERB("наречие"),
    PARTICIPLE("причастие"),
    PRONOUN("местоимение"),
    PREPOSITION("предлог"),
    LINKING("союз"),
    PARTICLE("частица"),
    INTERJECTION("междометие"),
    NA("---");

    private String ruName;

    RuWiktionaryPartOfSpeech(String ruName) {
        this.ruName = ruName;
    }

    public String getRuName() {
        return ruName;
    }

    public static String[] ruNames() {
        return Arrays.stream(values())
                .map(p -> p.ruName)
                .toArray(String[]::new);
    }

    public static boolean existsByRuName(String ruName) {
        return Arrays.stream(values()).map(p -> p.ruName)
                .anyMatch(n -> n.equalsIgnoreCase(ruName));
    }

    public static RuWiktionaryPartOfSpeech getByRuName(String ruName) {
        return Arrays.stream(values())
                .filter(p -> ruName.equalsIgnoreCase(p.ruName))
                .findAny()
                .orElse(NA);
    }
}
