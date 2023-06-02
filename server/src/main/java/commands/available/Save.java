package commands.available;

import managers.CollectionManager;
import managers.CommandCollection;
import network.Request;
import network.Response;

public class Save extends CollectionWorker {

    private CommandCollection commandCollection;
    public Save(CollectionManager collectionManager, CommandCollection commandCollection) {
        super("save", " : сохраняет коллекцию в файл", collectionManager);
        this.commandCollection = commandCollection;
    }

    @Override
    public Response execute(Request request) {
        return null;
    }
}

