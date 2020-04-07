package lesson2;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;

public class WorkWithDatabase {
    private static Connection connection;
    private static Statement statement;
    private static final String URL = "src/main/java/lesson2/";

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
            String[][] idNameScore;

            ArrayList<String> updateData  = UpdateDataFromTxt(URL + "DZ_update.txt");
            int i = 0;
            idNameScore = splitArrayList(updateData, i);
            for (int j = 0; j <idNameScore.length ; j++) {
                //updateScoreByName("STUDENTS", Integer.parseInt(idNameScore[j][2]),idNameScore[j][1]);
                //Думаю так будет правильнее, вдруг есть тески
                int score = Integer.parseInt(idNameScore[j][2]);
                int id = Integer.parseInt(idNameScore[j][0]);
                String name = idNameScore[j][1];
                int resUpdate = updateScoreByID("STUDENTS", score, id,name);
                if (resUpdate<1)
                    insertSql("STUDENTS", new String[]{"NAME", name}, new String[]{"SCORE", String.valueOf(score)});

            }
            disconnect();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static String[][] splitArrayList(ArrayList<String> updateData, int i) {
        String[][] res;
        res = new String[countWords(updateData.get(0))-1][updateData.size()];
        for (String updateDatum : updateData) {
            String []temp = updateDatum.split("\\s+");
            res[i] = temp;
            i++;
        }
        return res;
    }

    static void connect() throws ClassNotFoundException, SQLException {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:" + URL + "studentBase.db");
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

    static int insertSql(String table,String[]... name_ValueColumn) {
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
            int res = statement.executeUpdate(query);
            info(query);
            return res;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static int updateScoreByName(String table, int score, String name){
        String query = String.format("UPDATE '%s' SET Score = '%d' WHERE Name ='%s';",table,score,name);
        try {
            int res = statement.executeUpdate(query);
                if (res>=1)
                    info(query);
                else
                    insertSql("STUDENTS", new String[]{"NAME", name}, new String[]{"SCORE", String.valueOf(score)});
            return res;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static int updateScoreByID(String table, int score, int id,String name){
        String query = String.format("UPDATE '%s' SET Score = '%d' WHERE StudID = '%d' AND Name ='%s';",table,score,id,name);
        try {
            int res = statement.executeUpdate(query);
            info(query);
            return res;
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

    private static int createTable(String table, String nameColumn1, String nameColumn2){
        String query = String.format("CREATE TABLE IF NOT EXISTS '%s' (" +
                "StudID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "'%s' TEXT NOT NULL," +
                "'%s' INTEGER NOT NULL);",table,nameColumn1,nameColumn2);
        try {
            int res = statement.executeUpdate(query);
            info(query);
            return res;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static ArrayList<String> UpdateDataFromTxt(String url){
        ArrayList<String> res = new ArrayList<>();
        try {
            File file = new File(url);
            FileReader fr = new FileReader(file);
            BufferedReader reader = new BufferedReader(fr);
            String line = reader.readLine();
            while (line != null) {
                if (isDigit(line.charAt(0)))
                    res.add(line);
                line = reader.readLine();
            }
            return res;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean isDigit(char chr){
        return Character.isDigit(chr);
    }

    public static int countWords(String txt){
        int count = 0;
        if(txt.length() != 0){
            count++;
            for (int i = 0; i < txt.length(); i++) {
                if(txt.charAt(i) == ' '){
                    count++;
                }
            }
        }
        return count;
    }
}
