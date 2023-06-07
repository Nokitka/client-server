package managers;

import data.Coordinates;
import data.Dragon;
import data.DragonCharacter;
import data.DragonHead;
import utils.BlankConsole;
import utils.Console;
import utils.Printable;
import utils.UserInput;

import java.util.NoSuchElementException;

/**
 * Class includes methods, which return input yield to class Dragon element
 */
public class ReadManager {

    private static Printable console = new Console();

//----------------------Auxiliary methods----------------------

    /**
     * Method use input string from method canSpeak to convert this string in boolean format
     *
     * @param yesNo input parameter
     * @return {1, 0, -1}
     */
    private static int yesOrNot(String yesNo) {
        if (yesNo.equals("y")
                || yesNo.equals("yes")
                || yesNo.equals("да")
                || yesNo.equals("true")) return 1;
        else if (yesNo.equals("n")
                || yesNo.equals("no")
                || yesNo.equals("нет")
                || yesNo.equals("")
                || yesNo.equals("false")) return 0;
        return -1;
    }

    public static boolean doesThisTypeOfCharacterExist(String character) {
        for (DragonCharacter values : DragonCharacter.values()) {
            if (values.name().equals(character)) {
                return true;
            }
        }
        return false;
    }

//----------------------Methods for input----------------------

    /**
     * Method set input yield to Dragon element from this class`s methods
     *
     * @return Dragon
     */
    public static Dragon inputDragon(UserInput in) {
        console = (ScannerManager.fileMode())
                ? new BlankConsole()
                : new Console();
        return new Dragon(
                inputName(in),
                inputCoordinates(in),
                inputAge(in),
                inputDescription(in),
                canSpeak(in),
                inputCharacter(in),
                inputDragonHead(in)
        );
    }

    public static String inputEnvVar(UserInput in) {

        String env = "";

        try {

            console.print("Введите переменную окружения : ");
            env = in.nextLine().trim();

        } catch (NoSuchElementException exception) {
            console.println("Переменная окружения не может быть типа null");
        } catch (IllegalArgumentException exception) {
            console.println("Ответ не распознан");
        }

        return env;
    }

    public static String inputName(UserInput in) {

        String name;

        while (true) {
            try {

                console.print("Имя : ");
                name = in.nextLine().trim();

                if (name.equals("")) throw new IllegalArgumentException();
                break;

            } catch (NoSuchElementException exception) {
                console.printError("Имя дракона не может быть типа null");
            } catch (IllegalArgumentException exception) {
                console.printError("Имя дракона должно содержать хотя бы один символ");
            }
        }

        return name;
    }

    private static int inputX(UserInput in) {

        String strX;
        int x;

        while (true) {
            try {

                console.print("X = ");
                strX = in.nextLine().trim();

                x = Integer.parseInt(strX);

                break;

            } catch (NumberFormatException exception) {
                console.printError("Введенная координата должна быть типа int");
            } catch (IllegalArgumentException exception) {
                console.printError("Введенное значение передано с ошибкой");
            } catch (NoSuchElementException exception) {
                console.printError("Введенное значение не может быть типа null");
            }
        }

        return x;
    }

    private static float inputY(UserInput in) {

        String strY;
        float y;

        while (true) {
            try {

                console.print("Y = ");
                strY = in.nextLine().trim();

                y = Float.parseFloat(strY);

                break;

            } catch (NumberFormatException exception) {
                console.printError("Введенная координата должна с плавающей точкой");
            } catch (IllegalArgumentException exception) {
                console.printError("Введенное значение передано с ошибкой");
            } catch (NoSuchElementException exception) {
                console.printError("Введенное значение не может быть типа null");
            }
        }

        return y;
    }

    public static Coordinates inputCoordinates(UserInput in) {
        return new Coordinates(inputX(in), inputY(in));
    }

