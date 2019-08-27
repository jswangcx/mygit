package com.util;
 
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
 
/**
 * 根据数据库表结构生成相应的JavaBean
 *
 * 思路
 * 1、创建数据库连接
 * 2、获取数据库表、表注释
 * 3、获取数据库表中字段名、字段类型、字段注释
 * 4、构建StringBuffer缓存
 * 5、写入文件
 * 6、关闭连接、输入流等等
 *
 * @author ella_li
 *
 */
public class GenEntityOracle {
    private String packageOutPath = "D:/";// 指定实体生成所在包的路径
    private String tableName = "HOUSE_REGISTER";//数据库中的表名
    private String packageName = "com.ufgov.wcx.entity";//实体类所在的包
    //private String authorName = "wcx";// 作者名字
    private String[] colsPre = { "F_NB_", "F_VC_", "F_CR_", "F_DT_"};
    private String[] colnames; // 列名数组
    private String[] colTypes; // 列名类型数组
    private int[] colSizes; // 列名大小数组
    private boolean f_util = false; // 是否需要导入包java.util.*
    private boolean f_sql = false; // 是否需要导入包java.sql.*
 
    // 数据库连接
    private static final String URL = "jdbc:oracle:thin:@127.0.0.1:1521:orcl";
    private static final String NAME = "scott";
    private static final String PASS = "1";
    private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
    private Connection con = null;
    private Statement pStemt = null;
    private ResultSet rs = null;
    private ResultSetMetaData rsmd = null;
 
    public GenEntityOracle() {
        connect();
        getTableInfo(tableName);
        colseConnect();
    }
 
