package network;

import java.io.*;

/**
 * Класс для сериализации и десериализации данных
 */
public class ObjectSerializer {

    public static byte[] serializeObject(Object o) throws IOException {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
             ObjectOutputStream out = new ObjectOutputStream(bos)) {
            out.writeObject(o);
            out.flush();
            out.close();
            return bos.toByteArray();
        }
    }

    public static Object deserializeObject(byte[] byteArray) throws IOException, ClassNotFoundException {
        try (ByteArrayInputStream bis = new ByteArrayInputStream(byteArray);
             ObjectInputStream in = new ObjectInputStream(bis)) {
            in.close();
            return in.readObject();
        }
    }


}
