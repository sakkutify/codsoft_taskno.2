import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NumberGuessingGameGUI extends JFrame {
    private int minNumber = 1;
    private int maxNumber = 100;
    private int targetNumber;
    private int attemptsLeft = 10;
    private int roundsWon = 0;

    private JLabel promptLabel;
    private JTextField guessTextField;
    private JButton guessButton;
    private JLabel feedbackLabel;
    private JLabel attemptsLeftLabel;
    private JLabel roundsWonLabel;
    private JButton newRoundButton;

    public NumberGuessingGameGUI() {
        setupUI();
        startNewRound();
    }

    private void setupUI() {
        setTitle("Number Guessing Game");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        promptLabel = new JLabel("Enter your guess (between " + minNumber + " and " + maxNumber + "):");
        guessTextField = new JTextField(10);
        guessButton = new JButton("Guess");
        feedbackLabel = new JLabel();
        attemptsLeftLabel = new JLabel("Attempts left: " + attemptsLeft);
        roundsWonLabel = new JLabel("Rounds won: " + roundsWon);
        newRoundButton = new JButton("Start New Round");

        add(promptLabel);
        add(guessTextField);
        add(guessButton);
        add(feedbackLabel);
        add(attemptsLeftLabel);
        add(roundsWonLabel);
        add(newRoundButton);

        guessButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleGuess();
            }
        });

        newRoundButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                startNewRound();
            }
        });
    }

    private void handleGuess() {
        if (attemptsLeft <= 0) {
            feedbackLabel.setText("Out of attempts! The number was: " + targetNumber);
            guessButton.setEnabled(false);
            return;
        }

        String guessText = guessTextField.getText();
        int guess;
        try {
            guess = Integer.parseInt(guessText);
        } catch (NumberFormatException ex) {
            feedbackLabel.setText("Invalid input. Enter a valid number.");
            return;
        }

        if (guess < minNumber || guess > maxNumber) {
            feedbackLabel.setText("Your guess is outside the valid range.");
        } else if (guess == targetNumber) {
            feedbackLabel.setText("Congratulations! You guessed the number!");
            roundsWon++;
            guessButton.setEnabled(false);
        } else if (guess < targetNumber) {
            feedbackLabel.setText("Too low. Try again.");
        } else {
            feedbackLabel.setText("Too high. Try again.");
        }

        attemptsLeft--;
        attemptsLeftLabel.setText("Attempts left: " + attemptsLeft);
    }

    private void startNewRound() {
        targetNumber = generateRandomNumber(minNumber, maxNumber);
        attemptsLeft = 10;
        feedbackLabel.setText("");
        attemptsLeftLabel.setText("Attempts left: " + attemptsLeft);
        guessTextField.setText("");
        guessButton.setEnabled(true);
        roundsWonLabel.setText("Rounds won: " + roundsWon);
    }

    private int generateRandomNumber(int min, int max) {
        return min + (int) (Math.random() * (max - min + 1));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new NumberGuessingGameGUI().setVisible(true);
            }
        });
    }
}
