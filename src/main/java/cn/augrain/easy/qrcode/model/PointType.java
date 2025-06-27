package cn.augrain.easy.qrcode.model;

/**
 * @author biaoy
 * @since 2025/06/27
 */
public enum PointType {
    EYE("码眼"),
    MARK_POINT("定位点"),
    CODE_POINT("码点");

    private String text;

    PointType(String text) {
        this.text = text;
    }
}
