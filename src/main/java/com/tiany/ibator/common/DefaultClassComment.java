package com.tiany.ibator.common;

import com.alibaba.fastjson.JSONObject;
import com.tiany.ibator.common.meta.Field;
import com.tiany.ibator.common.meta.Remark;
import com.tiany.ibator.common.meta.Table;
import com.tiany.ibator.impl.comment.BaseComment;
import com.tiany.ibator.infs.ClassComment;
import com.tiany.util.DateUtil;
import com.tiany.util.MapUtil;
import com.tiany.util.StringUtil;
import com.tiany.util.math.NumberUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Entity注释模板
 */
@Component
public class DefaultClassComment extends BaseComment implements ClassComment {
    @Autowired
    private Map<String, String> tibatisConfig;

    protected String getTypeDesc() {
        return "";
    }
    protected void updateInfo(Table table,List<String> updateDescs) {

    }

    @Override
    public Remark remark(Table table, Map history) {
        if (history == null || history.isEmpty()) {
            // 没有历史记录
            return getCreatedRemark(table);
        } else {
            return getUpdateRemark(table, history);
        }
    }

    private Remark getUpdateRemark(Table table, Map history) {
        if (history == null || history.isEmpty()) {
            return getCreatedRemark(table);
        }
        Map entityHistory = (Map) history.get(table.getEntityName());
        if (entityHistory == null || entityHistory.isEmpty()) {
            return getCreatedRemark(table);
        }
        Remark remark = new Remark();
        List<Map<String, Object>> tableFields = (List<Map<String, Object>>) entityHistory.get("tableFields");
        List<Field> fields = table.getFields();
        List<Map<String, Object>> newTableFields = MapUtil.objectsToMaps(fields);
        List modifiedHistory = (List) entityHistory.get("modifiedHistory");

        List<String> updateDescs = new ArrayList<>();
        // 计算字段的变化
        List<String> tableUpdateDescs = resolveTableUpdateDesc(table.getComment(), (String) entityHistory.get("comment"));
        List<String> fieldsUpdateDescs = resolveFieldsUpdateDesc(newTableFields, tableFields);
        updateDescs.addAll(tableUpdateDescs);
        updateDescs.addAll(fieldsUpdateDescs);

        updateInfo(table,updateDescs);

        // 字段变化则版本增加，其他情况暂时忽略
        if (updateDescs.size() > 0) {
            double version = Double.parseDouble((String) entityHistory.get("version"));
            // 每次版本增加0.1
            entityHistory.put("version", NumberUtil.round(version + 0.1, 1));

            // 更新 modifiedHistory
            Map map = new JSONObject(true);
            map.put("modifiedDescs", updateDescs);
            map.put("modifiedDate", DateUtil.thisDate());
            map.put("author", System.getProperty("user.name"));
            modifiedHistory.add(map);
        }


        String productInfo = tibatisConfig.get("productInfo");
        String[] infos = productInfo.split("\\n");
        String ret = "";
        ret += "/**\n";
        ret += " * " + getCommentString(table.getComment()) + getTypeDesc() + "\n";
        ret += " * " + table.getEntityName() + getTypeDesc() + " .\n";
        ret += " * @author " + entityHistory.get("author") + "\n";
        ret += " * <pre>\n";
        ret += " *   Created on " + entityHistory.get("createDate") + " \n";
        ret += " *   Modification class history\n";
        ret += " *   {\n";
        for (int i = 0; i < modifiedHistory.size(); i++) {
            Map historyMap = (Map) modifiedHistory.get(i);
            String author = (String) historyMap.get("author");
            String modifiedDate = (String) historyMap.get("modifiedDate");
            List<String> modifiedDescs = (List<String>) historyMap.get("modifiedDescs");
            ret += " *     " + (i + 1) + "、Modified on " + modifiedDate + " @author " + author;
            if (modifiedDescs.size() > 1) {
                ret += " : 详情如下\n";
                for (String desc : modifiedDescs) {
                    ret += " *       " + desc + "\n";
                }
            } else if (modifiedDescs.size() == 1) {
                ret += " : " + modifiedDescs.get(0) + "\n";
            }
        }
        ret += " *   }\n";
        ret += " * </pre>\n";
        ret += " * <pre>\n";
        for (String info : infos) {
            ret += " *   " + info + "\n";
        }
        ret += " * </pre>\n";
        ret += " * @version " + entityHistory.get("version") + "\n";
        ret += " * @since 1.0\n";
        ret += " */";
        entityHistory.put("comment", table.getComment());
        entityHistory.put("tableFields", table.getFields());
        remark.setRemark(ret);
        remark.setConfig(history);
        return remark;
    }

    public static void main(String[] args) {
        List<Map<String, Object>> list1 = new ArrayList<>();
        List<Map<String, Object>> list2 = new ArrayList<>();


        // old
        Map<String, Object> map1 = new HashMap<>();
        Map<String, Object> map2 = new HashMap<>();
        Map<String, Object> map3 = new HashMap<>();

        // new
        Map<String, Object> map4 = new HashMap<>();
        Map<String, Object> map5 = new HashMap<>();
        Map<String, Object> map6 = new HashMap<>();
        Map<String, Object> map7 = new HashMap<>();


        map1.put("name", "id");
        map1.put("type", "int");
        map1.put("comment", "ID");

        map2.put("name", "cust_name");
        map2.put("type", "sring");
        map2.put("comment", "客户姓名");


        map3.put("name", "age");
        map3.put("type", "int");
        map3.put("comment", "年龄");

        list1.add(map1);
        list1.add(map2);
        list1.add(map3);


        map4.put("name", "id2");
        map4.put("type", "int");
        map4.put("comment", "ID");

        map5.put("name", "cust_name");
        map5.put("type", "int");
        map5.put("comment", "客户姓名3");


        map6.put("name", "age");
        map6.put("type", "int");
        map6.put("comment", "年龄");

        map7.put("name", "name");
        map7.put("type", "string");
        map7.put("comment", "姓名");

        list2.add(map4);
        list2.add(map5);
        list2.add(map6);
        list2.add(map7);

        List<String> strings = resolveFieldsUpdateDesc(list1, list2);


    }

