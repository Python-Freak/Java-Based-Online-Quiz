package org.example;

import org.example.Students.ReadStudents;
import org.example.Students.Student;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

import java.awt.Image;
import java.awt.Toolkit;

class LoginFrame extends JFrame implements ActionListener {
    Container container = getContentPane();
    JLabel userLabel = new JLabel("ROLL NUMBER : ");
    JLabel passwordLabel = new JLabel("PASSWORD : ");
    JTextField userTextField = new JTextField();
    JPasswordField passwordField = new JPasswordField();
    JButton loginButton = new JButton("LOGIN");
    JButton resetButton = new JButton("RESET");
    JLabel errorLabel = new JLabel("", SwingConstants.CENTER);
    List<Student> students;

    LoginFrame() throws FileNotFoundException {
        errorLabel.setForeground(Color.RED);
        errorLabel.setHorizontalAlignment(SwingConstants.CENTER);
        errorLabel.setVerticalAlignment(SwingConstants.CENTER);
        this.students = new ReadStudents(null).read();
        loginButton.addActionListener(e -> {
            String rollNo = userTextField.getText().toUpperCase();
            String password = new String(passwordField.getPassword());
            System.out.println(rollNo + ", " + password);
            Student current = null;
            for (Student s : students) {
                if (Objects.equals(s.rollNo, rollNo)) {
                    current = s;
                }
            }
            if (current != null) {
                if (current.login(password)) {
                    System.out.println("LOGGED IN");
                    QuizScreen ls = null;
                    ls = new QuizScreen();
                    try {
                        ls.show(current);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                } else {
                    errorLabel.setText("WRONG PASSWORD");
                }
            } else {
                errorLabel.setText("NO USER EXISTS");
            }
        });

        resetButton.addActionListener(e -> {
            userTextField.setText("");
            passwordField.setText("");
            errorLabel.setText("");
        });

        setLayoutManager();
        setLocationAndSize();
        addComponentsToContainer();
    }

    public void setLayoutManager() {
        container.setLayout(null);
    }

    public void setLocationAndSize() {
        userLabel.setBounds(50, 50, 100, 30);
        passwordLabel.setBounds(50, 120, 100, 30);
        userTextField.setBounds(200, 50, 150, 30);
        passwordField.setBounds(200, 120, 150, 30);
        loginButton.setBounds(50, 200, 100, 30);
        resetButton.setBounds(230, 200, 100, 30);
        errorLabel.setBounds(40, 240, 300, 20);
    }

    public void addComponentsToContainer() {
        container.add(userLabel);
        container.add(passwordLabel);
        container.add(userTextField);
        container.add(passwordField);
        container.add(loginButton);
        container.add(resetButton);
        container.add(errorLabel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}

public class LoginScreen {
    public LoginScreen() throws FileNotFoundException {
        Image iconImage = Toolkit.getDefaultToolkit().getImage("Resources/Media/quiz.png");
        LoginFrame frame = new LoginFrame();
        frame.setIconImage(iconImage);
        frame.setTitle("Student Login");
        frame.setVisible(true);
        frame.setBounds(10, 10, 400, 350);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
    }

}

