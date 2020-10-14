package com.bookmanage.dao;

import com.bookmanage.util.DBUtil;

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

    private static final long serialVersionUID = -8810624818010176567L;

    private static final Pattern linePattern = Pattern.compile("_(\\w)");
    private static final SimpleDateFormat formattime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private Class<?> cls;
    public BaseDao() {
        //得到父类的泛型
        Type sType=getClass().getGenericSuperclass();
        //得到实际的类型参数数组
        Type[] generics=((ParameterizedType) sType).getActualTypeArguments();
        //得到第一个泛型的Class
        cls=(Class<?>) (generics[0]);
    }


    /**
     * 设置占位符
     * @param st 预处理
     * @param parameters 占位符数组
     * @return 返回存储占位符对应的对象的数组
     */
    private void setParameters(PreparedStatement st, Object[] parameters) {
        //判断是否有结果集，结果集中是否有记录
        if(parameters!=null&&parameters.length>0) {
            for(int i=0;i<parameters.length;i++) {
                try {
                    st.setObject(i+1,parameters[i] );
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * 单表多条查询，将查询到的多条记录传入一个对象，然后再将这些存入一个集合中，返回这个集合
     * @param sql 传入对应的sql查询语句
     * @param object 传入对应的占位符的值
     * @return 返回查询到的记录转化成的对象的集合
     */
    //Object...parameters是sql语句中对应的占位符的值，是一个不定长可变参数，我们需要写一个函数来获取他
    public List<E> list(String sql, Object object) {
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        List<E> list = new ArrayList<>();

        try {
            conn = DBUtil.getConnection();
            st = conn.prepareStatement(sql);
            setPstm(st,object);
            System.out.println(st.toString());
            rs = st.executeQuery();
            while(rs.next()) {
                //将获取到的结果集存入一个对象中，这个我们也单独写一个函数来实现
                E obj = oneRowToObject(rs);
                //然后将对象存入一个集合中返回
                list.add(obj);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn,st,rs);
        }
        return list;
    }

    public int updateByDto(String sql, Object object){
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        int rows = 0;

        try {
            conn = DBUtil.getConnection();
            st = conn.prepareStatement(sql);
            setPstm(st,object);
            System.out.println(st.toString());
            rows = st.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn,st,rs);
        }
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
        obj=(E) cls.newInstance();
        //获取结果集元数据(获取此 ResultSet 对象的列的编号、类型和属性。)
        ResultSetMetaData rd=rs.getMetaData();
        for (int i = 0; i < rd.getColumnCount(); i++) {
            //获取列名
            String columnName=rd.getColumnLabel(i+1);
            //组合方法名
            String methodName="set"+columnName.substring(0, 1).toUpperCase()+lineToHump(columnName.substring(1));
            //获取列类型
            int columnType=rd.getColumnType(i+1);
            Method method=null;
            switch(columnType) {
                case java.sql.Types.VARCHAR:
                case java.sql.Types.CHAR:
                    try{
                        method=cls.getMethod(methodName, String.class);
                    }
                    catch (Exception e){
                        break;
                    }
                    if(method!=null) {
                        method.invoke(obj, rs.getString(columnName));
                    }
                    break;
                case java.sql.Types.INTEGER:
                case java.sql.Types.SMALLINT:

                    try {
                        method = cls.getMethod(methodName, Integer.class);
                    }
                    catch (Exception e){
                        break;
                    }
                    if(method!=null) {
                        method.invoke(obj, rs.getInt(columnName));
                    }
                    break;
                case java.sql.Types.BIGINT:
                    try {

                        method=cls.getMethod(methodName, Long.class);
                    }catch (Exception e){
                        break;
                    }
                    if(method!=null) {
                        method.invoke(obj, rs.getLong(columnName));
                    }
                    break;
                case java.sql.Types.DATE:
                case java.sql.Types.TIMESTAMP:
                    try {
                        method=cls.getMethod(methodName, java.util.Date.class);
                        if(method!=null) {
                            method.invoke(obj, rs.getTimestamp(columnName));
                        }
                    } catch(Exception e) {
                        try {
                            method=cls.getMethod(methodName, String.class);
                        }catch (Exception ex){
                            break;
                        }
                        if(method!=null) {
                            method.invoke(obj, rs.getString(columnName));
                        }
                    }
                    break;
                case java.sql.Types.DECIMAL:
                    try {
                        method=cls.getMethod(methodName, BigDecimal.class);
                    }catch (Exception e){
                        break;
                    }
                    if(method!=null) {
                        method.invoke(obj, rs.getBigDecimal(columnName));
                    }
                    break;
                case java.sql.Types.DOUBLE:
                case java.sql.Types.NUMERIC:
                    try {
                        method=cls.getMethod(methodName, Double.class);
                    }catch (Exception ex){
                        break;
                    }
                    if(method!=null) {
                        method.invoke(obj, rs.getDouble(columnName));
                    }
                    break;
                case java.sql.Types.BIT:
                    try {
                        method=cls.getMethod(methodName, boolean.class);
                    }catch (Exception ex){
                        break;
                    }
                    if(method!=null) {
                        method.invoke(obj, rs.getBoolean(columnName));
                    }
                    break;
                default:
                    break;
            }
        }
        return obj;
    }


    private static PreparedStatement setPstm(PreparedStatement preparedStatement, Object object) throws InvocationTargetException, IllegalAccessException, SQLException {
        AtomicInteger atomicInteger=new AtomicInteger();
        Field[] fields = object.getClass().getDeclaredFields();
        for (Field field : fields) {
            Object value = null;
            try {
                value = getGetMethod(object, field.getName());
            } catch (Exception e) {
                e.printStackTrace();
            }

            if(value != null ){

                int i = atomicInteger.incrementAndGet();
                // 如果类型是String
                if (field.getGenericType().toString().equals(
                        "class java.lang.String")) { // 如果type是类类型，则前面包含"class "，后面跟类名
// 拿到该属性的gettet方法

                    String val = (String) value;// 调用getter方法获取属性值
                    if (val != null) {
                        System.out.println("String type:" + val);
                        preparedStatement.setString(i,val);
                    }
                }
// 如果类型是Integer
                if (field.getGenericType().toString().equals(
                        "class java.lang.Integer")) {
                    Integer val = (Integer) value;
                    if (val != null) {
                        System.out.println("Integer type:" + val);
                        preparedStatement.setInt(i,val);
                    }
                }
// 如果类型是Double
                if (field.getGenericType().toString().equals(
                        "class java.lang.Double")) {
                    Double val = (Double) value;
                    if (val != null) {
                        System.out.println("Double type:" + val);
                        preparedStatement.setDouble(i,val);
                    }
                }
// 如果类型是Boolean 是封装类
                if (field.getGenericType().toString().equals(
                        "class java.lang.Boolean")) {
                    Boolean val = (Boolean) value;
                    if (val != null) {
                        System.out.println("Boolean type:" + val);
                        preparedStatement.setBoolean(i,val);
                    }
                }
// 如果类型是boolean 基本数据类型不一样 这里有点说名如果定义名是 isXXX的 那就全都是isXXX的
// 反射找不到getter的具体名
                if (field.getGenericType().toString().equals("boolean")) {
                    Boolean val = (Boolean) value;
                    if (val != null) {
                        System.out.println("boolean type:" + val);
                        preparedStatement.setBoolean(i,val);
                    }
                }
// 如果类型是Date
                if (field.getGenericType().toString().equals(
                        "class java.util.Date")) {
                    Date val = (Date) value;
                    if (val != null) {
                        System.out.println("Date type:" + val);
                        preparedStatement.setDate(i,val);
                    }
                }
// 如果类型是Short
                if (field.getGenericType().toString().equals(
                        "class java.lang.Short")) {
                    Short val = (Short) value;
                    if (val != null) {
                        System.out.println("Short type:" + val);
                        preparedStatement.setShort(i,val);
                    }
                }
            }
        }
        return preparedStatement;
    }


    /**
     * 根据属性，获取get方法
     * @param ob 对象
     * @param name 属性名
     * @return
     * @throws Exception
     */
    public static Object getGetMethod(Object ob , String name)throws Exception {
        Method[] m = ob.getClass().getMethods();
        for (int i = 0; i < m.length; i++) {
            if (("get" + name).toLowerCase().equals(m[i].getName().toLowerCase())) {
                return m[i].invoke(ob);
            }
            else if (("is" + name).toLowerCase().equals(m[i].getName().toLowerCase())) {
                return m[i].invoke(ob);
            }
        }
        return null;
    }

    /** 下划线转驼峰 */
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
