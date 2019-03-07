package me.changjie.controller;

import me.changjie.common.Response;
import me.changjie.common.UserInfoConstant;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ChangJie on 2019-03-07.
 */
@Controller
public class UserInfoController {


    @RequestMapping(value = "login")
    @ResponseBody
    public Response login(String userName){
        Response response = new Response();
        Integer userId = UserInfoConstant.nameIdMap.get(userName);
        if(userId == null){
            response.setResult("400");
            response.setMessage("用户不存在");
            return response;
        }

        response.setData(userId);
        return response;
    }

    @RequestMapping(value = "toChatRoom")
    public String chatRoom(Integer userId, HttpServletRequest request){
        request.setAttribute("userId", userId);
        return "chatRoom";
    }
}
