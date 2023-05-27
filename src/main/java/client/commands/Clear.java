package client.commands;

import client.ReadManager;
import collections.DragonCollection;
import colors.ConsoleOutput;
import server.exception.DragonCollectionIsEmptyException;
import superCommand.AbstractCommand;
import superCommand.TypeOfArguments;
import utils.InputData;

/**
 * Class implements command clear.
 * Command clear the server.dragon collection
 */
public class Clear extends AbstractCommand {

    public Clear(String commandName, DragonCollection dragonsCollection, ReadManager inputData) {
        super(commandName, dragonsCollection, inputData);
        this.typeOfArg = TypeOfArguments.NULL;
    }

    @Override
    public void execute() {

        try {

            if (dragonsCollection.getDragons().size() == 0) throw new DragonCollectionIsEmptyException();

            dragonsCollection.getDragons().clear();
            System.out.println("Dragon collection cleared");

        } catch (DragonCollectionIsEmptyException exception) {
            System.out.println("Dragon collection is already clear");
        }

    }

    @Override
    public String getCommandInfo() {
        return " clear the server.dragon`s collection";
    }
}
