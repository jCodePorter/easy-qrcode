package cn.augrain.easy.qrcode.core;

import cn.augrain.easy.qrcode.config.QrCodeConfig;
import cn.augrain.easy.qrcode.wrapper.BitMatrixWrapper;
import com.google.zxing.qrcode.encoder.ByteMatrix;

import java.awt.*;
import java.awt.image.BufferedImage;

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

        Color finderPatternColor = qrCodeConfig.getInnerLocationColor();
        Color innerFinderColor = qrCodeConfig.getOuterLocationColor();
        Color color = qrCodeConfig.getColor();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
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
                        g.setColor(color);
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
}
