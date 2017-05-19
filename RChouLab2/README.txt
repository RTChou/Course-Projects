## Lab2 (Renee Ti Chou)

The source code for this project was built using Eclipse, and it can be compiled by JDK 8.

—————————————————————————
## Usage

To run the program, please enter:  java Lab2 [number of disks] [output file path].

<Optional> The program will ask for generating an additional file for the table of times of towers with size from 1 to any number after creating the previous output file.

—————————————————————————
## Files

1. Lab2.java
2. RecHanoi.java
3. IterHanoi.java
4. IterHanoiFromRec.java
5. IllegalParameterException.java

—————————————————————————
## Description

Lab2 is a program which generates three versions of solution to the Tower of Hanoi problem, with tower size starting from 1 to the assigned number. It prints out the movement steps along with time spent in each tower size. All three versions will be printed into one single output file.

The first solution is of recursive version, the second is of iterative version which involves a data structure to move the disks behind the scene, and the third one is another iterative solution which is directly converted from the recursive one with some minor modifications.

The program also provides an option to generate the table of times with tower size from 1 to the assigned number. The output file format is of comma separated values for convenience purposes, and there will be steps printed out to the console. 
