---
layout: page
title: Semantic text processing
footer: false
---

This page describes how to perform semantic text processing with the AIF2 library.

## The process overview

The semantic process requires the initial text processing result as an input value(the result of the initial text processing is the IText instance). 

Semantic process of the text processing with the AIF2 library contains 3 steps:

* creating the instance of the ISemanticText
 type;
* usage of the ISemanticText
 type instance; 

## Creating the instance of the ISemanticText
 type

ISemanticText instance need to be build by executing the factory method.

``` java import io.aif.pipeline.factory.semantic.ISemanticTextFactory; import io.aif.pipeline.model.ISemanticText;

    // ...
  final IText text = fileTextFactory.build();
    final ISemanticText semanticText = ISemanticTextFactory.build(text);
```

this is time consuming operation.

## Usage of the ISemanticText type instance

Once the ISemanticText instance is created it could be used to obtain all necessary data from the SemanticText:

``` java
final IGraph<IWord> wordsGraph = semanticText.wordsGraph(); final IFactQuery factQuery = semanticText.factQuery();
```