package com.aif.language.sentence;

import com.aif.language.common.IExtractor;
import com.aif.language.common.VisibilityReducedForTestPurposeOnly;

import java.util.List;
import java.util.Optional;

class StatDataExtractor {

    private final IExtractor<String, Character>             edgeCharacterExtractor;

    private final IExtractor<String, Character>             characterNearEdgeCharacterExtractor;

    StatDataExtractor(IExtractor<String, Character> edgeCharacterExtractor, IExtractor<String, Character> characterNearEdgeCharacterExtractor) {
        this.edgeCharacterExtractor = edgeCharacterExtractor;
        this.characterNearEdgeCharacterExtractor = characterNearEdgeCharacterExtractor;
    }

    public StatData parseStat(List<String> tokens) {
        final StatData endCharacterStatdata = new StatData();
        tokens.parallelStream().filter(token -> token.length() > 2).forEach(token -> parsToken(token, endCharacterStatdata));
        return endCharacterStatdata;
    }

    @VisibilityReducedForTestPurposeOnly
    void parsToken(final String token, final StatData statData) {
        token.chars().forEach(ch -> statData.addCharacter((char) ch));

        final Optional<Character> edgeCharacter = edgeCharacterExtractor.extract(token);
        final Optional<Character> characterNearEdge = characterNearEdgeCharacterExtractor.extract(token);

        if (!edgeCharacter.isPresent() || !characterNearEdge.isPresent()) {
            return;
        }

        statData.addEdgeCharacter(characterNearEdge.get(), edgeCharacter.get());
    }

}
