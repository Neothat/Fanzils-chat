package client;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class History {

    private static final int NUMBER_OF_LINES = 5;

    public static void start(String login){
        try {
            FileOutputStream out = new FileOutputStream("history/history_" + login + ".txt", true);
        } catch (IOException e) {
            System.err.println("Не удалось создать файл с историей переписки");
        }
    }

    public static void writeLine(String str, String login) {
        try {
            FileOutputStream out = new FileOutputStream("history/history_" + login + ".txt", true);
            out.write(str.getBytes());
            out.write("\r\n".getBytes());
        } catch (IOException e) {
            System.err.println("Не удалось записать историю переписки");
        }
    }

    public static String getLastLinesOfHistory(String login) {

        try {
            List<String> lines = Files.readAllLines(Paths.get("history/history_" + login + ".txt"));
            StringBuilder last5Lines = new StringBuilder();
            if (lines.size() < NUMBER_OF_LINES){
                for (String line : lines) {
                    last5Lines.append(line).append("\n\r");
                }
            } else {
                for (int i = 0; i < NUMBER_OF_LINES; i++) {
                    last5Lines.append(lines.get(lines.size() - NUMBER_OF_LINES + i)).append("\n\r");
                }
            }
            return String.valueOf(last5Lines);
        } catch (IOException e) {
            System.err.println("Не удалось получить N последних сообщений");
        }
        return "";
    }
}