    /**
     * 1、创建数据库连接
     */
    private void connect() {
        try {
            Class.forName(DRIVER);
            con = DriverManager.getConnection(URL, NAME, PASS);
            pStemt = (Statement) con.createStatement();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
 
    private void colseConnect() {
        try {
            if (con != null) {
                con.close();
                con = null;
            }
            if (pStemt != null) {
                pStemt.close();
                pStemt = null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
 
    }
 
    /**
     * 获取单个数据库表信息
     */
    private void getTableInfo(String tableName) {
        int size = 0;
        String sql = "SELECT * FROM " + tableName;
        try {
            rs = pStemt.executeQuery(sql);
            rsmd = rs.getMetaData();
            size = rsmd.getColumnCount();
            colnames = new String[size];
            colTypes = new String[size];
            colSizes = new int[size];
            for (int i = 0; i < size; i++) {
                colnames[i] = rsmd.getColumnName(i + 1);
                colTypes[i] = rsmd.getColumnTypeName(i + 1);
 
                if (colTypes[i].equalsIgnoreCase("date")
                        || colTypes[i].equalsIgnoreCase("timestamp")) {
                    f_util = true;
                }
                if (colTypes[i].equalsIgnoreCase("blob")
                        || colTypes[i].equalsIgnoreCase("char")) {
                    f_sql = true;
                }
                colSizes[i] = rsmd.getColumnDisplaySize(i + 1);
            }
            //获取单张数据库表注释
            String tableComment = getTableComment(tableName);
            //获取单张数据库表的所有列信息
            StringBuffer tempSb = getColsInfo(tableName);
            //生成JavaBean文件
            genFile(tableName, getSb(tableName, tableComment, tempSb));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
 
    /**
     * 获取单张数据库表注释
     *
     * @param tableName
     * @return
     */
    private String getTableComment(String tableName) {
        String str = "";
        String sql = "select * from user_tab_comments where table_name = '"
                + tableName + "'";
        try {
            rs = pStemt.executeQuery(sql);
            while (rs.next()) {
                str = rs.getString("comments");
                if (null != str && str.indexOf("\r\n") != -1) {
                    str = str.replace("\r\n", "");
                }
                if (null != str && str.indexOf("\n") != -1) {
                    str = str.replace("\n", "");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return str;
    }
 
    /**
     * 获取单张数据库表的所有列信息
     *
     * @param tableName
     */
    private StringBuffer getColsInfo(String tableName) {
        StringBuffer temp = new StringBuffer();
        for (int i = 0; i < colnames.length; i++) {
            temp.append("\tprivate " + getColsType(colTypes[i]) + " "
                    + getColsName(colnames[i]) + ";   //"
                    + getColComment(tableName, colnames[i]) + "\r\n");
            String colname = getColsName(colnames[i]);
            String colnameUp = captureName(colname);
            temp.append("\n\tpublic void set" + colnameUp + "("
                    + getColsType(colTypes[i]) + " " + colname + "){\r\n");
            temp.append("\t\tthis." + colname + "=" + colname + ";\r\n");
            temp.append("\t}\r\n");
            temp.append("\n\tpublic " + getColsType(colTypes[i]) + " get"
                    + colnameUp + "(){\r\n");
            temp.append("\t\treturn " + colname + ";\r\n");
            temp.append("\t}\r\n");
        }
        return temp;
    }
 
    /**
     * 获取列类型
     *
     * @param sqlType
     * @return
     */
    private String getColsType(String sqlType) {
        if (sqlType.equalsIgnoreCase("binary_double")) {
            return "double";
        } else if (sqlType.equalsIgnoreCase("binary_float")) {
            return "float";
        } else if (sqlType.equalsIgnoreCase("blob")) {
            return "byte[]";
        } else if (sqlType.equalsIgnoreCase("blob")) {
            return "byte[]";
        } else if (sqlType.equalsIgnoreCase("char")
                || sqlType.equalsIgnoreCase("nvarchar2")
                || sqlType.equalsIgnoreCase("varchar2")) {
            return "String";
        } else if (sqlType.equalsIgnoreCase("date")
                || sqlType.equalsIgnoreCase("timestamp")
                || sqlType.equalsIgnoreCase("timestamp with local time zone")
                || sqlType.equalsIgnoreCase("timestamp with time zone")) {
            return "Date";
        } else if (sqlType.equalsIgnoreCase("number")) {
            return "Long";
        }
        return "String";
    }
 
    /**
     * 获取列名
     *
     * @param str
     * @return
     */
    private String getColsName(String str) {
        for (String temp : colsPre) {
            int preIndex = str.indexOf(temp);
            if (preIndex >= 0) {
                str = str.substring(preIndex + temp.length());
                str = str.replace("_", "").toLowerCase();
            }
        }
        return str;
    }
 
    /**
     * 获取列注释
     *
     * @param tableName
     * @param columnName
     * @return
     */
    private String getColComment(String tableName, String columnName) {
        String str = "";
        String sql = "select comments from USER_COL_COMMENTS where table_name= '"
                + tableName + "' and column_name= '" + columnName + "'";
        try {
            rs = pStemt.executeQuery(sql);
            while (rs.next()) {
                str = rs.getString("comments");
                if (null != str && str.indexOf("\r\n") != -1) {
                    str = str.replace("\r\n", "");
                }
                if (null != str && str.indexOf("\n") != -1) {
                    str = str.replace("\n", "");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return str;
    }
 
    /**
     * 构建StringBuffer缓存
     *
     * @param tableName
     * @param tableComment
     * @param colSb
     * @return
     */
    private StringBuffer getSb(String tableName, String tableComment,
            StringBuffer colSb) {
        StringBuffer sb = new StringBuffer();
        sb.append("package " + this.packageName + ";\r\n");
        // 判断是否导入工具包
        if (f_util) {
            sb.append("import java.util.Date;\r\n");
        }
        if (f_sql) {
            sb.append("import java.sql.*;\r\n");
        }
        sb.append("\r\n");
        // 注释部分
        //sb.append("   /**\r\n");
        //sb.append("    * " + tableName + " 实体类              " + tableComment + "\r\n");
        //sb.append("    * " + new Date() + "\r\n");
        //sb.append("    * @author " + this.authorName + "\r\n");
        //sb.append("    */ \r\n");
        // 实体部分
        sb.append("\r\n\r\npublic class " + tableName + "{\r\n");
        sb.append("\tprivate static final long serialVersionUID = 1L;\r\n");
        sb.append(colSb);
        sb.append("}\r\n");
        return sb;
    }
 
    /**
     * 生成JavaBean文件
     *
     * @param content
     */
    private void genFile(String tableName, StringBuffer content) {
        try {
            String outputPath = this.packageOutPath + tableName
                    + ".java";
            FileWriter fw = new FileWriter(outputPath);
            PrintWriter pw = new PrintWriter(fw);
            pw.println(content);
            pw.flush();
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
        /**
     * 首字母大写
     * @param name
     * @return
     */
    public static String captureName(String name) {
        char[] cs = name.toCharArray();
        //首字母是小写的需要大写
        if(cs[0] >= 97 && cs[0] <= 122) {
            cs[0] -= 32;
        }
        return String.valueOf(cs);
    }
 
    /**
     * 出口 TODO
     *
     * @param args
     */
    public static void main(String[] args) {
        new GenEntityOracle();   
    }
}