package com.dj.boot.aspect.log.utils;

/**
 * 异常堆栈转文本
 * Date: 2018/4/2
 * Time: 15:56
 * To change this template use File | Settings | File Templates.
 */
public class ExceptionUtils {

    /**
     * 异常堆栈为一行
     * @param e
     * @return
     */
    public static String toString(Throwable e) {
        StackTraceElement[] traces = e.getStackTrace();
        StringBuilder sb = new StringBuilder(1024);
        sb.append(e.toString()).append("\\n");
        if(traces != null) {
            StackTraceElement[] arr$ = traces;
            int len$ = traces.length;

            for(int i$ = 0; i$ < len$; ++i$) {
                StackTraceElement trace = arr$[i$];
                sb.append(" at ").append(trace).append("\\n");
            }
        }

        return sb.toString();
    }

    /**
     * 异常堆栈换行
     * @param e
     * @return
     */
    public static String StackTraceString(Throwable e){
        StackTraceElement[] traces = e.getStackTrace();
        StringBuilder sb = new StringBuilder(1024);
        sb.append(e.toString()).append("\n");
        if(traces != null) {
            StackTraceElement[] arr$ = traces;
            int len$ = traces.length;

            for(int i$ = 0; i$ < len$; ++i$) {
                StackTraceElement trace = arr$[i$];
                sb.append(" at ").append(trace).append("\n");
            }
        }

        return sb.toString();
    }
}
