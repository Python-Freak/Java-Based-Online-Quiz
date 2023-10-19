package org.example.Questions;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.util.List;
public class ReadQuestions {
    private final String filePath;
    List<Question> questions;
    public ReadQuestions(String filePath){
        this.filePath = (filePath != null) ? filePath : "Resources\\questions.csv";
        this.questions = new ArrayList<>();
    }

    public List<Question> read() throws FileNotFoundException {
        System.out.println("Working Directory = " + System.getProperty("user.dir"));
        Scanner sc = new Scanner(new File(this.filePath));
        sc.useDelimiter("\n");
        while (sc.hasNext())
        {
            int i = 0;
            String q = "",a = "",b = "",c = "",d = "";
            char cans = '\0';
            for( String x : sc.next().split(",") ){
                x = x.strip();
                switch (i) {
                    case 0: {
                        q = x;
                    }
                    case 1: {
                        a = x;
                    }
                    case 2: {
                        b = x;
                    }
                    case 3: {
                        c = x;
                    }
                    case 4: {
                        d = x;
                    }
                    case 5: {
                        cans = x.charAt(0);
                    }
                }
                i++;
            }
            Question ques = new Question(q,a,b,c,d, cans);
            this.questions.add(ques);
        }
        sc.close();
        return this.questions;
    }
    public static void main(String[] args) throws FileNotFoundException {
        ReadQuestions rq = new ReadQuestions(null);
        List<Question> questions = rq.read();
        for ( Question q: questions) {
            q.show();
        }
    }
}

