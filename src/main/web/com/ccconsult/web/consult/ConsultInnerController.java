/**
 * 
 */
package com.ccconsult.web.consult;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ccconsult.base.AssertUtil;
import com.ccconsult.base.BlankServiceCallBack;
import com.ccconsult.base.CcConstrant;
import com.ccconsult.base.CcResult;
import com.ccconsult.base.PageList;
import com.ccconsult.base.PageQuery;
import com.ccconsult.base.enums.ConsultStepEnum;
import com.ccconsult.base.enums.DataStateEnum;
import com.ccconsult.base.enums.MessageRelTypeEnum;
import com.ccconsult.base.enums.PayStateEnum;
import com.ccconsult.base.util.CodeGenUtil;
import com.ccconsult.base.util.DateUtil;
import com.ccconsult.base.util.StringUtil;
import com.ccconsult.core.ConsultComponent;
import com.ccconsult.dao.ConsultDAO;
import com.ccconsult.dao.CounselorDAO;
import com.ccconsult.dao.MessageDAO;
import com.ccconsult.dao.ServiceConfigDAO;
import com.ccconsult.dao.ServiceDAO;
import com.ccconsult.pojo.Consult;
import com.ccconsult.pojo.Consultant;
import com.ccconsult.pojo.Service;
import com.ccconsult.web.BaseController;
import com.ccconsult.web.view.ConsultBase;
import com.ccconsult.web.view.CounselorVO;
import com.ccconsult.web.view.MessageVO;
import com.ccconsult.web.view.ServiceConfigVO;

/**
 * @author jingyu.dan
 * 
 */
@Controller
public class ConsultInnerController extends BaseController {

    @Autowired
    private CounselorDAO     counselorDAO;
    @Autowired
    private ConsultDAO       consultDAO;
    @Autowired
    private MessageDAO       messageDAO;
    @Autowired
    private ConsultComponent consultComponent;
    @Autowired
    private ServiceConfigDAO serviceConfigDAO;
    @Autowired
    private ServiceDAO       serviceDAO;

    @RequestMapping(value = "/searchConsultInner.htm")
    public ModelAndView searchConsult(HttpServletRequest request, PageQuery query, ModelMap modelMap) {
        ModelAndView view = new ModelAndView("content/searchConsultInner");
        String keyWord = request.getParameter("keyWord");
        if (query == null || StringUtil.isBlank(keyWord)) {
            return view;
        }
        PageList<ConsultBase> pageConsult = consultComponent.queryInnerConsultByGoal(keyWord,
            query.getPageSize(), query.getPageNo());
        modelMap.put("pageConsult", pageConsult);
        modelMap.put("query", query);
        modelMap.put("keyWord", keyWord);
        return view;

    }

    //~~~~~~~~~~~innerConsult~~~~~~~~~~~~~~~~~~~~
    @RequestMapping(value = "/consultant/consult/createConsultInnerInit.htm", method = RequestMethod.GET)
    public ModelAndView handleRequest(HttpServletRequest request, final String serviceConfigId,
                                      ModelMap modelMap) {
        ModelAndView view = new ModelAndView("consultant/consult/createConsultInner");
        CcResult result = serviceTemplate.execute(CcResult.class, new BlankServiceCallBack() {
            @Override
            public CcResult executeService() {
                int configId = NumberUtils.toInt(serviceConfigId);
                AssertUtil.state(configId > 0, "非法的创建请求！");
                ServiceConfigVO serviceConfigVO = serviceConfigDAO.findVoById(configId);
                AssertUtil.state(
                    serviceConfigVO != null
                            && serviceConfigVO.getServiceConfig().getState()
                                .equals(DataStateEnum.NORMAL.getValue()), "当前服务设定过期或者被修改，请重新预约！");
                return new CcResult(serviceConfigVO);
            }
        });
        modelMap.put("result", result);
        return view;
    }

    @RequestMapping(value = "/consultInner.htm", method = RequestMethod.GET)
    public ModelAndView publicInnerConsult(final HttpServletRequest request,
                                           final String consultId, final ModelMap modelMap) {
        ModelAndView view = new ModelAndView("content/consultInner");
        CcResult result = serviceTemplate.execute(CcResult.class, new BlankServiceCallBack() {
            @Override
            public CcResult executeService() {
                int consuid = NumberUtils.toInt(consultId);
                AssertUtil.state(consuid > 0, "非法请求！");
                ConsultBase consultBase = consultComponent.queryById(consuid);
                List<MessageVO> messageVOs = messageDAO.queryByRelInfo(consuid,
                    MessageRelTypeEnum.CONSULT.getValue());
                modelMap.put("consultBase", consultBase);
                modelMap.put("messageVOs", messageVOs);
                return new CcResult(true);
            }
        });
        modelMap.put("result", result);
        return view;
    }

