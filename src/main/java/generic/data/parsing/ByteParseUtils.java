package generic.data.parsing;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class ByteParseUtils {
    private static byte[] extractBytes(byte[] b, int start, int end) {
        if (b.length < end) {
            throw new IllegalArgumentException("Byte array does not have enough bytes.");
        }

        byte[] extractedBytes = new byte[end - start];
        System.arraycopy(b, start, extractedBytes, 0, end - start);
        return extractedBytes;
    }

    public static float getFloat(byte[] b, int start, int end) {
        return ByteBuffer.wrap(extractBytes(b, start, end))
                .order(ByteOrder.LITTLE_ENDIAN)
                .getFloat();
    }

    public static long getLong(byte[] b, int start, int end) {
        return ByteBuffer.wrap(extractBytes(b, start, end))
                .order(ByteOrder.LITTLE_ENDIAN)
                .getLong();
    }

    public static int getInt(byte[] b, int start, int end) {
        return ByteBuffer.wrap(extractBytes(b, start, end))
                .order(ByteOrder.LITTLE_ENDIAN)
                .getInt();
    }

    public static double getDouble(byte[] b, int start, int end) {
        return ByteBuffer.wrap(extractBytes(b, start, end))
                .order(ByteOrder.LITTLE_ENDIAN)
                .getDouble();
    }
}
