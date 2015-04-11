package com.xinran.service.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;
import com.xinran.constant.ExceptionCode;
import com.xinran.dao.mapper.UserMapper;
import com.xinran.exception.SignInValidationException;
import com.xinran.exception.SignOutValidationException;
import com.xinran.exception.SignUpValidationException;
import com.xinran.pojo.User;
import com.xinran.service.UserService;
import com.xinran.util.DateUtil;
import com.xinran.util.StringUtil;

/**
 * @author 高海军 帝奇 Apr 9, 2015 9:30:37 PM
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    /*
     * (non-Javadoc)
     * @see com.xinran.service.UserService#signUp(java.lang.String, java.lang.String)
     */
    @Override
    public Long signUpForMobileIndentifier(String identifier, String password) throws SignUpValidationException {
        User userIfExists = userMapper.findUserByMobile(identifier);

        if (null == userIfExists) {
            String salt = StringUtil.random(8);
            String hash = calcHash(password, salt);

            User signUpUser = new User();
            signUpUser.setMobile(identifier);
            signUpUser.setSalt(salt);
            signUpUser.setPassword(hash);
            userMapper.addUser(signUpUser);
            return signUpUser.getId();
        } else {
            throw new SignUpValidationException(ExceptionCode.IndentifierAlreadyBeenTaken.getCode());
        }

    }

    private String calcHash(String password, String salt) {
        return Hashing.sha256().hashString(password + salt, Charsets.UTF_8).toString();
    }

    /*
     * (non-Javadoc)
     * @see com.xinran.service.UserService#signIn(java.lang.String, java.lang.String)
     */
    @Override
    public Long signIn(String identifier, String password) throws SignInValidationException {
        User user = userMapper.findUserByMobile(identifier);

        // TODO 非激活校验
        if (null == user) {
            throw new SignInValidationException(ExceptionCode.InvalidUserNameOrPassowrd.getCode());
        }

        String actualHash = user.getPassword();
        String expectedHash = calcHash(password, user.getSalt());

        if (StringUtils.equals(expectedHash, actualHash)) {
            user.setLastSignInAt(user.getCurrentSignInAt());
            user.setCurrentSignInAt(DateUtil.getCurrentDate());
            user.setSignInCount(user.getSignInCount() + 1);
            userMapper.updateUser(user);
            return user.getId();
        } else {
            throw new SignInValidationException(ExceptionCode.InvalidUserNameOrPassowrd.getCode());
        }

    }

    /*
     * (non-Javadoc)
     * @see com.xinran.service.UserService#signOut(java.lang.String)
     */
    @Override
    public void signOut(String accessToken) throws SignOutValidationException {

    }

    /*
     * (non-Javadoc)
     * @see com.xinran.service.UserService#findUserByUserId(java.lang.Long)
     */
    @Override
    public User findUserByUserId(Long userId) {
        User user = userMapper.findUserById(userId);
        return user;
    }

}
