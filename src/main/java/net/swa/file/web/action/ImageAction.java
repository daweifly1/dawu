package net.swa.file.web.action;

import lombok.extern.slf4j.Slf4j;
import net.swa.system.web.action.AbstractBaseAction;
import net.swa.util.StringUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.IOException;
import java.util.Random;

@Controller
@RequestMapping({"/image"})
@Slf4j
public class ImageAction
        extends AbstractBaseAction {
    private static final long serialVersionUID = 5333303240193114340L;

    public static BufferedImage rotateImg(BufferedImage image, int degree, Color bgcolor)
            throws IOException {
        int iw = image.getWidth();
        int ih = image.getHeight();
        int w = 0;
        int h = 0;
        int x = 0;
        int y = 0;
        degree %= 360;
        if (degree < 0) {
            degree += 360;
        }
        double ang = Math.toRadians(degree);
        if ((degree == 180) || (degree == 0) || (degree == 360)) {
            w = iw;
            h = ih;
        } else if ((degree == 90) || (degree == 270)) {
            w = ih;
            h = iw;
        } else {
            int d = iw + ih;
            w = (int) (d * Math.abs(Math.cos(ang)));
            h = (int) (d * Math.abs(Math.sin(ang)));
        }
        x = w / 2 - iw / 2;
        y = h / 2 - ih / 2;
        BufferedImage rotatedImage = new BufferedImage(w, h, image.getType());
        Graphics2D gs = (Graphics2D) rotatedImage.getGraphics();
        if (bgcolor == null) {
            rotatedImage = gs.getDeviceConfiguration().createCompatibleImage(w, h, 3);
        } else {
            gs.setColor(bgcolor);
            gs.fillRect(0, 0, w, h);
        }
        AffineTransform at = new AffineTransform();
        at.rotate(ang, w / 2, h / 2);
        at.translate(x, y);
        AffineTransformOp op = new AffineTransformOp(at, 3);
        op.filter(image, rotatedImage);
        image = rotatedImage;
        return rotatedImage;
    }

    @RequestMapping({"/validateCode"})
    public void validateCode(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0L);
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0L);

        int CODE_LENGTH = 4;
        Random rand = new Random();

        String[] FontFamily = {"Times New Roman", "宋体", "黑体", "Arial Unicode MS", "Lucida Sans"};

        boolean ROTATE_FLAG = true;

        int height = 25;
        String code = "";
        String sRand = "";

        int fontstyle = 1;
        double oldrot = 0.0D;

        int width = 80;
        BufferedImage image = new BufferedImage(width, height, 1);
        Graphics graphics = image.getGraphics();
        Graphics2D g2 = (Graphics2D) graphics;
        g2.setColor(getRandColor(100, 255));
        g2.fillRect(0, 0, width, height);
        for (int i = 0; i < 4; i++) {
            sRand = StringUtil.getRandomString(1);
            code = code + sRand;
            int fontsize = Math.abs(rand.nextInt(24));
            if (fontsize < 5) {
                fontsize += 22;
            }
            if (fontsize < 8) {
                fontsize += 19;
            }
            if (fontsize < 15) {
                fontsize += 12;
            }
            fontstyle = rand.nextInt(6);
            g2.setFont(new Font(FontFamily[rand.nextInt(5)], fontstyle, fontsize));
            double rot = -0.25D + Math.abs(Math.toRadians(rand.nextInt(25)));
            if (ROTATE_FLAG) {
                g2.rotate(-oldrot, 10.0D, 15.0D);
                oldrot = rot;
                g2.rotate(rot, 3 * (i % 2) + 8, 15.0D);
            }
            float stroke = Math.abs(rand.nextFloat() % 30.0F);
            g2.setStroke(new BasicStroke(stroke));
            g2.setColor(new Color(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255)));
            g2.setColor(getRandColor(1, 100));
            g2.drawString(sRand, 20 * i, 20);
        }
        request.getSession().setAttribute("validateCode", code);

        g2.dispose();
        try {
            ImageIO.write(image, "JPEG", response.getOutputStream());
        } catch (Exception e) {
            this.log.error(e.getMessage());
        }
    }

    public Color getRandColor(int fc, int bc) {
        Random random = new Random();
        if (fc > 255) {
            fc = 255;
        }
        if (bc > 255) {
            bc = 255;
        }
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }

    public BufferedImage reSize(BufferedImage srcBufImage, int width, int height) {
        BufferedImage bufTarget = null;
        double sx = width / srcBufImage.getWidth();
        double sy = height / srcBufImage.getHeight();
        int type = srcBufImage.getType();
        if (type == 0) {
            ColorModel cm = srcBufImage.getColorModel();
            WritableRaster raster = cm.createCompatibleWritableRaster(width, height);
            boolean alphaPremultiplied = cm.isAlphaPremultiplied();
            bufTarget = new BufferedImage(cm, raster, alphaPremultiplied, null);
        } else {
            bufTarget = new BufferedImage(width, height, type);
        }
        Graphics2D g = bufTarget.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g.drawRenderedImage(srcBufImage, AffineTransform.getScaleInstance(sx, sy));
        g.dispose();
        return bufTarget;
    }
}
