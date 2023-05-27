package client.commands;

import colors.ConsoleOutput;
import superCommand.AbstractCommand;
import superCommand.TypeOfArguments;

public class ClearConsole extends AbstractCommand {

    public ClearConsole(String commandName) {
        super(commandName);
        this.typeOfArg = TypeOfArguments.NULL;
    }

    @Override
    public void execute() {

        try {

            final String os = System.getProperty("os.name");

            if (os.contains("Windows"))
                Runtime.getRuntime().exec("cls");
            else
                Runtime.getRuntime().exec("clear");

        } catch (final Exception exception) {
            ConsoleOutput.errOutput("I can`t clear the console");
        }
    }

    @Override
    public String getCommandInfo() {
        return " clear console";
    }

}
