package cn.acyou.scorpio.controller.tool;

import cn.acyou.framework.model.Result;
import cn.acyou.framework.utils.printer.PrinterUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.print.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author youfang
 * @version [1.0.0, 2020/12/8]
 **/
@Slf4j
@RestController
@RequestMapping("printer")
@Api(value = "打印", tags = "打印接口")
public class PrinterController {

    @PostMapping(value = "/findAllPrinter")
    @ApiOperation(value = "查询所有打印机与默认打印机")
    public Result<?> findAllPrinter() {
        PrintService[] printServices = PrintServiceLookup.lookupPrintServices(null, null);
        System.out.println("查询所有打印机");
        for (PrintService printService : printServices) {
            System.out.println(printService);
        }
        PrintService printService = PrintServiceLookup.lookupDefaultPrintService();
        System.out.println("默认配置为：" + printService);
        List<String> nameCollect = Arrays.stream(printServices).map(PrintService::getName).collect(Collectors.toList());
        return Result.success(nameCollect);
    }
    @PostMapping(value = "/print")
    @ApiOperation(value = "查询所有打印机与默认打印机")
    public Result<?> print(@RequestParam("name") String goodName, @RequestParam("printerName") String printerName) {
        String strData = PrinterUtil.formatGoodsCode(goodName);
        PrintService printService = null;
        if (StringUtils.isNotEmpty(printerName)){
            PrintService[] printServices = PrintServiceLookup.lookupPrintServices(null, null);
            for (PrintService service : printServices) {
                if (service.getName().equals(printerName)){
                    printService = service;
                }
            }
            if (printService == null){
                return Result.error("未找到指定打印机：" + printerName);
            }
        }else {
            printService = PrintServiceLookup.lookupDefaultPrintService();
        }

        DocPrintJob printJob = printService.createPrintJob();
        byte[] by = PrinterUtil.str2bytes(strData);
        DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
        Doc doc = new SimpleDoc(by, flavor, null);
        try {
            printJob.print(doc, null);
        } catch (PrintException e) {
            e.printStackTrace();
            return Result.error(e.getMessage());
        }
        return Result.success();
    }
}
