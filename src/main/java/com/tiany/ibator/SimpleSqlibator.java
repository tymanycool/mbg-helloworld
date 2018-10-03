package com.tiany.ibator;

import com.tiany.ibator.impl.EntityExampleGenerator;
import com.tiany.ibator.impl.EntityGenerator;
import com.tiany.ibator.inf.DaoGenerator;
import com.tiany.ibator.inf.DaoImplGenerator;
import com.tiany.ibator.inf.MapperGenerator;
import com.tiany.ibator.meta.Field;
import com.tiany.ibator.meta.Table;
import com.tiany.inf.Convert;
import com.tiany.util.CollectionUtil;
import com.tiany.util.MapUtil;
import com.tiany.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 通过CREATE TABLE SQL 语句生成实体类及sqlMapper.xml
 * @author tiany
 * @version 1.0
 */
@Component
//@Scope("prototype")
public class SimpleSqlibator extends AbstractBaseSqlibator implements Convert {

    private List<List<String>> data;

    private Table table;

    @Autowired
    private EntityExampleGenerator entityExampleGenerator;

    @Autowired
    private EntityGenerator entityGenerator;

    @Autowired
    private DaoGenerator daoGenerator;

    @Autowired
    private DaoImplGenerator daoImplGenerator;

    @Autowired
    private MapperGenerator mapperGenerator;

    @Override
    public Object convert(Object in) {

        if (in instanceof String) {
            String inStr = (String) in;
            inStr = inStr.trim();
            String outStr = "";
            // 转换成一行
            inStr = inStr.replaceAll("\r\n", " ");
            inStr = inStr.replaceAll("\n", " ");
            inStr = inStr.replaceAll("\r", " ");
            initData(inStr);
            parseData(data);
            Map<String, Object> map = new HashMap<>();
            outStr = entityExampleGenerator.generate(table);
            map.put("entityExample",outStr);
            outStr = entityGenerator.generate(table);
            map.put("entity",outStr);
            outStr = mapperGenerator.generate(table);
            map.put("xml",outStr);
            if(generateInterface) {
                outStr = daoGenerator.generate(table);
                map.put("dao", outStr);
                outStr = daoImplGenerator.generate(table);
                map.put("daoImpl",outStr);
            }else{
                outStr = daoImplGenerator.generate(table);
                map.put("dao",outStr);
            }
            return map;
        }
        return null;
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
        table = new Table();
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

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }
}
