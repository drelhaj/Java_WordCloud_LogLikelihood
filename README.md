# LogLikelihood Java Word Clouds
Java tool to create word clouds using log Likelihood and word frequencies (or any other weight values). 
Log Likelihood is calculated for a word between two large corpora input that could be in any language. The tool is language independent and was tested on Arabic and English

The system takes source and reference corpora text files as input and calculate log likelihood and frequencies for a set of keywords input from the user side.
The system creates word clouds using java and I also provide file for d3 Javascript word clouds alongside this project, check the llhCloud folder for the javascript and data.csv files, the javascript file has been modified to
support log likelihood values as the keywords weight, basically the bigger the value the larger the text.

The java word clouds include a tooltip text feature to show the value of the weight used.

# Features
* Language independent
* random colouring
* Create Log likelihood and word frequency word clouds
* can also create word clouds for a user chosen weight value you just need to provide an array of double values
* you can drag drop words to match font colours
* tooltip text to show the value of the word's weight (e.g. LLH score)
* save output results in csv file format
* methods and classes are stand alone the tool can be used to deal with large text files to:
  * count words frequency
  * calculate log likelihood
  * get corpus size
  * read CSV and other line delimited files into arrays
  * create your own word clouds by providing two arrays (String[] and Double[])


# Requirements
* Requires java to run, the system was not tested on Java 8 but considering the library used it should run smoothly.
* Has been tested on both Linux and Windows and run smoothly on both.
* Has been tested on large files.
* The system runs in a UTF-8 environment by default and was confirmed to work with Linux, however Windows users: by setting the (Windows) environment variable JAVA_TOOL_OPTIONS to -Dfile.encoding=UTF8, the (Java) System property will be set automatically every time a JVM is started. 

# Execution
* The tool has a CLI interface and uses java swing to create the Java word clouds.
* For those who know their way with java there is a test class which gives a glimpse on how the methods work.
* Running LogLikelihood Java Word Clouds from command line is fairly simple.
For further information you can use the help command.

To run LogLikelihood Java Word Clouds you need SourceFile, ReferenceFile, KeywordsFile, OutputFile and OutputMethod:
* Example: src.txt ref.txt keywords.txt output.csv 1
* The OutputMethod determines the values saved to the user output. 1: saves words and their LLH values, 2: saves words and their frequency count in the source corpus, 3: saves words alongside LLH and frequency counts.
* To run the javascript file on your server or localhost you need to make sure the csv file contains the correct values, check the readme file in the llhCloud directory.

# Interface
LogLikelihood Java Word Clouds is an open source command-line application. The method and classes are independent from each others to make it easy to integrate with other systems (e.g. you can count frequencies or calculate Log Likelihood without the need to create word clouds)

# Input/Output file format
LogLikelihood Java Word Clouds works with UTF-8 file format for both input and output.

# Sample Output
* Java Word Cloud </br> </br>
![alt tag](https://github.com/drelhaj/Java_WordCloud_LogLikelihood/blob/master/cloudssample/llhcloudjava.png)
* Java Word Cloud Arabic </br> </br>
![alt tag](https://github.com/drelhaj/Java_WordCloud_LogLikelihood/blob/master/cloudssample/llhcloudjavaarabic.png)
* d3 Javascript Word Cloud </br> </br>
![alt tag](https://github.com/drelhaj/Java_WordCloud_LogLikelihood/blob/master/cloudssample/llhcloudjs.png)
* d3 Javascript Word Cloud Arabic </br> </br>
![alt tag](https://github.com/drelhaj/Java_WordCloud_LogLikelihood/blob/master/cloudssample/llhcloudjsarabic.png)

