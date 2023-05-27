package server;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import common.data.Coordinates;
import common.data.Dragon;
import common.data.DragonCharacter;
import common.data.DragonHead;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.TreeSet;
import java.util.UUID;

public class Parser {

    public Parser() {
    }

    public static void convertToCSV(DragonCollection dragons, String filename) throws IOException {

        String generateUUIDNo = String.
                format("%010d", new BigInteger(UUID
                        .randomUUID()
                        .toString()
                        .replace("-", ""), 16));

        File file = new File(filename);
        String filePath = generateUUIDNo + ".csv";

        if ((file.exists()
                && !file.isDirectory())
                || (!file.exists())) {

            file.mkdir();

        }

        Files.createFile(Path.of(filePath));
        FileOutputStream fileOutputStream = new FileOutputStream(filePath, true);

        for (Dragon dragon : dragons.getDragons()) {
            fileOutputStream.write(dragon.toCSV().getBytes());
        }

    }

    public static DragonCollection convertToDragons(File file) throws FileNotFoundException {

        //String path = "C:\\Users\\mad_duck\\Documents\\GitHub\\lab5\\test.csv";
        String path = file.getPath();
        if (path.equals("")) {
            return new DragonCollection();
        }

        CSVReader csvReader = new CSVReader(new FileReader(path));
        TreeSet<Dragon> dragons = new TreeSet<>(new CustomComparator());
        String[] line;

        try {

            while ((line = csvReader.readNext()) != null) {
                line = StringUtils.stripAll(line);
                Dragon dragon = new Dragon(
                        line[0],
                        new Coordinates(Integer.parseInt(line[1]),
                                Float.parseFloat(line[2])),
                        Long.parseLong(line[3]),
                        line[4],
                        Boolean.parseBoolean(line[5]),
                        Enum.valueOf(DragonCharacter.class, String.valueOf(Integer.parseInt(line[6]))),
                        new DragonHead(Float.parseFloat(line[7]),
                                Integer.parseInt(line[8]))
                );
                dragons.add(dragon);
            }

        } catch (IOException | NumberFormatException | ArrayIndexOutOfBoundsException |
                 NullPointerException | CsvValidationException e) {
            System.out.println("Ошибка при парсе csv файла");
        }

        return new DragonCollection(dragons);
    }

}
