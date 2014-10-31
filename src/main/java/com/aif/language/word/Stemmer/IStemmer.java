package com.aif.language.word.stemmer;

import com.aif.language.common.IExtractor;
import com.aif.language.word.IWord;

import java.util.Collection;
import java.util.List;

/**
 * Created by ifthen on 10/30/14.
 */
public interface IStemmer extends IExtractor<Collection<String>, List<IWord>> {
}
