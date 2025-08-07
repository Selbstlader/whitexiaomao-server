package cn.iocoder.yudao.module.douban.convert.user;

import cn.iocoder.yudao.module.douban.controller.app.user.vo.UserRespVO;
import cn.iocoder.yudao.module.douban.dal.dataobject.UserDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 豆瓣用户 App Convert
 */
@Mapper
public interface UserAppConvert {

    UserAppConvert INSTANCE = Mappers.getMapper(UserAppConvert.class);

    UserRespVO convert(UserDO bean);

    List<UserRespVO> convertList(List<UserDO> list);

}