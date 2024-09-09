package calculator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Calculator {
    private JFrame frame;
    private JTextField textField;
    private String operator = ""; // Stores the current operator
    private double num1 = 0; // First operand
    private double num2 = 0; // Second operand
    private boolean isOperatorClicked = false; // Flag to track operator clicks

    public static void main(String[] args) {
        // Launch the calculator application
        SwingUtilities.invokeLater(() -> new Calculator().createAndShowGUI());
    }

    private void createAndShowGUI() {
        // Create and set up the main application frame
        frame = new JFrame("Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 600);
        frame.setLayout(new BorderLayout());

        // Create a text field for displaying input and results
        textField = new JTextField();
        textField.setFont(new Font("Arial", Font.PLAIN, 36)); // Larger font for better visibility
        textField.setHorizontalAlignment(JTextField.RIGHT); // Align text to the right
        textField.setPreferredSize(new Dimension(400, 100)); // Make the input field larger
        frame.add(textField, BorderLayout.NORTH);

        // Create a panel with GridLayout to hold the calculator buttons
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 4, 5, 5)); // 5 rows, 4 columns layout with padding
        frame.add(panel, BorderLayout.CENTER);

        // Define button labels
        String[] buttonLabels = {
            "C", "", "/", "*", 
            "7", "8", "9", "-",
            "4", "5", "6", "+",
            "1", "2", "3", "=",
            "0", ".", "", "" // Empty slots for spacing
        };

        // Create and add buttons to the panel
        for (String label : buttonLabels) {
            JButton button = new JButton(label);
            button.setFont(new Font("Arial", Font.PLAIN, 24)); // Font size similar to Apple's calculator
            button.setFocusPainted(false);
            button.setPreferredSize(new Dimension(80, 80)); // Uniform button size
            button.addActionListener(new ButtonClickListener());
            panel.add(button);
        }

        // Make the frame visible
        frame.setVisible(true);
    }

    private class ButtonClickListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton source = (JButton) e.getSource();
            String text = source.getText();

            switch (text) {
                case "Delete":
                    // Remove the last character from the text field
                    String currentText = textField.getText();
                    if (currentText.length() > 0) {
                        textField.setText(currentText.substring(0, currentText.length() - 1));
                    }
                    break;

                case "C":
                    // Clear the text field and reset calculation state
                    textField.setText("");
                    operator = "";
                    num1 = 0;
                    num2 = 0;
                    isOperatorClicked = false;
                    break;

                case "=":
                    // Calculate the result based on the operator and operands
                    num2 = Double.parseDouble(textField.getText());
                    double result = calculate(num1, num2, operator);
                    textField.setText(String.valueOf(result));
                    operator = ""; // Reset operator after calculation
                    break;

                case ".":
                    // Handle decimal point input
                    if (!textField.getText().contains(".")) {
                        textField.setText(textField.getText() + text);
                    }
                    break;

                default:
                    if (text.matches("[0-9]")) {
                        // Handle number input
                        if (isOperatorClicked) {
                            textField.setText(text);
                            isOperatorClicked = false;
                        } else {
                            textField.setText(textField.getText() + text);
                        }
                    } else {
                        // Handle operator input
                        if (!operator.isEmpty()) {
                            // If an operator was previously entered, perform the calculation
                            num2 = Double.parseDouble(textField.getText());
                            num1 = calculate(num1, num2, operator);
                            textField.setText(String.valueOf(num1));
                        } else {
                            // Store the first operand
                            num1 = Double.parseDouble(textField.getText());
                        }
                        operator = text; // Set the new operator
                        isOperatorClicked = true;
                    }
                    break;
            }
        }

        private double calculate(double num1, double num2, String operator) {
            // Perform arithmetic operations based on the operator
            switch (operator) {
                case "+":
                    return num1 + num2;
                case "-":
                    return num1 - num2;
                case "*":
                    return num1 * num2;
                case "/":
                    if (num2 != 0) {
                        return num1 / num2;
                    } else {
                        // Show error message for division by zero
                        JOptionPane.showMessageDialog(frame, "Error: Division by zero");
                        return 0;
                    }
                default:
                    return 0;
            }
        }
    }
}
