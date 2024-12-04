package com.example.finalcalculatorproj;

// AdvancedOperations class that extends Calculator
class AdvancedOperations extends FinalCalculator {
    private String advancedType;
    private double memory;

    // constructor
    public AdvancedOperations() {
        advancedType = "";
        memory = 0.0;
    }

    // implementing performOperation
    @Override
    double performOperation() {
        return performAdvancedOperation();  // Calls the specific method to handle advanced operations
    }

    // method to perform advanced operations
    double performAdvancedOperation() {
       if (advancedType.equals("SQRT")) {
            setCurrentResult(Math.sqrt(Double.parseDouble(getInput())));
        } else if (advancedType.equals("EXP")) {
            setCurrentResult(Math.pow(Double.parseDouble(getInput()), 2));
        } else {
            System.out.println("Invalid advanced operation type.");
        }
        return getCurrentResult();
    }

    // set advanced operation type
    void setAdvancedType(String type) {
        advancedType = type;
    }

    // get advanced operation type
    String getAdvancedType() {
        return advancedType;
    }

    // memory functions similar to BasicOperations
    void clearMemory() {
        memory = 0.0;
    }

    void storeMemory(double value) {
        memory = value;
    }

    double getMemory() {
        return memory;
    }

}