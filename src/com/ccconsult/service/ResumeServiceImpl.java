package com.ccconsult.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import com.ccconsult.base.AbstractService;
import com.ccconsult.base.AssertUtil;
import com.ccconsult.base.BlankServiceCallBack;
import com.ccconsult.base.CcResult;
import com.ccconsult.dao.ResumeDAO;
import com.ccconsult.pojo.Resume;


public class ResumeServiceImpl extends AbstractService implements ResumeService {

	@Autowired
	private ResumeDAO resumeDAO;

	@Override
	public CcResult saveResume(final Resume resume) {
		return serviceTemplate.execute(CcResult.class, new BlankServiceCallBack() {

            @Override
            public void check() {
                AssertUtil.notBlank(resume.getRealName(), "å§????ä¸???½ä¸ºç©?");
                AssertUtil.notBlank(resume.getSexy(), "??§å??ä¸???½ä¸ºç©?");
                AssertUtil.notBlank(resume.getMobile(), "???ç³»æ?¹å??ä¸???½ä¸ºç©?");
            }

            @Override
            public CcResult executeService() {
            	resume.setGmtCreate(new Date());
            	resume.setGmtModified(new Date());
            	resumeDAO.save(resume);
                return new CcResult(resume);
            }
        });
	}

	@Override
	public CcResult updateResume(Resume resume) {
		// TODO Auto-generated method stub
		return null;
	}

    @Override
    public CcResult getResumeByJobSeekerId(final String jobSeekerId) {
        return serviceTemplate.execute(CcResult.class, new BlankServiceCallBack() {
            @Override
            public CcResult executeService() {
                List<Resume> resumes = resumeDAO.findByJobSeekerId(jobSeekerId);
                if (!CollectionUtils.isEmpty(resumes)) {
                    return new CcResult(resumes.get(0));
                }
                return new CcResult(new Resume());
            }
        });
    }

}
