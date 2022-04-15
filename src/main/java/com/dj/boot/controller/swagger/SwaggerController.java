package com.dj.boot.controller.swagger;

import com.dj.boot.common.base.BaseResponse;
import com.dj.boot.common.base.Response;
import com.dj.boot.controller.base.BaseController;
import com.dj.boot.pojo.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * http://localhost:8082/swagger-ui.html
 * @ClassName SwaggerController
 * @Description TODO
 * @Author wj
 * @Date 2019/12/5 14:33
 * @Version 1.0
 **/
@RestController
@RequestMapping("/swagger/")
@Api("swagger 测试接口 ")
public class SwaggerController extends BaseController {

    @ApiOperation(value="多个参数说明", notes="多个参数说明")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "签约根据标id查询", paramType = "query",  required = true, dataType = "long"),
            @ApiImplicitParam(name = "token", value = "token", paramType = "query",  required = true , dataType = "string")
    })
    @PostMapping("sureOrder")
    public Response sureOrder(String token, Integer id){
        try {
            HashMap<String, Object> map = new HashMap<>();
            map.put("id", id);
            map.put("token", token);
            return Response.success("签约成功", map);
        } catch (Exception e) {
            logger.error("系统异常:{}", e.getMessage());
            return Response.error(BaseResponse.ERROR_SYSTEM, "异常"+e.getMessage());
        }
    }


    @ApiOperation(value="单个参数说明", notes="单个参数说明")
    @ApiImplicitParam(name = "token", value = "token", paramType = "query", required = true , dataType = "string")
    @GetMapping("swaggerTest")
    public Response swaggerTest( @RequestParam(name = "token") String token){
        try {
            HashMap<String, Object> map = new HashMap<>();
            map.put("token", token);
            return Response.success("签约成功", map);
        } catch (Exception e) {
            logger.error("系统异常:{}", e.getMessage());
            return Response.error(BaseResponse.ERROR_SYSTEM, "异常"+e.getMessage());
        }
    }



    /**
     * 用户注册 测试实体类接参数
     * @return
     */
    @ApiOperation(value=" 测试实体类接参数", notes=" 测试实体类接参数  Json 传参")
    @ApiImplicitParam(name = "user", value = "用户实体", required = true, dataType = "User")
    @PostMapping("register")
    public Response register(@RequestBody User user){
        try {
        HashMap<String, Object> map = new HashMap<>();
        //断言非空判断
        Assert.notNull(user.getUserName(), "null");
            map.put("user",user);
            map.put("token","token");
            return Response.success("success", map);
        } catch (Exception e) {
            logger.error("系统异常:{}", e.getMessage());
            return Response.error(BaseResponse.ERROR_SYSTEM, "异常"+e.getMessage());
        }

    }
}
