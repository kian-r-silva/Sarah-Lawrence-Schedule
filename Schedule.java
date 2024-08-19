//Kian Silva Data Structures and Algorithms
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.io.File;
import java.util.concurrent.TimeUnit;
import java.util.*;

//Course Selection System
public class Schedule {
    static ArrayList<CourseBlock> CourseList = new ArrayList<>();
    static HashMap<String, ArrayList<CourseBlock>> CourseListing = new HashMap<>();
    static ArrayList<ArrayList<CourseBlock>> curSchedule = new ArrayList<>();
    public static void main(String[] args) throws IOException{
        Schedule s = new Schedule();
        s.write();
        s.buildCourseList("courseList.txt");
        s.interact();
    }

    //Builds the HashMap of Courses Ordered by Title as the Key, the value is an ArrayList of CourseBlocks
    private void buildCourseList(String filename) throws IOException{
        CourseList = new ArrayList<>(); // Initialize CourseList ArrayList
        Scanner catalogue = new Scanner(new File(filename));
        catalogue.nextLine(); //Skip the header line with the field order        
        
        //build courseTime items
        while(catalogue.hasNext()){ 
            CourseBlock ClassMeet = new CourseBlock(catalogue.nextLine());
            CourseList.add(ClassMeet);
        }
        catalogue.close();
        //build CourseBlock chunk
        CourseListing = new HashMap<String,ArrayList<CourseBlock>>();
        for(int i = 0; i < CourseList.size(); i++){
            if (!CourseListing.containsKey(CourseList.get(i).title)) {
                ArrayList<CourseBlock> course = new ArrayList<>();
                course.add(CourseList.get(i));
                CourseListing.put(CourseList.get(i).title, course);
            } else {
                ArrayList<CourseBlock> courses = CourseListing.get(CourseList.get(i).title);
                courses.add(CourseList.get(i));
                CourseListing.put(CourseList.get(i).title, courses);
            }
        } 
        
    }

    //Retrieves all courses based on a search term, where the search term can be CourseTitle, professor, or course code
    public static ArrayList<ArrayList<CourseBlock>> searchCourse(String searchTerm) {
        ArrayList<ArrayList<CourseBlock>> foundCourses = new ArrayList<ArrayList<CourseBlock>>();
        searchTerm = searchTerm.toLowerCase(); // Convert search term to lowercase
        
        for (String title : CourseListing.keySet()) {
            if (title != null) {
                String titleLowerCase = title.toLowerCase(); // Convert title to lowercase
                ArrayList<CourseBlock> course = CourseListing.get(title); // Fetch courses for the current title
                if (course != null) { // Check if the course list is not null
                    if (titleLowerCase.contains(searchTerm)) { // Check if title contains search term
                        foundCourses.add(course);
                    } else {
                        for (CourseBlock c : course) { // Iterate through each course in the list
                            if (c != null) {
                                String courseCode = c.code.toLowerCase(); // Convert course code to lowercase
                                String courseProf = c.prof.toLowerCase(); // Convert professor name to lowercase
                                if (courseCode.contains(searchTerm) || courseProf.contains(searchTerm)) {
                                    foundCourses.add(course);
                                    break; // Break the loop once a matching course is found for the code or professor
                                }
                            }
                        }
                    }
                }
            }
        }
        return foundCourses;
    }
    
    //Adds a course to Schedule
    public static void addCourse(ArrayList<CourseBlock> course) {
        if (course.isEmpty()) {
            System.out.println("No course selected.");
            return;
        }
        if (curSchedule.contains(course)){
            System.out.println("Course already in Course Schedule. Select another Course.");
            return;
        }
       List<CourseBlock> conflictingCourses = conflictCheck(course);

        if (conflictingCourses.isEmpty()) {
            curSchedule.add(course);
            System.out.printf("%s added to schedule.\n", course.get(0).title);
        } else {
            System.out.printf("%s conflicts with existing schedule. Not added.\n", course.get(0).title);
        }
    }
    
