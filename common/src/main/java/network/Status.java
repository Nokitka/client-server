package network;

import java.io.Serializable;

/**
 * Класс со статусами исполнения запроса
 */
public enum Status implements Serializable {

    OK,
    EXIT,
    ERROR,
    WRONG_ARGUMENTS,
    ASK_OBJECT,
    EXECUTE_SCRIPT

}
