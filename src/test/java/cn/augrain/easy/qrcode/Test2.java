package cn.augrain.easy.qrcode;

import org.junit.Test;

import java.awt.*;

public class Test2 {

    @Test
    public void t1() {
        EasyQrcode qrcode = new EasyQrcode("叮有鱼成立了叮有鱼成立了叮有鱼成立了")
                .setInnerLocationColor(Color.blue)
                .setOuterLocationColor(Color.RED)
                .setColor(Color.pink);

        qrcode.asImg("test_qrcode.png");
    }
}
