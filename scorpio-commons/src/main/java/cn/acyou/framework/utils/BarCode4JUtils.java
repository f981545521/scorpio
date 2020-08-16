package cn.acyou.framework.utils;

import org.apache.commons.lang.ObjectUtils;
import org.krysalis.barcode4j.HumanReadablePlacement;
import org.krysalis.barcode4j.impl.code128.Code128Bean;
import org.krysalis.barcode4j.impl.code39.Code39Bean;
import org.krysalis.barcode4j.impl.pdf417.PDF417Bean;
import org.krysalis.barcode4j.impl.upcean.UPCABean;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;
import org.krysalis.barcode4j.tools.UnitConv;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author youfang
 * @version [1.0.0, 2020-8-15 下午 10:32]
 **/
public class BarCode4JUtils {

    /**
     * 生成code128条形码
     *
     * @param height        条形码的高度
     * @param width         条形码的宽度
     * @param message       要生成的文本
     * @param withQuietZone 是否两边留白
     * @param hideText      隐藏可读文本
     * @return 图片对应的字节码
     */
    public static void generateBarCode128(String message, Double height, Double width, boolean withQuietZone, boolean hideText) {
        Code128Bean bean = new Code128Bean();
        // 分辨率
        int dpi = 512;
        // 设置两侧是否留白
        bean.doQuietZone(withQuietZone);

        // 设置条形码高度和宽度
        bean.setBarHeight((double) ObjectUtils.defaultIfNull(height, 9.0D));
        if (width != null) {
            bean.setModuleWidth(width);
        }
        // 设置文本位置（包括是否显示）
        if (hideText) {
            bean.setMsgPosition(HumanReadablePlacement.HRP_NONE);
        }
        // 设置图片类型
        String format = "image/png";

        try {
            FileOutputStream fileOutputStream = new FileOutputStream(new File("D:\\temp\\barcode4j.png"));
            BitmapCanvasProvider canvas = new BitmapCanvasProvider(fileOutputStream, format, dpi,
                    BufferedImage.TYPE_BYTE_BINARY, false, 0);
            // 生产条形码
            bean.generateBarcode(canvas, message);
            canvas.finish();
            fileOutputStream.close();
        } catch (IOException e) {

        }
    }
    /**
     * 生成Pdf417码
     *
     * @param message       要生成的文本
     * @return 图片对应的字节码
     */
    public static void generateBarPdf417(String message, Double height, Double width, boolean withQuietZone, boolean hideText) {
        PDF417Bean bean = new PDF417Bean();
        // 分辨率
        int dpi = 512;
        // 设置两侧是否留白
        bean.doQuietZone(withQuietZone);

        // 设置条形码高度和宽度
        bean.setBarHeight(height);
        bean.setHeight(height);
        bean.setModuleWidth(width);
        // 设置文本位置（包括是否显示）
        if (hideText) {
            bean.setMsgPosition(HumanReadablePlacement.HRP_NONE);
        }
        // 设置图片类型
        String format = "image/png";

        try {
            FileOutputStream fileOutputStream = new FileOutputStream(new File("D:\\temp\\barcode4j.png"));
            BitmapCanvasProvider canvas = new BitmapCanvasProvider(fileOutputStream, format, dpi,
                    BufferedImage.TYPE_BYTE_BINARY, false, 0);
            // 生产条形码
            bean.generateBarcode(canvas, message);
            canvas.finish();
            fileOutputStream.close();
        } catch (IOException e) {

        }
    }

