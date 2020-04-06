package lesson2;

import java.sql.*;

public class WorkWithDatabase {
    private static Connection connection;
    private static Statement statement;

    public static void main(String[] args) {
        try {
            connect();
            createTable("STUDENTS","NAME", "SCORE");
            connection.setAutoCommit(false);
            for (int i = 1; i <11 ; i++) {
                String[][] intoValue = {{"NAME", "Bob"+i},{"SCORE", String.valueOf(10 * i)}};
                insertSql("STUDENTS", intoValue[0] ,intoValue[1]);
            }
            connection.setAutoCommit(true);
            deleteColumnByName("STUDENTS","Bob4");
            updateScoreByName("STUDENTS",100,"Bob5");
            selectSql("STUDENTS","StudID","NAME","SCORE");
            disconnect();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static void connect() throws ClassNotFoundException, SQLException {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:src/main/java/lesson2/studentBase.db");
            statement = connection.createStatement();
    }

    static void disconnect() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    static String selectSql(String table, String... selectColumn) {
        String selectColumns = String.join(",", selectColumn);
        String query = String.format("SELECT %s FROM %s;",selectColumns,table);
        try (ResultSet set = statement.executeQuery(query)) {
            if (set.next()) {
                info(query);
                return set.getString(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    static void insertSql(String table,String[]... name_ValueColumn) {
        String nameColumns = "";
        String valueColumns = "";
        for (String[] strings : name_ValueColumn) {
            nameColumns = nameColumns + "," +  strings[0];
            valueColumns = valueColumns + "','" + strings[1];
        }
        nameColumns = nameColumns.substring(1);
        valueColumns = valueColumns.substring(3);
        String query = String.format("INSERT INTO %s (%s) VALUES ('%s');",table,nameColumns,valueColumns);
        try {
            statement.execute(query);
            info(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void updateScoreByName(String table, int score, String name){
        String query = String.format("UPDATE '%s' SET Score = '%d' WHERE Name ='%s';",table,score,name);
        try {
            statement.execute(query);
            info(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void deleteColumnByName(String table, String name){
        String query = String.format("DELETE FROM '%s' WHERE Name = '%s';)",table,name);
        try {
            statement.execute(query);
            info(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void info(String query) {
        System.out.println("Выполнен запрос " + query);
    }

    private static void createTable(String table, String nameColumn1, String nameColumn2){
        String query = String.format("CREATE TABLE IF NOT EXISTS '%s' (" +
                "StudID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "'%s' TEXT NOT NULL," +
                "'%s' INTEGER NOT NULL);",table,nameColumn1,nameColumn2);
        try {
            statement.execute(query);
            info(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
//1. Сделать методы для работы с БД (CREATE, DELETE)
