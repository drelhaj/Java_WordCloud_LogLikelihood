The d3 example runs on a server (e.g. localhost)
you need the correct data.csv file which can be any csv file with two columns
one for the keyword you need to show in the word cloud and the other is the 
font size (weight, frequency ...etc) for each of the keywords.
Please note the usage of "text" and "size" headers in the code which need to
match the csv file headers as well. i modified the javascript to use the size column in the csv
file to reflect the size of words in the word cloud.
This worked well with both Arabic and English both on Linux and Windows. -- Mahmoud El-Haj