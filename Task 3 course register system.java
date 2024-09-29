import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Course {
    String code;
    String title;
    String description;
    int capacity;
    int enrolled;
    String schedule;

    public Course(String code, String title, String description, int capacity, String schedule) {
        this.code = code;
        this.title = title;
        this.description = description;
        this.capacity = capacity;
        this.enrolled = 0;
        this.schedule = schedule;
    }

    public boolean isAvailable() {
        return enrolled < capacity;
    }

    public void enroll() {
        if (isAvailable()) {
            enrolled++;
        }
    }

    public void drop() {
        if (enrolled > 0) {
            enrolled--;
        }
    }

    @Override
    public String toString() {
        return String.format("%s: %s\nDescription: %s\nCapacity: %d\nEnrolled: %d\nSchedule: %s",
                code, title, description, capacity, enrolled, schedule);
    }
}

class Student {
    String id;
    String name;
    List<Course> registeredCourses;

    public Student(String id, String name) {
        this.id = id;
        this.name = name;
        this.registeredCourses = new ArrayList<>();
    }

    public void registerCourse(Course course) {
        if (course.isAvailable()) {
            course.enroll();
            registeredCourses.add(course);
            System.out.println("Successfully registered for " + course.title);
        } else {
            System.out.println("Course " + course.title + " is full.");
        }
    }

    public void dropCourse(Course course) {
        if (registeredCourses.remove(course)) {
            course.drop();
            System.out.println("Successfully dropped " + course.title);
        } else {
            System.out.println("You are not registered for " + course.title);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Student ID: ").append(id).append("\n");
        sb.append("Name: ").append(name).append("\n");
        sb.append("Registered Courses: ");
        if (registeredCourses.isEmpty()) {
            sb.append("None");
        } else {
            for (Course course : registeredCourses) {
                sb.append(course.code).append(" ");
            }
        }
        return sb.toString();
    }
}

public class CourseRegistrationSystem {
    private static List<Course> courseList = new ArrayList<>();
    private static List<Student> studentList = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        initializeCourses();

        while (true) {
            System.out.println("\n--- Course Registration System ---");
            System.out.println("1. Register Student");
            System.out.println("2. Show Registered Students");
            System.out.println("3. List Courses");
            System.out.println("4. Register for Course");
            System.out.println("5. Drop Course");
            System.out.println("6. Exit");
            System.out.print("Select an option: ");
            int option = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (option) {
                case 1:
                    registerStudent();
                    break;
                case 2:
                    showRegisteredStudents();
                    break;
                case 3:
                    listCourses();
                    break;
                case 4:
                    registerForCourse();
                    break;
                case 5:
                    dropCourse();
                    break;
                case 6:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void initializeCourses() {
        courseList.add(new Course("CS1001", "Introduction To Computer Science", "Basic concepts of computer science.", 30, "Mon/Wed 09:00 AM - 10:00 AM"));
        courseList.add(new Course("GAI1002", "Generative AI", "Introduction To Generative AI And Its Applications.", 25, "Tue/Thu 10:00 AM - 11:00 AM"));
        courseList.add(new Course("JAVA1003", "Java Language ", "Study Of Java Full Stack Developement ", 20, "Mon/Wed 12:00 AM - 14:00 PM"));
    }

    private static void registerStudent() {
        System.out.print("Enter Student ID: ");
        String id = scanner.nextLine();
        System.out.print("Enter Student Name: ");
        String name = scanner.nextLine();
        studentList.add(new Student(id, name));
        System.out.println("Student registered successfully.");
    }

    private static void showRegisteredStudents() {
        System.out.println("\n--- Registered Students ---");
        for (Student student : studentList) {
            System.out.println(student);
            System.out.println();
        }
    }

    private static void listCourses() {
        System.out.println("\n--- Available Courses ---");
        for (Course course : courseList) {
            System.out.println(course);
            System.out.println();
        }
    }

    private static void registerForCourse() {
        System.out.print("Enter Student ID: ");
        String id = scanner.nextLine();
        Student student = findStudent(id);
        if (student == null) {
            System.out.println("Student not found.");
            return;
        }

        System.out.print("Enter Course Code: ");
        String code = scanner.nextLine();
        Course course = findCourse(code);
        if (course == null) {
            System.out.println("Course not found.");
            return;
        }

        student.registerCourse(course);
    }

    private static void dropCourse() {
        System.out.print("Enter Student ID: ");
        String id = scanner.nextLine();
        Student student = findStudent(id);
        if (student == null) {
            System.out.println("Student not found.");
            return;
        }

        System.out.print("Enter Course Code: ");
        String code = scanner.nextLine();
        Course course = findCourse(code);
        if (course == null) {
            System.out.println("Course not found.");
            return;
        }

        student.dropCourse(course);
    }

    private static Student findStudent(String id) {
        for (Student student : studentList) {
            if (student.id.equals(id)) {
                return student;
            }
        }
        return null;
    }

    private static Course findCourse(String code) {
        for (Course course : courseList) {
            if (course.code.equals(code)) {
                return course;
            }
        }
        return null;
    }
}