---
layout: page
title: Work with tokens 
footer: false
---

This page ***describes*** the functions that AIF2 can perform with Words.

## Functions

There are 2 main functions that can be used in Alpha3 about words:
* generate words dictionary for the text;
* find all tokens that represnet the Word;
* find the root token of the word. 

## Dictionary generating

For extracting words from text IDictBuilder instance should be instanciated:
   
    final List<String> tokens = ...
    
    final ITokenComparator tokenComparator = ITokenComparator.defaultComparator();
    final ISetComparator setComparator = ISetComparator.createDefaultInstance(tokenComparator);
    
    final IDictBuilder dictBuilder = new DictBuilder(setComparator, tokenComparator);
    final IDict dict = dictBuilder.build(tokens);

It could be also instanciated with default values:
    
    final List<String> tokens = ...
    
    final IDictBuilder dictBuilder = new DictBuilder();
    final IDict dict = dictBuilder.build(filteredTokens);

## Text dict building

TODO