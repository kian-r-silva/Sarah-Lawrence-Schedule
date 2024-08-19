public class Component {
    String weekdays, term, comments;
    Time time;
    
    public Component(String[] args) {
        this.weekdays = args[5];
        if (args[2].equals("Fall")) {
            this.term = "F";
        } else if (args[2].equals("Spring")) {
            this.term = "S";
        } else {
            this.term = "Y";
        }
        this.comments = args[9];
        Time t = new Time(args[6]);
        this.time = t;
    }

    public String toString(){
        return String.format("weekdays: %s, term: %s, comments: %s, time: %s", weekdays, term, comments, time.toString());
    }

    
    public static void main(String[] args) {
        String[] comp = {"Making the World Go Round: Children in Machinery of Empire", "ANTH 2072 L 1", "Fall", "5", "Mary Porter", "W", "3:35 PM - 5:00 PM", "ANDH", "101", "Group conference, meets weekly"};
        Component comps = new Component(comp);
        System.out.println(String.format("weekdays: %s, term: %s, comments: %s, time: %s", comps.weekdays, comps.term, comps.comments, comps.time.toString()));
    }
}

