package com.package1;


import java.io.FileNotFoundException;
import java.io.IOException;

public interface ImportInterface  {
    public Gate generateGate(String path) throws IOException;
    public void extractValuesToGate();
    public void extractHeadersToGate() throws FileNotFoundException;
}
