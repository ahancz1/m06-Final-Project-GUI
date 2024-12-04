package com.example.finalcalculatorproj;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CalculatorGUI extends Application {
    // label to display current input/result
    private Label displayLabel = new Label("0"); // the "screen" of the calculator

    // create an instance of BasicOperations for basic calculations
    private BasicOperations basicCalculator = new BasicOperations();

    // track the first operand, operator, and current input
    private String currentInput = "";
    private String currentOperator = "";

    // flag to indicate if a calculation has been completed (after pressing equals)
    private boolean calculationComplete = false;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // set the title of the window
        primaryStage.setTitle("Final Project Calculator");

        // create VBox layout to hold the display and buttons
        VBox mainLayout = new VBox();
        mainLayout.setSpacing(10);
        mainLayout.setAlignment(Pos.CENTER);

        // add display label to the top of the layout
        mainLayout.getChildren().add(displayLabel);

        // create the grid for buttons
        GridPane buttonGrid = new GridPane();
        buttonGrid.setHgap(10);
        buttonGrid.setVgap(10);
        buttonGrid.setAlignment(Pos.CENTER);

        // create number buttons (1-9) and add to grid
        for (int i = 0; i <= 9; i++) {
            Button numButton = new Button(String.valueOf(i));
            numButton.setMinSize(50, 50); // set the button size
            final int num = i; // to use in lambda expressions

            // event for number buttons
            numButton.setOnAction(e -> {
                if (calculationComplete) {
                    currentInput = "";
                    displayLabel.setText("0");
                    calculationComplete = false; // reset the flag
                }
                currentInput += num;
                displayLabel.setText(displayLabel.getText().equals("0") ? String.valueOf(num) : displayLabel.getText() + num); // update the display
            });

            if (i == 0) {
                buttonGrid.add(numButton, 1, 3); // add 0 to the middle bottom
            } else {
                int row = (9 - i) / 3; // calculate row based on the number
                int col = (i - 1) % 3; // calculate column based on the number
                buttonGrid.add(numButton, col, row);
            }
        }

        // create the "clear" button and add it to the bottom left
        Button clearButton = new Button("C");
        clearButton.setMinSize(50, 50);
        clearButton.setOnAction(e -> {
            basicCalculator.reset();
            currentInput = "";
            currentOperator = "";
            displayLabel.setText("0");
            calculationComplete = false; // reset calculation flag
        });
        buttonGrid.add(clearButton, 0, 4); // add clear button to bottom left

        // create operation buttons (+, -, *, /) and add to grid
        String[] operations = {"+", "-", "*", "/"};
        for (int i = 0; i < operations.length; i++) {
            String op = operations[i];
            Button opButton = new Button(op);
            opButton.setMinSize(50, 50);

            // event for operation buttons
            opButton.setOnAction(e -> {
                if (!currentInput.isEmpty()) {
                    basicCalculator.setInput(currentInput);
                    basicCalculator.setCurrentResult(Double.parseDouble(currentInput));
                    currentOperator = op;
                    switch (currentOperator) {
                        case "+":
                            basicCalculator.setOperationsType("ADD");
                            break;
                        case "-":
                            basicCalculator.setOperationsType("SUBTRACT");
                            break;
                        case "*":
                            basicCalculator.setOperationsType("MULTIPLY");
                            break;
                        case "/":
                            basicCalculator.setOperationsType("DIVIDE");
                            break;
                    }
                    currentInput = "";
                    displayLabel.setText(basicCalculator.getCurrentResult() + " " + currentOperator + " ");
                    calculationComplete = false;
                }
            });

            buttonGrid.add(opButton, 3, i); // add operation buttons to rightmost column (0, 1, 2, 3)
        }

        // create and add equals button below the operators
        Button equalsButton = new Button("=");
        equalsButton.setMinSize(50, 50);
        equalsButton.setOnAction(e -> {
            if (!currentInput.isEmpty() && !currentOperator.isEmpty()) {

        // prevent division by zero
        if (currentOperator.equals("/") && Double.parseDouble(currentInput) == 0) {
            displayLabel.setText("Error: Cannot divide by zero");
            calculationComplete = true;
            return; // stop
        }
               // continue if not division by 0
                basicCalculator.setInput(currentInput);
                double result = basicCalculator.performOperation();
                displayLabel.setText(String.valueOf(result));
                currentInput = String.valueOf(result); // use result as next input
                calculationComplete = true;
            }
        });
        buttonGrid.add(equalsButton, 3, 4); // place "=" below operator buttons

        // add button grid to the main layout
        mainLayout.getChildren().add(buttonGrid);

        // create and set the scene
        Scene scene = new Scene(mainLayout, 350, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}