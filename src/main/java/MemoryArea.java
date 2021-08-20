import java.util.ArrayList;
import java.util.List;

public class MemoryArea {

    ArrayList<Byte> byteArea;

    private int startPhysicalAddress;
    private int endPhysicalAddress;

    MemoryArea(int startPhysicalAddress, int endPhysicalAddress) {
        this.startPhysicalAddress = startPhysicalAddress;
        this.endPhysicalAddress = endPhysicalAddress;
        byteArea = new ArrayList<>();
        clearMemoryArea();
    }

    private void clearMemoryArea() {
        byteArea.clear();
        for (int i = 0; i < getMemorySize(); i++) {
            byteArea.add((byte) 0xFF);
        }
    }

    public int getStartPhysicalAddress() {
        return startPhysicalAddress;
    }

    public int getEndPhysicalAddress() {
        return endPhysicalAddress;
    }

    public boolean getInBounds(int pysicalAddress) {
        return ((pysicalAddress >= getStartPhysicalAddress()) & (pysicalAddress <= getEndPhysicalAddress()));
    }

    public byte getRelativeByte(int shiftVal) {
        return byteArea.get(shiftVal);
    }

    public byte getByte(int physicalAddress) throws ControllerException {
        if (getInBounds(physicalAddress)) {
            byteArea.get(getArrayShift(physicalAddress));
        } else {
            throw new ControllerException("Read Out of memory bound.");
        }
        return 0;
    }

    public void setByte(int physicalAddress, byte data) throws ControllerException {
        if (getInBounds(physicalAddress)) {
            byteArea.set(getArrayShift(physicalAddress), data);
        } else {
            throw new ControllerException("Write Out of memory bound.");
        }
    }

    public int getMemorySize() {
        return (getEndPhysicalAddress() - getStartPhysicalAddress() + 1);
    }

    public int getArrayShift(int address) {
        return (address - getStartPhysicalAddress());
    }

}
