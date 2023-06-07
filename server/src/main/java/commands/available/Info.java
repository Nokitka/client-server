package commands.available;

import commands.abstact.CollectionWorker;
import managers.CollectionManager;
import network.Request;
import network.Response;
import network.Status;

/**
 * Class implements command info.
 * Command output in standard output information about collection(type, date of initialization, size of collection etc)
 */
//add date of init and more info about collection DONE
//some else?
public class Info extends CollectionWorker {

    public Info(CollectionManager collectionManager) {
        super("info", " : вывести информацию о коллекции", collectionManager);
    }

    @Override
    public Response execute(Request request) throws IllegalArgumentException {
        if (!request.getArg().isBlank()) throw new IllegalArgumentException();
        return new Response(Status.OK, collectionManager.info());
    }
}
