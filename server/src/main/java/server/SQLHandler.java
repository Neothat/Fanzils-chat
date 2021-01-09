package server;

import java.sql.*;

public class SQLHandler {

    private static Connection connection;
    private static PreparedStatement psGetNickname;
    private static PreparedStatement psRegistration;
    private static PreparedStatement psChangeNickname;

    public static boolean connect(){

        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:main.db");
            prepareAllStatements();
            return true;
        } catch (SQLException e) {
            System.err.println("Ошибка во время взаимодействия с источником данных");
            return false;
        } catch (ClassNotFoundException e) {
            System.err.println("Не удалось подключиться к базе данных используя JDBC библиотеку");
            return false;
        }

    }

    private static void prepareAllStatements() throws SQLException {
        psGetNickname = connection.prepareStatement("SELECT nickname FROM users WHERE login = ? AND password = ?;");
        psRegistration = connection.prepareStatement("INSERT INTO users(login, password, nickname) VALUES (?, ?, ?);");
        psChangeNickname = connection.prepareStatement("UPDATE users SET nickname = ? WHERE nickname = ?;");
    }

    public static String getNicknameByLoginAndPassword(String login, String password) {
        String nick = null;
        try{
            psGetNickname.setString(1, login);
            psGetNickname.setString(2, password);
            ResultSet rs = psGetNickname.executeQuery();
            if (rs.next()){
                nick = rs.getString(1);
            }
            rs.close();
        } catch (SQLException e) {
            System.err.println("Ошибка во время взаимодействия с источником данных на этапе аутентификации");
        }
        return nick;
    }

    public static boolean registration(String login, String password, String nickname){
        try {
            psRegistration.setString(1,login);
            psRegistration.setString(2,password);
            psRegistration.setString(3,nickname);
            psRegistration.executeUpdate();
            return true;
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    public static boolean changeNick(String oldNickname, String newNickname) {
        try{
            psChangeNickname.setString(1, newNickname);
            psChangeNickname.setString(2, oldNickname);
            psChangeNickname.executeUpdate();
            return true;
        }catch (SQLException e){
            System.err.println("Ошибка во время взаимодействия с источником данных на этапе смены ника");
            return false;
        }
    }

    public static void disconnect() {
        try {
            psChangeNickname.close();
            psGetNickname.close();
            connection.close();
        } catch (SQLException e) {
            System.err.println("Ошибка во время взаимодействия с источником данных на этапе завершения");
        }

    }
}
