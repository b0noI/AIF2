---
layout: page
title: Work with tokens (v 0.0.0)
footer: false
---

This page ***describes*** functions that AIF2 can perform with tokens.

## Functions

There are 2 main functions that can be used in AIF2 about tokens:

* extract tokens splitter character from text;
* split text into tokens.

## Extract tokens

This function gives you a possibility to extract token separators list from specific input text. This function should be used by using interface: ITokenSeparatorExtractor (from package: com.aif.language.token). If you want to create instance of this interface you need to select type of TokenSeparatorExtractor and get instance, like this:

    ITokenSeparatorExtractor.Type.PREDEFINED.getInstance()

For difference between separators types see the section below

### Token Separators Extractor Types 

#### Type.PREDEFINED

This extractor has **predefined** characters that will be used for token splitting. This means that extractor type will not parse text in any way and will just return predefined characters.

## Token splitting

If you want to split tokens you need to:

* create TokenSplitter instance(from package: com.aif.language.token);
* call split method.

### Creating TokenSplitter

You can initiate it in 2 ways:

* setting Token Separators Extractor Type; 
* using default Token Separators Extractor Type.  

### 

    ITokenSeparatorExtractor tokenSeparatorExtractor = ...
    
    ISplitter<String, String> tokenSplitter = new TokenSplitter(tokenSeparatorExtractor);

This will create tokenSplitter that will use tokenSeparatorExtractor to split tokens in text. Also, you can create TokenSplitter with default ITokenSeparatorExtractor like this:

    ISplitter<String, String> tokenSplitter = new TokenSplitter();

By default it will use this ITokenSeparatorExtractor.Type.PREDEFINED.getInstance() token separator extractor.

### Splitting text with TokenSplitter

After you have TokenSplitter instance, you can split text by calling split method like this:

    ISplitter<String, String> tokenSplitter = ...
     
    String text = ...
     
    List<String> tokens = tokenSplitter.split(text);

## Usage example

[Here](https://github.com/b0noI/aif-cli/blob/master/src/main/java/com/aif/language/sentence/TokenSplitCommand.java) you can find usage example.