package managers;

import commands.abstact.Command;
import exceptions.CommandRuntimeException;
import exceptions.InFileModeException;
import exceptions.NoSuchCommandException;
import network.Request;
import network.Response;
import network.Status;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.stream.Collectors;

/**
 * Класс для работы с командами
 */
public class CommandCollection {

    private HashMap<String, Command> commands = new HashMap<>();
    private Parser parser;

    public void addCommand(Command command) {
        this.commands.put(command.getName(), command);
    }

    public CommandCollection(Parser parser) {
        this.parser = parser;
    }

    public void addCommand(Collection<Command> commands) {
        this.commands.putAll(commands.stream()
                .collect(Collectors.toMap(Command::getName, s -> s)));
    }

    public Response executeCommand(Request request) throws InFileModeException, IllegalArgumentException, CommandRuntimeException, NoSuchCommandException {
        if (commands.containsKey(request.getCommand()))
            return commands.get(request.getCommand()).execute(request);
        return new Response(Status.ERROR, "Данной команды не существует");
    }

    public Collection<Command> getCommands() {
        return commands.values();
    }
}
