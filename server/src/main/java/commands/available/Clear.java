package commands.available;

import commands.abstact.CollectionWorker;
import data.Dragon;
import managers.CollectionManager;
import network.Request;
import network.Response;
import network.Status;
import utils.DatabaseHandler;

import java.util.List;

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
        List<Long> deletedIds = collectionManager.getDragons().stream()
                .filter(dragon -> dragon.getUserLogin().equals(request.getUser().name()))
                .map(Dragon::getId)
                .toList();
        if(DatabaseHandler.getDatabaseManager().deleteAllObjects(request.getUser(), deletedIds)) {
            collectionManager.removeDragonsByListOfIds(deletedIds);
            return new Response(Status.OK, "Ваши элементы удалены");
        }
        return new Response(Status.ERROR, "Элементы коллекции удалить не удалось");
    }
}
