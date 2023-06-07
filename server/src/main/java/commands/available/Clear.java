package commands.available;

import commands.abstact.CollectionWorker;
import managers.CollectionManager;
import network.Request;
import network.Response;
import network.Status;

/**
 * Class implements command clear.
 * Command clear the dragon collection
 */
public class Clear extends CollectionWorker {

    public Clear(CollectionManager collectionManager) {
        super("clear", " : очистить коллекцию", collectionManager);
    }

    @Override
    public Response execute(Request request) throws IllegalArgumentException {
        if (!request.getArg().isBlank()) throw new IllegalArgumentException();
        collectionManager.getDragons().clear();
        return new Response(Status.OK, "Элементы удалены");
    }
}
