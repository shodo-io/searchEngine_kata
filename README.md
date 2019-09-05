# SearchEngine
Java coding exercise

## HOWTO
To run the program, you must install the [Oracle JDK 11](https://www.oracle.com/technetwork/java/javase/downloads/jdk11-downloads-5066655.html).

To generate application jar, you must additionally install [Apache maven](https://maven.apache.org/download.cgi).

### Instructions for build and run the application
 
* Install `JDK` and `Maven`
* Go to the application source code directory 
* `mvn package`
* Copy the generated jar in a external folder
* Go to this folder
* Create a subfolder named, for example, `files` and put inside it some files containing words  
* Execute the application : `java -jar search-1.0.jar ScoresEngine files`

## Topic

The exercise is to write a command line driven text search engine.
This should read all the text files in the given directory, 
building an in memory representation of the files and their contents, 
and then give a command prompt at which interactive searches can be performed. 

The search should take the words given on the prompt and return a list
of the top 10 (maximum) matching filenames in rank order, giving the rank
score against each match.	 

* The rank score must be 100% if a file	contains all the words
* It must be 0% if it contains none of the words	 
* It should be between 0 and 100 if it contains only some of the words
 but the exact ranking formula is up to you to choose and implement 

