package commands.available;

import managers.CollectionManager;

public abstract class CollectionWorker extends Command{

    protected CollectionManager collectionManager;

    public CollectionManager getDragons() {
        return collectionManager;
    }

    public CollectionWorker(String name, String description, CollectionManager collectionManager) {
        super(name, description);
        this.collectionManager = collectionManager;
    }



}
