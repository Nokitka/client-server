package managers;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import utils.CustomComparator;
import data.*;
import exceptions.ExitObligedException;
import org.apache.commons.lang3.StringUtils;
import utils.Printable;

import java.io.*;
import java.util.TreeSet;

import static utilty.ConsoleColors.*;

public class Parser {

    private Printable console;
    private final String dirPath = "result";

    public Parser(Printable console) {
        this.console = console;
    }

    public String convertToCSV(CollectionManager dragons) throws IOException {

        File theDir = new File(dirPath);
        if (!theDir.exists()){
            theDir.mkdirs();
        }

        Long generateUUIDNo = GenerationId.generatorId();
        String filePath = dirPath + generateUUIDNo + ".csv";
        File file = new File(filePath);

        /*if ((file.exists()
                && !file.isDirectory())
                || (!file.exists())) {

            file.mkdir();

        }*/

        FileOutputStream fileOutputStream = new FileOutputStream(file, true);

        for (Dragon dragon : dragons.getDragons()) {
            fileOutputStream.write(dragon.toCSV().getBytes());
        }

        return file.getAbsolutePath();

    }

    public CollectionManager convertToDragons() throws FileNotFoundException, ExitObligedException {

        //String path = "C:\\Users\\mad_duck\\Documents\\GitHub\\lab5\\test.csv";
        File file = this.findFile();
        String path = file.getPath();
        if (path.equals("")) {
            return new CollectionManager();
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

        return new CollectionManager(dragons);
    }

    public File findFile() throws ExitObligedException {
        String file_path = System.getenv("file_path");
        if (file_path == null || file_path.isEmpty()) {
            console.printError("Путь должен быть в переменных окружения в переменной 'file_path'");
            throw new ExitObligedException();
        }
        else console.println(toColor("Путь получен успешно", PURPLE));

        File file = new File(file_path);

        return file;
    }

}
