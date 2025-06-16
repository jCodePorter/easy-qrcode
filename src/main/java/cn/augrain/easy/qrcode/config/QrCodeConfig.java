package cn.augrain.easy.qrcode.config;

import lombok.Getter;
import lombok.Setter;

import java.awt.*;

/**
 * 二维码配置
 *
 * @since 0.0.1
 */
@Getter
@Setter
public class QrCodeConfig {

    /**
     * 二维码的信息
     */
    private String msg;

    /**
     * 二维码的宽
     */
    private int width = 200;

    /**
     * 二维码的高
     */
    private int height = 200;

    /**
     * 最大留白区域
     */
    private int padding = 1;

    /**
     * 外部定位点颜色
     */
    private Color outerLocationColor = Color.BLACK;

    /**
     * 内部定位点颜色
     */
    private Color innerLocationColor = Color.BLACK;

    /**
     * 矩阵信息颜色
     */
    private Color color = Color.BLACK;
}
