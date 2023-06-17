package commands.available;

import commands.abstact.CollectionWorker;
import data.GenerationId;
import managers.CollectionManager;
import network.Request;
import network.Response;
import network.Status;
import utils.DatabaseHandler;

import java.util.Objects;


/**
 * Command add {element}
 */
public class Add extends CollectionWorker {

    public Add(CollectionManager collectionManager) {
        super("add", " {element} : добавить новый элемент в коллекцию", collectionManager);
    }

    @Override
    public Response execute(Request request) throws IllegalArgumentException {
        if (!request.getArg().isBlank()) throw new IllegalArgumentException();
        if (Objects.isNull(request.getDragon())) {
            return new Response(Status.ASK_OBJECT, "Для команды " + this.getName() + " требуется объект");
        } else {
            int new_id = DatabaseHandler.getDatabaseManager().addObject(request.getDragon(), request.getUser());
            if(new_id == -1) return new Response(Status.ERROR, "Объект добавить не удалось");
            request.getDragon().setId(new_id);
            request.getDragon().setUserLogin(request.getUser().name());
            collectionManager.add(request);
            return new Response(Status.OK, "Дракон успешно добавлен");
        }
    }
}