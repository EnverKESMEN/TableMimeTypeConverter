package com.package1;
import com.package1.Export;
import com.package1.Gate;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class ExportCSV extends Export {

    public ExportCSV() {
    }

    public void gateToCSV(Gate gate, String  path, String fileName) throws FileNotFoundException {
        this.gate = gate;
        this.path = path;
        this.fileName = fileName;
        headersCount = gate.headers.length;
        createPrintWriter();
        exportHeadersFromGateToCSV();
        exportValuesFromGateToCSV();
        pw.close();
    }

    private void exportHeadersFromGateToCSV() {
        int i=0;
        for (String header : gate.headers) {
            i++;
            if(isEndOfLine(i)) {
                pw.write(header.replaceAll("_"," "));
                i=0;
            }
            else
                pw.write(header+",");
        }

        pw.write("\n");
    }


    private void createPrintWriter() throws FileNotFoundException {
        pw = new PrintWriter(new File(path + fileName + ".csv"));
    }


    private void exportValuesFromGateToCSV() throws FileNotFoundException {
        int i=0;
        for (ArrayList<String> strings : gate.values) {
            for (String x : strings) {
                i++;
                if(isEndOfLine(i)) {
                    pw.write(x);
                    i=0;
                }
                else
                    pw.write(x +",");
            }

            pw.write("\n");
        }
        pw.close();
    }

    private boolean isEndOfLine(int i)
    {
        if (i==headersCount)
            return true;
        else
            return false;
    }

}
