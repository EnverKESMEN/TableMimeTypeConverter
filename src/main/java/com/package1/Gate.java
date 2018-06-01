package com.package1;

import java.util.ArrayList;

public class Gate {

    ArrayList<ArrayList<String>> values = new ArrayList<ArrayList<String>>();
    String[] headers;

    public String[] getHeaders() {
        return headers;
    }

    public void setHeaders(String[] headers) {
        this.headers = headers;
    }

    public ArrayList<ArrayList<String>> getGate() {
        return values;
    }

    public void setGate(ArrayList<ArrayList<String>> gate) {
        this.values = gate;
    }
}
