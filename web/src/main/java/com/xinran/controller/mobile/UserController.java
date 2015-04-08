
package com.xinran.controller.mobile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xinran.service.UserService;

/**
 * @author 高海军 帝奇 Apr 8, 2015 7:50:59 AM
 */
@RestController
@RequestMapping("/web/user/signIn")
public class UserController {

    private Logger      log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserService userService;

    @RequestMapping("/user/signIn")
    public String signIn(@RequestParam(value = "identifier") String identifier,
                         @RequestParam(value = "password") String password, HttpServletRequest request) {
        boolean canSignIn = userService.canSignIn(identifier, password);

        if (canSignIn) {
            HttpSession session = request.getSession(true);
            session.getId();
        }

        // write accesToken
        return "homePage";
    }
}
