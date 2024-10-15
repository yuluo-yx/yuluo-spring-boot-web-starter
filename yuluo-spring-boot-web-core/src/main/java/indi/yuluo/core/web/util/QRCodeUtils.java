package indi.yuluo.core.web.util;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Hashtable;

public class QRCodeUtils {

    private static final String CHARSET = "utf-8";

    private static final String FORMAT_NAME = "JPG";

    // 二维码尺寸
    private static final int QRCODE_SIZE = 300;

    // LOGO宽度
    private static final int WIDTH = 60;

    // LOGO高度
    private static final int HEIGHT = 60;

    // 保存文件路径
    private final static String SAVE_CODE_PATH = "garage/images/qr/";

    /**
     * @param content      二维码内容
     * @param imgPath      logo图片地址
     * @param needCompress 是否需要压缩
     */
    private static BufferedImage createImage(String content, String imgPath,
                                             boolean needCompress) throws Exception {

        Hashtable<EncodeHintType, Object> hints = new Hashtable<>();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        hints.put(EncodeHintType.CHARACTER_SET, CHARSET);

        // 文本编码格式
        hints.put(EncodeHintType.MARGIN, 1);

        BitMatrix bitMatrix = new MultiFormatWriter().encode(
                content,
                BarcodeFormat.QR_CODE,
                QRCODE_SIZE, QRCODE_SIZE,
                hints
        );

        // 设置生成二维码的大小
        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();
        BufferedImage image = new BufferedImage(
                width, height, BufferedImage.TYPE_INT_RGB);

        // 设置二维码中logo的大小
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(
                        x, y, bitMatrix.get(x, y) ?
                                0xFF000000 :
                                0xFFFFFFFF
                );
            }
        }
        if (imgPath == null || imgPath.isEmpty()) {
            return image;
        }

        // 插入图片
        QRCodeUtils.insertImage(image, imgPath, needCompress);
        return image;
    }

    /**
     * 插入LOGO
     * @param source       二维码图片
     * @param imgPath      LOGO图片地址
     * @param needCompress 是否压缩
     */
    private static void insertImage(BufferedImage source, String imgPath,
                                    boolean needCompress) throws Exception {

        File file = new File(imgPath);
        if (!file.exists()) {
            System.err.println(imgPath + " 该文件不存在！");
            return;
        }

        Image src = ImageIO.read(new File(imgPath));
        int width = src.getWidth(null);
        int height = src.getHeight(null);

        if (needCompress) {
            if (width > WIDTH) {
                width = WIDTH;
            }
            if (height > HEIGHT) {
                height = HEIGHT;
            }
            Image image = src.getScaledInstance(width, height,
                    Image.SCALE_SMOOTH);
            BufferedImage tag = new BufferedImage(width, height,
                    BufferedImage.TYPE_INT_RGB);
            Graphics g = tag.getGraphics();
            g.drawImage(image, 0, 0, null); // 绘制缩小后的图
            g.dispose();
            src = image;
        }

        // 插入LOGO
        Graphics2D graph = source.createGraphics();
        int x = (QRCODE_SIZE - width) / 2;
        int y = (QRCODE_SIZE - height) / 2;
        graph.drawImage(src, x, y, width, height, null);
        Shape shape = new RoundRectangle2D.Float(x, y, width, width, 6, 6);
        graph.setStroke(new BasicStroke(3f));
        graph.draw(shape);
        graph.dispose();
    }

    /**
     * 生成二维码，有 logo
     * @param content      内容
     * @param logo      LOGO地址
     * @param output       输出流
     * @param needCompress 是否压缩LOGO
     */
    public static void encode(String content, String logo,
                              OutputStream output, boolean needCompress) throws Exception {
        BufferedImage image = QRCodeUtils.createImage(content, logo,
                needCompress);
        // 输出二维码图片流
        ImageIO.write(image, FORMAT_NAME, output);
        // 保存到本地
        save(image, content);
    }

    /**
     * 生成二维码，无 logo
     * @param content 内容
     * @param output  输出流
     */
    public static void encode(String content, OutputStream output) throws Exception {

        QRCodeUtils.encode(content, null, output, false);
    }

    /**
     * 解析二维码
     * @param file 二维码图片
     */
    public static String decode(File file) throws Exception {

        BufferedImage image;
        image = ImageIO.read(file);
        if (image == null) {
            return null;
        }

        BufferedImageLuminanceSource source = new BufferedImageLuminanceSource(
                image);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
        Result result;
        Hashtable<DecodeHintType, Object> hints = new Hashtable<>();
        hints.put(DecodeHintType.CHARACTER_SET, CHARSET);
        hints.put(DecodeHintType.TRY_HARDER, Boolean.TRUE);
        hints.put(DecodeHintType.PURE_BARCODE, Boolean.TRUE);
        result = new MultiFormatReader().decode(bitmap, hints);

        return result.getText();
    }

    /**
     * 解析二维码
     * @param path 二维码图片地址
     */
    public static String decode(String path) throws Exception {

        return QRCodeUtils.decode(new File(path));
    }

    /**
     * 保存二维码到文件系统
     */
    public static void save(BufferedImage image, String name) throws IOException {

        File files = new File(SAVE_CODE_PATH);
        if (!files.exists() && !files.isDirectory()) {
            files.mkdirs();
        }
        ImageIO.write(image, FORMAT_NAME, new File(SAVE_CODE_PATH + "/" + name + ".jpg"));
    }

}
