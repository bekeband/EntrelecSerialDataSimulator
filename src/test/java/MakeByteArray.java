import java.util.ArrayList;

public class MakeByteArray {

    ArrayList<Byte> MakeArray(byte[] buffer) {
        ArrayList<Byte> result = new ArrayList<Byte>();

        for (byte b: buffer) {
            result.add(b);
        }
        return result;
    }

}
