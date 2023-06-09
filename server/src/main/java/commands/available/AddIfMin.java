package commands.available;

import commands.abstact.CollectionWorker;
import data.Dragon;
import managers.CollectionManager;
import network.Request;
import network.Response;
import network.Status;
import utils.DatabaseHandler;

import java.util.Objects;

/**
 * Class implements command add_if_min.
 * Command add element in collection, if input object lower than the min element
 */
public class AddIfMin extends CollectionWorker {

    public AddIfMin(CollectionManager collectionManager) {
        super("add_if_min", " {element} : добавить элемент в коллекцию если он больше минимального", collectionManager);
    }

    @Override
    public Response execute(Request request) throws IllegalArgumentException {
        if (!request.getArg().isBlank()) throw new IllegalArgumentException();
        if (Objects.isNull(request.getDragon())) {
            return new Response(Status.ASK_OBJECT, "Для команды " + this.getName() + " требуется объект");
        }
        if (request.getDragon().compareTo(Objects.requireNonNull(collectionManager.getDragons().stream()
                .filter(Objects::nonNull)
                .min(Dragon::compareTo)
                .orElse(null))) >= 1) {
            int new_id = DatabaseHandler.getDatabaseManager().addObject(request.getDragon(), request.getUser());
            if(new_id == -1) return new Response(Status.ERROR, "Объект добавить не удалось");
            request.getDragon().setId(new_id);
            request.getDragon().setUserLogin(request.getUser().name());
            collectionManager.add(request);
            return new Response(Status.OK, "Объект успешно добавлен");
        }
        return new Response(Status.ERROR, "Элемент больше минимального");
    }
}
