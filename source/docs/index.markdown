---
layout: page
title: Documentation
footer: false
---
 
AIF2 is **fully language independent** NLU (Natural Language Understanding) library. AIF2 is written in Java 8+. 

## How to use it

You can use it by pulling artifact. 

Add repository to pom.xml:
``` xml
    <project ...>
        <repositories>
            <repository>
                <id>aif.com</id>
                <url>http://artifactory.aif.io/artifactory/libs-release-local
</url>
            </repository>
        </repositories>
    </project>
```
and add dependency:
``` xml
    <dependency>
       <groupId>io.aif.pipeline</groupId>      <artifactId>pipeline</artifactId>      <packaging>jar</packaging>      <version>1.0-Beta1</version>
    </dependency> 
```
###AIF2 API
* [AIF2: v2.0.0-beta1](/docs/aif2-lib/beta1/)
* [AIF2: v1.2.0-alpha4](/docs/aif2-lib/alpha4/)
* [AIF2: v1.1.0-alpha3](/docs/aif2-lib/alpha3/)
* [AIF2: v1.0.0-alpha2](/docs/aif2-lib/alpha2/)
* [AIF2: v0.0.0-alpha1](/docs/aif2-lib/alpha1/)

###Algorithms
* [Main definitions](./algorithm/main-definitions.html)
* [Sentence splitting algorithm](./algorithm/sentence-splitting.html)
* [Algorithm of tokens separator extraction](./algorithm/token-separator-extraction.html)

### Other AIF documentation
* [Road map](./common/road-map.html)
* [ChangeLog](./common/changelog.html)
* [Environment](./common/environment.html)
* [How version number is chosen](./common/how-version-number-is-chosen.html)
* [Software that uses aif](./common/software-that-uses-aif.html)

###AIF2 CLI Documentation
We have command line interface for our library.  
You can download it from [CLI Download page](/downloads/cli.html "CLI Download page"),  
and you can find usage instructions on [CLI Documentation page](/docs/aif-cli/ "CLI Documentation page")
