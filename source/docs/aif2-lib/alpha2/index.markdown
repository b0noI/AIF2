---
layout: page
title:
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

## Docs navigation

* API documentation:
  * v.1.0.0:
    * [Work with tokens](./api/1.0.0/tokens.html)
    * [Work with sentences](./api/1.0.0/sentences.html)
  * v.0.0.0:
    * [Work with tokens](./api/0.0.0/tokens.html)
    * [Work with sentences](./api/0.0.0/sentences.html)
* Algorithm documentation:
  * [Sentence splitting algorithm](./algorithm/sentence-splitting.html)
  * [Algorithm of tokens separator extraction](./algorithm/token-separator-extraction.html)
  * [Main definitions](./algorithm/main-definitions.html)
* Other documentation:
  * [ChangeLog](./changelog.html)
  * [Environment](./environment.html)
  * [How versions number is chosen ](./how-version-number-is-chosen.html)
  * [RoadMap](./road-map.html)
  * [Software that uses AIF](./software-that-uses-aif.hmtl)
  * [Tests](./tests.html)

## MailList

- **dev**: aif2-dev@yahoogroups.com (subscribe: aif2-dev-subscribe@yahoogroups.com)

## Current version

- alpha2

## Next versions

- alpha3 (~midl of December)
- alpha4 (~midl of January 2015)

## Deprecated versions 

This versions are no longer supported and should not be used:

- alpha1

## License

AIF2 is distributed under [MIT license](http://choosealicense.com/licenses/mit/)
