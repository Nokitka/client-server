package utils;

import exceptions.CommandRuntimeException;
import exceptions.NoSuchCommandException;
import managers.CommandCollection;
import network.Request;
import network.Response;
import network.Status;

import java.io.IOException;

/**
 * Распределитель ответов от сервера по ошибкам
 */
public class RequestHandler {
    private CommandCollection commandCollection;

    public RequestHandler(CommandCollection commandCollection) {
        this.commandCollection = commandCollection;
    }

    public Response handle(Request request) throws IOException {
        try {
            return commandCollection.executeCommand(request);
        } catch (IllegalArgumentException e) {
            return new Response(Status.WRONG_ARGUMENTS,
                    "Неверное использование аргументов команды");
        } catch (CommandRuntimeException e) {
            return new Response(Status.ERROR,
                    "Ошибка при исполнении программы");
        } catch (NoSuchCommandException e) {
            return new Response(Status.ERROR, "Такой команды нет в списке");
        }
    }
}