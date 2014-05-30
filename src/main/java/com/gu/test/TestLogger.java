package com.gu.test;

import java.util.ArrayList;
import java.util.List;

public class TestLogger {

    private List<String> messages = new ArrayList<String>();
    private String name;

    public TestLogger(String name) {
        this.name = name;
    }

    public void addMessage(String message) {
        messages.add(System.currentTimeMillis() + ": " + message);
    }

    public void dumpMessages() {
        System.out.println(name);
       for (String s: messages) {
           System.out.println(s);
       }
    }

    public void given(String str) {
        addMessage("Given " + str);
    }

    public void when(String str) {
        addMessage("When " + str);
    }

    public void then(String str) {
        addMessage("Then " + str);
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
}
