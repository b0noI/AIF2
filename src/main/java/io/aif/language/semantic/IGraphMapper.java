package io.aif.language.semantic;

import io.aif.language.common.IMapper;
import io.aif.language.word.IWord;

import java.util.List;
import java.util.Map;
import java.util.Set;


public interface IGraphMapper extends IMapper<Map<IWord, Map<IWord, List<Double>>>, Set<ISemanticNode<IWord>>> {

    public Set<ISemanticNode<IWord>> map(final Map<IWord, Map<IWord, List<Double>>> nestedList);
    
}
