package client.commands;

import colors.ConsoleOutput;
import superCommand.AbstractCommand;
import superCommand.TypeOfArguments;
import utils.file.ToCSV;

import java.io.IOException;

public class Save extends AbstractCommand {

    private ToCSV parseToCSV;

    public Save(String commandName, ToCSV parseToCSV) {
        super(commandName);
        this.typeOfArg = TypeOfArguments.NULL;
        this.parseToCSV = parseToCSV;
    }

    @Override
    public void execute() throws IOException {
        parseToCSV.runner();
        System.out.println("Collection saved");
    }

    @Override
    public String getCommandInfo() {
        return " save collection in the file";
    }
}

