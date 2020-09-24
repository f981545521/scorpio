package cn.acyou.scorpio.test.informal;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author youfang
 * @version [1.0.0, 2020-9-3 下午 07:28]
 **/
public class TemplateReplaceTest {

    public static void main(String[] args) {
        String templateContent = "{供应商名称} 对 {业务员} 的销售授权已经到期，请更新最新的证件信息。";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("供应商名称", "hhh");
        paramMap.put("业务员", "王二小");
        Set<Map.Entry<String, Object>> entrySet = paramMap.entrySet();
        for (Map.Entry<String, Object> entry : entrySet) {
            if (entry.getValue() == null) {
                continue;
            }
            if (templateContent.indexOf(entry.getKey()) > 0) {
                templateContent = templateContent.replace("{" + entry.getKey() + "}", entry.getValue().toString());
            }
        }
        System.out.println(templateContent);
    }
}
