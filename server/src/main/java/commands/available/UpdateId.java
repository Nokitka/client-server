package commands.available;

import managers.CollectionManager;
import network.Request;
import network.Response;
import network.Status;

import java.util.Objects;

/**
 * Class implements command update id {element}.
 * Command update element in collection with input id
 */
public class UpdateId extends CollectionWorker {

    public UpdateId(CollectionManager collectionManager) {
        super("update", " id {element}: обновить значение элемента коллекции, id которого равен заданному", collectionManager);
    }

    /**
     * Исполнить команду
     * @param request аргументы команды
     * @throws IllegalArgumentException неверные аргументы команды
     */
    @Override
    public Response execute(Request request) throws IllegalArgumentException{
        if (request.getArg().isBlank()) throw new IllegalArgumentException();
        class NoSuchId extends RuntimeException{

        }
        try {
            int id = Integer.parseInt(request.getArg().trim());
            if (!collectionManager.existId(id)) throw new NoSuchId();
            if (Objects.isNull(request.getDragon())){
                return new Response(Status.ASK_OBJECT, "Для команды " + this.getName() + " требуется объект");
            }
            collectionManager.editById(id, request.getDragon());
            return new Response(Status.OK, "Объект успешно обновлен");
        } catch (NoSuchId err) {
            return new Response(Status.ERROR,"В коллекции нет элемента с таким id");
        } catch (NumberFormatException exception) {
            return new Response(Status.ERROR,"id должно быть числом типа int");
        }
    }
}
