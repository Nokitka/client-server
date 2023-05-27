package server;

import common.data.Dragon;

import java.util.Comparator;

/**
 * Class CustomComparator for using custom sorting dragon`s TreeSet
 */
public class CustomComparator implements Comparator<Dragon> {

    /**
     * Firstly method compare object`s names, then - id (two-levels sort)
     */
    @Override
    public int compare(Dragon dragon1, Dragon dragon2) {
        int nameCompare = dragon1.getName().compareToIgnoreCase(dragon2.getName());
        if (nameCompare != 0)
            return nameCompare;
        else
            return Long.compare(dragon1.getId(), dragon2.getId());
    }
}