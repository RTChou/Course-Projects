## Lab3 (Renee Ti Chou)

The source code for this project was built using Eclipse, and it can be compiled by JDK 8.

This assignment contains two folders: Protocol#1 and Protocol #2. 

Protocol#1 uses the protocol given by assign instruction; Protocol #2 uses a protocol which resolves ties alphabetically first, and then by the length of groups. 

!!Note: The differences are the compareTo method in Node.java, and the code strings in Code2.txt

—————————————————————————
## Usage

To run the program, please enter:  

java Lab3 [frequency table file path] [message file path] [code file path] [output file path]

—————————————————————————
## Format of frequency table

[SingleCharacter,IntegerFrequency] in CSV format for each line

example:
A,18
B,10
C,19
.
.
.

* Duplicates of the same character are not allowed
* You can have at most one (can be 0) separator such as space( ), dash(-) or anything else but not comma(,)
* The program only reads in at most 27 informative (not blank) lines, and if there are more informative lines, a warning message will be given in the output file. 

—————————————————————————
## Files

1. HuffmanEncoding.java
2. HuffmanTree.java
3. Lab3.java
4. Node.java
5. PriorityQueue.java

—————————————————————————
## Description

Lab3 is a program of Huffman Encoding.

It reads in a frequency table file, a message file, and a code file, and then builds a Huffman tree to encode the message strings and decode the code strings. It will print out the results in one output file, along with the tree in preorder and the code table it generates.