public class Course {
    String title, code, prof;
    int credits;
    Component comp;

    public Course(String line){
        String [] parseLine = line.split("\\|");
        this.title = parseLine[0];
        this.code = parseLine[0];
        this.prof = parseLine[4];
        this.credits = Integer.valueOf(parseLine[3]);
        comp = new Component(parseLine);
    }

    public static void main(String[] args){
        String line = "Making the World Go Round: Children in Machinery of Empire|ANTH 2072 L 1|Fall|5|Mary Porter|W|3:35 PM - 5:00 PM|ANDH|101|Group conference, meets weekly|";
        Course g = new Course(line);
        System.out.println(g.title);
        System.out.println(Time.convertToString(g.comp.time.last));
    }
}
