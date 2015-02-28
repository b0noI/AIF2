package io.aif.language.word.dict;

import java.util.Collection;
import java.util.Comparator;
import java.util.Optional;
import java.util.Set;

import io.aif.language.common.IExtractor;
import io.aif.language.common.IMapper;
import io.aif.language.word.IWord;
import org.apache.log4j.Logger;

public class WordMapper implements IMapper<WordMapper.DataForMapping, IWord> {

    private static final Logger LOGGER = Logger.getLogger(WordMapper.class);

    private final IExtractor<Collection<String>, String> rootTokenExtractor;

    WordMapper(IExtractor<Collection<String>, String> rootTokenExtractor) {
        this.rootTokenExtractor = rootTokenExtractor;
    }

    @Override
    public IWord map(final WordMapper.DataForMapping data) {
        LOGGER.debug(String.format("Map %s", data));
        final Optional<String> rootTokenOpt = rootTokenExtractor.extract(data.getData());
        final String rootToken;
        if (rootTokenOpt.isPresent()) {
            rootToken = rootTokenOpt.get();
        } else {
            rootToken = data.getData()
                    .stream()
                    .sorted(Comparator.comparing(String::length))
                    .findFirst()
                    .get();
        }
        return new Word(rootToken, data.getData(), data.getCount());
    }

    public static class DataForMapping {

        private final Collection<String> data;

        private final Long count;

        public DataForMapping(Collection<String> data, Long count) {
            this.data = data;
            this.count = count;
        }

        public Collection<String> getData() {
            return data;
        }

        public Long getCount() {
            return count;
        }

    }

}
