package org.example.Questions;

public class Question {
    public String ques;
    public String optA;
    public String optB;
    public String optC;
    public String optD;
    private final char correctAns;
    private char givenAns = 'Z';

    public void setGivenAns(char givenAns) {
        this.givenAns = givenAns;
    }


    public Question(String ques, String A, String B, String C, String D, char ans){
        this.ques = ques;
        this.optA = A;
        this.optB = B;
        this.optC = C;
        this.optD = D;
        this.correctAns = ans;
    }

    public boolean compareAns(){
        return this.correctAns == this.givenAns;
    }

    public void show(){
        System.out.println(this.ques);
        System.out.println("A. " + this.optA);
        System.out.println("B. " + this.optB);
        System.out.println("C. " + this.optC);
        System.out.println("D. " + this.optD);
        System.out.println("CANS. " + this.correctAns);
    }
}
