import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class ScheduleWrite {
    PrintWriter CourseSchedule;

    public void write() throws IOException {
        PrintWriter CourseSchedule = new PrintWriter("courseList.txt");
        CourseSchedule.print("courseTitle|");
        CourseSchedule.print("courseCode|");
        CourseSchedule.print("subterm|");
        CourseSchedule.print("creditHours|");
        CourseSchedule.print("instructor|");
        CourseSchedule.print("courseDays|");
        CourseSchedule.print("timeSlot|");
        CourseSchedule.print("buildingCode|");
        CourseSchedule.print("room|");
        CourseSchedule.print("comments|");
        CourseSchedule.print("\n");

        try {
            // opens HTML file to parse
            File input = new File("CourseSchedule.html");
            Document document = Jsoup.parse(input, "UTF-8");
            Elements courseTopRows = document.select("tr.rowTop");
            Elements courseBottomRows = document.select("tr.rowBottom");

            for (int i = 0; i < courseTopRows.size(); i++) {
                Element courseTopRow = courseTopRows.get(i);
                Element courseBottomRow = courseBottomRows.get(i); // Assuming same length

                // Extract CourseTitle from the first <td> in the current row
                String courseTitle = courseTopRow.select("td[colspan=9]").text();

                // Extract other data from the <td> elements in the current row
                String courseCode = courseBottomRow.select("td[data-bind=text: CourseCode]").text();
                String subterm = courseBottomRow.select("td[data-bind=text: Subterm]").text();
                String creditHours = courseBottomRow.select("td[data-bind=text: CreditHours]").text();
                String instructor = courseBottomRow.select("td[data-bind=text: Instructor]").text();
                String courseDays = courseBottomRow.select("td[data-bind=text: CourseDays]").text();
                String timeSlot = courseBottomRow.select("td[data-bind=text: TimeSlot]").text();
                String buildingCode = courseBottomRow.select("td[data-bind=text: BuildingCode]").text();
                String room = courseBottomRow.select("td[data-bind=text: Room]").text();
                String comments = courseBottomRow.select("td[data-bind=text: Comments]").text();

                // Print or process the extracted data as needed
                CourseSchedule.print(courseTitle+"|");
                CourseSchedule.print(courseCode+"|");
                CourseSchedule.print(subterm+"|");
                CourseSchedule.print(creditHours+"|");
                CourseSchedule.print(instructor+"|");
                CourseSchedule.print(courseDays+"|");
                CourseSchedule.print(timeSlot+"|");
                CourseSchedule.print(buildingCode+"|");
                CourseSchedule.print(room+"|");
                CourseSchedule.print(comments+"|");
                CourseSchedule.print("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        CourseSchedule.close();
    }

}