package edu.ucalgary.oop;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.sql.SQLException;

public class UserInterface {
    private ArrayList<DisasterVictim> victims = new ArrayList<>();
    private ArrayList<Location> locations = new ArrayList<>();
    private ArrayList<Inquirer> inquirers = new ArrayList<>();
    private ArrayList<FamilyGroup> familyGroups = new ArrayList<>();
    private Scanner scanner = new Scanner(System.in);
    private ModifiableDatabaseConnector dbConnector;
    private LanguageSupport languageSupport;
    private static final String DEFAULT_LANGUAGE = "en-CA";

    public UserInterface(String languageCode) {
        this.languageSupport = new LanguageSupport(languageCode);
    }

    public static void main(String[] args) {
        Scanner tempScanner = new Scanner(System.in);
        String languageCode = DEFAULT_LANGUAGE;

        System.out.print("Please specify a language code to run the program in (en-CA for English, fr-CA for French): ");
        String inputLanguage = tempScanner.nextLine().trim();

        if (inputLanguage.isEmpty()) {
            System.out.println("Error: No language code provided. Defaulting to " + DEFAULT_LANGUAGE + ".");
        } else if (!inputLanguage.matches("^[a-z]{2}-[A-Z]{2}$")) {
            System.out.println("Error: Invalid language code format '" + inputLanguage + "'. Language code must be in the format 'xx-XX' (e.g., en-CA, fr-CA). Defaulting to " + DEFAULT_LANGUAGE + ".");
        } else {
            java.io.File languageFile = new java.io.File("data/" + inputLanguage + ".xml");
            if (!languageFile.exists()) {
                System.out.println("Error: Language file for '" + inputLanguage + "' not found at 'data/" + inputLanguage + ".xml'. Defaulting to " + DEFAULT_LANGUAGE + ".");
            } else {
                languageCode = inputLanguage;
            }
        }

        UserInterface ui = new UserInterface(languageCode);
        ui.dbConnector = new ModifiableDatabaseConnector();
        try {
            ui.dbConnector.createConnection();
            ui.dbConnector.loadData(ui.victims, ui.locations, new ArrayList<>());
            ui.runCLI();
        } catch (SQLException e) {
            ui.logError(e);
        } finally {
            try {
                ui.dbConnector.close();
            } catch (SQLException e) {
                ui.logError(e);
            }
        }
    }

    private void runCLI() {
        while (true) {
            displayMainMenu();
            System.out.print(languageSupport.getTranslation("menu_prompt") + " ");
            String choice = scanner.nextLine().trim();

            if (choice.matches("^[1-5]$")) {
                if (choice.equals("1")) {
                    addDisasterVictim();
                } else if (choice.equals("2")) {
                    viewOrModifyVictim();
                } else if (choice.equals("3")) {
                    logInquiry();
                } else if (choice.equals("4")) {
                    allocateSupply();
                } else if (choice.equals("5")) {
                    System.out.println(languageSupport.getTranslation("exit_message"));
                    return;
                }
            } else {
                System.out.println(languageSupport.getTranslation("invalid_option"));
            }
        }
    }

    private void displayMainMenu() {
        System.out.println("\n" + languageSupport.getTranslation("menu_title"));
        System.out.println("1. " + languageSupport.getTranslation("menu_add_victim"));
        System.out.println("2. " + languageSupport.getTranslation("menu_view_modify"));
        System.out.println("3. " + languageSupport.getTranslation("menu_log_inquiry"));
        System.out.println("4. " + languageSupport.getTranslation("menu_allocate_supply"));
        System.out.println("5. " + languageSupport.getTranslation("menu_exit"));
    }

