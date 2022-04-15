package com.dj.boot.common.base.page;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * @ProjectName: boot_demo
 * @Author: wangJia
 * @Date: 2021-05-25-15-41
 */
public class PageRequestParam <T> implements Serializable {

    private static final long serialVersionUID = 3332222657927L;
    private static final Logger log = LogManager.getLogger(PageRequestParam.class);
    private HashMap<String, Boolean> permissionMap;
    /**
     * @Fields 分页 数据
     */
    private List<T> aaData;
    /**
     * @Fields 总记录数
     */
    private int  iTotalRecords;
    /**
     * @Fields 开始行
     */
    private int  iDisplayStart;
    /**
     * @Fields 总 显示记录数
     */
    private int  iTotalDisplayRecords;
    /**
     * @Fields 每页显示 行数
     */
    private int iDisplayLength;
    private int sortColIndex;
    private String sortColName;
    private String sortDir;
    private List<String> colsNames;

    public PageRequestParam() {
    }

    public PageRequestParam(int iDisplayStart, int iTotalRecords, int iTotalDisplayRecords, List<T> aaData, int iDisplayLength) {
        this.aaData = aaData;
        this.iTotalRecords = iTotalRecords;
        this.iDisplayStart = iDisplayStart;
        this.iTotalDisplayRecords = iTotalDisplayRecords;
        this.iDisplayLength = iDisplayLength;
    }

    public List<T> getAaData() {
        return this.aaData;
    }

    public void setAaData(List<T> aaData) {
        this.aaData = aaData;
    }

    public int getSortColIndex() {
        return this.sortColIndex;
    }

    public void setSortColIndex(int sortColIndex) {
        this.sortColIndex = sortColIndex;
    }

    public String getSortColName() {
        return this.sortColName;
    }

    public void setSortColName(String sortColName) {
        this.sortColName = sortColName;
    }

    public String getSortDir() {
        return this.sortDir;
    }

    public void setSortDir(String sortDir) {
        this.sortDir = sortDir;
    }

    public int getiTotalRecords() {
        return this.iTotalRecords;
    }

    public int getiDisplayLength() {
        return this.iDisplayLength;
    }

    public void setiDisplayLength(int iDisplayLength) {
        this.iDisplayLength = iDisplayLength;
    }

    public int getiDisplayStart() {
        return this.iDisplayStart;
    }

    public void setiDisplayStart(int iDisplayStart) {
        this.iDisplayStart = iDisplayStart;
    }

    public int getiTotalDisplayRecords() {
        return this.iTotalDisplayRecords;
    }

    public void setiTotalRecords(int iTotalRecords) {
        this.iTotalRecords = iTotalRecords;
        this.iTotalDisplayRecords = iTotalRecords;
    }

    public List<String> getColsNames() {
        return this.colsNames;
    }

    public void setColsNames(List<String> colsNames) {
        this.colsNames = colsNames;
    }

    public static PageRequestParam getPageParams(String aaData) {
        Type type = (new TypeToken<List<PageVal>>() {
        }).getType();
        List<PageVal> list = (List)(new Gson()).fromJson(aaData, type);
        PageRequestParam page = new PageRequestParam();
        List<String> colsNames = new ArrayList();
        Iterator it = list.iterator();

        while(true) {
            while(it.hasNext()) {
                PageVal pageVal = (PageVal)it.next();
                if (pageVal.getName().startsWith("mDataProp_")) {
                    colsNames.add(pageVal.getValue());
                } else if ("iDisplayStart".equals(pageVal.getName())) {
                    page.setiDisplayStart(Integer.parseInt(pageVal.getValue()));
                } else if ("iDisplayLength".equals(pageVal.getName())) {
                    page.setiDisplayLength(Integer.parseInt(pageVal.getValue()));
                } else if ("sSortDir_0".equals(pageVal.getName())) {
                    if ("desc".equalsIgnoreCase(pageVal.getValue()) || "asc".equalsIgnoreCase(pageVal.getValue())) {
                        page.setSortDir(pageVal.getValue());
                    }
                } else if ("iSortCol_0".equals(pageVal.getName())) {
                    page.setSortColIndex(Integer.parseInt(pageVal.getValue()));
                }
            }

            if (CollectionUtils.isNotEmpty(colsNames)) {
                page.setSortColName((String)colsNames.get(page.getSortColIndex()));
                it = colsNames.iterator();

                while(it.hasNext()) {
                    if (StringUtils.isBlank((String)it.next())) {
                        it.remove();
                    }
                }

                page.setColsNames(colsNames);
            }

            return page;
        }
    }

    public HashMap<String, Boolean> getPermissionMap() {
        return this.permissionMap;
    }

    public void setPermissionMap(HashMap<String, Boolean> permissionMap) {
        this.permissionMap = permissionMap;
    }

    public static List<Integer> getAllPageStartIdx(int allRecordsNum, int perPageNum) {
        if (allRecordsNum > 0 && perPageNum > 0) {
            List<Integer> perPageIdx = new ArrayList();
            int pageNo = allRecordsNum % perPageNum == 0 ? allRecordsNum / perPageNum : allRecordsNum / perPageNum + 1;

            for(int nowPage = 0; nowPage < pageNo; ++nowPage) {
                perPageIdx.add(nowPage * perPageNum);
            }

            return perPageIdx;
        } else {
            return null;
        }
    }
}
