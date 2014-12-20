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

## Docs

[AIF docs](./docs)

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
