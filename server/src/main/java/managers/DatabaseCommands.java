package managers;

public class DatabaseCommands {
    public static final String allTablesCreation = """
            CREATE TYPE CHARACTERS AS ENUM(
                'EVIL',
                'GOOD',
                'CHAOTIC_EVIL',
                'FICKLE'
            );
            CREATE TABLE IF NOT EXISTS dragons (
                id SERIAL PRIMARY KEY,
                name TEXT NOT NULL ,
                cord_x INTEGER NOT NULL,
                cord_y FLOAT NOT NULL ,
                creation_date DATE NOT NULL ,
                age BIGINT NOT NULL,
                description TEXT NOT NULL,
                speaking BOOLEAN NOT NULL,
                character CHARACTERS NOT NULL,
                head_eyes FLOAT NOT NULL,
                head_tooth INTEGER NOT NULL,
                owner_login TEXT NOT NULL
            );
            CREATE TABLE IF NOT EXISTS users (
                id SERIAL PRIMARY KEY,
                login TEXT,
                password TEXT,
                salt TEXT
            );
            """;
    public static final String addUser = """
            INSERT INTO users(login, password, salt) VALUES (?, ?, ?);""";

    public static final String getUser = """
            SELECT * FROM users WHERE (login = ?);""";

    public static final String addObject = """
            INSERT INTO dragons(name, cord_x, cord_y, creation_date, age, description, speaking, character, head_eyes, head_tooth, owner_login)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
            RETURNING id;
            """;

    public static final String getAllObjects = """
            SELECT * FROM dragons;
            """;

    public static final String deleteUserOwnedObjects = """
            DELETE FROM dragons WHERE (owner_login = ?) AND (id = ?) RETURNING id;
            """;

    public static final String deleteUserObject = """
            DELETE FROM dragons WHERE (owner_login = ?) AND (id = ?) RETURNING id;
            """;

    public static final String updateUserObject = """
            UPDATE dragons
            SET (name, cord_x, cord_y, creation_date, age, description, speaking, character, head_eyes, head_tooth)
             = (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
            WHERE (id = ?) AND (owner_login = ?)
            RETURNING id;
            """;
}