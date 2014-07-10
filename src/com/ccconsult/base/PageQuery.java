/**
 * ccinterviewer.com Inc.
 * Copyright (c) 2014-2014 All Rights Reserved.
 */
package com.ccconsult.base;

/**
 * 分页查询基本累
 * 
 * @author jingyudan
 * @version $Id: PageQuery.java, v 0.1 2014-7-7 下午11:47:09 jingyudan Exp $
 */
public class PageQuery extends ToString {

    private static final long serialVersionUID = 1L;
    protected int             pageSize         = 20;
    protected int             pageNo           = 1;

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

}
