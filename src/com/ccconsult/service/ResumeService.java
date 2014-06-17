/**
 * 
 */
package com.ccconsult.service;

import com.ccconsult.base.CcResult;
import com.ccconsult.pojo.Resume;


/**
 * @author jinsaichen
 *
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
