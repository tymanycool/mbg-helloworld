package com.tiany.ibator.impl.executor;

import com.tiany.ibator.common.meta.Field;
import com.tiany.ibator.common.meta.SQL;
import com.tiany.ibator.common.meta.Table;
import com.tiany.ibator.util.ListUtil;
import com.tiany.util.CollectionUtil;
import com.tiany.util.MapUtil;
import com.tiany.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Create sql 执行器
 */
@Component
public class CreateTableExecutor extends AbstractRemoveCommentExecutor {
    private List<List<String>> data;
    private Table table;
    @Autowired
    private Map<String, String> typesConfig;
    @Autowired
    private Map<String, String> tibatisConfig;


    @Override
    protected void handle(Table table, List<String> words) {
        data = new ArrayList<>();
        this.table = table;
        int begin = words.indexOf("(");
        int end = words.lastIndexOf(")");
        List<String> list1 = words.subList(0, begin + 1);
        List<String> list2 = words.subList(begin + 1, end);
        List<String> list3 = words.subList(end, words.size());

        List<List<String>> split = CollectionUtil.split(list2, (obj, nexObj) -> {
            return obj.equals(",");
        });
        list1.addAll(list3);
        data.add(list1);
        data.addAll(split);

        parseData(data);
    }

    @Override
    public boolean accept(SQL sql) {
        if (!sql.isCleared()) {
            clear(sql);
        }
        List<String> words = sql.getSqlWords();
        if (!CollectionUtil.containsIgnoreCase(words, "CREATE")) {
            return false;
        }
        if (!CollectionUtil.containsIgnoreCase(words, "TABLE")) {
            return false;
        }
        return true;
    }

    @Override
    public String getTableName(SQL sql) {
        if (!sql.isCleared()) {
            clear(sql);
        }
        List<String> sqlWords = sql.getSqlWords();
        if (accept(sql)) {
            String table = ListUtil.getAfter(sqlWords, "TABLE", 1);
            return table;
        }
        return null;
    }

    private void findTableNameAndComment(List<List<String>> data) {
        String removePrefix = tibatisConfig.get("removePrefix");
        String prefix = tibatisConfig.get("prefix");
        String suffix = tibatisConfig.get("suffix");
        // 第一行存放的是表的信息
        List<String> row = data.get(0);
        boolean initName = false;
        boolean initComment = false;
        for (int j = 0; j + 2 < row.size() && (initComment == false || initName == false); j++) {
            if (initName == false && "TABLE".equals(row.get(j + 1).toUpperCase()) && StringUtil.containsIgnoreCase(row, "CREATE")) {
                table.setName(row.get(j + 2));
                initName = true;
            }
            if (initComment == false && "COMMENT".equals(row.get(j).toUpperCase())) {
                String after = ListUtil.getAfter(row, row.get(j), 2);
                if (after.startsWith("'")) {
                    after = after.substring(1);
                }
                if (after.endsWith("'")) {
                    after = after.substring(0, after.length() - 1);
                }
                table.setComment(after);
                initComment = true;
            } else if ("-".equals(row.get(j)) && j + 2 < row.size() && "-".equals(row.get(j + 1))) {
                table.setComment(row.get(j + 2));
                initComment = true;
            }
        }
        // 处理entity的名字
        table.setEntityName(StringUtil.getCamelClassName(table.getName().toLowerCase()));
        if (StringUtil.isEmpty(removePrefix)) {
            return;
        }
        String[] split = removePrefix.split(",");
        for (String s : split) {
            if (StringUtil.isNotEmpty(s)) {
                // 忽略大小写
                if (StringUtil.startsWithIgnoreCase(table.getName(), s)) {
                    table.setEntityName(StringUtil.getCamelClassName(table.getName().substring(s.length())));
                    break;
                }
            }
        }
        if (StringUtil.isNotEmpty(prefix)) {
            table.setEntityName(StringUtil.getCamelClassName(prefix) + table.getEntityName());
        }
        if (StringUtil.isNotEmpty(suffix)) {
            table.setEntityName(table.getEntityName() + StringUtil.getCamelClassName(suffix));
        }
    }

    private void parseData(List<List<String>> data) {
        Map map = (Map) typesConfig;
        findTableNameAndComment(data);
        for (int i = 0; i < data.size(); i++) {
            List<String> row = data.get(i);
            if (ListUtil.contains(row, map)) {
                Field field = new Field();
                for (int j = 0; j < row.size(); j++) {
                    if (MapUtil.containsKeyIgnoreCase(map, row.get(j))) {
                        String pre = ListUtil.getPre(row, row.get(j));
                        field.setName(pre);
                        field.setType(row.get(j));
                    }

                }
                for (int j = 0; j < row.size(); j++) {
                    if ("COMMENT".equals(row.get(j).toUpperCase())) {
                        String after = ListUtil.getAfter(row, row.get(j));
                        field.setComment(after);
                    } else if ("-".equals(row.get(j)) && j + 2 < row.size() && "-".equals(row.get(j + 1))) {
                        field.setComment(row.get(j + 2));
                    }
                }
                table.getFields().add(field);
            } else if (StringUtil.containsIgnoreCase(row, "PRIMARY")) {
                for (int j = 0; j < row.size(); j++) {
                    if ("PRIMARY".equals(row.get(j).toUpperCase())) {
                        // 找到主键的名字
                        String after = ListUtil.getAfter(row, row.get(j), 3);
                        Field primaryKey = getPrimaryKey(table.getFields(), after);
                        table.getPrimaryKeys().add(primaryKey);
                    }
                }
            }

        }
    }

    /**
     * 通过名字得到属性对象
     *
     * @param fields
     * @param name
     * @return
     */
    private Field getPrimaryKey(List<Field> fields, String name) {
        for (Field field : fields) {
            if (StringUtil.equalsIgnoreCase(field.getName(), name)) {
                return field;
            }
        }
        return null;
    }

}
