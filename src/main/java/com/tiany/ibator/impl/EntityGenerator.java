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
public class EntityGenerator extends AbstractBaseSqlibator implements Generator {
    private Random random = new Random();

    @Override
    public String generate(Table table) {
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
            ret += "\tprivate " + getSimpleClassName((String) MapUtil.getIgnoreCase((Map) props,fields.get(i).getType())) +" " + StringUtil.getCamelProperty(fields.get(i).getName()) + ";\r\n";
        }
        ret += "\r\n";
        ret += generateGetterSetter(table);
        ret += generateToString(table);
        ret += "}\r\n";
        return ret;
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
}
