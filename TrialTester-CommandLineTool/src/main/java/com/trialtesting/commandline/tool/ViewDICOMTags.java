package com.trialtesting.commandline.tool;
import java.util.TreeMap;
import java.io.Writer;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Map;
import java.io.FileNotFoundException;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import org.dcm4che2.io.DicomInputStream;
import java.io.File;
import java.util.Scanner;
import org.dcm4che2.data.DicomObject;
import org.apache.log4j.Logger;
import org.dcm4che2.data.DicomElement;
import java.util.Iterator;
import java.util.HashMap;

public class ViewDICOMTags 
{
    private static final Logger log = Logger.getLogger(ViewDICOMTags.class);
    HashMap<String, String> dicomFile = new HashMap<String,String>();
    HashMap<String, String> dicomTagList = new HashMap<String, String>();
    HashMap<String, String> Result = new HashMap<String,String>();
    Iterator<DicomElement> iter;
    DicomObject working;
    public static DicomElement element;
    DicomElement elementTwo;
    public static String result;
    Scanner input = new Scanner(System.in);
        
    public void readDICOMObject(String path) throws IOException 
    {
    	log.debug("Reading in DICOM object");
        DicomInputStream din = null;
        din = new DicomInputStream(new File(path));
        try 
        {
            working = din.readDicomObject();
            iter = working.iterator();
            while (iter.hasNext()) 
            {
                element = iter.next();
                result = element.toString();
                String s = element.toString().substring(0, Math.min(element.toString().length(), 11));
                dicomFile.put(String.valueOf(s.toString()), element.toString());
            }
            System.out.println("Collected tags, VR Code, and Description from DICOM file....");
        }
        catch (IOException e) 
        {
            log.error(e);
        }
        catch (NullPointerException a)
        {
        	log.error(a);
        }
        finally {
                din.close();
        }
    }  
    public void readFromTextFile(String path) throws IOException 
    {
        try 
        {
            log.debug("Reading From Text File");
            File dicomList = new File(path);
            String dicomData = "DICOM";
            String line = null;
            BufferedReader bReader = new BufferedReader(new FileReader(dicomList));
            while ((line = bReader.readLine()) != null) 
            {
            	System.out.println(line + dicomData);
                dicomTagList.put(line, dicomData);
            }
            log.debug("Reading Tags from Text File....");
            bReader.close();
        }
        catch (FileNotFoundException e) 
        {
        	log.error(e);
        }
        catch (IOException i) 
        {
            log.error(i);
        }
        compareDICOMSets();
    }   
    public void compareDICOMSets() throws IOException 
    {
    	log.debug("Making Comparison of DICOM data");
        for (Map.Entry<String, String> entry : dicomFile.entrySet()) 
        {
            String s = entry.toString().substring(0, Math.min(entry.toString().length(), 11));
            if (dicomTagList.containsKey(entry.getKey())) 
            {
            	System.out.println(s);
                Result.put(s, entry.getValue());
            }
        }
        orderResults();
        Menu.showMenu();
    }   
    public void orderResults() throws IOException 
    {
    	log.debug("Ordering DICOM data");
        System.out.println("Please enter directory where you want results saved to (Make sure to add filename and extension): ");
        String location = input.next();
        FileWriter fs = new FileWriter(location);
        BufferedWriter bw = new BufferedWriter(fs);
        Map<String, String> ordered = new TreeMap<String, String>(Result);
        for (Map.Entry<String, String> entry : ordered.entrySet()) 
        {
            String v = entry.getValue();
            bw.write(v + "\r\n");
        }
        bw.close();
        log.debug("File is Created");
    }
}
