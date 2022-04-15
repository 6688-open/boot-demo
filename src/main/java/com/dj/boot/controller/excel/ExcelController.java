package com.dj.boot.controller.excel;

import com.dj.boot.common.base.BaseResponse;
import com.dj.boot.common.base.Response;
import com.dj.boot.common.csv.CSVExporter;
import com.dj.boot.common.csv.CsvExportUtil;
import com.dj.boot.common.excel.ps.ExportExcel;
import com.dj.boot.common.excel.exc.ExcelType;
import com.dj.boot.common.excel.exc.ExcelUtil;
import com.dj.boot.common.excel.exc.UserDto;
import com.dj.boot.common.excel.poi.ExcelAnalysisUtilsPlus;
import com.dj.boot.common.excel.poi.ReceiptsPerformImportDto;
import com.dj.boot.common.excel.poi.ReceiptsPerformItemDto;
import com.dj.boot.common.threadpoolutil.ThreadPoolUtils;
import com.dj.boot.common.util.date.DateUtil;
import com.dj.boot.controller.base.BaseController;
import com.dj.boot.pojo.User;
import com.dj.boot.service.pdf.PdfService;
import com.dj.boot.service.user.UserService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

@Api(value = "excel操作接口")
@RestController
@RequestMapping("/excel/")
@Slf4j
public class ExcelController extends BaseController {

    @Resource
    private PdfService pdfService;

    @Resource
    private UserService userService;

    /**
     * 订单导出
     */
    public static final String ERROR_DEVICE_ORDER_FILENAME = "订单";
    public static final String[] ERROR_DEVICE_ORDER_FIELDNAMES = {"主键","性别","姓名","密码","创建时间","日期"};
    public static final String[] ERROR_DEVICE_ORDER_FIELDPRENAMES = {"id","sex","userName","password","created","createTimeStr"};

    /**
     * 导出excel
     * @param response
     */
    @GetMapping("export")
    public void exportExcel(HttpServletResponse response){
        ExcelUtil<UserDto> excelUtil = new ExcelUtil<>(UserDto.class);
        List<UserDto> userDtos = new ArrayList<>();
        UserDto build1 = UserDto.builder().username("333").password("2222").id("222").build();
        UserDto build2 = UserDto.builder().username("333").password("2222").id("222").build();
        UserDto build3 = UserDto.builder().username("3333").password("2222").id("222").build();
        UserDto build4 = UserDto.builder().username("44444").password("2222").id("222").build();
        userDtos.add(build1);
        userDtos.add(build2);
        userDtos.add(build3);
        userDtos.add(build4);
        excelUtil.exportExcelToWeb(response, userDtos,"用户信息表", ExcelType.XLSX);
    }

    /**
     * 导入excel
     * @param file
     * @param appCode
     * @param operatorType
     * @return
     */
    @PostMapping("import")
    public Response importExcel(@RequestParam("file") MultipartFile file, String appCode, String operatorType){
        Response response = Response.success();
        if (file.isEmpty()) {
            response.setMsg("文件为空");
            response.setCode(400);
            return response;
        }
        switch (operatorType){
            case "1" :
                System.out.println(11111111);
                break;
            case "2" :
                System.out.println(777777);
                break;
        }
        try {
            long time1=System.currentTimeMillis();

            ExcelUtil<UserDto> excelUtil = new ExcelUtil<>(UserDto.class);
            List<UserDto> userDtos = excelUtil.importExcel(null, file.getInputStream());
            System.out.println(userDtos.size());

            StringBuffer sb = new StringBuffer();
            if (CollectionUtils.isNotEmpty(userDtos) && (userDtos.get(0).getId().startsWith("CWO") || userDtos.get(0).getId().startsWith("CPO"))) {
                String preFix = "'";
                String subFix = "'";
                sb.append("[");
                userDtos.forEach(userDto -> {
                    userDto.setId(preFix + userDto.getId() + subFix);
                });
                List<String> list = userDtos.stream().map(UserDto::getId).collect(Collectors.toList());

                String join = String.join(",", list);
                sb.append(join);
                sb.append("]");

                System.out.println(sb);
                Map<String, Object> map = new HashMap<>();
                map.put("eoNo", sb.toString());
                map.put("size", userDtos.size());
                return Response.success(BaseResponse.SUCCESS_MESSAGE, map);
            }



            List<User> userList = Lists.newArrayList();
            userDtos.forEach(userDto -> {
                User user = new User();
                user.setEmail("18351867657@163.com");
                user.setUserName(userDto.getUsername());
                user.setPassword(userDto.getPassword());
                user.setSex(1);
                user.setSalt("userDtos.forEach(userDto");
                user.setCreateTime(LocalDateTime.now());
                userList.add(user);
            });

            threadTasks(userList);
            //saveUser(userList);
            long time2=System.currentTimeMillis();
            logger.error("当前程序耗时："+(time2-time1)+"ms");

        } catch (Exception e) {
            logger.error("导入失败:{}", e.getMessage());
        }

        return  response;
    }

