//Author: Habib Nasir
//Import statements for all libraries used.
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.*;

//class for making the GUI
public class GUIQuiz implements ActionListener{
    JFrame gWindow; //The main frame

    //Initializing all panels
    JPanel buttonPanel = new JPanel(); // panel for buttons
    JPanel optionPanel = new JPanel(); // panel for the 4 MCQ  options
    JLabel textArea = new JLabel(); // text area for the question

    //initializing all buttons
    private JButton bRestart; // restart button
    private JButton bBack; // back button
    private JButton bNext; // next button
    private JButton bSubmit; // submit button

    //Initializing 4 MCQ radio buttons
    private JRadioButton option1 = new JRadioButton();
    private JRadioButton option2 = new JRadioButton();
    private JRadioButton option3 = new JRadioButton();
    private JRadioButton option4 = new JRadioButton();
    ButtonGroup group = new ButtonGroup(); // Group to hold all 4 radio buttons

    private ArrayList<QuizQuestion> quizGame = new ArrayList<>(); //Array list to store QuizQuestion objects

    private int questioNO = 0; // variable to store the question number
    private int totalPoints = 0; // variable to store the total points
    private final int point = 10; // variable that holds the integer value of one correct answer
    int [] choiceArray = new int [5]; // array to store the answer choice of the user
    int [] correctAnsArray = new int [5]; // array to store the correct answeers
    int [] randomQuestions = new int [] {0,1,2,3,4}; // array to store the random question to display

    Random rand = new Random(); // random variable to make the questions random

    /**
     * This function is responsible to make the GUI and set all the buttons, layouts, panels, etc.
     * It also generates random questions for display
     * @param title displays the title of the GUI
     */
    public void gui(String title) {
        gWindow = new JFrame(title);
        gWindow.setLayout(new BorderLayout());

        gWindow.setSize(500, 500);


        // generating random questions
        for (int j = 0; j<randomQuestions.length; j++) {
            randomQuestions[j] = rand.nextInt(quizGame.size());
            correctAnsArray[j] = quizGame.get(randomQuestions[j]).correctAnswer;
        }

        // Giving values to all buttons and adding them to the Action Listener
        bRestart = new JButton("Restart");
        bRestart.setFont(new Font("SansSerif", Font.PLAIN, 10));
        bRestart.addActionListener(this);

        bBack = new JButton("Back");
        bBack.setFont(new Font("SansSerif", Font.PLAIN, 10));
        bBack.addActionListener(this);

        bNext = new JButton("Next");
        bNext.setFont(new Font("SansSerif", Font.PLAIN, 10));
        bNext.addActionListener(this);

        bSubmit = new JButton("Submit");
        bSubmit.setFont(new Font("SansSerif", Font.PLAIN, 10));
        bSubmit.addActionListener(this);

        textArea.setPreferredSize(new Dimension(550, 200));

        //providing all the options with suitable values corresponding to the question.
        option1 = new JRadioButton(quizGame.get(randomQuestions[questioNO]).getAnswers(0));
        option1.addActionListener(this);

        option2 = new JRadioButton(quizGame.get(randomQuestions[questioNO]).getAnswers(1));
        option2.addActionListener(this);

        option3 = new JRadioButton(quizGame.get(randomQuestions[questioNO]).getAnswers(2));
        option3.addActionListener(this);

        option4 = new JRadioButton(quizGame.get(randomQuestions[questioNO]).getAnswers(3));
        option4.addActionListener(this);


        group.add(option1);
        group.add(option2);
        group.add(option3);
        group.add(option4);

        //adding all the options to the option panel
        optionPanel.setPreferredSize(new Dimension(250, 100));
        optionPanel.setLayout(new GridLayout(2,2));
        optionPanel.add(option1);
        optionPanel.add(option2);
        optionPanel.add(option3);
        optionPanel.add(option4);

        //adding all the buttons to the button panel
        buttonPanel.setPreferredSize(new Dimension(550,200));
        buttonPanel.add(bRestart);
        buttonPanel.add(bBack);
        buttonPanel.add(bNext);
        buttonPanel.add(bSubmit);

        //displaying the first question
        textArea.setText("Question " + (questioNO + 1) + " of 5: " +
                quizGame.get(randomQuestions[questioNO]).getQuestion());
        textArea.setHorizontalAlignment(JLabel.CENTER);
        gWindow.add(textArea, BorderLayout.NORTH);



        gWindow.add(buttonPanel, BorderLayout.SOUTH);
        gWindow.add(optionPanel, BorderLayout.CENTER);


        // initializing the choice array
        for (int i = 0; i < 5; i++) {
            choiceArray[i] = -1;
        }
        gWindow.pack();
        gWindow.setLocationRelativeTo(null);
        gWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gWindow.setVisible(true);


    }


