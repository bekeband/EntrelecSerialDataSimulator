import java.util.List;

public class SimulatorUI {

    private final int oneLineBytes = 16;

    Controller controller;

    public SimulatorUI(Controller controller) {
        this.controller = controller;
    }

    private void printHexBytes(int startPos, int endPos, MemoryArea memoryArea, int baseAreaPosition) {
        for (int i = 0; i < oneLineBytes; i++) {
            if ((i >= startPos) & (i <= endPos)) {
                System.out.printf("%02X  ", memoryArea.getRelativeByte(baseAreaPosition++));
            } else {
                System.out.printf("    ");
            }
        }
    }

    public void printHexList(MemoryArea memoryArea, int startAddress, int endAddress) {
        if ((memoryArea.getInBounds(startAddress)) & memoryArea.getInBounds(endAddress)) {
            int startShift = memoryArea.getArrayShift(startAddress);
            int endShift = memoryArea.getArrayShift(endAddress);

            int startLine = startShift / oneLineBytes;
            int endLine = endShift / oneLineBytes;
            int lineNumb = endLine - startLine;
            int startPos = startShift % oneLineBytes;
            int endPos = endShift % oneLineBytes;

            int addressCounter = (startAddress / oneLineBytes) * oneLineBytes;
            String bytesLine = "";

            for (int i = 0; i < 16; i++) {
                bytesLine += String.format("%02X -", i);
            }
            System.out.print("           " + bytesLine);

            switch (lineNumb) {
                case 0:
                    System.out.println();
                    System.out.printf("%08X:  ", addressCounter);
                    printHexBytes(startPos, endPos, memoryArea, startShift);
                    break;
                case 1:
                    System.out.println();
                    System.out.printf("%08X:  ", addressCounter);
                    printHexBytes(startPos, oneLineBytes - 1, memoryArea, startShift);
                    startShift += oneLineBytes;
                    addressCounter += oneLineBytes;
                    System.out.println();
                    System.out.printf("%08X:  ", addressCounter);
                    printHexBytes(0, endPos, memoryArea, startShift);
                    break;
                default:
                    System.out.println();
                    System.out.printf("%08X:  ", addressCounter);
                    printHexBytes(startPos, oneLineBytes - 1, memoryArea, startShift);

                    for (int i = 0; i < (lineNumb - 1); i++) {
                        startShift += oneLineBytes;
                        addressCounter += oneLineBytes;
                        System.out.println();
                        System.out.printf("%08X:  ", addressCounter);
                        printHexBytes(0, oneLineBytes - 1, memoryArea, startShift);
                    }

                    startShift += oneLineBytes;
                    addressCounter += oneLineBytes;
                    System.out.println();
                    System.out.printf("%08X:  ", addressCounter);
                    printHexBytes(0, endPos, memoryArea, startShift);
                    break;
            }

/*            if (lineNumb == 1) {
            } else {

                System.out.println();
                System.out.printf("%08X:  ", addressCounter);
                printHexBytes(startPos, endPos, memoryArea, startShift);
                startShift += (endPos - startPos);
                if ((endLine - startLine) >= 3) {
                    for (int i = startLine; i < endLine; i++) {
                        System.out.println();
                        System.out.printf("%08X:  ", addressCounter);
                        printHexBytes(0, oneLineBytes - 1, memoryArea, startShift);
                        startShift += oneLineBytes;

                    }
                }
            }*/



//                System.out.printf("%02X  ", memoryArea.getRelativeByte(i));
/*                if ((i % oneLineBytes) == 0) {
                    System.out.println();
                    System.out.printf("%08X:  ", addressCounter);
                } else {
                    System.out.printf("%02X  ", memoryArea.getRelativeByte(i));
                }*/
            // Out of bound.
        }
    }

    public String getHexString(List<Byte> memoryList, int startAddress, int endAddress) {
        String result = "";
        for (int i = 0; i < memoryList.size() / 16; i++) {
            result += String.format("%04X : ", i * 16);
            for (int j = 0; j < 16; j++) {
                result += String.format("%02X,", memoryList.get((i * 16) + j));
            }
            result += "\n\r";
        }
        return result;

    }

 /*   public String printAppMem(int startAddress, int endAddress) {
        return getHexString(appMemory, startAddress, endAddress);
    }

    public String printBootMem(int startAddress, int endAddress) {
        return getHexString(bootMemory, startAddress, endAddress);
    }

    public String printConfigMem(int startAddress, int endAddress) {
        return getHexString(configMemory, startAddress, endAddress);
    }*/
}