    private static final Integer BATCH_NUM = 10;

    private void threadTasks(List<User> list) {
        System.out.println("启动所有核心线程");
        //启动所有核心线程
        ThreadPoolUtils.getThreadPoolExecutor().prestartAllCoreThreads();
        //size向上取整
        int size = (int) Math.ceil((double) list.size() / (double) BATCH_NUM);

        CountDownLatch countDownLatch = new CountDownLatch(size);
        List<Future> futureList = new CopyOnWriteArrayList<>();
        // 分批异步处理 开启多个线程执行多个任务
        for (int i = 0; i < size; i++) {
            List<User> userList = list.stream().skip(i * BATCH_NUM).limit(BATCH_NUM).collect(Collectors.toList());

            Future<String> future = ThreadPoolUtils.getThreadPoolExecutor().submit(new doSaveUserInfo(userList, countDownLatch));
            futureList.add(future);
        }
        try {
            countDownLatch.await();
        } catch (Exception e) {
            logger.error("系统异常:{}", e.getMessage());
        }

        try {
            // 获取所有并发任务的运行结果
            for (Future f : futureList) {
                // 从Future对象上获取任务的返回值，并输出到控制台
                logger.error(">>>" + f.get().toString());
            }
        } catch (InterruptedException | ExecutionException e) {
            logger.error("系统出现异常:{}", e.getMessage());
        }

    }


    class doSaveUserInfo implements Callable<String> {

        private List<User> userList;
        private CountDownLatch countDownLatch;

        public doSaveUserInfo(List<User> userList, CountDownLatch countDownLatch) {
            this.userList = userList;
            this.countDownLatch = countDownLatch;
        }

        @Override
        public String call() throws Exception {
            try {
                Format f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String format = f.format(new Date());
                logger.error("当前线程名称-------"+Thread.currentThread().getName() + "时间 " + format + "数量:" + userList.size());

                saveUser(userList);

            } catch (Exception e) {
                logger.error("线程:,{} 异常原因,{}", Thread.currentThread().getName(), e.getMessage());

            } finally {
                countDownLatch.countDown();
            }
            return "当前线程名称-------"+Thread.currentThread().getName()  + "数量:" + userList.size();
        }
    }


    private void saveUser(List<User> userList){
        userList.forEach(user -> {
            userService.save(user);
        });
    }

    /**
     * 用户下载 导入ex工作表模板
     * @return
     */
    @GetMapping("uploadTemp")
    public void uploadTemp(HttpServletResponse response) {
        try {
            //从resource 下获取
            //InputStream inputStream = this.getClass().getResourceAsStream("/template/xlsx/Product.xlsx");
            //InputStream inputStream = new FileInputStream("D:/user.xlsx");
            InputStream inputStream = this.getClass().getResourceAsStream("/template/xlsx/User.xlsx");
            ExcelUtil.downloadTemplate(inputStream, response, "User.xlsx");
        } catch (Exception e) {
            logger.error("异常,{}", e.getMessage());
        }
    }

