/**
 * 
 */
package com.ccconsult.web;

import java.util.Date;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ccconsult.base.AssertUtil;
import com.ccconsult.base.BlankServiceCallBack;
import com.ccconsult.base.CcException;
import com.ccconsult.base.CcLogger;
import com.ccconsult.base.CcResult;
import com.ccconsult.base.enums.MobileTokenEnum;
import com.ccconsult.base.enums.NotifySenderEnum;
import com.ccconsult.base.util.CodeGenUtil;
import com.ccconsult.base.util.DateUtil;
import com.ccconsult.base.util.LogUtil;
import com.ccconsult.base.util.ValidateUtil;
import com.ccconsult.core.notify.NotifySender;
import com.ccconsult.dao.MobileTokenDAO;
import com.ccconsult.pojo.MobileToken;
import com.cloopen.rest.sdk.CCPRestSDK;

/**
 * @author jingyu.dan
 * 
 */
@Controller
public class TokenController extends BaseController {
    @Autowired
    private MobileTokenDAO mobileTokenDAO;

    @Autowired
    private NotifySender   notifySender;

    /**
     * 注册面试官init页面
     * 
     * @return
     */
    @RequestMapping(value = "regist/sendSmsToken.json", method = RequestMethod.POST)
    public @ResponseBody
    ModelMap handleRequest(HttpServletRequest request, final String mobile, final Integer type,
                           ModelMap modelMap) {
        modelMap.clear();
        CcResult result = serviceTemplate.executeWithTx(CcResult.class, new BlankServiceCallBack() {
            @Override
            public CcResult executeService() {

                AssertUtil.notNull(ValidateUtil.isMobile(mobile), "您输入正确的手机号码");
                MobileTokenEnum tokenType = MobileTokenEnum.getByValue(type);
                AssertUtil.notNull(tokenType, "非法请求");
                MobileToken localToken = mobileTokenDAO.getByTypeAndMobile(tokenType.getValue(),
                    mobile, -1);
                if (localToken != null) {
                    AssertUtil.state(
                        DateUtil.getDiffSeconds(new Date(), localToken.getGmtModified()) >= 60,
                        "您操作太快，60s后重试");
                    AssertUtil.state(localToken.getSendTimes() <= 4,
                        "当前手机号码注册次数超限（4次），无法注册如果需要注册请到吐槽中反馈");
                    localToken.setGmtModified(new Date());
                    localToken.setSendTimes(localToken.getSendTimes() + 1);
                    mobileTokenDAO.update(localToken);
                    notifySender.notify(NotifySenderEnum.REGIST_SMS_NOTIFY.getCode(), localToken);
                    return new CcResult(localToken);
                }
                MobileToken mobileToken = new MobileToken();
                mobileToken.setGmtCreate(new Date());
                mobileToken.setGmtModified(new Date());
                mobileToken.setToken(CodeGenUtil.getFixLenthString(6));
                mobileToken.setTokenType(tokenType.getValue());
                mobileToken.setSendTimes(1);
                mobileToken.setMobile(mobile);
                mobileToken.setParams(mobile);
                mobileTokenDAO.save(mobileToken);
                notifySender.notify(NotifySenderEnum.REGIST_SMS_NOTIFY.getCode(), mobileToken);
                return new CcResult(mobileToken);
            }
        });
        modelMap.put("result", result);
        return modelMap;
    }

}
