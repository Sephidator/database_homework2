package utils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class FileTool {

    private FileTool() {
    }

    /**
     * 读文件，返回的值是SQL语句集合（UTF8编码）
     * @param filePath
     * @return 文件内容
     */
    public static ArrayList<String> readFile(String filePath) {
        ArrayList<String> sqlList = new ArrayList<>();

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    new FileInputStream(filePath), "utf-8"));
            StringBuilder sql = new StringBuilder();
            String line;
            while (true){
                line = br.readLine();
                if (line == null) {
                    if (!sql.toString().equals("")) {
                        sqlList.add(sql.toString());
                    }
                    break;
                } else if (line.equals("") || line.startsWith("#")) {
                    if (!sql.toString().equals("")) {
                        sqlList.add(sql.toString());
                    }
                    sql = new StringBuilder();
                } else {
                    sql.append(line);
                }
            };
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sqlList;
    }
}
