package managers;

import data.Coordinates;
import data.Dragon;
import data.DragonCharacter;
import data.DragonHead;
import main.App;
import network.User;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ArrayDeque;
import java.util.List;
import java.util.Properties;
import java.util.Random;

public class DatabaseManager {
    private Connection connection;
    private MessageDigest md;

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrs" +
            "tuvwxyz0123456789<>?:@{!$%^&*()_+£$";
    private static final String PEPPER = "[g$J*(l;";

    public DatabaseManager(){
        try {
            md = MessageDigest.getInstance(App.HASHING_ALGORITHM);

            this.connect();
            this.createMainBase();
        } catch (SQLException e) {
            System.out.println("Ошибка при исполнении изначального запроса либо таблицы уже созданы");
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Такого алгоритма нет!");
        }
    }

    public void connect(){
        Properties info = null;
        try {
            info = new Properties();
            info.load(new FileInputStream(App.DATABASE_CONFIG_PATH));
            connection = DriverManager.getConnection(App.DATABASE_URL, info);
            System.out.println("Успешно подключен к базе данных");
        } catch (SQLException | IOException e) {
            try{

                connection = DriverManager.getConnection(App.DATABASE_URL_HELIOS, info);
            } catch (SQLException ex) {
                ex.printStackTrace();
                System.out.println("Невозможно подключиться к базе данных");
                System.exit(1);
            }
        }
    }

    public void createMainBase() throws SQLException {
        connection
                .prepareStatement(DatabaseCommands.allTablesCreation)
                .execute();
        System.out.println("Таблицы созданы");
    }

    public void addUser(User user) throws SQLException {
        String login = user.name();
        String salt = this.generateRandomString();
        String pass = PEPPER + user.password() + salt;

        PreparedStatement ps = connection.prepareStatement(DatabaseCommands.addUser);
        if (this.checkExistUser(login)) throw new SQLException();
        ps.setString(1, login);
        ps.setString(2, this.getMD5Hash(pass));
        ps.setString(3, salt);
        ps.execute();
        System.out.println("Добавлен юзер " + user);
    }

    public boolean confirmUser(User inputUser){
        try {
            String login = inputUser.name();
            PreparedStatement getUser = connection.prepareStatement(DatabaseCommands.getUser);
            getUser.setString(1, login);
            ResultSet resultSet = getUser.executeQuery();
            if(resultSet.next()) {
                String salt = resultSet.getString("salt");
                String toCheckPass = this.getMD5Hash(PEPPER + inputUser.password() + salt);
                return toCheckPass.equals(resultSet.getString("password"));
            }
            else {
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Неверная команда sql!");
            return false;
        }
    }

    public boolean checkExistUser(String login) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(DatabaseCommands.getUser);
        ps.setString(1, login);
        ResultSet resultSet = ps.executeQuery();
        return resultSet.next();
    }

    // Метод возвращает -1 при ошибке добавления объекта
    public int addObject(Dragon dragon, User user){
        try {
            PreparedStatement ps = connection.prepareStatement(DatabaseCommands.addObject);
            ps.setString(1, dragon.getName());
            ps.setInt(2, dragon.getCoordinates().getX());
            ps.setFloat(3, dragon.getCoordinates().getY());
            ps.setDate(4,  java.sql.Date.valueOf(dragon.getCreationDate().toLocalDate()));
            ps.setLong(5, dragon.getAge());
            ps.setString(6, dragon.getDescription());
            ps.setBoolean(7, dragon.isSpeaking());
            ps.setObject(8, dragon.getCharacter(), Types.OTHER);
            ps.setFloat(9, dragon.getHead().getEyesCount());
            ps.setInt(10, dragon.getHead().getToothCount());
            ps.setString(11, user.name());
            ResultSet resultSet = ps.executeQuery();

            if (!resultSet.next()) {
                System.out.println("Объект не добавлен в таблицу");
                return -1;
            }
            System.out.println("Объект добавлен в таблицу");
            return resultSet.getInt(1);
        } catch (SQLException e) {
            System.out.println("Объект не добавлен в таблицу");
            return -1;
        }
    }

    public boolean updateObject(long id, Dragon dragon, User user){
        try {
            PreparedStatement ps = connection.prepareStatement(DatabaseCommands.updateUserObject);
            ps.setString(1, dragon.getName());
            ps.setInt(2, dragon.getCoordinates().getX());
            ps.setFloat(3, dragon.getCoordinates().getY());
            ps.setDate(4,  java.sql.Date.valueOf(dragon.getCreationDate().toLocalDate()));
            ps.setLong(5, dragon.getAge());
            ps.setString(6, dragon.getDescription());
            ps.setBoolean(7, dragon.isSpeaking());
            ps.setObject(8, dragon.getCharacter(), Types.OTHER);
            ps.setFloat(9, dragon.getHead().getEyesCount());
            ps.setInt(10, dragon.getHead().getToothCount());

            ps.setLong(11, id);
            ps.setString(12, user.name());
            ResultSet resultSet = ps.executeQuery();
            System.out.println(resultSet);
            return resultSet.next();
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean deleteObject(long id, User user){
        try{
            PreparedStatement ps = connection.prepareStatement(DatabaseCommands.deleteUserObject);
            ps.setString(1, user.name());
            ps.setLong(2, id);
            ResultSet resultSet = ps.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            System.out.println("Объект удалить не удалось");
            return false;
        }
    }

    public boolean deleteAllObjects(User user, List<Long> ids){
        try {
            for (Long id : ids) {
                PreparedStatement ps = connection.prepareStatement(DatabaseCommands.deleteUserOwnedObjects);
                ps.setString(1, user.name());
                ps.setLong(2, id);
                ResultSet resultSet = ps.executeQuery();
            }
            System.out.println("Удалены все строки таблицы Dragons принадлежащие " + user.name());
            return true;
        } catch (SQLException e) {
            System.out.println("Удалить строки таблицы Dragon не удалось!");
            return false;
        }
    }

    public ArrayDeque<Dragon> loadCollection(){
        try {
            PreparedStatement ps = connection.prepareStatement(DatabaseCommands.getAllObjects);
            ResultSet resultSet = ps.executeQuery();
            ArrayDeque<Dragon> collection = new ArrayDeque<>();
            while (resultSet.next()){
                collection.add(new Dragon(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        new Coordinates(
                                resultSet.getInt("cord_x"),
                                resultSet.getFloat("cord_y")
                        ),
                        resultSet.getDate("creation_date"),
                        resultSet.getInt("age"),
                        resultSet.getString("description"),
                        resultSet.getBoolean("speaking"),
                        DragonCharacter.valueOf(resultSet.getString("character")),
                        new DragonHead(
                                resultSet.getFloat("head_eyes"),
                                resultSet.getInt("head_tooth")
                        ),
                        resultSet.getString("owner_login")
                ));
            }
            System.out.println("Коллекция успешно загружена из таблицы");
            return collection;
        } catch (SQLException e) {
            System.out.println("Коллекция пуста либо возникла ошибка при исполнении запроса");
            return new ArrayDeque<>();
        }
    }

    private String generateRandomString() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder(6);
        for (int i = 0; i < 6; i++) {
            sb.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        return sb.toString();
    }

    private String getMD5Hash(String input){
        byte[] inputBytes = input.getBytes();
        md.update(inputBytes);
        byte[] hashBytes = md.digest();
        StringBuilder sb = new StringBuilder();
        for (byte b : hashBytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}
