package cn.augrain.easy.qrcode.utils;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 颜色相关处理
 *
 * @since 0.0.1
 */
public class ColorUtils {

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

        // 拆分起始和结束颜色的RGB分量
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
