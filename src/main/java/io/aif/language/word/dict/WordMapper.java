package io.aif.language.word.dict;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import io.aif.language.common.IMapper;
import io.aif.language.token.comparator.ITokenComparator;
import io.aif.language.word.IWord;

public class WordMapper implements IMapper<Set<String>, IWord> {

    private final RootTokenExtractor rootTokenExtractor;

    WordMapper(ITokenComparator tokenComparator) {
        rootTokenExtractor = new RootTokenExtractor(ITokenComparator tokenComparator);
    }

    @Override
    public IWord map(Set<String> set) {
        final Optional<String> rootTokenOpt = rootTokenExtractor.extract(set);
        final String rootToken;
        if (rootTokenOpt.isPresent()) {
            rootToken = rootTokenOpt.get();
        } else {
            rootToken = set.iterator().next();
        }
        return new Word(rootToken, set, (long) set.size());
    }
}
