
import com.fazecast.jSerialComm.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Properties;

public class Simulator {

    private final static Logger LOGGER = Logger.getLogger(Simulator.class.getName());

    String COM_PORT_STRING = "com1";
    String BAUD_STRING = "9600";
    String DATABITS_STRING = "8";
    String STOPBITS_STRING = "1";
    String PARITY_STRING = "none";

    private Controller controller;

    public Simulator() {

        Path configPath = Paths.get("config.properties");
        LOGGER.setLevel(Level.INFO);
        LOGGER.info("Properties file path: " + configPath.toAbsolutePath());

        try (InputStream input = new FileInputStream(configPath.toAbsolutePath().toString())) {

            LOGGER.log(Level.INFO, "Load config file: " + configPath.toAbsolutePath());

            Properties prop = new Properties();

            // load a properties file
            prop.load(input);

            // get the property value and print it out
            COM_PORT_STRING = prop.getProperty("comport", "com1");
            BAUD_STRING = prop.getProperty("baud", "9600");
            DATABITS_STRING = prop.getProperty("databits", "8");
            STOPBITS_STRING = prop.getProperty("stopbits", "1");
            PARITY_STRING = prop.getProperty("parity", "none");

        } catch (FileNotFoundException ex) {
            LOGGER.setLevel(Level.WARNING);
            LOGGER.warning("Couldn't find config.properties file.");
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        controller = new Controller();
    }

    public Controller getController() {
        return controller;
    }

    public void Run() {

        SerialInOut serialInOut = new SerialInOut();
        serialInOut.setSerialFeatures(COM_PORT_STRING, BAUD_STRING, DATABITS_STRING, STOPBITS_STRING, PARITY_STRING);
        serialInOut.startPort();

        while (true) {
            byte[] readBuffer = serialInOut.readNextPacket();
            ProcessFrame processFrame = new ProcessFrame();

            try {

                ArrayList<Byte> result = processFrame.TranslatePacket(readBuffer);

                ProcessCommands processCommands = new ProcessCommands(serialInOut, controller);
                processCommands.Process(result);

            } catch (PacketException e) {
                System.out.println(e.getMessage());
            }
        }
//        serialInOut.stopPort();
    }

    @Override
    public String toString() {
        return "Simulator{}";
    }

}
