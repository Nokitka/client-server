package commands.available;

import commands.abstact.CollectionWorker;
import managers.CollectionManager;
import network.Request;
import network.Response;
import network.Status;

import java.util.Objects;

/**
 * Class implements command filter_starts_with_description description.
 * Command output elements, which start with input string
 */
//add try+catch
public class FilterStartsWithDescription extends CollectionWorker {

    public FilterStartsWithDescription(CollectionManager collectionManager) {
        super("filter_starts_with_description", " {description} : вывести драконов, описание которых начинается с заданной строки", collectionManager);
    }

    /**
     * Исполнить команду
     *
     * @param request запрос с клиента
     */
    @Override
    public Response execute(Request request) throws IllegalArgumentException {
        if (!request.getArg().isBlank()) throw new IllegalArgumentException();
        String description = request.getArg().trim();

        return new Response(Status.OK, collectionManager.getDragons().stream()
                .filter(Objects::nonNull)
                .filter(s -> s.getDescription().startsWith(description))
                .map(Objects::toString)
                .toString());
    }
}
