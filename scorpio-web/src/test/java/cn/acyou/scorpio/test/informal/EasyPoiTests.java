package cn.acyou.scorpio.test.informal;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.TemplateExportParams;
import com.google.common.io.Resources;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author youfang
 * @version [1.0.0, 2021/1/5]
 **/
public class EasyPoiTests {
    @Test
    public void testExportTemplate() throws Exception {
        URL resource = Resources.getResource("poi_example/settlement_example.xls");
        TemplateExportParams params = new TemplateExportParams(resource.getPath());
        Map<String, Object> map = new HashMap<>();
        String settlementNo = "JS2020122700005";
        map.put("enterpriseName", "江苏省口腔医院");
        map.put("settlementNo", settlementNo);
        map.put("settlementType", "货票同行");
        Workbook workbook = ExcelExportUtil.exportExcel(params, map);
        File savefile = new File("D:/excel/");
        if (!savefile.exists()) {
            savefile.mkdirs();
        }
        FileOutputStream fos = new FileOutputStream("D:/excel/结算单" + settlementNo + ".xls");
        workbook.write(fos);
        fos.close();
    }

    @Test
    public void testReadExample(){
        URL resource = Resources.getResource("poi_example/settlement_example.xls");
        File file = new File(resource.getPath());
        System.out.println(file.exists());
    }


    @Test
    public void testExportTemplate2() throws Exception {
        URL resource = Resources.getResource("poi_example/settlement_example.xls");
        TemplateExportParams params = new TemplateExportParams(resource.getPath());
        Map<String, Object> map = new HashMap<>();
        String settlementNo = "JS2020122700013";
        map.put("enterpriseName", "江苏省口腔医院");
        map.put("settlementNo", settlementNo);
        map.put("settlementType", "货票同行");
        map.put("invoiceNo", "FPPPPPP00001");
        map.put("invoiceDate", "2021-01-01");
        map.put("invoicePrice", "2000.00");
        map.put("settlementStatus", "已确认");
        map.put("invoiceDescription", "OK");
        map.put("allSumPrice", 2000000.00);

        List<Map<String, String>> listMap = new ArrayList<>();
        for (int i = 1; i < 10; i++) {
            Map<String, String> lm = new HashMap<>();
            lm.put("id", i + 1 + "");
            lm.put("productName", i  + "物品");
            lm.put("specModal", "A001");
            lm.put("fixModal", "10个/包");
            lm.put("number", "10");
            lm.put("price", "10.00");
            lm.put("registrationCertificateNo", "10121300" + i);
            lm.put("totalPrice", i * 10000 + "");
            listMap.add(lm);
        }
        map.put("maplist", listMap);

        Workbook workbook = ExcelExportUtil.exportExcel(params, map);
        File savefile = new File("D:/excel/");
        if (!savefile.exists()) {
            savefile.mkdirs();
        }
        FileOutputStream fos = new FileOutputStream("D:/excel/结算单" + settlementNo + ".xls");
        workbook.write(fos);
        fos.close();
    }



}
