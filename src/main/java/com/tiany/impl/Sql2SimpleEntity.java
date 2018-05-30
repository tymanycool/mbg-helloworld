package com.tiany.impl;

import com.tiany.inf.Convert;
import com.tiany.util.StringUtil;

import java.io.IOException;
import java.util.*;

public class Sql2SimpleEntity implements Convert {
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

            String[] rows = inStr.split("\r\n");
            initData(inStr);
            parseData(data);

            outStr = generateJava(table);

            return outStr;

        }
        return null;
    }

    private String generateJava(Table table) {
        String ret = "";
        ret = "public class " + StringUtil.getCamelClassName(table.getName()) + " {\r\n";
        List<Field> fields = table.getFields();
        for(int i =0;i<fields.size();i++){
            ret += "\tprivate " + getSimpleClassName(properties.get(fields.get(i).getType()) +" " +StringUtil.getCamelProperty(fields.get(i).getName())) + ";\r\n";
        }


        ret += "}";
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

  //              System.out.println(row);
            }

        }
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
}
