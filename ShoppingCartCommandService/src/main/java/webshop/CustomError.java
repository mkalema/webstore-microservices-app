package webshop;

public class CustomError {

    private String message;

    public CustomError() {
    }

    public CustomError(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "CustomError{" +
                "message='" + message + '\'' +
                '}';
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
