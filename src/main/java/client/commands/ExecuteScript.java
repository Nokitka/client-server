package client.commands;

import server.exception.IncorrectInputInScriptException;
import superCommand.AbstractCommand;
import superCommand.TypeOfArguments;
import utils.file.DataRegister;

import java.io.IOException;

//добавить проверку на рекурсию
public class ExecuteScript extends AbstractCommand {

    private DataRegister dataRegister;

    public ExecuteScript(String commandName, DataRegister dataRegister) {
        super(commandName);
        this.typeOfArg = TypeOfArguments.STRING;
        this.dataRegister = dataRegister;
    }

    @Override
    public void execute(String path) throws IOException, IncorrectInputInScriptException {
        dataRegister.runner(path);
    }

    @Override
    public String getCommandInfo() {
        return " read and execute script";
    }
}
