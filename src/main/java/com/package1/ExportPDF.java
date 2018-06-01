package com.package1;


import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;



public class ExportPDF extends Export {

    PdfPTable table;
    Document document;

    public ExportPDF() {

    }

    public void gateToPDF(Gate gate, String path, String fileName) throws FileNotFoundException, DocumentException {
        this.gate = gate;
        this.path = path;
        this.fileName = fileName;
        headersCount = gate.headers.length;
        openDocument();
        exportHeadersFromGateToPDF();
        exportValuesFromGateToPDF();


        document.add(table);
        document.close();
    }


    public void gateToPDFWithGroup(Gate gate,String path, String fileName, ArrayList<String> groupNames) throws FileNotFoundException, DocumentException {
        this.gate = gate;
        this.path = path;
        this.fileName = fileName;
        headersCount = gate.headers.length;
        ArrayList<String> hashList = new ArrayList<>();
        List<String> uniqueValues = new ArrayList<>();
        int[] headerIDs = new int[groupNames.size()];
        openDocument();

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

        table = new PdfPTable(gate.headers.length - groupNames.size());

        for (int i = 0; i < gate.headers.length; i++) {
            PdfPCell cell;
            int finalI = i;
            if (!IntStream.of(headerIDs).anyMatch(x -> x == finalI)) {
                cell = new PdfPCell(new Paragraph(gate.headers[i].replaceAll("_"," ")));
                table.addCell(cell);

            }
        }



        for (int i =0; i < uniqueValues.size(); i++) {
            PdfPCell cell = null;

            String[] tmpp = uniqueValues.get(i).split("\\*");
            String headerCell="";
            for (int i1 = 0; i1 < headerIDs.length; i1++) {
                headerCell += gate.headers[headerIDs[i1]].replaceAll("_"," ") + ":" + tmpp[i1]+ "   ";
            }

            cell = new PdfPCell(new Paragraph(headerCell));
            cell.setColspan(headersCount-headerIDs.length);
            table.addCell(cell);




            cell =null;
            for (ArrayList<String> value : gate.values) {

                if(uniqueValues.get(i).equals(value.get(value.size()-1)))
                {

                    for (int j = 0; j < value.size()-1; j++) {
                        int finalI = j;
                        if (!IntStream.of(headerIDs).anyMatch(x -> x == finalI)) {
                            cell = new PdfPCell(new Paragraph(value.get(j)));
                            table.addCell(cell);
                        }
                    }


                }
            }




        }








        document.add(table);
        document.close();

    }


    private void openDocument() throws FileNotFoundException, DocumentException {
        document = new Document();
        // step 2
        PdfWriter.getInstance(document, new FileOutputStream(path+fileName+".pdf"));
        // step 3
        document.open();
        // step 4
    }


    private void exportHeadersFromGateToPDF() throws DocumentException, FileNotFoundException {

        table = new PdfPTable(headersCount);
        PdfPCell cell;
        for (int aw = 0; aw < headersCount; aw++) {
            cell = new PdfPCell(new Paragraph(gate.headers[aw].replaceAll("_"," ")));
            table.addCell(cell);
        }

    }

    private void exportValuesFromGateToPDF() {
        PdfPCell cell;
        for (ArrayList<String> value : gate.values) {
            for (int i = 0; i < value.size(); i++) {
                cell = new PdfPCell(new Paragraph(value.get(i)));
                table.addCell(cell);
            }
        }

    }
}





