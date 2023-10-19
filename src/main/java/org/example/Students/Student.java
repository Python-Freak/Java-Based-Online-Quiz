package org.example.Students;

import java.util.Objects;

public class Student {
    public String rollNo;
    private final String password;
    public Student(String rollNo, String password){
        this.rollNo = rollNo;
        this.password = password;
    }
    public boolean login(String givenPassword){
        return Objects.equals(this.password, givenPassword);
    }

    public void show(){
        System.out.println("ROLL NO : " + this.rollNo + ", PASSWORD : " + this.password);
    }
}
