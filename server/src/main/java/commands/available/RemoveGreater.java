package commands.available;

import commands.abstact.CollectionWorker;
import data.Dragon;
import exceptions.InFileModeException;
import managers.CollectionManager;
import network.Request;
import network.Response;
import network.Status;

import java.util.Collection;
import java.util.Objects;

/**
 * Class implements command remove_greater {element}.
 * Command remove elements that don`t exceed input element
 */
public class RemoveGreater extends CollectionWorker {

    public RemoveGreater(CollectionManager collectionManager) {
        super("remove_greater", " {element} : удалить из коллекции все элементы, превышающие заданный", collectionManager);
    }

    /**
     * Исполнить команду
     *
     * @param request аргументы команды
     * @throws IllegalArgumentException неверные аргументы команды
     */
    @Override
    public Response execute(Request request) throws IllegalArgumentException {
        if (!request.getArg().isBlank()) throw new IllegalArgumentException();
        class NoElements extends RuntimeException {

        }
        try {
            if (Objects.isNull(request.getDragon())) {
                return new Response(Status.ASK_OBJECT, "Для команды " + this.getName() + " требуется объект");
            }
            Collection<Dragon> toRemove = collectionManager.getDragons().stream()
                    .filter(Objects::nonNull)
                    .filter(dragon -> dragon.compareTo(request.getDragon()) >= 1)
                    .toList();
            collectionManager.removeDragons(toRemove);
            return new Response(Status.OK, "Удалены элементы большие чем заданный");
        } catch (NoElements e) {
            return new Response(Status.ERROR, "В коллекции нет элементов");
        } catch (InFileModeException e) {
            return new Response(Status.ERROR, "Поля в файле не валидны! Объект не создан");
        }
    }
}
