package com.tiany.ibator.common;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tiany.ibator.common.meta.Remark;
import com.tiany.ibator.common.meta.Table;
import com.tiany.ibator.infs.ClassComment;
import com.tiany.util.format.JsonUtil;
import com.tiany.util.io.FileUtil;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

@Component
public class RemarkHelper extends BaseComponent {

    public String getClassRemark(ClassComment classComment, Table table) {
        Remark remark = null;
        try {
            File file = new File("history.json");
            if (!file.exists()) {
                file.createNewFile();
                remark = classComment.remark(table, null);
                // 写入文件
                FileUtil.write(file, JsonUtil.obj2FormatJson(remark.getConfig()));
            } else {
                String read = FileUtil.read(file);
                JSONObject histroy = (JSONObject) JSON.parse(read);
                remark = classComment.remark(table, histroy);
                histroy.putAll(remark.getConfig());
                // 写入文件
                FileUtil.write(file, JsonUtil.obj2FormatJson(histroy));
            }
        } catch (IOException e) {
            logger.error("版本读取或者操作失败{}", e.getMessage());
            remark = classComment.remark(table, null);
            try {
                FileUtil.write("hsitory-error.json", JsonUtil.obj2FormatJson(remark.getConfig()));
            } catch (IOException e1) {
                logger.error("版本补救措施未能生效：{}", e1.getMessage());
            }
        }
        if (remark == null) {
            throw new RuntimeException("版本异常");
        }
        String retString = remark.getRemark();
        if (!retString.endsWith("\\n")) {
            retString += "\n";
        }
        return retString;
    }

}
