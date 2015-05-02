---
layout: page
title: Initial text processing
footer: false
---

This page describes how to perform initial text processing with the AIF2 library.

## The process overview

Initial process of the text processing with the AIF2 library contains 3 steps:

* creating the instance of the ITextFactory type;
* creating the instance of the IText type;
* usage of the IText type instance; 

## Creating the instance of the ITextFactory type

For initial text parsing - instance of the ITextFactory need to be created. At this stage of development there is only one implementation of the ITextFactory interface:

``` java

    import io.aif.pipeline.factory.plain.ITextFactory;

    import io.aif.pipeline.factory.plain.FileTextFactory;


    // ...


    final String pathToTheTxtFile = "/path/to/the/text.file";

    final ITextFactory textFactory = new FileTextFactory(pathToTheTxtFile);

```

## Creating the instance of the IText type

Once the ITextFactory type instance has been created, IText instance need to be build by executing the factory method.

``` java

    import io.aif.pipeline.model.IText;


    // ...

    final ITextFactory fileTextFactory = new FileTextFactory(path);

    final IText text = fileTextFactory.build();
```

this is time consuming operation.

## Usage of the IText type instance

Once the IText instance is created it could be used to obtain all necessary data from the text:

``` java

final List<List<IWord.IWordPlaceholder>> sentences = text.sentences();
 final List<String> tokens = text.tokens();
 final Map<ISeparatorGroupsClassifier.Group, Set<Character>> separators = text.separators();
 final Set<IWord> words = text.words();
```