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
     * 是否整个二维码渲染，设置为true，忽略码眼颜色，使用统计背景图或者背景色
     */
    private boolean whole;

    /**
     * 渐变色配置
     */
    private Gradient gradient;

    /**
     * 码眼外圈颜色
     */
    private Color eyeOutColor = Color.BLACK;

    /**
     * 码眼内圈颜色
     */
    private Color eyeInnerColor = Color.BLACK;

    /**
     * 背景色
     */
    private Color bgColor = Color.BLACK;
}
