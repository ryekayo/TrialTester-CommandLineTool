package com.trialtesting.commandline.tool;
import org.dcm4che2.util.CloseUtils;
import org.dcm4che2.io.DicomOutputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;

import org.dcm4che2.io.DicomInputStream;
import java.io.File;
import java.io.IOException;
import org.dcm4che2.data.VR;
import java.util.HashMap;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.dcm4che2.data.DicomObject;
import java.util.Scanner;

public class ChangeTags {
	
    private static final Logger log = Logger.getLogger(ChangeTags.class);
    Scanner input = new Scanner(System.in);
    public static DicomObject dcmObj;
    public static ArrayList<String> list = new ArrayList<String>();
    public static final HashMap<VR, String> stringReplacements = new HashMap<VR, String>();
    public static final HashMap<VR, Integer> intReplacements = new HashMap<VR, Integer>();
    
    public ChangeTags() {
        stringReplacements.put(VR.DA, "20150101");
        stringReplacements.put(VR.DT, "20150101");
        stringReplacements.put(VR.SH, "testingThis");
        stringReplacements.put(VR.AE, "testingThis");
        stringReplacements.put(VR.CS, "testingThis");
        stringReplacements.put(VR.LO, "testingThis");
        stringReplacements.put(VR.PN, "testingThis");
        stringReplacements.put(VR.ST, "testingThis");
        stringReplacements.put(VR.LT, "testingThis");
        stringReplacements.put(VR.UT, "testingThis");
        stringReplacements.put(VR.AS, "25");
        stringReplacements.put(VR.DS, "25");
        stringReplacements.put(VR.TM, "22:00");
        stringReplacements.put(VR.UI, "8.8.8.8");
        intReplacements.put(VR.UL, 25);
        intReplacements.put(VR.SL, 25);
    }  
    public void promptForTagLocation() {
    	try 
    	{
    	    log.debug("Prompting User for Location");
    	    System.out.println("Please Enter DICOM File Location: ");
    	    String dicomLocation = input.next();
    	    changeToDICOMObject(dicomLocation);
    	    System.out.println("From what TXT File are you reading the tags that need changing: ");
    	    String tagChangeText = input.next();
    	    System.out.println(tagChangeText);
    	    readFromTextFile(tagChangeText);
    	}
    	catch(IOException e)
    	{
    	    log.error(e);
    	}
    	catch(NullPointerException a)
    	{
    	    log.error(a);
    	}
    }   
    public void changeToDICOMObject(String path) throws IOException 
    {
        DicomInputStream din = null;
        try {
            din = new DicomInputStream(new File(path));
            dcmObj = din.readDicomObject();
        }
        catch (IOException e) {
            log.error(e);
        }
        catch (NullPointerException a)
        {
            log.error(a);
        }
        finally 
        {
            din.close();
        }
    }    
    public void readFromTextFile(String path) throws IOException {
    	log.debug("Reading from Text File");
    	Scanner read = new Scanner(new File(path));
        while (read.hasNext()) {
            list.add(read.nextLine());
        }
        System.out.println("Reading from tag List....");
        System.out.println("Changing dicom tags...");
        matchTags();
    }  
    public void matchTags() throws IOException {
        try {
        	log.debug("Changing Tags");
            for (int i = 0; i < list.size(); i++) {
                String replaceTag = list.get(i).toString().replaceAll("[(),]", "");
                int valueofReplaceTag = (int)Long.parseLong(replaceTag, 16);
                VR replaceTagVR = dcmObj.vrOf(valueofReplaceTag);
                String s = stringReplacements.get(replaceTagVR);
                if (s != null) {
                    dcmObj.putString(valueofReplaceTag, replaceTagVR, s);
                }
                Integer j = intReplacements.get(replaceTagVR);
                if (j != null) {
                    dcmObj.putInt(valueofReplaceTag, replaceTagVR, j);
                }
            }
            System.out.println("Where is the new DCM file being saved to? Please enter directory (Include File name and Extension): ");
            String directoryPath = input.next();
            File out = new File(directoryPath);
            DicomOutputStream dos = new DicomOutputStream(new BufferedOutputStream(new FileOutputStream(out)));
            dos.writeDicomFile(dcmObj);
            CloseUtils.safeClose(dos);
            System.out.println("Tags have been successfully changed!");
            Menu.showMenu();
        }
        catch (NumberFormatException e) {
            log.error(e);
        }
    }
}
