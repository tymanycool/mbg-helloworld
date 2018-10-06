package com.tiany.ibator.impl;

import com.tiany.ibator.AbstractBaseSqlibator;
import com.tiany.ibator.inf.Generator;
import com.tiany.ibator.meta.Field;
import com.tiany.ibator.meta.Table;
import com.tiany.util.DateUtil;
import com.tiany.util.MapUtil;
import com.tiany.util.StringUtil;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Random;

@Component
public class EntityExampleGenerator extends AbstractBaseSqlibator implements Generator {
    private Random random = new Random();

    @Override
    public String generate(Table table) {
        String ret = "";
        ret += "package "+ entityPackageName +";\r\n\r\n";
        ret += "import java.io.Serializable;\r\n";
        ret += "import java.util.Date;\r\n";
        ret += "import java.util.List;\r\n";
        ret += "import java.util.ArrayList;\r\n";
        ret += "import java.math.BigDecimal;\r\n\r\n";
        ret += "/*\r\n";
        ret += " * @description "+getCommentString(table.getComment())+"Example\r\n";
        ret += " * @author "+ System.getProperty("user.name")+"\r\n";
        ret += " * @version "+ DateUtil.thisDate()+" modify: "+System.getProperty("user.name")+"\r\n";
        ret += " * @since 1.0\r\n";
        ret += " */\r\n";
        ret += "public class " + table.getEntityName() + "Example implements Serializable{\r\n";
        ret += "\t/** 序列化号 */\r\n";
        ret += "\tprivate static final long serialVersionUID = "+Math.abs(random.nextLong())+"L;\r\n";
        ret += "\tprotected String orderByClause;\n";
        ret += "\tprotected boolean distinct;\n";
        ret += "\tprotected List<Criteria> oredCriteria;\n";
        ret += "\tpublic "+table.getEntityName()+"Example() {\n";
        ret += "\t\toredCriteria = new ArrayList<Criteria>();\n";
        ret += "\t}\n";
        ret += "\tpublic void setOrderByClause(String orderByClause) {\n";
        ret += "\t\tthis.orderByClause = orderByClause;\n";
        ret += "\t}\n";
        ret += "\tpublic String getOrderByClause() {\n";
        ret += "\t\treturn orderByClause;\n";
        ret += "\t}\n";
        ret += "\tpublic void setDistinct(boolean distinct) {\n";
        ret += "\t\tthis.distinct = distinct;\n";
        ret += "\t}\n";
        ret += "\tpublic boolean isDistinct() {\n";
        ret += "\t\treturn distinct;\n";
        ret += "\t}\n";
        ret += "\tpublic List<Criteria> getOredCriteria() {\n";
        ret += "\t\treturn oredCriteria;\n";
        ret += "\t}\n";
        ret += "\tpublic void or(Criteria criteria) {\n";
        ret += "\t\toredCriteria.add(criteria);\n";
        ret += "\t}\n";
        ret += "\tpublic Criteria or() {\n";
        ret += "\t\tCriteria criteria = createCriteriaInternal();\n";
        ret += "\t\toredCriteria.add(criteria);\n";
        ret += "\t\treturn criteria;\n";
        ret += "\t}\n";
        ret += "\tpublic Criteria createCriteria() {\n";
        ret += "\t\tCriteria criteria = createCriteriaInternal();\n";
        ret += "\t\tif (oredCriteria.size() == 0) {\n";
        ret += "\t\t\toredCriteria.add(criteria);\n";
        ret += "\t\t}\n";
        ret += "\t\treturn criteria;\n";
        ret += "\t}\n";
        ret += "\tprotected Criteria createCriteriaInternal() {\n";
        ret += "\t\tCriteria criteria = new Criteria();\n";
        ret += "\t\treturn criteria;\n";
        ret += "\t}\n";
        ret += "\tpublic void clear() {\n";
        ret += "\t\toredCriteria.clear();\n";
        ret += "\t\torderByClause = null;\n";
        ret += "\t\tdistinct = false;\n";
        ret += "\t}\n";
        ret += "\n";
        ret += "\tprotected abstract static class GeneratedCriteria {\n";
        ret += "\t\tprotected List<Criterion> criteria;\n";
        ret += "\t\tprotected GeneratedCriteria() {\n";
        ret += "\t\t\tsuper();\n";
        ret += "\t\t\tcriteria = new ArrayList<Criterion>();\n";
        ret += "\t\t}\n";
        ret += "\t\tpublic boolean isValid() {\n";
        ret += "\t\t\treturn criteria.size() > 0;\n";
        ret += "\t\t}\n";
        ret += "\t\tpublic List<Criterion> getAllCriteria() {\n";
        ret += "\t\t\treturn criteria;\n";
        ret += "\t\t}\n";
        ret += "\t\tpublic List<Criterion> getCriteria() {\n";
        ret += "\t\t\treturn criteria;\n";
        ret += "\t\t}\n";
        ret += "\t\tprotected void addCriterion(String condition) {\n";
        ret += "\t\t\tif (condition == null) {\n";
        ret += "\t\t\t\tthrow new RuntimeException(\"Value for condition cannot be null\");\n";
        ret += "\t\t\t}\n";
        ret += "\t\t\tcriteria.add(new Criterion(condition));\n";
        ret += "\t\t}\n";
        ret += "\t\tprotected void addCriterion(String condition, Object value, String property) {\n";
        ret += "\t\t\tif (value == null) {\n";
        ret += "\t\t\t\tthrow new RuntimeException(\"Value for \" + property + \" cannot be null\");\n";
        ret += "\t\t\t}\n";
        ret += "\t\t\tcriteria.add(new Criterion(condition, value));\n";
        ret += "\t\t}\n";
        ret += "\t\tprotected void addCriterion(String condition, Object value1, Object value2, String property) {\n";
        ret += "\t\t\tif (value1 == null || value2 == null) {\n";
        ret += "\t\t\t\tthrow new RuntimeException(\"Between values for \" + property + \" cannot be null\");\n";
        ret += "\t\t\t}\n";
        ret += "\t\t\tcriteria.add(new Criterion(condition, value1, value2));\n";
        ret += "\t\t}\n";


        // 生成属性相关的条件
        for(Field field:table.getFields()){
            ret += generateIsNull(field);
            ret += generateIsNotNull(field);
            ret += generateEqualTo(field);
            ret += generateNotEqualTo(field);
            ret += generateLessThan(field);
            ret += generateLike(field);
            ret += generateNotLike(field);
            ret += generateIn(field);
            ret += generateNotIn(field);
            ret += generateBetween(field);
            ret += generateNotBetween(field);
        }

        ret += "\t}\n";




        ret += "\tpublic static class Criteria extends GeneratedCriteria {\n";
        ret += "\t\tprotected Criteria() {\n";
        ret += "\t\t\tsuper();\n";
        ret += "\t\t}\n";
        ret += "\t}\n";
        ret += "\tpublic static class Criterion {\n";
        ret += "\t\tprivate String condition;\n";
        ret += "\t\tprivate Object value;\n";
        ret += "\t\tprivate Object secondValue;\n";
        ret += "\t\tprivate boolean noValue;\n";
        ret += "\t\tprivate boolean singleValue;\n";
        ret += "\t\tprivate boolean betweenValue;\n";
        ret += "\t\tprivate boolean listValue;\n\n";
        ret += "\t\tprivate String typeHandler;\n\n";
        ret += "\t\tpublic String getCondition() {\n";
        ret += "\t\t\treturn condition;\n";
        ret += "\t\t}\n";
        ret += "\t\tpublic Object getValue() {\n";
        ret += "\t\t\treturn value;\n";
        ret += "\t\t}\n";
        ret += "\t\tpublic Object getSecondValue() {\n";
        ret += "\t\t\treturn secondValue;\n";
        ret += "\t\t}\n";
        ret += "\t\tpublic boolean isNoValue() {\n";
        ret += "\t\t\treturn noValue;\n";
        ret += "\t\t}\n";
        ret += "\t\tpublic boolean isSingleValue() {\n";
        ret += "\t\t\treturn singleValue;\n";
        ret += "\t\t}\n";
        ret += "\t\tpublic boolean isBetweenValue() {\n";
        ret += "\t\treturn betweenValue;\n";
        ret += "\t\t}\n";
        ret += "\t\tpublic boolean isListValue() {\n";
        ret += "\t\t\treturn listValue;\n";
        ret += "\t\t}\n";
        ret += "\t\tpublic String getTypeHandler() {\n";
        ret += "\t\t\treturn typeHandler;\n";
        ret += "\t\t}\n";
        ret += "\t\tprotected Criterion(String condition) {\n";
        ret += "\t\t\tsuper();\n";
        ret += "\t\t\tthis.condition = condition;\n";
        ret += "\t\t\tthis.typeHandler = null;\n";
        ret += "\t\t\tthis.noValue = true;\n";
        ret += "\t\t}\n";
        ret += "\t\tprotected Criterion(String condition, Object value, String typeHandler) {\n";
        ret += "\t\t\tsuper();\n";
        ret += "\t\t\tthis.condition = condition;\n";
        ret += "\t\t\tthis.value = value;\n";
        ret += "\t\t\tthis.typeHandler = typeHandler;\n";
        ret += "\t\t\tif (value instanceof List<?>) {\n";
        ret += "\t\t\t\tthis.listValue = true;\n";
        ret += "\t\t\t} else {\n";
        ret += "\t\t\t\tthis.singleValue = true;\n";
        ret += "\t\t\t}\n";
        ret += "\t\t}\n";
        ret += "\t\tprotected Criterion(String condition, Object value) {\n";
        ret += "\t\t\tthis(condition, value, null);\n";
        ret += "\t\t}\n";
        ret += "\t\tprotected Criterion(String condition, Object value, Object secondValue, String typeHandler) {\n";
        ret += "\t\t\tsuper();\n";
        ret += "\t\t\tthis.condition = condition;\n";
        ret += "\t\t\tthis.value = value;\n";
        ret += "\t\t\tthis.secondValue = secondValue;\n";
        ret += "\t\t\tthis.typeHandler = typeHandler;\n";
        ret += "\t\t\tthis.betweenValue = true;\n";
        ret += "\t\t}\n";
        ret += "\t\tprotected Criterion(String condition, Object value, Object secondValue) {\n";
        ret += "\t\t\tthis(condition, value, secondValue, null);\n";
        ret += "\t\t}\n";
        ret += "\t}\n";
        ret += "}\r\n";
        return ret;
    }

