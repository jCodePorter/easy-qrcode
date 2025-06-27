package cn.augrain.easy.qrcode;

import org.junit.Test;

import java.awt.*;

public class Test2 {

    @Test
    public void t1() {
        EasyQrcode qrcode = new EasyQrcode("叮有鱼成立了叮有鱼成立了叮有鱼成立了")
                .setInnerLocationColor(Color.blue)
                .setOuterLocationColor(Color.RED)
                .setBgColor(Color.pink);

        qrcode.asImg("test_qrcode.png");
    }

    @Test
    public void testWhole() {
        EasyQrcode qrcode = new EasyQrcode("空间引力是一家专注于软件的公司")
                .setBgColor(Color.pink)
                .setWhole(true);
        qrcode.asImg("test_qrcode_whole.png");
    }
}
