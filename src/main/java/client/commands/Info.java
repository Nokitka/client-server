package client.commands;

import client.ReadManager;
import collections.DragonCollection;
import colors.ConsoleOutput;
import superCommand.AbstractCommand;
import superCommand.TypeOfArguments;
import utils.InputData;

import java.time.format.DateTimeFormatter;

/**
 * Class implements command info.
 * Command output in standard output information about collection(type, date of initialization, size of collection etc)
 */
//add date of init and more info about collection DONE
//some else?
public class Info extends AbstractCommand {

    public Info(String commandName, DragonCollection dragonsCollection, ReadManager inputData) {
        super(commandName, dragonsCollection, inputData);
        this.typeOfArg = TypeOfArguments.NULL;
    }

    @Override
    public void execute() {

        System.out.println("Info about collection{");
        System.out.println("    Type of collection : " + dragonsCollection.getDragons().getClass().getName());
        System.out.println("    Size of collection : " + dragonsCollection.getDragons().size());
        System.out.println("    Date of creation collection : " + dragonsCollection.getCreationDate().format(DateTimeFormatter.ofPattern("yy/MM/dd hh:mm:ss")));
        System.out.println("}");

    }

    @Override
    public String getCommandInfo() {
        return " output in standard output information about collection(type, date of initialization, size of collection etc)";
    }
}
