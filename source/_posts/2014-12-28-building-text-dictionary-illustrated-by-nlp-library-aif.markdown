---
layout: post
title: "Building text dictionary illustrated by NLP library AIF"
date: 2014-12-28 20:42:34 +0000
comments: true
categories: 
---
It is already a custom to complement every release of AIF, a language independent library of natural language processing, with a note about things done and the way they work. Similar texts (but on Russian) on the two previous releases of Alpha1 and Alpha2 can be found [here](http://habrahabr.ru/post/238359/) and [here](http://habrahabr.ru/post/242147/) (text might be translated later). The current release i.e Alpha3 with its new feature of building a token dictionary from the entry text is no exception. We will describe how the token dictionary building process and how it can be applied to your project.

# A few terms
The following terms although common does not necessarily mean the same in the context of NLP and specifically our library. Find the complete list of terms [here](http://aif.io/docs/algorithm/main-definitions.html).

* Token is a sequence of alphabetic characters, bounded by separators on both sides.
* Language is the whole set of unique tokens.
* Text language is a set of all possible unique tokens present in text.
* Word is a language subset containing similar tokens.
* Semantic word is a language subset containing tokens with a similar context of use.
* Text vocabulary is a set of all possible words built on the basis of text language.

Today we will be speaking of a “common” word, not a semantic one. Building semantic connections in text and a dictionary of semantic words of the text is the task of the next release.

# “Similarity” of tokens

It can be easily noticed that some terms are incomplete and require some clarification in order to be practically used. For instance, “word” requires clarification on the token similarity notion. In our article we will calculate token similarity using formula [1]. The formula shows the probability that two tokens are included in one word. Accordingly, we assume that two tokens are included in one word if there is inequality [2].

ifthen: Token similarity tries to infer whether a given token is similar to another. It's execlusively based on form, for an instance we consider the tokens "see" and "seemed" similar. 

Token similarity is computed using the formula [1]. It shows the probability that two tokens are included in one word. Accordingly, we assume that two tokens are included in one word if there is inequality [2]. 

[1] 
![img](http://hsto.org/files/e7f/7ff/efa/e7f7ffefad8b4710948f5ea4a9c22890.png)

where:

* ![img](http://hsto.org/files/09f/389/49b/09f38949b26c454aaa559a6beb522076.png) - formula of token similarity based on common characters calculation (see formula 1.1)
* ![img](http://habrastorage.org/files/bb3/1b4/5c2/bb31b45c2ac5400e8bb8d0dffc30177e.png) - weight of formula of token similarity is based on common characters calculation (ranges 0.0 to 1.0). The given parameter is hardcoded (yuck!) and has a value of [0.8](https://github.com/b0noI/AIF2/blob/2ebcc2fe7d5a404554c8b0d812554e5b9816e720/src/main/java/io/aif/language/token/comparator/ITokenComparator.java#L16). This value will be configurable from the next release. However, if you want to fiddle with it without digging into the code, open a task for us here and we will do it.
* ![img](http://habrastorage.org/files/09f/389/49b/09f38949b26c454aaa559a6beb522076.png) - formula of token similarity based on recursively calculating the longest common substrings (see formula 1.2).
* ![img](http://habrastorage.org/files/a96/f88/102/a96f881027664d8c96378e4cf22ec424.png) - weight of formula of token similarity based on recursive calculation of the longest common strings. This value is hardcoded as 1 and will be configurable from the next release.

# Formula for token similarity based on common characters calculation
It has a noble name of which I can’t remember. There’s no thesis with all its links at hand. But I’m sure that a valiant reader will enlighten us.

[1.1] ![img](http://habrastorage.org/files/925/d01/ecf/925d01ecf6c143829dafe523e4598b9b.png)

where:

* ![img](http://habrastorage.org/files/389/acf/60b/389acf60b8cf4bf4a9508c2b42b79e62.png) - Length of token.
* ![img](http://habrastorage.org/files/cab/a83/ca0/caba83ca082c416ca8ba1b98f8654e85.png) - The number of characters that are included in the first and the second token. E.g., for input tokens: “aabbcc”, “aadddc”, the result is 3, since there are 3 characters [a, a, c] that are included in both tokens.

The formula is very simple. We calculate the characters that are included in both tokens without regard to the position of these characters in the tokens.

# Formula of token similarity based on recursive calculation of the longest common strings

Here’s more fun, the formula is recursive and is also named after its author :)

[1.2] ![img](http://habrastorage.org/files/14b/a2d/37e/14ba2d37ebd5484c89cc7b5f36b0570b.png)

where:

* ![img](http://habrastorage.org/files/06f/11c/bc9/06f11cbc997144da8265c7341db79bf4.png) - The maximum string that is included in both tokens;
* ![img](http://habrastorage.org/files/05f/528/038/05f528038b134e5b8116861220f706b7.png) - Recursive call of formula 1.2 for the left substring, see formula 1.2.1;
* ![img](http://habrastorage.org/files/102/be4/a0b/102be4a0b3024d89a358e4fc8cd30728.png) - Recursive call of formula 1.2 for the right substring, see formula 1.2.2;

[1.2.1] ![img](http://habrastorage.org/files/8dd/f03/f84/8ddf03f840f544439314f61a0a0aada6.png)

where:

* ![img](http://habrastorage.org/files/17f/1d9/72d/17f1d972d73248e88f209d635a7160e0.png) - The given method returns the substring of the first parameter in the range from the first character to the string that is passed as the second parameter. E.g., for the strings “hello” and “ll” the result is “he”

[1.2.2] ![img](http://habrastorage.org/files/e15/d00/072/e15d000729564edbb21e40926a8f9cfd.png)

where:

* ![img](http://habrastorage.org/files/d34/102/ed3/d34102ed3f01481eac24a8be1ece5f04.png) - The given method returns the substring of the first parameter in the range from the end of the string of the second argument in the first argument to the last character of the string. E.g., for the strings “hello” and “ll” the result is “o”.

[2] ![img](http://habrastorage.org/files/46f/85b/800/46f85b800a9046b18a78b411287c9004.png)

The threshold used in this inequality was chosen empirically: 0.75. In the current Alpha3 release this parameter is devilishly hardcoded [here](https://github.com/b0noI/AIF2/blob/2ebcc2fe7d5a404554c8b0d812554e5b9816e720/src/main/java/io/aif/language/word/dict/WordSetDict.java#L17).

Fixing this major sin :) is already planned in [Alpha4](https://github.com/b0noI/AIF2/issues/162).

# Comparison of words

In fact, the word is nothing more than a set of tokens grouped by a certain rule. The rule has been already specified (meeting the condition of inequality 2). So the comparison of two words is solved easily ([3]).

[3] ![img](http://habrastorage.org/files/bb2/205/237/bb22052377ae4aeaae3570bdc250b376.png)

# A word about practical application

The practice of building a dictionary is described on [this page](http://aif.io/docs/aif2-lib/alpha3/api/words.html). The process is very simple and takes no more than a few lines of code:

``` java
final List<String> tokens = ...  final IDictBuilder dictBuilder = new DictBuilder();
 final IDict dict = dictBuilder.build(tokens);
```

IDict interface:

``` java
public interface IDict {     public Set<IWord> getWords();  }
```

And now IWord interface itself:

``` java
public interface IWord {     public String getRootToken();     public Set<String> getAllTokens();     public Long getCount();     public static interface IWordPlaceholder {         public IWord getWord();         public String getToken();     }  }
```

By the way, all the documentation of Alpha3 release is [here](http://aif.io/docs/aif2-lib/alpha3/)
[There](http://aif.io/docs/aif2-lib/alpha3/api/tokens.html) you can find the description of API to work with tokens

and [sentences](http://aif.io/docs/aif2-lib/alpha3/api/sentences.html)

Let’s get back to the task of building a vocabulary though. An example of using this function can be seen in [the code of command line utility of the given library](https://github.com/b0noI/aif-cli/blob/8186538600d64c3eed8099ec6a94242d4eb8248d/src/main/java/com/aif/language/sentence/DictBuildCommand.java#L25).

``` java
public Void apply(String... args) {
        final String text;
        try {
            text = FileHelper.readAllTextFromFile(args[0]);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }        final TokenSplitter tokenSplitter = new TokenSplitter();
        final IDictBuilder<Collection<String>> stemmer = new DictBuilder();
        final Set<IWord> result = stemmer.build(tokenSplitter.split(text)).getWords();
        ResultPrinter.PrintStammerExtrctResult(result);
        return null;
    }
```

# How to start using AIF in your project

Everything is quite easy, you need to connect our repository to your project like this:

``` xml
<project ...>
    <repositories>
        <repository>
            <id>aif.com</id>
            <url>http://192.241.238.122:8081/artifactory/libs-release-local/</url>
        </repository>
    </repositories>
 </project>  
```

and add a dependency:

``` java
<dependency>
            <groupId>io.aif</groupId>
            <artifactId>aif</artifactId>
            <version>1.1.0-Alpha3</version>
 </dependency>
```

# Example of using AIF-CLI 1.2 command line utility

Let’s examine the actual work of the algorithm by the example of command line utility that uses AIF Alpha3 engine. You can read about using the utility on [this page](http://aif.io/docs/aif-cli/1.2/). Building a dictionary of a huge book can take a while (VSK provide some comparison). We are continually working towards improving the performance [the issue](https://github.com/b0noI/AIF2/issues/166).

Here is how the program set on the text of the article works (only a part of the program output is shown):

``` bash
java -jar aif-cli-1.2-jar-with-dependencies.jar -dbuild ~/tmp/text1.txt
```
result:

    Basic token: something tokens: [ [something] ]
    Basic token: where: tokens: [ [where:] ]
    Basic token: repository tokens: [ [repository] ]
    Basic token: can tokens: [ [can, (can] ]
    Basic token: your tokens: [ [your, our] ]
    Basic token: library tokens: [ [library, library.] ]
    Basic token: synonyms tokens: [ [synonyms] ]
    Basic token: two tokens: [ [two] ]
    Basic token: few tokens: [ [few] ]
    Basic token: documentation tokens: [ [documentation] ]
    Basic token: characters tokens: [ [characters, character, characters,] ]
    Basic token: implementation, tokens: [ [implementation,, implementation] ]
    Basic token: today tokens: [ [today] ]
    Basic token: has tokens: [ [has] ]
    Basic token: word, tokens: [ [word,, words, word] ]
    Basic token: input tokens: [ [input] ]
    Basic token: works tokens: [ [works, work] ]
    Basic token: token, tokens: [ [token,, token., tokens, token] ]
    Basic token: complement tokens: [ [complement] ]
    Basic token: easily tokens: [ [easily] ]
    Basic token: use. tokens: [ [use., used.] ]
    Basic token: AIF tokens: [ [AIF] ]
    Basic token: connected tokens: [ [connected, connect] ]
    Basic token: sense tokens: [ [sense] ]
    Basic token: Alpha2 tokens: [ [Alpha2] ]
    Basic token: searching tokens: [ [searching] ]
    Basic token: calculation tokens: [ [calculation] ]
    Basic token: shows tokens: [ [shows] ]
    Basic token: pleased tokens: [ [pleased, passed] ]
    Basic token: last tokens: [ [last] ]
    Basic token: developers tokens: [ [developers, developer., developer, developer,] ]
    Basic token: already tokens: [ [already] ]
    Basic token: context tokens: [ [context] ]
    Basic token: back tokens: [ [back] ]
    Basic token: named tokens: [ [named, name] ]
    Basic token: next tokens: [ [next] ]
    Basic token: practice tokens: [ [practice] ]
    Basic token: uses tokens: [ [uses, used] ]
    Basic token: weight tokens: [ [weight] ]
    Basic token: connections tokens: [ [connections] ]
    Basic token: length tokens: [ [length] ]
    Basic token: clarification tokens: [ [clarification] ]
    Basic token: hardcoded. tokens: [ [hardcoded., hardcoded] ] 
    Press 'Enter' to continue or 'q' command to quit. There are -102 entities to show
# Now a word about the next release

The next release (Alpha 4) is scheduled for the end of January and we anticipate to release the following features:
* Building a dictionary of semantic words
* Searching for synonyms in text
* Building a graph of relations of semantic words in text

Perhaps, even more features ;).

As parting note any feedback is welcome, If you would like to help us moving the project forward write to us or if you have interesting use case in the field of NLP do let us know. Please keep the comments flowing and help AIF make a small dent (we love big bangs too :)) in the world of NLP!

# Our team

* Kovalevskyi Viacheslav – algorithm developer, architecture design, team lead (viacheslav@b0noi.com / @b0noi)
* Ifthikhan Nazeem (@ifthikhan) – algorithm designer, architecture design, developer
* Sviatoslav Glushchenko — REST design and implementation, developer
* Oleg Kozlovskyi QA (integration and qaulity testing), developer.
* Evgenia Kozlovska, English teacher and translator (who translated this article!).
* Balenko Aleksey (podorozhnick@gmail.com) – added stammer support to CLI, junior developer
* Evgeniy Dolgikh — QA assistance, junior developer

# Links on the project and details

* Project language: Java 8
* License: MIT license
* Issue tracker: github.com/b0noI/AIF2/issues
* Wiki: github.com/b0noI/AIF2/wiki
* Source code: github.com/b0noI/AIF2
* Developers mail list: aif2-dev@yahoogroups.com (subscribe: aif2-dev-subscribe@yahoogroups.com)
