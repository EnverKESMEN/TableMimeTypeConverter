package com.package1;

import com.itextpdf.text.DocumentException;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws IOException, DocumentException {



        Gate gate = new Gate();

        ArrayList<String> groupNames = null;
        File f = new File(args[0]);
        String fileName = f.getName();
        fileName = fileName.substring(0,fileName.indexOf("."));
        String inputPath = f.getPath();
        boolean isGroup = false;


        String fileType =  getFileTypeFromPath(args[0]);
        String outputPath = args[1];
        String[] outputFormats = args[2].split(",");


        try {
            isGroup = args[3].equals("-g");

            String[] tmp = args[4].split(",");
            groupNames = new ArrayList<String>(Arrays.asList(tmp));


        }
        catch (Exception e)
        {

        }



        //input settings
        if(fileType.toLowerCase().equals("csv"))
        {

            ImportCSV csv = new ImportCSV();
            gate = csv.generateGate(inputPath);

        }

        if(fileType.toLowerCase().equals("xls"))
        {

            ImportXLS xls = new ImportXLS();
            gate = xls.generateGate(inputPath);

        }


        //output settings
        for (String outputFormat : outputFormats) {

            if(outputFormat.toLowerCase().equals("csv"))
            {

                ExportCSV csv = new ExportCSV();
                csv.gateToCSV(gate,outputPath,fileName);
                System.out.println("CSV başarı ile oluşturuldu");

            }
            else if(outputFormat.toLowerCase().equals("html"))
            {
                ExportHTML htm = new ExportHTML();
                htm.gateToHTML(gate,outputPath,fileName);
                System.out.println("HTML başarı ile oluşturuldu");
            }
            else if(outputFormat.toLowerCase().equals("json"))
            {
                ExportJSON json = new ExportJSON();
                json.gateToJson(gate,outputPath,fileName);
                System.out.println("JSON başarı ile oluşturuldu");
            }
            else if(outputFormat.toLowerCase().equals("pdf"))
            {
                ExportPDF pdf = new ExportPDF();
                if(isGroup)
                {
                    pdf.gateToPDFWithGroup(gate,outputPath,fileName, groupNames);
                    System.out.println("PDF veriler gruplandırılarak oluşturuldu");
                }
                else{
                    pdf.gateToPDF(gate,outputPath,fileName);
                    System.out.println("PDF başarı ile oluşturuldu");

                }


            }

            else if(outputFormat.toLowerCase().equals("xls"))
            {
                ExportXLS xls = new ExportXLS();
                if(isGroup)
                {
                    xls.gateToXLSWithGroup(gate,outputPath,fileName, groupNames);

                    System.out.println("XLS veriler gruplandırılarak oluşturuldu");
                }
                else
                {
                    xls.gateToXLS(gate, outputPath, fileName);
                    System.out.println("XLS başarı ile oluşturuldu");
                }

            }
            else if(outputFormat.toLowerCase().equals("xml"))
            {
                ExportXML xml = new ExportXML();
                xml.gateToXML(gate,outputPath,fileName);
                System.out.println("XML başarı ile oluşturuldu");
            }

        }




    }

    private static String getFileTypeFromPath(String path)
    {
        String fileType ="";
        int i = path.lastIndexOf('.');
        if (i > 0) {
            fileType = path.substring(i+1);
        }

        return fileType;
    }

}



