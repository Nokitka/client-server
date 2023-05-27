package server;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import common.data.Dragon;
import common.data.DragonCharacter;
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
        if (path == null) {
            return new DragonCollection();
        }

        CSVReader csvReader = new CSVReader(new FileReader(path));
        TreeSet<Dragon> dragons = new TreeSet<>(new CustomComparator());
        String[] line;

        try {

            while ((line = csvReader.readNext()) != null) {
                line = StringUtils.stripAll(line);
                Dragon dragon = new Dragon();
                dragon.setId(Long.parseLong(line[0]));
                dragon.setName(line[1]);
                dragon.getCoordinates().setX(Integer.parseInt(line[2]));
                dragon.getCoordinates().setY(Float.parseFloat(line[3]));
                dragon.setAge(Long.parseLong(line[4]));
                dragon.setDescription(line[5]);
                dragon.setSpeaking(Boolean.parseBoolean(line[6]));
                dragon.setCharacter(DragonCharacter.valueOf(line[7].trim().toUpperCase()));
                dragon.getHead().setEyesCount(Float.parseFloat(line[8]));
                dragon.getHead().setToothCount(Integer.parseInt(line[9]));
                dragons.add(dragon);
            }

        } catch (IOException | NumberFormatException | ArrayIndexOutOfBoundsException |
                 NullPointerException | CsvValidationException e) {
            System.out.println("Ошибка при парсе csv файла");
        }

        DragonCollection dragonCollection = new DragonCollection();
        dragonCollection.setDragons(dragons);

        return dragonCollection;
    }

}
