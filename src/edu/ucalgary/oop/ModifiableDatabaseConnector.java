package edu.ucalgary.oop;

import java.sql.*;
import java.util.ArrayList;

public class ModifiableDatabaseConnector implements DataBaseInterface {
    protected Connection databaseConnection;
    protected ResultSet databaseResults;

    public ModifiableDatabaseConnector() {
    }

    @Override
    public void createConnection() throws SQLException {
        try {
            databaseConnection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/ensf380project",
                    "oop",
                    "ucalgary"
            );
            databaseConnection.setAutoCommit(false);
        } catch (SQLException exception) {
            throw new SQLException("Failed to connect to database: " + exception.getMessage());
        }
    }

    @Override
    public void close() throws SQLException {
        if (databaseResults != null) databaseResults.close();
        if (databaseConnection != null) databaseConnection.close();
    }

    @Override
    public void loadData(ArrayList<DisasterVictim> victims, ArrayList<Location> locations, ArrayList<ReliefService> inquiries) throws SQLException {
        Statement locationStatement = databaseConnection.createStatement();
        databaseResults = locationStatement.executeQuery("SELECT * FROM Location");
        while (databaseResults.next()) {
            String locationName = databaseResults.getString("name");
            String locationAddress = databaseResults.getString("address");
            locations.add(new Location(locationName, locationAddress));
        }
        databaseResults.close();
        locationStatement.close();

        Statement personStatement = databaseConnection.createStatement();
        databaseResults = personStatement.executeQuery("SELECT * FROM Person");
        while (databaseResults.next()) {
            int personId = databaseResults.getInt("person_id");
            String firstName = databaseResults.getString("first_name");
            String lastName = databaseResults.getString("last_name");
            Date dateOfBirth = databaseResults.getDate("date_of_birth");
            String gender = databaseResults.getString("gender");
            String comments = databaseResults.getString("comments");

            DisasterVictim victim = new DisasterVictim(firstName, "2025-01-01");
            victim.setLastName(lastName);
            if (dateOfBirth != null) victim.setDateOfBirth(dateOfBirth.toString());

            try {
                victim.setGender(normalizeGender(gender));
            } catch (IllegalArgumentException exception) {
                victim.setGender("other");
            }

            victim.setComments(comments);

            Statement personLocationStatement = databaseConnection.createStatement();
            ResultSet personLocationResultSet = personLocationStatement.executeQuery(
                    "SELECT location_id FROM PersonLocation WHERE person_id = " + personId
            );
            if (personLocationResultSet.next()) {
                int locationId = personLocationResultSet.getInt("location_id");
                String locationName = getLocationNameById(locationId);
                for (Location location : locations) {
                    if (location.getName().equals(locationName)) {
                        victim.setLocation(location);
                        location.addOccupant(victim);
                        break;
                    }
                }
            }
            personLocationResultSet.close();
            personLocationStatement.close();

            victims.add(victim);
        }
        databaseResults.close();
        personStatement.close();
    }

    @Override
    public void addData(Object data) throws SQLException {

    }


    @Override
    public void updateData(Object data) throws SQLException {

    }


    private String normalizeGender(String gender) {
        if (gender == null) return "other";
        String normalizedGender = gender.trim().toLowerCase();

        if (normalizedGender.equals("male") || normalizedGender.equals("m")) {
            return "man";
        } else if (normalizedGender.equals("female") || normalizedGender.equals("f")) {
            return "woman";
        } else if (normalizedGender.equals("nonbinary") || normalizedGender.equals("non-binary") || normalizedGender.equals("nb")) {
            return "non-binary";
        } else if (normalizedGender.equals("other")) {
            return "other";
        } else {
            throw new IllegalArgumentException("Invalid gender: " + gender);
        }
    }

    private int getLocationIdByName(String locationName) throws SQLException {
        String query = "SELECT location_id FROM Location WHERE name = ?";
        PreparedStatement preparedStatement = databaseConnection.prepareStatement(query);
        preparedStatement.setString(1, locationName);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            int locationId = resultSet.getInt("location_id");
            resultSet.close();
            preparedStatement.close();
            return locationId;
        }
        resultSet.close();
        preparedStatement.close();
        throw new SQLException("Location not found: " + locationName);
    }

    private String getLocationNameById(int locationId) throws SQLException {
        String query = "SELECT name FROM Location WHERE location_id = ?";
        PreparedStatement preparedStatement = databaseConnection.prepareStatement(query);
        preparedStatement.setInt(1, locationId);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            String locationName = resultSet.getString("name");
            resultSet.close();
            preparedStatement.close();
            return locationName;
        }
        resultSet.close();
        preparedStatement.close();
        return null;
    }

    public static void main(String[] args) {
        ModifiableDatabaseConnector connector = new ModifiableDatabaseConnector();
        ArrayList<DisasterVictim> victims = new ArrayList<>();
        ArrayList<Location> locations = new ArrayList<>();
        ArrayList<ReliefService> inquiries = new ArrayList<>();

        try {
            connector.createConnection();
            connector.loadData(victims, locations, inquiries);
            connector.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
}
