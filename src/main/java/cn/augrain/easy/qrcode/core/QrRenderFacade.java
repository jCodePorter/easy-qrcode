package cn.augrain.easy.qrcode.core;

import cn.augrain.easy.qrcode.config.QrCodeConfig;
import cn.augrain.easy.qrcode.core.render.QrRender;
import cn.augrain.easy.qrcode.model.RenderDot;
import cn.augrain.easy.qrcode.wrapper.BitMatrixWrapper;

import java.awt.image.BufferedImage;
import java.util.List;

/**
 * 二维码渲染门面
 *
 * @since 0.0.1
 */
public class QrRenderFacade {

    public static BufferedImage renderAsImg(QrCodeConfig qrCodeConfig) throws Exception {
        BitMatrixWrapper wrapper = QrMatrixGenerator.calculateMatrix(qrCodeConfig);

        List<RenderDot> dotList = QrRenderDotResolver.parse(wrapper, qrCodeConfig);

        return QrRender.render(dotList, qrCodeConfig);
    }
}
