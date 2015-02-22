package io.aif.language.semantic;

import io.aif.language.common.IDict;
import io.aif.language.common.IDictBuilder;
import io.aif.language.word.IWord;

import java.util.*;
import java.util.stream.Collectors;

class SemanticDictBuilder2 implements IDictBuilder<Collection<IWord.IWordPlaceholder>, ISemanticNode<IWord>> {

    private final int connectAhead;
    private final Map<IWord, SemanticWord> iwordToSemanticWordCache = new HashMap<>();

    public static final int CONNECT_AHEAD_TILL_END = -1;
    
    public static final int DONT_CONNECT = 0;

    public SemanticDictBuilder2() {
        this.connectAhead = CONNECT_AHEAD_TILL_END;
    }

    public SemanticDictBuilder2(int connectAhead) {
        if (connectAhead < CONNECT_AHEAD_TILL_END)
            throw new IllegalArgumentException(
                    "Connect ahead parameter should be greater than -1, given"  + connectAhead);
        this.connectAhead = connectAhead;
    }

    @Override
    public ISemanticDict build(Collection<IWord.IWordPlaceholder> placeholders) {
        final List<SemanticWord> semanticWords = placeholders
                .stream()
                .map(placeholder -> getOrCreateSemanticWord(placeholder.getWord()))
                .collect(Collectors.toList());

        final int subListLen = (connectAhead < 0 || connectAhead > semanticWords.size())
                ? semanticWords.size()
                : connectAhead;

        SemanticWord subjectNode;
        List<SemanticWord> subList;
        for (int i = 0; i < semanticWords.size(); i++) {
            subjectNode = semanticWords.get(i);
            subList = semanticWords.subList(1, subListLen);
            for (int j = 0; j < subList.size(); j++)
                subjectNode.addEdge(subList.get(j), (double)(j + 1));
        }

        return new SemanticDict(new HashSet<>(semanticWords));
    }

    private SemanticWord getOrCreateSemanticWord(IWord word) {
        return iwordToSemanticWordCache.getOrDefault(word, new SemanticWord(word));
    }

}
