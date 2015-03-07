---
layout: page
title: Work with sentences
footer: false
---

This page describes the functions that AIF2 can perform with semantic.

## Functions

On the semantic level AIF provides the following functions:

* calculate weight of the each word;
* build semantic connections graph between the words;

## <a id="create-SemanticDictBuilder-instance">Create SemanticDictBuilder instance</a>

This function gives you a possibility to calculate semantic weight of all words in the text. 
This function should be used by using class: SemanticDictBuilder (from package: io.aif.language.semantic). To create an instance of this class you need to extract sentence splitters characters like this:

``` java
    final ISeparatorExtractor testInstance = ISeparatorExtractor.Type.PROBABILITY.getInstance();   final ISeparatorsGrouper separatorsGrouper = ISeparatorsGrouper.Type.PROBABILITY.getInstance();   final ISeparatorGroupsClassifier sentenceSeparatorGroupsClassificatory = ISeparatorGroupsClassifier.Type.PROBABILITY.getInstance();   final List<Character> separators = testInstance.extract(tokens).get();   final Map<ISeparatorGroupsClassifier.Group, Set<Character>> grouppedSeparators = sentenceSeparatorGroupsClassificatory.classify(tokens, separatorsGrouper.group(tokens, separators));
```
with groups of separators SemanticDictBuilder class could be create:

``` java
    final SemanticDictBuilder semanticDictBuilder = new SemanticDictBuilder(grouppedSeparators);
```

For the difference between separator types see the section below

## <a id="calculate-weight-of-the-each-word">Calculate weight of the each word</a>

To work with semantic words you need to build Semantic dict from the text. This could be done by:

``` java
    final ISemanticDict semanticDict = semanticDictBuilder.build(placeholders);
```

ISemanticDict contains words with weights and connections. To obtain words with weights in sorted list:

``` java
    final List<ISemanticNode<IWord>> sortedNodes = semanticDict.getWords().stream().sorted((w2, w1) -> ((Double) w1.weight()).compareTo(w2.weight())).collect(Collectors.toList());
```

## <a id="build-semantic-connections-graph-between-the-words">Build semantic connections graph between the words</a>

To work with semantic words you need to build Semantic dict from the text. This could be done by:

``` java
    final ISemanticDict semanticDict = semanticDictBuilder.build(placeholders);
```

ISemanticDict contains words connections with connections weights. 