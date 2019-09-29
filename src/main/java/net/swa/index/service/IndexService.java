package net.swa.index.service;

import net.swa.index.beans.entity.GymLog;

import java.util.Map;

public interface IndexService {
    Map<String, Object> login(String paramString1, String paramString2, String paramString3, String paramString4);

    Map<String, Object> saveRegister(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8, String paramString9, String paramString10, Integer paramInteger1, Integer paramInteger2, Integer paramInteger3, Integer paramInteger4, Integer paramInteger5);

    Map<String, Object> modifyPassword(String paramString1, String paramString2, String paramString3, String paramString4);

    Map<String, Object> queryQuestions(String paramString);

    Map<String, Object> queryPassword(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7);

    Map<String, Object> resetPassword(String paramString1, String paramString2);

    Map<String, Object> queryMyAccount(String paramString);

    Map<String, Object> queryDetail(String paramString);

    Map<String, Object> saveNewInfo(String paramString1, String paramString2, Double paramDouble);

    Map<String, Object> queryNewInfo(String paramString1, String paramString2);

    Map<String, Object> updateMoney(String paramString, Long paramLong);

    Map<String, Object> queryInfos(String paramString1, String paramString2, Integer paramInteger);

    Map<String, Object> updateIgnoreMoney(String paramString, Long paramLong);

    Map<String, Object> queryTypeExs(String paramString1, String paramString2);

    Map<String, Object> queryUserDetail(String paramString1, String paramString2, String paramString3);

    Map<String, Object> queryByAdminDetail(String paramString1, String paramString2, String paramString3, String paramString4);

    Map<String, Object> updateLimit(String paramString, Double paramDouble);

    Map<String, Object> updateEditByAdmin(String paramString1, Long paramLong, String paramString2, String paramString3, Double paramDouble);

    Map<String, Object> shezhiByAdmin(String paramString1, String paramString2, Integer paramInteger1, Integer paramInteger2, Integer paramInteger3, Integer paramInteger4, Integer paramInteger5);

    Map<String, Object> shenjiByAdmin(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6);

    void saveLog(GymLog paramGymLog);

    Map<String, Object> updateIgnoreByAdmin(String paramString, Long paramLong);

    Map<String, Object> queryLockedUsers(String paramString1, String paramString2);

    Map<String, Object> updateUnLocked(String paramString1, String paramString2, Long paramLong);

    Map<String, Object> queryMembers(String paramString, String[] paramArrayOfString, Boolean paramBoolean);

    Map<String, Object> addBackUpDb(String paramString);

    Map<String, Object> queryOneUser(String paramString);

    Map<String, Object> queryBackHis(String paramString);

    Map<String, Object> queryRecordsAdm(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5);

    Map<String, Object> updateAfterQuery(String paramString);
}
