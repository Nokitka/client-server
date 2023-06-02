package managers;

import data.Coordinates;
import data.Dragon;
import data.DragonCharacter;
import data.DragonHead;

import java.util.ArrayList;
import java.util.Scanner;

public class ClientManager {

    public static Dragon getNewDragon(Scanner in) {
        String name = ReadManager.inputName(in);
        Coordinates coordinates = ReadManager.inputCoordinates(in);
        long age = ReadManager.inputAge(in);
        String description = ReadManager.inputDescription(in);
        boolean speak = ReadManager.canSpeak(in);
        DragonCharacter character = ReadManager.inputCharacter(in);
        DragonHead head = ReadManager.inputDragonHead(in);
        return new Dragon(name,
                coordinates,
                age,
                description,
                speak,
                character,
                head);
    }

    public static Dragon createPersonFromScript(ArrayList<String> data) {
        try {
            if ((!data.get(0).equals("")) // name
                    && (Long.parseLong(data.get(3)) > 0) // age
                    && (!data.get(4).equals("")) // description
                    && (ReadManager.doesThisTypeOfCharacterExist(data.get(6))) // character
                    && (Float.parseFloat(data.get(7)) > 0  // eyes
                    && Integer.parseInt(data.get(8)) > 0)) { // tooth

                return new Dragon(data.get(0), // name
                        new Coordinates(Integer.parseInt(data.get(1)), // x
                                Float.parseFloat(data.get(2))), // y
                        Long.parseLong(data.get(3)), // age
                        data.get(4), // description
                        Boolean.parseBoolean(data.get(5)), // speaking
                        Enum.valueOf(DragonCharacter.class, data.get(6)), // character
                        new DragonHead(Float.parseFloat(data.get(7)), // eyes
                                Integer.parseInt(data.get(8))) // tooth
                );

            } else {
                System.out.println("Неправильно введены данные");
                return null;
            }
        } catch (NumberFormatException e) {
            System.out.println("Неправильно введены данные");
            return null;
        }
    }
}