    @RequestMapping(value = "consultant/consult/createConsultInner.json", method = RequestMethod.POST)
    public @ResponseBody
    ModelMap createConsult(final HttpServletRequest request, final Consult consult,
                           ModelMap modelMap) {
        modelMap.clear();
        CcResult result = serviceTemplate.executeWithTx(CcResult.class, new BlankServiceCallBack() {
            @Override
            public CcResult executeService() {
                Consultant consultant = getConsultantInSession(request.getSession());
                AssertUtil.notNull(consultant, "不合法的用户");
                consult.getCounselorId();
                CounselorVO counselorVO = counselorDAO.findById(consult.getCounselorId());
                AssertUtil.notNull(counselorVO, "咨询对象不存在，请检查");
                AssertUtil.notBlank(consult.getGoal(), "必须输入您需要咨询的问题");
                AssertUtil.state(consult.getGoal().length() <= CcConstrant.COMMON_512_LENGTH,
                    "描述问题太长，请简洁描述");
                consult.setGmtCreate(new Date());
                consult.setStep(ConsultStepEnum.ON_CONSULT.getValue());
                consult.setPayTag(PayStateEnum.PAY_SUCCESS.getValue());//无须支付
                Service service = serviceDAO.findById(consult.getServiceId());
                AssertUtil.notNull(service, "服务不存在，请检查");
                consult.setGmtEffectEnd(DateUtil.addHours(new Date(), service.getEffectTime()));
                consult.setGmtModified(new Date());
                consult.setIndetityCode(CodeGenUtil.getFixLenthString(6));
                consultDAO.save(consult);
                return new CcResult(consult);
            }
        });
        modelMap.put("result", result);
        return modelMap;
    }

    @RequestMapping(value = "/consultant/consult/goConsultInner.htm", method = RequestMethod.GET)
    public ModelAndView innerConsult(final HttpServletRequest request, final String consultId,
                                     final ModelMap modelMap) {
        ModelAndView view = new ModelAndView("consultant/consult/consultInner");
        CcResult result = serviceTemplate.execute(CcResult.class, new BlankServiceCallBack() {
            @Override
            public CcResult executeService() {
                int consuid = NumberUtils.toInt(consultId);
                AssertUtil.state(consuid > 0, "非法请求！");
                ConsultBase consultBase = consultComponent.queryById(consuid);
                Consultant consultant = getConsultantInSession(request.getSession());
                AssertUtil.state(
                    consultant != null
                            && consultBase.getConsultant().getId().equals(consultant.getId()),
                    "非法请求！");
                List<MessageVO> messageVOs = messageDAO.queryByRelInfo(consuid,
                    MessageRelTypeEnum.CONSULT.getValue());
                modelMap.put("consultBase", consultBase);
                modelMap.put("messageVOs", messageVOs);
                return new CcResult(true);
            }
        });
        modelMap.put("result", result);
        return view;
    }

    //----------counselor--------------------
    @RequestMapping(value = "/counselor/consult/goConsultInner.htm", method = RequestMethod.GET)
    public ModelAndView counselorInnerConsult(final HttpServletRequest request,
                                              final String consultId, final ModelMap modelMap) {
        ModelAndView view = new ModelAndView("/counselor/consult/consultInner");
        CcResult result = serviceTemplate.execute(CcResult.class, new BlankServiceCallBack() {
            @Override
            public CcResult executeService() {
                int consuid = NumberUtils.toInt(consultId);
                AssertUtil.state(consuid > 0, "非法请求！");
                ConsultBase consultBase = consultComponent.queryById(consuid);
                CounselorVO counselorVO = getCounselorInSession(request.getSession());
                AssertUtil.state(
                    counselorVO != null
                            && consultBase.getCounselorVO().getCounselor().getId()
                                .equals(counselorVO.getCounselor().getId()), "非法请求！");
                List<MessageVO> messageVOs = messageDAO.queryByRelInfo(consuid,
                    MessageRelTypeEnum.CONSULT.getValue());
                modelMap.put("consultBase", consultBase);
                modelMap.put("messageVOs", messageVOs);
                return new CcResult(true);
            }
        });
        modelMap.put("result", result);
        return view;
    }

}