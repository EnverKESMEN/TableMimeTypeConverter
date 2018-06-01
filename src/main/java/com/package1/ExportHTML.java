package com.package1;

import com.package1.Export;
import com.package1.Gate;

import java.io.FileWriter;
import java.io.IOException;

public class ExportHTML extends Export {


    String htmlTag="<html>\n";
    String htmlTagEND="</html>\n";
    String htmlHeadTag = "<head>\n" +
            "\t\t<title></title>\n" +
            "\t\t<meta http-equiv=Content-type content=\"text/html; charset=UTF-8\">\n" +
            "\t</head>\n";
    String htmlBodyTag = "<body>\n";
    String htmlBodyTagEND = "</body>\n";
    String htmlTableTag = "<table style=\"empty-cells: show;\" cellspacing=\"0\" cellpadding=\"2\">\n";
    String htmlTableTagEND="</table>\n";
    String htmlTHeadTag = "<thead>\n";
    String htmlHeaderTag="<th style=\"width: 130px; font-size:14px;font-weight:400;font-style:normal;background-color:#F8F8F8;color:#555555;border-color:#DDDDDD;text-align:left;border-top-width:1px;border-left-width:1px;border-right-width:1px;border-bottom-width:0px;border-top-style:solid;border-left-style:solid;border-right-style:solid;border-bottom-style:solid;height:30px;\">";
    String htmlHeaderTagEND="</th>\n";
    String htmlTHeadTagEND = "</thead>\n";
    String htmlTBodyTag = "<tbody>\n";
    String htmlTbodyTagEND="</tbody>\n";
    String htmlLineTag ="<tr>\n";
    String htmlLineTagEND="</tr>\n";
    String htmlValuesTag="<td style=\"font-size:14px;font-weight:400;font-style:normal;background-color:#FFFFFF;color:#555555;border-color:#DDDDDD;text-align:left;formatString:;dataType:string;border-top-width:0px;border-left-width:0px;border-right-width:1px;border-bottom-width:1px;border-top-style:solid;border-left-style:solid;border-right-style:solid;border-bottom-style:solid;height:28px;\">";
    String htmlValuesTagEND = "</td>\n";



    public ExportHTML() {
    }

    public void gateToHTML(Gate gate, String path , String fileName) throws IOException {
        this.gate = gate;
        this.path = path;
        this.fileName = fileName;
        headersCount = gate.headers.length;
        exportFromGateToHTML();

    }

    private void exportFromGateToHTML() throws IOException {
        FileWriter fileWriter = new FileWriter(path + fileName +".html");
        fileWriter.write(htmlTag);
        fileWriter.write(htmlHeadTag);
        fileWriter.write(htmlBodyTag);
        fileWriter.write(htmlTableTag);
        fileWriter.write(htmlTHeadTag);

        for (int i = 0; i < gate.headers.length; i++) {
            fileWriter.write(htmlHeaderTag);
            fileWriter.write(gate.headers[i].replaceAll("_"," "));
            fileWriter.write(htmlHeaderTagEND);
        }

        fileWriter.write(htmlTHeadTagEND);
        fileWriter.write(htmlTBodyTag);

        for (int i = 0; i < gate.values.size(); i++) {
            fileWriter.write(htmlLineTag);
            for(int j=0; j< gate.values.get(i).size();j++){

                fileWriter.write(htmlValuesTag);
                fileWriter.write(gate.values.get(i).get(j));

            }
            fileWriter.write(htmlLineTagEND);

        }
        fileWriter.write(htmlTHeadTagEND);
        fileWriter.write(htmlTableTagEND);
        fileWriter.write(htmlBodyTagEND);
        fileWriter.write(htmlTagEND);
        fileWriter.close();


    }





}
