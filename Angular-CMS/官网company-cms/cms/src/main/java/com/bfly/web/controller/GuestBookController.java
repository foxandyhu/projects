package com.bfly.web.controller;

import com.bfly.cms.entity.GuestBook;
import com.bfly.cms.service.IGuestBookService;
import com.bfly.common.ResponseData;
import com.bfly.common.ResponseUtil;
import com.bfly.core.base.action.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * 留言Controller
 *
 * @author andy_hulibo@163.com
 * @date 2019/9/13 11:32
 */
@RestController("webGuestBookController")
@RequestMapping(value = "/guestbook")
public class GuestBookController extends BaseController {

    @Autowired
    private IGuestBookService guestBookService;

    /**
     * 发表留言
     *
     * @author andy_hulibo@163.com
     * @date 2019/9/13 12:15
     */
    @PostMapping(value = "/post")
    public void postGuestBook(HttpServletResponse response, @RequestBody GuestBook book) {
        guestBookService.save(book);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(""));
    }
}
