//Kian Silva Data Structures and Algorithms
//Creates the component object that is attached to each courseBlock, contains the extra details about the course
public class Component{
    String weekdays, term, comments, buildingCode, room;
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
        this.buildingCode = args[7];
        this.room = args[8];
        commentType(args[9]);
        if (args[6].trim().isEmpty()){
            this.time = null;
        } else {
            Time t = new Time(args[6]);
            this.time = t;
        }
    }

    //generalizes the commenttype to simplify the sorting later for conflicts
    public void commentType(String comment){
        if (comment.contains("roup")){
            this.comments = "Group Conference";
        } else if(comment.contains("ab")){
            this.comments = "Lab";
        } else {
            this.comments = "Open";
        }
    }

    //to String method
    public String toString(){
        return String.format("weekdays: %s\nterm: %s\ncomments: %s\ntime: %s", weekdays, term, comments, time != null ? time.toString() : "No time specified");
    }
}