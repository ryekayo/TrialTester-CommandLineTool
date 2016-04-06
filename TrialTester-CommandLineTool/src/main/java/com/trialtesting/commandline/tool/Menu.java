package com.trialtesting.commandline.tool;
import java.io.IOException;
import java.util.Scanner;
import org.apache.log4j.Logger;

public class Menu 
{
	private static final Logger log = Logger.getLogger(Menu.class);
	
	public static void main(String[] args) throws IOException 
	{
		showMenu();
	}
    public static void showMenu() throws IOException 
    {
        Scanner input = new Scanner(System.in);
        log.debug("Displaying Menu");
        System.out.println("Please Enter:\n1 to View DICOM Tags\n2 to Add DICOM Tags\n3 to Edit DICOM Tags\n4 to exit");
        final int option = input.nextInt();
        switch (option) 
        {
            case 1: 
            	{
                    System.out.println("View DICOM tags");
                    System.out.println("Enter DICOM File Path Name: ");
                    String pathName = input.next();
                    System.out.println("Enter Tag list: ");
                    String pathToTags = input.next();
                
                    ViewDICOMTags vDT = new ViewDICOMTags();
                    vDT.readDICOMObject(pathName);
                    vDT.readFromTextFile(pathToTags);
                    break;
                }
            case 2: 
            	{
                    System.out.println("Add DICOM tags");
                    CreateTags cT = new CreateTags();
                    cT.determineIfIntOrStringTag();
                    break;
                }
            case 3: 
            	{
                    ChangeTags cT2 = new ChangeTags();
                    cT2.promptForTagLocation();
                    break;
                }
            case 4: 
            	{
                    System.exit(0);
                    break;
                }
        }
    }
}
