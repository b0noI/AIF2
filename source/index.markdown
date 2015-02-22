---
layout: page
title:
footer: false
---

AIF2 is **fully language independent** NLU library. AIF2 is written in Java 8+. 

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
       <version>1.2.0-Alpha4</version>
    </dependency> 
```

## Current version

- alpha4

## Next versions

- alpha5 (~end of March 2015)
- beta1 

## Deprecated versions 

This versions are no longer supported and should not be used:

- alpha3
- alpha2
- alpha1

## License

AIF2 is distributed under [MIT license](http://choosealicense.com/licenses/mit/)
