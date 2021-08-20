
import com.fazecast.jSerialComm.*;
import org.apache.commons.lang3.ArrayUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class SerialInOut {

    private SerialPort comPort = null;

    SerialInOut() {
        SerialPort[] serialPorts;
        serialPorts = SerialPort.getCommPorts();

        PrintAvailablePorts(serialPorts);

        comPort = serialPorts[0];

        PrintCurrentSerialPort(comPort);

        comPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0);
        comPort.setComPortParameters(230400, 8, 1, SerialPort.NO_PARITY);

        comPort.openPort();
    }

    public void writeNextPacket(ArrayList<Byte> packet) {

        byte[] outBuffer = ArrayUtils.toPrimitive(packet.toArray(new Byte[0]));

        try {
            comPort.writeBytes(outBuffer, outBuffer.length);

        } catch (Exception e) {
        }
    }

    public byte[] readNextPacket() {
        int numRead = 0;
        byte[] readBuffer = {};
        try {
            while (numRead == 0) {

                while (comPort.bytesAvailable() == 0)
                    Thread.sleep(20);

                readBuffer = new byte[comPort.bytesAvailable()];

                numRead = comPort.readBytes(readBuffer, readBuffer.length);

            }

        } catch (Exception e) {
            comPort.closePort();
            e.printStackTrace();
        }
        return readBuffer;
    }

    private void PrintAvailablePorts(SerialPort[] serialPorts) {
        System.out.println("Available serial ports : ");
        for (SerialPort serialPort : serialPorts) {
            System.out.println(serialPort.getDescriptivePortName());
            System.out.println(serialPort.getSystemPortName());
            System.out.println(serialPort.getPortDescription());
        }
    }

    private void PrintCurrentSerialPort(SerialPort comPort) {
        System.out.println("-----------------------------------------");
        System.out.println("Current serial port :");

        System.out.println(comPort.getDescriptivePortName());
        System.out.println(comPort.getSystemPortName());
        System.out.println(comPort.getPortDescription());
        System.out.println("------------------------------------------");
    }

    public void closeSerialInOut() {
        comPort.closePort();
    }
}
