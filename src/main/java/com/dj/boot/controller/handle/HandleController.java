package com.dj.boot.controller.handle;

import com.dj.boot.common.base.BaseResponse;
import com.dj.boot.common.base.Request;
import com.dj.boot.common.base.Response;
import com.dj.boot.handlertest.aware.MyBeanFactoryAware;
import com.dj.boot.service.handle.QueryService;
import com.dj.boot.service.user.UserService;
import com.dj.boot.service.user.impl.UserServiceImpl;
import io.swagger.annotations.Api;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Api(value = "handle操作接口")
@RestController
@RequestMapping("/handle/")
public class HandleController {

    @Resource
    private QueryService queryService;

    /**
     * 枚举维护 operateType类型和bean实例名称 通过operateType 获取不同的实现类
     * 根据bean名称在IOC容器中获取bean
     * @return
     */
    @GetMapping("handleTest")
    public void handleTest(String operateType) {
        Request<T> request = new Request<>();
        //request.setOperateType(HandleTypeEnums.USER_PAGE_QUERY.getType());
        request.setOperateType(operateType);
        queryService.queryStock(request);

    }



    /**将实现类注解值和实现类 维护map 维护到spring容器
     * 根据传的type 找到map里面的value---> 获取指定的实现类
     * 通过实现类上的注解   获取不同的实现类
     * @return
     */
    @GetMapping("handlerType")
    public List<String> handlerType(String handlerType) {
        List<String> list = queryService.handlerType(handlerType);
        return list;
    }




    @GetMapping("test")
    public Response test() {
        UserService userService = (UserService) MyBeanFactoryAware.getBean("userServiceImpl");
        UserService userService1 = MyBeanFactoryAware.getBean(UserServiceImpl.class);
        Long count = userService.getCount();
        Long count1 = userService1.getCount();
        return Response.success(BaseResponse.SUCCESS_MESSAGE, count);
    }

}
