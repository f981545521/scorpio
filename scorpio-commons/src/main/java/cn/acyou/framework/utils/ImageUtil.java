package cn.acyou.framework.utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


/**
 * 在图片下面追加文字
 *
 * @author youfang
 * @version [1.0.0, 2020-8-16 下午 03:43]
 **/
public class ImageUtil {

    /**
     * @param str
     *            生产的图片文字
     * @param oldPath
     *            原图片保存路径
     * @param newPath
     *            新图片保存路径
     * @param width
     *            定义生成图片宽度
     * @param height
     *            定义生成图片高度
     * @return
     * @throws IOException
     */
    public void create(String str, String oldPath, String newPath, int width, int height){
        try {
            File oldFile = new File(oldPath);
            Image image = ImageIO.read(oldFile);

            File file = new File(newPath);
            BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2 = bi.createGraphics();
            g2.setBackground(Color.WHITE);
            g2.clearRect(0, 0, width, height);
            g2.drawImage(image, 0, 0, width, height - 25, null); //这里减去25是为了防止字和图重合
            /** 设置生成图片的文字样式 * */
            Font font = new Font("黑体", Font.BOLD, 25);
            g2.setFont(font);
            g2.setPaint(Color.BLACK);

            /** 设置字体在图片中的位置 在这里是居中* */
            FontRenderContext context = g2.getFontRenderContext();
            Rectangle2D bounds = font.getStringBounds(str, context);
            double x = (width - bounds.getWidth()) / 2;
            //double y = (height - bounds.getHeight()) / 2; //Y轴居中
            double y = (height - bounds.getHeight());
            double ascent = -bounds.getY();
            double baseY = y + ascent;

            /** 防止生成的文字带有锯齿 * */
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

            /** 在图片上生成文字 * */
            g2.drawString(str, (int) x, (int) baseY);

            ImageIO.write(bi, "png", file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        try {
            ImageUtil img = new ImageUtil();
            img.create("asdafdsr0011", "D:\\temp\\barcode4j.png", "D:\\temp\\barcode4j_IMG.png", 1000, 300);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
