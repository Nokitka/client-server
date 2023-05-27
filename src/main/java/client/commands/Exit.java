package client.commands;

import colors.ConsoleOutput;
import superCommand.AbstractCommand;
import superCommand.TypeOfArguments;

/**
 * Class implements command exit.
 * Command close console application(without save the file)
 */
public class Exit extends AbstractCommand {

    public Exit(String commandName) {
        super(commandName);
        closed = false;
        this.typeOfArg = TypeOfArguments.NULL;
    }

    @Override
    public void execute() {
        System.out.println("Console application closed");
        closed = true;
    }

    @Override
    public String getCommandInfo() {
        return " close console application(without save the file)";
    }

}
