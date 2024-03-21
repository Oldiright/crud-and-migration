package org.example.service;

import db.dto.Client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ClientService {
    private static final String CREATE_CLIENT = "INSERT INTO client(name) VALUES(?)";
    private static final String CLIENTE_BY_ID = "SELECT name FROM CLIENT WHERE id = ?";
    private static final String  SET_NAME_BY_ID_TEMPLATE = "UPDATE client SET name=? WHERE id=?";
    private static final String DELETE_CLIENT = "DELETE FROM client WHERE id=?";
    private static final String GET_ALL_CLIENTS = "SELECT id, name FROM client";
    private Connection connection;

public ClientService(Connection connection) {
    this.connection = connection;
}


    public long create(String name) {

        try(PreparedStatement pr = connection.prepareStatement(CREATE_CLIENT, Statement.RETURN_GENERATED_KEYS)) {
            validateClientName(name);
            pr.setString(1, name);
            pr.executeUpdate();
            ResultSet rs = pr.getGeneratedKeys();
            while(rs.next()) {
                return rs.getLong("id");
            }
        } catch (SQLException | IllegalArgumentException ex) {
            ex.printStackTrace();
        }


        return -1L;
    }

    public String getById(long id)  throws IllegalArgumentException {
        try(PreparedStatement pr = connection.prepareStatement(CLIENTE_BY_ID)) {
            validateClientId(id);
            pr.setLong(1, id);
            pr.execute();
            ResultSet rs = pr.getResultSet();
            while(rs.next()) {
                return rs.getString("name");
            }
        } catch (SQLException | IllegalArgumentException ex) {
            ex.printStackTrace();
        }
        return "Client not found";
    }

    public void setName(long id, String name) throws IllegalArgumentException{
        try(PreparedStatement pr = connection.prepareStatement(SET_NAME_BY_ID_TEMPLATE)) {
            validateClientName(name);
            validateClientId(id);
            pr.setLong(2, id);
            pr.setString(1, name);
            if(pr.executeUpdate() == 1) {
                System.out.println("Update was successful");
            } else {
                System.out.println("client not found");
            }

        } catch  (SQLException | IllegalArgumentException ex) {
            ex.printStackTrace();
        }
    }

    public  void deleteById(long id) {

        try(PreparedStatement pr = connection.prepareStatement(DELETE_CLIENT)) {
            validateClientId(id);
            pr.setLong(1, id);
            if(pr.executeUpdate() == 1) {
                System.out.println("Delete was successful");
            } else {
                System.out.println("client not found");
            }

        } catch  (SQLException | IllegalArgumentException ex) {
            ex.printStackTrace();
        }



    }

    public List<Client> listAll() {
        List<Client> resultList = new ArrayList<>();
        try {
            PreparedStatement pr = connection.prepareStatement(GET_ALL_CLIENTS);
            ResultSet  rs = pr.executeQuery();
            while (rs.next()) {
                resultList.add(new Client(rs.getLong(1), rs.getString(2)));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resultList;
    }
    private void validateClientName(String name) throws  IllegalArgumentException {
        int nameLength = name.length();
        if(nameLength < 2 || nameLength > 1000) {

            throw new IllegalArgumentException("incorrect name");
        }
    }

    private void validateClientId(Long id) throws IllegalArgumentException {
        if (id == null || id < 0) {
            throw new IllegalArgumentException("incorrect ID");
        }
    }







}
