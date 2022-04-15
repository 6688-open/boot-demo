package com.dj.boot.controller.base;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.dj.boot.common.base.BaseResponse;
import com.dj.boot.common.base.Response;
import com.dj.boot.common.util.StringUtils;
import com.dj.boot.common.util.collection.CollectionUtils;
import com.dj.boot.pojo.base.BaseData;
import com.dj.boot.pojo.errorreason.ErrorReason;
import com.dj.boot.service.base.BaseDataService;
import com.dj.boot.service.errorreason.ErrorReasonService;
import org.apache.commons.compress.utils.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author wangjia
 */
@RestController
@RequestMapping("/baseData/")
public class BaseDataController extends BaseController{

    @Autowired
    private ErrorReasonService errorReasonService;
    @Autowired
    private BaseDataService baseDataService;



    @RequestMapping(value = "getErrorReasonList", method = RequestMethod.POST)
    public  Response getErrorReasonList(String id) {
        Response response = Response.success("异步加载数据成功!");
        try {
            ErrorReason condition = new ErrorReason();
            if(StringUtils.isBlank(id) || "dict_root".equals(id)){
                //condition.setParentCode(-1L);
            }else {
                condition.setParentCode(Long.parseLong(id));
            }
            List<ErrorReason> errorDeviceReasonList = errorReasonService.findErrorReasonListByCondition(condition);
            response.setData(errorDeviceReasonList);
        } catch (Exception e) {
            logger.error("异步加载设备异常备注，出现异常:", e);
            response.setCode(BaseResponse.ERROR_BUSINESS);
            response.setMsg("异步加载设备异常备注，出现异常!--->"+ e.getMessage());
            return response;
        }
        return response;
    }

    @RequestMapping(value = "addErrorReason", method = RequestMethod.POST)
    public Response addErrorReason(ErrorReason errorReason) {
        Response response = Response.success();
        try {
            if (errorReason == null) {
                response.setCode(BaseResponse.ERROR_BUSINESS);
                response.setMsg("入参实体不能为空");
                return response;
            }
            if (errorReason.getReportName() == null || errorReason.getParentCode() == null) {
                response.setCode(BaseResponse.ERROR_BUSINESS);
                response.setMsg("当前编码和父级编码不能为空");
                return response;
            }
            errorReason.setIsDelete((byte) 1);
            errorReason.setCreateUser("SYSTEM");
            errorReason.setUpdateUser("SYSTEM");
            errorReasonService.insertErrorReason(errorReason);
        }catch (RuntimeException e){
            logger.error("addErrorDeviceReason error",e);
            response.setCode(BaseResponse.ERROR_BUSINESS);
            response.setMsg("添加异常------>"+e.getMessage());
        }
        return response;
    }

    @RequestMapping(value = "removeErrorReason", method = RequestMethod.POST)
    public Response removeErrorReason(ErrorReason errorReason) {
        Response response = Response.success();
        try {
            if (errorReason == null) {
                response.setCode(BaseResponse.ERROR_BUSINESS);
                response.setMsg("入参实体不能为空");
                return response;
            }
            if (errorReason.getId() == null) {
                response.setCode(BaseResponse.ERROR_BUSINESS);
                response.setMsg("当前节点ID必填");
                return response;
            }
            List<Long> removeIds = getRemoveIds(errorReason);
            boolean b = errorReasonService.removeByIds(removeIds);
            if(!b) {
                response.setCode(BaseResponse.ERROR_PARAM);
                response.setMsg("删除失败");
                return response;
            }
            return response;
        }catch (RuntimeException e){
            logger.error("addErrorDeviceReason error",e);
            response.setCode(BaseResponse.ERROR_BUSINESS);
            response.setMsg("系统执行异常---->"+ e.getMessage());
            return response;
        }
    }

    private List<Long> getRemoveIds(ErrorReason errorReason) {
        List<Long> ids = Lists.newArrayList();
        ids.add(errorReason.getId()); //删除当前节点
        //获取当前节点的所有子节点
        QueryWrapper<ErrorReason> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_code", errorReason.getId());
        List<ErrorReason> list = errorReasonService.list(queryWrapper);
        getChildIds(list, ids);
        return ids;
    }
    private void getChildIds(List<ErrorReason> list, List<Long> ids) {
        list.forEach(errorReason ->{
            ids.add(errorReason.getId());
            QueryWrapper<ErrorReason> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("parent_code", errorReason.getId());
            List<ErrorReason> childList = errorReasonService.list(queryWrapper);
            if(CollectionUtils.isNotEmpty(childList)) {
                getChildIds(childList, ids);
            }
        });
    }

    @RequestMapping(value = "updateErrorReason", method = RequestMethod.POST)
    public Response updateErrorDeviceReason(ErrorReason errorReason) {
        Response response = Response.success();
        try {
            if (errorReason == null) {
                response.setCode(BaseResponse.ERROR_BUSINESS);
                response.setMsg("入参实体不能为空");
                return response;
            }
            if (errorReason.getId() == null ) {
                response.setCode(BaseResponse.ERROR_BUSINESS);
                response.setMsg("节点id不能为空");
                return response;
            }
            if (errorReason.getReportName() == null || errorReason.getParentCode() == null) {
                response.setCode(BaseResponse.ERROR_BUSINESS);
                response.setMsg("当前编码和父级编码不能为空");
                return response;
            }
            errorReasonService.updateErrorReason(errorReason);
            return response;
        }catch (RuntimeException e){
            logger.error("updateErrorDeviceReason error",e);
            response.setCode(BaseResponse.ERROR_BUSINESS);
            response.setMsg("系统执行异常");
            return response;
        }
    }

