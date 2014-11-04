---
layout: page
title: Main definitions
footer: false
---
## Definitions List

* [tokens separator](#tokens-separator)
* [text separators](#text-separators)
* [text separators groups](#text-separators-groups)
* [sentence](#sentence)
* [word](#word)

## <a id="tokens-separator"></a>Tokens separator

The character that is used in text for splitting tokens (the most common character that is used for this purpose is space)

## <a id="text-separators"></a>Text separators

Characters that are used for logical separation of the text. Some of the characters are used for text separation into sentences, while other characters are used for sentences separation into logical parts. Example of such characters: ```,.!/?:;"'-()[]```

## <a id="text-separators-groups"></a>Text separators groups

All text separators divided into 2 groups: Group1 and Group2

### Group1

This group contains separators that are _usually_ used for splitting text into sentences. Most common characters in this group: ```.!?``` . Separators of this group are not always (but usually) used for separation of text into sentences, for example: "dear Mr. Max"; the dot in the example is not used for sentence separation. In this particular case the dot is used as a separator from Group2

### Group2

This group contains separators that are _usually_ used for splitting sentences into logical sub-sentences. Most common characters in this group: ```;:,-"'()``` . Separators of this group are not always (but usually) used for separation of sentences into sub-sentences, for example: "- Hi - Hello"

## <a id="sentence"></a>Sentence

Sequence of tokens that contains semantic piece of information and surrounded by text separators. Sentence can include text separators. Usually sentence is surrounded by text separators of Group1 and includes text separators from Group2.

## <a id="word"></a>Word

Consists of an approximated root token and a list of tokens with similar forms