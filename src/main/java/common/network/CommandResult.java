package common.network;

public class CommandResult {

    private final String message;
    private boolean status;

    public CommandResult(boolean status, String message) {
        this.message = message;
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public boolean isStatus() {
        return status;
    }
}