    /**
     * 创建空的excel  下载
     * @param response
     * @throws IOException
     */
    private void createWorkBookDownloadTemplate(HttpServletResponse response) throws IOException {
        //
        //创建工作表
        Workbook workbook = new XSSFWorkbook();
        //创建工作表
        Sheet sheet = workbook.createSheet();
        //设置表头
        Row row = sheet.createRow(0);
        row.createCell(0).setCellValue("商品名称名称");
        row.createCell(1).setCellValue("邮费");
        row.createCell(2).setCellValue("描述");
        row.createCell(3).setCellValue("商品类型名称");
        row.createCell(4).setCellValue("商品Sku属性名");
        row.createCell(5).setCellValue("商品Sku属性值(多个属性值用逗号分开)");
        row.createCell(6).setCellValue("商品Sku属性");
        // 设置response参数，可以打开下载页面
        response.reset();
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment;filename="+ new String(("商品导入模板表.xlsx").getBytes(), "iso-8859-1"));
        workbook.write(response.getOutputStream());
    }





    /**
     * 导出PDF
     * @return
     */
    @GetMapping("exportPdf")
    public void exportPdf(HttpServletResponse response) {
        try {
            pdfService.exportPdf(response);
        } catch (Exception e) {
            logger.error("异常,{}", e.getMessage());
        }
    }




    /*
     * 导入入库单
     * */
    @PostMapping(value = "importReceiptsPerform")
    public Response importGoodsCategory(@RequestParam MultipartFile importFile, HttpServletRequest request) {

        if (importFile == null){
            return  Response.error(BaseResponse.ERROR_PARAM, "文件不能为空");
        }
        try {
            List<List<List<String>>>  excleList = ExcelAnalysisUtilsPlus.analysisExcel(importFile.getInputStream());
            if (excleList == null || excleList.isEmpty() || excleList.size() <1){
                return Response.error(BaseResponse.ERROR_PARAM, "没有需要导入的数据");
            }
            List<List<String>> rows = excleList.get(0);
            if (rows == null || rows.isEmpty() || rows.size() < 0) {
                return Response.error(BaseResponse.ERROR_PARAM, "没有需要导入的数据");
            }

            List<ReceiptsPerformImportDto> receiptsPerformList = new ArrayList<>(rows.size());

            String errorMsg = null;
            for (int i = 0; i < rows.size(); i++) {
                List<String> row = rows.get(i);
                if (row == null) {
                    continue;
                }

                int rowNum = i + 2;

                ReceiptsPerformImportDto receiptsPerform = new ReceiptsPerformImportDto();

                if (row.size() <= 1 || StringUtils.isBlank(row.get(0))) {
                    errorMsg = "第" + rowNum + "行采购单号(防重码)为空";
                    break;
                }
                receiptsPerform.setReferenceReceiptsNo(row.get(0).trim());


                if (row.size() <= 2 || StringUtils.isBlank(row.get(1))) {
                    errorMsg = "第" + rowNum + "行仓库名称为空";
                    break;
                }
                receiptsPerform.setWarehouseName(row.get(1).trim());


                List<ReceiptsPerformItemDto> receiptsPerformItemDtoList = new ArrayList<>(rows.size());
                ReceiptsPerformItemDto receiptsPerformItemDto = new ReceiptsPerformItemDto();
                if (row.size() <= 3 || StringUtils.isBlank(row.get(2))) {
                    errorMsg = "第" + rowNum + "行物资名称为空";
                    break;
                }
                receiptsPerformItemDto.setGoodsName(row.get(2).trim());

                if (row.size() <= 4 || StringUtils.isBlank(row.get(3))) {
                    errorMsg = "第" + rowNum + "行机构名称为空";
                    break;
                }
                receiptsPerform.setOrgName(row.get(3).trim());

                if (row.size() <= 5 || Integer.parseInt(row.get(4)) <= 0) {
                    errorMsg = "第" + rowNum + "行采购类型为空";
                    break;
                }
                receiptsPerform.setOrderType(Integer.valueOf(row.get(4).trim()));

                if (row.size() <= 6 || StringUtils.isBlank(row.get(5))) {
                    errorMsg = "第" + rowNum + "商品数量不能为空";
                    break;
                }
                receiptsPerformItemDto.setGoodsApplyQty(new BigDecimal(row.get(5).trim()));


                if (row.size() <= 7 && StringUtils.isNotBlank(row.get(6))) {
                    errorMsg = "第" + rowNum + "计划完成日期为空";
                    break;
                }
                receiptsPerform.setPlanCompleteTime(new Date());


                if (row.size() <= 8 || Integer.parseInt(row.get(7)) <= 0) {
                    errorMsg = "第" + rowNum + "行来源为空";
                    break;
                }
                receiptsPerform.setSource(Integer.valueOf(row.get(7).trim()));

                if (row.size() <= 9) {
                    errorMsg = "第" + rowNum + "行生产日期为空";
                    break;
                }
                receiptsPerformItemDto.setProductTime(StringUtils.trim(row.get(8)));
                //默认良品
                receiptsPerformItemDto.setGoodsLevel("100");

                receiptsPerformItemDtoList.add(receiptsPerformItemDto);

                receiptsPerform.setOrderSource((byte) 3);
                receiptsPerform.setReceiptsPerformItemDtoList(receiptsPerformItemDtoList);
                receiptsPerformList.add(receiptsPerform);
            }
        } catch (Exception e) {
            logger.error("导入入库单，系统出错，错误信息："+ e);
        }
        
        return Response.success("成功");
    }












