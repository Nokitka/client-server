package common.data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Class includes all characteristics of dragon
 */
public class Dragon implements Comparable<Dragon> {

    private Long id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически

    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private LocalDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически

    private Long age; //Значение поля должно быть больше 0

    private String description; //Поле не может быть null

    private boolean speaking;

    private DragonCharacter character; //Поле не может быть null
    private DragonHead head;

    public Dragon() {
        this.coordinates = new Coordinates();
        this.head = new DragonHead();
        this.creationDate = LocalDateTime.now();
    }

    public Dragon(Long id, String name, Coordinates coordinates, Long age, String description, boolean speaking, DragonCharacter dragonCharacter, DragonHead dragonHead) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = LocalDateTime.now();
        this.age = age;
        this.description = description;
        this.speaking = speaking;
        this.character = dragonCharacter;
        this.head = dragonHead;
    }

    
    /*public String toString() {
        return "Dragon_" + id + " {" +
                "id : " + id +
                ", name : '" + name + "\', " +
                coordinates.toString() +
                ", creationDate : " + creationDate.format(DateTimeFormatter.ofPattern("hh:mm:ss")) +
                ", age : " + age +
                ", description : '" + description + '\'' +
                ", speaking : " + speaking +
                ", character : " + character.getTittle() +
                ", " + head.toString() +
                '}';
    }*/

    public String toCSV() {
        return id +
                ", \"" + name + "\", " +
                coordinates.toCSVCoordinates() +
                ", \"" + creationDate.format(DateTimeFormatter.ofPattern("hh:mm:ss")) +
                "\", " + age +
                ", \"" + description + '\"' +
                ", \"" + speaking +
                "\", \"" + character.getClass() +
                "\", " + head.toCSVHead();
    }

    
    public int compareTo(Dragon dragon) {
        int nameCompare = this.getName().compareToIgnoreCase(dragon.getName());
        if (nameCompare != 0)
            return nameCompare;
        else
            return Long.compare(this.getAge(), dragon.getAge());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public Long getAge() {
        return age;
    }

    public void setAge(long age) {
        this.age = age;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isSpeaking() {
        return speaking;
    }

    public void setSpeaking(boolean speaking) {
        this.speaking = speaking;
    }

    public DragonCharacter getCharacter() {
        return character;
    }

    public void setCharacter(DragonCharacter character) {
        this.character = character;
    }

    public DragonHead getHead() {
        return head;
    }

    public void setHead(DragonHead head) {
        this.head = head;
    }

    public boolean validData() {
        if (id == -1) return false;
        if (name.equals("")) return false;
        if (coordinates.getX() == null || coordinates.getY() == null) return false;
        if (age == -1) return false;
        if (character == DragonCharacter.ERROR) return false;
        //if (speaking == null) return false;
        if (head.getEyesCount() == -1 || head.getToothCount() == -1) return false;
        return true;
    }
}