    //removes a course from the schedule, it is case sensitive, but accepts partial matches
    public static void removeCourse(String Course){
        ArrayList<CourseBlock> delete = new ArrayList<>();
        for(ArrayList<CourseBlock> course: curSchedule){
            if (course.get(0).title.contains(Course)){
                delete = course; 
            }
        }
        if (delete.size() == 0){
            System.out.println("Course not found in curSchedule.");
            return;
        } else {
            curSchedule.remove(delete);
            System.out.printf("%s removed from schedule.\n", delete.get(0).title);
        }
    }

    //checks if the course being added conflicts with the current schedule that has been chosen
    private static List<CourseBlock> conflictCheck(ArrayList<CourseBlock> course) {
        ArrayList<CourseBlock> conflictingCourses = new ArrayList<>();
        if (curSchedule.isEmpty()) {
            return conflictingCourses;
        }

        for (CourseBlock addingCourse : course) {
            for (ArrayList<CourseBlock> curCourse : curSchedule) {
                for (CourseBlock existingCourse : curCourse) {
                    if (checkSem(existingCourse, addingCourse)) {
                        conflictingCourses.add(existingCourse);
                        conflictingCourses.add(addingCourse);
                        break;
                    } else if (checkWeekdays(existingCourse, addingCourse) && checkTime(existingCourse, addingCourse)) {
                        conflictingCourses.add(existingCourse);
                        conflictingCourses.add(addingCourse);
                        break;
                    }
                }
            }
        }

        boolean groupOrLabAvailable = false;
        int gc = 0;
        int lb = 0;
        for (CourseBlock cb : course) {
            if(comType(cb).equals("gc")){ gc++; groupOrLabAvailable = true;}
            if(comType(cb).equals("lab")){ lb++; groupOrLabAvailable = true;}
        }

        ArrayList<CourseBlock> confCour = CourseListing.get(conflictingCourses.get(0).title);
        for (CourseBlock cb : confCour) {
            if(comType(cb).equals("gc")){ gc++; groupOrLabAvailable = true;}
            if(comType(cb).equals("lab")){ lb++; groupOrLabAvailable = true;
            }
        }

        if (!groupOrLabAvailable) {
            return conflictingCourses;
        } else {
            for(int i = 0; i < conflictingCourses.size(); i+=2){
                if(comType(conflictingCourses.get(i)).equals("open") && comType(conflictingCourses.get(i+1)).equals("open")){
                    return conflictingCourses;
                }
                if (comType(conflictingCourses.get(i)).equals(comType(conflictingCourses.get(i+1))) && conflictingCourses.get(i).title.equals(conflictingCourses.get(i+1).title)){
                    if(comType(conflictingCourses.get(i+1)).equals("gc")){
                        gc--;
                    }
                    if(comType(conflictingCourses.get(i+1)).equals("lab")){
                        lb--;
                    }
                }
                if(comType(conflictingCourses.get(i)).equals("gc") || comType(conflictingCourses.get(i+1)).equals("gc")){
                    gc--;
                }
                if(comType(conflictingCourses.get(i)).equals("lab") || comType(conflictingCourses.get(i+1)).equals("lab")){
                    lb--;
                }
            }
            
            if (gc == 0 && lb == 0) {
                return conflictingCourses;
            } else {
                conflictingCourses.clear();
                return conflictingCourses;
            }
        }
    
    }
    