    /**
     * 导出异常报备单
     *
     * @param response
     * @param condition 导出条件
     * @throws IOException
     * @throws IllegalAccessException
     * @throws NoSuchFieldException
     */
    @RequestMapping(value = "exportErrorDeviceOrderDetail")
    public void exportErrorDeviceOrderDetail(HttpServletRequest request, HttpServletResponse response, com.dj.boot.pojo.UserDto condition) throws IOException, IllegalAccessException, NoSuchFieldException {
        try {
            List<User> userList = userService.findUserListByCondition(condition);
            userList.forEach(user -> {
                user.setCreated(new Date());
                user.setCreateTimeStr(DateUtil.format(new Date()));
            });
            //导出excel开始
            ExportExcel.exportExcel(response, ERROR_DEVICE_ORDER_FILENAME, ERROR_DEVICE_ORDER_FIELDNAMES, ERROR_DEVICE_ORDER_FIELDPRENAMES, userList);
        } catch (Exception e) {
            logger.error("异常报备单导出异常:", e);
        }
    }


    /**
     * https://blog.csdn.net/qq_33355858/article/details/90044222
     * @param request
     * @param response
     */
    @RequestMapping(value = "exportCsv",method = RequestMethod.GET)
    public void exportCsv(HttpServletRequest request, HttpServletResponse response){
        File tmpFile = null;
        try {
            Field[] columnFields = CsvExportUtil.createColumnFileds(ERROR_DEVICE_ORDER_FIELDPRENAMES, User.class);
            List<User> userList = userService.findUserListByCondition(new com.dj.boot.pojo.UserDto());
            tmpFile = File.createTempFile("user-export-template", ".csv");

            boolean  flag = false;
            long startTime = new Date().getTime();

            while (new Date().getTime()-startTime <= 3000 && !flag){//执行3s
                logger.error("时间:{},进入循环", new Date().getTime()-startTime);
                CsvExportUtil.createCSVFile(tmpFile, columnFields, ERROR_DEVICE_ORDER_FIELDNAMES, userList);
                CsvExportUtil.appendCSVFile(tmpFile, columnFields, ERROR_DEVICE_ORDER_FIELDNAMES, userList);
                //Thread.sleep(6000);
                flag = true;
            }

            if (flag) {
                log.error("异步任务导出完成");
                //将文件临时存储文件服务器
                String key = "export/user/user-export-" + "wangjia".hashCode() + "-" + new Date().getTime() + ".csv";
                uploadFile(tmpFile, key);
            } else {
                log.error("异步任务执行超时,线程:"+Thread.currentThread().getName()+"线程执行时间:"+(new Date().getTime() - startTime)+"ms");
            }

            //setResponse(tmpFile, response);
            setResponse2(tmpFile, response);
        } catch (Exception ex) {
            logger.error("导出失败",ex);
            //异常的话需要记录数据
        } finally {
            //删除文件
            FileUtils.deleteQuietly(tmpFile);
        }

    }

