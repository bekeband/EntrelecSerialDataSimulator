
import com.fazecast.jSerialComm.*;

import java.util.ArrayList;

public class Simulator {

    private Controller controller;

    public Simulator() {
        controller = new Controller();
    }

    public Controller getController() {
        return controller;
    }

    public void Run() {

        SerialInOut serialInOut = new SerialInOut();

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
    }

    @Override
    public String toString() {
        return "Simulator{}";
    }

}
