package cn.augrain.easy.qrcode.core.render;

import cn.augrain.easy.qrcode.config.QrCodeConfig;
import cn.augrain.easy.qrcode.model.PointType;
import cn.augrain.easy.qrcode.model.RenderDot;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

/**
 * 二维码渲染
 *
 * @author biaoy
 * @since 0.0.2
 */
public class QrRender {

    public static BufferedImage render(List<RenderDot> dotList, QrCodeConfig qrCodeConfig) {
        BufferedImage bufferImage = createBufferImage(qrCodeConfig);

        if (qrCodeConfig.isWhole()) {
            renderWhole(bufferImage, dotList, qrCodeConfig);
        } else {
            renderCustom(bufferImage, dotList, qrCodeConfig);
        }

        return bufferImage;
    }

    private static void renderCustom(BufferedImage bufferImage, List<RenderDot> dotList, QrCodeConfig qrCodeConfig) {
        Graphics2D g = bufferImage.createGraphics();
        for (RenderDot renderDot : dotList) {
            if (renderDot.getPointType() == PointType.EYE) {
                if (renderDot.isOuter()) {
                    g.setColor(qrCodeConfig.getEyeOuterColor());
                } else {
                    g.setColor(qrCodeConfig.getEyeInnerColor());
                }
            } else {
                g.setColor(qrCodeConfig.getBgColor());
            }
            g.fillRect(renderDot.getX(), renderDot.getY(), renderDot.getSize(), renderDot.getSize());
        }
    }

    private static void renderWhole(BufferedImage bufferImage, List<RenderDot> dotList, QrCodeConfig qrCodeConfig) {
        Graphics2D g = bufferImage.createGraphics();
        g.setColor(qrCodeConfig.getBgColor());
        if (qrCodeConfig.getGradient() != null) {
            for (RenderDot dot : dotList) {
                for (int i = 0; i <= dot.getSize(); i++) {
                    int ry = dot.getY() + i;
                    for (int j = 0; j <= dot.getSize(); j++) {
                        int rx = dot.getX() + j;
                        Color color = qrCodeConfig.getGradient().getColor(rx, ry, bufferImage.getWidth(), bufferImage.getHeight());
                        g.setColor(color);
                        g.fillRect(rx, ry, 1, 1);
                    }
                }
            }
        } else {
            for (RenderDot dot : dotList) {
                g.fillRect(dot.getX(), dot.getY(), dot.getSize(), dot.getSize());
            }
        }

    }

    private static BufferedImage createBufferImage(QrCodeConfig qrCodeConfig) {
        BufferedImage baseImg = new BufferedImage(qrCodeConfig.getWidth(), qrCodeConfig.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = baseImg.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g.setColor(Color.white);
        g.fillRect(0, 0, qrCodeConfig.getWidth(), qrCodeConfig.getHeight());
        g.dispose();
        return baseImg;
    }
}
