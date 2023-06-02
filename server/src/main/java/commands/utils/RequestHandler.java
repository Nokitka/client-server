package commands.utils;

import exceptions.CommandRuntimeException;
import exceptions.NoSuchCommandException;
import managers.CommandCollection;
import network.Request;
import network.Response;
import network.Status;

public class RequestHandler {
    private CommandCollection commands;

    public RequestHandler(CommandCollection commands) {
        this.commands = commands;
    }

    public Response handle(Request request) {
        try {
            return commands.executeCommand(request);
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
