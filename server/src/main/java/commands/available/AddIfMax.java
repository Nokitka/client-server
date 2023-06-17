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
 * Class implements command add_if_max.
 * Command add element in collection, if input object higher than the max element
 */
public class AddIfMax extends CollectionWorker {
    public AddIfMax(CollectionManager collectionManager) {
        super("add_if_max", " {element} : добавить элемент в коллекцию если он больше максимального", collectionManager);
    }

    @Override
    public Response execute(Request request) throws IllegalArgumentException {
        if (!request.getArg().isBlank()) throw new IllegalArgumentException();
        if (Objects.isNull(request.getDragon())) {
            return new Response(Status.ASK_OBJECT, "Для команды " + this.getName() + " требуется объект");
        }
        if (request.getDragon().compareTo(Objects.requireNonNull(collectionManager.getDragons().stream()
                .filter(Objects::nonNull)
                .max(Dragon::compareTo)
                .orElse(null))) >= 1) {
            int new_id = DatabaseHandler.getDatabaseManager().addObject(request.getDragon(), request.getUser());
            if(new_id == -1) return new Response(Status.ERROR, "Объект добавить не удалось");
            request.getDragon().setId(new_id);
            request.getDragon().setUserLogin(request.getUser().name());
            collectionManager.add(request);
            return new Response(Status.OK, "Объект успешно добавлен");
        }
        return new Response(Status.ERROR, "Элемент меньше максимального");
    }
}