    //Checks for conflicts in weekdays of the courses
    private static boolean checkWeekdays(CourseBlock existingCourse, CourseBlock addingCourse){
        char[] existingDays = existingCourse.comp.weekdays.toCharArray();
        char[] addingDays = addingCourse.comp.weekdays.toCharArray();
        for (char eDay : existingDays) {
            if (eDay != ' '){
                for(char aDay: addingDays){
                    if (aDay != ' '){
                        if (eDay == aDay){
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    //checks if the times conflict between courses
    private static boolean checkTime(CourseBlock existingCourse, CourseBlock addingCourse){
        return (existingCourse.comp.time.conflict(addingCourse.comp.time));
    }

    //checks if the semesters conflict
    private static boolean checkSem(CourseBlock existingCourse, CourseBlock addingCourse){
        String existingCourseSem = existingCourse.comp.term;
        String addingCourseSem = addingCourse.comp.term;
    
        if (existingCourseSem.equals("Y") || addingCourseSem.equals("Y")) return false;
        if (existingCourseSem.equals(addingCourseSem)) return false;
        return true;
    }
    
    //converts the comments to something standard to be able to compare
    private static String comType(CourseBlock course){
        String Comment = course.comp.comments;
        if (Comment.contains("roup"))return "gc";
        if (Comment.contains("ab"))return"lab";
        return "open";
    }
    
    //calls the writer class to convert the txt file into the CourseBlocks
    public void write() throws IOException {
        Writer courseList = new Writer();
        courseList.write();
    }

    //Is the command keylog for the terminal controls
    public static int controlKeyLog() {
        Scanner keyboard = new Scanner(System.in);
        System.out.println("Select a key to execute the following");
        System.out.println("1: Course Catagolue by Title");
        System.out.println("2: Search Course");
        System.out.println("3: Add Course");
        System.out.println("4: Remove Course");
        System.out.println("5: View Schedule");
        System.out.println("6: Clear Schedule");
        System.out.println("7: Exit");
        return keyboard.nextInt();
    }

    // Controls for the interaction of the terminal controls, the main repl loop
    public static void interact() {
        Scanner keyboard = new Scanner(System.in);
        Boolean run = true;
        System.out.println("Welcome to the slc Course selection system");
    
        while (run) {
    
            int input = controlKeyLog();
    
            if (input == 1) {
                for (HashMap.Entry<String, ArrayList<CourseBlock>> courses : CourseListing.entrySet()) {
                    String title = courses.getKey();
                    System.out.println(title);
                }
            } else if (input == 2) {
                System.out.println("Please enter the course you are looking for: ");
                String course = keyboard.nextLine();
                ArrayList<ArrayList<CourseBlock>> found = searchCourse(course);
                if (found.size() > 0) {
                    for (ArrayList<CourseBlock> foundC : found) {
                        for (CourseBlock cMeet : foundC) {
                            System.out.println(cMeet);
                        }
                    }
                } else {
                    System.out.println("Course not found.");
                }
            } else if (input == 3) {
                System.out.println("Enter the name of the course you would like to add to your schedule: ");
                String courseInput = keyboard.nextLine();
                ArrayList<ArrayList<CourseBlock>> found = searchCourse(courseInput);
                if (found.size() == 1) {
                    addCourse(found.get(0));
                } else if (found.isEmpty()){
                    System.out.println("Course not found.");
                } else {
                    for (ArrayList<CourseBlock> options: found){
                        System.out.printf("Did you mean %s? (y/n) \n", options.get(0).title);
                        String choice = keyboard.nextLine();
                        if (choice.equals("y")){
                            addCourse(options);
                            break;
                        } else if (choice.equals("n")){
                            continue;
                        }
                    }
                }
            } else if (input == 4) {
                if (curSchedule.size() == 0) {
                    System.out.println("There are no courses in your current schedule");
                } else {
                    System.out.println("What course would you like to remove from your schedule?");
                    String remove = keyboard.nextLine();
                    removeCourse(remove);
                    /*for (ArrayList<CourseBlock> course : curSchedule) {
                        if (course.get(0).title.contains(remove)) {
                            removeCourse(course);
                            System.out.println("Course removed.");
                            break;
                        }
                    }
                    */
                }
            } else if (input == 5) {
                System.out.println("Your schedule is:\n");
                buildSchedule();
            } else if (input == 6) {
                curSchedule.clear();
                System.out.println("Schedule cleared.");
            } else if (input == 7) {
                run = false;
                System.out.println("Exiting...");
            } else {
                System.out.println("Invalid input. Please enter a number from 1 to 7.");
            }
            System.out.println();
        }

        keyboard.close();
    }
    
    //builds the schedule when user selects option 5, view schedule
    public static void buildSchedule(){
        ArrayList<CourseBlock> Monday = new ArrayList<CourseBlock>();
        ArrayList<CourseBlock> Tuesday = new ArrayList<CourseBlock>();
        ArrayList<CourseBlock> Wednesday = new ArrayList<CourseBlock>();
        ArrayList<CourseBlock> Thursday = new ArrayList<CourseBlock>();
        ArrayList<CourseBlock> Friday = new ArrayList<CourseBlock>();
        ArrayList<CourseBlock> needSched = new ArrayList<CourseBlock>();
        for (ArrayList<CourseBlock> Course: curSchedule){
            for(CourseBlock meet: Course){
                char[] days = meet.comp.weekdays.toCharArray();
                for (int i = 0; i< days.length; i++){
                    if (days[i] == ' '){
                        continue;
                    }else if (days[i] == 'M'){
                        Monday.add(meet);
                    } else if (days[i] == 'T'){
                        Tuesday.add(meet);
                    } else if (days[i] == 'W'){
                        Wednesday.add(meet);
                    } else if (days[i] == 'R'){
                        Thursday.add(meet);
                    } else if (days[i] == 'F'){
                        Friday.add(meet);
                    } else{
                        needSched.add(meet);
                    }
                }
            }
        }
        Monday.sort((a,b)->a.comp.time.compareTo(b.comp.time));
        Tuesday.sort((a,b)->a.comp.time.compareTo(b.comp.time));
        Wednesday.sort((a,b)->a.comp.time.compareTo(b.comp.time));
        Thursday.sort((a,b)->a.comp.time.compareTo(b.comp.time));
        Friday.sort((a,b)->a.comp.time.compareTo(b.comp.time));
        
        System.out.println("Monday: ");
        for(CourseBlock meet: Monday){
            System.out.printf("%s %s ;%s ;%s ;%s %s; %s\n", meet.title, meet.code, meet.prof, meet.comp.time.toString(), meet.comp.buildingCode, meet.comp.room, meet.comp.comments);
        }
        System.out.println();
        System.out.println("Tuesday: ");
        for(CourseBlock meet: Tuesday){
            System.out.printf("%s %s ;%s ;%s ;%s %s; %s\n", meet.title, meet.code, meet.prof, meet.comp.time.toString(), meet.comp.buildingCode, meet.comp.room, meet.comp.comments);
        }
        System.out.println();
        System.out.println("Wednesday: ");
        for(CourseBlock meet: Wednesday){
            System.out.printf("%s %s ;%s ;%s ;%s %s; %s\n", meet.title, meet.code, meet.prof, meet.comp.time.toString(), meet.comp.buildingCode, meet.comp.room, meet.comp.comments);
        }
        System.out.println();
        System.out.println("Thursday: ");
        for(CourseBlock meet: Thursday){
            System.out.printf("%s %s ;%s ;%s ;%s %s; %s\n", meet.title, meet.code, meet.prof, meet.comp.time.toString(), meet.comp.buildingCode, meet.comp.room, meet.comp.comments);
        }
        System.out.println();
        System.out.println("Friday: ");
        for(CourseBlock meet: Friday){
            System.out.printf("%s %s ;%s ;%s ;%s %s; %s\n", meet.title, meet.code, meet.prof, meet.comp.time.toString(), meet.comp.buildingCode, meet.comp.room, meet.comp.comments);
        }
        System.out.println();
        System.out.println("Need to Schedule the following your professor: ");
        for(CourseBlock meet: needSched){
            System.out.printf("%s %s ;%s ;%s ;%s %s; %s\n", meet.title, meet.code, meet.prof, meet.comp.time.toString(), meet.comp.buildingCode, meet.comp.room, meet.comp.comments);
        }
    }
}