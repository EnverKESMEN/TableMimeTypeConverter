package com.package1;


import  java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.package1.Export;
import com.package1.Gate;
import  org.apache.poi.hssf.usermodel.HSSFSheet;
import  org.apache.poi.hssf.usermodel.HSSFWorkbook;
import  org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;


public class ExportXLS extends Export {

    public ExportXLS() {

    }

    public void gateToXLS(Gate gate, String path, String  fileName) throws IOException {
        this.gate = gate;
        this.path = path;
        this.fileName = fileName;
        headersCount = gate.headers.length;
        exportHeadersAndValuesFromGateToXLS();
    }


    public void gateToXLSWithGroup(Gate gate,String path, String fileName, ArrayList<String> groupNames) throws IOException, DocumentException {

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("ilk sayfa");

        this.gate = gate;
        this.path = path;
        this.fileName = fileName;
        headersCount = gate.headers.length;
        ArrayList<String> hashList = new ArrayList<>();
        List<String> uniqueValues = new ArrayList<>();
        int[] headerIDs = new int[groupNames.size()];


        //Header idleri çıkart
        for (int i = 0; i < groupNames.size(); i++) {
            for (int j = 0; j < headersCount; j++) {
                if (groupNames.get(i).toLowerCase().equals(gate.headers[j].toLowerCase()))
                    headerIDs[i] = j;
            }
        }


        //Verileri sütun sütun listelere at
        for (ArrayList<String> value : this.gate.values) {
            String tmp = "";
            for (int i = 0; i < headerIDs.length; i++) {
                tmp += value.get(headerIDs[i])+"*";
            }
            tmp = tmp.substring(0, tmp.length() - 1);
            value.add(tmp);
            hashList.add(tmp);
        }


        //Sütunlardakı unique değerleri bul

        uniqueValues = hashList.stream().distinct().collect(Collectors.toList());

        int excelCounter=1;

        HSSFRow rowhead = sheet.createRow((short) 0);
        int tmp = 0;
        for (int i = 0; i < gate.headers.length; i++) {
            int finalI = i;
            if (!IntStream.of(headerIDs).anyMatch(x -> x == finalI))
                rowhead.createCell(i - tmp).setCellValue(gate.headers[i].replaceAll("_"," "));
            else
                tmp++;

        }

        for (int i =0; i < uniqueValues.size(); i++) {

            HSSFRow row = sheet.createRow((short) excelCounter++);
            String[] tmpp = uniqueValues.get(i).split("\\*");
            String headerCell="";
            for (int i1 = 0; i1 < headerIDs.length; i1++) {
                headerCell += gate.headers[headerIDs[i1]].replaceAll("_"," ") + ":" + tmpp[i1]+ "   ";
            }

            row.createCell(0).setCellValue(headerCell);

            sheet.addMergedRegion(new CellRangeAddress(
                    excelCounter-1 , //first row (0-based)
                    excelCounter-1, //last row  (0-based)
                    0, //first column (0-based)
                    headersCount -headerIDs.length- 1  //last column  (0-based)
            ));


            for (ArrayList<String> value : gate.values) {
                tmp=0;
                if(uniqueValues.get(i).equals(value.get(value.size()-1)))
                {
                    row = sheet.createRow((short) excelCounter++);
                    for (int j = 0; j < value.size()-1; j++) {
                        int finalI = j;
                        if (!IntStream.of(headerIDs).anyMatch(x -> x == finalI))
                            row.createCell(j - tmp).setCellValue(value.get(j));
                        else
                            tmp++;
                    }

                }
            }




            }




        FileOutputStream fileOut = new FileOutputStream(path+fileName+".xls");
        workbook.write(fileOut);
        fileOut.close();


    }










    private void exportHEadersAndValuesFromGateToXLSWithGroups()
    {

    }


    private void exportHeadersAndValuesFromGateToXLS() throws IOException {

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("ilk sayfa");

        HSSFRow rowhead = sheet.createRow((short)0);
        for (int i = 0; i < gate.headers.length; i++) {
            rowhead.createCell(i).setCellValue(gate.headers[i].replaceAll("_"," "));
        }
        int i=1;
        for (ArrayList<String> value : gate.values) {
            HSSFRow row = sheet.createRow((short)i++);
            for (int j = 0; j < value.size(); j++) {
                row.createCell(j).setCellValue(value.get(j));
            }
        }

        FileOutputStream fileOut = new FileOutputStream(path+fileName+".xls");
        workbook.write(fileOut);
        fileOut.close();

    }







}
