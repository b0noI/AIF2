package io.aif.language.word.dict;

import io.aif.language.common.IDictBuilder;
import io.aif.language.common.IGrouper;
import io.aif.language.common.IMapper;
import io.aif.language.token.comparator.ITokenComparator;
import io.aif.language.common.IDict;
import io.aif.language.word.IWord;
import io.aif.language.word.comparator.IGroupComparator;
import org.apache.log4j.Logger;
import java.util.*;
import java.util.stream.Collectors;

public class DictBuilder implements IDictBuilder<Collection<String>, IWord> {

    private static final Logger LOGGER = Logger.getLogger(DictBuilder.class);

    private final IGrouper grouper;
    private final IMapper<Collection<String>, IWord> groupToWordMapper;

    public DictBuilder() {
        ITokenComparator tokenComparator = ITokenComparator.defaultComparator();
        IGroupComparator groupComparator = IGroupComparator.createDefaultInstance(tokenComparator);

        this.groupToWordMapper = new WordMapper(new RootTokenExtractor(tokenComparator));
        this.grouper = new FormGrouper(groupComparator);
    }

    public DictBuilder(IGrouper grouper, IMapper<Collection<String>, IWord> groupToWordMapper) {
        this.grouper = grouper;
        this.groupToWordMapper = groupToWordMapper;
    }

    @Override
    public IDict<IWord> build(final Collection<String> from) {
        LOGGER.debug(String.format("Beginning to build a dict: %s", from));

        List<Set<String>> groups = grouper.group(from);
        LOGGER.debug(String.format("Tokens after grouping: %s", groups));

        List<Collection<String>> converted = groups
                .stream()
                .collect(Collectors.toList());
        List<IWord> iWords = groupToWordMapper.mapAll(converted);
        LOGGER.debug(String.format("IWords created: %s", iWords));

        IDict dict = Dict.create(new HashSet<>(iWords));
        LOGGER.debug(String.format("Dict generated: %s", dict.getWords()));
        return dict;
    }
}
