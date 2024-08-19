import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;

public class Schedule {
    static ArrayList<Course> CourseSchedule;

    public static void main(String[] args) throws IOException{
        CourseSchedule = new ArrayList<>(); // Initialize CourseSchedule ArrayList
        Schedule s = new Schedule();
        s.write();
        Scanner catalogue = new Scanner(new File("courseList.txt"));
        catalogue.nextLine(); //Skip the header line with the field order        
        int count = 0;
        while(catalogue.hasNext()){ 
            Course ClassMeet = new Course(catalogue.nextLine());
            CourseSchedule.add(ClassMeet);
            count++;
            System.out.println(count);
        }
        for(int i = 0; i < CourseSchedule.size(); i++){
            System.out.println(CourseSchedule.get(i).title);
        }
        catalogue.close();
    }

    
    public void write() throws IOException {
        ScheduleWrite courseList = new ScheduleWrite();
        courseList.write();
        System.out.println("courseList.txt has been updated with course information.");
    }
    
}




