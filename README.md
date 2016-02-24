# LogLikelihood Java Word Clouds
Java tool to create word clouds using log Likelihood and word frequencies. Log Likelihood is calculated for a word between two large corpora

The tool is language independent and was tested on Arabic and English

The system takes source and reference corpora text files as input and calculate log likelihood and frequencies for a set of keywords input from the user side.
The system creates word clouds using java and the output also supports d3 Javascript word clouds which is provided alongside this project, check the llhCloud folder for the javascript and data.csv files, the javascript file has been modified to
support log likelihood values as the keywords weight, basically the bigger the value the larger the text.

The java word clouds include a tooltip text feature to show the frquency and log likelihood value for each keyword.

# Requirements
* Requires java to run, the system was not tested on Java 8 but considering the library used it should run smoothly.
* Has been tested on both Linux and Windows and run smoothly on both.
* Has been tested on large files.
* The system runs in a UTF-8 environment by default and was confirmed to work with Linux, however Windows users: by setting the (Windows) environment variable JAVA_TOOL_OPTIONS to -Dfile.encoding=UTF8, the (Java) System property will be set automatically every time a JVM is started. 

# Execution
* The tool has a CLI interface only but uses java swing to create the Java word clouds.

* Running LogLikelihood Java Word Clouds from command line is fairly simple.
For further information you can use the help command.

To run LogLikelihood Java Word Clouds you need SourceFile, ReferenceFile, KeywordsFile, WordCloudType, OutputFile (Optional):
* Example: src.txt ref.txt keywords.txt 1 output.csv
* The WordCloudType determines the wordcloud plotting process, the word cloud keywords size (font size) will change according to the keyword Log Likelihood value (1) or word frequency value (0).
* The javascript only works with log Likelihood values for font size but should be very easy to work with word frequency instead. All you need to do is change "size" to "freq" in the script.js file.
# Interface
LogLikelihood Java Word Clouds is an open source command-line application to make it easy to integrate with other systems.

# Input/Output file format
LogLikelihood Java Word Clouds works with UTF-8 file format for both input and output.

# Sample Output
* Java Word Cloud </br> </br>
![alt tag](https://github.com/drelhaj/LogLikelihood_Java/blob/master/CloudsSample/LLHCloudJava.png)
* Java Word Cloud Arabic </br> </br>
![alt tag](https://github.com/drelhaj/LogLikelihood_Java/blob/master/CloudsSample/LLHCloudJavaArabic.png)
* d3 Javascript Word Cloud </br> </br>
* ![alt tag](https://github.com/drelhaj/LogLikelihood_Java/blob/master/CloudsSample/LLHCloudJS.png)
* d3 Javascript Word Cloud Arabic </br> </br>
![alt tag](https://github.com/drelhaj/LogLikelihood_Java/blob/master/CloudsSample/LLHCloudJSArabic.png)