    public static long inputAge(UserInput in) {

        String strAge;
        long age;

        while (true) {
            try {

                console.print("Age = ");
                strAge = in.nextLine().trim();

                age = Long.parseLong(strAge);

                if (age <= 0) throw new IllegalArgumentException();
                break;

            } catch (NoSuchElementException exception) {
                console.printError("Возраст не может быть типа null");
            } catch (NumberFormatException exception) {
                console.printError("Введенное число должны быть типа long");
            } catch (IllegalArgumentException exception) {
                console.printError("Введенное значение передано с ошибкой");
            }
        }

        return age;
    }

    public static String inputDescription(UserInput in) {

        String description;

        while (true) {
            try {

                console.print("Описание : ");
                description = in.nextLine().trim();

                if (description.equals("")) throw new IllegalArgumentException();
                break;

            } catch (NoSuchElementException exception) {
                console.printError("Описание не может быть типа null");
            } catch (IllegalArgumentException exception) {
                console.printError("Описание должно содержать хотя бы один символ");
            }
        }

        return description;
    }

    public static boolean canSpeak(UserInput in) {

        String canSpeak;

        while (true) {
            try {

                console.print("Может ли дракон говорить(по умолчанию нет)? ");
                canSpeak = in.nextLine().trim();

                if (yesOrNot(canSpeak) == -1) throw new IllegalArgumentException();
                break;

            } catch (IllegalArgumentException exception) {
                console.printError("Ответ не распознан");
            } catch (NoSuchElementException exception) {
                console.printError("Данное поле не может быть типа null");
            }
        }

        return yesOrNot(canSpeak) == 1;
    }

    public static DragonCharacter inputCharacter(UserInput in) {

        String strCode;
        int code;

        while (true) {
            try {

                console.println("Выберите характер дракона из предложенных : " + DragonCharacter.getValues());
                console.print("Характер : ");

                strCode = in.nextLine();
                if (strCode.equals("")) throw new IllegalArgumentException();
                code = Integer.parseInt(strCode);

                for (DragonCharacter character : DragonCharacter.values()) {
                    if (character.getCode() == code)
                        return character;
                }

            } catch (NoSuchElementException exception) {
                console.printError("Характер не может быть типа null");
            } catch (NumberFormatException exception) {
                console.printError("Введите цифру соответствующую характеру дракона");
            } catch (IllegalArgumentException exception) {
                console.printError("Такой характер не определен");
            }
        }
    }

    private static float inputEyesCount(UserInput in) {

        String strEyesCount;
        float eyesCount;

        while (true) {
            try {

                console.print("Количество глаз = ");
                strEyesCount = in.nextLine().trim();
                eyesCount = Float.parseFloat(strEyesCount);

                if (eyesCount <= 0) throw new IllegalArgumentException();
                break;

            } catch (NumberFormatException exception) {
                console.printError("Введенное число должны быть с плавающей точкой");
            } catch (NoSuchElementException exception) {
                console.printError("Количество глаз не должно быть типа ");
            } catch (IllegalArgumentException exception) {
                console.printError("Введенное значение передано с ошибкой");
            }
        }

        return eyesCount;
    }

    private static int inputToothCount(UserInput in) {

        String strToothCount;
        int toothCount;

        while (true) {
            try {

                console.print("Количество зубов = ");
                strToothCount = in.nextLine().trim();

                toothCount = Integer.parseInt(strToothCount);
                if (toothCount <= 0) throw new IllegalArgumentException();
                break;

            } catch (NumberFormatException exception) {
                console.printError("Введенное число должны быть с плавающей точкой");
            } catch (NoSuchElementException exception) {
                console.printError("Количество зубов не должно быть типа ");
            } catch (IllegalArgumentException exception) {
                console.printError("Введенное значение передано с ошибкой");
            }
        }

        return toothCount;
    }

    public static DragonHead inputDragonHead(UserInput in) {
        return new DragonHead(inputEyesCount(in), inputToothCount(in));
    }
}
