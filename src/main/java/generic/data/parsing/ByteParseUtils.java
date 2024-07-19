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

    public static float getFloat32(byte[] b, int start) {
        return ByteBuffer.wrap(extractBytes(b, start, start + 4))
                .order(ByteOrder.LITTLE_ENDIAN)
                .getFloat();
    }

    public static long getLong64(byte[] b, int start) {
        return ByteBuffer.wrap(extractBytes(b, start, start + 8))
                .order(ByteOrder.LITTLE_ENDIAN)
                .getLong();
    }

    public static byte getByte8(byte[] b, int start) {
        return ByteBuffer.wrap(extractBytes(b, start, start + 1))
                .order(ByteOrder.LITTLE_ENDIAN)
                .get();
    }

    public static int getShort16(byte[] b, int start) {
        return ByteBuffer.wrap(extractBytes(b, start, start + 2))
                .order(ByteOrder.LITTLE_ENDIAN)
                .getShort();
    }

    public static int getInt32(byte[] b, int start) {
        return ByteBuffer.wrap(extractBytes(b, start, start + 4))
                .order(ByteOrder.LITTLE_ENDIAN)
                .getInt();
    }

    public static double getDouble64(byte[] b, int start) {
        return ByteBuffer.wrap(extractBytes(b, start, start + 8))
                .order(ByteOrder.LITTLE_ENDIAN)
                .getDouble();
    }

    public static char getChar16(byte[] b, int start) {
        return ByteBuffer.wrap(extractBytes(b, start, start + 2))
                .order(ByteOrder.LITTLE_ENDIAN)
                .getChar();
    }

    public static boolean getBoolean(byte[] b, int start) {
        return ByteBuffer.wrap(extractBytes(b, start, start + 1))
                .order(ByteOrder.LITTLE_ENDIAN)
                .get() != 0;
    }

    public static String getString(byte[] b, int start, int charCount) {
        return new String(extractBytes(b, start, start + charCount));
    }
}
