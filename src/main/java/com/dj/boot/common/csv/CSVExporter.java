package com.dj.boot.common.csv;

import com.dj.boot.common.csv.annotation.CSVColumn;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.io.Closeable;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * CSV导出工具
 *
 * @param <T> 被导出类
 * @author wangjia
 * @since 2018.06.21
 *
 *
 *
 */
public class CSVExporter<T> implements Closeable {

    private static final Converter DEFAULT_CONVERTER = new Converter() {
        @Override
        public String convert(Field field, Object raw) {
            if(field.getType().isAssignableFrom(Date.class)){
                return DateFormatUtils.format((Date)raw,"yyyy-MM-dd HH:mm:ss");
            }
            return raw.toString();
        }
    };

    private Column[] columns;
    private CSVPrinter printer;

    private CSVExporter(Column[] columns, CSVPrinter printer) {
        this.columns = columns;
        this.printer = printer;
    }

    public static <T> CSVExporter<T> newExporter(Class<T> exportClz, Appendable out) throws IOException {
        final List<Column> columns = prepareColumn(exportClz);
        final String[] headers = prepareHeaders(columns);
        final CSVPrinter printer = new CSVPrinter(out, CSVFormat.EXCEL.withHeader(headers));
        return new CSVExporter<T>(columns.toArray(new Column[0]), printer);
    }

    /**
     * 依据注解解析列信息
     * @param exportClz 待导出的实体类型
     * @param <T>
     * @return 列信息
     */
    private static <T> List<Column> prepareColumn(Class<T> exportClz) {
        final Field[] fields = exportClz.getDeclaredFields();
        final List<Column> columns = new ArrayList<Column>();
        CSVColumn csvColumn;
        for (Field field : fields) {
            csvColumn = field.getAnnotation(CSVColumn.class);
            if(csvColumn!=null) {
                field.setAccessible(true);
                columns.add(new Column(csvColumn.order(), csvColumn.header(), field, csvColumn.defVal()));
            }
        }
        Collections.sort(columns);
        return columns;
    }

    /**
     * 解析Header
     * @param columns 列信息
     * @return headers
     */
    private static String[] prepareHeaders(List<Column> columns) {
        final String[] headers = new String[columns.size()];
        for (int idx = 0; idx < headers.length; idx++) {
            headers[idx] = columns.get(idx).header;
        }
        return headers;
    }

    /**
     * 追加导出数据
     * <b>注意：不关闭流,导出完成后调用{@link #close()}或最后一次追加数据时是调用{@link #export(List)}、{@link #export(List, Converter)}</b>
     * @param exportData 待导出实体
     * @param converter 转换器
     * @throws IOException
     */
    public void append(List<T> exportData, Converter converter) throws IOException {
        final List<String[]> records = fetchRecords(exportData, converter);
        printer.printRecords(records);
        printer.flush();
    }
    /**
     * 追加导出数据
     * <b>注意：不关闭流,导出完成后调用{@link #close()}或最后一次追加数据时是调用{@link #export(List)}、{@link #export(List, Converter)}</b>
     * @param exportData 待导出实体
     * @throws IOException
     */
    public void append(List<T> exportData) throws IOException {
        append(exportData, DEFAULT_CONVERTER);
    }
    /**
     * 导出数据
     * <b>注意：导出后自动关闭输出流</b>
     * @param exportData 待导出实体
     * @param converter 转换器
     * @throws IOException
     */
    public void export(List<T> exportData, Converter converter) throws IOException {
        append(exportData, converter);
        close();
    }
    /**
     * 导出数据
     * <b>注意：导出后自动关闭输出流</b>
     * @param exportData 待导出实体
     * @throws IOException
     */
    public void export(List<T> exportData) throws IOException {
        export(exportData, DEFAULT_CONVERTER);
    }

    /**
     * 将导出实体转换为CSV 记录
     * @param exportData 导出实体
     * @param converter 转换器
     * @return CSV 记录
     */
    private List<String[]> fetchRecords(List<T> exportData, Converter converter) {
        final List<String[]> records = new ArrayList<String[]>(exportData.size());
        String[] record;
        Column col;
        Object rawVal;
        for (T row : exportData) {
            record = new String[columns.length];
            for (int idx = 0; idx < record.length; idx++) {
                col = columns[idx];
                try {
                    rawVal = col.field.get(row);
                    record[idx] = rawVal == null ? col.defVal : converter.convert(col.field, rawVal);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            records.add(record);
        }
        return records;
    }

    /**
     * 关闭输出
     * @throws IOException
     */
    @Override
    public void close() throws IOException {
        printer.close();
    }

    /**
     * 列信息
     */
    private static class Column implements Comparable<Column>{
        /**
         * 顺序
         */
        private int order;
        /**
         * 列标题
         */
        private String header;
        /**
         * 对应实体字段
         */
        private Field field;
        /**
         * 默认值
         */
        private String defVal;

        private Column(int order, String header, Field field, String defVal) {
            this.order = order;
            this.header = header;
            this.field = field;
            this.defVal = defVal;
        }

        @Override
        public int compareTo(Column o) {
            return this.order-o.order;
        }
    }

    /**
     * 字段数据数据转换
     */
    public interface Converter {
        /**
         * 将指定字段值转换为字符串
         * @param field 指定字段
         * @param raw 指定字段raw 值
         * @return 转换后的字段值
         */
        String convert(Field field, Object raw);
    }
}
