package client.commands;

import client.ReadManager;
import collections.DragonCollection;
import colors.ConsoleOutput;
import server.dragon.Dragon;
import superCommand.AbstractCommand;
import superCommand.TypeOfArguments;
import utils.InputData;

/**
 * Class implements command count_less_than_age age.
 * Command output count of elements, which age lower than input parameter
 */
//add try+catch
public class CountLessThanAge extends AbstractCommand {

    public CountLessThanAge(String commandName, DragonCollection dragonsCollection, ReadManager inputData) {
        super(commandName, dragonsCollection, inputData);
        this.typeOfArg = TypeOfArguments.LONG;
    }

    @Override
    public void execute(Long argAge) {


        int count = 0;

        for (Dragon dragon : dragonsCollection.getDragons()) {
            if (dragon.getAge() <= argAge) count++;
        }

        if (count == 0) System.out.println("All dragons older than this age");
        else if (count == 1) System.out.println("There is 1 server.dragon older than this age");
        else System.out.println("There are " + count + " dragons older than this age");


    }

    @Override
    public String getCommandInfo() {
        return " output count of elements, which age lower than input parameter";
    }

}