    private void addDisasterVictim() {
        System.out.print(languageSupport.getTranslation("input_firstname") + " ");
        String firstName = scanner.nextLine();
        System.out.print(languageSupport.getTranslation("input_entrydate") + " ");
        String entryDate = scanner.nextLine();

        try {
            DisasterVictim victim = new DisasterVictim(firstName, entryDate);
            System.out.print(languageSupport.getTranslation("input_lastname") + " ");
            String lastName = scanner.nextLine();
            if (!lastName.isEmpty()) victim.setLastName(lastName);

            System.out.print(languageSupport.getTranslation("input_dob") + " ");
            String dob = scanner.nextLine();
            if (!dob.isEmpty()) victim.setDateOfBirth(dob);

            System.out.print(languageSupport.getTranslation("input_gender") + " ");
            String gender = scanner.nextLine();
            if (!gender.isEmpty()) victim.setGender(gender);

            System.out.print(languageSupport.getTranslation("input_comments") + " ");
            String comments = scanner.nextLine();
            if (!comments.isEmpty()) victim.setComments(comments);

            selectLocationForVictim(victim);
            selectFamilyGroupForVictim(victim);

            // Prompt to add a medical record with simplified flow
            System.out.print(languageSupport.getTranslation("received_treatment_prompt") + " ");
            String receivedTreatment = scanner.nextLine().trim();
            if (receivedTreatment.equalsIgnoreCase("y")) {
                System.out.print(languageSupport.getTranslation("input_treatment") + " ");
                String details = scanner.nextLine().trim();
                if (details.isEmpty()) {
                    System.out.println(languageSupport.getTranslation("error_empty_treatment"));
                } else {
                    System.out.print(languageSupport.getTranslation("input_treatment_date") + " (YYYY-MM-DD): ");
                    String date = scanner.nextLine().trim();

                    try {
                        // Use the victim's assigned location for the medical record
                        Location victimLocation = victim.getLocation();
                        if (victimLocation == null) {
                            System.out.println("Error: Victim must have a location assigned before adding a medical record.");
                        } else {
                            MedicalRecord record = new MedicalRecord(victimLocation, details, date);
                            victim.addMedicalRecord(record);
                            dbConnector.addData(record);
                            System.out.println(languageSupport.getTranslation("medical_added"));
                        }
                    } catch (IllegalArgumentException | SQLException e) {
                        System.out.println(languageSupport.getTranslation("error_message", e.getMessage()));
                    }
                }
            }

            victims.add(victim);
            dbConnector.addData(victim);
            System.out.println(languageSupport.getTranslation("report_person",
                    firstName,
                    victim.getLocation() != null ? victim.getLocation().getName() : "unknown",
                    entryDate));
        } catch (IllegalArgumentException | SQLException e) {
            System.out.println(languageSupport.getTranslation("error_message", e.getMessage()));
        }
    }

    private void viewOrModifyVictim() {
        DisasterVictim victim = selectVictim();
        if (victim == null) return;

        while (true) {
            System.out.println("\n" + languageSupport.getTranslation("victim_info",
                    victim.getFirstName(), victim.getLastName(), victim.getAssignedSocialID()));
            System.out.println("1. " + languageSupport.getTranslation("view_details"));
            System.out.println("2. " + languageSupport.getTranslation("add_medical"));
            System.out.println("3. " + languageSupport.getTranslation("modify_location"));
            System.out.println("4. " + languageSupport.getTranslation("modify_family"));
            System.out.println("5. " + languageSupport.getTranslation("back_to_menu"));
            System.out.print(languageSupport.getTranslation("choose_option") + " ");
            String choice = scanner.nextLine().trim();

            if (choice.matches("^[1-5]$")) {
                if (choice.equals("1")) {
                    displayVictimDetails(victim);
                } else if (choice.equals("2")) {
                    addMedicalRecord(victim);
                } else if (choice.equals("3")) {
                    selectLocationForVictim(victim);
                    try {
                        dbConnector.updateData(victim);
                    } catch (SQLException e) {
                        System.out.println(languageSupport.getTranslation("error_message", e.getMessage()));
                    }
                } else if (choice.equals("4")) {
                    selectFamilyGroupForVictim(victim);
                    try {
                        dbConnector.updateData(victim);
                    } catch (SQLException e) {
                        System.out.println(languageSupport.getTranslation("error_message", e.getMessage()));
                    }
                } else if (choice.equals("5")) {
                    return;
                }
            } else {
                System.out.println(languageSupport.getTranslation("invalid_option"));
            }
        }
    }

