---
layout: page
title:
footer: false
---

AIF2 is **fully language independent** NLP library. AIF2 is written in Java 8+. 

## How to use it

You can use it by pulling artifact. 

Add repository to pom.xml:
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
and add dependency:
``` xml
    <dependency>
       <groupId>io.aif</groupId>
       <artifactId>aif</artifactId>
       <version>1.1.0-Alpha3</version>
    </dependency> 
```
## Docs

[AIF docs](./docs)

## MailList

- **dev**: aif2-dev@yahoogroups.com (subscribe: aif2-dev-subscribe@yahoogroups.com)

## Current version

- alpha3

## Next versions

- alpha4 (~end of January 2015)
- alpha5 (~end of February 2015)

## Deprecated versions 

This versions are no longer supported and should not be used:

- alpha2
- alpha1

## License

AIF2 is distributed under [MIT license](http://choosealicense.com/licenses/mit/)
