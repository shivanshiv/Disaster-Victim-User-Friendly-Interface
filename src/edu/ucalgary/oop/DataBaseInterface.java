package edu.ucalgary.oop;

import java.sql.SQLException;
import java.util.ArrayList;

public interface DataBaseInterface {
    void createConnection() throws SQLException;
    void close() throws SQLException;
    void loadData(ArrayList<DisasterVictim> disasterVictims, ArrayList<Location> locations, ArrayList<ReliefService> reliefServices) throws SQLException;
    void addData(Object data) throws SQLException; // Add this method
    void updateData(Object data) throws SQLException; // Add this method
}