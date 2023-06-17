package managers;

import data.Dragon;
import exceptions.InFileModeException;
import exceptions.InvalidFormException;
import exceptions.NoSuchCommandException;
import network.Request;
import network.Response;
import network.Status;
import network.User;
import utils.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.PortUnreachableException;
import java.util.*;

/**
 * Класс для работы с пользовательским вводом
 */
public class RuntimeManager {
    private final UserInput userScanner;
    private final Printable console;
    private final Client client;
    private User user = null;
    private boolean running = true;

    private static List<File> scriptStack = new LinkedList<>();

    public RuntimeManager(Console console, Client client, UserInput scanner) {
        this.console = console;
        this.client = client;
        this.userScanner = scanner;
    }

    /**
     * Работа с пользователем и выполнение команд
     */
    public void interactiveMode() {
        Scanner userScanner = ScannerManager.getScanner();

        while (running) {
            try {
                if (Objects.isNull(user)) {
                    Response response = null;
                    boolean isLogin = true;
                    do {
                        if(!Objects.isNull(response)) {
                            console.println( (isLogin)
                                    ? "Такой связки логин-пароль нет, попробуйте снова"
                                    : "Этот логин уже занят, попробуйте снова");
                        }
                        UserLogin userForm = new UserLogin(console);
                        isLogin = userForm.askIfLogin();
                        user = new UserLogin(console).build();
                        if (isLogin) {
                            response = client.sendAndAskResponse(new Request("ping", "", user));
                        } else {
                            response = client.sendAndAskResponse(new Request("register", "", user));
                        }
                    } while (response.getStatus() != Status.OK);
                    console.println("Вы успешно зашли в аккаунт");
                }
                if (!userScanner.hasNext()) {
                    console.println("До свидания");
                    break;
                }
                String[] userCommand = (userScanner.nextLine().trim() + " ").split(" ", 2);
                if (userCommand[0].isBlank()) continue;

                Response response = client.sendAndAskResponse(new Request(userCommand[0].trim(), userCommand[1].trim(), user));
                this.printResponse(response);
                switch (response.getStatus()) {
                    case ASK_OBJECT -> {
                        Dragon dragon = ReadManager.inputDragon(new ConsoleInput());
                        if (!dragon.validData()) throw new InvalidFormException();
                        Response newResponse = client.sendAndAskResponse(
                                new Request(
                                        userCommand[0].trim(),
                                        userCommand[1].trim(),
                                        dragon,
                                        user));
                        if (newResponse.getStatus() != Status.OK) {
                            console.printError(newResponse.getMessage());
                        } else {
                            this.printResponse(newResponse);
                        }
                    }
                    case EXIT -> {
                        running = false;
                    }
                    case EXECUTE_SCRIPT -> {
                        ScannerManager.setFileMode();
                        this.fileExecution(response.getMessage());
                        ScannerManager.setUserMode();
                    }
                    default -> {
                    }
                }
            } catch (NoSuchElementException exception) {
                console.printError("Пользовательский ввод не обнаружен");
            } catch (NoSuchCommandException e) {
                console.printError("Такой команды нет в списке");
            } catch (IllegalArgumentException e) {
                console.printError("Введены неправильные аргументы команды");
            } catch (InvalidFormException e) {
                console.printError("Поля не валидны! Объект не создан");
            } catch (PortUnreachableException e) {
                e.printStackTrace();
                console.printError("Ошибка подключения, сервер недоступен");
            } catch (IOException e) {
                console.printError("Неизвестная ошибка " + e);
            }
        }
    }

    /**
     * Выполнение команды execute_script
     * @param args
     */
    private void fileExecution(String args) {
        if (args == null || args.isEmpty()) {
            console.printError("Путь не распознан");
            return;
        } else console.println(ConsoleColors.toColor("Путь получен успешно", ConsoleColors.PURPLE));
        args = args.trim();
        try {
            ExecuteFileManager.pushFile(args);
            for (String line = ExecuteFileManager.readLine(); line != null; line = ExecuteFileManager.readLine()) {
                String[] userCommand = (line + " ").split(" ", 2);
                userCommand[1] = userCommand[1].trim();
                if (userCommand[0].isBlank()) return;
                if (userCommand[0].equals("execute_script")) {
                    if (ExecuteFileManager.fileRepeat(userCommand[1])) {
                        console.printError("Найдена рекурсия по пути " + new File(userCommand[1]).getAbsolutePath());
                        continue;
                    }
                }
                console.println(ConsoleColors.toColor("Выполнение команды " + userCommand[0], ConsoleColors.YELLOW));
                Response response = client.sendAndAskResponse(new Request(userCommand[0].trim(), userCommand[1].trim(), user));
                this.printResponse(response);
                switch (response.getStatus()) {
                    case ASK_OBJECT -> {
                        Dragon dragon;
                        try {
                            dragon = ReadManager.inputDragon(new ExecuteFileManager());
                            if (!dragon.validData()) throw new InFileModeException();
                        } catch (InFileModeException err) {
                            console.printError("Поля в файле не валидны! Объект не создан");
                            continue;
                        }
                        Response newResponse = client.sendAndAskResponse(
                                new Request(
                                        userCommand[0].trim(),
                                        userCommand[1].trim(),
                                        dragon,
                                        user));
                        if (newResponse.getStatus() != Status.OK) {
                            console.printError(newResponse.getMessage());
                        } else {
                            this.printResponse(newResponse);
                        }
                    }
                    case EXIT -> running = false;
                    case EXECUTE_SCRIPT -> {
                        this.fileExecution(response.getMessage());
                        ExecuteFileManager.popRecursion();
                    }
                    default -> {
                    }
                }
            }
            ExecuteFileManager.popFile();
        } catch (FileNotFoundException fileNotFoundException) {
            console.printError("Такого файла не существует");
        } catch (IOException e) {
            console.printError("Ошибка ввода вывода");
        }
    }

    public void printResponse(Response response) {
        switch (response.getStatus()) {
            case OK -> console.println(response.getMessage());
            case ERROR -> console.printError(response.getMessage());
            case WRONG_ARGUMENTS -> console.printError("Неверное использование команды!");
            default -> {
            }
        }
    }
}