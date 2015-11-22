package com.xinran.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;
import com.xinran.constant.SystemResultCode;
import com.xinran.dao.mapper.UserMapper;
import com.xinran.exception.UserException;
import com.xinran.pojo.User;
import com.xinran.service.UserService;
import com.xinran.util.DateUtil;
import com.xinran.util.StringUtil;

/**
 * @author 高海军 帝奇 Apr 9, 2015 9:30:37 PM
 */
@Service
public class UserServiceImpl implements UserService {

    private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserMapper userMapper;

    /*
     * (non-Javadoc)
     * @see com.xinran.service.UserService#signUp(java.lang.String, java.lang.String)
     */
    @Override
    public Long signUpForMobileIndentifier(String identifier, String password, String nickName)
 throws UserException {
        User userIfExists = userMapper.findUserByMobile(identifier);

        if (null == userIfExists) {
            String salt = StringUtil.random(8);
            String hash = calcHash(password, salt);

            User signUpUser = new User();
            signUpUser.setMobile(identifier);
            signUpUser.setSalt(salt);
            signUpUser.setNickName(nickName);
            signUpUser.setPassword(hash);
            userMapper.addUser(signUpUser);
            return signUpUser.getId();
        } else {
            throw new UserException(SystemResultCode.IndentifierAlreadyBeenTaken );
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
    public Long signIn(String identifier, String password) throws UserException {
        User user = userMapper.findUserByMobile(identifier);

        // TODO 非激活校验
        if (null == user) {
            throw new UserException(SystemResultCode.InvalidUserNameOrPassowrd );
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
            throw new UserException(SystemResultCode.InvalidUserNameOrPassowrd );
        }

    }

    /*
     * (non-Javadoc)
     * @see com.xinran.service.UserService#signOut(java.lang.String)
     */
    @Override
    public void signOut(Long userId) throws UserException {
        // TODO empty implement
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

    @Override
    public void updateUser(User user) {
        userMapper.updateUser(user);
    }

    @Override
    public User registerUserByMobile(String phone) {
        String salt = StringUtil.random(8);
        String hash = calcHash("123456", salt);
        User signUpUser = new User();
        signUpUser.setMobile(phone);
        signUpUser.setSalt(salt);
        signUpUser.setNickName(phone);
        signUpUser.setPassword(hash);
        userMapper.addUser(signUpUser);
        return signUpUser;
    }


    private List<String> administrators;
    private ReentrantLock adminLocker = new ReentrantLock();
    @Override
    public boolean isAdmin(User user) {
        if (administrators == null && adminLocker.tryLock()) {
            BufferedReader reader = null;
            try {
                administrators = new ArrayList<>();
                InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream("/administrators");
                reader = new BufferedReader(new InputStreamReader(resourceAsStream, "UTF-8"));
                String phone = reader.readLine();
                while (phone != null && phone.length() > 0) {
                    administrators.add(phone.trim());
                    phone = reader.readLine();
                }
            } catch (Exception e) {
                LOG.error("Error to load administrators.", e);
            }finally {
                adminLocker.unlock();
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        LOG.error("Error to close reader", e);
                    }
                }
            }
        }
        return administrators.contains(user.getMobile());
    }
}
