package cn.augrain.easy.qrcode;

import cn.augrain.easy.qrcode.config.Gradient;
import cn.augrain.easy.qrcode.config.GradientDirection;
import org.junit.Test;

import java.awt.*;

public class BasicTest {

    @Test
    public void t1() {
        EasyQrcode qrcode = new EasyQrcode("叮有鱼成立了叮有鱼成立了叮有鱼成立了")
                .setInnerLocationColor(Color.blue)
                .setOuterLocationColor(Color.RED)
                .setBgColor(Color.pink);

        qrcode.asImg("out_qrcode.png");
    }

    @Test
    public void testWhole() {
        EasyQrcode qrcode = new EasyQrcode("空间引力是一家专注于软件的公司")
                .setBgColor(Color.pink)
                .setWhole(true);
        qrcode.asImg("out_qrcode_whole.png");
    }

    @Test
    public void testGradient() {
        EasyQrcode qrcode = new EasyQrcode("空间引力是一家专注于软件的公司")
                .setGradient(new Gradient("#74A5FF", "#CEFF7E", GradientDirection.LEFT_BOTTOM_TOP_RIGHT))
                .setWhole(true);
        qrcode.asImg("out_qrcode_whole_gradient.png");
    }
}