    private void displayVictimDetails(DisasterVictim victim) {
        System.out.println(languageSupport.getTranslation("detail_firstname", victim.getFirstName()));
        System.out.println(languageSupport.getTranslation("detail_lastname", victim.getLastName()));
        System.out.println(languageSupport.getTranslation("detail_entrydate", victim.getEntryDate()));
        System.out.println(languageSupport.getTranslation("detail_dob", victim.getDateOfBirth()));
        System.out.println(languageSupport.getTranslation("detail_gender", victim.getGender()));
        System.out.println(languageSupport.getTranslation("detail_comments", victim.getComments()));
        System.out.println(languageSupport.getTranslation("detail_location",
                victim.getLocation() != null ? victim.getLocation().getName() : "None"));
        System.out.println(languageSupport.getTranslation("detail_family",
                victim.getFamilyGroup() != null ? victim.getFamilyGroup().getFamilyGroupID() : "None"));

        List<MedicalRecord> medicalRecords = victim.getMedicalRecords();
        if (medicalRecords.isEmpty()) {
            System.out.println(languageSupport.getTranslation("detail_medical", "None"));
        } else {
            System.out.println(languageSupport.getTranslation("detail_medical", ""));
            for (int i = 0; i < medicalRecords.size(); i++) {
                MedicalRecord record = medicalRecords.get(i);
                System.out.println("  Record " + (i + 1) + ":");
                System.out.println("    " + languageSupport.getTranslation("detail_location", record.getLocation().getName()));
                System.out.println("    " + languageSupport.getTranslation("input_treatment") + " " + record.getTreatmentDetails());
                System.out.println("    " + languageSupport.getTranslation("input_treatment_date") + " " + record.getDateOfTreatment());
            }
        }
    }

    private void addMedicalRecord(DisasterVictim victim) {
        System.out.println("\n" + languageSupport.getTranslation("collect_medical_info"));

        Location location = selectOrAddLocation();
        if (location == null) return;

        System.out.print(languageSupport.getTranslation("input_treatment") + " ");
        String details = scanner.nextLine().trim();
        if (details.isEmpty()) {
            System.out.println(languageSupport.getTranslation("error_empty_treatment"));
            return;
        }

        System.out.print(languageSupport.getTranslation("input_treatment_date") + " (YYYY-MM-DD): ");
        String date = scanner.nextLine().trim();

        try {
            MedicalRecord record = new MedicalRecord(location, details, date);
            victim.addMedicalRecord(record);
            dbConnector.addData(record);
            System.out.println(languageSupport.getTranslation("medical_added"));
        } catch (IllegalArgumentException | SQLException e) {
            System.out.println(languageSupport.getTranslation("error_message", e.getMessage()));
        }
    }

    private Location selectOrAddLocation() {
        if (locations.isEmpty()) {
            System.out.println("No existing locations available. Please create a new one.");
            return addLocation();
        }

        System.out.println("1. Select an existing location");
        System.out.println("2. Create a new location");
        System.out.print("Choose an option (1-2): ");
        String choice = scanner.nextLine().trim();

        if (choice.matches("^[1-2]$")) {
            if (choice.equals("1")) {
                System.out.println("Available locations:");
                for (int i = 0; i < locations.size(); i++) {
                    Location loc = locations.get(i);
                    System.out.println((i + 1) + ". " + loc.getName() + " (" + loc.getAddress() + ")");
                }
                System.out.print(languageSupport.getTranslation("select_prompt") + " ");
                String locChoiceStr = scanner.nextLine().trim();
                int locChoice;
                try {
                    locChoice = Integer.parseInt(locChoiceStr);
                } catch (NumberFormatException e) {
                    System.out.println(languageSupport.getTranslation("invalid_input"));
                    return null;
                }
                if (locChoice == 0 || locChoice > locations.size()) {
                    return null;
                }
                return locations.get(locChoice - 1);
            } else {
                return addLocation();
            }
        } else {
            System.out.println("Invalid option. Please enter 1 or 2.");
            return null;
        }
    }

    private void logInquiry() {
        System.out.print(languageSupport.getTranslation("input_inquirer_firstname") + " ");
        String inquirerFirstName = scanner.nextLine();
        System.out.print(languageSupport.getTranslation("input_inquirer_lastname") + " ");
        String inquirerLastName = scanner.nextLine();
        System.out.print(languageSupport.getTranslation("input_phone") + " ");
        String inquirerPhone = scanner.nextLine();

        DisasterVictim missingPerson = selectVictim();
        if (missingPerson == null) return;

        System.out.print(languageSupport.getTranslation("input_inquiry_details") + " ");
        String details = scanner.nextLine();

        try {
            Inquirer newInquirer = new Inquirer(inquirerFirstName, inquirerLastName, inquirerPhone, details);
            newInquirer.setVictimInquirer(missingPerson);
            inquirers.add(newInquirer);
            dbConnector.addData(newInquirer);
            System.out.println(languageSupport.getTranslation("inquiry_logged"));
        } catch (IllegalArgumentException | SQLException e) {
            System.out.println(languageSupport.getTranslation("error_message", e.getMessage()));
        }
    }

