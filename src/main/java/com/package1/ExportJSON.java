package com.package1;

import com.package1.Export;
import com.package1.Gate;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;


public class ExportJSON extends Export {
    public ExportJSON() {

    }



public void gateToJson(Gate gate, String path, String fileName) throws IOException {
    this.gate = gate;
    this.path = path;
    this.fileName = fileName;
    headersCount = gate.headers.length;
    exportFromGateToJSONfile();
}

    private void exportFromGateToJSONfile() throws IOException {



        FileWriter fileWriter = new FileWriter(path + fileName +".JSON");

        fileWriter.write("[");
        for (int i = 0; i < gate.values.size(); i++) {
            JSONObject jsonObject = new JSONObject();
            for (int j=0; j<gate.values.get(i).size(); j++)
            {
                jsonObject.put(gate.headers[j].replaceAll("_"," "),gate.values.get(i).get(j));
            }
            fileWriter.write(jsonObject.toString()+",");
        }

        fileWriter.write("]");
        fileWriter.close();

    }



}
