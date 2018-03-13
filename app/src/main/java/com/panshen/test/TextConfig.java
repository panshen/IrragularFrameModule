package com.panshen.test;

import android.graphics.Color;

public class TextConfig extends Config {

    private String textContent = "";
    private int textColor = Color.BLACK;

    public String getTextContent() {
        return textContent;
    }

    public void setTextContent(String textContent) {
        this.textContent = textContent;
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }
}
