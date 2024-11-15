// Spaces.java
package Catalog;

import DAO.SpacesDAO;
import Lesson.Space;

import java.util.List;

public class Spaces {
    private SpacesDAO spacesDAO;

    public Spaces() {
        this.spacesDAO = new SpacesDAO();
    }

    public List<Space> getSpaces() {
        return spacesDAO.getAllSpaces();
    }

    public void displaySpaces() {
        List<Space> spaces = getSpaces();
        if (spaces == null || spaces.isEmpty()) {
            System.out.println("No spaces available.");
            return;
        }
        for (int i = 0; i < spaces.size(); i++) {
            System.out.println("Space " + (i + 1) + ": " + spaces.get(i).getDetails());
        }
    }
}
