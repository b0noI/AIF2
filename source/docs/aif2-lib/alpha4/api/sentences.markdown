---
layout: page
title: Work with sentences
footer: false
---

This page describes the functions that AIF2 can perform with sentences.

## Functions

On the sentence level AIF provides the following functions:

* [extract splitter characters from tokens list](#extract-splitters-characters-from-tokens) (like: ;./?'"!()[]);
* [divide splitter characters into groups](#divide-splitters-characters-on-groups) (like: GROUP1 [()!?.], GROUP2 [,:;]);
* [define splitter characters group that contains sentence splitter characters](#classify-splitters-groups) (like: GROUP1 [()!?.]);
* [split tokens into sentences](#sentence-splitting);

## <a id="extract-splitters-characters-from-tokens"></a> Extract splitter characters from tokens

This function gives you a possibility to extract the splitter characters list from the input tokens list. 
This function should be used by using interface: ISeparatorExtractor (from package: com.aif.language.sentence.separators.extractors). To create an instance of this interface you need to select the type of ISeparatorExtractor and get an instance like this:
``` java
    ISeparatorExtractor.Type.PROBABILITY.getInstance()
```
The currently supported types are:

* PROBABILITY
* PREDEFINED

For the difference between separator types see the section below

### Separators Extractor Types 

#### Type.PREDEFINED

This extractor has **predefined** characters. This extractor type ignores tokens and input and returns a predefined array of characters. 

List of predefined characters: '.', '!', '?', '(', ')', '[', ']', '{', '}', ';', '\'', '\"'

#### Type.PROBABILITY

This is a default extractor type. This extractor will parse input tokens and will extract splitters from tokens. 

### Extracting separators

Example of extracting separators:
``` java
    final List<String> inputTokens = ...;

    final ISeparatorExtractor separatorsExtractor = ISeparatorExtractor.Type.PROBABILITY.getInstance();

    Optional<List<Character>> optionalSeparators = testInstance.extract(inputTokens);
```
## <a id="divide-splitters-characters-on-groups"></a>Divide splitters characters into groups

This function gives you a possibility to divide all separators into groups. This function should be used by using interface: ISeparatorsGrouper (from the package: com.aif.language.sentence.separators.groupers). To create an instance of this interface you need to select the type of ISeparatorsGrouper and get an instance like this:
``` java
    ISeparatorsGrouper.Type.PROBABILITY.getInstance()
```
The currently supported types are:

* PROBABILITY
* PREDEFINED

For the difference between types see the section below

### Grouper Types 

#### Type.PREDEFINED

This grouper has **predefined** characters. This grouper type will put all separators that contain the predefined list into one group and all other separators into the other group. The input tokens list is just ignored. 

The list of predefined characters:  '.', '!', '?'

For example, if input separators: '.', '!', ',', ';', '(', ')'

Then the groups on output will be the following:

* '.', '!'
* ',', ';', '(', ')'

#### Type.PROBABILITY

This is a default grouper type. This grouper will parse input tokens and will group separators according to the statistical analysis.  

### Grouping separators

The example of grouping separators:
``` java
    final List<String> inputTokens = ...;

    final List<Character> separators = ...; 

    final ISeparatorsGrouper separatorsGrouper = ISeparatorsGrouper.Type.PROBABILITY.getInstance();

    final List<Set<Character>> separatorsGroups = separatorsGrouper.group(inputTokens, separators)
```
## <a id="classify-splitters-groups"></a>Classify splitters groups

The group names that are used in the classifier:

* GROUP1 - group of separators that are used for splitting tokens on sentences (like: .?!);
* GROUP2 - group of separators that are used for splitting sentences on parts (like: ,;:);

This function gives you a possibility to classify the separators group. This function should be used by using the interface: ISeparatorGroupsClassificatory (from package: com.aif.language.sentence.separators.classificators). To create an instance of this interface you need to select the type of ISeparatorGroupsClassificatory end get an instance like this:
``` java
    ISeparatorGroupsClassificatory.Type.PROBABILITY.getInstance()
```
The currently supported types are:

* PROBABILITY
* PREDEFINED

For the difference between types see the section below

### Classificators Types 

#### Type.PREDEFINED

This classificatory has the **predefined** character. This classificatory type will set a group that contains a predefined character as GROUP1, and the other group as GROUP2. The input tokens list is just ignored. 

The predefined character:  '.'

For example, if input groups are: ['.', '!'], [',', ';', '(', ')']

groups in output will be the following:

* GROUP1: ['.', '!']
* GROUP2: [',', ';', '(', ')']

#### Type.PROBABILITY

This is a default classificatory type. This classificatory will parse input tokens and will group separators according to statistical analysis.  

### Classifying separators groups

An example of classifying separators groups:
``` java
    final List<String> inputTokens = ...;

    final List<Set<Character>> separatorsGroups = ...; 

    final ISeparatorGroupsClassificatory sentenceSeparatorGroupsClassificatory = ISeparatorGroupsClassificatory.Type.PROBABILITY.getInstance();

    final Map<ISeparatorGroupsClassificatory.Group, Set<Character>> result = sentenceSeparatorGroupsClassificatory.classify(inputTokens, separatorGroups);
```
## <a id="sentence-splitting"></a>Sentence splitting

To split sentences you need to:

* create AbstractSentenceSplitter instance (from package: com.aif.language.sentence.splitters);
* call a split method.

### Creating AbstractSentenceSplitter
``` java
    final AbstractSentenceSplitter sentenceSplitter = AbstractSentenceSplitter.Type.HEURISTIC.getInstance();
```
Supported types:

* HEURISTIC (default)
* SIMPLE

#### Simple sentence separator

This separator will extract GROUP1 separators from the text and execute a split text by GROUP1 separators. This splitter is not smart, so any cases like: "Mr." would be parsed as sentence end.

#### Heuristic sentence separator

This separator will extract GROUP1 separators from the text and execute a **smart** split text by GROUP1 separators. This splitter will analyze all the occurrences of GROUP1 characters to understand where there are cases like this: "Mr." and where there are cases of end sentences.

### Splitting tokens with AbstractSentenceSplitter

After you have got AbstractSentenceSplitter instance, you can split tokens by calling the split method like this:
``` java
    ISplitter<List<String>, List<String>> sentenceSplitter = ...
    
    List<String> tokens = ...
    
    List<List<String>> sentences = sentenceSplitter.split(tokens);
```
## Usage example 

An example of real usage can be found [here](https://github.com/b0noI/aif-cli/blob/master/src/main/java/com/aif/language/sentence/SentenceSplitCommand.java#L40)