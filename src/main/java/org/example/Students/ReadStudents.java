package org.example.Students;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ReadStudents {
    private final String filePath;
    List<Student> students;

    public ReadStudents(String filePath){
        this.filePath = (filePath != null) ? filePath : "Resources\\students.csv";
        this.students = new ArrayList<>();
    }

    public List<Student> read() throws FileNotFoundException {
        System.out.println("Working Directory = " + System.getProperty("user.dir"));
        Scanner sc = new Scanner(new File(this.filePath));
        sc.useDelimiter("\n");
        while (sc.hasNext())
        {
            int i = 0;
            String rn = "",p = "";
            for( String x : sc.next().split(",") ){
                x = x.strip();
                switch (i) {
                    case 0: {
                        rn = x;
                    }
                    case 1: {
                        p = x;
                    }
                }
                i++;
            }
            Student student = new Student(rn, p);
            this.students.add(student);
        }
        sc.close();
        return this.students;
    }

    public static void main(String[] args) throws FileNotFoundException {
        ReadStudents rs = new ReadStudents(null);
        List<Student> questions = rs.read();
        for ( Student s: questions) {
            s.show();
            if(s.login("1234")){
                System.out.println("CORRECT");
            }
        }
    }
}
