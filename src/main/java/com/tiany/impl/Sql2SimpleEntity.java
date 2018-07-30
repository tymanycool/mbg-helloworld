package com.tiany.impl;

import com.tiany.inf.Convert;
import com.tiany.util.*;
import com.tiany.util.format.FormatUtil;
import com.tiany.util.validate.AssertUtil;

import java.io.IOException;
import java.util.*;

/**
 * 通过CREATE TABLE SQL 语句生成实体类及sqlMapper.xml
 * @author tiany
 * @version 1.0
 */
public class Sql2SimpleEntity implements Convert {
    private Random random = new Random();
    // 需要去除的前缀
    private static String removePrefix ="xq_,pmis_";

    // 定义新的前缀
    private static String prefix = "";

    // 定义后缀
    private static String suffix = "";

    // 实体类的存放的包路径
    private static String entityPackageName = "com.csii.pmis.service.bean.model";

    // dao类的存放的包路径
    private static String daoPackageName = "com.csii.pmis.admin.dao";

    // mapper文件的路径
    private static String mapperLocation = "service/db/sql-mapping/service/";

    // 是否生成接口
    private static boolean generateInterface = false;


    private  Properties props;

    private List<List<String>> data;

    private Table table = new Table();



    @Override
    public Object convert(Object in) {
        try {
            props = new Properties();
            props.load(this.getClass().getClassLoader().getResourceAsStream("type.properties"));
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
            if(generateInterface) {
                outStr = generateDao(table);
                map.put("dao", outStr);
                outStr = generateDaoImpl(table,generateInterface);
                map.put("daoImpl",outStr);
            }else{
                outStr = generateDaoImpl(table,generateInterface);
                map.put("dao",outStr);
            }
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
        ret += "\r\n";
        ret += FormatUtil.addTab(generateSelectCountByParams(table),1);
        ret += "\r\n";
        ret += FormatUtil.addTab(generateSelectForList(table),1);
        ret += "\r\n";
        ret += FormatUtil.addTab(generateSelectForMapList(table),1);
        ret += "\r\n";
        ret += FormatUtil.addTab(generateSelectForObject(table),1);
        ret += "\r\n";
        ret += FormatUtil.addTab(generateSelectForMap(table),1);
        if(hasPrimatyKey(table)){
            ret += "\r\n";
            ret += FormatUtil.addTab(generateSelectByPrimaryKey(table),1);
        }
        if(hasPrimatyKey(table)){
            ret += "\r\n";
            ret += FormatUtil.addTab(generateDeleteByPrimaryKey(table),1);
        }
        if(hasPrimatyKey(table)){
            ret += "\r\n";
            ret += FormatUtil.addTab(generateDeleteList(table),1);
        }
        ret += "\r\n";
        ret += FormatUtil.addTab(generateDeleteByParams(table),1);
        ret += "\r\n";
        ret += FormatUtil.addTab(generateUpdateByParams(table),1);
        if(hasPrimatyKey(table)) {
            ret += "\r\n";
            ret += FormatUtil.addTab(generateUpdateByPrimaryKey(table), 1);
        }
        ret += "\r\n";
        if(hasPrimatyKey(table)) {
            ret += "\r\n";
            ret += FormatUtil.addTab(generateUpdateList(table), 1);
        }
        ret += "\r\n";
        ret += FormatUtil.addTab(generateInsert(table),1);
        ret += "\r\n";
        ret += FormatUtil.addTab(generateInsertList(table),1);
        ret += "\r\n";
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
        ret += "\tprivate static final long serialVersionUID = "+Math.abs(random.nextLong())+"L;\r\n";
        List<Field> fields = table.getFields();
        for(int i =0;i<fields.size();i++){
            ret += "\t/** "+getCommentString(fields.get(i).getComment())+" */\r\n";
            // TODO TIANYAO
            ret += "\tprivate " + getSimpleClassName((String)MapUtil.getIgnoreCase((Map) props,fields.get(i).getType())) +" " +StringUtil.getCamelProperty(fields.get(i).getName()) + ";\r\n";
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
        ret = getInsertComment(table, ret);
        ret += "\tObject insert("+table.getEntityName()+" "+getBeanNameByClassName(table.getEntityName())+");\r\n\r\n";

        ret = getInsertListComment(table, ret);
        ret += "\tObject insertList(List<"+table.getEntityName()+"> "+getBeanNameByClassName(table.getEntityName())+"List);\r\n\r\n";

        if(hasPrimatyKey(table)){
            ret = getSelectByPrimaryKeyComment(table, ret);
            ret +="\t"+table.getEntityName()+ " selectByPrimaryKey(" + getSimpleClassName((String)MapUtil.getIgnoreCase((Map) props,table.getPrimaryKeys().get(0).getType())) + " " + StringUtil.getCamelProperty(table.getPrimaryKeys().get(0).getName()) + ");\r\n\r\n";
        }

        ret = getSelectForListComment(table, ret);
        ret += "\tList<"+table.getEntityName()+"> selectForList(Map<String,? extends Object> params);\r\n\r\n";

        ret = getSelectCountByParamsComment(table, ret);
        ret += "\tint selectCountByParams(Map<String,? extends Object> params);\r\n\r\n";

        ret = getSelectForMapListComment(table, ret);
        ret += "\tList<Map<String,Object>> selectForMapList(Map<String,? extends Object> params);\r\n\r\n";

        ret = getSelectForObjectComment(table, ret);
        ret += "\t"+table.getEntityName()+" selectForObject("+table.getEntityName()+" "+getBeanNameByClassName(table.getEntityName())+");\r\n\r\n";

        ret = getSelectForMapComment(table, ret);
        ret += "\tMap<String,Object> selectForMap(Map<String,? extends Object> params);\r\n\r\n";

        if(hasPrimatyKey(table)){
            ret = getUpdateByPrimaryKeyComment(table, ret);
            ret += "\tint updateByPrimaryKey("+table.getEntityName()+" "+getBeanNameByClassName(table.getEntityName())+");\r\n\r\n";
        }

        if(hasPrimatyKey(table)){
            ret = getUpdateListComment(table, ret);
            ret += "\tint updateList(List<" + getSimpleClassName((String)MapUtil.getIgnoreCase((Map) props,table.getPrimaryKeys().get(0).getType())) + "> " + StringUtil.getCamelProperty(table.getPrimaryKeys().get(0).getName()) + "List,"+table.getEntityName()+" "+getBeanNameByClassName(table.getEntityName())+");\r\n\r\n";
        }

        ret = getUpdateByParamsComment(table, ret);
        ret += "\tint updateByParams(Map<String,? extends Object> params);\r\n\r\n";

        if(hasPrimatyKey(table)) {
            ret = getDeleteByPrimaryKeyComment(table, ret);
            ret += "\tint deleteByPrimaryKey(" + getSimpleClassName((String)MapUtil.getIgnoreCase((Map) props,table.getPrimaryKeys().get(0).getType())) + " " + StringUtil.getCamelProperty(table.getPrimaryKeys().get(0).getName()) + ");\r\n\r\n";
        }

        if(hasPrimatyKey(table)) {
            ret = getDeleteListComment(table, ret);
            ret += "\tint deleteList(List<" + getSimpleClassName((String)MapUtil.getIgnoreCase((Map) props,table.getPrimaryKeys().get(0).getType())) + "> " + StringUtil.getCamelProperty(table.getPrimaryKeys().get(0).getName()) + "List);\r\n\r\n";
        }

        ret = getDeleteByParamsComment(table, ret);
        ret += "\tint deleteByParams(Map<String,? extends Object> params);\r\n\r\n";
        ret += "}\r\n";
        return ret;
    }

    private String getDeleteByParamsComment(Table table, String ret) {
        ret += "\t/**\r\n";
        ret += "\t * 根据params删除"+table.getEntityName()+"\r\n";
        ret += "\t */\r\n";
        return ret;
    }

    private String getDeleteListComment(Table table, String ret) {
        ret += "\t/**\r\n";
        ret += "\t * 根据主键列表批量删除"+table.getEntityName()+"\r\n";
        ret += "\t */\r\n";
        return ret;
    }

    private String getDeleteByPrimaryKeyComment(Table table, String ret) {
        ret += "\t/**\r\n";
        ret += "\t * 根据主键删除"+table.getEntityName()+"\r\n";
        ret += "\t */\r\n";
        return ret;
    }

    private String getUpdateByParamsComment(Table table, String ret) {
        ret += "\t/**\r\n";
        ret += "\t * 根据params更新"+table.getEntityName()+"\r\n";
        ret += "\t */\r\n";
        return ret;
    }

    private String getUpdateListComment(Table table, String ret) {
        ret += "\t/**\r\n";
        ret += "\t * 根据主键列表更新"+table.getEntityName()+"\r\n";
        ret += "\t */\r\n";
        return ret;
    }

    private String getUpdateByPrimaryKeyComment(Table table, String ret) {
        ret += "\t/**\r\n";
        ret += "\t * 根据主键更新"+table.getEntityName()+"\r\n";
        ret += "\t */\r\n";
        return ret;
    }

    private String getSelectByPrimaryKeyComment(Table table, String ret) {
        ret += "\t/**\r\n";
        ret += "\t * 根据主键查询"+table.getEntityName()+"\r\n";
        ret += "\t */\r\n";
        return ret;
    }

    private String getSelectForMapComment(Table table, String ret) {
        ret += "\t/**\r\n";
        ret += "\t * 根据params查询，返回Map类型\r\n";
        ret += "\t */\r\n";
        return ret;
    }

    private String getSelectForObjectComment(Table table, String ret) {
        ret += "\t/**\r\n";
        ret += "\t * 根据对象字段查询"+table.getEntityName()+"对象\r\n";
        ret += "\t */\r\n";
        return ret;
    }

    private String getSelectForMapListComment(Table table, String ret) {
        ret += "\t/**\r\n";
        ret += "\t * 根据params查询Map类型的List集合，params为null表示查询所有\r\n";
        ret += "\t */\r\n";
        return ret;
    }

    private String getSelectCountByParamsComment(Table table, String ret) {
        ret += "\t/**\r\n";
        ret += "\t * 根据params查询"+table.getEntityName()+"记录的条数，params为null表示查询所有\r\n";
        ret += "\t */\r\n";
        return ret;
    }

    private String getSelectForListComment(Table table, String ret) {
        ret += "\t/**\r\n";
        ret += "\t * 根据params查询"+table.getEntityName()+"的List集合，params为null表示查询所有\r\n";
        ret += "\t */\r\n";
        return ret;
    }

    private String getInsertListComment(Table table, String ret) {
        ret += "\t/**\r\n";
        ret += "\t * 批量新增\r\n";
        ret += "\t */\r\n";
        return ret;
    }

    private String getInsertComment(Table table, String ret) {
        ret += "\t/**\r\n";
        ret += "\t * 插入一条新的纪录\r\n";
        ret += "\t */\r\n";
        return ret;
    }

    /**
     * 生成entity类
     * @param table
     * @return
     */
    private String generateDaoImpl(Table table,boolean generateInterface) {
        String ret = "";
        ret += "package "+ daoPackageName +(generateInterface?".impl":"")+";\r\n\r\n";
        ret += "import java.util.List;\r\n";
        ret += "import java.util.Map;\r\n\r\n";
        ret += "import java.util.HashMap;\r\n\r\n";
        ret += "import org.springframework.beans.factory.annotation.Autowired;\r\n";
        ret += "import org.springframework.orm.ibatis.SqlMapClientOperations;\r\n";
        ret += "import org.springframework.stereotype.Repository;\r\n\r\n";
        if(generateInterface) {
            ret += "import " + daoPackageName + "." + table.getEntityName() + "Dao;\r\n";
        }
        ret += "import "+entityPackageName+"."+table.getEntityName()+";\r\n\r\n";
        ret += "/*\r\n";
        ret += " * @description "+getCommentString(table.getComment())+"Dao"+(generateInterface?"Impl":"")+"\r\n";
        ret += " * @author "+ System.getProperty("user.name")+"\r\n";
        ret += " * @version "+ DateUtil.thisDate()+" modify: "+System.getProperty("user.name")+"\r\n";
        ret += " * @since 1.0\r\n";
        ret += " */\r\n";
        ret += "@Repository\r\n";
        if(generateInterface) {
            ret += "public class " + table.getEntityName() + "DaoImpl implements " + table.getEntityName() + "Dao {\r\n";
        }else{
            ret += "public class " + table.getEntityName() + "Dao {\r\n";
        }
        ret += "\t@Autowired\r\n";
        ret += "\tprivate SqlMapClientOperations sqlMap;\r\n\r\n";

        if(generateInterface) {
            ret += "\t@Override\r\n";
        }else{
            ret = getInsertComment(table, ret);
        }
        ret += "\tpublic Object insert("+table.getEntityName()+" "+getBeanNameByClassName(table.getEntityName())+"){\r\n";
        ret += "\t\treturn sqlMap.insert(\""+table.getEntityName()+".insert\", "+getBeanNameByClassName(table.getEntityName())+");\r\n";
        ret += "\t}\r\n\r\n";

        if(generateInterface) {
            ret += "\t@Override\r\n";
        }else{
            ret = getInsertListComment(table, ret);
        }
        ret += "\tpublic Object insertList(List<"+table.getEntityName()+"> "+getBeanNameByClassName(table.getEntityName())+"List){\r\n";
        ret += "\t\treturn sqlMap.insert(\""+table.getEntityName()+".insertList\", "+getBeanNameByClassName(table.getEntityName())+"List);\r\n";
        ret += "\t}\r\n\r\n";

        if(generateInterface) {
            ret += "\t@Override\r\n";
        }else {
            ret = getSelectByPrimaryKeyComment(table,ret);
        }
        ret +="\tpublic "+table.getEntityName()+ " selectByPrimaryKey(" + getSimpleClassName((String)MapUtil.getIgnoreCase((Map) props,table.getPrimaryKeys().get(0).getType())) + " " + StringUtil.getCamelProperty(table.getPrimaryKeys().get(0).getName()) + "){\r\n";
        ret += "\t\treturn ("+table.getEntityName()+")sqlMap.queryForObject(\""+table.getEntityName()+".selectByPrimaryKey\","+StringUtil.getCamelProperty(table.getPrimaryKeys().get(0).getName())+");\r\n";
        ret += "\t}\r\n\r\n";

        if(generateInterface) {
            ret += "\t@Override\r\n";
        }else {
            ret = getSelectForListComment(table,ret);
        }
        ret += "\tpublic List<"+table.getEntityName()+"> selectForList(Map<String,? extends Object> params){\r\n";
        ret += "\t\treturn sqlMap.queryForList(\""+table.getEntityName()+".selectForList\",params);\r\n";
        ret += "\t}\r\n\r\n";

        if(generateInterface) {
            ret += "\t@Override\r\n";
        }else{
            ret = getSelectCountByParamsComment(table,ret);
        }
        ret += "\tpublic int selectCountByParams(Map<String,? extends Object> params){\r\n";
        ret += "\t\treturn (Integer)sqlMap.queryForObject(\""+table.getEntityName()+".selectCountByParams\",params);\r\n";
        ret += "\t}\r\n\r\n";

        if(generateInterface) {
            ret += "\t@Override\r\n";
        }else{
            ret = getSelectForMapListComment(table, ret);
        }
        ret += "\tpublic List<Map<String,Object>> selectForMapList(Map<String,? extends Object> params){\r\n";
        ret += "\t\treturn sqlMap.queryForList(\""+table.getEntityName()+".selectForMapList\",params);\r\n";
        ret += "\t}\r\n\r\n";

        if(generateInterface) {
            ret += "\t@Override\r\n";
        }else{
            ret = getSelectForObjectComment(table,ret);
        }
        ret += "\tpublic "+table.getEntityName()+" selectForObject("+table.getEntityName()+" "+getBeanNameByClassName(table.getEntityName())+"){\r\n";
        ret += "\t\treturn ("+table.getEntityName()+")sqlMap.queryForObject(\""+table.getEntityName()+".selectForObject\","+getBeanNameByClassName(table.getEntityName())+");\r\n";
        ret += "\t}\r\n\r\n";

        if(generateInterface) {
            ret += "\t@Override\r\n";
        }else {
            ret = getSelectForMapComment(table, ret);
        }
        ret += "\tpublic Map<String,Object> selectForMap(Map<String,? extends Object> params){\r\n";
        ret += "\t\treturn (Map<String,Object>)sqlMap.queryForObject(\""+table.getEntityName()+".selectForMap\",params);\r\n";
        ret += "\t}\r\n\r\n";

        if(hasPrimatyKey(table)){
            if(generateInterface) {
                ret += "\t@Override\r\n";
            }else{
                ret = getUpdateByParamsComment(table,ret);
            }
            ret += "\tpublic int updateByPrimaryKey("+table.getEntityName()+" "+getBeanNameByClassName(table.getEntityName())+"){\r\n";
            ret += "\t\treturn sqlMap.update(\""+table.getEntityName()+".updateByPrimaryKey\","+getBeanNameByClassName(table.getEntityName())+");\r\n";
            ret += "\t}\r\n\r\n";
        }
        if(hasPrimatyKey(table)){
            if(generateInterface) {
                ret += "\t@Override\r\n";
            }else{
                ret = getUpdateListComment(table,ret);
            }
            ret += "\tpublic int updateList(List<" + getSimpleClassName((String)MapUtil.getIgnoreCase((Map) props,table.getPrimaryKeys().get(0).getType())) + "> " + StringUtil.getCamelProperty(table.getPrimaryKeys().get(0).getName()) + "List,"+table.getEntityName()+" "+getBeanNameByClassName(table.getEntityName())+"){\r\n";
            ret += "\t\tMap<String, Object> params = new HashMap<String,Object>();\r\n";
            ret += "\t\tparams.put(\""+StringUtil.getCamelProperty(table.getPrimaryKeys().get(0).getName()) + "List"+"\", "+StringUtil.getCamelProperty(table.getPrimaryKeys().get(0).getName()) + "List"+");\r\n";
            ret += "\t\tparams.put(\""+getBeanNameByClassName(table.getEntityName())+"\", "+getBeanNameByClassName(table.getEntityName())+");\r\n";
            ret += "\t\treturn sqlMap.update(\""+table.getEntityName()+".updateList\",params);\r\n";
            ret += "\t}\r\n\r\n";
        }

        if(generateInterface) {
            ret += "\t@Override\r\n";
        }else {
            ret = getUpdateByParamsComment(table,ret);
        }
        ret += "\tpublic int updateByParams(Map<String,? extends Object> params){\r\n";
        ret += "\t\treturn sqlMap.update(\""+table.getEntityName()+".updateByParams\",params);\r\n";
        ret += "\t}\r\n\r\n";

        if(hasPrimatyKey(table)) {
            if(generateInterface) {
                ret += "\t@Override\r\n";
            }else {
                ret = getDeleteByPrimaryKeyComment(table,ret);
            }
            ret += "\tpublic int deleteByPrimaryKey(" + getSimpleClassName((String)MapUtil.getIgnoreCase((Map) props,table.getPrimaryKeys().get(0).getType())) + " " + StringUtil.getCamelProperty(table.getPrimaryKeys().get(0).getName()) + "){\r\n";
            ret += "\t\treturn sqlMap.delete(\"" + table.getEntityName() + ".deleteByPrimaryKey\"," + StringUtil.getCamelProperty(table.getPrimaryKeys().get(0).getName()) + ");\r\n";
            ret += "\t}\r\n\r\n";
        }

        if(hasPrimatyKey(table)) {
            if(generateInterface) {
                ret += "\t@Override\r\n";
            }else {
                ret = getDeleteListComment(table,ret);
            }
            ret += "\tpublic int deleteList(List<" + getSimpleClassName((String)MapUtil.getIgnoreCase((Map) props,table.getPrimaryKeys().get(0).getType())) + "> " + StringUtil.getCamelProperty(table.getPrimaryKeys().get(0).getName()) + "List){\r\n";
            ret += "\t\treturn sqlMap.delete(\"" + table.getEntityName() + ".deleteList\"," + StringUtil.getCamelProperty(table.getPrimaryKeys().get(0).getName()) + "List);\r\n";
            ret += "\t}\r\n\r\n";
        }

        if(generateInterface) {
            ret += "\t@Override\r\n";
        }else {
            ret = getDeleteByParamsComment(table,ret);
        }
        ret += "\tpublic int deleteByParams(Map<String,? extends Object> params){\r\n";
        ret += "\t\treturn sqlMap.delete(\""+table.getEntityName()+".deleteByParams\",params);\r\n";
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
        ret += "</resultMap>\r\n\r\n";

        ret += "<resultMap id=\"HashMapBaseResultMap\" class=\"java.util.HashMap\" >\r\n";
        for(int i =0;i<fields.size();i++){
            ret += "\t<result column=\"" + fields.get(i).getName()+"\" property=\"" + StringUtil.getCamelProperty(fields.get(i).getName()) + "\" jdbcType=\"" + fields.get(i).getType().toUpperCase()+"\" />\r\n";
        }
        ret += "</resultMap>\r\n";
        return ret;
    }
    /**
     * 生成selectForList查询
     * @param table
     * @return
     */
    private String generateSelectForList(Table table) {
        String ret = "<select id=\"selectForList\" resultMap=\""+table.getEntityName()+"BaseResultMap\" parameterClass=\"java.util.Map\" >\r\n";
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
            ret += "\t\t<"+getPropertyDynamicLabel(fields.get(i))+" prepend=\"and\" property=\""+StringUtil.getCamelProperty(fields.get(i).getName())+"\" >";
            ret += " "+fields.get(i).getName()+" = #"+StringUtil.getCamelProperty(fields.get(i).getName())+"#";
            ret += " </"+getPropertyDynamicLabel(fields.get(i))+">\r\n";
        }
        ret += "\t</dynamic>\r\n";
        ret += "</select>\r\n";
        return ret;
    }

    /**
     * 生成selectCountByParams查询
     * @param table
     * @return
     */
    private String generateSelectCountByParams(Table table) {
        String ret = "<select id=\"selectCountByParams\" resultClass=\"java.lang.Integer\" parameterClass=\"java.util.Map\" >\r\n";
        ret += "\tSELECT COUNT(*)\r\n";
        ret += "\tFROM " + table.getName() +" \r\n";
        ret += "\t<dynamic prepend=\"where\" >\r\n";
        List<Field> fields = table.getFields();
        for(int i =0;i<fields.size();i++){
            ret += "\t\t<"+getPropertyDynamicLabel(fields.get(i))+" prepend=\"and\" property=\""+StringUtil.getCamelProperty(fields.get(i).getName())+"\" >";
            ret += " "+fields.get(i).getName()+" = #"+StringUtil.getCamelProperty(fields.get(i).getName())+"#";
            ret += " </"+getPropertyDynamicLabel(fields.get(i))+">\r\n";
        }
        ret += "\t</dynamic>\r\n";
        ret += "</select>\r\n";
        return ret;
    }

    /**
     * 生成selectForMapList查询
     * @param table
     * @return
     */
    private String generateSelectForMapList(Table table) {
        String ret = "<select id=\"selectForMapList\" resultMap=\"HashMapBaseResultMap\" parameterClass=\"java.util.Map\" >\r\n";
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
            ret += "\t\t<"+getPropertyDynamicLabel(fields.get(i))+" prepend=\"and\" property=\""+StringUtil.getCamelProperty(fields.get(i).getName())+"\" >";
            ret += " "+fields.get(i).getName()+" = #"+StringUtil.getCamelProperty(fields.get(i).getName())+"#";
            ret += " </"+getPropertyDynamicLabel(fields.get(i))+">\r\n";
        }
        ret += "\t</dynamic>\r\n";
        ret += "</select>\r\n";
        return ret;
    }

