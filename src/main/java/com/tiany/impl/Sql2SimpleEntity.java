package com.tiany.impl;

import com.tiany.inf.Convert;
import com.tiany.util.*;
import com.tiany.util.format.FormatUtil;

import java.io.IOException;
import java.util.*;

/**
 * 通过CREATE TABLE SQL 语句生成实体类及sqlMapper.xml
 * @author tiany
 * @version 1.0
 */
public class Sql2SimpleEntity implements Convert {

    // 需要去除的前缀
    private final String removePrefix ="xq_,pmis_";
    // 实体类的存放的包路径
    private String entityPackageName = "com.csii.pmis.service.bean.model";

    // dao类的存放的包路径
    private String daoPackageName = "com.csii.pmis.admin.dao";


    private Properties properties;

    private List<List<String>> data;

    private Table table = new Table();



    @Override
    public Object convert(Object in) {
        try {
            properties = new Properties();
            properties.load(this.getClass().getClassLoader().getResourceAsStream("type.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (in instanceof String) {
            String inStr = (String) in;
            inStr = inStr.trim();
            String outStr = "";
            // 转换成一行
            inStr = inStr.replaceAll("\r\n", " ");
            initData(inStr);
            parseData(data);
            Map<String, Object> map = new HashMap<>();
            outStr = generateEntity(table);
            map.put("entity",outStr);
            outStr = generateXml(table);
            map.put("xml",outStr);
            outStr = generateDao(table);
            map.put("dao",outStr);
            outStr = generateDaoImpl(table);
            map.put("daoImpl",outStr);

            return map;
        }
        return null;
    }

    public String generateXml(Table table){
        String ret = "";
        ret += "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\r\n";
        ret += "<!DOCTYPE sqlMap PUBLIC \"-//ibatis.apache.org//DTD SQL Map 2.0//EN\" \"http://ibatis.apache.org/dtd/sql-map-2.dtd\" >\r\n";
        ret += "<sqlMap namespace=\""+table.getEntityName()+"\" >\r\n";
        ret += FormatUtil.addTab(generateResultMap(table),1);
        ret += FormatUtil.addTab(generateSelect(table),1);
        ret += FormatUtil.addTab(generateDelete(table),1);
        ret += FormatUtil.addTab(generateUpdate(table),1);
        ret += FormatUtil.addTab(generateInsert(table),1);
        ret += "</sqlMap>\r\n";
        return ret;
    }
    /**
     * 生成entity类
     * @param table
     * @return
     */
    private String generateEntity(Table table) {
        String ret = "";
        ret += "package "+ entityPackageName +";\r\n\r\n";
        ret += "import java.io.Serializable;\r\n";
        ret += "import java.util.Date;\r\n";
        ret += "import java.math.BigDecimal;\r\n\r\n";
        ret += "/*\r\n";
        ret += " * @description "+getCommentString(table.getComment())+"\r\n";
        ret += " * @author "+ System.getProperty("user.name")+"\r\n";
        ret += " * @version "+ DateUtil.thisDate()+" modify: "+System.getProperty("user.name")+"\r\n";
        ret += " * @since 1.0\r\n";
        ret += " */\r\n";
        ret += "public class " + table.getEntityName() + " implements Serializable{\r\n";
        ret += "\t/** 序列化号 */\r\n";
        ret += "\tprivate static final long serialVersionUID = 1L;\r\n";
        List<Field> fields = table.getFields();
        for(int i =0;i<fields.size();i++){
            ret += "\t/** "+getCommentString(fields.get(i).getComment())+" */\r\n";
            // TODO TIANYAO
            ret += "\tprivate " + getSimpleClassName((String)MapUtil.getIgnoreCase((Map)properties,fields.get(i).getType())) +" " +StringUtil.getCamelProperty(fields.get(i).getName()) + ";\r\n";
        }
        ret += "\r\n";
        ret += generateGetterSetter(table);
        ret += generateToString(table);
        ret += "}\r\n";
        return ret;
    }

    /**
     * 生成entity类
     * @param table
     * @return
     */
    private String generateDao(Table table) {
        String ret = "";
        ret += "package "+ daoPackageName +";\r\n\r\n";
        ret += "import java.util.List;\r\n";
        ret += "import java.util.Map;\r\n\r\n";
        ret += "import "+entityPackageName+"."+table.getEntityName()+";\r\n\r\n";
        ret += "/*\r\n";
        ret += " * @description "+getCommentString(table.getComment())+"Dao\r\n";
        ret += " * @author "+ System.getProperty("user.name")+"\r\n";
        ret += " * @version "+ DateUtil.thisDate()+" modify: "+System.getProperty("user.name")+"\r\n";
        ret += " * @since 1.0\r\n";
        ret += " */\r\n";
        ret += "public interface " + table.getEntityName() + "Dao {\r\n";
        ret += "\tObject insert("+table.getEntityName()+" "+getBeanNameByClassName(table.getEntityName())+");\r\n";
        ret += "\tList<"+table.getEntityName()+"> select(Map<String,Object> params);\r\n";
        ret += "\tint update(Map<String,Object> params);\r\n";
        ret += "\tint delete(Map<String,Object> params);\r\n";
        ret += "}\r\n";
        return ret;
    }

    /**
     * 生成entity类
     * @param table
     * @return
     */
    private String generateDaoImpl(Table table) {
        String ret = "";
        ret += "package "+ daoPackageName +".impl;\r\n\r\n";
        ret += "import java.util.List;\r\n";
        ret += "import java.util.Map;\r\n\r\n";
        ret += "import org.springframework.beans.factory.annotation.Autowired;\r\n";
        ret += "import org.springframework.orm.ibatis.SqlMapClientOperations;\r\n";
        ret += "import org.springframework.stereotype.Repository;\r\n\r\n";
        ret += "import "+daoPackageName+"."+table.getEntityName()+"Dao;\r\n";
        ret += "import "+entityPackageName+"."+table.getEntityName()+";\r\n\r\n";
        ret += "/*\r\n";
        ret += " * @description "+getCommentString(table.getComment())+"DaoImpl\r\n";
        ret += " * @author "+ System.getProperty("user.name")+"\r\n";
        ret += " * @version "+ DateUtil.thisDate()+" modify: "+System.getProperty("user.name")+"\r\n";
        ret += " * @since 1.0\r\n";
        ret += " */\r\n";
        ret += "@Repository\r\n";
        ret += "public class " + table.getEntityName() + "DaoImpl implements "+table.getEntityName()+"Dao {\r\n";
        ret += "\t@Autowired\r\n";
        ret += "\tpublic SqlMapClientOperations sqlMap;\r\n\r\n";

        ret += "\t@Override\r\n";
        ret += "\tpublic Object insert("+table.getEntityName()+" "+getBeanNameByClassName(table.getEntityName())+"){\r\n";
        ret += "\t\treturn sqlMap.insert(\""+table.getEntityName()+".insert\", "+getBeanNameByClassName(table.getEntityName())+");\r\n";
        ret += "\t}\r\n\r\n";

        ret += "\t@Override\r\n";
        ret += "\tpublic List<"+table.getEntityName()+"> select(Map<String,Object> params){\r\n";
        ret += "\t\treturn sqlMap.queryForList(\""+table.getEntityName()+".select\",params);\r\n";
        ret += "\t}\r\n\r\n";

        ret += "\t@Override\r\n";
        ret += "\tpublic int update(Map<String,Object> params){\r\n";
        ret += "\t\treturn sqlMap.update(\""+table.getEntityName()+".update\",params);\r\n";
        ret += "\t}\r\n\r\n";

        ret += "\t@Override\r\n";
        ret += "\tpublic int delete(Map<String,Object> params){\r\n";
        ret += "\t\treturn sqlMap.delete(\""+table.getEntityName()+".delete\",params);\r\n";
        ret += "\t}\r\n\r\n";

        ret += "}\r\n\r";
        return ret;
    }

    private String getBeanNameByClassName(String className){
        return className.substring(0,1).toLowerCase()+className.substring(1);
    }

    /**
     * 生成结果映射
     * @param table
     * @return
     */
    private String generateResultMap(Table table) {
        String ret = "<resultMap id=\""+table.getEntityName()+"BaseResultMap\" class=\""+ entityPackageName +"."+table.getEntityName()+"\" >\r\n";
        List<Field> fields = table.getFields();
        for(int i =0;i<fields.size();i++){
            ret += "\t<result column=\"" + fields.get(i).getName()+"\" property=\"" + StringUtil.getCamelProperty(fields.get(i).getName()) + "\" jdbcType=\"" + fields.get(i).getType().toUpperCase()+"\" />\r\n";
        }
        ret += "</resultMap>\r\n";
        return ret;
    }
    /**
     * 生成select查询
     * @param table
     * @return
     */
    private String generateSelect(Table table) {
        String ret = "<select id=\"select\" resultMap=\""+table.getEntityName()+"BaseResultMap\" parameterClass=\"java.util.HashMap\" >\r\n";
        ret += "\tSELECT ";
        List<Field> fields = table.getFields();
        for(int i =0;i<fields.size();i++){
            ret += fields.get(i).getName();
            if(i<fields.size()-1){
                ret += ",";
            }
        }
        ret += "\t\r\n";
        ret += "\tFROM " + table.getName() +" \r\n";
        ret += "\t<dynamic prepend=\"where\" >\r\n";
        for(int i =0;i<fields.size();i++){
            ret += "\t\t<isNotNull prepend=\"and\" property=\""+StringUtil.getCamelProperty(fields.get(i).getName())+"\" >\r\n";
            ret += "\t\t\t"+fields.get(i).getName()+" = #"+StringUtil.getCamelProperty(fields.get(i).getName())+"#\r\n";
            ret += "\t\t</isNotNull>\r\n";
        }
        ret += "\t</dynamic>\r\n";
        ret += "</select>\r\n";
        return ret;
    }

    /**
     * 生成delete查询
     * @param table
     * @return
     */
    private String generateDelete(Table table) {
        String ret = "<delete id=\"delete\"  parameterClass=\"java.util.HashMap\" >\r\n";
        ret += "\tDELETE FROM ";
        ret += table.getName() +" \r\n";
        List<Field> fields = table.getFields();

        ret += "\tWHERE "+fields.get(0).getName()+" = #"+StringUtil.getCamelProperty(fields.get(0).getName())+"#\r\n";
        ret += "</delete>\r\n";
        return ret;
    }
    /**
     * 生成insert语句
     * @param table
     * @return
     */
    private String generateInsert(Table table) {
        String ret = "<insert id=\"insert\"  parameterClass=\""+ entityPackageName +"."+table.getEntityName()+"\" >\r\n";
        ret += "\tINSERT INTO "+table.getName()+" (\r\n\t\t";
        List<Field> fields = table.getFields();
        for(int i =0;i<fields.size();i++){
            ret += fields.get(i).getName();
            if(i<fields.size()-1){
                ret += ",";
            }
        }
        ret += "\r\n\t)values (\n" + "\t\t\t";
        for(int i =0;i<fields.size();i++){
            ret += "#"+StringUtil.getCamelProperty(fields.get(i).getName())+"#";
            if(i<fields.size()-1){
                ret += ",";
            }
        }
        ret += "\r\n\t)\r\n";
        ret += "</insert>\r\n";
        return ret;
    }

    /**
     * 生成update语句
     * @param table
     * @return
     */
    private String generateUpdate(Table table) {
        String ret = "<update id=\"update\"  parameterClass=\"java.util.HashMap\" >\r\n";
        ret += "\tUPDATE "+table.getName()+"\r\n";
        List<Field> fields = table.getFields();
        ret += "\t<dynamic prepend=\"set\" >\r\n";
        for(int i =0;i<fields.size();i++){
            ret += "\t\t<isNotNull prepend=\",\" property=\""+StringUtil.getCamelProperty(fields.get(i).getName())+"\" >\r\n";
            ret += "\t\t\t"+fields.get(i).getName()+" = #"+StringUtil.getCamelProperty(fields.get(i).getName())+"#\r\n";
            ret += "\t\t</isNotNull>\r\n";
        }
        ret += "\t</dynamic>\r\n";

        ret += "\t<dynamic prepend=\"where\" >\r\n";
        for(int i =0;i<fields.size();i++){
            ret += "\t\t<isNotNull prepend=\"and\" property=\""+StringUtil.getCamelProperty(fields.get(i).getName())+"\" >\r\n";
            ret += "\t\t\t"+fields.get(i).getName()+" = #"+StringUtil.getCamelProperty(fields.get(i).getName())+"#\r\n";
            ret += "\t\t</isNotNull>\r\n";
        }
        ret += "\t</dynamic>\r\n";
        ret += "</update>\r\n";
        return ret;
    }

    /**
     * 得到简单类名
     * @param name
     * @return
     */
    private String getSimpleClassName(String name){
        if(name == null){
            return null;
        }
        int index = name.lastIndexOf(".");
        return name.substring(index+1);
    }

    private void parseData(List<List<String>> data) {
        Map map = (Map)properties;
        initTableNameAndComment(data);
        //initTableComment(row);
        for (int i = 0; i < data.size(); i++) {
            List<String> row = data.get(i);
            if(contains(row, map)){
                Field field = new Field();
                for (int j = 0; j< row.size(); j++) {
                    if(MapUtil.containsKeyIgnoreCase(map,row.get(j))){
                        String pre = getPre(row, row.get(j));
                        field.setName(pre);
                        field.setType(row.get(j));
                    }

                }
                for (int j = 0; j< row.size(); j++) {
                    if("COMMENT".equals(row.get(j).toUpperCase())){
                        String after = getAfter(row, row.get(j));
                        field.setComment(after);
                    }else if("-".equals(row.get(j))&&j+2<row.size()&&"-".equals(row.get(j+1))){
                        field.setComment(row.get(j+2));
                    }
                }
                table.getFields().add(field);
            }

        }
    }
    private String generateGetterSetter(Table table){
        List<Field> fields = table.getFields();
        String ret = "";
        for(Field field: fields){
            ret += generateGetter(field);
            ret += generateSetter(field);
        }
        return ret;
    }

    /**
     * 生成getter方法
     * @param field
     * @return
     */
    private String generateGetter(Field field){
        String ret = "";
        String camelProperty = StringUtil.getCamelProperty(field.getName());
        ret += "\tpublic " + getSimpleClassName((String)MapUtil.getIgnoreCase((Map)properties,field.getType())) + " " + StringUtil.getCamelProperty("get_" +field.getName())+"() {\r\n";
        ret += "\t\treturn "+"this."+camelProperty +" ;\r\n";
        ret += "\t}\r\n\r\n";
        return ret;
    }

    /**
     * 生成setter方法
     * @param field
     * @return
     */
    private String generateSetter(Field field){
        String ret = "";
        String camelProperty = StringUtil.getCamelProperty(field.getName());
        ret += "\tpublic void " + StringUtil.getCamelProperty("set_" +field.getName())+"("+getSimpleClassName((String)MapUtil.getIgnoreCase((Map)properties,field.getType()))+" "+camelProperty+") {\r\n";
        ret += "\t\tthis."+camelProperty+" = "+camelProperty +" ;\r\n";
        ret += "\t}\r\n\r\n";
        return ret;
    }

    /**
     * 处理注释
     * @param comment
     * @return
     */
    private String getCommentString(String comment){
        if(comment != null) {
            if (comment.startsWith("'")) {
                comment = comment.substring(1);
            }
            if (comment.endsWith("'")) {
                comment = comment.substring(0, comment.length() - 1);
            }
        }else {
            return "";
        }
        return comment;
    }
    private void initTableComment(List<String> row) {
        for (int j = 0; j< row.size(); j++) {
            if("COMMENT".equals(row.get(j).toUpperCase())){
                String after = getAfter(row, row.get(j),2);
                if(after.startsWith("'")){
                    after = after.substring(1);
                }
                if(after.endsWith("'")){
                    after = after.substring(0,after.length()-1);
                }
                table.setComment(after);
            }else if("-".equals(row.get(j))&&j+2<row.size()&&"-".equals(row.get(j+1))){
                table.setComment(row.get(j+2));
            }
        }
    }

    /**
     * 得到name之后的，偏移offset
     * @param list
     * @param name
     * @param offset
     * @return
     */
    private String getAfter(List<String> list,String name,int offset){
        for(int i =0;i<list.size();i++){
            if(list.get(i).equals(name)){
                return list.get(i+offset);
            }
        }
        return null;
    }
    /**
     * 得到name之前的，偏移offset
     * @param list
     * @param name
     * @param offset
     * @return
     */
    private String getPre(List<String> list,String name,int offset){
        for(int i =0;i<list.size();i++){
            if(list.get(i).equals(name)){
                return list.get(i-offset);
            }
        }
        return null;
    }


    /**
     * 得到name前一个
     * @param list
     * @param name
     * @return
     */
    private String getPre(List<String> list,String name){
        return getPre(list,name,1);
    }
    /**
     * 得到name后一个
     * @param list
     * @param name
     * @return
     */
    private String getAfter(List<String> list,String name){
        return getAfter(list,name,1);
    }
    private void initTableNameAndComment(List<List<String>> data) {
        // 第一行存放的是表的信息
        List<String> row = data.get(0);
        boolean initName = false;
        boolean initComment = false;
        for (int j = 0; j + 2 < row.size()&&(initComment==false||initName==false); j++) {
            if (initName==false&&"CREATE".equals(row.get(j).toUpperCase()) && "TABLE".equals(row.get(j+1).toUpperCase())) {
                table.setName(row.get(j + 2));
                initName = true;
            }
            if(initComment==false&&"COMMENT".equals(row.get(j).toUpperCase())){
                String after = getAfter(row, row.get(j),2);
                if(after.startsWith("'")){
                    after = after.substring(1);
                }
                if(after.endsWith("'")){
                    after = after.substring(0,after.length()-1);
                }
                table.setComment(after);
                initComment = true;
            }else if("-".equals(row.get(j))&&j+2<row.size()&&"-".equals(row.get(j+1))){
                table.setComment(row.get(j+2));
                initComment = true;
            }
        }
        // 处理entity的名字
        table.setEntityName(StringUtil.getCamelClassName(table.getName().toLowerCase()));
        if(StringUtil.isEmpty(removePrefix)){
            return;
        }
        String[] split = removePrefix.split(",");
        for (String s : split){
            if(StringUtil.isNotEmpty(s)){
                // 忽略大小写
                if(StringUtil.startsWithIgnoreCase(table.getName(),s)){
                    table.setEntityName(StringUtil.getCamelClassName(table.getName().substring(s.length())));
                    break;
                }
            }
        }
    }

    /**
     * 集合list里面是否包含map中的任何的一个值(key)
     *
     * @param list
     * @param map
     * @return
     */
    private boolean contains(List<String> list, Map<String, Object> map) {
        Iterator iterator = map.keySet().iterator();
        while (iterator.hasNext()) {
            String next = (String) iterator.next();
            // 忽略大小写
            if (CollectionUtil.containsIgnoreCase(list,next)) {
                return true;
            }
        }
        return false;
    }


    private void initData(String inStr) {
        data = new ArrayList<>();
        SqlTokenizer tokenizer = new SqlTokenizer();
        List<String> wordList = tokenizer.split(inStr);
        List<String> newWordList = new ArrayList<>();

        for (int i = 0;i<wordList.size();i++){
            // 过滤不必要的字段
            if (!StringUtil.isEmpty(wordList.get(i).trim()) && !"`".equals(wordList.get(i).trim())) {
                newWordList.add(wordList.get(i).trim());
            }
        }
        int index_1 = newWordList.indexOf("(");
        int index_2 = newWordList.lastIndexOf(")");
        List<String> list1 = newWordList.subList(0, index_1 + 1);
        List<String> list2 = newWordList.subList(index_1 + 1, index_2);
        List<String> list3 = newWordList.subList(index_2, newWordList.size());

        List<List<String>> split = CollectionUtil.split(list2, (obj, nexObj) -> {
            return obj.equals(",");
        });
        list1.addAll(list3);
        data.add(list1);
        data.addAll(split);
    }

    /**
     * 生成toString
     * @param table
     * @return
     */
    private String generateToString(Table table){
        List<Field> fields = table.getFields();
        String ret = "";
        ret += "\t@Override\r\n";
        ret += "\tpublic String toString() {\r\n";
        ret += "\t\tfinal StringBuffer sb = new StringBuffer(\""+table.getEntityName()+"{\");\r\n";
        for (int i = 0;i<fields.size();i++){
            Field field = fields.get(i);
            if(i!=fields.size()-1) {
                ret += "\t\tsb.append(\"" + StringUtil.getCamelProperty(field.getName()) + "='\").append(" + StringUtil.getCamelProperty(field.getName()) + ").append('\\'');\r\n";
            }else {
                ret += "\t\tsb.append(\"" + StringUtil.getCamelProperty(field.getName()) + "='\").append(" + StringUtil.getCamelProperty(field.getName()) + ");\r\n";
            }
        }
        ret += "\t\tsb.append('}');\t\n";
        ret += "\t\treturn sb.toString();\r\n";
        ret += "\t}\r\n";
        return ret;
    }

    public String getEntityPackageName() {
        return entityPackageName;
    }

    public void setEntityPackageName(String entityPackageName) {
        this.entityPackageName = entityPackageName;
    }

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public String getDaoPackageName() {
        return daoPackageName;
    }

    public void setDaoPackageName(String daoPackageName) {
        this.daoPackageName = daoPackageName;
    }
}
