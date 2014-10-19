/**
 * ccinterviewer.com Inc.
 * Copyright (c) 2014-2014 All Rights Reserved.
 */
package com.ccconsult.service;

import java.util.Date;

import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import com.ccconsult.base.AbstractService;
import com.ccconsult.base.AssertUtil;
import com.ccconsult.base.BlankServiceCallBack;
import com.ccconsult.base.CcConstrant;
import com.ccconsult.base.CcResult;
import com.ccconsult.base.enums.ConsultStepEnum;
import com.ccconsult.base.enums.DataStateEnum;
import com.ccconsult.base.enums.FileTypeEnum;
import com.ccconsult.base.enums.PayStateEnum;
import com.ccconsult.base.util.CodeGenUtil;
import com.ccconsult.base.util.DateUtil;
import com.ccconsult.core.consult.ConsultQueryComponent;
import com.ccconsult.core.file.FileComponent;
import com.ccconsult.dao.ConsultDAO;
import com.ccconsult.dao.ConsultResumeDAO;
import com.ccconsult.dao.CounselorDAO;
import com.ccconsult.dao.ServiceConfigDAO;
import com.ccconsult.dao.ServiceDAO;
import com.ccconsult.pojo.Consult;
import com.ccconsult.pojo.ConsultResume;
import com.ccconsult.pojo.Service;
import com.ccconsult.web.view.CounselorVO;
import com.ccconsult.web.view.ServiceConfigVO;

/**
 * 创建consult
 * 
 * @author jingyudan
 * @version $Id: ConsultResumeServiceImpl.java, v 0.1 2014年10月19日 上午8:02:17 jingyudan Exp $
 */
public class ConsultResumeServiceImpl extends AbstractService implements ConsultResumeService {

    @Autowired
    private ServiceDAO            serviceDAO;
    @Autowired
    private CounselorDAO          counselorDAO;
    @Autowired
    private ConsultDAO            consultDAO;
    @Autowired
    private ConsultQueryComponent consultComponent;
    @Autowired
    private FileComponent         fileComponent;
    @Autowired
    private ConsultResumeDAO      consultResumeDAO;
    @Autowired
    private ServiceConfigDAO      serviceConfigDAO;

    /** 
     * @see com.ccconsult.service.ConsultResumeService#create()
     */
    public CcResult create(final Consult consult, final MultipartFile[] localFile,
                           final String contextPath) {

        CcResult result = serviceTemplate.executeWithTx(CcResult.class, new BlankServiceCallBack() {
            /** 
             * @see com.ccconsult.base.ServiceCallBack#check()
             */
            @Override
            public void check() {
                consult.getCounselorId();
                CounselorVO counselorVO = counselorDAO.findById(consult.getCounselorId());
                AssertUtil.notNull(counselorVO, "咨询对象不存在，请检查");
                AssertUtil.notBlank(consult.getGoal(), "请填写您的咨询目的，请检查");
                AssertUtil.state(consult.getGoal().length() <= CcConstrant.COMMON_4096_LENGTH,
                    "描述问题太长，请简洁描述");
            }

            @Override
            public CcResult executeService() {
                String fileName = "";
                for (MultipartFile myfile : localFile) {
                    AssertUtil.state(!myfile.isEmpty(), "必须上传您的简历文件！");
                    fileName = fileComponent.uploadFile(myfile, FileTypeEnum.RESUME, contextPath,
                        CcConstrant.FILE_2M_SIZE, "doc|pdf|docx");
                }
                ServiceConfigVO serviceConfigVO = serviceConfigDAO.findVoById(consult
                    .getServiceConfigId());
                AssertUtil.state(
                    serviceConfigVO != null
                            && serviceConfigVO.getServiceConfig().getState()
                                .equals(DataStateEnum.NORMAL.getValue()), "服务配置对象当前过期，请查询发起咨询");
                //创建一个咨询记录
                consult.setGmtCreate(new Date());
                consult.setGmtModified(new Date());
                Service service = serviceDAO.findById(consult.getServiceId());
                AssertUtil.notNull(service, "服务不存在，请检查");
                consult.setPrice(NumberUtils.toDouble(service.getPriceRegion()));
                consult.setPayTag(PayStateEnum.WAIT_FOR_PAY.getValue());
                consult.setStep(ConsultStepEnum.CREATE.getValue());
                consult.setGmtEffectBegin(DateUtil.addMinutes(new Date(), 10));
                consult.setGmtEffectEnd(DateUtil.addHours(new Date(), service.getEffectTime()));
                consult.setIndetityCode(CodeGenUtil.getFixLenthString(6));
                consultDAO.save(consult);
                //创建面试咨询记录
                ConsultResume resumeConsult = new ConsultResume();
                resumeConsult.setConsultId(consult.getId());
                resumeConsult.setResumeFiles(fileName);
                consultResumeDAO.save(resumeConsult);
                return new CcResult(consult);
            }
        });
        return result;
    }

}
