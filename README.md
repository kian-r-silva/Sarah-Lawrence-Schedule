## SLC Course Selection Project Summary

As part of my Summer Science program at Sarah Lawrence College, I had the opportunity to work on various projects under the guidance of Mike Siff and Jim Marshall. One of my projects involved recreating the Sarah Lawrence course selection system in Python using the pandas library. Although functional, the initial version was unorganized and inefficient. During the last semester, I decided to redo this project in Java, focusing on an object-oriented approach to create a more efficient solution for building student schedules at Sarah Lawrence College.

Sarah Lawrence courses follow a unique structure, with a typical fifteen-credit semester divided into three five-credit courses, each with multiple meeting times, including mandatory sessions, group conferences, and labs. The complexity of determining which courses fit into a student's schedule necessitated the development of a course scheduling system.

I started by extracting course data from the mySLC course catalog and processing it into a text file, which served as the foundation for creating CourseBlock objects in my program. These objects store detailed information about each course and its meeting times, enabling the program to organize and manage the data effectively.

The program's core functionality includes searching for courses, adding and removing courses from a schedule, and checking for conflicts between courses. The conflictCheck method ensures that a student's schedule is free of conflicts by comparing course meeting times, days, and other relevant factors. While the system works well for the majority of cases, I acknowledge that it may not handle every possible scenario perfectly.

To enhance the user experience and provide a visual representation of the constructed schedule, I developed a very rough graphical user interface (GUI) in Java. The GUI was designed primarily as a simple means of displaying the schedule, making it easier to interact with than a terminal interface.

Ultimately, the school recognized the value of the algorithm and decided to take it on, modifying it to better fit their specific needs. This project allowed me to apply the concepts learned in my Data Structures and Algorithms course, resulting in a more efficient and organized course scheduling system.