    private void allocateSupply() {
        System.out.println(languageSupport.getTranslation("select_supply"));
        System.out.println("1. " + languageSupport.getTranslation("supply_blanket"));
        System.out.println("2. " + languageSupport.getTranslation("supply_cot"));
        System.out.println("3. " + languageSupport.getTranslation("supply_water"));
        System.out.println("4. " + languageSupport.getTranslation("supply_personal"));
        System.out.print(languageSupport.getTranslation("supply_prompt") + " ");
        String typeChoice = scanner.nextLine().trim();

        if (!typeChoice.matches("^[1-4]$")) {
            System.out.println(languageSupport.getTranslation("invalid_supply"));
            return;
        }

        Supply supply;
        if (typeChoice.equals("1")) {
            supply = new Supply("blanket");
        } else if (typeChoice.equals("2")) {
            supply = new Supply("cot");
        } else if (typeChoice.equals("3")) {
            supply = new Supply("water");
        } else {
            System.out.print(languageSupport.getTranslation("input_item_name") + " ");
            String name = scanner.nextLine();
            supply = new Supply(name);
        }

        System.out.print(languageSupport.getTranslation("allocate_to_victim") + " ");
        String allocateChoice = scanner.nextLine().trim();
        if (allocateChoice.equalsIgnoreCase("y")) {
            DisasterVictim victim = selectVictim();
            if (victim == null) return;
            try {
                supply.setAllocation(victim, victim.getLocation());
                dbConnector.addData(supply);
                System.out.println(languageSupport.getTranslation("supply_allocated_victim", victim.getFirstName()));
            } catch (IllegalArgumentException | SQLException e) {
                System.out.println(languageSupport.getTranslation("error_message", e.getMessage()));
            }
        } else {
            Location location = selectOrAddLocation();
            if (location == null) return;
            try {
                supply.setAllocation(null, location);
                dbConnector.addData(supply);
                System.out.println(languageSupport.getTranslation("supply_allocated_location", location.getName()));
            } catch (IllegalArgumentException | SQLException e) {
                System.out.println(languageSupport.getTranslation("error_message", e.getMessage()));
            }
        }
    }

    private DisasterVictim selectVictim() {
        if (victims.isEmpty()) {
            System.out.println(languageSupport.getTranslation("no_victims"));
            return null;
        }
        System.out.println(languageSupport.getTranslation("select_victim"));
        for (int i = 0; i < victims.size(); i++) {
            DisasterVictim v = victims.get(i);
            System.out.println((i + 1) + ". " + languageSupport.getTranslation("victim_list_item",
                    v.getFirstName(), v.getLastName(), v.getAssignedSocialID()));
        }
        System.out.print(languageSupport.getTranslation("select_prompt") + " ");
        String choiceStr = scanner.nextLine().trim();
        int choice;
        try {
            choice = Integer.parseInt(choiceStr);
        } catch (NumberFormatException e) {
            System.out.println(languageSupport.getTranslation("invalid_input"));
            return null;
        }
        if (choice == 0 || choice > victims.size()) {
            return null;
        }
        return victims.get(choice - 1);
    }

    private Location addLocation() {
        System.out.print(languageSupport.getTranslation("input_location_name") + " ");
        String name = scanner.nextLine();
        if (name.trim().isEmpty()) {
            System.out.println(languageSupport.getTranslation("empty_location_name"));
            return null;
        }

        System.out.print(languageSupport.getTranslation("input_location_address") + " ");
        String address = scanner.nextLine();
        if (address.trim().isEmpty()) {
            System.out.println(languageSupport.getTranslation("empty_location_address"));
            return null;
        }

        // Check for duplicate location (same name and address)
        for (Location existingLocation : locations) {
            if (existingLocation.getName().equalsIgnoreCase(name) && existingLocation.getAddress().equalsIgnoreCase(address)) {
                System.out.println("Location already exists: " + name + " (" + address + "). Using existing location.");
                return existingLocation;
            }
        }

        try {
            Location location = new Location(name, address);
            locations.add(location);
            dbConnector.addData(location);
            System.out.println(languageSupport.getTranslation("location_added", name));
            return location;
        } catch (SQLException e) {
            System.out.println(languageSupport.getTranslation("error_message", e.getMessage()));
            return null;
        }
    }

