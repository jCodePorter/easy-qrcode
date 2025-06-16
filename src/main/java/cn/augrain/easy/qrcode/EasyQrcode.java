package cn.augrain.easy.qrcode;

import cn.augrain.easy.qrcode.config.QrCodeConfig;
import cn.augrain.easy.qrcode.core.QrRenderFacade;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * 简单二维码美化工具
 *
 * @since 0.0.1
 */
public class EasyQrcode {

    private final QrCodeConfig codeConfig = new QrCodeConfig();

    public EasyQrcode(String msg) {
        this.codeConfig.setMsg(msg);
    }

    public EasyQrcode setMsg(String msg) {
        this.codeConfig.setMsg(msg);
        return this;
    }

    public EasyQrcode setWidth(int width) {
        this.codeConfig.setWidth(width);
        return this;
    }

    public EasyQrcode setHeight(int height) {
        this.codeConfig.setHeight(height);
        return this;
    }

    public EasyQrcode setOuterLocationColor(Color color) {
        this.codeConfig.setOuterLocationColor(color);
        return this;
    }

    public EasyQrcode setInnerLocationColor(Color color) {
        this.codeConfig.setInnerLocationColor(color);
        return this;
    }

    public EasyQrcode setColor(Color color) {
        this.codeConfig.setColor(color);
        return this;
    }

    public void asImg(String path) {
        try {
            BufferedImage result = QrRenderFacade.renderAsImg(codeConfig);
            ImageIO.write(result, "png", new File(path));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
