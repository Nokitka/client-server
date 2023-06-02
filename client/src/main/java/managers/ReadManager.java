package managers;

import data.Coordinates;
import data.Dragon;
import data.DragonCharacter;
import data.DragonHead;

import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Class includes methods, which return input yield to class Dragon element
 */
public class ReadManager {

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
    public static Dragon inputDragon(Scanner in) {

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

    public static String inputEnvVar(Scanner in) {

        String env = "";

        try {

            System.out.println("Введите переменную окружения : ");
            env = in.nextLine().trim();

        } catch (NoSuchElementException exception) {
            System.out.println("Переменная окружения не может быть типа null");
        } catch (IllegalArgumentException exception) {
            System.out.println("Ответ не распознан");
        }

        return env;
    }

    public static String inputName(Scanner in) {

        String name;

        while (true) {
            try {

                System.out.println("Имя : ");
                name = in.nextLine().trim();

                if (name.equals("")) throw new IllegalArgumentException();
                break;

            } catch (NoSuchElementException exception) {
                System.out.println("Имя дракона не может быть типа null");
            } catch (IllegalArgumentException exception) {
                System.out.println("Имя дракона должно содержать хотя бы один символ");
            }
        }

        return name;
    }

    private static int inputX(Scanner in) {

        String strX;
        int x;

        while (true) {
            try {

                System.out.println("X = ");
                strX = in.nextLine().trim();

                x = Integer.parseInt(strX);

                break;

            } catch (NumberFormatException exception) {
                System.out.println("Введенная координата должна быть типа int");
            } catch (IllegalArgumentException exception) {
                System.out.println("Введенное значение передано с ошибкой");
            } catch (NoSuchElementException exception) {
                System.out.println("Введенное значение не может быть типа null");
            }
        }

        return x;
    }

    private static float inputY(Scanner in) {

        String strY;
        float y;

        while (true) {
            try {

                System.out.println("Y = ");
                strY = in.nextLine().trim();

                y = Float.parseFloat(strY);

                break;

            } catch (NumberFormatException exception) {
                System.out.println("Введенная координата должна с плавающей точкой");
            } catch (IllegalArgumentException exception) {
                System.out.println("Введенное значение передано с ошибкой");
            } catch (NoSuchElementException exception) {
                System.out.println("Введенное значение не может быть типа null");
            }
        }

        return y;
    }

    public static Coordinates inputCoordinates(Scanner in) {
        return new Coordinates(inputX(in), inputY(in));
    }

    public static long inputAge(Scanner in) {

        String strAge;
        long age;

        while (true) {
            try {

                System.out.println("Age = ");
                strAge = in.nextLine().trim();

                age = Long.parseLong(strAge);

                if (age <= 0) throw new IllegalArgumentException();
                break;

            } catch (NoSuchElementException exception) {
                System.out.println("Возраст не может быть типа null");
            } catch (NumberFormatException exception) {
                System.out.println("Введенное число должны быть с плавающей точкой");
            } catch (IllegalArgumentException exception) {
                System.out.println("Введенное значение передано с ошибкой");
            }
        }

        return age;
    }

    public static String inputDescription(Scanner in) {

        String description;

        while (true) {
            try {

                System.out.println("Описание : ");
                description = in.nextLine().trim();

                if (description.equals("")) throw new IllegalArgumentException();
                break;

            } catch (NoSuchElementException exception) {
                System.out.println("Описание не может быть типа null");
            } catch (IllegalArgumentException exception) {
                System.out.println("Описание должно содержать хотя бы один символ");
            }
        }

        return description;
    }

    public static boolean canSpeak(Scanner in) {

        String canSpeak;

        while (true) {
            try {

                System.out.println("Может ли дракон говорить(по умолчанию нет)?");
                canSpeak = in.nextLine().trim();

                if (yesOrNot(canSpeak) == -1) throw new IllegalArgumentException();
                break;

            } catch (IllegalArgumentException exception) {
                System.out.println("Ответ не распознан");
            } catch (NoSuchElementException exception) {
                System.out.println("Данное поле не может быть типа null");
            }
        }

        return yesOrNot(canSpeak) == 1;
    }

    public static DragonCharacter inputCharacter(Scanner in) {

        String strCode;
        int code;

        while (true) {
            try {

                System.out.println("Выберите характер дракона из предложенных : " + DragonCharacter.getValues());
                System.out.println("Характер : ");

                strCode = in.nextLine();
                code = Integer.parseInt(strCode);

                if (strCode.equals("")) throw new IllegalArgumentException();
                for (DragonCharacter character : DragonCharacter.values()) {
                    if (character.getCode() == code)
                        return Enum.valueOf(DragonCharacter.class, String.valueOf(code));
                }

            } catch (NoSuchElementException exception) {
                System.out.println("Характер не может быть типа null");
            } catch (NumberFormatException exception) {
                System.out.println("Введите цифру соответствующую характеру дракона");
            } catch (IllegalArgumentException exception) {
                System.out.println("Такой характер не определен");
            }
        }
    }

    private static float inputEyesCount(Scanner in) {

        String strEyesCount;
        float eyesCount;

        while (true) {
            try {

                System.out.println("Количество глаз = ");
                strEyesCount = in.nextLine().trim();
                eyesCount = Float.parseFloat(strEyesCount);

                if (eyesCount <= 0) throw new IllegalArgumentException();
                break;

            } catch (NumberFormatException exception) {
                System.out.println("Введенное число должны быть с плавающей точкой");
            } catch (NoSuchElementException exception) {
                System.out.println("Количество глаз не должно быть типа ");
            } catch (IllegalArgumentException exception) {
                System.out.println("Введенное значение передано с ошибкой");
            }
        }

        return eyesCount;
    }

    private static int inputToothCount(Scanner in) {

        String strToothCount;
        int toothCount;

        while (true) {
            try {

                System.out.println("Количество зубов = ");
                strToothCount = in.nextLine().trim();

                toothCount = Integer.parseInt(strToothCount);
                if (toothCount <= 0) throw new IllegalArgumentException();
                break;

            } catch (NumberFormatException exception) {
                System.out.println("Введенное число должны быть с плавающей точкой");
            } catch (NoSuchElementException exception) {
                System.out.println("Количество зубов не должно быть типа ");
            } catch (IllegalArgumentException exception) {
                System.out.println("Введенное значение передано с ошибкой");
            }
        }

        return toothCount;
    }

    public static DragonHead inputDragonHead(Scanner in) {
        return new DragonHead(inputEyesCount(in), inputToothCount(in));
    }
}
