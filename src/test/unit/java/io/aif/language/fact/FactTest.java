package io.aif.language.fact;

import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import io.aif.language.word.IWord;

import static org.mockito.Mockito.mock;
import static org.testng.Assert.assertEquals;

public class FactTest {

  @Test(groups = "unit-tests")
  public void testFactConstruction() throws Exception {
    final IFact fact = new Fact(Collections.EMPTY_LIST, Collections.EMPTY_SET);
    assert true;
  }

  @Test(groups = "unit-tests")
  public void testGetSemanticSentence() throws Exception {
    IWord semanticNodeMock1 = mock(IWord.class);
    IWord semanticNodeMock2 = mock(IWord.class);
    List<IWord> semanticSentenceInput = Arrays.asList(semanticNodeMock1, semanticNodeMock2);

    IFact fact = new Fact(semanticSentenceInput, Collections.EMPTY_SET);
    assertEquals(fact.getSemanticSentence(), semanticSentenceInput);
  }

}