    @RequestMapping(value = "gotoErrorReasonDetailPage", method = RequestMethod.GET)
    public Response gotoErrorReasonDetailPage(Long id) throws Exception {
        Response response = Response.success();
        try {
            if (id == null) {
                response.setCode(BaseResponse.ERROR_BUSINESS);
                response.setMsg("节点id不能为空");
                return response;
            }
            ErrorReason errorDeviceReason = errorReasonService.getErrorReason(id);
            response.setData(errorDeviceReason);
        }catch (RuntimeException e){
            logger.error("updateErrorDeviceReason error",e);
            response.setCode(BaseResponse.ERROR_BUSINESS);
            response.setMsg("系统执行异常");
            return response;
        }
        return response;
    }



    @PostMapping("updateName")
    public Response updateName(String name, Integer id){
        Map<String, Object> map = new HashMap<>();
        Response response = Response.success();
        try {
            UpdateWrapper<BaseData> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("id", id);
            updateWrapper.set("name", name);
            boolean i = baseDataService.update(updateWrapper);
            if (!i) {
                response.setCode(BaseResponse.ERROR_PARAM);
                response.setMsg("修改失败");
                return response;
            }
        } catch (Exception e) {
            logger.error("出现异常", e);
            response.setMsg("出现异常");
        }
        return response;
    }








    //异步展示
    @PostMapping("ztreeShow")
    public List<BaseData> ztreeShow() {
        try {
            List<BaseData> list = baseDataService.list();

            list.forEach(baseData ->{
                QueryWrapper<BaseData> queryWrapper1 = new QueryWrapper<>();
                queryWrapper1.eq("parent_id", baseData.getId());
                List<BaseData> list1 = baseDataService.list(queryWrapper1);
                if(null != list1 && list1.size()>0) {
                    baseData.setParent(true);
                }
            });
            return list;
        } catch (Exception e) {
            logger.error("服务器异常{}", e.getMessage());
            return null;//提示
        }

    }




    //异步展示
    @PostMapping("ztreeDifferShow")
    public List<BaseData> ztreeDifferShow(Integer id) {
        try {
            QueryWrapper<BaseData> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("parent_id", id = id == null ? 0 :id);
            List<BaseData> list = baseDataService.list(queryWrapper);

            list.forEach(baseData ->{
                QueryWrapper<BaseData> queryWrapper1 = new QueryWrapper<>();
                queryWrapper1.eq("parent_id", baseData.getId());
                List<BaseData> list1 = baseDataService.list(queryWrapper1);
                if(null != list1 && list1.size()>0) {
                    baseData.setParent(true);
                }
            });
            return list;
        } catch (Exception e) {
            logger.error("服务器异常{}", e.getMessage());
            return null;//提示
        }

    }




    //异步删除   后台递归
    @PostMapping("delete")
    public Response delete(Integer id) {
        Response<Object> response = Response.success();
        try {
            List<Integer> ids = new ArrayList<>();//声明集合
            ids.add(id);
            QueryWrapper<BaseData> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("parent_id", id);
            List<BaseData> list = baseDataService.list(queryWrapper);
            getChild(list, ids);
            boolean b = baseDataService.removeByIds(ids);
            if(!b) {
                response.setCode(BaseResponse.ERROR_PARAM);
                response.setMsg("删除失败");
                return response;
            }
        } catch (Exception e) {
            logger.error("服务器异常{}", e.getMessage());
            response.setMsg("服务器异常");
            return response;//提示
        }
        return response;
    }


    private void getChild(List<BaseData> list, List<Integer> ids) {
        list.forEach(baseData ->{
            ids.add(baseData.getId());
            QueryWrapper<BaseData> queryWrapper1 = new QueryWrapper<>();
            queryWrapper1.eq("parent_id", baseData.getId());
            List<BaseData> list1 = baseDataService.list(queryWrapper1);
            if(null != list1 && list1.size()>0) {
                getChild(list1, ids);
            }
        });
    }





    //异步删除   前台递归
//    @PostMapping("update")
//		public Response update(Integer[] ids) {
//			try {
//				List<Integer> list = new ArrayList<>();//声明集合
//				//foreach遍历
//				for (Integer in : ids) {
//					list.add(in);
//				}
//				UpdateWrapper<BaseData> uWrapper = new UpdateWrapper<>();//修改条件封装
//				uWrapper.in("id", list);//条件差
//				//uWrapper.set("is_delete", SystemConstant.NUMBER_ONE);//改
//				//boolean update = baseDataService.update(uWrapper);//执行语句
//				boolean remove = baseDataService.remove(uWrapper);
//				if(remove == true)//判断是否正确
//					return Response.success("删除成功");//页面提示
//				return Response.error("删除失败");//页面提示
//			} catch (Exception e) {
//				e.printStackTrace();
//				return Response.error("服务器异常" + e.getMessage());//提示
//			}
//		}









}