    /**
     * 生成UPC码
     *
     * @param message       要生成的文本
     * @return 图片对应的字节码
     */
    public static void generateBarUpc(String message, Double height, Double width, boolean withQuietZone, boolean hideText) {
        UPCABean bean = new UPCABean();
        // 分辨率
        int dpi = 512;
        // 设置两侧是否留白
        bean.doQuietZone(withQuietZone);

        // 设置条形码高度和宽度
        bean.setBarHeight((double) ObjectUtils.defaultIfNull(height, 9.0D));
        if (width != null) {
            bean.setModuleWidth(width);
        }
        // 设置文本位置（包括是否显示）
        if (hideText) {
            bean.setMsgPosition(HumanReadablePlacement.HRP_NONE);
        }
        // 设置图片类型
        String format = "image/png";

        try {
            FileOutputStream fileOutputStream = new FileOutputStream(new File("D:\\temp\\barcode4j.png"));
            BitmapCanvasProvider canvas = new BitmapCanvasProvider(fileOutputStream, format, dpi,
                    BufferedImage.TYPE_BYTE_BINARY, false, 0);
            // 生产条形码
            bean.generateBarcode(canvas, message);
            canvas.finish();
            fileOutputStream.close();
        } catch (IOException e) {

        }
    }

    public static void testCode39(){
        try {
            //Create the barcode bean
            Code39Bean bean = new Code39Bean();

            final int dpi = 150;

            //Configure the barcode generator
            bean.setModuleWidth(UnitConv.in2mm(1.0f / dpi)); //makes the narrow bar
            //width exactly one pixel
            bean.setWideFactor(3);
            bean.doQuietZone(false);

            //Open output file
            File outputFile = new File("D:\\temp\\barcode4j.png");
            OutputStream out = new FileOutputStream(outputFile);
            try {
                //Set up the canvas provider for monochrome JPEG output
                BitmapCanvasProvider canvas = new BitmapCanvasProvider(
                        out, "image/jpeg", dpi, BufferedImage.TYPE_BYTE_BINARY, false, 0);

                //Generate the barcode
                bean.generateBarcode(canvas, "123456");

                //Signal end of generation
                canvas.finish();
            } finally {
                out.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void testPdf417Bean(String content, int width, int height) throws Exception{
        PDF417Bean bean = new PDF417Bean();
        bean.doQuietZone(true);//两边留白
        bean.setQuietZone(20);//两边留白宽度
        bean.setVerticalQuietZone(50);//留白垂直的宽度

        bean.setHeight(250);//设置条形码的全高。??
        bean.setBarHeight(210);//码高度
        bean.setModuleWidth(30);//宽度
        //bean.setWidthToHeightRatio(5);//宽高比？?
        bean.setMsgPosition(HumanReadablePlacement.HRP_BOTTOM);

        //FileOutputStream os = new FileOutputStream(new File("D:\\temp\\barcode4j.png"));


        BitmapCanvasProvider canvas = new BitmapCanvasProvider(128,
                BufferedImage.TYPE_BYTE_BINARY, false, 0);
        bean.generateBarcode(canvas, content);
        canvas.finish();
        BufferedImage image = canvas.getBufferedImage();

        BufferedImage finalImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = finalImage.createGraphics();
        g2.setBackground(Color.WHITE);
        g2.clearRect(0, 0, width, height);
        g2.drawImage(image, 0, 0, width, height - 25, null); //这里减去25是为了防止字和图重合
        /* 设置生成图片的文字样式 * */
        Font font = new Font("黑体", Font.BOLD, 25);
        g2.setFont(font);
        g2.setPaint(Color.BLACK);
        /* 设置字体在图片中的位置 在这里是居中* */
        FontRenderContext context = g2.getFontRenderContext();
        Rectangle2D bounds = font.getStringBounds(content, context);
        double x = (width - bounds.getWidth()) / 2;
        //double y = (height - bounds.getHeight()) / 2; //Y轴居中
        double y = (height - bounds.getHeight());
        double ascent = -bounds.getY();
        double baseY = y + ascent;
        /* 防止生成的文字带有锯齿 * */
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        /* 在图片上生成文字 * */
        g2.drawString(content, (int) x, (int) baseY);

        boolean write = ImageIO.write(finalImage, "png", new File("D:\\temp\\pdf417_"+content+".png"));
        System.out.println(write);

    }


    public static void main(String[] args) throws Exception{
        //generateBarCode128("4305383450594", 10.00, 0.3, true, false);
        //generateBarUpc("22222223457", 10.00, 0.3, true, false);
        //generateBarPdf417("22222223457", 10.00, 50.00, true, false);
        testPdf417Bean("1lIi2zZ0oOD", 1000, 300);
    }


}
