package com.tiany.ibator;

import com.tiany.ibator.common.meta.Field;
import com.tiany.ibator.common.meta.Table;
import com.tiany.util.MapUtil;
import com.tiany.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

public abstract class AbstractBaseSqlibator extends AbstractSqlibator {
    @Autowired
    protected Map<String,String> typesConfig;
    public AbstractBaseSqlibator() {
    }
    protected boolean hasClass(Table table,String clazz){
        List<Field> fields = table.getFields();
        for(Field field : fields){
            String type = (String) typesConfig.get(field.getType().toLowerCase());
            if(StringUtil.isNotEmpty(type)){
                if(type.endsWith(clazz)){
                    return true;
                }
            }
        }
        return false;
    }
    protected boolean hasPrimatyKey(Table table){
        if(table.getPrimaryKeys().size()>=1){
            return true;
        }
        return false;
    }

    protected String getPropertyDynamicLabel(Field field){
        if(StringUtil.equalsIgnoreCase(field.getType(),"varchar")||StringUtil.equalsIgnoreCase(field.getType(),"char")){
            return "isNotEmpty";
        }
        return "isNotNull";
    }

    /**
     * 得到简单类名
     * @param name
     * @return
     */
    protected String getSimpleClassName(String name){
        if(name == null){
            return null;
        }
        int index = name.lastIndexOf(".");
        if(index == -1){
            return name;
        }
        return name.substring(index+1);
    }

    protected String getBeanNameByClassName(String className){
        return className.substring(0,1).toLowerCase()+className.substring(1);
    }

    /**
     * 处理注释
     * @param comment
     * @return
     */
    protected String getCommentString(String comment){
        if(comment != null) {
            if (comment.startsWith("'")) {
                comment = comment.substring(1);
            }
            if (comment.endsWith("'")) {
                comment = comment.substring(0, comment.length() - 1);
            }
            comment = comment.replaceAll("\t"," ");
            comment = comment.replaceAll("\r"," ");
            comment = comment.replaceAll("\n"," ");

            comment = comment.replaceAll("\\t"," ");
            comment = comment.replaceAll("\\r"," ");
            comment = comment.replaceAll("\\n"," ");

            comment = comment.replaceAll("\\\\t"," ");
            comment = comment.replaceAll("\\\\r"," ");
            comment = comment.replaceAll("\\\\n"," ");
            comment = comment.replaceAll("[\\s]+"," ");

        }else {
            return "";
        }
        return comment;
    }




    /**
     * 得到java类型
     * @param field
     * @return
     */
    protected String getJavaType(Field field){
        return getSimpleClassName((String) MapUtil.getIgnoreCase((Map) typesConfig,field.getType()));
    }

    /**
     * 得到javaName
     * @param field
     * @return
     */
    protected String getJavaName(Field field){
        return StringUtil.getCamelProperty(field.getName());
    }
    /**
     * 得到javaName
     * @param field
     * @return
     */
    protected String getJavaName2(Field field){
        String camelProperty = StringUtil.getCamelProperty(field.getName());
        return camelProperty.substring(0,1).toUpperCase()+camelProperty.substring(1);
    }

}