    /**
     * This function makes the actions corresponding to all the buttons.
     * @param e ActionEvent that determines the action done by the user
     */
    public void actionPerformed(ActionEvent e) {
        if (questioNO < 4) {
            // making the action of the next button
            if (e.getSource() == bNext) {
                questioNO++;

                // setting up the question and the corresponding answers
                textArea.setText("Question " + (questioNO + 1) + " of 5 "  +
                        quizGame.get(randomQuestions[questioNO]).getQuestion());
                option1.setText(quizGame.get(randomQuestions[questioNO]).getAnswers(0));
                option2.setText(quizGame.get(randomQuestions[questioNO]).getAnswers(1));
                option3.setText(quizGame.get(randomQuestions[questioNO]).getAnswers(2));
                option4.setText(quizGame.get(randomQuestions[questioNO]).getAnswers(3));

                group.clearSelection();

                // storing the answer selected and making sure it does not change when the user goes next or back
                if (choiceArray[questioNO] == 0) option1.setSelected(true);
                else if (choiceArray[questioNO] == 1) option2.setSelected(true);
                else if (choiceArray[questioNO] == 2) option3.setSelected(true);
                else if (choiceArray[questioNO] == 3) option4.setSelected(true);
            }
        }

        if (e.getSource() == bBack) {
            questioNO--;

            // setting up the question and the corresponding answers
            textArea.setText("Question " + (questioNO + 1) + " of 5 "  +
                    quizGame.get(randomQuestions[questioNO]).getQuestion());
            option1.setText(quizGame.get(randomQuestions[questioNO]).getAnswers(0));
            option2.setText(quizGame.get(randomQuestions[questioNO]).getAnswers(1));
            option3.setText(quizGame.get(randomQuestions[questioNO]).getAnswers(2));
            option4.setText(quizGame.get(randomQuestions[questioNO]).getAnswers(3));
            group.clearSelection();

            // storing the answer selected and making sure it does not change when the user goes next or back
            if (choiceArray[questioNO] == 0) option1.setSelected(true);
            else if (choiceArray[questioNO] == 1) option2.setSelected(true);
            else if (choiceArray[questioNO] == 2) option3.setSelected(true);
            else if (choiceArray[questioNO] == 3) option4.setSelected(true);
        }
        if (e.getSource() == bRestart) {
            questioNO = 0;
            totalPoints = 0;

            //randomizing all the questions again
            for (int a = 0; a<randomQuestions.length; a++) {
                randomQuestions[a] = rand.nextInt(quizGame.size());
                correctAnsArray[a] = quizGame.get(randomQuestions[a]).correctAnswer;
            }

            //initializing the choiceArray again
            for (int b = 0; b < 5; b++) {
                choiceArray[b] = -1;
            }

            // setting up the question and the corresponding answers
            textArea.setText("Question " + (questioNO + 1) + " of 5 "  +
                    quizGame.get(randomQuestions[questioNO]).getQuestion());
            option1.setText(quizGame.get(randomQuestions[questioNO]).getAnswers(0));
            option2.setText(quizGame.get(randomQuestions[questioNO]).getAnswers(1));
            option3.setText(quizGame.get(randomQuestions[questioNO]).getAnswers(2));
            option4.setText(quizGame.get(randomQuestions[questioNO]).getAnswers(3));
            group.clearSelection();
        }


        // taking user input if any of the radio buttons are selected.
        if(option1.isSelected()) {
            choiceArray[questioNO] = 0;
        }
        if(option2.isSelected()) {
            choiceArray[questioNO] = 1;
        }
        if(option3.isSelected()) {
            choiceArray[questioNO] = 2;

        }
        if(option4.isSelected()) {
            choiceArray[questioNO] = 3;
        }

        //Action performed if the user clicks the submit button
        if (e.getSource()==bSubmit) {
            totalPoints = 0;
            for (int a = 0; a<5; a++) {
                //calculating the points
                if (choiceArray[a] == correctAnsArray[a]) {
                    totalPoints += point;
                }
            }
            JOptionPane.showMessageDialog(null, "Congratulations, Your score is " + totalPoints);
        }

        //disabling the next button if the question number is more than 4
        if (questioNO >=4){
            bNext.setEnabled(false);
        }
        else bNext.setEnabled(true);

        //disabling the back button if the question number is less than 0
        if(questioNO <=0){
            bBack.setEnabled(false);
        }
        else bBack.setEnabled(true);
    }


