package top.chendawei.file.web.action;

import java.awt.image.ColorModel;
import java.awt.image.DirectColorModel;
import java.awt.image.RGBImageFilter;

class MyFilter
        extends RGBImageFilter {
    int alpha = 0;

    public MyFilter(int alpha) {
        this.canFilterIndexColorModel = true;

        this.alpha = alpha;
    }

    public int filterRGB(int x, int y, int rgb) {
        DirectColorModel dcm = (DirectColorModel) ColorModel.getRGBdefault();

        int red = dcm.getRed(rgb);
        int green = dcm.getGreen(rgb);
        int blue = dcm.getBlue(rgb);
        if ((red == 255) && (blue == 255) && (green == 255)) {
            this.alpha = 0;
        } else {
            this.alpha = 255;
        }
        return this.alpha << 24 | red << 16 | green << 8 | blue;
    }
}
