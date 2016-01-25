package io.aif.language.fact;

import io.aif.graph.simple.ISimpleGraph;
import io.aif.graph.simple.ISimpleGraphBuilder;
import io.aif.language.ner.NERExtractor;
import io.aif.language.word.IWord;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Factr {

    private final IFactDefiner factDefiner;

    private final NERExtractor nerExtractor;

    public Factr(final IFactDefiner definer, final NERExtractor nerExtractor) {
        this.factDefiner = definer;
        this.nerExtractor = nerExtractor;
    }

    public Factr() {
        this(IFactDefiner.Type.SIMPLE_FACT.getInstance(), new NERExtractor());
    }

    public IFactQuery run(final List<List<IWord.IWordPlaceholder>> sentences) {
        List<IFact> facts = sentences
                .parallelStream()
                .map(sentence -> sentence.stream().map(IWord.IWordPlaceholder::getWord).collect(Collectors.toList()))
                .filter(factDefiner::isFact)
                .map(this::mapToFact)
                .collect(Collectors.toList());

        final ISimpleGraph<IFact> g = buildGraph(facts);
        return new FactQuery(g);
    }

    IFact mapToFact(final List<IWord> sentence) {
        final Set<IWord> namedEntities = sentence.stream()
                .filter(word -> nerExtractor.getNerType(word).isPresent())
                .collect(Collectors.toSet());
        return new Fact(sentence, namedEntities);
    }

    // TODO Move this to the graph class. Should take a lambda to build graph
    private static ISimpleGraph<IFact> buildGraph(final List<IFact> facts) {
        final ISimpleGraphBuilder<IFact> g = ISimpleGraphBuilder.defaultBuilder();
        for (int i = 0; i < facts.size(); i++) {
            IFact from = facts.get(i);
            for (int j = i + 1; j < facts.size(); j++) {
                IFact to = facts.get(j);
                if (hasCommonProperNoun(from, to))
                    g.connect(from, to);
            }
        }
        return g.build();
    }

    // TODO move this as a method to fact class
    private static boolean hasCommonProperNoun(IFact sf1, IFact sf2) {
        for (IWord node : sf1.getNamedEntities()) {
            // TODO ISemanticNode does not have equality checks
            // TODO getNamedEntities generates the proper nouns on the fly.
            if (sf2.getNamedEntities().contains(node))
                return true;
        }

        return false;
    }
}
