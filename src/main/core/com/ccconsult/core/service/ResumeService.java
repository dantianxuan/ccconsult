/**
 * 
 */
package com.ccconsult.core.service;

import com.ccconsult.base.CcResult;
import com.ccconsult.pojo.Resume;

/**
 * 
 * 
 * @author jinsaichen
 * @version $Id: ResumeService.java, v 0.1 2014-6-20 上午8:40:08 jinsaichen Exp $
 */
public interface ResumeService {
    /**
    * �?�?�????
    * 
    * @param article
    * @return
    */
    public CcResult saveResume(Resume resume);

    /**
     * �???��?????
     * 
     * @param article
     * @return
     */
    public CcResult updateResume(Resume resume);

    /**
     * ??��??�??????????索�?????
     * 
     * @param jobSeekerId
     * @return
     */
    public CcResult getResumeByConsultantId(final String consultantId);

}
