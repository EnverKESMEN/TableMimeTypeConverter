package com.package1;

import com.package1.Gate;

import java.io.PrintWriter;

public abstract class Export {

    PrintWriter pw;
    String path;
    String fileType;
    String fileName;
    int headersCount;

    public Gate getGate() {
        return gate;
    }

    public void setGate(Gate gate) {
        this.gate = gate;
    }

    Gate gate = new Gate();




    public Export( ) {

    }
}
