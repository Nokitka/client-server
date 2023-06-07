package commands.available;

import commands.abstact.Command;
import managers.CommandCollection;
import network.Request;
import network.Response;
import network.Status;

/**
 * Class implements command help.
 * Command output information about unlocked command
 */
public class Help extends Command {

    private CommandCollection commandCollection;
    public Help(CommandCollection commandCollection) {
        super("help", " : вывести справку по доступным командам");
        this.commandCollection = commandCollection;
    }

    /**
     * Исполнить команду
     * @param request запрос клиента
     * @throws IllegalArgumentException неверные аргументы команды
     */
    @Override
    public Response execute(Request request) throws IllegalArgumentException {
        if (!request.getArg().isBlank()) throw new IllegalArgumentException();
        return new Response(Status.OK,
                String.join("\n",
                        commandCollection.getCommands()
                                .stream().map(Command::toString).toList()));
    }
}
