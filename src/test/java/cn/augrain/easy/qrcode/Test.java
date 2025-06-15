package cn.augrain.easy.qrcode;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Test {

    public static void main(String[] args) {
        String content = "https://www.augrain.com"; // 二维码内容
        int size = 200; // 二维码尺寸
        String filePath = "colorful_qrcode.png"; // 输出文件路径

        try {
            BufferedImage qrCodeImage = generateColorfulQRCode(content, size);
            ImageIO.write(qrCodeImage, "PNG", new File(filePath));
            System.out.println("彩色二维码生成成功！");
        } catch (WriterException | IOException e) {
            e.printStackTrace();
        }
    }

    public static BufferedImage generateColorfulQRCode(String content, int size) throws WriterException {
        // 设置二维码参数
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        hints.put(EncodeHintType.MARGIN, 1);

        // 生成二维码矩阵
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, size, size, hints);

        // 创建彩色图像
        BufferedImage image = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = image.createGraphics();
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, size, size);

        // 定义颜色
        Color mainColor = new Color(0, 102, 204); // 主色 - 蓝色
        Color finderPatternColor = new Color(204, 0, 102); // 定位点颜色 - 粉红色
        Color innerFinderColor = new Color(51, 204, 51); // 内部定位点颜色 - 绿色

        int startIndex = detectStart(bitMatrix);
        int detectCornerSize = detectSize(bitMatrix, startIndex, startIndex);

        // 绘制二维码
        for (int x = startIndex; x < size - startIndex; x++) {
            for (int y = startIndex; y < size - startIndex; y++) {
                if (bitMatrix.get(x, y)) {
                    // 检查是否是定位点（三个角上的大方块）
                    if (isInFinderPattern(x, y, size, 7)) {
                        System.out.println("----");
                        // 在定位点区域使用特殊颜色和样式
                        if (isInInnerFinderPattern(x, y, size, 7)) {
                            graphics.setColor(innerFinderColor);
                        } else {
                            graphics.setColor(finderPatternColor);
                        }
                    } else {
                        // 其他区域使用渐变色
                        float hue = (float) (x + y) / (size * 2);
                        graphics.setColor(Color.getHSBColor(hue, 0.8f, 0.8f));
                    }
                    graphics.fillRect(x, y, 1, 1);
                }
            }
        }

        // 自定义定位点样式 - 替换为圆形
        customizeFinderPatterns(graphics, bitMatrix, size, 7);

        graphics.dispose();
        return image;
    }

    private static int detectStart(BitMatrix bitMatrix) {
        int i = 0;
        while (!bitMatrix.get(i, i)) {
            i++;
        }
        return i;
    }

    private static int detectSize(BitMatrix bitMatrix, int startX, int startY) {
        int temp = startX;
        while (bitMatrix.get(temp, startY)) {
            temp++;
        }
        return temp - startX;
    }


    // 检查是否在定位点区域
    private static boolean isInFinderPattern(int x, int y, int size, int finderPatternSize) {
        // 左上角定位点
        if (x < finderPatternSize && y < finderPatternSize) return true;
        // 右上角定位点
        if (x < finderPatternSize && y >= size - finderPatternSize) return true;
        // 左下角定位点
        if (x >= size - finderPatternSize && y < finderPatternSize) return true;
        return false;
    }

    // 更准确的定位点内部检测方法
    private static boolean isInInnerFinderPattern(int x, int y, int size, int finderPatternSize) {
        // 左上角内部 (2,2) 到 (4,4)
        if (x >= 2 && x <= 4 && y >= 2 && y <= 4 &&
                x < finderPatternSize && y < finderPatternSize) return true;

        // 右上角内部 (2, size-5) 到 (4, size-3)
        if (x >= 2 && x <= 4 && y >= size - 5 && y <= size - 3 &&
                x < finderPatternSize) return true;

        // 左下角内部 (size-5, 2) 到 (size-3, 4)
        if (x >= size - 5 && x <= size - 3 && y >= 2 && y <= 4 &&
                y < finderPatternSize) return true;

        return false;
    }

    // 更精确的定位点绘制方法
    private static void customizeFinderPatterns(Graphics2D graphics, BitMatrix bitMatrix,
                                                int size, int finderPatternSize) {
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // 左上角定位点
        drawCustomFinderPattern(graphics, 0, 0, finderPatternSize);

        // 右上角定位点
        drawCustomFinderPattern(graphics, 0, size - finderPatternSize, finderPatternSize);

        // 左下角定位点
        drawCustomFinderPattern(graphics, size - finderPatternSize, 0, finderPatternSize);
    }

    // 改进的定位点绘制方法
    private static void drawCustomFinderPattern(Graphics2D graphics, int x, int y, int size) {
        // 绘制外框 (7x7)
        graphics.setColor(new Color(255, 0, 0)); // 红色外框
        graphics.fillRect(x, y, size, size);

        // 中间白色层 (5x5)
        graphics.setColor(Color.WHITE);
        graphics.fillRect(x + 1, y + 1, size - 2, size - 2);

        // 内层黑色 (3x3)
        graphics.setColor(Color.BLACK);
        graphics.fillRect(x + 2, y + 2, size - 4, size - 4);

        // 中心点 (1x1)
        graphics.setColor(Color.WHITE);
        graphics.fillRect(x + 3, y + 3, 1, 1);
    }
}