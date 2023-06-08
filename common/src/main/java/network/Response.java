package network;

import java.io.Serializable;

/**
 * Класс ответа сервера
 */
public class Response implements Serializable {

    private String message;
    private Status status;

    public Response(Status status) {
        this.status = status;
    }

    public Response(Status status, String message) {
        this.message = message;
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public Status getStatus() {
        return status;
    }
}
