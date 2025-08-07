package cn.iocoder.yudao.module.douban.service.user;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.douban.controller.admin.user.vo.*;
import cn.iocoder.yudao.module.douban.dal.dataobject.UserDO;
import cn.iocoder.yudao.module.douban.dal.mysql.user.UserMapper;
import cn.iocoder.yudao.module.douban.enums.ErrorCodeConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import jakarta.annotation.Resource;
import java.util.Collection;
import java.util.List;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 豆瓣用户 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
@Slf4j
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper doubanUserMapper;

    @Override
    public Long createUser(UserCreateReqVO createReqVO) {
        // 校验用户名是否存在
        validatePeopleExists(createReqVO.getPeople(), null);
        
        // 插入
        UserDO user = BeanUtils.toBean(createReqVO, UserDO.class);
        doubanUserMapper.insert(user);
        // 返回
        return user.getId();
    }

    @Override
    public void updateUser(UserUpdateReqVO updateReqVO) {
        // 校验存在
        validateUserExists(updateReqVO.getId());
        // 校验用户名是否存在
        validatePeopleExists(updateReqVO.getPeople(), updateReqVO.getId());
        
        // 更新
        UserDO updateObj = BeanUtils.toBean(updateReqVO, UserDO.class);
        doubanUserMapper.updateById(updateObj);
    }

    @Override
    public void deleteUser(Long id) {
        // 校验存在
        validateUserExists(id);
        // 删除
        doubanUserMapper.deleteById(id);
    }

    private void validateUserExists(Long id) {
        if (doubanUserMapper.selectById(id) == null) {
            throw exception(ErrorCodeConstants.USER_NOT_EXISTS);
        }
    }

    private void validatePeopleExists(String people, Long id) {
        UserDO user = doubanUserMapper.selectByPeople(people);
        if (user == null) {
            return;
        }
        // 如果 id 为空，说明不用比较是否为相同 id 的用户
        if (id == null) {
            throw exception(ErrorCodeConstants.USER_PEOPLE_EXISTS);
        }
        if (!user.getId().equals(id)) {
            throw exception(ErrorCodeConstants.USER_PEOPLE_EXISTS);
        }
    }

    @Override
    public UserDO getUser(Long id) {
        return doubanUserMapper.selectById(id);
    }

    @Override
    public List<UserDO> getUserList(Collection<Long> ids) {
        return doubanUserMapper.selectList("id", ids);
    }

    @Override
    public PageResult<UserDO> getUserPage(UserPageReqVO pageReqVO) {
        return doubanUserMapper.selectPage(pageReqVO);
    }

    @Override
    public List<UserDO> getUserList() {
        return doubanUserMapper.selectList();
    }

    @Override
    public UserDO getUserByUsername(String username) {
        return doubanUserMapper.selectByPeople(username);
    }

    @Override
    public UserDO getUserByPeople(String people) {
        return doubanUserMapper.selectByPeople(people);
    }
}