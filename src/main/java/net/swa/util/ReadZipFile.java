package net.swa.util;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipFile;

import java.util.Enumeration;

public class ReadZipFile {
    public static void listJarFile(String zipPathName)
            throws Exception {
        ZipFile zf = new ZipFile("d:/Archive.zip", "gbk");
        System.out.println(zf.getEncoding());

        Enumeration<ZipArchiveEntry> it = zf.getEntries();
        while (it.hasMoreElements()) {
            ZipArchiveEntry ze = it.nextElement();
            System.out.println("-" + ze.getName());
        }
    }

    public static void main(String[] args)
            throws Exception {
    }
}
