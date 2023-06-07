package utils;

import managers.ScannerManager;

import java.util.Scanner;

/**
 * Класс для стандартного ввода через консоль
 */
public class ConsoleInput implements UserInput{
    private static final Scanner userScanner = ScannerManager.getScanner();

    @Override
    public String nextLine() {
        return userScanner.nextLine();
    }
}