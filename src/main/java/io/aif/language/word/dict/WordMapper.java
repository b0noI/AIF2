package io.aif.language.word.dict;

import java.util.Collection;
import java.util.Comparator;
import java.util.Optional;
import java.util.Set;
import io.aif.language.common.IMapper;
import io.aif.language.word.IWord;
import org.apache.log4j.Logger;

public class WordMapper implements IMapper<Collection<String>, IWord> {

    private static final Logger LOGGER = Logger.getLogger(WordMapper.class);

    private final RootTokenExtractor rootTokenExtractor;

    WordMapper(RootTokenExtractor rootTokenExtractor) {
        this.rootTokenExtractor = rootTokenExtractor;
    }

    @Override
    public IWord map(Collection<String> data) {
        LOGGER.debug(String.format("Map %s", data));
        final Optional<String> rootTokenOpt = rootTokenExtractor.extract(data);
        final String rootToken;
        if (rootTokenOpt.isPresent()) {
            rootToken = rootTokenOpt.get();
        } else {
            rootToken = data.stream().sorted(Comparator.comparing(String::length)).findFirst().get();
        }
        return new Word(rootToken, data, (long) data.size());
    }
}
