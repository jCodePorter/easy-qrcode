package cn.augrain.easy.qrcode.core;

import cn.augrain.easy.qrcode.config.QrCodeConfig;
import cn.augrain.easy.qrcode.model.PointType;
import cn.augrain.easy.qrcode.model.RenderDot;
import cn.augrain.easy.qrcode.wrapper.BitMatrixWrapper;
import com.google.zxing.qrcode.encoder.ByteMatrix;

import java.util.ArrayList;
import java.util.List;

/**
 * 二维码渲染点位解析器
 *
 * @author biaoy
 * @since 0.0.2
 */
public class QrRenderDotResolver {

    /**
     * 二维码点位解析
     */
    public static List<RenderDot> parse(BitMatrixWrapper wrapper, QrCodeConfig codeConfig) {
        if (codeConfig.isWhole()) {
            return parseWhole(wrapper);
        }

        int width = wrapper.getWidth();
        int height = wrapper.getHeight();
        ByteMatrix byteMatrix = wrapper.getByteMatrix();

        List<RenderDot> dots = new ArrayList<>();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (byteMatrix.get(x, y) == 1) {
                    // 检查是否是定位点（三个角上的大方块）
                    if (isInFinderPattern(x, y, width, 7)) {
                        if (isInInnerFinderPattern(x, y, width, 7)) {
                            RenderDot dot = new RenderDot(wrapper.getLeftPadding() + x * wrapper.getRatio(),
                                    wrapper.getTopPadding() + y * wrapper.getRatio(),
                                    wrapper.getRatio(), PointType.EYE, true);
                            dots.add(dot);
                        } else {
                            RenderDot dot = new RenderDot(wrapper.getLeftPadding() + x * wrapper.getRatio(),
                                    wrapper.getTopPadding() + y * wrapper.getRatio(),
                                    wrapper.getRatio(), PointType.EYE, false);
                            dots.add(dot);
                        }
                    } else {
                        RenderDot dot = new RenderDot(wrapper.getLeftPadding() + x * wrapper.getRatio(),
                                wrapper.getTopPadding() + y * wrapper.getRatio(),
                                wrapper.getRatio(), PointType.CODE_POINT);
                        dots.add(dot);
                    }
                }
            }
        }
        return dots;
    }

    private static List<RenderDot> parseWhole(BitMatrixWrapper wrapper) {
        ByteMatrix byteMatrix = wrapper.getByteMatrix();
        int width = byteMatrix.getWidth();
        int height = byteMatrix.getHeight();

        List<RenderDot> dots = new ArrayList<>();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (byteMatrix.get(x, y) == 1) {
                    RenderDot dot = new RenderDot(wrapper.getLeftPadding() + x * wrapper.getRatio(),
                            wrapper.getTopPadding() + y * wrapper.getRatio(),
                            wrapper.getRatio(), PointType.CODE_POINT);
                    dots.add(dot);
                }
            }
        }
        return dots;
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
