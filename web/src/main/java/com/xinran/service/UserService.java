package com.xinran.service;

import com.xinran.exception.UserException;
import com.xinran.pojo.User;

/**
 * @author 高海军 帝奇 Apr 6, 2015 5:07:09 PM
 */
public interface UserService {

    /**
     * @param identifier
     * @param password
     * @return
     */
    public Long signUpForMobileIndentifier(String identifier, String password, String nickName) throws UserException;

    public Long signIn(String identifier, String password) throws UserException;

    /**
     * @param identifier
     * @param password
     * @return
     */
    public void signOut(String accessToken) throws UserException;

    /**
     * @param identifier
     * @param password
     * @return
     */
    public User findUserByUserId(Long userId);

    /**
     * @param identifier
     * @param password
     * @return
     */
    public void updateUser(User user);
}
