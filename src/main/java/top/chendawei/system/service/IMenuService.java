package top.chendawei.system.service;

import top.chendawei.system.beans.entity.Menu;

import java.util.List;

public interface IMenuService {
    List<Menu> getRootMenu(long paramLong)
            throws Exception;

    List<Menu> getRootsByRoleId(long paramLong)
            throws Exception;
}
