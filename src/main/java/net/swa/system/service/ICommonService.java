package net.swa.system.service;

import net.swa.util.JsonResult;

import java.util.List;

public interface ICommonService {
    <T> JsonResult<T> search(String[] paramArrayOfString1, Object[] paramArrayOfObject, String[] paramArrayOfString2, Class<T> paramClass, int paramInt1, int paramInt2, String paramString1, String paramString2)
            throws Exception;

    boolean commonUpdateStatus(String paramString, Long[] paramArrayOfLong, int paramInt)
            throws Exception;

    boolean commonDelete(String paramString, Long... paramVarArgs)
            throws Exception;

    void commonAdd(Object paramObject)
            throws Exception;

    void commonUpdate(Object paramObject)
            throws Exception;

    <T> T commonFind(Class<T> paramClass, long paramLong)
            throws Exception;

    <T> List<T> search(String paramString, Object paramObject, Class<T> paramClass);

    <T> List<T> search(Class<T> paramClass, String[] paramArrayOfString, Object[] paramArrayOfObject);

    <T> JsonResult<T> search(String[] paramArrayOfString, Object[] paramArrayOfObject, Class<T> paramClass, int paramInt1, int paramInt2, String paramString1, String paramString2)
            throws Exception;

    boolean batchDelete(List<?> paramList)
            throws Exception;

    <T> JsonResult<T> searchBean(String paramString, Object paramObject, Class<T> paramClass)
            throws Exception;

    boolean commonDelHisStatus(String paramString1, String paramString2, Long[] paramArrayOfLong, String paramString3)
            throws Exception;

    <T> T findByAttribute(Class<T> paramClass, String paramString, Object paramObject);

    <T> List<T> search(String paramString1, Object paramObject, Class<T> paramClass, Integer paramInteger, String paramString2, String paramString3);
}
