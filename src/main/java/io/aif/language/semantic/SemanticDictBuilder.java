package io.aif.language.semantic;


import io.aif.language.common.IDict;
import io.aif.language.word.IWord;
import io.aif.language.common.IDictBuilder;

import java.util.Collection;
import java.util.stream.Collectors;

public class SemanticDictBuilder implements IDictBuilder<IDict<IWord>, ISemanticNode<IWord>> {

    private SemanticDictBuilder(){}

    public static SemanticDictBuilder getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    public ISemanticDict build(final IDict<IWord> dict) {
        return new SemanticDict(dict.getWords().stream().map(SemanticWord::new).collect(Collectors.toSet()));
    }

    private static class SingletonHolder {

        static final SemanticDictBuilder INSTANCE = new SemanticDictBuilder();

    }

}
