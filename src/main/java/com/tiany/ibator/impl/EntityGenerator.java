package com.tiany.ibator.impl;

import com.tiany.ibator.AbstractBaseSqlibator;
import com.tiany.ibator.common.RemarkHelper;
import com.tiany.ibator.impl.comment.EntityClassComment;
import com.tiany.ibator.impl.comment.EntityExampleClassComment;
import com.tiany.ibator.infs.Generator;
import com.tiany.ibator.common.meta.Field;
import com.tiany.ibator.common.meta.Table;
import com.tiany.ibator.util.SerializableNoUtil;
import com.tiany.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class EntityGenerator extends AbstractBaseSqlibator implements Generator {
    private Random random = new Random();
    @Autowired
    private EntityClassComment classComment;
    @Autowired
    private RemarkHelper remarkHelper;

    @Override
    public String generate(Table table) {
        String entityPackageName = tibatisConfig.get("entityPackageName");
        String ret = "";
        ret += "package " + entityPackageName + ";\r\n\r\n";

        List<String> imports = new ArrayList<>();
        imports.add("java.io.Serializable");
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
//        ret += "/**\r\n";
//        ret += " * " + getCommentString(table.getComment()) + " .\r\n";
//        ret += " * @author " + System.getProperty("user.name") + "\r\n";
//        ret += " * @version " + DateUtil.thisDate() + " modify: " + System.getProperty("user.name") + "\r\n";
//        ret += " * @since 1.0\r\n";
//        ret += " */\n";
        ret += "public class " + table.getEntityName() + " implements Serializable {\r\n";
        ret += "  /** 序列化号 . */\r\n";
        ret += "  private static final long serialVersionUID = " + SerializableNoUtil.getSerializableNo(table.getEntityName(), "" + Math.abs(random.nextLong())) + "L;\r\n";
        List<Field> fields = table.getFields();
        for (int i = 0; i < fields.size(); i++) {
            ret += "  /** " + getCommentString(fields.get(i).getComment()) + " . */\r\n";
            // TODO TIANYAO
            ret += "  private " + getSimpleClassName((String) MapUtil.getIgnoreCase((Map) typesConfig, fields.get(i).getType())) + " " + StringUtil.getCamelProperty(fields.get(i).getName()) + ";\r\n";
        }
        ret += "\r\n";
        ret += generateGetterSetter(table);
        ret += generateGetPrimaryKey(table);
        ret += generateToString(table);
        ret += "}\r\n";
        return ret;
    }

    private String generateGetterSetter(Table table) {
        List<Field> fields = table.getFields();
        String ret = "";
        for (Field field : fields) {
            ret += generateGetter(field);
            ret += generateSetter(field);
        }
        return ret;
    }

    /**
     * 生成getter方法
     *
     * @param field
     * @return
     */
    private String generateGetter(Field field) {
        String ret = "";
        String camelProperty = StringUtil.getCamelProperty(field.getName());
        ret += "  public " + getSimpleClassName((String) MapUtil.getIgnoreCase((Map) typesConfig, field.getType())) + " " + StringUtil.getCamelProperty("get_" + field.getName()) + "() {\r\n";
        ret += "    return " + "this." + camelProperty + ";\r\n";
        ret += "  }\r\n\r\n";
        return ret;
    }

    /**
     * 生成getter方法
     *
     * @param table
     * @return
     */
    private String generateGetPrimaryKey(Table table) {
        String ret = "";
        List<Field> primaryKeys = table.getPrimaryKeys();
        if (primaryKeys != null && primaryKeys.size() == 1) {
            ret += "  public String getPrimaryKey() {\r\n";
            ret += "    return \"" + StringUtil.getCamelProperty(primaryKeys.get(0).getName()) + "\";\r\n";
            ret += "  }\r\n\r\n";
        }

        return ret;
    }

    /**
     * 生成setter方法
     *
     * @param field
     * @return
     */
    private String generateSetter(Field field) {
        String ret = "";
        String camelProperty = StringUtil.getCamelProperty(field.getName());
        ret += "  public void " + StringUtil.getCamelProperty("set_" + field.getName()) + "(" + getSimpleClassName((String) MapUtil.getIgnoreCase((Map) typesConfig, field.getType())) + " " + camelProperty + ") {\r\n";
        ret += "    this." + camelProperty + " = " + camelProperty + ";\r\n";
        ret += "  }\r\n\r\n";
        return ret;
    }

    /**
     * 生成toString
     *
     * @param table
     * @return
     */
    private String generateToString(Table table) {
        List<Field> fields = table.getFields();
        String ret = "";
        ret += "  @Override\r\n";
        ret += "  public String toString() {\r\n";
        ret += "    final StringBuilder sb = new StringBuilder(\"" + table.getEntityName() + "{\");\r\n";
        for (int i = 0; i < fields.size(); i++) {
            Field field = fields.get(i);
            if (i != fields.size() - 1) {
                ret += "    sb.append(\"" + StringUtil.getCamelProperty(field.getName()) + "='\").append(" + StringUtil.getCamelProperty(field.getName()) + ").append('\\'');\r\n";
            } else {
                ret += "    sb.append(\"" + StringUtil.getCamelProperty(field.getName()) + "='\").append(" + StringUtil.getCamelProperty(field.getName()) + ").append('\\'');\r\n";
            }
        }
        ret += "    sb.append('}');  \n";
        ret += "    return sb.toString();\r\n";
        ret += "  }\r\n";
        return ret;
    }
}
