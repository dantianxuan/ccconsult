/**
 * 
 */
package com.ccconsult.web;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ccconsult.base.PageList;
import com.ccconsult.base.enums.UserRoleEnum;
import com.ccconsult.dao.AccountDAO;
import com.ccconsult.dao.TransRecordDAO;
import com.ccconsult.pojo.Account;
import com.ccconsult.pojo.Consultant;
import com.ccconsult.pojo.TransRecord;

/**
 * 账户信息
 * 
 * @author jingyu.dan
 */
@Controller
public class AccountController extends BaseController {

    @Autowired
    private AccountDAO     accountDAO;
    @Autowired
    private TransRecordDAO transRecordDAO;

    @RequestMapping(value = "consultant/personalAccount.htm", method = RequestMethod.GET)
    public ModelAndView queryAccountInfo(HttpServletRequest request, final Integer transType,
                                         final Integer pageSize, final Integer pageNo,
                                         final ModelMap modelMap) {
        ModelAndView view = new ModelAndView("consultant/personalAccount");
        Consultant consultant = getConsultantInSession(request.getSession());
        Account account = accountDAO.queryByRoleForUpdate(consultant.getId(),
            UserRoleEnum.CONSULTANT.getValue());
        modelMap.put("account", account);
        int localTransType = transType == null ? 0 : transType;
        PageList<TransRecord> pageList = transRecordDAO.queryPaged(consultant.getId(),
            UserRoleEnum.CONSULTANT.getValue(), localTransType, pageSize == null ? 20 : pageSize,
            pageNo == null ? 1 : pageNo);
        modelMap.put("pageList", pageList);
        modelMap.put("transType", localTransType);
        return view;
    }
}
