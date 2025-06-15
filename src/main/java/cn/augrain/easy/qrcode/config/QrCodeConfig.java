package cn.augrain.easy.qrcode.config;

import lombok.Getter;
import lombok.Setter;

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
    private Integer width;

    /**
     * 二维码的高
     */
    private Integer height;

    /**
     * 最大留白区域
     */
    private Integer padding;
}
