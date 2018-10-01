package com.tiany.ibator;

import com.tiany.ibator.meta.Field;
import com.tiany.ibator.meta.Table;
import com.tiany.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Properties;

public abstract class AbstractBaseSqlibator extends AbstractSqlibator {
    private static final Logger logger = LoggerFactory.getLogger(AbstractBaseSqlibator.class);
    protected Properties props = new Properties();

    public AbstractBaseSqlibator() {
        try {
            props.load(this.getClass().getClassLoader().getResourceAsStream("type.properties"));
        }catch (Exception e){
            logger.error("type.properties加载失败");
        }
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
        }else {
            return "";
        }
        return comment;
    }


    /**
     * 得到name之后的，偏移offset
     * @param list
     * @param name
     * @param offset
     * @return
     */
    protected String getAfter(List<String> list, String name, int offset){
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
    protected String getPre(List<String> list,String name,int offset){
        for(int i =0;i<list.size();i++){
            if(list.get(i).equals(name)){
                if(i-offset>=0) {
                    return list.get(i - offset);
                }else{
                    return null;
                }
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
    protected String getPre(List<String> list,String name){
        return getPre(list,name,1);
    }
    /**
     * 得到name后一个
     * @param list
     * @param name
     * @return
     */
    protected String getAfter(List<String> list,String name){
        return getAfter(list,name,1);
    }

}
