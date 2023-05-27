package client.commands;

import client.ReadManager;
import collections.DragonCollection;
import colors.ConsoleOutput;
import server.dragon.Dragon;
import server.exception.DragonCollectionIsEmptyException;
import server.exception.IncorrectInputInScriptException;
import server.exception.NoElementInCollectionException;
import superCommand.AbstractCommand;
import superCommand.TypeOfArguments;
import utils.InputData;

/**
 * Class implements command remove_greater {element}.
 * Command remove elements that don`t exceed input element
 */
public class RemoveGreater extends AbstractCommand {

    public RemoveGreater(String commandName, DragonCollection dragonsCollection, ReadManager inputData) {
        super(commandName, dragonsCollection, inputData);
        this.typeOfArg = TypeOfArguments.NULL;
    }

    @Override
    public void execute() throws IncorrectInputInScriptException {

        try {

            if (dragonsCollection.getDragons().size() == 0)
                throw new DragonCollectionIsEmptyException();
            
            else {

                try {

                    Dragon inputDragon;

                    inputDragon = inputDragonData.inputDragon();
                    boolean flag = false;
                    int count = 0;

                    for (Dragon dragon : dragonsCollection.getDragons()) {
                        if (inputDragon.compareTo(dragon) < 0) {
                            dragonsCollection.getDragons().remove(dragon);
                                        count ++;
                        }
                    }

                    if (!flag) throw new NoElementInCollectionException();

                    System.out.println("Delete count of dragons : " + count);

                } catch (NoElementInCollectionException exception) {
                    System.out.println("No dragons which exceed this server.dragon");
                }

            }

        } catch (
                DragonCollectionIsEmptyException exception) {
            System.out.println("Dragon collection is empty");
        }

    }

    @Override
    public String getCommandInfo() {
        return " remove elements that exceed input element";
    }
}
