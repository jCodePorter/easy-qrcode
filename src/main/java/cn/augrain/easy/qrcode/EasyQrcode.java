package cn.augrain.easy.qrcode;

import cn.augrain.easy.qrcode.config.Gradient;
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
        this.codeConfig.setEyeOutColor(color);
        return this;
    }

    public EasyQrcode setInnerLocationColor(Color color) {
        this.codeConfig.setEyeInnerColor(color);
        return this;
    }

    public EasyQrcode setBgColor(Color color) {
        this.codeConfig.setBgColor(color);
        return this;
    }

    public EasyQrcode setWhole(boolean whole) {
        this.codeConfig.setWhole(whole);
        return this;
    }

    public EasyQrcode setGradient(Gradient gradient) {
        this.codeConfig.setGradient(gradient);
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
