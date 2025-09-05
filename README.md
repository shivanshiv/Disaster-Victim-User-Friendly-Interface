# 380 Summative Assignment: How to Run

Just letting you know, I ran all of these commands on my MacBook-- if you are not using the MacBook then please input the Windows-equivalent terminal commands to these. That being said, make sure that you follow these instructions to see how to run the codes for my assignment. 

# Setup
Step #1) Install PostgresSQL 17, and make sure you have SQL shell
Step #2) Open SQL shell, press enter until you see username. Within username, enter 'oop', and press enter. For password, enter 'ucalgary'.
Step #3) Copy and paste the contents in the file project.sql (located in the data folder). 
Step #4) When you see that you are connected to ensf380project, make sure to grant all of the correct permissions from the database to OOP. Run the following commands in the terminal:

    ALTER DATABASE ensf380project OWNER TO oop;
    GRANT ALL PRIVILEGES ON DATABASE ensf380project TO oop;

Step #5) Be sure that you have hamcrest-core-1.3.jar and junit-4.13.2.jar files downloaded. Also, make sure that your database is running on port 5432.
Step #6) After having everything downloaded, place it into a folder called lib. In your IDE, be sure to correctly configure your path. I used IntelliJ IDEA, so these are the settings that I altered to configure my path:
    - Open the project folder using the IDE
    - Create a folder called out (will be used to run the output of the files)
    - Right click on the project, then open module settings
    - In Modules -> Sources, assign test as a test folder, src as a source folder, lib as a test resource folder, data as a resource folder, out as an excluded folder
    - In Modules -> Dependencies, click the + sign and add JARS, then select the lib folder (which contains all the jar files)

# Running the User Friendly Interface
In order to run the user-compatible interface, you can do so manually (using your IDE - just click the 'Run' button), or you can also do so with the command line. This is what I ran through my terminal:

    cd Desktop
    cd 30211537
    javac -d bin src/edu/ucalgary/oop/*.java
    java -cp bin:lib/postgresql-42.7.5.jar edu.ucalgary.oop.UserInterface
    
Then you're ready to run the user interface! Make sure that you specify the language that you would like to use the program in. Some things to note:
    - The xml files HAVE to be in the data directory, please don't change that as my code relies on that
    - Make sure to follow all conventions that are in the program (it will specify). For example, when you enter the gender, make sure it is lowercase.
    
    
Below is a sample output of what you should be getting when you run the program:

    Please specify a language code to run the program in (en-CA for English, fr-CA for French): en-CA

    User Friendly Interface
    1. Add a new Disaster Victim
    2. View or Modify Existing Victim
    3. Log an Inquiry
    4. Allocate a Supply
    5. Exit
    Enter your choice (1-5): 1
    Enter the person's first name: Aditi
    Enter entry date (YYYY-MM-DD): 2025-12-12
    Enter last name (optional, press Enter to skip): Jain
    Enter date of birth (YYYY-MM-DD, optional, press Enter to skip): 2005-01-01
    Enter gender (man/woman/non-binary/other, optional, press Enter to skip): woman
    Enter comments (optional, press Enter to skip): No comments
    1. Select an existing location
    2. Create a new location
    Choose an option (1-2): 1
    Available locations:
    1. TELUS (136 8 Ave SE)
    2. University of Calgary (2500 University Dr NW)
    3. Home (42 Edgeridge Park NW)
    Enter number (or 0 to cancel): 1
    Location is set to TELUS.
    1. Create a new family group
    2. Join an existing family group
    3. No family group
    Choose an option (1-3): 3
    No family group assigned.
    Did this victim receive treatment? (y/n): n
    Aditi entered the TELUS facility on 2025-12-12.

    User Friendly Interface
    1. Add a new Disaster Victim
    2. View or Modify Existing Victim
    3. Log an Inquiry
    4. Allocate a Supply
    5. Exit
    Enter your choice (1-5): 5
    Program Exited.

# Running the Tests
Step #1) Compile the test classes within the test folder:
    javac -cp "lib/junit-4.13.2.jar:lib/hamcrest-core-1.3.jar:bin" -d bin test/edu/ucalgary/oop/*.java
    
Step #2) After compiling, you can just run the tests one by one. You can use the following commands for all the tests:
- BlanketTest
    java -cp "bin:lib/junit-4.13.2.jar:lib/hamcrest-core-1.3.jar" org.junit.runner.JUnitCore edu.ucalgary.oop.BlanketTest
- CotTest
    java -cp "bin:lib/junit-4.13.2.jar:lib/hamcrest-core-1.3.jar" org.junit.runner.JUnitCore edu.ucalgary.oop.CotTest
- DiasterVictimTest
    java -cp "bin:lib/junit-4.13.2.jar:lib/hamcrest-core-1.3.jar" org.junit.runner.JUnitCore edu.ucalgary.oop.DisasterVictimTest 
- FamilyGroupTest
    java -cp "bin:lib/junit-4.13.2.jar:lib/hamcrest-core-1.3.jar" org.junit.runner.JUnitCore edu.ucalgary.oop.FamilyGroupTest
- InquirerTest
    java -cp "bin:lib/junit-4.13.2.jar:lib/hamcrest-core-1.3.jar" org.junit.runner.JUnitCore edu.ucalgary.oop.InquirerTest
- LocationTest
    java -cp "bin:lib/junit-4.13.2.jar:lib/hamcrest-core-1.3.jar" org.junit.runner.JUnitCore edu.ucalgary.oop.LocationTest
- MedicalRecordTest
    java -cp "bin:lib/junit-4.13.2.jar:lib/hamcrest-core-1.3.jar" org.junit.runner.JUnitCore edu.ucalgary.oop.MedicalRecordTest
- PersonalBelongingTest
    java -cp "bin:lib/junit-4.13.2.jar:lib/hamcrest-core-1.3.jar" org.junit.runner.JUnitCore edu.ucalgary.oop.PersonalBelongingTest
- ReliefServiceTest
    java -cp "bin:lib/junit-4.13.2.jar:lib/hamcrest-core-1.3.jar" org.junit.runner.JUnitCore edu.ucalgary.oop.ReliefServiceTest
- SupplyTest
    java -cp "bin:lib/junit-4.13.2.jar:lib/hamcrest-core-1.3.jar" org.junit.runner.JUnitCore edu.ucalgary.oop.SupplyTest
- WaterTest
    java -cp "bin:lib/junit-4.13.2.jar:lib/hamcrest-core-1.3.jar" org.junit.runner.JUnitCore edu.ucalgary.oop.WaterTest

