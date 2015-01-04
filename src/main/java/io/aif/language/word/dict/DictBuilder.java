package io.aif.language.word.dict;

import io.aif.language.common.IGrouper;
import io.aif.language.word.IDict;
import io.aif.language.word.IWord;
import org.apache.log4j.Logger;
import java.util.*;
import java.util.stream.Collectors;

public class DictBuilder implements IDictBuilder<Collection<String>> {

    private static final Logger LOGGER = Logger.getLogger(DictBuilder.class);

    private final IGrouper grouper;
    private final WordMapper groupToWordMapper;

    public DictBuilder(IGrouper grouper, WordMapper groupToWordMapper) {
        this.grouper = grouper;
        this.groupToWordMapper = groupToWordMapper;
    }

    @Override
    public IDict build(final Collection<String> from) {
        LOGGER.debug(String.format("Beginning to build a dict: %s", from));

        List<Set<String>> groups = grouper.group(from);
        LOGGER.debug(String.format("Tokens after grouping: %s", groups));

        //TODO Ugly casting here!
        List<Collection<String>> converted = groups
                .stream()
                .collect(Collectors.toList());
        List<IWord> iWords = groupToWordMapper.mapAll(converted);
        LOGGER.debug(String.format("IWords created: %s", iWords));

        IDict dict = new Dict(new HashSet<>(iWords));
        LOGGER.debug(String.format("Dict generated: %s", dict.getWords()));
        return dict;
    }
}
