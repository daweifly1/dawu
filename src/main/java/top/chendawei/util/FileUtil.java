package top.chendawei.util;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class FileUtil {
    public static String getExtension(File f) {
        return f != null ? getExtension(f.getName()) : "";
    }

    public static String getExtension(String filename) {
        return getExtension(filename, "");
    }

    public static String getExtension(String filename, String defExt) {
        if ((filename != null) && (filename.length() > 0)) {
            int i = filename.lastIndexOf('.');
            if ((i > -1) && (i < filename.length() - 1)) {
                return filename.substring(i + 1);
            }
        }
        return defExt;
    }

    public static String trimExtension(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int i = filename.lastIndexOf('.');
            if ((i > -1) && (i < filename.length())) {
                return filename.substring(0, i);
            }
        }
        return filename;
    }

    public static boolean saveImage(File img, String dest, int top, int left, int width, int height)
            throws IOException {
        File fileDest = new File(dest);
        if (!fileDest.getParentFile().exists()) {
            fileDest.getParentFile().mkdirs();
        }
        String ext = getExtension(dest).toLowerCase();
        BufferedImage bi = ImageIO.read(img);
        height = Math.min(height, bi.getHeight());
        width = Math.min(width, bi.getWidth());
        if (height <= 0) {
            height = bi.getHeight();
        }
        if (width <= 0) {
            width = bi.getWidth();
        }
        top = Math.min(Math.max(0, top), bi.getHeight() - height);
        left = Math.min(Math.max(0, left), bi.getWidth() - width);

        BufferedImage bi_cropper = bi.getSubimage(left, top, width, height);
        return ImageIO.write(bi_cropper, ext.equals("png") ? "png" : "jpeg", fileDest);
    }

    public static void main(String[] args) {
        System.out.println(getExtension("flowers.png"));
    }

}
