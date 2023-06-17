package managers;

import data.Dragon;
import network.Request;
import network.Response;
import network.Status;
import utils.CustomComparator;

import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;
import java.util.TreeSet;

/**
 * Класс для работы с коллекцией драконов
 */
public class CollectionManager implements Serializable {

//----------------------Variables----------------------

    private String filename;
    private TreeSet<Dragon> dragons;
    private LocalDateTime creationDate;
    private Parser parser;

//----------------------Constructor----------------------

    public CollectionManager(Parser parser) {
        this.creationDate = LocalDateTime.now();
        this.dragons = new TreeSet<>(new CustomComparator());
        this.parser = parser;
    }

    public CollectionManager(TreeSet<Dragon> dragons) {
        this.dragons = dragons;
    }

    public CollectionManager() {
    }

//----------------------Getters&Setters----------------------

    public TreeSet<Dragon> getDragons() {
        return dragons;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setDragons(TreeSet<Dragon> dragons) {
        this.dragons = dragons;
    }

//----------------------Auxiliary methods----------------------

    public String dragonInfo(Dragon dragon) {
        return ("id : " + dragon.getId() +
                "\n     Имя : " + dragon.getName() +
                "\n     X = " + dragon.getCoordinates().getX() +
                "\n     Y = " + dragon.getCoordinates().getY() +
                "\n     Дата создания : " + dragon.getCreationDate().format(DateTimeFormatter.ofPattern("hh:mm:ss")) +
                "\n     Возраст : " + dragon.getAge() +
                "\n     Описание : " + dragon.getDescription() +
                "\n     Умение разговаривать : " + dragon.isSpeaking() +
                "\n     Характер : " + dragon.getCharacter().getCode() +
                "\n     Количество глаз = " + dragon.getHead().getEyesCount() +
                "\n     Количество зубов = " + dragon.getHead().getToothCount()) + "\n";
    }

    public boolean existId(int id) {
        for (Dragon dragon : dragons) {
            if (dragon.getId() == id)
                return true;
        }
        return false;
    }

    public void removeDragonById(long id) {
        for (Dragon dragon : dragons) {
            if (dragon.getId() == id) {
                dragons.remove(dragon);
                return;
            }
        }
    }

    public void removeDragon(Dragon rmvDragon) {
        for (Dragon dragon : dragons) {
            if (dragon.equals(rmvDragon)) {
                dragons.remove(dragon);
                return;
            }
        }
    }

    public void removeDragons(Collection<Dragon> collection) {
        this.dragons.removeAll(collection);
    }

    public void removeDragonsByListOfIds(List<Long> delete) {
        for (long id : delete){
            for (Dragon dragon : this.dragons){
                if (dragon.getId() == id){
                    dragons.remove(dragon);
                    break;
                }
            }
        }
    }

    /*public void updateDragon(Dragon update, int id) {
        for (Dragon dragon : dragons) {
            if (dragon.getId() == id) {
                dragon.setName(update.getName());
                dragon.setCoordinates(update.getCoordinates());
                dragon.setAge(update.getAge());
                dragon.setDescription(update.getDescription());
                dragon.setSpeaking(update.isSpeaking());
                dragon.setCharacter(update.getCharacter());
                dragon.setHead(update.getHead());
            }
        }
    }*/

    public Dragon getById(long id) {
        for (Dragon dragon : dragons) {
            if (dragon.getId() == id) {
                return dragon;
            }
        }
        return null;
    }

    public void editById(long id, Dragon newElement) {
        Dragon pastElement = this.getById(id);
        this.removeDragonById(pastElement.getId());
        newElement.setId(id);
        this.dragons.add(newElement);
    }

//----------------------Methods of commands----------------------

    public Response add(Request request) {
        Dragon dragon = request.getDragon();
        dragons.add(dragon);
        return new Response(Status.OK, "Новый элемент успешно добавлен");
    }

    public String show() {
        if (dragons.isEmpty()) {
            return "Коллекция пустая";
        }
        StringBuilder info = new StringBuilder();
        for (Dragon dragon : dragons) {
            info.append(dragonInfo(dragon));
        }
        return info.toString();
    }

    public String info() {
        String info = "Тип : " + dragons.getClass() +
                "\nРазмер = " + dragons.size() +
                "\nДата создания : " + creationDate.format(DateTimeFormatter.ofPattern("hh:mm:ss"));
        return info;
    }

    public Response help(Request request) {
        return null;
    }

    public Response filterContainsName(Request request) {
        return null;
    }

    public Response filterStartsWithDescription(Request request) {
        return null;
    }

    public Response countLessThanAges(Request request) {
        return null;
    }

    public Response removeById(Request request) {
        return null;
    }

    public Response removeGreater(Request request) {
        return null;
    }

    public Response updateId(Request request) {
        return null;
    }

    public void save() {
        // написать request и command(там будет инициализация объектов команд) manager, почитать про udp в яве!!!
        // перед этим исправь во всем командах ошибки
    }

    public String saveCollection() {
        return parser.convertToCSV(this);
    }


}
