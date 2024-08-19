//Kian Silva Data Structures and Algorithms


import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

//Converts the html file into the txt file
public class Writer {

    public void write() throws FileNotFoundException {
        PrintWriter courseSchedule = new PrintWriter(new File("courseList.txt")); // Overwrite mode
        courseSchedule.println("courseTitle|courseCode|subterm|creditHours|instructor|courseDays|timeSlot|buildingCode|room|comments");

        Scanner reader = new Scanner(new File("CourseSchedule.html"));
        String line;
        String courseDetails;
        while (reader.hasNextLine()) {
            line = reader.nextLine();
            if (line.contains("<tr class=\"rowTop")) {
                courseSchedule.print(parseCourseTitle(line) + "|");
                line = reader.nextLine();
                line = reader.nextLine();//Skip middle table line
                courseDetails = parseCourseDetails(line);//courseCode
                courseSchedule.print(courseDetails + "|");
                line = reader.nextLine();
                courseDetails = parseCourseDetails(line);//subterm
                courseSchedule.print(courseDetails + "|");
                line = reader.nextLine();
                courseDetails = parseCourseDetails(line);//creditHours
                courseSchedule.print(courseDetails + "|");
                line = reader.nextLine();
                courseDetails = parseCourseDetails(line);//instructor
                courseSchedule.print(courseDetails + "|");
                line = reader.nextLine();
                courseDetails = parseCourseDetails(line);//courseDays
                courseSchedule.print(courseDetails + "|");
                line = reader.nextLine();
                courseDetails = parseCourseDetails(line);//timeSlot
                courseSchedule.print(courseDetails + "|");
                line = reader.nextLine();
                courseDetails = parseCourseDetails(line);//buildingCode
                courseSchedule.print(courseDetails + "|");
                line = reader.nextLine();
                courseDetails = parseCourseDetails(line);//roomCode
                courseSchedule.print(courseDetails + "|");
                line = reader.nextLine();
                courseDetails = parseCourseDetails(line);//commens
                courseSchedule.print(courseDetails + "\n");
            }
        }
        courseSchedule.close();
    }

    //parses the CourseTitle
    private String parseCourseTitle(String line) {
        int startIndex = line.indexOf("CourseTitle\">") + 13;
        int endIndex = line.indexOf("</td></tr>");
        return line.substring(startIndex, endIndex).trim();
    }

    //parses the course Details
    private String parseCourseDetails(String line) {
        int startIndex = line.indexOf("\">") + 2;
        int endIndex = line.indexOf("</td>");
        String detail = line.substring(startIndex, endIndex).trim();
        
        if (detail.equals("")) { 
            detail = " ";
            return detail;
        } 
        return detail;
    }
    
}