//Kian Silva Data Structures and Algorithms
//Creates time object to be used in the component class

public class Time{
    public int first;
    public int last;

    public Time(String t) {
        String[] split = t.split("-");
        if (split.length < 2) {
            first = 0;
            last = 0;
            return;
        }
        first = convert(split[0]);
        last = convert(split[1]);
        
    }

    //converts the time formating to military time
    public int convert(String time) {
        String[] parts = time.trim().split(":|\\s+");

        int hour = Integer.parseInt(parts[0]);
        int minute = 0;
        String period = "";

        if (parts.length > 1 && !"AM".equals(parts[1]) && !"PM".equals(parts[1])) {
            minute = Integer.parseInt(parts[1]);
            if (parts.length > 2) {
                period = parts[2];
            }
        } else if (parts.length > 1 && ("AM".equals(parts[1]) || "PM".equals(parts[1]))) {
            period = parts[1];
        }

        if ("PM".equals(period) && hour != 12) {
            hour += 12;
        } else if ("AM".equals(period) && hour == 12) {
            hour = 0;
        }

        return hour * 100 + minute;
    }

    //toString method for the time
    public String toString() {
        return convertToString(first) + " - " + convertToString(last);
    }

    //Converts the time to a string
    public static String convertToString(int time) {
        int hour = time / 100;
        int minute = time % 100;
        String period = "AM";
        if (hour >= 12) {
            period = "PM";
            if (hour > 12) {
                hour -= 12;
            }
        }

        return String.format("%02d:%02d %s", hour, minute, period);
    }

    //checks the conflict of the times
    public boolean conflict(Time other) {
        if (this.last < other.first || this.first > other.last) {
            return false; // No overlap
        }
        return true; // Overlap
    }
    //comparison of times
    public int compareTo(Time other){
        if (this.first < other.first) return -1;
        if (this.first > other.first) return 1;
        return 0;
    }
}

