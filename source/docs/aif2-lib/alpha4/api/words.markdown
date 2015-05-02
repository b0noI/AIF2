---
layout: page
title: Work with words 
footer: false
---

This page ***describes*** the functions that AIF2 can perform with Words.

## Functions

There are 2 main functions that can be used in Alpha3 about words:

* generate words dictionary for the text;
* find all tokens that represent the Word;
* find the root token of the word. 

## Dictionary generating

For extracting words from text IDictBuilder instance should be instantiated:

``` java   
    final List<String> tokens = ...
    
    final ITokenComparator tokenComparator = ITokenComparator.defaultComparator();
    final ISetComparator setComparator = ISetComparator.createDefaultInstance(tokenComparator);
    
    final IDictBuilder dictBuilder = new DictBuilder(setComparator, tokenComparator);
    final IDict dict = dictBuilder.build(tokens);
```

It could be also instantiated with default values:

``` java    
    final List<String> tokens = ...
    
    final IDictBuilder dictBuilder = new DictBuilder();
    final IDict dict = dictBuilder.build(tokens);
```

## Working with text dictionary

For building dictionary that includes all words from text next methods need to be called:

``` java
    final List<String> tokens = ...
    
    final IDictBuilder dictBuilder = new DictBuilder();
    final IDict dict = dictBuilder.build(tokens);
    final IDict dict = dictBuilder.build(filteredTokens);
    final Set<IWord> words = dict.getWords();
```

Each word has set of tokens that this word represents:

``` java
    final Set<String> tokens = word.getAllTokens();
```

And root token:

``` java
    final String rootToken = word.getRootToken();
```
