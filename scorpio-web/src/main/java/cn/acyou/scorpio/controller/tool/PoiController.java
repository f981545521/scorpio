package cn.acyou.scorpio.controller.tool;

import cn.acyou.framework.model.Result;
import cn.acyou.framework.utils.DateUtil;
import cn.acyou.framework.utils.RandomUtil;
import cn.acyou.scorpio.dto.demo.StudentExportEntity;
import cn.acyou.scorpio.dto.demo.StudentExportEntityGroup;
import cn.afterturn.easypoi.entity.vo.NormalExcelConstants;
import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author youfang
 * @version [1.0.0, 2018-10-26 上午 10:24]
 **/
@Slf4j
@Controller
@RequestMapping("poi")
public class PoiController {

    @RequestMapping(value = "simpleExport", method = {RequestMethod.GET})
    @ApiOperation("easy poi 简单List导出")
    public void simpleExport(ModelMap map, HttpServletResponse response) throws Exception {
        List<StudentExportEntity> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            StudentExportEntity client = new StudentExportEntity();
            client.setId("2" + i);
            client.setName("小明" + i);
            client.setBirthday(DateUtil.randomDate());
            client.setRegistrationDate(DateUtil.parseDate("2010-12-08", "yyyy-MM-dd"));
            client.setSex(RandomUtil.random01());
            StudentExportEntityGroup group = new StudentExportEntityGroup();
            group.setGroupName("测试" + i);
            client.setGroup(group);
            list.add(client);
        }
        ExportParams params = new ExportParams("2412312", "测试", ExcelType.XSSF);
        params.setFreezeCol(2);
        map.put(NormalExcelConstants.DATA_LIST, list); // 数据集合
        map.put(NormalExcelConstants.CLASS, StudentExportEntity.class);//导出实体
        map.put(NormalExcelConstants.PARAMS, params);//参数
        map.put(NormalExcelConstants.FILE_NAME, "测试文件");//文件名称

        //方式1 ： View 返回
        //PoiBaseView.render(map, request, response, NormalExcelConstants.EASYPOI_EXCEL_VIEW);
        //方式2 ： 直接保存文件
        //Workbook workbook = ExcelExportUtil.exportExcel(params, StudentExportEntity.class, list );
        //FileOutputStream os = new FileOutputStream("F:\\iotest\\测试.xlsx");
        //workbook.write(os);
        //os.close();
        Workbook workbook = ExcelExportUtil.exportExcel(params, StudentExportEntity.class, list);
        workbook.write(response.getOutputStream());
    }


    @ApiOperation("easy poi 导入")
    @RequestMapping(value = "import", method = {RequestMethod.POST})
    @ResponseBody
    public Result<?> importExcel(MultipartFile file) throws Exception {
        ImportParams params = new ImportParams();
        params.setTitleRows(1);
        params.setHeadRows(1);
        InputStream fileStream = file.getInputStream();
        List<StudentExportEntity> list = ExcelImportUtil.importExcel(fileStream, StudentExportEntity.class, params);
        System.out.println(list.size());
        System.out.println(ReflectionToStringBuilder.toString(list.get(0)));
        return Result.success();
    }

}
