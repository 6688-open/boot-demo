package com.dj.boot.controller.excel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
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
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

@Api(value = "excel????????????")
@RestController
@RequestMapping("/excel/")
@Slf4j
public class ExcelController extends BaseController {

    @Resource
    private PdfService pdfService;

    @Resource
    private UserService userService;

    /**
     * ????????????
     */
    public static final String ERROR_DEVICE_ORDER_FILENAME = "??????";
    public static final String[] ERROR_DEVICE_ORDER_FIELDNAMES = {"??????","??????","??????","??????","????????????","??????"};
    public static final String[] ERROR_DEVICE_ORDER_FIELDPRENAMES = {"id","sex","userName","password","created","createTimeStr"};

    /**
     * ??????excel
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
        /*excelUtil.exportExcelToWeb(response, userDtos,"???????????????", ExcelType.XLSX);*/
        ExcelWriter excelWriter = null;
        try {
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            String fileName = URLEncoder.encode("???????????????", "UTF-8");
            response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ExcelType.XLSX.getType());
            excelWriter = EasyExcel.write(response.getOutputStream(), UserDto.class).build();
            WriteSheet writeSheet = EasyExcel.writerSheet("???????????????").build();
            excelWriter.write(userDtos, writeSheet);
            log.error("????????????");
        } catch (Exception e) {
            log.error("?????????????????????" + e.getMessage());
        } finally {
            // ??????
            if (excelWriter != null) {
                excelWriter.finish();
            }
        }
    }

    /**
     * ??????excel
     * @param file
     * @param appCode
     * @param operatorType
     * @return
     */
    @PostMapping("import")
    public Response importExcel(@RequestParam("file") MultipartFile file, String appCode, String operatorType){
        Response response = Response.success();
        if (file.isEmpty()) {
            response.setMsg("????????????");
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
            logger.error("?????????????????????"+(time2-time1)+"ms");

        } catch (Exception e) {
            logger.error("????????????:{}", e.getMessage());
        }

        return  response;
    }

    private static final Integer BATCH_NUM = 10;

    private void threadTasks(List<User> list) {
        System.out.println("????????????????????????");
        //????????????????????????
        ThreadPoolUtils.getThreadPoolExecutor().prestartAllCoreThreads();
        //size????????????
        int size = (int) Math.ceil((double) list.size() / (double) BATCH_NUM);

        CountDownLatch countDownLatch = new CountDownLatch(size);
        List<Future> futureList = new CopyOnWriteArrayList<>();
        // ?????????????????? ????????????????????????????????????
        for (int i = 0; i < size; i++) {
            List<User> userList = list.stream().skip(i * BATCH_NUM).limit(BATCH_NUM).collect(Collectors.toList());

            Future<String> future = ThreadPoolUtils.getThreadPoolExecutor().submit(new doSaveUserInfo(userList, countDownLatch));
            futureList.add(future);
        }
        try {
            countDownLatch.await();
        } catch (Exception e) {
            logger.error("????????????:{}", e.getMessage());
        }

        try {
            // ???????????????????????????????????????
            for (Future f : futureList) {
                // ???Future?????????????????????????????????????????????????????????
                logger.error(">>>" + f.get().toString());
            }
        } catch (InterruptedException | ExecutionException e) {
            logger.error("??????????????????:{}", e.getMessage());
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
                logger.error("??????????????????-------"+Thread.currentThread().getName() + "?????? " + format + "??????:" + userList.size());

                saveUser(userList);

            } catch (Exception e) {
                logger.error("??????:,{} ????????????,{}", Thread.currentThread().getName(), e.getMessage());

            } finally {
                countDownLatch.countDown();
            }
            return "??????????????????-------"+Thread.currentThread().getName()  + "??????:" + userList.size();
        }
    }


    private void saveUser(List<User> userList){
        userList.forEach(user -> {
            userService.save(user);
        });
    }

    /**
     * ???????????? ??????ex???????????????
     * @return
     */
    @GetMapping("uploadTemp")
    public void uploadTemp(HttpServletResponse response) {
        try {
            //???resource ?????????
            //InputStream inputStream = this.getClass().getResourceAsStream("/template/xlsx/Product.xlsx");
            //InputStream inputStream = new FileInputStream("D:/user.xlsx");
            InputStream inputStream = this.getClass().getResourceAsStream("/template/xlsx/User.xlsx");
            ExcelUtil.downloadTemplate(inputStream, response, "User.xlsx");
        } catch (Exception e) {
            logger.error("??????,{}", e.getMessage());
        }
    }

    /**
     * ????????????excel  ??????
     * @param response
     * @throws IOException
     */
    private void createWorkBookDownloadTemplate(HttpServletResponse response) throws IOException {
        //
        //???????????????
        Workbook workbook = new XSSFWorkbook();
        //???????????????
        Sheet sheet = workbook.createSheet();
        //????????????
        Row row = sheet.createRow(0);
        row.createCell(0).setCellValue("??????????????????");
        row.createCell(1).setCellValue("??????");
        row.createCell(2).setCellValue("??????");
        row.createCell(3).setCellValue("??????????????????");
        row.createCell(4).setCellValue("??????Sku?????????");
        row.createCell(5).setCellValue("??????Sku?????????(??????????????????????????????)");
        row.createCell(6).setCellValue("??????Sku??????");
        // ??????response?????????????????????????????????
        response.reset();
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment;filename="+ new String(("?????????????????????.xlsx").getBytes(), "iso-8859-1"));
        workbook.write(response.getOutputStream());
    }





    /**
     * ??????PDF
     * @return
     */
    @GetMapping("exportPdf")
    public void exportPdf(HttpServletResponse response) {
        try {
            pdfService.exportPdf(response);
        } catch (Exception e) {
            logger.error("??????,{}", e.getMessage());
        }
    }




    /*
     * ???????????????
     * */
    @PostMapping(value = "importReceiptsPerform")
    public Response importGoodsCategory(@RequestParam MultipartFile importFile, HttpServletRequest request) {

        if (importFile == null){
            return  Response.error(BaseResponse.ERROR_PARAM, "??????????????????");
        }
        try {
            List<List<List<String>>>  excleList = ExcelAnalysisUtilsPlus.analysisExcel(importFile.getInputStream());
            if (excleList == null || excleList.isEmpty() || excleList.size() <1){
                return Response.error(BaseResponse.ERROR_PARAM, "???????????????????????????");
            }
            List<List<String>> rows = excleList.get(0);
            if (rows == null || rows.isEmpty() || rows.size() < 0) {
                return Response.error(BaseResponse.ERROR_PARAM, "???????????????????????????");
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
                    errorMsg = "???" + rowNum + "???????????????(?????????)??????";
                    break;
                }
                receiptsPerform.setReferenceReceiptsNo(row.get(0).trim());


                if (row.size() <= 2 || StringUtils.isBlank(row.get(1))) {
                    errorMsg = "???" + rowNum + "?????????????????????";
                    break;
                }
                receiptsPerform.setWarehouseName(row.get(1).trim());


                List<ReceiptsPerformItemDto> receiptsPerformItemDtoList = new ArrayList<>(rows.size());
                ReceiptsPerformItemDto receiptsPerformItemDto = new ReceiptsPerformItemDto();
                if (row.size() <= 3 || StringUtils.isBlank(row.get(2))) {
                    errorMsg = "???" + rowNum + "?????????????????????";
                    break;
                }
                receiptsPerformItemDto.setGoodsName(row.get(2).trim());

                if (row.size() <= 4 || StringUtils.isBlank(row.get(3))) {
                    errorMsg = "???" + rowNum + "?????????????????????";
                    break;
                }
                receiptsPerform.setOrgName(row.get(3).trim());

                if (row.size() <= 5 || Integer.parseInt(row.get(4)) <= 0) {
                    errorMsg = "???" + rowNum + "?????????????????????";
                    break;
                }
                receiptsPerform.setOrderType(Integer.valueOf(row.get(4).trim()));

                if (row.size() <= 6 || StringUtils.isBlank(row.get(5))) {
                    errorMsg = "???" + rowNum + "????????????????????????";
                    break;
                }
                receiptsPerformItemDto.setGoodsApplyQty(new BigDecimal(row.get(5).trim()));


                if (row.size() <= 7 && StringUtils.isNotBlank(row.get(6))) {
                    errorMsg = "???" + rowNum + "????????????????????????";
                    break;
                }
                receiptsPerform.setPlanCompleteTime(new Date());


                if (row.size() <= 8 || Integer.parseInt(row.get(7)) <= 0) {
                    errorMsg = "???" + rowNum + "???????????????";
                    break;
                }
                receiptsPerform.setSource(Integer.valueOf(row.get(7).trim()));

                if (row.size() <= 9) {
                    errorMsg = "???" + rowNum + "?????????????????????";
                    break;
                }
                receiptsPerformItemDto.setProductTime(StringUtils.trim(row.get(8)));
                //????????????
                receiptsPerformItemDto.setGoodsLevel("100");

                receiptsPerformItemDtoList.add(receiptsPerformItemDto);

                receiptsPerform.setOrderSource((byte) 3);
                receiptsPerform.setReceiptsPerformItemDtoList(receiptsPerformItemDtoList);
                receiptsPerformList.add(receiptsPerform);
            }
        } catch (Exception e) {
            logger.error("????????????????????????????????????????????????"+ e);
        }
        
        return Response.success("??????");
    }












    /**
     * ?????????????????????
     *
     * @param response
     * @param condition ????????????
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
            //??????excel??????
            ExportExcel.exportExcel(response, ERROR_DEVICE_ORDER_FILENAME, ERROR_DEVICE_ORDER_FIELDNAMES, ERROR_DEVICE_ORDER_FIELDPRENAMES, userList);
        } catch (Exception e) {
            logger.error("???????????????????????????:", e);
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

            while (new Date().getTime()-startTime <= 3000 && !flag){//??????3s
                logger.error("??????:{},????????????", new Date().getTime()-startTime);
                CsvExportUtil.createCSVFile(tmpFile, columnFields, ERROR_DEVICE_ORDER_FIELDNAMES, userList);
                CsvExportUtil.appendCSVFile(tmpFile, columnFields, ERROR_DEVICE_ORDER_FIELDNAMES, userList);
                //Thread.sleep(6000);
                flag = true;
            }

            if (flag) {
                log.error("????????????????????????");
                //????????????????????????????????????
                String key = "export/user/user-export-" + "wangjia".hashCode() + "-" + new Date().getTime() + ".csv";
                uploadFile(tmpFile, key);
            } else {
                log.error("????????????????????????,??????:"+Thread.currentThread().getName()+"??????????????????:"+(new Date().getTime() - startTime)+"ms");
            }

            //setResponse(tmpFile, response);
            setResponse2(tmpFile, response);
        } catch (Exception ex) {
            logger.error("????????????",ex);
            //??????????????????????????????
        } finally {
            //????????????
            FileUtils.deleteQuietly(tmpFile);
        }

    }

    public void uploadFile(File file, String key) {
        try {
            log.error("??????????????????");
        } catch (Exception e) {
            log.error("??????????????????", e);
            throw new RuntimeException("??????????????????");
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
        String trueCSVName="????????????.csv";
        response.setHeader("Content-Disposition", "attachment;  filename="+ new String(trueCSVName.getBytes("GBK"), "ISO8859-1"));
        long fileLength = fileLoad.length();
        String length1 = String.valueOf(fileLength);
        response.setHeader("Content_Length", length1);
        FileInputStream in = new FileInputStream(fileLoad);
        int n;
        while ((n = in.read(b)) != -1) {
            out.write(b, 0, n); // ????????????out1024??????
        }
        in.close();
        out.close();
    }




    public void setResponse2(File tmpFile, HttpServletResponse response) throws IOException {
        // 1 ??????CSV???????????????????????????
        // 2 ????????????????????? ????????????url??????????????????????????? ??????
        CsvExportUtil.setResponseParam(response, ERROR_DEVICE_ORDER_FILENAME);
        //File fileLoad = new File(tmpFile.getCanonicalPath());
        FileInputStream in = new FileInputStream(tmpFile);
        try {
            CsvExportUtil.writeResponse(in, response.getOutputStream());
        } catch (Exception e) {
            logger.error("????????????", e);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    logger.error("???????????????", e);
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
        response.addHeader("Content-Disposition", "attachment;filename=".concat(new String("???????????????.csv".getBytes(StandardCharsets.UTF_8), Charset.forName("ISO8859-1"))));
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
            logger.error("????????????",ex);
        }

    }

    /**
     * ??????CSV  xlsx??????????????????
     * http://localhost:8082/excel/fetchExportData
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "fetchExportData",method=RequestMethod.GET)
    public void fetchExportData(HttpServletResponse response, String key) throws Exception {
        OutputStream output = null;
        InputStream input = null;
        //???????????????????????????InputStream
        input = this.getClass().getResourceAsStream("/template/xlsx/Product.xlsx");
        //????????????????????????InputStream
        //input = getInputStreamByPath();

        response.setHeader("Content-Type","application/csv");
        response.addHeader("Content-Disposition", "attachment;filename=".concat(new String("???????????????.xlsx".getBytes(StandardCharsets.UTF_8), Charset.forName("ISO8859-1"))));
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
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();//??????HttpURLConnection??????,??????????????????????????????????????????.
            conn.setDoInput(true);
            conn.connect();
            input = conn.getInputStream();	//??????????????????????????????
        } catch (IOException e) {
            logger.error("??????InputStream??????", e);
        }
        return input;
    }


    /**
     * ??????CSV??????
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
            //???????????????????????????key  ??????key????????????key ??????fetchExportData2??????key????????????
            //response.setData(key);

            //????????????
            exportCsvFinal(re, temp);
        } catch (Exception e) {
            logger.error("????????????",e);
        }
//        finally {
//            FileUtils.deleteQuietly(temp);
//            IOUtils.closeQuietly(exporter);
//        }
    }

    private void exportCsvFinal(HttpServletResponse response, File tmpFile) {
        //??????key ??????????????????????????????
        response.setHeader("Content-Type","application/csv");
        response.setHeader("Content-Length",String.valueOf(tmpFile.length()));
        response.addHeader("Content-Disposition", "attachment;filename="
                .concat(new String("???????????????.csv".getBytes(StandardCharsets.UTF_8), Charset.forName("ISO8859-1"))));
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
        //??????key ??????????????????????????????
        response.setHeader("Content-Type","application/csv");
        //response.setHeader("Content-Length",String.valueOf(obj.getContentLength()));
        response.addHeader("Content-Disposition", "attachment;filename="
                .concat(new String("???????????????.csv".getBytes(StandardCharsets.UTF_8), Charset.forName("ISO8859-1"))));
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
