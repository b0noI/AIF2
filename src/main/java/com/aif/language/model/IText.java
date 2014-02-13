package com.aif.language.model;

import com.aif.associations.model.IConnectionExtractor;
import com.aif.associations.model.IItem;

public interface IText {

    public IConnectionExtractor<IBaseWord, IBaseWord> getBaseWordConnectionExtractor();

    public IConnectionExtractor<IPostfix, IPrefix> getPostfixTpPrefixConnectionExtractor();

}
