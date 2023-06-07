package network;

public class Response {

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
