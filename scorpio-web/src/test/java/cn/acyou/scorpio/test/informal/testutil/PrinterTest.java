package cn.acyou.scorpio.test.informal.testutil;

import cn.acyou.framework.utils.printer.PrinterUtil;

import javax.print.*;

/**
 * @author youfang
 * @version [1.0.0, 2020/12/9]
 **/
public class PrinterTest {

    public static void main(String[] args) {
        PrintService printService = PrintServiceLookup.lookupDefaultPrintService();
        String strData = PrinterUtil.formatGoodsCode(
                "眼科手术钩-物品条码",
                "100/400-1",
                "GK888T",
                "",
                "353454612342");
        DocPrintJob printJob = printService.createPrintJob();
        byte[] by = PrinterUtil.str2bytes(strData);
        DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
        Doc doc = new SimpleDoc(by, flavor, null);
        try {
            printJob.print(doc, null);
        } catch (PrintException e) {
            e.printStackTrace();
        }
    }
}
