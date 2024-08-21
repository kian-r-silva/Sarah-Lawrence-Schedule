import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class ScheduleGUI extends JFrame {
    private Schedule schedule; // Reference to the Schedule class
    private JTextArea outputArea;
    private JTextField inputField;

    public ScheduleGUI() {
        schedule = new Schedule(); // Initialize the Schedule class
        try {
            schedule.write();
            schedule.buildCourseList("courseList.txt");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading course list: " + e.getMessage());
        }

        // Setup GUI
        setTitle("Course Scheduling System");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Output area
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);
        add(scrollPane, BorderLayout.CENTER);

        // Input area
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BorderLayout());
        inputField = new JTextField();
        inputPanel.add(inputField, BorderLayout.CENTER);
        add(inputPanel, BorderLayout.NORTH);

        // Buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(2, 3));

        JButton viewCatalogButton = new JButton("View Catalog");
        JButton searchCourseButton = new JButton("Search Course");
        JButton addCourseButton = new JButton("Add Course");
        JButton removeCourseButton = new JButton("Remove Course");
        JButton viewScheduleButton = new JButton("View Schedule");
        JButton clearScheduleButton = new JButton("Clear Schedule");

        buttonPanel.add(viewCatalogButton);
        buttonPanel.add(searchCourseButton);
        buttonPanel.add(addCourseButton);
        buttonPanel.add(removeCourseButton);
        buttonPanel.add(viewScheduleButton);
        buttonPanel.add(clearScheduleButton);

        add(buttonPanel, BorderLayout.SOUTH);

        // Add action listeners to buttons
        viewCatalogButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                viewCatalog();
            }
        });

        searchCourseButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                searchCourse();
            }
        });

        addCourseButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addCourse();
            }
        });

        removeCourseButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                removeCourse();
            }
        });

        viewScheduleButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                viewSchedule();
            }
        });

        clearScheduleButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                clearSchedule();
            }
        });
    }

    private void viewCatalog() {
        outputArea.setText(""); // Clear output area
        
        ArrayList<String> CourseTitle = new ArrayList<>(Schedule.CourseListing.keySet());
        Collections.sort(CourseTitle);
        for (String title : CourseTitle) {
            outputArea.append(title + "\n");
        }
    }

    private void searchCourse() {
        String searchTerm = inputField.getText().trim();
        ArrayList<ArrayList<CourseBlock>> found = Schedule.searchCourse(searchTerm);

        outputArea.setText(""); // Clear output area
        if (!found.isEmpty()) {
            for (ArrayList<CourseBlock> foundCourses : found) {
                for (CourseBlock course : foundCourses) {
                    outputArea.append(course.toString() + "\n");
                }
            }
        } else {
            outputArea.setText("Course not found.\n");
        }
    }

    private void addCourse() {
        String courseInput = inputField.getText().trim();
        ArrayList<ArrayList<CourseBlock>> found = Schedule.searchCourse(courseInput);
        System.out.println(found);
        if (found.size() == 1) {
            Schedule.addCourse(found.get(0));
            outputArea.setText(found.get(0).get(0).title + " added to schedule.\n");
        } else if (found.isEmpty()) {
            outputArea.setText("Course not found.\n");
        } else {
            for (ArrayList<CourseBlock> options : found) {
                outputArea.append("Did you mean " + options.get(0).title + "? (y/n)\n");
                String choice = JOptionPane.showInputDialog("Did you mean " + options.get(0).title + "? (y/n)");
                if ("y".equalsIgnoreCase(choice)) {
                    Schedule.addCourse(options);
                    outputArea.setText(options.get(0).title + " added to schedule.\n");
                    break;
                }
            }
        }
    }

    private void removeCourse() {
        if (Schedule.curSchedule.isEmpty()){
            outputArea.setText("No courses in schedule.\n");
        }
        else {
            String courseToRemove = inputField.getText().trim();
            Schedule.removeCourse(courseToRemove);
            outputArea.setText(courseToRemove + " removed from schedule.\n");
        }
    }

    private void viewSchedule() {
        outputArea.setText(""); // Clear output area
        ArrayList<ArrayList<CourseBlock>> week = new ArrayList<ArrayList<CourseBlock>>(Schedule.buildSchedule());
        String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Need to Schedule With Professor"}; 
        for (int i = 0; i < week.size(); i++){
            outputArea.append(days[i] + ":\n");
            for (CourseBlock block : week.get(i)) {
                outputArea.append(String.format("%s %s ;%s ;%s ;%s %s; %s\n", block.title, block.code, block.prof, block.comp.time.toString(), block.comp.buildingCode, block.comp.room, block.comp.comments));
            }
            outputArea.append("\n"); // Add a blank line between days
        }
    }

    private void clearSchedule() {
        Schedule.curSchedule.clear();
        outputArea.setText("Schedule cleared.\n");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new ScheduleGUI().setVisible(true);
            }
        });
    }
}
