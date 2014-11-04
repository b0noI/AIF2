---
layout: page
title: Algorithm of tokens separator extraction
footer: false
---
## Definitions

* [tokens separator](./main-definitions.html#tokens-separator)

## Algorithm

The process of tokens separator extraction counts each unique character in the text. One rule is to be applied during calculation: if a unique character occurs more than once in a row, this occurrence is counted as one.

This approach does not work under the following conditions:
* input text is small;
* language of input text doesn't have a token separator (some languages don't use token separators at all or in some cases).

**Example of input:**
Token1 token2 eeee4    .

**Counting result:**

'T' - 1

't' - 1

'o' - 2

'k' - 2

'e' - 3

'n' - 2

'1' - 1

'2' - 1

'4' - 1

' ' - 3

'.' - 1