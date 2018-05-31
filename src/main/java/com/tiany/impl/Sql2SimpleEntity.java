package com.tiany.impl;

import com.tiany.inf.Convert;
import com.tiany.util.StringUtil;

import java.io.IOException;
import java.util.*;

public class Sql2SimpleEntity implements Convert {
    private Properties properties;

    private List<List<String>> data;

    private Table table = new Table();

    private String packageName = "com.csii.pmis.service.bean.model";

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

            String[] rows = inStr.split("\r\n");
            initData(inStr);
            parseData(data);
            HashMap<String, Object> map = new HashMap<>();
            outStr = generateJava(table);
            map.put("java",outStr);
            outStr = generateXml(table);
            map.put("xml",outStr);
//            outStr = generateResultMap(table);
//            map.put("resultMap",outStr);
//            outStr = generateSelect(table);
//            map.put("select",outStr);
//            outStr = generateInsert(table);
//            map.put("insert",outStr);
//            outStr = generateUpdate(table);
//            map.put("update",outStr);
//            outStr = generateDelete(table);
//            map.put("delete",outStr);

            return map;
        }
        return null;
    }

    public String generateXml(Table table){
        String ret = "";
        ret += "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\r\n";
        ret += "<!DOCTYPE sqlMap PUBLIC \"-//ibatis.apache.org//DTD SQL Map 2.0//EN\" \"http://ibatis.apache.org/dtd/sql-map-2.dtd\" >\r\n";
        ret += "<sqlMap namespace=\""+table.getName()+"\" >\r\n";
        ret += generateResultMap(table);
        ret += generateSelect(table);
        ret += generateDelete(table);
        ret += generateUpdate(table);
        ret += generateInsert(table);
        ret += "</sqlMap>\r\n";
        return ret;
    }
    /**
     * 生成java属性
     * @param table
     * @return
     */
    private String generateJava(Table table) {
        String ret = "";
        ret += "package "+packageName+";\r\n\r\n";
        ret += "import java.io.Serializable;\r\n";
        ret += "import java.util.Date;\r\n";
        ret += "import java.math.BigDecimal;\r\n\r\n";
        ret += "/*\r\n";
        ret += " *"+getCommentString(table.getComment())+"\r\n";
        ret += " */\r\n";
        ret += "public class " + StringUtil.getCamelClassName(table.getName()) + " implements Serializable{\r\n";
        ret += "\t/** 序列化号 */\r\n";
        ret += "\tprivate static final long serialVersionUID = 1L;\r\n";
        List<Field> fields = table.getFields();
        for(int i =0;i<fields.size();i++){
            ret += "\t/** "+getCommentString(fields.get(i).getComment())+" */\r\n";
            ret += "\tprivate " + getSimpleClassName(properties.get(fields.get(i).getType()) +" " +StringUtil.getCamelProperty(fields.get(i).getName())) + ";\r\n";
        }
        ret += "\r\n";
        ret += generateGetterSetter(table);
        ret += "}\r\n";
        return ret;
    }

    /**
     * 生成结果映射
     * @param table
     * @return
     */
    private String generateResultMap(Table table) {
        String ret = "<resultMap id=\""+StringUtil.getCamelClassName(table.getName())+"BaseResultMap\" class=\""+packageName+"."+StringUtil.getCamelClassName(table.getName())+"\" >\r\n";
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
        String ret = "<select id=\"select\" resultMap=\""+StringUtil.getCamelClassName(table.getName())+"BaseResultMap\" parameterClass=\""+packageName+"."+StringUtil.getCamelClassName(table.getName())+"\" >\r\n";
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
        String ret = "<delete id=\"delete\" resultMap=\""+StringUtil.getCamelClassName(table.getName())+"BaseResultMap\" parameterClass=\""+packageName+"."+StringUtil.getCamelClassName(table.getName())+"\" >\r\n";
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
        String ret = "<insert id=\"insert\"  parameterClass=\""+packageName+"."+StringUtil.getCamelClassName(table.getName())+"\" >\r\n";
        ret += "\tINSERT INTO "+table.getName()+" (\r\n\t\t";
        List<Field> fields = table.getFields();
        for(int i =0;i<fields.size();i++){
            ret += fields.get(i).getName();
            if(i<fields.size()-1){
                ret += ",";
            }
        }
        ret += "\r\n\t)values (\n" + "\t\t";
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
        String ret = "<update id=\"update\"  parameterClass=\""+packageName+"."+StringUtil.getCamelClassName(table.getName())+"\" >\r\n";
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
        int index = name.lastIndexOf(".");
        return name.substring(index+1);
    }

    private void parseData(List<List<String>> data) {
        for (int i = 0; i < data.size(); i++) {
            List<String> row = data.get(i);
            if (!contains(row, (Map)properties)) {
                getTableName(row);
                getTableComment(row);
            }else{
                Field field = new Field();
                for (int j = 0; j< row.size(); j++) {
                    if(properties.containsKey(row.get(j).toLowerCase())){
                        String pre = getPre(row, row.get(j));
                        field.setName(pre);
                        field.setType(row.get(j));
                    }

                }
                for (int j = 0; j< row.size(); j++) {
                    if("COMMENT".equals(row.get(j).toUpperCase())){
                        String after = getAfter(row, row.get(j));
                        field.setComment(after);
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
        ret += "\tpublic " + getSimpleClassName((String) properties.get(field.getType())) + " " + StringUtil.getCamelProperty("get_" +field.getName())+"() {\r\n";
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
        ret += "\tpublic void " + StringUtil.getCamelProperty("set_" +field.getName())+"("+getSimpleClassName((String) properties.get(field.getType()))+" "+camelProperty+") {\r\n";
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
        if(comment.startsWith("'")){
            comment = comment.substring(1);
        }
        if(comment.endsWith("'")){
            comment = comment.substring(0,comment.length()-1);
        }
        return comment;
    }
    private void getTableComment(List<String> row) {
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
    private void getTableName(List<String> row) {
        for (int j = 0; j + 2 < row.size(); j++) {
            if ("CREATE".equals(row.get(j).toUpperCase()) && "TABLE".equals(row.get(j+1).toUpperCase())) {
                table.setName(row.get(j + 2));
                break;
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
            // TODO 大小写？？？
            if (list.contains(next)) {
                return true;
            }
        }
        return false;
    }


    private void initData(String inStr) {
        data = new ArrayList<>();
        String[] rows = inStr.split("\r\n");
        for (int i = 0; i < rows.length; i++) {
            SqlRowTokenizer sqlRowTokenizer = new SqlRowTokenizer();
            List<String> toke = sqlRowTokenizer.toke(rows[i]);
            ArrayList<String> list = new ArrayList<>();
            for (int j = 0; j < toke.size(); j++) {
                // 过滤不必要的字段
                if (!StringUtil.isEmpty(toke.get(j).trim()) && !"`".equals(toke.get(j).trim())) {
                    list.add(toke.get(j).trim());
                }
            }
            data.add(list);
        }


    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }
}
