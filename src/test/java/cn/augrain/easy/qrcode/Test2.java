package cn.augrain.easy.qrcode;

import cn.augrain.easy.qrcode.core.QrRenderFacade;
import cn.augrain.easy.qrcode.config.QrCodeConfig;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class Test2 {

    @Test
    public void t1() {
        QrCodeConfig codeConfig = new QrCodeConfig();
        codeConfig.setMsg("叮有鱼成立了叮有鱼成立了叮有鱼成立了");
        codeConfig.setWidth(300);
        codeConfig.setHeight(300);
        codeConfig.setPadding(0);

        try {
            BufferedImage result = QrRenderFacade.renderAsImg(codeConfig);
            ImageIO.write(result, "png", new File("test.png"));
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
