package client.commands;

import client.ReadManager;
import collections.DragonCollection;
import colors.ConsoleOutput;
import server.dragon.Dragon;
import server.exception.DragonCollectionIsEmptyException;
import server.exception.IncorrectInputInScriptException;
import superCommand.AbstractCommand;
import superCommand.TypeOfArguments;
import utils.InputData;

/**
 * Class implements command add_if_max.
 * Command add element in collection, if input object higher than the max element
 */
public class AddIfMax extends AbstractCommand {

    public AddIfMax(String commandName, DragonCollection dragonsCollection, ReadManager inputData) {
        super(commandName, dragonsCollection, inputData);
        this.typeOfArg = TypeOfArguments.NULL;
    }

    @Override
    public void execute() throws IncorrectInputInScriptException {

        try {

            if (dragonsCollection.getDragons().size() == 0) throw new DragonCollectionIsEmptyException();

            Dragon compareDragon = inputDragonData.inputDragon();


            if (dragonsCollection.getDragons().last().compareTo(compareDragon) < 0) {
                dragonsCollection.getDragons().add(compareDragon);
                System.out.println("Dragon added to collection");
            } else {
                System.out.println("Dragon lower than the greatest server.dragon in collection ");
            }

        } catch (DragonCollectionIsEmptyException exception) {
            System.out.println("Dragon collection is empty");
        }

    }

    @Override
    public String getCommandInfo() {
        return " add element in collection, if input object higher than the max element";
    }
}
