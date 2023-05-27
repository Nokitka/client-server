package client.commands;

import client.ReadManager;
import collections.DragonCollection;
import colors.ConsoleOutput;
import server.dragon.Dragon;
import server.exception.NoElementInCollectionException;
import superCommand.AbstractCommand;
import superCommand.TypeOfArguments;
import utils.InputData;

/**
 * Class implements command remove_by_id.
 * Command remove element by input id
 */
//add try+catch
public class RemoveById extends AbstractCommand {

    public RemoveById(String commandName, DragonCollection dragonsCollection, ReadManager inputData) {
        super(commandName, dragonsCollection, inputData);
        this.typeOfArg = TypeOfArguments.LONG;
    }

    @Override
    public void execute(Long argId) {


        try {


            boolean flag = false;
            Dragon rmvDragon = null;

            for (Dragon dragon : dragonsCollection.getDragons()) {
                if (dragon.getId().equals(argId)) {
                    rmvDragon= dragon;
                    }
            }
            if (!flag) throw new NoElementInCollectionException();
            dragonsCollection.getDragons().remove(rmvDragon);

            System.out.println("Dragon with id = " + argId + " deleted");

        } catch (
                NoElementInCollectionException exception) {
            System.out.println("No server.dragon with that id in collection");
        }

    }

    @Override
    public String getCommandInfo() {
        return " remove element by input id";
    }
}
