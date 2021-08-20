public class PacketException extends Exception{
    String message;

    public PacketException(String message, String message1) {
        super(message);
        this.message = message1;
    }

    public PacketException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return super.getMessage() + message;
    }
}
