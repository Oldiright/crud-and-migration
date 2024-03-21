package org.example;

import db.Database;
import org.example.service.ClientService;

import java.sql.Connection;

public class Main {
    public static void main(String[] args){
        Connection connection = Database.getInstance().getConnection();
        ClientService clientService = new ClientService(connection);
        clientService.create("Old boys");
        clientService.deleteById(8);
        System.out.println(clientService.getById(8));
        System.out.println(clientService.listAll());



    }
}