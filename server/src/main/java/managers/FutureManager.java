package managers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;


public class FutureManager {
    private static final Collection<Future<ConnectionManagerPool>> fixedThreadPoolFutures = new ArrayList<>();

    public static void addNewFixedThreadPoolFuture(Future<ConnectionManagerPool> future){
        fixedThreadPoolFutures.add(future);
    }

    public static void checkAllFutures() {

        if(!fixedThreadPoolFutures.isEmpty()) {
            fixedThreadPoolFutures.forEach(s -> System.out.println(s.toString()));
        }
        fixedThreadPoolFutures.stream()
                .filter(Future::isDone)
                .forEach(f -> {
                    try {
                        ConnectionManager.submitNewResponse(f.get());
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                    }
                });
        fixedThreadPoolFutures.removeIf(Future::isDone);
    }
}