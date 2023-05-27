package client.commands;

import client.ReadManager;
import collections.DragonCollection;
import server.dragon.Dragon;
import superCommand.AbstractCommand;
import superCommand.TypeOfArguments;
import utils.InputData;

/**
 * Class implements command filter_starts_with_description description.
 * Command output elements, which start with input string
 */
//add try+catch
public class FilterStartsWithDescription extends AbstractCommand {

    public FilterStartsWithDescription(String commandName, DragonCollection dragonsCollection, ReadManager inputData) {
        super(commandName, dragonsCollection, inputData);
        this.typeOfArg = TypeOfArguments.STRING;
    }

    @Override
    public void execute(String inputDescription) {

        int count = 0;

        for (Dragon dragon : dragonsCollection.getDragons()) {
            if (inputDescription.length() <= dragon.getDescription().length()) {
                if (inputDescription.equals(dragon.getDescription().substring(0, inputDescription.length()))) {
                    count++;
                    System.out.println(dragon);
                }
            }
        }

        if (count == 0)
            System.out.println("There are no one server.dragon`s description which starts with this string");

    }

    @Override
    public String getCommandInfo() {
        return " output elements, which start with input string";
    }
}
