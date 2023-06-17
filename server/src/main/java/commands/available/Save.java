package commands.available;

import commands.abstact.CollectionWorker;
import managers.CollectionManager;
import managers.CommandCollection;
import network.Request;
import network.Response;
import network.Status;

import java.io.IOException;

public class Save extends CollectionWorker {

    public Save(CollectionManager collectionManager, CommandCollection commandCollection) {
        super("save", " : сохраняет коллекцию в файл", collectionManager);
    }

    @Override
    public Response execute(Request request) throws IllegalArgumentException {
        if (!request.getArg().isBlank()) throw new IllegalArgumentException();

        String path = collectionManager.saveCollection();

        return new Response(Status.OK, "Коллекция была успешно сохранена по адресу : " + path);
    }
}

