package net.swa.index.service;

import net.swa.index.beans.entity.ApkVersion;
import net.swa.util.JsonResult;

import java.util.Map;

public interface ApkVersionService {
    JsonResult<ApkVersion> queryPage(String paramString1, int paramInt1, int paramInt2, String paramString2, String paramString3);

    ApkVersion queryLastVersion();

    ApkVersion queryLastAdmVersion(boolean paramBoolean);

    ApkVersion queryById(Long paramLong);

    Map<String, Object> add(ApkVersion paramApkVersion);

    Map<String, Object> update(ApkVersion paramApkVersion);

    Map<String, Object> save(ApkVersion paramApkVersion);
}