    /**
     * 返回字段的更新描述
     *
     * @param newFields
     * @param oldFields
     * @return
     */
    public static List<String> resolveFieldsUpdateDesc(List<Map<String, Object>> newFields, List<Map<String, Object>> oldFields) {
        List<String> ret = new ArrayList<>();
        //减少的字段
        List<Map> list1 = new ArrayList<>();
        //增加的字段
        List<Map> list2 = new ArrayList<>();

        // 新的里面已存在
        Map<String, Map> newMap = new HashMap<>();
        // 旧的里面已存在
        Map<String, Map> oldMap = new HashMap<>();

        filter(newFields, oldFields, list1, oldMap);
        filter(oldFields, newFields, list2, newMap);

        for (Map map : list1) {
            Object name = map.get("name");
            ret.add("删除字段：" + name);
        }
        for (Map map : list2) {
            Object name = map.get("name");
            ret.add("增加字段：" + name);
        }

        Iterator<Map.Entry<String, Map>> iterator = newMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Map> next = iterator.next();
            String name = next.getKey();
            Map om = oldMap.get(name);
            Map nm = newMap.get(name);

            String omtype = StringUtil.toStringAndTrim(om.get("type")).toUpperCase();
            String omcomment = StringUtil.toStringAndTrim(om.get("comment"));
            String nmtype = StringUtil.toStringAndTrim(nm.get("type")).toUpperCase();
            String nmcomment = StringUtil.toStringAndTrim(nm.get("comment"));
            //  更新字段
            if (omtype.equals(nmtype) && omcomment.equals(nmcomment)) {
                continue;
            }
            String s = "字段更新：";
            if (!omtype.equals(nmtype)) {
                s += name + "类型更新; ";
            }
            if (!omcomment.equals(nmcomment)) {
                s += name + "描述更新; ";
            }
            ret.add(s);
        }
        return ret;
    }

    /**
     * 返回表的更新描述
     *
     * @return
     */
    public static List<String> resolveTableUpdateDesc(String newComment, String oldComment) {
        List<String> ret = new ArrayList<>();
        String s = StringUtil.toStringAndTrim(newComment);
        if (!s.equals(oldComment)) {
            ret.add("更新表描述; ");
        }
        return ret;
    }


    private static void filter(List<Map<String, Object>> sourceMapList, List<Map<String, Object>> referMapList, List<Map> diffList, Map<String, Map> commonMap) {
        for (Map rm : referMapList) {
            String name = StringUtil.toStringAndTrim(rm.get("name"));
            String type = StringUtil.toStringAndTrim(rm.get("type"));
            String comment = StringUtil.toStringAndTrim(rm.get("comment"));
            boolean find = false;
            for (Map sm : sourceMapList) {
                if (name.equals(sm.get("name"))) {
                    find = true;
                    break;
                }
            }
            if (!find) {
                // 该字段已经删除
                diffList.add(rm);
            } else {
                // 该字段是已有的
                commonMap.put(name, rm);
            }
        }
    }


    private Remark getCreatedRemark(Table table) {
        Remark remark = new Remark();
        Map config = new JSONObject(true);
        Map entityConfig = new JSONObject(true);
        List<Map> modifiedHistory = new ArrayList<>();
        Map historyMap = new JSONObject(true);
        List<String> modifiedDescs = new ArrayList<>();
        String productInfo = tibatisConfig.get("productInfo");
        String[] infos = productInfo.split("\\n");
        String ret = "";
        ret += "/**\n";
        ret += " * " + getCommentString(table.getComment()) + getTypeDesc() + "\n";
        ret += " * " + table.getEntityName() + getTypeDesc() + " .\n";
        ret += " * @author " + System.getProperty("user.name") + "\n";
        ret += " * <pre>\n";
        ret += " *   Created on " + DateUtil.thisDate() + " \n";
        ret += " *   Modification class history\n";
        ret += " *   {\n";
        ret += " *     1、Modified on " + DateUtil.thisDate() + " @author " + System.getProperty("user.name") + " : Created\n";
        ret += " *   }\n";
        ret += " * </pre>\n";
        ret += " * <pre>\n";
        for (String info : infos) {
            ret += " *   " + info + "\n";
        }
        ret += " * </pre>\n";
        ret += " * @version 1.0\n";
        ret += " * @since 1.0\n";
        ret += " */";

        entityConfig.put("comment", table.getComment());
        entityConfig.put("author", System.getProperty("user.name"));
        entityConfig.put("createDate", DateUtil.thisDate());
        entityConfig.put("version", "1.0");
        entityConfig.put("since", "1.0");
        entityConfig.put("tableFields", table.getFields());

        modifiedDescs.add("Created");

        historyMap.put("modifiedDescs", modifiedDescs);
        historyMap.put("modifiedDate", DateUtil.thisDate());
        historyMap.put("author", System.getProperty("user.name"));
        modifiedHistory.add(historyMap);
        entityConfig.put("modifiedHistory", modifiedHistory);
        config.put(table.getEntityName(), entityConfig);
        remark.setRemark(ret);
        remark.setConfig(config);
        return remark;
    }
}
