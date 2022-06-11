package com.prio.pariwisata.model;

import com.prio.pariwisata.service.Client;
import com.prio.pariwisata.service.Service;

public class Model_User {

    public static Model_User sg;
    public static boolean isExist(){
        return Model_User.sg != null;
    }
    public String Bearer = "Bearer ";
    public String token, username, password, email;
    public int id;
    private Client client;

    public Model_User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Client getClient(){
        if (client == null){
            client = Service.getClient().create(Client.class);
        }
        return client;
    }
}
