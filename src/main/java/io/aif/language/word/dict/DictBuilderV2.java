package io.aif.language.word.dict;

import io.aif.language.common.IMapper;
import io.aif.language.token.comparator.ITokenComparator;
import io.aif.language.word.IDict;
import io.aif.language.word.comparator.ISetComparator;
import io.aif.language.word.mapper.IWordMapper;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DictBuilderV2 implements IDictBuilder<Collection<String>> {

    private final ISetComparator setComparator;
    private final ITokenComparator tokenComparator;
    private final IGroupable grouper;

    // Set comparator is already leaking internals of the system. If we want to communicate to the user on
    // group comparison then we should be looking for a group comparator which could be comparator!
    public DictBuilderV2(ISetComparator setComparator, ITokenComparator tokenComparator, IGroupable grouper) {
        this.setComparator = setComparator;
        this.tokenComparator = tokenComparator;
        // Set comparator is required by the grouper object.
        this.grouper = grouper;
    }

    @Override
    public IDict build(final Collection<String> from) {
        List<Set<String>> groups = grouper.group(from, setComparator);
        WordMapper mapper = new WordMapper(tokenComparator);
        return new Dict(new HashSet<>(mapper.mapAll(groups)));
    }
}
