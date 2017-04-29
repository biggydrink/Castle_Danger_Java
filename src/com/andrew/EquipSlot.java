package com.andrew;

public enum EquipSlot {
    LEGS("LEGS"),
    MAINHAND("MAINHAND"),
    BODY("BODY");

    String variableName;

    EquipSlot(String variableName) {
        this.variableName = variableName;
    }

    public String getVariableName() {
        return variableName;
    }
}


