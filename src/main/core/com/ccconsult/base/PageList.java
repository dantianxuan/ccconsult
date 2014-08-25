/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2014 All Rights Reserved.
 */
package com.ccconsult.base;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author danjingyu
 * @version $Id: PageList.java, v 0.1 2014-3-16 下午12:04:06 danjingyu Exp $
 */
public class PageList<T> extends ToString {
    /**  */
    private static final long serialVersionUID = 1L;
    /** 
    * 当前页第一条数据的位置,从0开始 
    */
    private int               start;
    /** 
    * 每页的记录数 
    */
    private int               pageSize         = CcConstrant.DEFAULT_PAGE_SIZE;
    /** 
    * 当前页中存放的记录 
    */
    private List<T>           data;
    /** 
    * 总记录数 
    */
    private int               totalCount;

    /** 
    * 构造方法，只构造空页 
    */
    public PageList() {
        this(0, 0, CcConstrant.DEFAULT_PAGE_SIZE, new ArrayList<T>());
    }

    /** 
    * 默认构造方法 
    *  
    * @param start 
    * 本页数据在数据库中的起始位置 
    * @param totalSize 
    * 数据库中总记录条数 
    * @param pageSize 
    * 本页容量 
    * @param data 
    * 本页包含的数据 
    */
    public PageList(int start, int totalSize, int pageSize, List<T> data) {
        this.pageSize = pageSize;
        this.start = start;
        this.totalCount = totalSize;
        this.data = data;
    }

    /** 
    * 取数据库中包含的总记录数 
    */
    public int getTotalCount() {
        return this.totalCount;
    }

    /** 
    * 取总页数 
    */
    public int getTotalPageCount() {
        if (totalCount % pageSize == 0)
            return totalCount / pageSize;
        else
            return totalCount / pageSize + 1;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    /** 
    * 取每页数据容量 
    */
    public int getPageSize() {
        return pageSize;
    }

    /** 
    * 当前页中的记录 
    */
    public List getResult() {
        return data;
    }

    /** 
    * 取当前页码,页码从1开始 
    */
    public int getCurrentPageNo() {
        return (start / pageSize) + 1;
    }

    /** 
    * 是否有下一页 
    */
    public boolean hasNextPage() {
        return (this.getCurrentPageNo() < this.getTotalPageCount() - 1);
    }

    /** 
    * 是否有上一页 
    */
    public boolean hasPreviousPage() {
        return (this.getCurrentPageNo() > 1);
    }

    /** 
    * 获取任一页第一条数据的位置，每页条数使用默认值 
    * 关键字设为pretected 
    */
    protected static int getStartOfPage(int pageNo) {
        return getStartOfPage(pageNo, CcConstrant.DEFAULT_PAGE_SIZE);
    }

    /** 
    * 获取任一页第一条数据的位置,startIndex从0开始 
    * 关键字设为pretected 
    */
    protected static int getStartOfPage(int pageNo, int pageSize) {
        return (pageNo - 1) * pageSize;
    }
}
