package commands.available;

import commands.abstact.Command;
import network.Request;
import network.Response;
import network.Status;

/**
 * Class implements command exit.
 * Command close console application(without save the file)
 */
public class Exit extends Command {

    public Exit(){
        super("exit", " : завершить программу (без сохранения в файл)");
    }

    /**
     * Исполнить команду
     * @param request запрос с клиента
     */
    @Override
    public Response execute(Request request) throws IllegalArgumentException {
        if (!request.getArg().isBlank()) throw new IllegalArgumentException();
        return new Response(Status.EXIT);
    }
}
