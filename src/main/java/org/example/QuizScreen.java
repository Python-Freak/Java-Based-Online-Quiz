package org.example;

import org.example.Questions.Question;
import org.example.Questions.ReadQuestions;
import org.example.Students.Student;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


class QuizFrame extends JFrame implements ActionListener {
    private final Container container = getContentPane();
    private final JPanel mainPanel = new JPanel(new BorderLayout());
    private final JPanel navPanel = new JPanel();
    private final JPanel optionPanel = new JPanel();
    private final JPanel buttonPanel = new JPanel();
    private final JTextArea question = new JTextArea();
    private final JRadioButton op1 = new JRadioButton("");
    private final JRadioButton op2 = new JRadioButton("");
    private final JRadioButton op3 = new JRadioButton("");
    private final JRadioButton op4 = new JRadioButton("");
    private final ButtonGroup optionGroup = new ButtonGroup();
    private final JButton submit = new JButton("Submit");
    private final JButton next = new JButton("Next");
    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    private final List<JButton> questionButtons = new ArrayList<>();
    List<Question> questions;
    private int currentQuestionIndex = -1;
    private final Student currentStudent;

    private int score = 0;

    QuizFrame(String title, Student currentStudent) throws IOException {
        super(title);
        this.currentStudent = currentStudent;
        this.questions = new ReadQuestions(null).read();
        next.addActionListener(e -> {
            try {
                nextQuestion();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        submit.addActionListener(e -> {
            if (currentQuestionIndex != -1 && (optionGroup.getSelection() != null)) {
                char givenAns = optionGroup.getSelection().getActionCommand().charAt(0);
                this.questions.get(currentQuestionIndex).setGivenAns(givenAns);
            }
            int score = 0;
            for (Question q : this.questions) {
                if (q.compareAns()) {
                    score++;
                }
            }
            this.score = score;
            try {
                this.setMarks();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            next.setEnabled(false);
            submit.setEnabled(false);
        });
        op1.setActionCommand("A");
        op2.setActionCommand("B");
        op3.setActionCommand("C");
        op4.setActionCommand("D");
        optionPanel.setLayout(new BoxLayout(optionPanel, BoxLayout.Y_AXIS));
        buttonPanel.setPreferredSize(new Dimension(715, 100));
        optionPanel.setPreferredSize(new Dimension(715, 200));
        question.setPreferredSize(new Dimension(685, 100));
        navPanel.setPreferredSize(new Dimension(340, 400));
        initializeComponents();
        setupLayout();
        nextQuestion();
        addQuestionButtons();
        question.setMargin(new Insets(20, 30, 20, 10));
    }

    private void addQuestionButtons() {
        navPanel.setLayout(new BorderLayout());

        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.insets = new Insets(5, 5, 3, 3);

        // Set preferred size for buttons
        Dimension buttonSize = new Dimension(58, 40);

        for (int i = 0; i < questions.size(); i++) {
            JButton questionButton = new JButton("Q" + (i + 1));
            final int fi = i;
            questionButton.addActionListener(e -> {
                try {
                    showQuestion(fi);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });
            questionButton.setPreferredSize(buttonSize);
            constraints.gridx = i % 4;
            constraints.gridy = i / 4;
            buttonPane.add(questionButton, constraints);
            questionButtons.add(questionButton);
        }

        JScrollPane scrollPane = new JScrollPane(buttonPane);
        scrollPane.setBorder(new MatteBorder(0, 2, 0, 0, Color.BLACK));
        navPanel.add(scrollPane, BorderLayout.CENTER);
    }

    private void showQuestion(int index) throws IOException {
        if (index == currentQuestionIndex) {
            return;
        }
        currentQuestionIndex = index;
        updateQuestionDisplay();
    }

    private void updateQuestionDisplay() throws IOException {
        if (currentQuestionIndex < questions.size()) {
            question.setText("Q" + (currentQuestionIndex + 1) + ". " + questions.get(currentQuestionIndex).ques);
            op1.setText("A. " + questions.get(currentQuestionIndex).optA);
            op2.setText("B. " + questions.get(currentQuestionIndex).optB);
            op3.setText("C. " + questions.get(currentQuestionIndex).optC);
            op4.setText("D. " + questions.get(currentQuestionIndex).optD);
        } else {
            int score = 0;
            for (Question q : this.questions) {
                if (q.compareAns()) {
                    score++;
                }
            }
            this.score = score;
            setMarks();
            next.setEnabled(false);
        }
    }

    private void initializeComponents() {
        setStyles();
        setLocationAndSize();
        addComponentsToContainer();
    }

    private void setStyles() {
        question.setWrapStyleWord(true);
        question.setLineWrap(true);
        question.setEditable(false);
        question.setFocusable(false);
        question.setFont(question.getFont().deriveFont(16f));
        question.setBackground(Color.decode("#eeeeee"));
        question.setForeground(Color.BLACK);
        question.setMargin(new Insets(80, 30, 0, 10));
        Insets padding = new Insets(0, 30, 5, 5);
        Font optionFont = question.getFont().deriveFont(16f);
        op1.setMargin(padding);
        op2.setMargin(padding);
        op3.setMargin(padding);
        op4.setMargin(padding);
        op1.setFont(optionFont);
        op2.setFont(optionFont);
        op3.setFont(optionFont);
        op4.setFont(optionFont);
    }

    private void setLocationAndSize() {
        mainPanel.setBounds(0, 0, 710, 400);
        navPanel.setBounds(685, 0, 345, 400);
        optionPanel.setBounds(0, 100, 715, 220);
        buttonPanel.setBounds(0, 320, 715, 150);
        question.setBounds(20, 50, 675, 150);
    }

    private void addComponentsToContainer() {
        container.add(mainPanel);
        container.add(navPanel);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        optionPanel.add(op1);
        optionPanel.add(op2);
        optionPanel.add(op3);
        optionPanel.add(op4);
        buttonPanel.add(submit);
        buttonPanel.add(next);
        optionGroup.add(op1);
        optionGroup.add(op2);
        optionGroup.add(op3);
        optionGroup.add(op4);
    }

    private void setupLayout() {
        container.setLayout(new BorderLayout());
        container.add(mainPanel, BorderLayout.WEST);
        container.add(navPanel, BorderLayout.EAST);
        mainPanel.add(question, BorderLayout.NORTH);
        mainPanel.add(optionPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
    }

    private void setMarks() throws IOException {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String ct = dtf.format(now);
        try (FileWriter fw = new FileWriter("Resources/marksheet.csv", true)){
            fw.write(this.currentStudent.rollNo + ", " + this.score + ", " + ct + "\n");
            for (Component c : mainPanel.getComponents()
                 ) {
                mainPanel.remove(c);
            }
            mainPanel.revalidate();
            mainPanel.repaint();
            mainPanel.setPreferredSize(new Dimension(720, 400));
            mainPanel.setLayout(new GridBagLayout());
            JLabel fi = new JLabel("Responses Recorded ! ");
            fi.setHorizontalAlignment(JLabel.CENTER);
            fi.setVerticalAlignment(JLabel.CENTER);
            fi.setFont(mainPanel.getFont().deriveFont(18f));
            mainPanel.add(fi);
            waitAndDestroy();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void waitAndDestroy() throws InterruptedException {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                dispose();
                System.out.println("DONE");
                System.exit(0);
            }
        }, 3 * 1000);
    }

    private void nextQuestion() throws IOException {
        if (currentQuestionIndex != -1 && (optionGroup.getSelection() != null)) {
            char givenAns = optionGroup.getSelection().getActionCommand().charAt(0);
            this.questions.get(currentQuestionIndex).setGivenAns(givenAns);
        }
        currentQuestionIndex++;
        if (currentQuestionIndex < questions.size()) {
            question.setText("Q" + (currentQuestionIndex + 1) + ". " + questions.get(currentQuestionIndex).ques);
            op1.setText("A. " + questions.get(currentQuestionIndex).optA);
            op2.setText("B. " + questions.get(currentQuestionIndex).optB);
            op3.setText("C. " + questions.get(currentQuestionIndex).optC);
            op4.setText("D. " + questions.get(currentQuestionIndex).optD);
        } else {
            int score = 0;
            for (Question q : this.questions) {
                if (q.compareAns()) {
                    score++;
                }
            }
            this.score = score;
            setMarks();
            next.setEnabled(false);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submit) {
            System.out.println("QUITTING");
        } else if (e.getSource() == next) {
            try {
                nextQuestion();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

}

public class QuizScreen {
    public void show(Student currentStudent) throws IOException {
        Image iconImage = Toolkit.getDefaultToolkit().getImage("Resources/Media/quiz.png");
        QuizFrame quizScreen = new QuizFrame("QUIZ", currentStudent);
        quizScreen.setIconImage(iconImage);
        quizScreen.setVisible(true);
        quizScreen.setBounds(10, 10, 1060, 400);
        quizScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        quizScreen.setResizable(false);
    }
}
