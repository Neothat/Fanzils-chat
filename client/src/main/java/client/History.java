package client;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class History {

    private static final int NUMBER_OF_LINES = 5;
    private static PrintWriter out;

    private static String getHistoryFilenameByLogin(String login){
        return "history/history_" + login + ".txt";
    }

    public static void start(String login){
        try {
            out = new PrintWriter(new FileOutputStream(getHistoryFilenameByLogin(login)), true);
        } catch (IOException e) {
            System.err.println("Не удалось создать файл с историей переписки");
        }
    }

    public static void stop(){
        if (out != null){
            out.close();
        }
    }

    public static void writeLine(String str) {
        out.println(str);
    }

    public static String getLastLinesOfHistory(String login) {

        if (!Files.exists(Paths.get(getHistoryFilenameByLogin(login)))){
            return "";
        }

        StringBuilder lastLines = new StringBuilder();

        try {
            List<String> lines = Files.readAllLines(Paths.get(getHistoryFilenameByLogin(login)));
            int startPosition = 0;
            if (lines.size() > NUMBER_OF_LINES){
                startPosition = lines.size() - NUMBER_OF_LINES;
            }
            for (int i = startPosition; i < lines.size(); i++) {
                lastLines.append(lines.get(i)).append(System.lineSeparator());
            }
        } catch (IOException e) {
            System.err.println("Не удалось получить N последних сообщений");
        }
        return lastLines.toString();
    }
}
