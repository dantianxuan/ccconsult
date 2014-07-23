/**
 * 
 */
package com.ccconsult.web;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ccconsult.base.AssertUtil;
import com.ccconsult.base.BlankServiceCallBack;
import com.ccconsult.base.CcConstrant;
import com.ccconsult.base.CcResult;
import com.ccconsult.dao.ArticleDAO;
import com.ccconsult.dao.ServiceConfigDAO;
import com.ccconsult.dao.ServiceDAO;
import com.ccconsult.enums.DataStateEnum;
import com.ccconsult.pojo.Article;
import com.ccconsult.pojo.Service;
import com.ccconsult.pojo.ServiceConfig;
import com.ccconsult.view.CounselorVO;

/**
 * @author jingyu.dan
 * 
 */
@Controller
public class ServiceController extends BaseController {

    @Autowired
    private ServiceConfigDAO serviceConfigDAO;
    @Autowired
    private ServiceDAO       serviceDAO;
    @Autowired
    private ArticleDAO       articleDAO;

    /**
     * 公共个人信息介绍页面
     * 
     * @param request
     * @param counselorId
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/serviceInfo.htm", method = RequestMethod.GET)
    public ModelAndView serviceInfo(HttpServletRequest request, Integer serviceId,
                                    final ModelMap modelMap) {
        Service service = serviceDAO.findById(serviceId);
        modelMap.put("service", service);
        if (service != null) {
            Article article = articleDAO.findById(service.getIntroArticleId());
            modelMap.put("article", article);
        }
        ModelAndView view = new ModelAndView("content/serviceInfo");
        return view;
    }

    /**
     * 公共个人信息介绍页面
     * 
     * @param request
     * @param counselorId
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/counselor/counselorService.htm", method = RequestMethod.GET)
    public ModelAndView counselorService(HttpServletRequest request, final ModelMap modelMap) {
        ModelAndView view = new ModelAndView("counselor/counselorService");
        return view;
    }

    @RequestMapping(value = "counselor/serviceConfig.htm", method = RequestMethod.GET)
    public ModelAndView counselorSelf(HttpServletRequest request, ModelMap modelMap) {
        ModelAndView view = new ModelAndView("counselor/serviceConfig");
        List<Service> services = serviceDAO.findAll();
        modelMap.put("services", services);
        return view;
    }

    @RequestMapping(value = "counselor/addService.json", method = RequestMethod.POST)
    public @ResponseBody
    ModelMap addService(final HttpServletRequest request, final ServiceConfig serviceConfig,
                        ModelMap modelMap) {
        modelMap.clear();
        final CounselorVO sessionCounselorVO = getCounselorInSession(request.getSession());
        CcResult result = serviceTemplate.executeWithTx(CcResult.class, new BlankServiceCallBack() {
            @Override
            public CcResult executeService() {
                AssertUtil.notNull(serviceConfig, "非法请求");
                Service service = serviceDAO.findById(serviceConfig.getServiceId());
                AssertUtil.notNull(service, "服务项目不存在");
                String[] priceRegions = service.getPriceRegion().replace("[", "").replace("]", "")
                    .split(CcConstrant.ALT_SEPARATOR);
                AssertUtil.state(NumberUtils.toInt(priceRegions[0]) <= serviceConfig.getPrice()
                                 && serviceConfig.getPrice() <= NumberUtils.toInt(priceRegions[1]),
                    "价格请设置在在预定区间");
                List<ServiceConfig> serviceConfigs = serviceConfigDAO
                    .findByCounselorIdAndServiceId(sessionCounselorVO.getCounselor().getId(),
                        serviceConfig.getServiceId());
                AssertUtil.state(CollectionUtils.isEmpty(serviceConfigs), "请手动删除原有的服务项目后才能新增新的服务项目");
                serviceConfig.setGmtCreate(new Date());
                serviceConfig.setState(DataStateEnum.NORMAL.getValue());
                serviceConfig.setCounselorId(sessionCounselorVO.getCounselor().getId());
                serviceConfigDAO.save(serviceConfig);
                reflushCouselorSession(serviceConfig.getCounselorId(), request.getSession());
                return new CcResult(serviceConfig);
            }
        });
        modelMap.put("result", result);
        return modelMap;
    }

    @RequestMapping(value = "counselor/removeService.json", method = RequestMethod.POST)
    public @ResponseBody
    ModelMap removeService(final HttpServletRequest request, final String serviceConfigId,
                           ModelMap modelMap) {
        modelMap.clear();
        final CounselorVO sessionCounselorVO = getCounselorInSession(request.getSession());
        CcResult result = serviceTemplate.executeWithTx(CcResult.class, new BlankServiceCallBack() {
            @Override
            public CcResult executeService() {
                ServiceConfig serviceConfig = serviceConfigDAO.findById(NumberUtils
                    .toInt(serviceConfigId));
                AssertUtil.state(
                    sessionCounselorVO.getCounselor().getId()
                        .equals(serviceConfig.getCounselorId()), "非法请求，请不要尝试修改别人的记录");
                serviceConfig.setState(DataStateEnum.DELETE.getValue());
                serviceConfigDAO.update(serviceConfig);
                reflushCouselorSession(serviceConfig.getCounselorId(), request.getSession());
                return new CcResult(serviceConfig);
            }
        });
        modelMap.put("result", result);
        return modelMap;
    }
}
