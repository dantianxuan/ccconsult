/**
 * ccinterviewer.com Inc.
 * Copyright (c) 2014-2014 All Rights Reserved.
 */
package com.ccconsult.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
import com.ccconsult.base.PageList;
import com.ccconsult.base.enums.CommentRelTypeEnum;
import com.ccconsult.base.enums.UserRoleEnum;
import com.ccconsult.base.util.StringUtil;
import com.ccconsult.dao.CommentDAO;
import com.ccconsult.dao.ConsultantDAO;
import com.ccconsult.dao.CounselorDAO;
import com.ccconsult.pojo.Comment;
import com.ccconsult.pojo.Consultant;
import com.ccconsult.web.view.CounselorVO;

/**
 * @author jingyu.dan
 * @version $Id: CompanyController.java, v 0.1 2014-5-31 上午10:19:52 jingyu.dan Exp $
 */
@Controller
public class TuCaoController extends BaseController {
    @Autowired
    private CommentDAO    commentDAO;
    @Autowired
    private CounselorDAO  counselorDAO;
    @Autowired
    private ConsultantDAO consultantDAO;

    @RequestMapping(value = "/tucao.htm", method = RequestMethod.GET)
    public ModelAndView tuCaoInit(HttpServletRequest request, Integer pageNo, Integer pageSize,
                                  ModelMap modelMap) {
        ModelAndView view = new ModelAndView("content/tucao");
        if (pageNo == null || pageNo == 0) {
            pageNo = 1;
        }
        if (pageSize == null) {
            pageSize = 20;
        }
        PageList<Comment> pageList = commentDAO.queryByThemeIdAndType(0,
            CommentRelTypeEnum.TUCAO.getValue(), pageNo, pageSize);
        if (!CollectionUtils.isEmpty(pageList.getData())) {
            List<Integer> counselorIds = new ArrayList<Integer>();
            List<Integer> consultantIds = new ArrayList<Integer>();
            for (Comment comment : pageList.getData()) {
                if (comment.getCreatorId() == null) {
                    continue;
                }
                if (comment.getCreatorRole().equals(UserRoleEnum.COUNSELOR.getValue())) {
                    counselorIds.add(comment.getCreatorId());
                }
                if (comment.getCreatorRole().equals(UserRoleEnum.CONSULTANT.getValue())) {
                    consultantIds.add(comment.getCreatorId());
                }
            }
            modelMap.put("counselorMap", counselorDAO.getByIds(counselorIds));
            modelMap.put("consultantMap", consultantDAO.getByIds(consultantIds));
        }
        modelMap.put("pageList", pageList);
        return view;
    }

    @RequestMapping(value = "/tucao.json", method = RequestMethod.POST)
    public @ResponseBody
    ModelMap tuCaoInit(final HttpServletRequest request, final Comment comment, ModelMap modelMap) {
        modelMap.clear();
        CcResult result = serviceTemplate.executeWithTx(CcResult.class, new BlankServiceCallBack() {
            @Override
            public CcResult executeService() {
                AssertUtil.notNull(comment, "信息不存在");
                CounselorVO counselorVO = getCounselorInSession(request.getSession());
                if (counselorVO != null) {
                    comment.setCreatorId(counselorVO.getCounselor().getId());
                    comment.setCreatorRole(UserRoleEnum.COUNSELOR.getValue());
                }
                Consultant consultant = getConsultantInSession(request.getSession());
                if (consultant != null) {
                    comment.setCreatorId(consultant.getId());
                    comment.setCreatorRole(UserRoleEnum.CONSULTANT.getValue());
                }
                AssertUtil.state(!StringUtil.isBlank(comment.getContent()), "吐槽信息不能为空");
                AssertUtil.state(comment.getContent().length() <= CcConstrant.COMMON_256_LENGTH,
                    "用户名称长度不能超过256个字符");
                comment.setGmtCreate(new Date());
                comment.setRelId(0);
                comment.setRelType(CommentRelTypeEnum.TUCAO.getValue());
                commentDAO.save(comment);
                return new CcResult(comment);
            }
        });
        modelMap.put("result", result);
        return modelMap;
    }
}
