package cn.augrain.easy.qrcode.core;

import cn.augrain.easy.qrcode.config.QrCodeConfig;
import cn.augrain.easy.qrcode.wrapper.BitMatrixWrapper;
import cn.augrain.easy.tool.lang.HexUtils;
import com.google.zxing.qrcode.encoder.ByteMatrix;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * 二维码渲染门面
 *
 * @since 0.0.1
 */
public class QrRenderFacade {

    public static BufferedImage renderAsImg(QrCodeConfig qrCodeConfig) throws Exception {
        BitMatrixWrapper wrapper = QrMatrixGenerator.calculateMatrix(qrCodeConfig);
        ByteMatrix byteMatrix = wrapper.getByteMatrix();
        int width = byteMatrix.getWidth();
        int height = byteMatrix.getHeight();

        BufferedImage baseImg = new BufferedImage(qrCodeConfig.getWidth(), qrCodeConfig.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = baseImg.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g.setColor(Color.white);
        g.fillRect(0, 0, qrCodeConfig.getWidth(), qrCodeConfig.getHeight());

        Color finderPatternColor = new Color(204, 0, 102); // 定位点颜色 - 粉红色
        Color innerFinderColor = new Color(51, 204, 51); // 内部定位点颜色 - 绿色

        Color startColor = HexUtils.hexToColor("#74A5FF");
        Color endColor = HexUtils.hexToColor("#CEFF7E");
        List<Color> colors = calculateGradient(startColor, endColor, width);

        g.setColor(Color.RED);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                // if (byteMatrix.get(i, j) == 1) {
                //     g.fillRect(wrapper.getLeftPadding() + i * wrapper.getRatio(),
                //             wrapper.getTopPadding() + j * wrapper.getRatio(),
                //             wrapper.getRatio(), wrapper.getRatio());
                // }

                if (byteMatrix.get(x, y) == 1) {
                    // 检查是否是定位点（三个角上的大方块）
                    if (isInFinderPattern(x, y, width, 7)) {
                        // 在定位点区域使用特殊颜色和样式
                        if (isInInnerFinderPattern(x, y, width, 7)) {
                            g.setColor(innerFinderColor);
                        } else {
                            g.setColor(finderPatternColor);
                        }
                    } else {
                        // g.setColor(colors.get(x));
                        g.setColor(startColor);
                    }
                    g.fillRect(wrapper.getLeftPadding() + x * wrapper.getRatio(),
                            wrapper.getTopPadding() + y * wrapper.getRatio(),
                            wrapper.getRatio(), wrapper.getRatio());
                }
            }
        }
        g.dispose();
        return baseImg;
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

    /**
     * 计算两个颜色之间的渐变色
     *
     * @param startColor 起始颜色
     * @param endColor   结束颜色
     * @param steps      渐变步数
     * @return 渐变色列表
     */
    public static List<Color> calculateGradient(Color startColor, Color endColor, int steps) {
        List<Color> gradient = new ArrayList<>();

        // 分解起始和结束颜色的RGB分量
        int r1 = startColor.getRed();
        int g1 = startColor.getGreen();
        int b1 = startColor.getBlue();

        int r2 = endColor.getRed();
        int g2 = endColor.getGreen();
        int b2 = endColor.getBlue();

        // 计算每一步的RGB值
        for (int i = 0; i < steps; i++) {
            float ratio = (float) i / (float) (steps - 1);
            int r = (int) (r1 + ratio * (r2 - r1));
            int g = (int) (g1 + ratio * (g2 - g1));
            int b = (int) (b1 + ratio * (b2 - b1));
            gradient.add(new Color(r, g, b));
        }

        return gradient;
    }

    /**
     * 将颜色转换为十六进制字符串
     *
     * @param color 颜色对象
     * @return 十六进制颜色字符串
     */
    public static String colorToHex(Color color) {
        return String.format("#%02X%02X%02X",
                color.getRed(),
                color.getGreen(),
                color.getBlue());
    }
}
