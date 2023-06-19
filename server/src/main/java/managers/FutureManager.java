package managers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;


public class FutureManager {
    private static final Collection<Future<ConnectionManagerPool>> cashedThreadPoolFutures = new ArrayList<>();

    public static void addNewFixedThreadPoolFuture(Future<ConnectionManagerPool> future){
        cashedThreadPoolFutures.add(future);
    }

    public static void checkAllFutures() {

        if(!cashedThreadPoolFutures.isEmpty()) {
            cashedThreadPoolFutures.forEach(s -> System.out.println(s.toString()));
        }
        cashedThreadPoolFutures.stream()
                .filter(Future::isDone)
                .forEach(f -> {
                    try {
                        ConnectionManager.submitNewResponse(f.get());
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                    }
                });
        cashedThreadPoolFutures.removeIf(Future::isDone);
    }
}