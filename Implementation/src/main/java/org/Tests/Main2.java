// Main2.java
package org.Tests;

import Authentication.Users;
import Catalog.Lessons;
import Catalog.Offerings;
import Catalog.Spaces;
import User.Administrator;
import User.Client;
import User.Instructor;
import User.User;
import DAO.*;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main2 {

    public static void main(String[] args) {

        // Suppress Hibernate logs
        Logger hibernateLogger = Logger.getLogger("org.hibernate");
        hibernateLogger.setLevel(Level.SEVERE);

        // Create the DAO
        UserDAO userDAO = new UserDAO();
        Client client = new Client("John Dido", "345-456-7890");
        userDAO.saveUser(client);
        }
    }
