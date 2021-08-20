import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SimulatorTest {

    @Test
    void run() {

        Simulator simulator= new Simulator();
        SimulatorUI simulatorUI = new SimulatorUI(simulator.getController());
        simulator.Run();

        simulatorUI.printHexList(simulator.getController().appMemoryArea, 0x1D000004, 0x1D000045);
        System.out.println();
        simulatorUI.printHexList(simulator.getController().configMemoryArea, 0x1FC02FF0, 0x1FC02FFF);
        System.out.println();
        simulatorUI.printHexList(simulator.getController().bootMemoryArea, 0x1FC00000, 0x1FC0007F);


    }
}