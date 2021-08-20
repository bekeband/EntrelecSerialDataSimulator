import java.util.ArrayList;

public class ProcessFrame {

    final byte ESC = 16;
    final byte SOT = 01;
    final byte EOT = 04;

    private boolean rxFrameValid = false;

    public ArrayList<Byte> TranslatePacket(byte[] rawDatas) throws PacketException{

        rxFrameValid = false;
        boolean isWasEscape = false;
        ArrayList<Byte> result = new ArrayList<Byte>();

        for (int i = 0; ((i < rawDatas.length) & !rxFrameValid) ; i++) {
            /* ESC character*/
            switch (rawDatas[i]) {
                case SOT:
                    if (isWasEscape) {
                        result.add(SOT);
                        isWasEscape = false;
                    } else {
                        /* Start of header, clear the result array. */
                        result.clear();
                    }
                    break;
                case ESC:
                    if (isWasEscape) {
                        result.add(ESC);
                        isWasEscape = false;
                    } else {
                        isWasEscape = true;
                    }
                    break;
                case EOT:
                    if (isWasEscape) {
                        result.add(EOT);
                        isWasEscape = false;
                    } else {

                        if ((result.size() < 3)) {
                            throw new PacketException("No valid packet.");
                        } else {

                            CRCCalc crcCalc = new CRCCalc();
                            short crc = (short) ((result.get(result.size() - 2)) & 0x00ff);
                            crc = (short) (crc | ((result.get(result.size() - 1) << 8) & 0xFF00));
                            result.remove(result.size() - 1);
                            result.remove(result.size() - 1);
                            short calcCRC = crcCalc.getCRCValue(result);
                            if (calcCRC != crc) {
                                throw new PacketException("Invalid packet CRC.");
                            }

                            rxFrameValid = true;
                        };
                        break;
                    }
                    break;
                default:
                    result.add(rawDatas[i]);
                    isWasEscape = false;
                    break;
            }
        }
        return result;
    }

    public void addPacketCrc(ArrayList<Byte> packet) {
        CRCCalc crcCalc = new CRCCalc();
        short CRCValue = crcCalc.getCRCValue(packet);
        packet.add((byte) (CRCValue % 256));
        packet.add((byte) (CRCValue / 256));
    }

    public ArrayList<Byte> MakePacket(ArrayList<Byte> packet) {
        ArrayList<Byte> result = new ArrayList<>();
        result.add(SOT);
        for (int i = 0; i < packet.size(); i++) {
            switch (packet.get(i)) {
                case SOT:
                case ESC:
                case EOT:
                    result.add(ESC);
                default:
                    result.add(packet.get(i));
            }
        }
        result.add(EOT);
        return result;
    }

    public boolean isRxFrameValid() {
        return rxFrameValid;
    }
}
