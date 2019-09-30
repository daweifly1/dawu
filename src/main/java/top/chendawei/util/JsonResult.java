package top.chendawei.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonResult<T> {
    private boolean success;
    private String message;
    private int totalSize;
    private List<T> result;
    private int currentPage;
    private int pageSize;
    private int totalPage;
    private Map<String, Object> attribute;

    public JsonResult() {
        this.totalSize = 0;
        this.result = new ArrayList();
        this.attribute = new HashMap();
        this.success = true;
    }

    public int getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalSize() {
        return this.totalSize;
    }

    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
    }

    public int getTotalPage() {
        return this.totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public List<T> getResult() {
        return this.result;
    }

    public void setResult(List<T> result) {
        this.result = result;
    }

    public boolean isSuccess() {
        return this.success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCurrentPage() {
        return this.currentPage;
    }

    public void setCurrentPage(int currentPage) {
        if (this.totalSize == 0) {
            currentPage = 0;
            return;
        }
        this.currentPage = currentPage;
    }

    public Object getAttribute(String key) {
        return this.attribute.get(key);
    }

    public void setAttribute(String key, Object value) {
        this.attribute.put(key, value);
    }

    public Map<String, Object> getAttribute() {
        return this.attribute;
    }

    public void setAttribute(Map<String, Object> attribute) {
        this.attribute = attribute;
    }

    public void removeAttribute(String key) {
        this.attribute.remove(key);
    }

    public String getSpace(int count) {
        String str = "";
        for (int i = 1; i <= count; i++) {
            str = str + " ";
        }
        return str;
    }
}
