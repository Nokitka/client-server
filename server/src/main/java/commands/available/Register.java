package commands.available;

import commands.abstact.Command;
import managers.DatabaseManager;
import network.Request;
import network.Response;
import network.Status;

import java.sql.SQLException;

public class Register extends Command {
    DatabaseManager databaseManager;

    public Register(DatabaseManager databaseManager) {
        super("register", ": зарегистрировать пользователя");
        this.databaseManager = databaseManager;
    }

    @Override
    public Response execute(Request request) throws IllegalArgumentException {
        try {
            databaseManager.addUser(request.getUser());
        } catch (SQLException e) {
            System.out.println("Невозможно добавить пользователя");
            return new Response(Status.LOGIN_FAILED, "Введен невалидный пароль!");
        }
        return new Response(Status.OK,"Вы успешно зарегистрированы");
    }
}