    /**
     * 生成selectForObject查询
     * @param table
     * @return
     */
    private String generateSelectForObject(Table table) {
        String ret = "<select id=\"selectForObject\" resultMap=\""+table.getEntityName()+"BaseResultMap\" parameterClass=\""+entityPackageName+"."+table.getEntityName()+"\" >\r\n";
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
            ret += "\t\t<"+getPropertyDynamicLabel(fields.get(i))+" prepend=\"and\" property=\""+StringUtil.getCamelProperty(fields.get(i).getName())+"\" >";
            ret += " "+fields.get(i).getName()+" = #"+StringUtil.getCamelProperty(fields.get(i).getName())+"#";
            ret += " </"+getPropertyDynamicLabel(fields.get(i))+">\r\n";
        }
        ret += "\t</dynamic>\r\n";
        ret += "</select>\r\n";
        return ret;
    }

    /**
     * 生成selectByPrimaryKey查询
     * @param table
     * @return
     */
    private String generateSelectByPrimaryKey(Table table) {
        List<Field> primaryKeys = table.getPrimaryKeys();
        String ret = "<select id=\"selectByPrimaryKey\" resultMap=\""+table.getEntityName()+"BaseResultMap\" parameterClass=\""+ MapUtil.getIgnoreCase((Map)props,primaryKeys.get(0).getType())+"\" >\r\n";
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
        ret += "\tWHERE "+fields.get(0).getName()+" = #"+StringUtil.getCamelProperty(fields.get(0).getName())+"#\r\n";
        ret += "</select>\r\n";
        return ret;
    }

    /**
     * 生成selectForMap查询
     * @param table
     * @return
     */
    private String generateSelectForMap(Table table) {
        String ret = "<select id=\"selectForMap\" resultMap=\"HashMapBaseResultMap\" parameterClass=\"java.util.Map\" >\r\n";
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
            ret += "\t\t<"+getPropertyDynamicLabel(fields.get(i))+" prepend=\"and\" property=\""+StringUtil.getCamelProperty(fields.get(i).getName())+"\" >";
            ret += " "+fields.get(i).getName()+" = #"+StringUtil.getCamelProperty(fields.get(i).getName())+"#";
            ret += " </"+getPropertyDynamicLabel(fields.get(i))+">\r\n";
        }
        ret += "\t</dynamic>\r\n";
        ret += "</select>\r\n";
        return ret;
    }

    /**
     * 生成deleteByPrimaryKey
     * @param table
     * @return
     */
    private String generateDeleteByPrimaryKey(Table table) {
        List<Field> fields = table.getPrimaryKeys();
        AssertUtil.isTrue(fields.size() == 1);
        String ret = "<delete id=\"deleteByPrimaryKey\"  parameterClass=\""+ MapUtil.getIgnoreCase((Map)props,fields.get(0).getType())+"\" >\r\n";
        ret += "\tDELETE FROM ";
        ret += table.getName() +" \r\n";
        ret += "\tWHERE "+fields.get(0).getName()+" = #"+StringUtil.getCamelProperty(fields.get(0).getName())+"#\r\n";
        ret += "</delete>\r\n";
        return ret;
    }

    /**
     * 生成deleteList
     * @param table
     * @return
     */
    private String generateDeleteList(Table table) {
        List<Field> fields = table.getPrimaryKeys();
        AssertUtil.isTrue(fields.size() == 1);
        String ret = "<delete id=\"deleteList\"  parameterClass=\"java.util.List\" >\r\n";
        ret += "\tDELETE FROM ";
        ret += table.getName() +" \r\n";
        ret += "\tWHERE "+fields.get(0).getName() +" IN(\r\n";

        ret += "\t<iterate conjunction =\",\">\r\n";
        ret +="\t\t";
        for(int i =0;i<fields.size();i++){
            ret +="#[]#";
            if(i != fields.size()-1){
                ret += ",";
            }
        }
        ret += "\r\n\t</iterate>)\r\n";
        ret += "</delete>\r\n";
        return ret;
    }

    /**
     * 生成deleteByParams查询
     * @param table
     * @return
     */
    private String generateDeleteByParams(Table table) {
        String ret = "<delete id=\"deleteByParams\"  parameterClass=\"java.util.Map\" >\r\n";
        ret += "\tDELETE FROM ";
        ret += table.getName() +" \r\n";
        List<Field> fields = table.getPrimaryKeys();

        if(fields.size()<1){
            fields = table.getFields();
        }

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
        ret += "\tINSERT INTO "+table.getName()+" (\r\n";
        ret += "\t<dynamic prepend=\" \">\r\n";
        List<Field> fields = table.getFields();
        for(int i =0;i<fields.size();i++){
            ret += "\t\t<"+getPropertyDynamicLabel(fields.get(i))+" property=\""+StringUtil.getCamelProperty(fields.get(i).getName())+"\" prepend=\",\">"+fields.get(i).getName()+"</"+getPropertyDynamicLabel(fields.get(i))+"> \r\n";
        }

        ret += "\t</dynamic>";
        ret += "\r\n\t) VALUES (\r\n";
        ret += "\t<dynamic prepend=\" \">\r\n";
        for(int i =0;i<fields.size();i++){
            ret += "\t\t<"+getPropertyDynamicLabel(fields.get(i))+" property=\""+StringUtil.getCamelProperty(fields.get(i).getName())+"\" prepend=\",\"> #"+StringUtil.getCamelProperty(fields.get(i).getName())+"# </"+getPropertyDynamicLabel(fields.get(i))+"> \r\n";
        }
        ret += "\t</dynamic>";
        ret += "\r\n\t)\r\n";
        ret += "</insert>\r\n";
        return ret;
    }

    /**
     * 生成insertList语句
     * @param table
     * @return
     */
    private String generateInsertList(Table table) {
        String ret = "<insert id=\"insertList\"  parameterClass=\"java.util.List\" >\r\n";
        ret += "\tINSERT INTO "+table.getName()+" (\r\n\t\t";
        List<Field> fields = table.getFields();
        for(int i =0;i<fields.size();i++){
            ret += fields.get(i).getName();
            if(i != fields.size()-1){
                ret += ",";
            }
        }
        ret += "\r\n\t) VALUES \r\n";
        ret += "\t<iterate conjunction =\",\">(\r\n";
        ret +="\t\t";
        for(int i =0;i<fields.size();i++){
            ret +="#list[]."+StringUtil.getCamelProperty(fields.get(i).getName())+"#";
            if(i != fields.size()-1){
                ret += ",";
            }
        }
        ret += "\r\n\t)</iterate>\r\n";
        ret += "</insert>\r\n";
        return ret;
    }

    /**
     * 生成updateByParams语句
     * @param table
     * @return
     */
    private String generateUpdateByParams(Table table) {
        String ret = "<update id=\"updateByParams\"  parameterClass=\"java.util.Map\" >\r\n";
        ret += "\tUPDATE "+table.getName()+"\r\n";
        List<Field> fields = table.getFields();
        ret += "\t<dynamic prepend=\"set\" >\r\n";
        for(int i =0;i<fields.size();i++){
            ret += "\t\t<"+getPropertyDynamicLabel(fields.get(i))+" prepend=\",\" property=\""+StringUtil.getCamelProperty(fields.get(i).getName())+"\" >";
            ret += " "+fields.get(i).getName()+" = #"+StringUtil.getCamelProperty(fields.get(i).getName())+"#";
            ret += " </"+getPropertyDynamicLabel(fields.get(i))+">\r\n";
        }
        ret += "\t</dynamic>\r\n";

        ret += "\t<dynamic prepend=\"where\" >\r\n";
        for(int i =0;i<fields.size();i++){
            ret += "\t\t<"+getPropertyDynamicLabel(fields.get(i))+" prepend=\"and\" property=\""+StringUtil.getCamelProperty(fields.get(i).getName())+"\" >";
            ret += " "+fields.get(i).getName()+" = #"+StringUtil.getCamelProperty(fields.get(i).getName())+"#";
            ret += " </"+getPropertyDynamicLabel(fields.get(i))+">\r\n";
        }
        ret += "\t</dynamic>\r\n";
        ret += "</update>\r\n";
        return ret;
    }

    /**
     * 根据主键列表生成updateList语句
     * @param table
     * @return
     */
    private String generateUpdateList(Table table) {
        String ret = "<update id=\"updateList\"  parameterClass=\"java.util.Map\" >\r\n";
        ret += "\tUPDATE "+table.getName()+"\r\n";
        List<Field> fields = table.getFields();
        ret += "\t<dynamic prepend=\"set\" >\r\n";
        for(int i =0;i<fields.size();i++){
            ret += "\t\t<"+getPropertyDynamicLabel(fields.get(i))+" prepend=\",\" property=\""+getBeanNameByClassName(table.getEntityName())+"."+StringUtil.getCamelProperty(fields.get(i).getName())+"\" >";
            ret += " "+fields.get(i).getName()+" = #"+getBeanNameByClassName(table.getEntityName())+"."+StringUtil.getCamelProperty(fields.get(i).getName())+"#";
            ret += " </"+getPropertyDynamicLabel(fields.get(i))+">\r\n";
        }
        ret += "\t</dynamic>\r\n";

        ret += "\tWHERE "+fields.get(0).getName() +" IN(\r\n";
        List<Field> primaryKeys = table.getPrimaryKeys();
        ret += "\t<iterate conjunction =\",\" property=\""+StringUtil.getCamelProperty(primaryKeys.get(0).getName())+"List\">\r\n";
        ret +="\t\t";
        for(int i =0;i<primaryKeys.size();i++){
            ret +="#"+StringUtil.getCamelProperty(primaryKeys.get(0).getName())+"List[]#";
            if(i != primaryKeys.size()-1){
                ret += ",";
            }
        }
        ret += "\r\n\t</iterate>)\r\n";
        ret += "</update>\r\n";
        return ret;
    }

    /**
     * 生成updateByPrimaryKey语句
     * @param table
     * @return
     */
    private String generateUpdateByPrimaryKey(Table table) {
        List<Field> primaryKeys = table.getPrimaryKeys();
        String ret = "<update id=\"updateByPrimaryKey\"  parameterClass=\""+entityPackageName+"."+table.getEntityName()+"\" >\r\n";
        ret += "\tUPDATE "+table.getName()+"\r\n";
        List<Field> fields = table.getFields();
        ret += "\t<dynamic prepend=\"set\" >\r\n";
        for(int i =0;i<fields.size();i++){
            // 不是主键时
            if(!primaryKeys.get(0).getName().toUpperCase().equals(fields.get(i).getName().toUpperCase())) {
                ret += "\t\t<"+getPropertyDynamicLabel(fields.get(i))+" prepend=\",\" property=\"" + StringUtil.getCamelProperty(fields.get(i).getName()) + "\" >";
                ret += " " + fields.get(i).getName() + " = #" + StringUtil.getCamelProperty(fields.get(i).getName()) + "#";
                ret += " </"+getPropertyDynamicLabel(fields.get(i))+">\r\n";
            }
        }
        ret += "\t</dynamic>\r\n";
        ret += "\tWHERE "+ primaryKeys.get(0).getName() + " = # " +StringUtil.getCamelProperty(primaryKeys.get(0).getName())+" #\r\n";
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
        if(index == -1){
            return name;
        }
        return name.substring(index+1);
    }

    private void parseData(List<List<String>> data) {
        Map map = (Map) props;
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
            }else if(StringUtil.containsIgnoreCase(row,"PRIMARY")){
                for (int j = 0; j< row.size(); j++) {
                    if("PRIMARY".equals(row.get(j).toUpperCase())){
                        // 找到主键的名字
                        String after = getAfter(row, row.get(j),3);
                        Field primaryKey = getPrimaryKey(table.getFields(), after);
                        table.getPrimaryKeys().add(primaryKey);
                    }
                }
            }

        }
    }

    /**
     * 通过名字得到属性对象
     * @param fields
     * @param name
     * @return
     */
    private Field getPrimaryKey(List<Field> fields,String name){
        for (Field field:fields){
            if(StringUtil.equalsIgnoreCase(field.getName(),name)){
                return field;
            }
        }
        return null;
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
        ret += "\tpublic " + getSimpleClassName((String)MapUtil.getIgnoreCase((Map) props,field.getType())) + " " + StringUtil.getCamelProperty("get_" +field.getName())+"() {\r\n";
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
        ret += "\tpublic void " + StringUtil.getCamelProperty("set_" +field.getName())+"("+getSimpleClassName((String)MapUtil.getIgnoreCase((Map) props,field.getType()))+" "+camelProperty+") {\r\n";
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
        if(StringUtil.isNotEmpty(prefix)){
            table.setEntityName(StringUtil.getCamelClassName(prefix)+table.getEntityName());
        }
        if(StringUtil.isNotEmpty(suffix)){
            table.setEntityName(table.getEntityName()+StringUtil.getCamelClassName(suffix));
        }
    }

    private String getPropertyDynamicLabel(Field field){
        if(StringUtil.equalsIgnoreCase(field.getType(),"varchar")||StringUtil.equalsIgnoreCase(field.getType(),"char")){
            return "isNotEmpty";
        }
        return "isNotNull";
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

    private boolean hasPrimatyKey(Table table){
        if(table.getPrimaryKeys().size()>=1){
            return true;
        }
        return false;
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

    public String getMapperLocation() {
        return mapperLocation;
    }

    public void setMapperLocation(String mapperLocation) {
        this.mapperLocation = mapperLocation;
    }

    public String getRemovePrefix() {
        return removePrefix;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setRemovePrefix(String removePrefix) {
        this.removePrefix = removePrefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public static boolean isGenerateInterface() {
        return generateInterface;
    }

    public static void setGenerateInterface(boolean generateInterface) {
        Sql2SimpleEntity.generateInterface = generateInterface;
    }
}
