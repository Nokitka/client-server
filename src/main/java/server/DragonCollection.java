package server;

import common.DataManager;
import common.data.Dragon;
import common.data.DragonCharacter;
import common.network.CommandResult;
import common.network.Request;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.TreeSet;

public class DragonCollection extends DataManager implements Serializable {

//----------------------Variables----------------------

    private String filename;
    private TreeSet<Dragon> dragons;
    private LocalDateTime creationDate;
    private Parser parser;

//----------------------Constructor----------------------

    public DragonCollection(Parser parser) {
        this.creationDate = LocalDateTime.now();
        this.dragons = new TreeSet<>(new CustomComparator());
        this.parser = parser;
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

//----------------------Methods----------------------

    public String dragonInfo(Dragon dragon) {
        return ("id : " + dragon.getId() +
                "\nИмя : '" + dragon.getName() +
                "\nX = " + dragon.getCoordinates().getX() +
                "\nY = " + dragon.getCoordinates().getY() +
                "\nДата создания : " + dragon.getCreationDate().format(DateTimeFormatter.ofPattern("hh:mm:ss")) +
                "\nВозраст : " + dragon.getAge() +
                "\nОписание : '" + dragon.getDescription() +
                "\nУмение разговаривать : " + dragon.isSpeaking() +
                "\nХарактер : " + dragon.getCharacter().getTittle() +
                "\nКоличество глаз = " + dragon.getHead().getEyesCount() +
                "\nКоличество зубов = " + dragon.getHead().getToothCount());
    }

    public String information() {
        if (dragons.isEmpty()) {
            return "Коллекция пустая";
        }
        StringBuilder info = new StringBuilder();
        for (Dragon dragon : dragons) {
            info.append(dragonInfo(dragon));
        }
        return info.toString();
    }

    public boolean existId(int id) {
        for (Dragon dragon : dragons) {
            if (dragon.getId() == id)
                return true;
        }
        return false;
    }

    public void removeDragon(int id){
        for (Dragon dragon : dragons) {
            if (dragon.getId() == id){
                dragons.remove(dragon);
                return;
            }
        }
    }

//----------------------Methods of commands----------------------

    public CommandResult add(Request<?> request) {
        try {
            Dragon person = (Dragon) request.type;
            //dragon.setId(generateNextId());
            dragons.add(person);
            return new CommandResult(true, "Новый элемент успешно добавлен");
        } catch (Exception exception) {
            return new CommandResult(false, "Передан аргумент другого типа");
        }
    }

    public CommandResult addIfMax(Request<?> request) {
        Dragon dragon = (Dragon) request.type;
        if (dragons.last().compareTo(dragon) < 0) {
            dragons.add(dragon);
            return new CommandResult(true, "Дракон успешно добавлен");
        }
        return new CommandResult(false, "Дракон не был добавлен");
    }

    public CommandResult addIfMin(Request<?> request) {
        Dragon dragon = (Dragon) request.type;
        if (dragons.first().compareTo(dragon) > 0) {
            dragons.add(dragon);
            return new CommandResult(true, "Дракон успешно добавлен");
        }
        return new CommandResult(false, "Дракон не был добавлен");
    }

    public CommandResult show(Request<?> request) {
        return new CommandResult(true, information());
    }

    public CommandResult clear(Request<?> request) {
        dragons.clear();
        return new CommandResult(true, "Коллекция очищена");
    }

    public CommandResult info(Request<?> request) {
        String info = "Тип : " + dragons.getClass() +
                "\nРазмер = " + dragons.size() +
                "\nДата создания : " + creationDate.format(DateTimeFormatter.ofPattern("hh:mm:ss"));
        return new CommandResult(true, info);
    }

    public CommandResult help(Request<?> request) {
        return null;
    }

    public CommandResult filterContainsName(Request<?> request) {
        return null;
    }

    public CommandResult filterStartsWithDescription(Request<?> request) {
        return null;
    }

    public CommandResult countLessThanAges(Request<?> request) {
        return null;
    }

    public CommandResult removeById(Request<?> request) {
        return null;
    }

    public CommandResult removeGreater(Request<?> request) {
        return null;
    }

    public CommandResult updateId(Request<?> request) {
        return null;
    }
}
