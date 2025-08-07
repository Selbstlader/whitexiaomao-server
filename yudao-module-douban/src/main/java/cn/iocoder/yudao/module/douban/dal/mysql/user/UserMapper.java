package cn.iocoder.yudao.module.douban.dal.mysql.user;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.douban.controller.admin.user.vo.UserPageReqVO;
import cn.iocoder.yudao.module.douban.dal.dataobject.UserDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 豆瓣用户 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface UserMapper extends BaseMapperX<UserDO> {

    default PageResult<UserDO> selectPage(UserPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<UserDO>()
                .likeIfPresent(UserDO::getPeople, reqVO.getPeople())
                .likeIfPresent(UserDO::getLocation, reqVO.getLocation())
                .geIfPresent(UserDO::getFriendCount, reqVO.getMinFriendCount())
                .geIfPresent(UserDO::getBeAttentionCount, reqVO.getMinAttentionCount())
                .orderByDesc(UserDO::getId));
    }

    default UserDO selectByPeople(String people) {
        return selectOne(UserDO::getPeople, people);
    }
}