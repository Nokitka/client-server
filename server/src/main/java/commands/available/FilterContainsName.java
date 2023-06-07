package commands.available;

import commands.abstact.CollectionWorker;
import managers.CollectionManager;
import network.Request;
import network.Response;
import network.Status;

import java.util.Objects;

/**
 * Class implements command filter_contains_name name.
 * Command output elements, value of field name contains input string
 */
public class FilterContainsName extends CollectionWorker {

    public FilterContainsName(CollectionManager collectionManager) {
        super("filter_contains_name", " {name} : вывести драконов, в имени которых содержится строка", collectionManager);
    }

    /**
     * Исполнить команду
     *
     * @param request запрос с клиента
     */
    @Override
    public Response execute(Request request) throws IllegalArgumentException {
        if (!request.getArg().isBlank()) throw new IllegalArgumentException();
        String name = request.getArg().trim();

        return new Response(Status.OK, collectionManager.getDragons().stream()
                .filter(Objects::nonNull)
                .filter(s -> s.getName().contains(name))
                .map(Objects::toString)
                .toString());
    }
}
