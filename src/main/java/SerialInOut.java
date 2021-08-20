
import com.fazecast.jSerialComm.*;
import org.apache.commons.lang3.ArrayUtils;

import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SerialInOut {

    private final static Logger LOGGER = Logger.getLogger(Simulator.class.getName());
    private SerialPort comPort = null;

    String comPortName;
    Integer baudRate;
    Integer dataBits;
    Integer stopBits;
    Integer parity;

    public SerialPort getComPort() {
        return comPort;
    }

    public void setComPort(SerialPort comPort) {
        this.comPort = comPort;
    }

    public void setComPortName(String comPortName) {
        this.comPortName = comPortName;
    }

    public Integer getBaudRate() {
        return baudRate;
    }

    public void setBaudRate(Integer baudRate) {
        this.baudRate = baudRate;
    }

    public void setBaudRate(String baudRate) {
        try {
            this.baudRate = Integer.parseInt(baudRate);
        } catch (NumberFormatException ex) {

        }
    }

    public Integer getDataBits() {
        return dataBits;
    }

    public void setDataBits(Integer dataBits) {
        this.dataBits = dataBits;
    }

    public void setDataBits(String dataBits) {
        try {
            this.dataBits = Integer.parseInt(dataBits);
        } catch (NumberFormatException ex) {

        }
    }

    public Integer getStopBits() {
        return stopBits;
    }

    public void setStopBits(Integer stopBits) {
        this.stopBits = stopBits;
    }

    public void setStopBits(String stopBits) {
        try {
            this.stopBits = Integer.parseInt(stopBits);
        } catch (NumberFormatException ex) {

        }
    }

    public Integer getParity() {
        return parity;
    }

    public void setParity(Integer parity) {
        this.parity = parity;
    }

    public void setParity(String parity) {
        if (parity.equalsIgnoreCase("none")) {
            this.parity = SerialPort.NO_PARITY;
        } else if (parity.equalsIgnoreCase("even")) {
            this.parity = SerialPort.EVEN_PARITY;
        } else if (parity.equalsIgnoreCase("odd")) {
            this.parity = SerialPort.ODD_PARITY;
        } else if (parity.equalsIgnoreCase("mark")) {
            this.parity = SerialPort.MARK_PARITY;
        } else if (parity.equalsIgnoreCase("space")) {
            this.parity = SerialPort.SPACE_PARITY;
        } else {
            throw new NoSuchParityException("No such parity value : " + parity);
        }
    }

    SerialInOut() {

    }

    public void setSerialFeatures(String comPortName, String baudRate, String dataBits, String stopBits, String parity) {
        SerialPort[] serialPorts;

        setComPortName(comPortName);
        setBaudRate(baudRate);
        setDataBits(dataBits);
        setStopBits(stopBits);
        setParity(parity);

        serialPorts = SerialPort.getCommPorts();
        LOGGER.setLevel(Level.INFO);
        LOGGER.info("Avaliable serial ports : " + serialPorts);
        LOGGER.info("Required com port name : " + comPortName);

        LOGGER.info("Serial port features: Baud = " + baudRate);

        try {
            comPort = Arrays.stream(serialPorts)
                    .filter(name -> name.getSystemPortName().equalsIgnoreCase(comPortName))
                    .findFirst().orElseThrow();
        } catch (NoSuchElementException ex) {


            ex.printStackTrace();
        }

        PrintCurrentSerialPort(comPort);

        comPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0);
        comPort.setComPortParameters(getBaudRate(), getDataBits(), getStopBits(), getParity());
    }

    public void startPort() {
        comPort.openPort();
    }

    public void stopPort() {
        comPort.closePort();
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

                while (comPort.bytesAvailable() <= 0)
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
