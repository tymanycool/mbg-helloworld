package com.tiany.ibator.impl.mapper;

import com.tiany.ibator.AbstractBaseSqlibator;
import com.tiany.ibator.infs.MapperDeleteGenerator;
import com.tiany.ibator.common.meta.Field;
import com.tiany.ibator.common.meta.Table;
import com.tiany.util.StringUtil;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DeleteByParamsMapperGenerator extends AbstractBaseSqlibator implements MapperDeleteGenerator {
    @Override
    public String getDeleteId() {
        return "deleteByParams";
    }
    @Override
    public String generate(Table table) {
        String ret = "<delete id=\""+getDeleteId()+"\"  parameterClass=\"java.util.Map\" >\n";
        ret += "\tDELETE FROM ";
        ret += table.getName() +" \n";
        List<Field> fields = table.getFields();
        ret += "\t<dynamic prepend=\"where\" >\n";
        for(int i =0;i<fields.size();i++){
            ret += "\t\t<"+getPropertyDynamicLabel(fields.get(i))+" prepend=\"and\" property=\""+ StringUtil.getCamelProperty(fields.get(i).getName())+"\" >";
            ret += " "+fields.get(i).getName()+" = #"+StringUtil.getCamelProperty(fields.get(i).getName())+"#";
            ret += " </"+getPropertyDynamicLabel(fields.get(i))+">\n";
        }
        ret += "\t</dynamic>\n";
        ret += "</delete>\n";
        return ret;
    }
}
