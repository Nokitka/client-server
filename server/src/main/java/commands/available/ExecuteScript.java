package commands.available;

import commands.abstact.Command;
import network.Request;
import network.Response;
import network.Status;

//добавить проверку на рекурсию
public class ExecuteScript extends Command {

    public ExecuteScript() {
        super("execute_script", " : выполнить скрипт");
    }

    /**
     * Исполнить команду
     * @param request запрос клиента
     * @throws IllegalArgumentException неверные аргументы команды
     */
    @Override
    public Response execute(Request request) throws IllegalArgumentException {
        if (request.getArg().isBlank()) throw new IllegalArgumentException();
        return new Response(Status.EXECUTE_SCRIPT, request.getArg());
    }
}
