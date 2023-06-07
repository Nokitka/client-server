package commands.available;

import commands.abstact.CollectionWorker;
import managers.CollectionManager;
import network.Request;
import network.Response;
import network.Status;

/**
 * Class implements command show.
 * Command output in standard output all elements of collection in string representation
 */
public class Show extends CollectionWorker {

    public Show(CollectionManager collectionManager) {
        super("show", " : вывести всех драконов из коллекции", collectionManager);
    }

    @Override
    public Response execute(Request request) throws IllegalArgumentException {
        if (!request.getArg().isBlank()) throw new IllegalArgumentException();
        return new Response(Status.OK, collectionManager.show());
    }
}
