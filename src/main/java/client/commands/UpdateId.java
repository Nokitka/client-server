package client.commands;

import collections.DragonCollection;
import colors.ConsoleOutput;
import server.dragon.Dragon;
import server.exception.IncorrectInputInScriptException;
import server.exception.NoElementInCollectionException;
import superCommand.AbstractCommand;
import superCommand.TypeOfArguments;
import utils.InputData;

/**
 * Class implements command update id {element}.
 * Command update element in collection with input id
 */
public class UpdateId extends AbstractCommand {

    public UpdateId(String commandName, DragonCollection dragonsCollection, InputData inputData) {
        super(commandName, dragonsCollection, inputData);
        this.typeOfArg = TypeOfArguments.LONG;
    }

    @Override
    public void execute(Long argId) {


        try {

            Long inputId;

            //inputId = consoleInput.inputFromStringId();
            boolean flag = false;

            for (Dragon dragon : dragonsCollection.getDragons()) {
                if (dragon.getId().equals(argId)) {
                    if (inputDragonData.getFileMode()){
                        Dragon setDragon = inputDragonData.inputDragon();
                        if (setDragon != null) {
                            dragon.setId(setDragon.getId());
                            dragon.setName(setDragon.getName());
                            dragon.setCoordinates(setDragon.getCoordinates());
                            dragon.setAge(setDragon.getAge());
                            dragon.setDescription(setDragon.getDescription());
                            dragon.setSpeaking(setDragon.isSpeaking());
                            dragon.setCharacter(setDragon.getCharacter());
                            dragon.setHead(setDragon.getHead());
                            flag = true;
                            break;
                        } else {
                            throw new IncorrectInputInScriptException();
                        }
                    } else {
                        dragon.setName(inputDragonData.inputName());
                        dragon.setCoordinates(inputDragonData.inputCoordinates());
                        dragon.setAge(inputDragonData.inputAge());
                        dragon.setDescription(inputDragonData.inputDescription());
                        dragon.setSpeaking(inputDragonData.canSpeak());
                        dragon.setCharacter(inputDragonData.inputCharacter());
                        dragon.setHead(inputDragonData.inputDragonHead());
                        flag = true;
                        break;
                    }
                }
            }

            if (!flag) throw new NoElementInCollectionException();
            ConsoleOutput.messageOutput("Dragon updated");

        } catch (NoElementInCollectionException exception) {
            ConsoleOutput.errOutput("No server.dragon with that id in collection");
        } catch (IncorrectInputInScriptException e) {
            ConsoleOutput.errOutput("Dragon didnt update");
        }

    }

    @Override
    public String getCommandInfo() {
        return " update element in collection with input id";
    }

}
