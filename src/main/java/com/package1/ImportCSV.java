

package com.package1;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class ImportCSV extends Import {



    public ImportCSV(){
    }

    Scanner scanner;

    public Gate generateGate(String path) throws FileNotFoundException {
        this.path = path;
        createScanner();
        extractHeadersToGate();
        extractValuesToGate();
        scanner.close();
        return gate;
    }


     public void extractValuesToGate() {
        int i=0;
        ArrayList<String> temp = new ArrayList<String>();
        while (scanner.hasNext()){
            if(i==headersCount) {
                i = 0;
                gate.values.add(temp);
                temp = new ArrayList<String>();
            }
            String s = scanner.next();

            if(s.charAt(0) == '\"' || s.charAt(0) == '\r')
                s = s.substring(1,s.length());

            if(s.charAt(0) == '\"' || s.charAt(0) == '\r')
                s = s.substring(1,s.length());

            if(s.charAt(s.length()-1) == '\"' ||s.charAt(s.length()-1) == '\r')
                s = s.substring(0,s.length()-1);


            if(s.charAt(s.length()-1) == '\"' ||s.charAt(s.length()-1) == '\r')
                s = s.substring(0,s.length()-1);
            temp.add(i,s);

            i++;
        }
    }


    private void createScanner() throws FileNotFoundException {
        scanner = new Scanner(new File(path));
        scanner.useDelimiter(",|\\n");
    }

    public void extractHeadersToGate() throws FileNotFoundException {
        String tmp = scanner.nextLine();
        gate.headers = tmp.split(",");

        for (int i = 0; i < gate.headers.length ; i++) {

            if(gate.headers[i].charAt(0) == '\"')
                gate.headers[i] = gate.headers[i].substring(1,gate.headers[i].length()-1).replaceAll(" ","_");

        }

        headersCount = gate.headers.length;
    }


}
