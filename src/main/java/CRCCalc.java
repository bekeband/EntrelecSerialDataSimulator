import java.util.ArrayList;

public class CRCCalc
{
    private short CrcValue;
    private boolean CRCOK;

    static final short[] CRCTABLE = {(short) 0x0000, (short) 0x1021, (short) 0x2042, (short) 0x3063, (short) 0x4084,
            (short) 0x50a5, (short) 0x60c6, (short) 0x70e7, (short) 0x8108, (short) 0x9129, (short) 0xa14a,
            (short) 0xb16b, (short) 0xc18c, (short) 0xd1ad, (short) 0xe1ce, (short) 0xf1ef};

    public byte getCheksumBytes(ArrayList<Byte> data) {
        byte result = 0;
        for (Byte b: data) {
            result += b;
        }
        return result;
    }

    public short getCRCValue(ArrayList<Byte> data) {
        int ival, k, j;
        short crc = 0;
        for (int i = 0; i < data.size(); i++) {
            ival = (crc >> 12) ^ (data.get(i) >> 4);
            crc = (short) (CRCTABLE[ival & 0x0F] ^ (crc << 4));
            ival = (crc >> 12) ^ (data.get(i) >> 0);
            crc = (short) (CRCTABLE[ival & 0x0F] ^ (crc << 4));
        }
        return (short) (crc & 0xFFFF);
    }

    public void APP_CalculateCrc(ArrayList<Byte> data) throws PacketException {
        short CrcInArray = 0;
        short CRC_lo, CRC_hi;

        if (data.size() < 3) {
            throw new PacketException("Too small packet.");
        }

        CRC_lo = (data.get(data.size() - 2));
        CRC_hi = (short) (data.get(data.size() - 1) << 8);
        CrcInArray = (short) ((CRC_hi & 0xFF00) | (CRC_lo & 0xFF));
        data.remove(data.size() - 1);
        data.remove(data.size() - 1);
        CrcValue = getCRCValue(data);
        System.out.printf("CHI = %02X, %02X, ", CRC_hi, CRC_lo);
        System.out.printf("CRC = %04X ", CrcValue);
        System.out.printf("SIZE = %04d ", data.size());
        if (CrcInArray != CrcValue) {
            throw new PacketException("CRC Error.");
        }

    }

    public short getCrcValue() {
        return CrcValue;
    }

    public boolean isCRCOK() {
        return CRCOK;
    }
}

