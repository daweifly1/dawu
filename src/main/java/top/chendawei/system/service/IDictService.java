package top.chendawei.system.service;

import top.chendawei.system.beans.entity.Dict;
import top.chendawei.util.JsonResult;

import java.util.List;

public interface IDictService {
    List<Dict> getDictType()
            throws Exception;

    void updateDicNum(Long[] paramArrayOfLong1, Long[] paramArrayOfLong2);

    List<Dict> getDictType2();

    JsonResult<String> openSessiondelete(Long[] paramArrayOfLong);
}
