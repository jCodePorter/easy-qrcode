package cn.augrain.easy.qrcode.model;

import lombok.Getter;
import lombok.Setter;

/**
 * 渲染点位
 *
 * @since 0.0.1
 */
@Getter
@Setter
public class RenderDot {

    /**
     * x 坐标点
     */
    private int x;

    /**
     * y 坐标点
     */
    private int y;

    /**
     * 图形大小
     */
    private int size;

    /**
     * 资源类型 0: 探测图形  1: 背景点  2: 信息点|码点
     */
    protected int type;
}
