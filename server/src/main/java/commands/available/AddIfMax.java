package commands.available;

import managers.CollectionManager;
import data.Dragon;
import network.Request;
import network.Response;
import network.Status;

import java.util.Objects;

/**
 * Class implements command add_if_max.
 * Command add element in collection, if input object higher than the max element
 */
public class AddIfMax extends CollectionWorker {
    public AddIfMax(CollectionManager collectionManager) {
        super("add_if_max", " {element}: добавить элемент в коллекцию если он больше максимального", collectionManager);
    }

    @Override
    public Response execute(Request request) throws IllegalArgumentException {
        if (!request.getArg().isBlank()) throw new IllegalArgumentException();
        if (Objects.isNull(request.getDragon())){
            return new Response(Status.ASK_OBJECT, "Для команды " + this.getName() + " требуется объект");
        }
        if (request.getDragon().compareTo(Objects.requireNonNull(collectionManager.getDragons().stream()
                .filter(Objects::nonNull)
                .max(Dragon::compareTo)
                .orElse(null))) >= 1)
        {
            collectionManager.add(request);
            return new Response(Status.OK,"Объект успешно добавлен");
        }
        return new Response(Status.ERROR,"Элемент меньше максимального");
    }
}
