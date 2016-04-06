# TrialTester-CommandLineTool

Operating Systems:

Scientific Linux/CentOS 6.x
Windows 7


This tool is something I created for my first job outside of College. The Trial Tester is a command line tool that would essentially take in a .dcm file and manipulate the DICOM tags that are inside of it. The library I had used to dissect the file is dcm4che2. The Trial Tester consisted of a menu driven program that will allow you to either View the Tags, Insert the Tags, or Modify the Tags. I will explain each option in greater detail.


# View the Tags

This option used the dcm4che library to store the dcm file into a DICOM object. From there, the program will ask the user to input a text file that will read in the DICOM tags. Once this is done, the program will then ask the user to input a new directory path and file name to store the new text file with it's findings.

# Change the Tags

This option uses teh dcm4che library to store the dcm file into a DICOM object. Once this is done, the user will be prompted to enter in a directory path to a text file that contains the DICOM tags. This option will programmatically change the DICOM tags to a hard-coded String/int value based on it's VR code. Once done, the program will ask the user to enter in a location to store the new DICOM object/file with the changes made.

# Insert the Tag

This option will essentially change the tags based on the provided input of the user. It will use the dcm4che library to store a DICOM object. Then ask the user to enter in the tag numbers to change and to what value it will change it to. Once this is done, the program will ask the user where to store the new DICOM file to.

This program was a very special use case of testing the de-identification process of the software. It was necessary to find a quick way to change DICOM tags and then see if the software that de-identified actually did it's job.
