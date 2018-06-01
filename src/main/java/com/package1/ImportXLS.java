package com.package1;


import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;


public class ImportXLS extends Import {
    POIFSFileSystem fs ;
    HSSFWorkbook wb ;
    HSSFSheet sheet ;
    HSSFRow row;
    HSSFCell cell;
    int rows; // No of rows
    int cols = 0; // No of columns

    public ImportXLS ()  {

    }

    public void createXLSReader() throws IOException {
        fs = new POIFSFileSystem(new FileInputStream(path));
        wb = new HSSFWorkbook(fs);
        sheet = wb.getSheetAt(0);
        rows = sheet.getPhysicalNumberOfRows();



        int tmp = 0;
        for(int i = 0; i < 10 || i < rows; i++) {
            row = sheet.getRow(i);
            if(row != null) {
                tmp = sheet.getRow(i).getPhysicalNumberOfCells();
                if(tmp > cols) cols = tmp;
            }
        }
    }


    public Gate generateGate(String path) throws IOException {
        this.path = path;
        createXLSReader();
        extractHeadersToGate();
        extractValuesToGate();
        fs.close();
        return gate;
    }

    public void extractValuesToGate() {
        for(int r = 1; r < rows; r++) {
            row = sheet.getRow(r);
            ArrayList<String> temp = new ArrayList<String>();
            if(row != null) {
                for(int c = 0; c < cols; c++) {
                    cell = row.getCell((short)c);
                    if(cell != null) {
                        temp.add(cell.toString());
                    }
                }
                gate.values.add(temp);
            }
        }


    }

    public void extractHeadersToGate() throws FileNotFoundException {

   try {
       gate.headers = new String[cols];
            for(int r = 0; r < 1; r++) {
                row = sheet.getRow(r);
                if(row != null) {
                    for(int c = 0; c < cols; c++) {
                        cell = row.getCell((short)c);
                        if(cell != null) {
                            gate.headers[c]=cell.toString().replaceAll(" ","_");
                        }
                    }
                }
            }
        } catch(Exception ioe) {
            ioe.printStackTrace();
        }

    }
}
