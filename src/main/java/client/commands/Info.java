package client.commands;

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

    public Info(String commandName, DragonCollection dragonsCollection, InputData inputData) {
        super(commandName, dragonsCollection, inputData);
        this.typeOfArg = TypeOfArguments.NULL;
    }

    @Override
    public void execute() {

        ConsoleOutput.messageOutput("Info about collection{");
        ConsoleOutput.messageOutput("    Type of collection : " + dragonsCollection.getDragons().getClass().getName());
        ConsoleOutput.messageOutput("    Size of collection : " + dragonsCollection.getDragons().size());
        ConsoleOutput.messageOutput("    Date of creation collection : " + dragonsCollection.getCreationDate().format(DateTimeFormatter.ofPattern("yy/MM/dd hh:mm:ss")));
        ConsoleOutput.messageOutput("}");

    }

    @Override
    public String getCommandInfo() {
        return " output in standard output information about collection(type, date of initialization, size of collection etc)";
    }
}
