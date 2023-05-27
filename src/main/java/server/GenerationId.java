package server;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static java.lang.Long.parseLong;

/**
 * A class that creates unique id for each dragon
 */
public class GenerationId {

    private final static Set<Integer> setId = new HashSet<>();

    /**
     * Generate unique id greater than zero
     * @return unique id (int)
     */
    public static long generatorId() {
        long id;
        String generateUUIDNo = String.format("%010d", new BigInteger(UUID.randomUUID().toString().replace("-", ""), 16));
        id = parseLong(generateUUIDNo.substring(generateUUIDNo.length() - 10), 10);
        return id;
    }

}
