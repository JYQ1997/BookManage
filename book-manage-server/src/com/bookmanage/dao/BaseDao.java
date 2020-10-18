package com.bookmanage.dao;

import com.bookmanage.util.DBUtil;
import org.apache.log4j.Logger;

import java.io.Serializable;
import java.lang.reflect.*;
import java.math.BigDecimal;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author YongQiang
 * @Date 2020/10/13 15:54
 * @Version 1.0
 */
public class BaseDao<E> implements Serializable {

    private static final Logger log = Logger.getLogger(BaseDao.class);
    private static final long serialVersionUID = -8810624818010176567L;

    private static final Pattern linePattern = Pattern.compile("_(\\w)");
    private static final SimpleDateFormat formattime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private Class<?> cls;

    public BaseDao() {
        //得到父类的泛型
        Type sType = getClass().getGenericSuperclass();
        //得到实际的类型参数数组
        Type[] generics = ((ParameterizedType) sType).getActualTypeArguments();
        //得到第一个泛型的Class
        cls = (Class<?>) (generics[0]);
    }

    /**
     * 单表多条查询，将查询到的多条记录传入一个对象，然后再将这些存入一个集合中，返回这个集合
     *
     * @param sql    传入对应的sql查询语句
     * @param object 传入对应的占位符的值
     * @return 返回查询到的记录转化成的对象的集合
     */
    public List<E> list(String sql, List<?> object) {
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        List<E> list = new ArrayList<>();

        try {
            conn = DBUtil.getConnection();
            st = conn.prepareStatement(sql);
            //setPstm(st, object);
            fillPstm(st,object);
            log.info(st.toString());
            rs = st.executeQuery();
            while (rs.next()) {
                //将获取到的结果集存入一个对象中，这个我们也单独写一个函数来实现
                E obj = oneRowToObject(rs);
                //然后将对象存入一个集合中返回
                list.add(obj);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, st, rs);
        }
        log.info("查询结果为:" + list.toString());
        return list;
    }

    public Integer count(String sql, List<?> object){
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        List<E> list = new ArrayList<>();
        Integer total=0;
        try {
            conn = DBUtil.getConnection();
            st = conn.prepareStatement(sql);
            //setPstm(st, object);
            fillPstm(st,object);
            log.info(st.toString());
            rs = st.executeQuery();
            if (rs.next()) {
                total=rs.getInt(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, st, rs);
        }
        log.info("总记录数为:" + total);
        return total;
    }

    /**
     * 修改
     * @param sql
     * @param object
     * @return
     */
    public int updateByDto(String sql, List<?> object) {
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        int rows = 0;

        try {
            conn = DBUtil.getConnection();
            st = conn.prepareStatement(sql);
            fillPstm(st,object);
            log.info(st.toString());
            rows = st.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, st, rs);
        }
        log.info("修改结束，影响行数:" + rows);
        return rows;
    }

    public int insert(String sql, List<?> object){
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        int rows = 0;

        try {
            conn = DBUtil.getConnection();
            st = conn.prepareStatement(sql);
            fillPstm(st,object);
            log.info(st.toString());
            rows = st.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, st, rs);
        }
        log.info("添加结束，影响行数:" + rows);
        return rows;
    }

    /* 把得到的一列数据存入到一个对象中
     * @param rs
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws SQLException
     * @throws NoSuchMethodException
     * @throws SecurityException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     */
    private E oneRowToObject(ResultSet rs) throws InstantiationException, IllegalAccessException, SQLException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        E obj;
        obj = (E) cls.newInstance();
        //获取结果集元数据(获取此 ResultSet 对象的列的编号、类型和属性。)
        ResultSetMetaData rd = rs.getMetaData();
        for (int i = 0; i < rd.getColumnCount(); i++) {
            //获取列名
            String columnName = rd.getColumnLabel(i + 1);
            //组合方法名
            String methodName = "set" + columnName.substring(0, 1).toUpperCase() + lineToHump(columnName.substring(1));
            //获取列类型
            int columnType = rd.getColumnType(i + 1);
            Method method = null;
            switch (columnType) {
                case java.sql.Types.VARCHAR:
                case java.sql.Types.CHAR:
                    try {
                        method = cls.getMethod(methodName, String.class);
                    } catch (Exception e) {
                        break;
                    }
                    if (method != null) {
                        method.invoke(obj, rs.getString(columnName));
                    }
                    break;
                case java.sql.Types.INTEGER:
                case java.sql.Types.SMALLINT:

                    try {
                        method = cls.getMethod(methodName, Integer.class);
                    } catch (Exception e) {
                        break;
                    }
                    if (method != null) {
                        method.invoke(obj, rs.getInt(columnName));
                    }
                    break;
                case java.sql.Types.BIGINT:
                    try {

                        method = cls.getMethod(methodName, Long.class);
                    } catch (Exception e) {
                        break;
                    }
                    if (method != null) {
                        method.invoke(obj, rs.getLong(columnName));
                    }
                    break;
                case java.sql.Types.DATE:
                case java.sql.Types.TIMESTAMP:
                    try {
                        method = cls.getMethod(methodName, java.util.Date.class);
                        if (method != null) {
                            method.invoke(obj, rs.getTimestamp(columnName));
                        }
                    } catch (Exception e) {
                        try {
                            method = cls.getMethod(methodName, String.class);
                        } catch (Exception ex) {
                            break;
                        }
                        if (method != null) {
                            method.invoke(obj, rs.getString(columnName));
                        }
                    }
                    break;
                case java.sql.Types.DECIMAL:
                    try {
                        method = cls.getMethod(methodName, BigDecimal.class);
                    } catch (Exception e) {
                        break;
                    }
                    if (method != null) {
                        method.invoke(obj, rs.getBigDecimal(columnName));
                    }
                    break;
                case java.sql.Types.DOUBLE:
                case java.sql.Types.NUMERIC:
                    try {
                        method = cls.getMethod(methodName, Double.class);
                    } catch (Exception ex) {
                        break;
                    }
                    if (method != null) {
                        method.invoke(obj, rs.getDouble(columnName));
                    }
                    break;
                case java.sql.Types.BIT:
                    try {
                        method = cls.getMethod(methodName, boolean.class);
                    } catch (Exception ex) {
                        break;
                    }
                    if (method != null) {
                        method.invoke(obj, rs.getBoolean(columnName));
                    }
                    break;
                default:
                    break;
            }
        }
        return obj;
    }

    private static PreparedStatement fillPstm(PreparedStatement preparedStatement, List<?> object) throws InvocationTargetException, IllegalAccessException, SQLException {
        int i=0;
        AtomicInteger atomicInteger = new AtomicInteger();
        for (Object o : object) {
            preparedStatement.setObject(++i,o);
        }
        return preparedStatement;
    }


    /**
     * 下划线转驼峰
     */
    public static String lineToHump(String str) {
        str = str.toLowerCase();
        Matcher matcher = linePattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

}
