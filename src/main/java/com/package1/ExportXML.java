package com.package1;



import org.w3c.dom.Document;
import org.w3c.dom.Element;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;


public  class ExportXML extends Export {
    public ExportXML() {

    }


    public void gateToXML(Gate gate, String path, String fileName ) throws FileNotFoundException {
        this.gate = gate;
        this.path = path;
        this.fileName = fileName;
        headersCount = gate.headers.length;
        removeSpacesFromHeaders();
        createPrintWriter();
        exportValuesFromGateToXML();
    }


    private void removeSpacesFromHeaders()//XML tagda space kabul etmiyor
    {
        for (int i = 0; i < headersCount; i++) {
            gate.headers[i] = gate.headers[i].replaceAll("\\s+", "");

            if(gate.headers[i].charAt(0) == '\"')
                gate.headers[i] = gate.headers[i].substring(1,gate.headers[i].length()-1).replaceAll("_"," ");


        }
    }

    private void exportValuesFromGateToXML() {


        try {

            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("table");
            doc.appendChild(rootElement);

            for (ArrayList<String> strings : gate.values) {
                Element employee = doc.createElement("row");
                rootElement.appendChild(employee);
                for (int i=0;i<headersCount;i++) {
                    Element empName = doc.createElement(gate.headers[i]);
                    empName.appendChild(doc.createTextNode(strings.get(i)));
                    employee.appendChild(empName);
                }

            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(path+fileName+".xml"));
            transformer.transform(source, result);

        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (TransformerException tfe) {
            tfe.printStackTrace();}





    }

    private void createPrintWriter() throws FileNotFoundException {
        pw = new PrintWriter(new File(path+fileName+".xml"));
    }

}



