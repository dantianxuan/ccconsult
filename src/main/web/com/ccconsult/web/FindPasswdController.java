/**
 * 
 */
package com.ccconsult.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ccconsult.base.AssertUtil;
import com.ccconsult.base.BlankServiceCallBack;
import com.ccconsult.base.CcResult;
import com.ccconsult.dao.ConsultantDAO;
import com.ccconsult.dao.CounselorDAO;
import com.ccconsult.enums.NotifySenderEnum;
import com.ccconsult.notify.NotifySender;
import com.ccconsult.pojo.Consultant;
import com.ccconsult.util.ValidateUtil;
import com.ccconsult.view.CounselorVO;

/**
 * @author jingyu.dan
 * 
 */
@Controller
public class FindPasswdController extends BaseController {

    @Autowired
    private NotifySender  notifySender;
    @Autowired
    private CounselorDAO  counselorDAO;
    @Autowired
    private ConsultantDAO consultantDAO;

    @RequestMapping(value = "/findPasswdInit.htm", method = RequestMethod.GET)
    public ModelAndView findPasswdPage(HttpServletRequest request) {
        ModelAndView view = new ModelAndView("content/findPasswd");
        return view;
    }

    @RequestMapping(value = "/findPasswd.htm", method = RequestMethod.GET)
    public ModelAndView findPasswd(HttpServletRequest request, final String accountMail,
                                   ModelMap modelMap) {
        ModelAndView view = new ModelAndView("content/findPasswd");
        CcResult result = serviceTemplate.execute(CcResult.class, new BlankServiceCallBack() {
            @Override
            public CcResult executeService() {
                AssertUtil.state(ValidateUtil.isEmail(accountMail), "账号格式不合法，请输入合法的邮箱账号");
                CounselorVO counselorVO = counselorDAO.findByEmail(accountMail);
                Consultant consultant = consultantDAO.findByEmail(accountMail);
                AssertUtil.state(counselorVO != null || consultant != null, "不存在的合法账号");
                if (counselorVO != null) {
                    notifySender.notify(NotifySenderEnum.FINDPASSWD_NOTIFY.getCode(), counselorVO);
                }
                if (consultant != null) {
                    notifySender.notify(NotifySenderEnum.FINDPASSWD_NOTIFY.getCode(), consultant);
                }
                return new CcResult(true);
            }
        });
        modelMap.put("accountMail", accountMail);
        modelMap.put("result", result);
        return view;
    }

}
