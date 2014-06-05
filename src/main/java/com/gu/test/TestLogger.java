package com.gu.test;

import java.util.ArrayList;
import java.util.List;

public class TestLogger {

    private List<String> messages = new ArrayList<String>();
    private String name;
    private String phase;

    public TestLogger(String name) {
        this.name = name;
    }

    public void dumpMessages() {
        System.out.println(name);
       for (String s: messages) {
           System.out.println(s);
       }
    }

    public void setPhase(String phase) {
        this.phase = phase;
    }

    public void log(String str) {
        addMessage(phase + " " + str);
        setPhase("AND");
    }

    public void assertion(String msg) {
        addMessage("Assertion :" + msg);
    }

    public void failure(String str) {
        addMessage("Fail: " + str);
    }

    public void driver(String msg) {
        addMessage("Driver: " + msg);
    }

    public void addMessage(String message) {
        messages.add(System.currentTimeMillis() + ": " + message);
    }

}
