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
    * ä¿?å­?ç®????
    * 
    * @param article
    * @return
    */
    public CcResult saveResume(Resume resume);

    /**
     * ä¿???¹ç?????
     * 
     * @param article
     * @return
     */
    public CcResult updateResume(Resume resume);

    /**
     * ??¹æ??åº??????????ç´¢ç?????
     * 
     * @param jobSeekerId
     * @return
     */
    public CcResult getResumeByJobSeekerId(String jobSeekerId);

}
