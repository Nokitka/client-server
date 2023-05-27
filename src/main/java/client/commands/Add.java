package client.commands;

import client.ReadManager;
import collections.DragonCollection;
import colors.ConsoleOutput;
import server.dragon.Dragon;
import server.exception.IncorrectInputInScriptException;
import superCommand.AbstractCommand;
import superCommand.TypeOfArguments;
import utils.InputData;


//add try+catch

/**
 * Class implements command add.
 * This command add new element to collection
 */
public class Add extends AbstractCommand {

    public Add(String commandName, DragonCollection dragonsCollection, ReadManager inputData) {
        super(commandName, dragonsCollection, inputData);
        this.typeOfArg = TypeOfArguments.NULL;
    }

    @Override
    public void execute() throws IncorrectInputInScriptException {

        Dragon dragon = inputDragonData.inputDragon();

        if (dragon != null) {
            dragonsCollection.getDragons().add(dragon);
            System.out.println("Dragon added to collection");
        } else {
            System.out.println("Dragon didn`t add to collection");
        }

    }

    @Override
    public String getCommandInfo() {
        return " add new element to collection";
    }
}
