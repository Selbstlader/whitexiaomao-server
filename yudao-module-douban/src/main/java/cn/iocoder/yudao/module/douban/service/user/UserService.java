package cn.iocoder.yudao.module.douban.service.user;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.douban.controller.admin.user.vo.*;
import cn.iocoder.yudao.module.douban.dal.dataobject.UserDO;

import jakarta.validation.Valid;
import java.util.Collection;
import java.util.List;

/**
 * 豆瓣用户 Service 接口
 *
 * @author 芋道源码
 */
public interface UserService {

    /**
     * 创建用户
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createUser(@Valid UserCreateReqVO createReqVO);

    /**
     * 更新用户
     *
     * @param updateReqVO 更新信息
     */
    void updateUser(@Valid UserUpdateReqVO updateReqVO);

    /**
     * 删除用户
     *
     * @param id 编号
     */
    void deleteUser(Long id);

    /**
     * 获得用户
     *
     * @param id 编号
     * @return 用户
     */
    UserDO getUser(Long id);

    /**
     * 获得用户列表
     *
     * @param ids 编号
     * @return 用户列表
     */
    List<UserDO> getUserList(Collection<Long> ids);

    /**
     * 获得用户分页
     *
     * @param pageReqVO 分页查询
     * @return 用户分页
     */
    PageResult<UserDO> getUserPage(UserPageReqVO pageReqVO);

    /**
     * 获得用户列表（无参数）
     *
     * @return 用户列表
     */
    List<UserDO> getUserList();

    /**
     * 根据用户名获取用户
     *
     * @param username 用户名
     * @return 用户
     */
    UserDO getUserByUsername(String username);

    /**
     * 根据用户名获得用户
     *
     * @param people 用户名
     * @return 用户
     */
    UserDO getUserByPeople(String people);
}