package commands.available;

import commands.abstact.CollectionWorker;
import managers.CollectionManager;
import network.Request;
import network.Response;
import network.Status;

/**
 * Class implements command remove_by_id.
 * Command remove element by input id
 */
//add try+catch
public class RemoveById extends CollectionWorker {

    public RemoveById(CollectionManager collectionManager) {
        super("remove_by_id", " {id} : удалить элемент из коллекции по его id", collectionManager);
    }

    /**
     * Исполнить команду
     *
     * @param request аргументы команды
     * @throws IllegalArgumentException неверные аргументы команды
     */
    @Override
    public Response execute(Request request) throws IllegalArgumentException {
        if (request.getArg().isBlank()) throw new IllegalArgumentException();
        class NoSuchId extends RuntimeException {
        }
        try {
            int id = Integer.parseInt(request.getArg().trim());
            if (!collectionManager.existId(id)) throw new NoSuchId();
            collectionManager.removeDragon(id);
            return new Response(Status.OK, "Объект удален успешно");
        } catch (NoSuchId err) {
            return new Response(Status.ERROR, "В коллекции нет элемента с таким id");
        } catch (NumberFormatException exception) {
            return new Response(Status.WRONG_ARGUMENTS, "id должно быть числом типа int");
        }
    }
}
