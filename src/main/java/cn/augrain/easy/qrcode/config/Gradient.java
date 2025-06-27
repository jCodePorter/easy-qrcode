package cn.augrain.easy.qrcode.config;

import cn.augrain.easy.qrcode.utils.ColorUtils;
import cn.augrain.easy.tool.lang.HexUtils;

import java.awt.*;
import java.util.List;

/**
 * 渐变配置
 *
 * @author biaoy
 * @since 0.0.2
 */
public class Gradient {

    private Color startColor;

    private Color endColor;

    private GradientDirection direction;

    private List<Color> gradientColor;

    public Gradient(Color startColor, Color endColor, GradientDirection direction) {
        this.startColor = startColor;
        this.endColor = endColor;
        this.direction = direction;
    }

    public Gradient(String startColor, String endColor, GradientDirection direction) {
        this.startColor = HexUtils.hexToColor(startColor);
        this.endColor = HexUtils.hexToColor(endColor);
        this.direction = direction;
    }

    private List<Color> calculateGradient(int width, int height) {
        if (direction == GradientDirection.TOP_BOTTOM) {
            return ColorUtils.calculateGradient(startColor, endColor, height);
        } else if (direction == GradientDirection.LEFT_RIGHT) {
            return ColorUtils.calculateGradient(startColor, endColor, width);
        } else {
            return ColorUtils.calculateGradient(startColor, endColor, (int) Math.sqrt(width * width + height * height));
        }
    }

    public Color getColor(int x, int y, int width, int height) {
        if (this.gradientColor == null) {
            this.gradientColor = calculateGradient(width, height);
        }

        if (direction == GradientDirection.TOP_BOTTOM) {
            return gradientColor.get(y);
        } else if (direction == GradientDirection.LEFT_RIGHT) {
            return gradientColor.get(x);
        } else if (direction == GradientDirection.LEFT_BOTTOM_TOP_RIGHT) {
            double distance = Math.sqrt(x * x + (height - 1 - y) * (height - 1 - y));
            return this.gradientColor.get((int) (distance));
        } else {
            double distance = Math.sqrt(x * x + y * y);
            return this.gradientColor.get((int) (distance));
        }
    }
}