    private void selectLocationForVictim(DisasterVictim victim) {
        Location location = selectOrAddLocation();
        if (location != null) {
            victim.setLocation(location);
            location.addOccupant(victim);
            System.out.println(languageSupport.getTranslation("location_set", location.getName()));
        }
    }

    private void selectFamilyGroupForVictim(DisasterVictim victim) {
        System.out.println("1. " + languageSupport.getTranslation("new_family"));
        System.out.println("2. " + languageSupport.getTranslation("join_family"));
        System.out.println("3. " + languageSupport.getTranslation("no_family"));
        System.out.print(languageSupport.getTranslation("family_prompt") + " ");
        String choice = scanner.nextLine().trim();

        if (choice.matches("^[1-3]$")) {
            if (choice.equals("1")) {
                String newId = "FAM" + (familyGroups.size() + 1);
                FamilyGroup newGroup = new FamilyGroup(newId);
                newGroup.addMember(victim);
                victim.setFamilyGroup(newGroup);
                familyGroups.add(newGroup);
                try {
                    dbConnector.addData(newGroup);
                    System.out.println(languageSupport.getTranslation("family_created", newGroup.getFamilyGroupID()));
                } catch (SQLException e) {
                    System.out.println(languageSupport.getTranslation("error_message", e.getMessage()));
                }
            } else if (choice.equals("2")) {
                if (familyGroups.isEmpty()) {
                    System.out.println(languageSupport.getTranslation("no_families"));
                    return;
                }
                System.out.println(languageSupport.getTranslation("select_family"));
                for (int i = 0; i < familyGroups.size(); i++) {
                    FamilyGroup fg = familyGroups.get(i);
                    System.out.println((i + 1) + ". " + languageSupport.getTranslation("family_list_item",
                            fg.getFamilyGroupID(), fg.getMembers().length));
                }
                System.out.print(languageSupport.getTranslation("select_prompt") + " ");
                String groupChoiceStr = scanner.nextLine().trim();
                int groupChoice;
                try {
                    groupChoice = Integer.parseInt(groupChoiceStr);
                } catch (NumberFormatException e) {
                    System.out.println(languageSupport.getTranslation("invalid_input"));
                    return;
                }
                if (groupChoice == 0 || groupChoice > familyGroups.size()) {
                    return;
                }
                FamilyGroup selectedGroup = familyGroups.get(groupChoice - 1);
                selectedGroup.addMember(victim);
                victim.setFamilyGroup(selectedGroup);
                try {
                    dbConnector.updateData(selectedGroup);
                    System.out.println(languageSupport.getTranslation("family_joined", selectedGroup.getFamilyGroupID()));
                } catch (SQLException e) {
                    System.out.println(languageSupport.getTranslation("error_message", e.getMessage()));
                }
            } else {
                if (victim.getFamilyGroup() != null) {
                    FamilyGroup oldGroup = victim.getFamilyGroup();
                    oldGroup.removeMember(victim);
                    try {
                        dbConnector.updateData(oldGroup);
                    } catch (SQLException e) {
                        System.out.println(languageSupport.getTranslation("error_message", e.getMessage()));
                    }
                }
                victim.setFamilyGroup(null);
                System.out.println(languageSupport.getTranslation("no_family_assigned"));
            }
        } else {
            System.out.println(languageSupport.getTranslation("invalid_family_option"));
        }
    }

    private void logError(Exception e) {
        try (FileWriter writer = new FileWriter("errorlog.txt", true)) {
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            writer.write(timestamp + " - " + e.getMessage() + "\n");
            System.out.println(languageSupport.getTranslation("error_logged"));
            System.exit(1);
        } catch (IOException ex) {
            System.out.println(languageSupport.getTranslation("error_logging_failed",
                    e.getMessage(), ex.getMessage()));
            System.exit(1);
        }
    }
}