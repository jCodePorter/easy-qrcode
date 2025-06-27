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
     * 类型
     */
    private PointType pointType;

    /**
     * 是否外部，仅当 pointType = PointType.EYE 生效
     */
    private boolean isOuter;

    public RenderDot() {
    }

    public RenderDot(int x, int y, int size, PointType pointType) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.pointType = pointType;
    }

    public RenderDot(int x, int y, int size, PointType pointType, boolean isOuter) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.pointType = pointType;
        this.isOuter = isOuter;
    }
}
