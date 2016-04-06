package com.trialtesting.commandline.tool;
import org.dcm4che2.util.CloseUtils;
import org.dcm4che2.io.DicomOutputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import org.dcm4che2.io.DicomInputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import org.dcm4che2.data.DicomObject;
import java.util.Scanner;
import org.apache.log4j.Logger;

public class CreateTags {

	private static final Logger log = Logger.getLogger(CreateTags.class);
    Scanner input = new Scanner(System.in);
    public static DicomObject dcmObj;
    
    public void determineIfIntOrStringTag() {
    	log.debug("Asking User if Tag will be listed as String or Non-String");
        System.out.println("Please advise if the tag being changed will be numeric (non-String) or a String value: ");
        System.out.println("Please enter: NON-STRING or STRING");
        String answer = input.next();
        if (answer.equals("STRING")) {
            try 
            {
                promptforLocation();
            }
            catch (IOException e) {
                log.error(e);
            }
        }
        else if (answer.equals("NON-STRING")) {
            try 
            {
                promptForIntTags();
            }
            catch (IOException e) 
            {
                log.error(e);
            }
        }
        else {
            System.out.println("Please enter STRING or NON-STRING");
            this.determineIfIntOrStringTag();
        }
    }
    public void promptforLocation() throws IOException {
    	log.debug("Asking user where .dcm file is located");
        System.out.println("Please Enter DICOM File Location: ");
        String dicomLocation = this.input.next();        
        changeToDICOMObject(dicomLocation);
        
        System.out.println("Please Enter Tag (EX: (0010,0010)): ");
        String inputTag = this.input.next();
        System.out.println("Please Enter Value (Enter String values Only!! See VR Code Sheet for Assistance in correct Syntax!!): ");
        String tagValue = this.input.next();
        
        insertTag(inputTag, tagValue);
    }  
    public void promptForIntTags() throws IOException {
    	log.debug("Asking user where .dcm file is located");
        System.out.println("Please Enter DICOM File Location: ");
        String dicomLocation = this.input.next();
        changeToDICOMObject(dicomLocation);
        
        System.out.println("Enter Tag: ");
        String inputForTag = this.input.next();
        System.out.println("Enter Numeric Value: ");
        int number = this.input.nextInt();
        insertTag(inputForTag, number);
    }
    public void changeToDICOMObject(String path) throws IOException {
    	log.debug("Creating DICOM object");
        DicomInputStream din = null;
        try 
        {
            din = new DicomInputStream(new File(path));
            CreateTags.dcmObj = din.readDicomObject();
        }
        catch (IOException e) {
            log.error(e);
        }
        finally 
        {
            din.close();
        }
        System.out.println("Now reading DCM File");
    }   
    public void insertTag(String tag, String value) throws IOException {
    	log.debug("Inserting DICOM tag into .dcm file");
        String formattedTag = tag.replaceAll("[(),]", "");
        int valueofReplaceTag = (int)Long.parseLong(formattedTag, 16);
        CreateTags.dcmObj.putString(valueofReplaceTag, CreateTags.dcmObj.vrOf(valueofReplaceTag), value);
        
        System.out.println("Where is the new DCM file being saved to? Please enter directory (Include File name and Extension): ");
        String directoryPath = this.input.next();
        File out = new File(directoryPath);
        DicomOutputStream dos = new DicomOutputStream(new BufferedOutputStream(new FileOutputStream(out)));
        dos.writeDicomFile(CreateTags.dcmObj);
        CloseUtils.safeClose(dos);
        
        System.out.println("DICOM Tag has been successfully saved.");
        System.out.println("Would you like to add another tag? Type YES or NO");
        String answer = this.input.next();
        if (answer.equals("YES")) {
            promptforLocation();
        }
        else if (answer.equals("NO")) {
            Menu.showMenu();
        }
    }  
    public void insertTag(String tag, int value) throws IOException {
    	log.debug("Inserting DICOM tag into .dcm file");
        String formattedTag = tag.replaceAll("[(),]", "");
        int valueofReplaceTag = (int)Long.parseLong(formattedTag, 16);
        CreateTags.dcmObj.putInt(valueofReplaceTag, CreateTags.dcmObj.vrOf(valueofReplaceTag), value);
        
        System.out.println("Where is the new DCM file being saved to? Please enter directory (Include File name and Extension): ");
        String directoryPath = this.input.next();
        File out = new File(directoryPath);
        DicomOutputStream dos = new DicomOutputStream(new BufferedOutputStream(new FileOutputStream(out)));
        dos.writeDicomFile(CreateTags.dcmObj);
        CloseUtils.safeClose(dos);
        
        System.out.println("DICOM Tag has been successfully saved.");
        System.out.println("Would you like to add another tag? Type YES or NO");
        String answer = this.input.next();
        if (answer.equals("YES")) {
            promptforLocation();
        }
        else if (answer.equals("NO")) {
            Menu.showMenu();
        }
    }
}
