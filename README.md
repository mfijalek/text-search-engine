# Text Search Engine
Command-line driven text search engine.

## Commands

#### Build

In the main project directory execute:
```
./gradlew build jar
```

Jar file of our project will be in ```/build/libs/text-search-engine-1.0-SNAPSHOT.jar``` directory.

### Start

In the root project directory execute:

```java -jar ./build/libs/text-search-engine-1.0-SNAPSHOT.jar TextSearchEngineApplication PATH_TO_FOLDER_WITH_TEXT_FILES```

### Exit

To exit application just type on cmd: ```:quit``` termination statement.

## Application solutions

### Text Search Algorithm

Application was prepare to implement a common interface - we can implement any algorithm, combine them. In the future of application there is possibility to add other implementations, combine them and find the best results. 

Now, to find matching words in our file we used implementation of ```Rabin-Karp Algorithm```. 
> Rabin Karp algorithm matches the hash value of the pattern with the hash value of current substring of text, and if the hash values match then only it starts matching individual characters.

### Rank Algorithm

Application was prepare to implement any rank method.

Now, to calculate rank we used percentage how many words from phrase is present in the given file.
0% - none of words is present in given file,
0-100% - some words are present in given file,
100% - all words are present in given file

There is possibility to write another rank algorithm by implementing the interface.
We can combine just implemented solution with more complicated solutions to find more accurate rank.

### Performance

In the future versions, there is possibility to add multi thread processing of text search, each file per thread. Model object are fully immutable and prepared for operations on threads.