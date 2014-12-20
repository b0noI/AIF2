---
layout: page
title: Work with sentences 
footer: false
---

This page describes the functions that AIF2 can perform with sentences.

## Functions

There are 2 main functions that can be used in AIF2 about tokens:

* extract sentence splitters characters from tokens list;
* split tokens list into sentences list.

## Extract sentence splitters characters

This function gives you a possibility to extract a sentence splitters characters list from the input tokens list. This function should be used by using interface: ISentenceSeparatorExtractor (from package: com.aif.language.sentence). To create an instance of this interface you need to select the type of ISentenceSeparatorExtractor and get an instance like this:

    ISentenceSeparatorExtractor.Type.PREDEFINED.getInstance()

For the difference between separators types see the section below

### Sentence Separators Extractor Types

#### Type.PREDEFINED

This extractor has **predefined** the characters that will be used for sentence splitting. It means that this extractor type will not parse tokens in any way and will just return the predefined characters.

#### Type.STAT

This extractor will parse input tokens and will extract sentence splitters from tokens. This splitter should be used when the current text has sentence splitters that are not included in Type.PREDEFINED splitter. It's very useful when your text has non-standard encoding, or symbols using strange encoding.  

## Sentence splitting

To split sentences you need to:

* create SentenceSplitter instance(from package: com.aif.language.sentence);
* call split method.

### Creating SentenceSplitter

You can initiate it in 2 ways:

* setting Sentence Separators Extractor Type; 
* using default Sentence Separators Extractor Type.

### 

    ISentenceSeparatorExtractor sentenceSeparatorExtractor = ...
    
    ISplitter<List<String>, List<String>> sentenceSplitter = new SentenceSplitter(sentenceSeparatorExtractor);

This will create sentenceSplitter that will use sentenceSeparatorExtractor to split sentences. You can also create SentenceSplitter with default ISentenceSeparatorExtractor like this:

    ISplitter<List<String>, List<String>> sentenceSplitter = new SentenceSplitter();

By default it will use this ISentenceSeparatorExtractor.Type.STAT.getInstance() sentences separator extractor.

### Splitting tokens with SentenceSplitter

After you have SentenceSplitter instance, you can split tokens by calling split method like this:

    ISplitter<List<String>, List<String>> sentenceSplitter = ...
    
    List<String> tokens = ...
    
    List<List<String>> sentences = sentenceSplitter.split(tokens);

## Usage example 

example of real usage can be found [here](https://github.com/b0noI/aif-cli/blob/master/src/main/java/com/aif/language/sentence/SentenceSplitCommand.java#L33)

## Algorithm

Information about algorithm can be found [here](../../algorithm/sentence-splitting.html)