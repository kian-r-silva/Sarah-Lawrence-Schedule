//Kian Silva Data Structures and Algorithms
//Creates the CourseBlock Object for each course time

public class CourseBlock {
    String title, code, prof;
    int credits;
    Component comp;
    //Parses eachline in the courselist.txt
    public CourseBlock(String line){
        String [] parseLine = line.split("\\|");

        // Check if parseLine is empty
        if (parseLine.length < 10) {
            return;
        }

        this.title = parseLine[0];
        this.code = parseLine[1];
        this.prof = parseLine[4];
        this.credits = Integer.valueOf(parseLine[3]);
        comp = new Component(parseLine);
    }

    //toString method for when a courseBlock is printed
    public String toString(){
        return String.format("CourseTitle: %s\nCourseCode: %s\nProfessor: %s\n%s\n ", title,code,prof,comp);
    }
}

