package cn.augrain.easy.qrcode.wrapper;

import com.google.zxing.qrcode.encoder.ByteMatrix;
import lombok.Getter;
import lombok.Setter;

/**
 * BitMatrix包装器
 *
 * @since 0.0.1
 */
@Getter
@Setter
public class BitMatrixWrapper {

    /**
     * 实际生成二维码的宽
     */
    private int width;

    /**
     * 实际生成二维码的高
     */
    private int height;

    /**
     * 左白边大小
     */
    private int leftPadding;

    /**
     * 上白边大小
     */
    private int topPadding;

    /**
     * 矩阵信息缩放比例
     */
    private int ratio;

    /**
     * 原始二维码矩阵信息
     */
    private ByteMatrix byteMatrix;
}
