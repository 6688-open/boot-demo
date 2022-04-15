package com.dj.boot.configuration.filter.permission;

import org.apache.commons.compress.utils.IOUtils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.Map;
import java.util.Vector;

/**
 * @ProjectName: boot_demo
 * @PackageName: com.dj.boot.configuration.filter.permission
 * @Author: wangJia
 * @Date: 2021-05-12-16-25
 */
public class ChangeRequestWrapper extends HttpServletRequestWrapper {
    private  byte[] body=null;
    private Map<String, String[]> parameterMap; // 所有参数的Map集合

    public ChangeRequestWrapper(HttpServletRequest request) {
        super(request);
        parameterMap = request.getParameterMap();

        try {
            loadBody(request);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 重写几个HttpServletRequestWrapper中的方法

    /**
     * 获取所有参数名
     *
     * @return 返回所有参数名
     */
    @Override
    public Enumeration<String> getParameterNames() {
        Vector<String> vector = new Vector<String>(parameterMap.keySet());
        return vector.elements();
    }

    /**
     * 获取指定参数名的值，如果有重复的参数名，则返回第一个的值 接收一般变量 ，如text类型
     *
     * @param name 指定参数名
     * @return 指定参数名的值
     */
    @Override
    public String getParameter(String name) {
        if(parameterMap.containsKey(name)){
            String[] results = parameterMap.get(name);
            return results[0];
        }
        return null;
    }


    /**
     * 获取指定参数名的所有值的数组，如：checkbox的所有数据
     * 接收数组变量 ，如checkobx类型
     */
    @Override
    public String[] getParameterValues(String name) {
        return parameterMap.get(name);
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        return parameterMap;
    }

    public void setParameterMap(Map<String, String[]> parameterMap) {
        this.parameterMap = parameterMap;
    }


    /*******in**************/

    private BufferedReader reader;

    private ServletInputStream inputStream;

    private void loadBody(HttpServletRequest request) throws IOException{
        body = IOUtils.toByteArray(request.getInputStream());
        inputStream = new RequestCachingInputStream(body);
    }


    public byte[] getBody() {
        return body;
    }

    public void setBody(byte[] body){
        this.body=body;
        inputStream = new RequestCachingInputStream(body);
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        if (inputStream != null) {
            ServletInputStream tmp = this.inputStream;
            this.inputStream = new RequestCachingInputStream(body);
            return tmp;
        }
        return super.getInputStream();
    }

    @Override
    public BufferedReader getReader() throws IOException {
        if (reader == null) {
            reader = new BufferedReader(new InputStreamReader(inputStream, getCharacterEncoding()));
        }
        return reader;
    }

    public  String getBodyContent() throws IOException {
        StringBuilder sb = new StringBuilder();
        try (InputStream inputStream = getInputStream();
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")))) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    private static class RequestCachingInputStream extends ServletInputStream {

        private final ByteArrayInputStream inputStream;

        public RequestCachingInputStream(byte[] bytes) {
            inputStream = new ByteArrayInputStream(bytes);
        }
        @Override
        public int read() throws IOException {
            return inputStream.read();
        }

        @Override
        public boolean isFinished() {
            return inputStream.available() == 0;
        }

        @Override
        public boolean isReady() {
            return true;
        }

        @Override
        public void setReadListener(ReadListener readlistener) {
        }

    }
}
