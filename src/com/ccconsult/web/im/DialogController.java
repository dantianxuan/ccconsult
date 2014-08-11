/**
 * 
 */
package com.ccconsult.web.im;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.ccconsult.base.AssertUtil;
import com.ccconsult.base.BlankServiceCallBack;
import com.ccconsult.base.CcConstrant;
import com.ccconsult.base.CcResult;
import com.ccconsult.base.PageQuery;
import com.ccconsult.enums.FileTypeEnum;
import com.ccconsult.pojo.Message;
import com.ccconsult.pojo.ResumeConsult;
import com.ccconsult.view.ConsultBase;
import com.ccconsult.view.CounselorVO;
import com.ccconsult.view.ResumeConsultVO;
import com.ccconsult.web.BaseController;

/**
 * @author jingyu.dan
 * 
 */
@Controller
public class DialogController extends BaseController {

	@RequestMapping(value = "counselor/dialog.htm")
	public ModelAndView counselorDiaglog(HttpServletRequest request,
			PageQuery query, ModelMap modelMap) {
		ModelAndView view = new ModelAndView("counselor/dialog");
		return view;
	}

	/**
	 * 公司邮箱链接注册
	 * 
	 * @return
	 */
	@RequestMapping(value = "im/fetchMessage.json", method = RequestMethod.GET)
	public @ResponseBody
	ModelMap updateResumeReview(final HttpServletRequest request,
			final Integer dialogId, final ModelMap modelMap) {
		modelMap.clear();
		// 读取最近一条信息

		// 返回对象信息

		return modelMap;
	}

	/**
	 * 公司邮箱链接注册
	 * 
	 * @return
	 */
	@RequestMapping(value = "im/sendMessage.json", method = RequestMethod.GET)
	public @ResponseBody
	ModelMap updateResumeReview(final HttpServletRequest request,
			final Message message, final ModelMap modelMap) {
		modelMap.clear();
		// 读取最近一条信息

		// 返回对象信息

		return modelMap;
	}

}
