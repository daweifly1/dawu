package net.swa.file.beans.entity;

public class Entry {
    private long fid;
    private int id;
    private String name;
    private long size;
    private String ext;
    private boolean dir;
    private Entry parent;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getSize() {
        return this.size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getExt() {
        return this.ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public boolean isDir() {
        return this.dir;
    }

    public void setDir(boolean dir) {
        this.dir = dir;
    }

    public String toXml() {
        StringBuilder html = new StringBuilder();

        html.append("<tr id='node-" + this.id + "' " + (this.parent == null ? "" : new StringBuilder("class='child-of-node-").append(this.parent.getId()).append("'").toString()) + ">");
        html.append("<td>\n");
        if (!this.dir) {
            html.append("<span class='file'>");
            if (this.name.indexOf("/") > 0) {
                this.name = this.name.substring(this.name.lastIndexOf("/") + 1);
            }
            html.append("<a class='fancybox-buttons' data-fancybox-group='button" + this.fid + "' href='file/viewEntry/" + this.fid + "/" + this.id + ".png' title='" + this.name + "'>" + this.name + "</a> \n");
            html.append("</span>");
        } else {
            String folder = this.name.substring(0, this.name.length() - 1);
            if (folder.indexOf("/") > 0) {
                folder = folder.substring(folder.lastIndexOf("/") + 1);
            }
            html.append("<span class='folder'>" + folder + "</span>\n");
        }
        html.append("</td>\n");

        Double val = Double.valueOf(this.size / 10.24D);
        this.size = val.intValue();

        html.append("<td>" + (this.dir ? "-" : new StringBuilder(String.valueOf(this.size / 100.0D)).append("KB").toString()) + " </td>\n");
        html.append("<td>" + (this.dir ? "文件夹" : this.ext) + "</td></tr>\n");

        return html.toString();
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Entry getParent() {
        return this.parent;
    }

    public void setParent(Entry parent) {
        this.parent = parent;
    }

    public long getFid() {
        return this.fid;
    }

    public void setFid(long fid) {
        this.fid = fid;
    }
}
