package com.package1;

public abstract class Import implements ImportInterface {


    String path;
    String fileType;
    int headersCount;
    Gate gate = new Gate();






    public Gate getGate() {
        return gate;
    }

    public void setGate(Gate gate) {
        this.gate = gate;
    }



    public Import() {

    }


    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }
}
