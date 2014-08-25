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

import com.ccconsult.base.PageList;
import com.ccconsult.base.enums.UserRoleEnum;
import com.ccconsult.dao.AccountDAO;
import com.ccconsult.dao.AccountTransDAO;
import com.ccconsult.pojo.Account;
import com.ccconsult.pojo.AccountTrans;
import com.ccconsult.pojo.Consultant;

/**
 * @author jingyu.dan
 * 
 */
@Controller
public class AccountController extends BaseController {

    @Autowired
    private AccountDAO      accountDAO;
    @Autowired
    private AccountTransDAO accountTransDAO;

    @RequestMapping(value = "consultant/personalAccount.htm", method = RequestMethod.GET)
    public ModelAndView queryAccountInfo(HttpServletRequest request, final Integer consultId,
                                         final Integer pageSize, final Integer pageNo,
                                         final Integer transType, final ModelMap modelMap) {
        ModelAndView view = new ModelAndView("consultant/personalAccount");
        Consultant consultant = getConsultantInSession(request.getSession());
        Account account = accountDAO.findByRoleIdAndType(consultant.getId(),
            UserRoleEnum.CONSULTANT.getValue());
        modelMap.put("account", account);
        PageList<AccountTrans> accountTrans = accountTransDAO.queryPaged(consultant.getId(),
            UserRoleEnum.CONSULTANT.getValue(), transType == null ? 0 : transType, 0,
            pageSize == null ? 20 : pageSize, pageNo == null ? 1 : pageNo);
        modelMap.put("accountTrans", accountTrans);
        return view;
    }
}