    //QuizQuestion class
    public class QuizQuestion {
        private String questionText;
        private String answers[];
        private int correctAnswer;
        final int MAX_QUESTION = 4;
        QuizQuestion(String qt){
            questionText = qt;
            answers = new String[MAX_QUESTION];
        }

        /**
         * this function adds the answer to the corresponding question
         * @param index the index of the answer
         * @param answerText text to store the answer string
         */
        void addQuestion(int index, String answerText) {
            answers[index] = answerText;
        }

        /**
         * this function sets the integer value corresponding to the correct answer
         * @param answerNumber integer value corresponding to the correct answer
         */
        void setCorrectAnswer(int answerNumber) {
            correctAnswer = answerNumber;
        }

        /**
         * this function returns the text of the question
         * @return the string storing the text of the question
         */
        String getQuestion() {
            return questionText;
        }

        /**
         * this function takes an index value as a parameter and returns the answer value corresponding to it
         * @param i index value for the answer array
         * @return the answer value corresponding to the index
         */
        String getAnswers(int i) {
            return answers[i];
        }
    }

    /**
     * this function reads the file and store the values in the quizQuestion object
     * @throws FileNotFoundException
     */
    public void readFile() throws FileNotFoundException {
        File inputFile = new File("mcq.txt");
        Scanner in = null;

        try {
            in = new Scanner(inputFile);
        } catch (FileNotFoundException e) {
            System.out.println("Files does not exist!");
            e.printStackTrace();
        }
        while (in.hasNext()) {
            String temp = in.nextLine();
            String tempString = "";

            //removing '#' from the question string
            for (int i = 0; i<temp.length(); i++) {
                char x = temp.charAt(i);
                if (x == '#') {
                    i++;
                }
                tempString+=temp.charAt(i);
            }
            QuizQuestion tempQuiz = new QuizQuestion(tempString);


            //removing '>' from the correct answer string
            for (int j = 0; j < tempQuiz.MAX_QUESTION; j++) {
                temp = in.nextLine();
                String tempAns = "";
                for (int i = 0; i<temp.length(); i++) {
                    char x = temp.charAt(i);
                    if (x == '>') {
                        i++;
                        tempQuiz.setCorrectAnswer(j);
                    }
                    tempAns += temp.charAt(i);
                }
                tempQuiz.addQuestion(j, tempAns);
            }
            quizGame.add(tempQuiz);
        }
        in.close();
    }


    // main function
    public static void main(String[] args) throws FileNotFoundException{
        GUIQuiz demo = new GUIQuiz();
        demo.readFile();
        demo.gui("This is the hardest quiz. Be prepared!");
    }
}
