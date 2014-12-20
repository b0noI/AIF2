---
layout: page
title: Documentation
footer: false
---
 
AIF2 is **fully language independent** NLP library. AIF2 is written in Java 8+. 

## How to use it

You can use it by pulling artifact. 

Add repository to pom.xml:

    <project ...>
        <repositories>
            <repository>
                <id>aif.com</id>
                <url>http://192.241.238.122:8081/artifactory/libs-release-local/</url>
            </repository>
        </repositories>
    </project>

and add dependency:

    <dependency>
       <groupId>com.aif</groupId>
       <artifactId>aif</artifactId>
       <version>2.0.0-Alpha2</version>
    </dependency> 
 
###AIF2 API
* [AIF2: v0.0.0-alpha1](/docs/aif2-lib/alpha1/)
* [AIF2: v1.0.0-alpha2](/docs/aif2-lib/alpha2/)
* [AIF2: v1.1.0-alpha3](/docs/aif2-lib/alpha3/)

###Algorithms
* [Main definitions](./algorithm/main-definitions.html)
* [Sentence splitting algorithm](./algorithm/sentence-splitting.html)
* [Algorithm of tokens separator extraction](./algorithm/token-separator-extraction.html)

### Other AIF documentation
* [Road map](./common/road-map.html)
* [ChangeLog](./common/changelog.html)
* [Environment](./common/environment.html)
* [How version number is chosen](./common/how-version-number-is-chosen.htrml)
* [Software that uses aif](./common/software-that-uses-aif.html)

###AIF2 CLI Documentation
We have command line interface for our library.  
You can download it from [CLI Download page](/downloads/cli.html "CLI Download page"),  
and you can find usage instructions on [CLI Documentation page](/docs/aif-cli/ "CLI Documentation page")
