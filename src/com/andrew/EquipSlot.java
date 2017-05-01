package com.andrew;

public enum EquipSlot {
    LEGS("LEGS","Legs"),
    MAINHAND("MAINHAND","Main Hand"),
    BODY("BODY","Body");

    String variableName;
    String name;

    EquipSlot(String variableName, String name) {
        this.variableName = variableName;
        this.name = name;
    }

    public String getVariableName() {
        return variableName;
    }

    public String getName() {
        return name;
    }
}


