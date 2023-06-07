package commands.available;

import commands.abstact.CollectionWorker;
import managers.CollectionManager;
import network.Request;
import network.Response;
import network.Status;

import java.util.Objects;

/**
 * Class implements command count_less_than_age age.
 * Command output count of elements, which age lower than input parameter
 */
//add try+catch
public class CountLessThanAge extends CollectionWorker {

    public CountLessThanAge(CollectionManager collectionManager) {
        super("count_less_than_age", " {age}: количество драконов младше данного возраста", collectionManager);
    }

    @Override
    public Response execute(Request request) throws IllegalArgumentException {

        if (request.getArg().isBlank()) throw new IllegalArgumentException();
        try {
            long age = Long.parseLong(request.getArg().trim());
            return new Response(Status.OK, "Количество элементов, с меньшим значением поля age: " +
                    collectionManager.getDragons().stream()
                            .filter(Objects::nonNull)
                            .filter(s -> Long.compare(s.getAge(), age) <= -1)
                            .map(Objects::toString)
                            .count());

        } catch (NumberFormatException exception) {
            return new Response(Status.ERROR, "age должно быть числом типа long");
        }

    }
}
