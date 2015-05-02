---
layout: page
title:
footer: false
---

AIF2 is **fully language independent** NLU library. AIF2 is written in Java 8+. 

## How to use it

You can use it by pulling artifact. 

Add repository to pom.xml:

```xml
    <project ...>
        <repositories>
            <repository>
                <id>aif.com</id>
                <url>http://artifactory.aif.io/artifactory/libs-release-local</url>
            </repository>
        </repositories>
    </project>
```
and add dependency:

```xml
    <dependency>
       <groupId>io.aif.pipeline</groupId> <artifactId>pipeline</artifactId> <version>1.0-Beta1</version>

    </dependency> 
```

## Current version

- beta1

## Next versions

- beta2 (~TBD)

## Deprecated versions 

This versions are no longer supported and should not be used:

- alpha4
- alpha3
- alpha2
- alpha1

## License

AIF2 is distributed under [MIT license](http://choosealicense.com/licenses/mit/)
