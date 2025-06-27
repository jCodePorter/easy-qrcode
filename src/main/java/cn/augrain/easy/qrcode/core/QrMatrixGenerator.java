package cn.augrain.easy.qrcode.core;

import cn.augrain.easy.qrcode.config.QrCodeConfig;
import cn.augrain.easy.qrcode.wrapper.BitMatrixWrapper;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.google.zxing.qrcode.encoder.ByteMatrix;
import com.google.zxing.qrcode.encoder.Encoder;
import com.google.zxing.qrcode.encoder.QRCode;

import java.util.Hashtable;

/**
 * 二维码图片矩阵生成
 *
 * @since 0.0.1
 */
public class QrMatrixGenerator {

    /**
     * 最大的留白
     */
    private static final int MAX_QUIET_ZONE_SIZE = 4;

    public static BitMatrixWrapper calculateMatrix(QrCodeConfig codeConfig) throws WriterException {
        ErrorCorrectionLevel errorCorrectionLevel = ErrorCorrectionLevel.L;
        int quietZone = codeConfig.getPadding();
        if (quietZone > MAX_QUIET_ZONE_SIZE) {
            quietZone = MAX_QUIET_ZONE_SIZE;
        } else if (quietZone < 0) {
            quietZone = 0;
        }

        Hashtable<EncodeHintType, Object> hints = new Hashtable<>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.Q);
        hints.put(EncodeHintType.MARGIN, quietZone);
        QRCode code = Encoder.encode(codeConfig.getMsg(), errorCorrectionLevel, hints);
        return renderResult(code, codeConfig.getWidth(), codeConfig.
                getHeight(), quietZone);
    }

    private static BitMatrixWrapper renderResult(QRCode code, int width, int height, int quietZone) {
        ByteMatrix input = code.getMatrix();
        if (input == null) {
            throw new IllegalStateException();
        }

        // 二维码宽高相等, 即 qrWidth == qrHeight
        int inputWidth = input.getWidth();
        int inputHeight = input.getHeight();
        // 二维码原始矩阵信息+两侧留白区域后的宽高，原始矩阵信息宽高必相等
        int qrWidth = inputWidth + (quietZone * 2);
        int qrHeight = inputHeight + (quietZone * 2);

        // 白边过多时, 缩放
        int minSize = Math.min(width, height);
        int scale = calculateScale(qrWidth, minSize);
        if (scale > 0) {
            // 计算边框留白
            int padding = (minSize - qrWidth * scale) / MAX_QUIET_ZONE_SIZE * quietZone;
            int tmpValue = qrWidth * scale + padding;
            if (width == height) {
                width = tmpValue;
                height = tmpValue;
            } else if (width > height) {
                width = width * tmpValue / height;
                height = tmpValue;
            } else {
                height = height * tmpValue / width;
                width = tmpValue;
            }
        }

        int outputWidth = Math.max(width, qrWidth);
        int outputHeight = Math.max(height, qrHeight);

        int ratio = Math.min(outputWidth / qrWidth, outputHeight / qrHeight);
        int leftPadding = (outputWidth - (inputWidth * ratio)) / 2;
        int topPadding = (outputHeight - (inputHeight * ratio)) / 2;

        BitMatrixWrapper wrapper = new BitMatrixWrapper();
        wrapper.setByteMatrix(input);
        wrapper.setLeftPadding(leftPadding);
        wrapper.setTopPadding(topPadding);
        wrapper.setRatio(ratio);
        wrapper.setWidth(outputWidth);
        wrapper.setHeight(outputHeight);
        return wrapper;
    }

    /**
     * 如果留白超过15%, 则需要缩放
     * (15% 可以根据实际需要进行修改)
     *
     * @param qrCodeSize 二维码大小
     * @param expectSize 期望输出大小
     * @return 返回缩放比例, <= 0 则表示不缩放, 否则指定缩放参数
     */
    private static int calculateScale(int qrCodeSize, int expectSize) {
        if (qrCodeSize >= expectSize) {
            return 0;
        }

        int scale = expectSize / qrCodeSize;
        int abs = expectSize - scale * qrCodeSize;
        if (abs < expectSize * 0.15) {
            return 0;
        }
        return scale;
    }
}
