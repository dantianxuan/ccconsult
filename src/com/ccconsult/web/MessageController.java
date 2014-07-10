/**
 * 
 */
package com.ccconsult.web;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ccconsult.base.AssertUtil;
import com.ccconsult.base.BlankServiceCallBack;
import com.ccconsult.base.CcConstrant;
import com.ccconsult.base.CcResult;
import com.ccconsult.dao.ConsultDAO;
import com.ccconsult.dao.MessageDAO;
import com.ccconsult.enums.MessageRelTypeEnum;
import com.ccconsult.enums.UserRoleEnum;
import com.ccconsult.pojo.Consult;
import com.ccconsult.pojo.Consultant;
import com.ccconsult.pojo.Message;
import com.ccconsult.view.CounselorVO;

/**
 * @author jingyu.dan
 * 
 */
@Controller
public class MessageController extends BaseController {

    @Autowired
    private ConsultDAO consultDAO;
    @Autowired
    private MessageDAO messageDAO;

    @RequestMapping(value = "/sendShotMessage.json", method = RequestMethod.POST)
    public @ResponseBody
    ModelMap sendMessage(final HttpServletRequest request, final Message message, ModelMap modelMap) {
        modelMap.clear();
        CcResult result = serviceTemplate.executeWithTx(CcResult.class, new BlankServiceCallBack() {
            @Override
            public CcResult executeService() {
                if (message.getCreatorRole() == UserRoleEnum.CONSULTANT.getValue()) {
                    Consultant consultant = getConsultantInSession(request.getSession());
                    AssertUtil.notNull(consultant, "用户不存在的非法消息，请重新登录");
                } else {
                    CounselorVO counselorVO = getCounselorInSession(request.getSession());
                    AssertUtil.notNull(counselorVO, "用户不存在的非法消息，请重新登录");
                }
                Consult consult = consultDAO.findById(message.getRelId());
                AssertUtil.notNull(consult, "关联记录不存在的非法消息，请重新登录");
                message.setGmtCreate(new Date());
                message.setRelType(MessageRelTypeEnum.CONSULT.getValue());
                AssertUtil.notNull(message, "非法请求");
                AssertUtil.notBlank(message.getMessage(), "消息内容不能为空");
                AssertUtil.state(message.getMessage().length() <= CcConstrant.COMMON_4096_LENGTH,
                    "消息长度太长");
                messageDAO.save(message);
                return new CcResult(message);
            }
        });
        modelMap.put("result", result);
        return modelMap;
    }
}
