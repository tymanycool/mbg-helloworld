package com.tiany.ibator.impl;

import com.tiany.ibator.AbstractBaseSqlibator;
import com.tiany.ibator.common.RemarkHelper;
import com.tiany.ibator.impl.comment.DaoImplClassComment;
import com.tiany.ibator.impl.comment.EntityExampleClassComment;
import com.tiany.ibator.infs.Generator;
import com.tiany.ibator.common.meta.Field;
import com.tiany.ibator.common.meta.Table;
import com.tiany.util.CastUtil;
import com.tiany.util.DateUtil;
import com.tiany.ibator.util.SerializableNoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class EntityExampleGenerator extends AbstractBaseSqlibator implements Generator {
    private Random random = new Random();
    @Autowired
    private EntityExampleClassComment classComment;
    @Autowired
    private RemarkHelper remarkHelper;

    @Override
    public String generate(Table table) {
        String entityPackageName = tibatisConfig.get("entityPackageName");
        boolean generatePage = CastUtil.castBoolean(tibatisConfig.get("generatePage"));
        String ret = "";
        ret += "package " + entityPackageName + ";\n\n";

        List<String> imports = new ArrayList<>();
        imports.add("java.io.Serializable");
        imports.add("java.util.ArrayList");
        imports.add("java.util.List");
        if (hasClass(table, "BigInteger")) {
            imports.add("java.math.BigInteger");
        }
        if (hasClass(table, "Date")) {
            imports.add("java.util.Date");
        }
        if (hasClass(table, "BigDecimal")) {
            imports.add("java.math.BigDecimal");
        }
        Collections.sort(imports);

        for (String s : imports) {
            ret += "import " + s + ";\n";
        }
        ret += "\n";
        String classRemark = remarkHelper.getClassRemark(classComment, table);
        ret += classRemark;
//        ret += "/**\n";
//        ret += " * " + getCommentString(table.getComment()) + "Example .\n";
//        ret += " * @author " + System.getProperty("user.name") + "\n";
//        ret += " * @version " + DateUtil.thisDate() + " modify: " + System.getProperty("user.name") + "\n";
//        ret += " * @since 1.0\n";
//        ret += " */\n";
        ret += "public class " + table.getEntityName() + "Example implements Serializable {\n";
        ret += "  /** 序列化号 . */\n";
        ret += "  private static final long serialVersionUID = " + SerializableNoUtil.getSerializableNo(table.getEntityName() + "Example", "" + Math.abs(random.nextLong())) + "L;\n";
        ret += "  protected String orderByClause;\n";
        ret += "  protected boolean distinct;\n";
        ret += "  private List<Criteria> oredCriteria;\n";
        if (generatePage) {
            ret += "  protected int pageNo;\n";
            ret += "  protected int pageSize;\n";
            ret += "  protected int limit;\n\n";
        }
        ret += "  public " + table.getEntityName() + "Example() {\n";
        ret += "    oredCriteria = new ArrayList<>();\n";
        ret += "  }\n\n";
        ret += "  public void setOrderByClause(String orderByClause) {\n";
        ret += "    this.orderByClause = orderByClause;\n";
        ret += "  }\n\n";
        ret += "  public String getOrderByClause() {\n";
        ret += "    return orderByClause;\n";
        ret += "  }\n\n";
        ret += "  public void setDistinct(boolean distinct) {\n";
        ret += "    this.distinct = distinct;\n";
        ret += "  }\n\n";
        ret += "  public boolean isDistinct() {\n";
        ret += "    return distinct;\n";
        ret += "  }\n\n";
        if (generatePage) {
            ret += "  public int getPageNo() {\n";
            ret += "    return pageNo;\n";
            ret += "  }\n\n";
            ret += "  public void setPageNo(int pageNo) {\n";
            ret += "    this.pageNo = pageNo;\n";
            ret += "  }\n\n";
            ret += "  public int getPageStartIndex() {\n";
            ret += "    return (pageNo - 1) * pageSize;\n";
            ret += "  }\n\n";
            ret += "  public int getPageSize() {\n";
            ret += "    return pageSize;\n";
            ret += "  }\n\n";
            ret += "  public void setPageSize(int pageSize) {\n";
            ret += "    this.pageSize = pageSize;\n";
            ret += "  }\n\n";
            ret += "  public int getLimit() {\n";
            ret += "    return limit;\n";
            ret += "  }\n\n";
            ret += "  public void setLimit(int limit) {\n";
            ret += "    this.limit = limit;\n";
            ret += "  }\n\n";
        }
        ret += "  public List<Criteria> getOredCriteria() {\n";
        ret += "    return oredCriteria;\n";
        ret += "  }\n\n";
        ret += "  /** or . **/\n";
        ret += "  public void or(Criteria criteria) {\n";
        ret += "    oredCriteria.add(criteria);\n";
        ret += "  }\n\n";
        ret += "  /** or . **/\n";
        ret += "  public Criteria or() {\n";
        ret += "    Criteria criteria = createCriteriaInternal();\n";
        ret += "    oredCriteria.add(criteria);\n";
        ret += "    return criteria;\n";
        ret += "  }\n\n";
        ret += "  /** createCriteria . **/\n";
        ret += "  public Criteria createCriteria() {\n";
        ret += "    Criteria criteria = createCriteriaInternal();\n";
        ret += "    if (oredCriteria.isEmpty()) {\n";
        ret += "      oredCriteria.add(criteria);\n";
        ret += "    }\n";
        ret += "    return criteria;\n";
        ret += "  }\n\n";
        ret += "  protected Criteria createCriteriaInternal() {\n";
        ret += "    return new Criteria();\n";
        ret += "  }\n\n";

        ret += "  /** clear . **/\n";
        ret += "  public void clear() {\n";
        ret += "    oredCriteria.clear();\n";
        ret += "    orderByClause = null;\n";
        ret += "    distinct = false;\n";
        ret += "  }\n\n";
        ret += "\n";
        ret += "  protected abstract static class GeneratedCriteria implements Serializable {\n";
        ret += "    /** 序列化号 . */\n";
        ret += "    private static final long serialVersionUID = " + SerializableNoUtil.getSerializableNo(table.getEntityName() + "Example.GeneratedCriteria", "" + Math.abs(random.nextLong())) + "L;\n\n";
        ret += "    protected List<Criterion> criteria;\n\n";
        ret += "    protected GeneratedCriteria() {\n";
        ret += "      super();\n";
        ret += "      criteria = new ArrayList<>();\n";
        ret += "    }\n\n";
        ret += "    public boolean isValid() {\n";
        ret += "      return !criteria.isEmpty();\n";
        ret += "    }\n\n";
        ret += "    public List<Criterion> getAllCriteria() {\n";
        ret += "      return criteria;\n";
        ret += "    }\n\n";
        ret += "    public List<Criterion> getCriteria() {\n";
        ret += "      return criteria;\n";
        ret += "    }\n\n";
        ret += "    protected void addCriterion(String condition) {\n";
        ret += "      if (condition == null) {\n";
        ret += "        throw new IllegalArgumentException(\"Value for condition cannot be null\");\n";
        ret += "      }\n";
        ret += "      criteria.add(new Criterion(condition));\n";
        ret += "    }\n\n";
        ret += "    protected void addCriterion(String condition, Object value, String property) {\n";
        ret += "      if (value == null) {\n";
        ret += "        throw new IllegalArgumentException(\"Value for \" + property + \" cannot be null\");\n";
        ret += "      }\n";
        ret += "      criteria.add(new Criterion(condition, value));\n";
        ret += "    }\n\n";
        ret += "    protected void addCriterion(String condition, Object value1, Object value2, String property) {\n";
        ret += "      if (value1 == null || value2 == null) {\n";
        ret += "        throw new IllegalArgumentException(\"Between values for \" + property + \" cannot be null\");\n";
        ret += "      }\n";
        ret += "      criteria.add(new Criterion(condition, value1, value2));\n";
        ret += "    }\n\n";


        // 生成属性相关的条件
        for (Field field : table.getFields()) {
            ret += generateIsNull(field);
            ret += "\n";
            ret += generateIsNotNull(field);
            ret += "\n";
            ret += generateEqualTo(field);
            ret += "\n";
            ret += generateNotEqualTo(field);
            ret += "\n";
            ret += generateLessThan(field);
            ret += "\n";
            ret += generateLessThanOrEqualTo(field);
            ret += "\n";
            ret += generateGreaterThan(field);
            ret += "\n";
            ret += generateGreaterThanOrEqualTo(field);
            ret += "\n";
            ret += generateLike(field);
            ret += "\n";
            ret += generateNotLike(field);
            ret += "\n";
            ret += generateIn(field);
            ret += "\n";
            ret += generateNotIn(field);
            ret += "\n";
            ret += generateBetween(field);
            ret += "\n";
            ret += generateNotBetween(field);
            ret += "\n";
        }

        ret += "  }\n\n";


        ret += "  public static class Criteria extends GeneratedCriteria {\n";
        ret += "    /** 序列化号 . */\n";
        ret += "    private static final long serialVersionUID = " + SerializableNoUtil.getSerializableNo(table.getEntityName() + "Example.Criteria", "" + Math.abs(random.nextLong())) + "L;\n\n";
        ret += "    protected Criteria() {\n";
        ret += "      super();\n";
        ret += "    }\n";
        ret += "  }\n\n";
        ret += "  public static class Criterion implements Serializable {\n";
        ret += "    /** 序列化号 . */\n";
        ret += "    private static final long serialVersionUID = " + SerializableNoUtil.getSerializableNo(table.getEntityName() + "Example.Criterion", "" + Math.abs(random.nextLong())) + "L;\n\n";
        ret += "    private String condition;\n";
        ret += "    private Object value;\n";
        ret += "    private Object secondValue;\n";
        ret += "    private boolean noValue;\n";
        ret += "    private boolean singleValue;\n";
        ret += "    private boolean betweenValue;\n";
        ret += "    private boolean listValue;\n\n";
        ret += "    private String typeHandler;\n\n";
        ret += "    public String getCondition() {\n";
        ret += "      return condition;\n";
        ret += "    }\n\n";
        ret += "    public Object getValue() {\n";
        ret += "      return value;\n";
        ret += "    }\n\n";
        ret += "    public Object getSecondValue() {\n";
        ret += "      return secondValue;\n";
        ret += "    }\n\n";
        ret += "    public boolean isNoValue() {\n";
        ret += "      return noValue;\n";
        ret += "    }\n\n";
        ret += "    public boolean isSingleValue() {\n";
        ret += "      return singleValue;\n";
        ret += "    }\n\n";
        ret += "    public boolean isBetweenValue() {\n";
        ret += "      return betweenValue;\n";
        ret += "    }\n\n";
        ret += "    public boolean isListValue() {\n";
        ret += "      return listValue;\n";
        ret += "    }\n\n";
        ret += "    public String getTypeHandler() {\n";
        ret += "      return typeHandler;\n";
        ret += "    }\n\n";
        ret += "    protected Criterion(String condition) {\n";
        ret += "      super();\n";
        ret += "      this.condition = condition;\n";
        ret += "      this.typeHandler = null;\n";
        ret += "      this.noValue = true;\n";
        ret += "    }\n\n";
        ret += "    protected Criterion(String condition, Object value, String typeHandler) {\n";
        ret += "      super();\n";
        ret += "      this.condition = condition;\n";
        ret += "      this.value = value;\n";
        ret += "      this.typeHandler = typeHandler;\n";
        ret += "      if (value instanceof List<?>) {\n";
        ret += "        this.listValue = true;\n";
        ret += "      } else {\n";
        ret += "        this.singleValue = true;\n";
        ret += "      }\n";
        ret += "    }\n\n";
        ret += "    protected Criterion(String condition, Object value) {\n";
        ret += "      this(condition, value, null);\n";
        ret += "    }\n\n";
        ret += "    protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {\n";
        ret += "      super();\n";
        ret += "      this.condition = condition;\n";
        ret += "      this.value = value;\n";
        ret += "      this.secondValue = secondValue;\n";
        ret += "      this.typeHandler = typeHandler;\n";
        ret += "      this.betweenValue = true;\n";
        ret += "    }\n\n";
        ret += "    protected Criterion(String condition, Object value, Object secondValue) {\n";
        ret += "      this(condition, value, secondValue, null);\n";
        ret += "    }\n\n";
        ret += "  }\n\n";
        ret += "}\n";
        return ret;
    }

    public String generateIsNull(Field field) {
        String ret = "";
        ret += "    public Criteria and" + getJavaName2(field) + "IsNull() {\n";
        ret += "      addCriterion(\"" + field.getName() + " IS NULL\");\n";
        ret += "      return (Criteria) this;\n";
        ret += "    }\n";
        return ret;
    }

    public String generateIsNotNull(Field field) {
        String ret = "";
        ret += "    public Criteria and" + getJavaName2(field) + "IsNotNull() {\n";
        ret += "      addCriterion(\"" + field.getName() + " IS NOT NULL\");\n";
        ret += "      return (Criteria) this;\n";
        ret += "    }\n";
        return ret;
    }

    public String generateEqualTo(Field field) {
        String ret = "";
        ret += "    public Criteria and" + getJavaName2(field) + "EqualTo(" + getJavaType(field) + " value) {\n";
        ret += "      addCriterion(\"" + field.getName() + " =\", value, \"" + getJavaName(field) + "\");\n";
        ret += "      return (Criteria) this;\n";
        ret += "    }\n";
        return ret;
    }

    public String generateNotEqualTo(Field field) {
        String ret = "";
        ret += "    public Criteria and" + getJavaName2(field) + "NotEqualTo(" + getJavaType(field) + " value) {\n";
        ret += "      addCriterion(\"" + field.getName() + " !=\", value, \"" + getJavaName(field) + "\");\n";
        ret += "      return (Criteria) this;\n";
        ret += "    }\n";
        return ret;
    }

    public String generateGreaterThan(Field field) {
        String ret = "";
        ret += "    public Criteria and" + getJavaName2(field) + "GreaterThan(" + getJavaType(field) + " value) {\n";
        ret += "      addCriterion(\"" + field.getName() + " >\", value, \"" + getJavaName(field) + "\");\n";
        ret += "      return (Criteria) this;\n";
        ret += "    }\n";
        return ret;
    }

    public String generateGreaterThanOrEqualTo(Field field) {
        String ret = "";
        ret += "    public Criteria and" + getJavaName2(field) + "GreaterThanOrEqualTo(" + getJavaType(field) + " value) {\n";
        ret += "      addCriterion(\"" + field.getName() + " >=\", value, \"" + getJavaName(field) + "\");\n";
        ret += "      return (Criteria) this;\n";
        ret += "    }\n";
        return ret;
    }

    public String generateLessThan(Field field) {
        String ret = "";
        ret += "    public Criteria and" + getJavaName2(field) + "LessThan(" + getJavaType(field) + " value) {\n";
        ret += "      addCriterion(\"" + field.getName() + "  <\", value, \"" + getJavaName(field) + "\");\n";
        ret += "      return (Criteria) this;\n";
        ret += "    }\n";
        return ret;
    }

    public String generateLessThanOrEqualTo(Field field) {
        String ret = "";
        ret += "    public Criteria and" + getJavaName2(field) + "LessThanOrEqualTo(" + getJavaType(field) + " value) {\n";
        ret += "      addCriterion(\"" + field.getName() + "  <=\", value, \"" + getJavaName(field) + "\");\n";
        ret += "      return (Criteria) this;\n";
        ret += "    }\n";
        return ret;
    }

    public String generateLike(Field field) {
        String ret = "";
        ret += "    public Criteria and" + getJavaName2(field) + "Like(" + getJavaType(field) + " value) {\n";
        ret += "      addCriterion(\"" + field.getName() + " LIKE\", value, \"" + getJavaName(field) + "\");\n";
        ret += "      return (Criteria) this;\n";
        ret += "    }\n";
        return ret;
    }

    public String generateNotLike(Field field) {
        String ret = "";
        ret += "    public Criteria and" + getJavaName2(field) + "NotLike(" + getJavaType(field) + " value) {\n";
        ret += "      addCriterion(\"" + field.getName() + " NOT LIKE\", value, \"" + getJavaName(field) + "\");\n";
        ret += "      return (Criteria) this;\n";
        ret += "    }\n";
        return ret;
    }

    public String generateIn(Field field) {
        String ret = "";
        ret += "    public Criteria and" + getJavaName2(field) + "In(List<" + getJavaType(field) + "> values) {\n";
        ret += "      addCriterion(\"" + field.getName() + " IN\", values, \"" + getJavaName(field) + "\");\n";
        ret += "      return (Criteria) this;\n";
        ret += "    }\n";
        return ret;
    }

    public String generateNotIn(Field field) {
        String ret = "";
        ret += "    public Criteria and" + getJavaName2(field) + "NotIn(List<" + getJavaType(field) + "> values) {\n";
        ret += "      addCriterion(\"" + field.getName() + " NOT IN\", values, \"" + getJavaName(field) + "\");\n";
        ret += "      return (Criteria) this;\n";
        ret += "    }\n";
        return ret;
    }

    public String generateBetween(Field field) {
        String ret = "";
        ret += "    public Criteria and" + getJavaName2(field) + "Between(" + getJavaType(field) + " value1, " + getJavaType(field) + " value2) {\n";
        ret += "      addCriterion(\"" + field.getName() + " BETWEEN\",";
        if (field.getName().length() > 20) {
            ret += "\n         ";
        }
        ret += " value1, value2, \"" + getJavaName(field) + "\");\n";

        ret += "      return (Criteria) this;\n";
        ret += "    }\n";
        return ret;
    }

    public String generateNotBetween(Field field) {
        String ret = "";
        ret += "    public Criteria and" + getJavaName2(field) + "NotBetween(" + getJavaType(field) + " value1, " + getJavaType(field) + " value2) {\n";
        ret += "      addCriterion(\"" + field.getName() + " NOT BETWEEN\",";
        if (field.getName().length() > 20) {
            ret += "\n         ";
        }
        ret += " value1, value2, \"" + getJavaName(field) + "\");\n";
        ret += "      return (Criteria) this;\n";
        ret += "    }\n";
        return ret;
    }
}