    public String generateIsNull(Field field){
        String ret = "";
        ret += "\t\tpublic Criteria and"+getJavaName2(field)+"IsNull() {\n";
        ret += "\t\t\taddCriterion(\""+field.getName()+" IS NULL\");\n";
        ret += "\t\t\treturn (Criteria) this;\n";
        ret += "\t\t}\n";
        return ret;
    }
    public String generateIsNotNull(Field field){
        String ret = "";
        ret += "\t\tpublic Criteria and"+getJavaName2(field)+"IsNotNull() {\n";
        ret += "\t\t\taddCriterion(\""+field.getName()+" IS NOT NULL\");\n";
        ret += "\t\t\treturn (Criteria) this;\n";
        ret += "\t\t}\n";
        return ret;
    }
    public String generateEqualTo(Field field){
        String ret = "";
        ret += "\t\tpublic Criteria and"+getJavaName2(field)+"EqualTo("+getJavaType(field)+" value) {\n";
        ret += "\t\t\taddCriterion(\""+field.getName()+" =\", value, \""+getJavaName(field)+"\");\n";
        ret += "\t\t\treturn (Criteria) this;\n";
        ret += "\t\t}\n";
        return ret;
    }
    public String generateNotEqualTo(Field field){
        String ret = "";
        ret += "\t\tpublic Criteria and"+getJavaName2(field)+"NotEqualTo("+getJavaType(field)+" value) {\n";
        ret += "\t\t\taddCriterion(\""+field.getName()+" &lt;&gt;\", value, \""+getJavaName(field)+"\");\n";
        ret += "\t\t\treturn (Criteria) this;\n";
        ret += "\t\t}\n";
        return ret;
    }
    public String generateGreaterThan(Field field){
        String ret = "";
        ret += "\t\tpublic Criteria and"+getJavaName2(field)+"GreaterThan("+getJavaType(field)+" value) {\n";
        ret += "\t\t\taddCriterion(\""+field.getName()+" &gt;\", value, \""+getJavaName(field)+"\");\n";
        ret += "\t\t\treturn (Criteria) this;\n";
        ret += "\t\t}\n";
        return ret;
    }
    public String generateLessThan(Field field){
        String ret = "";
        ret += "\t\tpublic Criteria and"+getJavaName2(field)+"LessThan("+getJavaType(field)+" value) {\n";
        ret += "\t\t\taddCriterion(\""+field.getName()+" &lt;\", value, \""+getJavaName(field)+"\");\n";
        ret += "\t\t\treturn (Criteria) this;\n";
        ret += "\t\t}\n";
        return ret;
    }
    public String generateLessThanOrEqualTo(Field field){
        String ret = "";
        ret += "\t\tpublic Criteria and"+getJavaName2(field)+"LessThanOrEqualTo("+getJavaType(field)+" value) {\n";
        ret += "\t\t\taddCriterion(\""+field.getName()+" &gt;=\", value, \""+getJavaName(field)+"\");\n";
        ret += "\t\t\treturn (Criteria) this;\n";
        ret += "\t\t}\n";
        return ret;
    }
    public String generateLike(Field field){
        String ret = "";
        ret += "\t\tpublic Criteria and"+getJavaName2(field)+"Like("+getJavaType(field)+" value) {\n";
        ret += "\t\t\taddCriterion(\""+field.getName()+" LIKE\", value, \""+getJavaName(field)+"\");\n";
        ret += "\t\t\treturn (Criteria) this;\n";
        ret += "\t\t}\n";
        return ret;
    }
    public String generateNotLike(Field field){
        String ret = "";
        ret += "\t\tpublic Criteria and"+getJavaName2(field)+"NotLike("+getJavaType(field)+" value) {\n";
        ret += "\t\t\taddCriterion(\""+field.getName()+" NOT LIKE\", value, \""+getJavaName(field)+"\");\n";
        ret += "\t\t\treturn (Criteria) this;\n";
        ret += "\t\t}\n";
        return ret;
    }
    public String generateIn(Field field){
        String ret = "";
        ret += "\t\tpublic Criteria and"+getJavaName2(field)+"In(List<"+getJavaType(field)+"> values) {\n";
        ret += "\t\t\taddCriterion(\""+field.getName()+" IN\", values, \""+getJavaName(field)+"\");\n";
        ret += "\t\t\treturn (Criteria) this;\n";
        ret += "\t\t}\n";
        return ret;
    }
    public String generateNotIn(Field field){
        String ret = "";
        ret += "\t\tpublic Criteria and"+getJavaName2(field)+"NotIn(List<"+getJavaType(field)+"> values) {\n";
        ret += "\t\t\taddCriterion(\""+field.getName()+" NOT IN\", values, \""+getJavaName(field)+"\");\n";
        ret += "\t\t\treturn (Criteria) this;\n";
        ret += "\t\t}\n";
        return ret;
    }
    public String generateBetween(Field field){
        String ret = "";
        ret += "\t\tpublic Criteria and"+getJavaName2(field)+"Between("+getJavaType(field)+" value1, "+getJavaType(field)+" value2) {\n";
        ret += "\t\t\taddCriterion(\""+field.getName()+" BETWEEN\", value1, value2, \""+getJavaName(field)+"\");\n";
        ret += "\t\t\treturn (Criteria) this;\n";
        ret += "\t\t}\n";
        return ret;
    }
    public String generateNotBetween(Field field){
        String ret = "";
        ret += "\t\tpublic Criteria and"+getJavaName2(field)+"NotBetween("+getJavaType(field)+" value1, "+getJavaType(field)+" value2) {\n";
        ret += "\t\t\taddCriterion(\""+field.getName()+" NOT BETWEEN\", value1, value2, \""+getJavaName(field)+"\");\n";
        ret += "\t\t\treturn (Criteria) this;\n";
        ret += "\t\t}\n";
        return ret;
    }
}
