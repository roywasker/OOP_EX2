# OOP Assignment 2
Assignment 2 of the course Object-Oriented Programming by Roy Wasker and Yuval Dahan.

##  Overview

In part A we created several text files and calculated the total number of lines in those files.<br>
We calaculated this in three different methods : <br>
• Normal method without using Threads<br>
• Using Threads<br>
• Using ThreadPool<br>

In part B we created ThreadPool that is implemented by priority queue. <br> 
in addition to each task submitted to the queue you can receive the task's result.

## Part A

In this part we created 3 departments to perform the task,
Class EX2_1 in which there are several methods that create several text files by the user's request and writes a number of lines to each file randomly according to the input from the user.
3 methods that receive an array of file's names and calculate the total number of lines in those files, all in three different methods: without threads, with threads, and with ThreadPool.<br>
and finally a method that will delete all the created files.

<br>
GetLineThreads class is a class that extends from the Thread class and contains 2 variables, the array of names and an integer that returns the number of lines.<br>
Also, contains a "run" method that calculates the number of lines in those files using Threads.<br><br>

GetLineThreadpool is a class that implements Callable <Integer> and contains 2 variables, the array of names and an integer that returns the number of lines.<br>
Also, contains a "call" method that calculates the number of lines in those files using ThreadPool and return and result.<br><br>

In addition, we wrote a test class in order to compare the times differences of the calculation in those three methods.<br><br><br>
Attached below is a picture of the calculation and times for each of the three methods :

<img src="EX2_1 tests.png" alt="EX2_1 tests.png" title="EX2_1 tests.png">

According to the times we can see threads is the fastest, followed by a relatively small difference the ThreadPool and finally the method without threads.

The differences in the times are because the number of lines that been calculated with Threads.
We defined the amount of Threads as the amount of files, so the calculation happens simultaneously in all the files, so it finished first.<br><br>
On the other hand, with ThreadPool we also calculate all the lines at the same time as with Threads, but because of the process of creating the ThreadPool which takes more time, it is slightly slower than a normal Thread.<br><br>
And finally, without Threads at all, we perform the calculation one file after the other and not at the same time, therefore it is the slowest.<br><br>
Attached below is a picture of the UML diagram :

<img src="EX2_1 diagram.png" alt="EX2_1 diagram.png" title="EX2_1 diagram.png">











<br><br><br>
#### To use the project clone the repositorty
Enter your IDE and clone the repository:
  ```sh
  git clone https://github.com/roywasker/OOP_EX2.git
  ```
