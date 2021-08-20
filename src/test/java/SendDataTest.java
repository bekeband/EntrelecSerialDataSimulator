import com.fazecast.jSerialComm.SerialPort;

public class SendDataTest {
    public static void main(String[] args) {

        SerialPort[] serialPorts;

        serialPorts = SerialPort.getCommPorts();

        SerialPort comPort = serialPorts[1];
        System.out.println(comPort.getDescriptivePortName());
        System.out.println(comPort.getSystemPortName());
        System.out.println(comPort.getPortDescription());
        System.out.println("------------------------------------------");

        comPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0);
        comPort.setComPortParameters(115200, 8, 1, SerialPort.NO_PARITY);

        final byte[] outBuffer = {1, 16, 1, 33, 16, 16, 4};

        comPort.openPort();
        try {
            int numRead = comPort.writeBytes(outBuffer, outBuffer.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
        comPort.closePort();
    }
}
