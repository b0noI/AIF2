package io.aif.language.word.dict;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import io.aif.language.common.IMapper;
import io.aif.language.common.ISearchable;
import io.aif.language.word.IWord;

public class WordPlaceHolderMapper implements IMapper<Collection<String>,
    List<IWord.IWordPlaceholder>> {

  private final ISearchable<String, IWord> searchable;

  public WordPlaceHolderMapper(ISearchable<String, IWord> searchable) {
    this.searchable = searchable;
  }

  @Override
  public List<IWord.IWordPlaceholder> map(Collection<String> tokens) {
    return tokens
        .stream()
        .map(token -> {
          Optional<IWord> result = searchable.search(token);
          //TODO Should we even test for this.
          if (result.isPresent() == false)
            throw new RuntimeException("Token '" + token + "'is not present in the searchable");

          Word word = (Word) result.get();
          return word.new WordPlaceholder(token);
        })
        .collect(Collectors.toList());
  }
}