    public void uploadFile(File file, String key) {
        try {
            log.error("上传文件成功");
        } catch (Exception e) {
            log.error("上传文件异常", e);
            throw new RuntimeException("上传文件异常");
        }
    }


    public void setResponse(File tmpFile, HttpServletResponse response) throws IOException {
        OutputStream out = response.getOutputStream();
        byte[] b = new byte[10240];
        File fileLoad = new File(tmpFile.getCanonicalPath());
        response.reset();
        response.setContentType("application/csv");
//            SimpleDateFormat fdate=new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
//            String trueCSVName=fdate.format(new Date())+".csv";
        String trueCSVName="导出数据.csv";
        response.setHeader("Content-Disposition", "attachment;  filename="+ new String(trueCSVName.getBytes("GBK"), "ISO8859-1"));
        long fileLength = fileLoad.length();
        String length1 = String.valueOf(fileLength);
        response.setHeader("Content_Length", length1);
        FileInputStream in = new FileInputStream(fileLoad);
        int n;
        while ((n = in.read(b)) != -1) {
            out.write(b, 0, n); // 每次写入out1024字节
        }
        in.close();
        out.close();
    }




    public void setResponse2(File tmpFile, HttpServletResponse response) throws IOException {
        // 1 写入CSV文件直接浏览器下载
        // 2 假如存在云存储 根据文件url在文件存储获取文件 下载
        CsvExportUtil.setResponseParam(response, ERROR_DEVICE_ORDER_FILENAME);
        //File fileLoad = new File(tmpFile.getCanonicalPath());
        FileInputStream in = new FileInputStream(tmpFile);
        try {
            CsvExportUtil.writeResponse(in, response.getOutputStream());
        } catch (Exception e) {
            logger.error("导出失败", e);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    logger.error("关闭流异常", e);
                }
            }

        }
    }


    /**
     * http://localhost:8082/excel/exportCsvFile
     * @param request
     * @param response
     */
    @GetMapping(value = "exportCsvFile")
    public void exportCsvFile(HttpServletRequest request, HttpServletResponse response){
        File tmpFile = null;
        OutputStream output = null;
        InputStream input = null;
        response.setHeader("Content-Type","application/csv");
        response.addHeader("Content-Disposition", "attachment;filename=".concat(new String("赔付单列表.csv".getBytes(StandardCharsets.UTF_8), Charset.forName("ISO8859-1"))));
        try {
            Field[] columnFields = CsvExportUtil.createColumnFileds(ERROR_DEVICE_ORDER_FIELDPRENAMES, User.class);
            List<User> userList = userService.findUserListByCondition(new com.dj.boot.pojo.UserDto());
            tmpFile = File.createTempFile("user-export-template", ".csv");
            CsvExportUtil.createCSVFile(tmpFile, columnFields, ERROR_DEVICE_ORDER_FIELDNAMES, userList);
            try {
                input = new FileInputStream(tmpFile);
                output=response.getOutputStream();
                IOUtils.copyLarge(input,output);
            } catch (IOException e) {
                logger.error(String.valueOf(e));
            }finally {
                IOUtils.closeQuietly(input);
                IOUtils.closeQuietly(output);
            }
        } catch (Exception ex) {
            logger.error("导出失败",ex);
        }

    }

    /**
     * 导出CSV  xlsx。。。。。。
     * http://localhost:8082/excel/fetchExportData
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "fetchExportData",method=RequestMethod.GET)
    public void fetchExportData(HttpServletResponse response, String key) throws Exception {
        OutputStream output = null;
        InputStream input = null;
        //获取相对路径文件的InputStream
        input = this.getClass().getResourceAsStream("/template/xlsx/Product.xlsx");
        //根据文件地址获取InputStream
        //input = getInputStreamByPath();

        response.setHeader("Content-Type","application/csv");
        response.addHeader("Content-Disposition", "attachment;filename=".concat(new String("赔付单列表.xlsx".getBytes(StandardCharsets.UTF_8), Charset.forName("ISO8859-1"))));
        try {
            output=response.getOutputStream();
            IOUtils.copyLarge(input,output);
        } catch (IOException e) {
            logger.error(String.valueOf(e));
        }finally {
            IOUtils.closeQuietly(input);
            IOUtils.closeQuietly(output);
        }
    }

    private InputStream getInputStreamByPath() throws Exception {
        InputStream input = null;
        String path = "http://www.baidu.com/file/name.xlsx";
        URL url = new URL(path);
        try {
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();//利用HttpURLConnection对象,我们可以从网络中获取网页数据.
            conn.setDoInput(true);
            conn.connect();
            input = conn.getInputStream();	//得到网络返回的输入流
        } catch (IOException e) {
            logger.error("获取InputStream异常", e);
        }
        return input;
    }


    /**
     * 导出CSV文件
     * @param request
     * @param re
     * @throws Exception
     */
    @RequestMapping(value = "exportCompensateList")
    //@ResponseBody
    public void exportCompensateList(HttpServletRequest request, HttpServletResponse re) throws Exception {
        //Response<Object> response = Response.success();
        com.dj.boot.pojo.UserDto userDto = new com.dj.boot.pojo.UserDto();
        List<User> userList = userService.findUserListByCondition(userDto);
        List<UserDto> userDtoList = Lists.newArrayList();
        userList.forEach(user -> {
            UserDto u = new UserDto();
            try {
                BeanUtils.copyProperties(u, user);
                u.setUsername(user.getUserName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            userDtoList.add(u);

        });
        CSVExporter<UserDto> exporter = null;
        File temp = null;
        try {
            final String key = String.format("export/compensate/p-export/%s-%s.csv", "wj", new Date().getTime());
            temp = File.createTempFile(key, "tmp");
            final OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(temp, true), "GB2312");
            exporter = CSVExporter.newExporter(UserDto.class, writer);
            exporter.export(userDtoList);
            //上传文件服务器返回key  保存key或者返回key 调用fetchExportData2根据key获取文件
            //response.setData(key);

            //直接导出
            exportCsvFinal(re, temp);
        } catch (Exception e) {
            logger.error("导出失败",e);
        }
//        finally {
//            FileUtils.deleteQuietly(temp);
//            IOUtils.closeQuietly(exporter);
//        }
    }

    private void exportCsvFinal(HttpServletResponse response, File tmpFile) {
        //根据key 从文件服务器获取文件
        response.setHeader("Content-Type","application/csv");
        response.setHeader("Content-Length",String.valueOf(tmpFile.length()));
        response.addHeader("Content-Disposition", "attachment;filename="
                .concat(new String("赔付单列表.csv".getBytes(StandardCharsets.UTF_8), Charset.forName("ISO8859-1"))));
        OutputStream output=null;
        InputStream input=null;
        try {
            input = new FileInputStream(tmpFile);
            output=response.getOutputStream();
            IOUtils.copyLarge(input,output);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }finally {
            IOUtils.closeQuietly(input);
            IOUtils.closeQuietly(output);
        }
    }

    @RequestMapping(value = "/fetchExportData2",method=RequestMethod.GET)
    public void fetchExportData2(HttpServletResponse response, String key){
        //根据key 从文件服务器获取文件
        response.setHeader("Content-Type","application/csv");
        //response.setHeader("Content-Length",String.valueOf(obj.getContentLength()));
        response.addHeader("Content-Disposition", "attachment;filename="
                .concat(new String("赔付单列表.csv".getBytes(StandardCharsets.UTF_8), Charset.forName("ISO8859-1"))));
        OutputStream output=null;
        InputStream input=null;
        try {
            //input=obj.getInputStream();
            output=response.getOutputStream();
            IOUtils.copyLarge(input,output);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }finally {
            IOUtils.closeQuietly(input);
            IOUtils.closeQuietly(output);
        }
    }
}
