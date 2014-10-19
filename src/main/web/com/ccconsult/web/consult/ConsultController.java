/**
 * 
 */
package com.ccconsult.web.consult;

import java.util.Date;

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
import com.ccconsult.base.CcException;
import com.ccconsult.base.CcResult;
import com.ccconsult.base.enums.CacheEnum;
import com.ccconsult.base.enums.ConsultStepEnum;
import com.ccconsult.base.enums.PayStateEnum;
import com.ccconsult.base.enums.UserRoleEnum;
import com.ccconsult.core.consult.ConsultQueryComponent;
import com.ccconsult.dao.ArticleDAO;
import com.ccconsult.dao.ConsultDAO;
import com.ccconsult.dao.MessageDAO;
import com.ccconsult.pojo.Article;
import com.ccconsult.pojo.Consult;
import com.ccconsult.pojo.Consultant;
import com.ccconsult.pojo.Service;
import com.ccconsult.service.ConsultService;
import com.ccconsult.web.BaseController;
import com.ccconsult.web.view.ConsultBase;

/**
 * @author jingyu.dan
 * 
 */
@Controller
public class ConsultController extends BaseController {

    @Autowired
    private ConsultDAO            consultDAO;
    @Autowired
    private MessageDAO            messageDAO;
    @Autowired
    private ConsultQueryComponent consultQueryComponent;
    @Autowired
    private ArticleDAO            articleDAO;
    @Autowired
    private ConsultService        consultService;

    @RequestMapping(value = "counselor/consult/rejectConsult.json", method = RequestMethod.POST)
    public @ResponseBody ModelMap createConsult(final HttpServletRequest request,
                                                final String consultId, final String rejectReason,
                                                ModelMap modelMap) {
        modelMap.clear();
        Consult consult = consultDAO.findById(NumberUtils.toInt(consultId));
        if (!validInSession(consult.getCounselorId(), UserRoleEnum.COUNSELOR, request.getSession())) {
            return illegalJsonRequest(modelMap);
        }
        CcResult result = consultService.rejectConsult(NumberUtils.toInt(consultId));
        modelMap.put("result", result);
        return modelMap;
    }

    @RequestMapping(value = "/consultant/consult/payForConsult.htm")
    public ModelAndView paryForConsult(final HttpServletRequest request, final Integer consultId,
                                       ModelMap modelMap) {
        ModelAndView view = new ModelAndView("consultant/consult/payForConsult");
        final Consultant consultant = getConsultantInSession(request.getSession());
        CcResult result = serviceTemplate.execute(CcResult.class, new BlankServiceCallBack() {
            @Override
            public CcResult executeService() {
                AssertUtil.state(consultId != null && consultId > 0, "不合法的请求，当前记录不存在");
                ConsultBase consultBase = consultQueryComponent.queryById(consultId);
                AssertUtil.state(consultBase != null, "您所访问的记录不存在");
                AssertUtil.state(
                    consultant != null
                            && consultant.getId().equals(consultBase.getConsultant().getId()),
                    "非法请求，不是您当前的记录");
                AssertUtil.state(
                    !consultBase.getConsult().getPayTag()
                        .equals(PayStateEnum.PAY_SUCCESS.getValue()), "对不起，当前记录已经支付");
                return new CcResult(consultBase);
            }
        });
        modelMap.put("result", result);
        return view;
    }

    /**
     * 公司邮箱链接注册
     * @return
     */
    @RequestMapping(value = "consultant/consult/completeConsult.json", method = RequestMethod.POST)
    public @ResponseBody ModelMap consultantCompleteConsult(final HttpServletRequest request,
                                                            final Integer consultId,
                                                            final ModelMap modelMap) {
        modelMap.clear();
        Consult consult = consultDAO.findById(consultId);
        if (!validInSession(consult.getConsultantId(), UserRoleEnum.CONSULTANT,
            request.getSession())) {
            return illegalJsonRequest(modelMap);
        }
        CcResult result = consultService.finishConsultOrder(consultId);
        modelMap.put("result", result);
        return modelMap;
    }

    @RequestMapping(value = "/consultant/consult/createSuccess.htm")
    public ModelAndView consultSuccess(final HttpServletRequest request, final Integer consultId,
                                       final ModelMap modelMap) {
        ModelAndView view = new ModelAndView("consultant/consult/createSuccess");
        CcResult result = serviceTemplate.execute(CcResult.class, new BlankServiceCallBack() {
            @Override
            public CcResult executeService() {
                AssertUtil.state(consultId != null && consultId > 0, "不合法的请求，当前记录不存在");
                ConsultBase consultBase = consultQueryComponent.queryById(consultId);
                AssertUtil.state(consultBase != null, "不合法的请求，当前记录不存在");
                modelMap.put("consultBase", consultBase);
                Service service = (Service) cachedComponent.getCache(
                    CacheEnum.SERVICE_CACHE.getCode(),
                    String.valueOf(consultBase.getServiceConfig().getId()));
                if (service != null) {
                    Article article = articleDAO.findById(service.getIntroArticleId());
                    modelMap.put("article", article);
                }
                return new CcResult(true);
            }
        });
        if (!result.isSuccess()) {
            throw new CcException(result.getMessage());
        }
        modelMap.put("result", result);
        return view;
    }

    @RequestMapping(value = "/consultant/consult/payTempForConsult.json")
    public @ResponseBody ModelMap paryTempForConsult(final HttpServletRequest request,
                                                     final Integer consultId, ModelMap modelMap) {
        modelMap.clear();
        CcResult result = consultService.payForConsult(consultId);
        modelMap.put("result", result);
        return modelMap;
    }

    //删除预约记录
    @RequestMapping(value = "consultant/deleteConsult.json", method = RequestMethod.POST)
    public @ResponseBody ModelMap deleteConsult(final HttpServletRequest request,
                                                final Integer consultId, ModelMap modelMap) {
        modelMap.clear();
        CcResult result = serviceTemplate.executeWithTx(CcResult.class, new BlankServiceCallBack() {
            @Override
            public CcResult executeService() {
                AssertUtil.state(consultId != null && consultId > 0, "非法请求，当前记录不存在");
                Consultant consultant = getConsultantInSession(request.getSession());
                AssertUtil.notNull(consultant, "当前请求过期，请刷新页面或登录后重试");
                Consult consult = consultDAO.findById(consultId);
                AssertUtil.state(consult.getConsultantId().equals(consultant.getId()),
                    "请不要尝试修改不属于您的记录");
                consult.setStep(ConsultStepEnum.DELETE.getValue());//将状态设定为已经完成
                consult.setGmtModified(new Date());
                consultDAO.update(consult);
                return new CcResult(consult);
            }
        });
        modelMap.put("result", result);
        return modelMap;
    }

}
