import java.util.ArrayList;

public class ProcessCommands {

    final byte READ_BOOT_INFO   = 1;
    final byte ERASE_FLASH      = 2;
    final byte PROGRAM_FLASH    = 3;
    final byte READ_CRC         = 4;
    final byte JMP_TO_APP       = 5;

    final Byte VER_HI = 1;
    final Byte VER_LO = 4;


    SerialInOut curSerial;
    Controller controller;

    public ProcessCommands(SerialInOut curSerial, Controller controller)
    {
        this.curSerial = curSerial;
        this.controller = controller;
    }

    private ArrayList<Byte> MakeSimpleAnswer(Byte command) {
        ArrayList<Byte> result = new ArrayList<>();
        result.add(command);
        return result;
    }

    public void Process(ArrayList<Byte> packet) {
        ArrayList<Byte> result = new ArrayList<>();
        switch (packet.get(0)) {
            case READ_BOOT_INFO:
                System.out.println("Read boot Info command:");
                result.add(READ_BOOT_INFO);
                result.add(VER_HI);
                result.add(VER_LO);
                break;
            case ERASE_FLASH:
                System.out.println("Erase flash command:");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                }
                result.add(ERASE_FLASH);

                break;
            case PROGRAM_FLASH:
//                System.out.println(" Program flash command:\n\r");
                try {
//                    controller.ProgramMemory(packet);
                    controller.Interpreter(packet);
                } catch (ControllerException e) {
                    System.out.println(e.getMessage());
                }
                try {
                    Thread.sleep(0);
                } catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                }
                result.add(PROGRAM_FLASH);

                break;
            case READ_CRC:
                System.out.println(" Read CRC command:\n\r");
                break;
            case JMP_TO_APP:
                System.out.println(" Jump to apps command:\n\r");
                break;
        }

        ProcessFrame processFrame = new ProcessFrame();
        processFrame.addPacketCrc(result);
        result = processFrame.MakePacket(result);
        curSerial.writeNextPacket(result);

    }

}
