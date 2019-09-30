package top.chendawei.util.io.enums;

public enum MsOfficeVersion {
    Office2003, Office2007, Office2010;

    public String getSuffix() {
        if ((this == Office2007) || (this == Office2010)) {
            return "xlsx";
        }
        return "xls";
    }
}
