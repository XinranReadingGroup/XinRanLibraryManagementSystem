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
    public void signOut(Long userId) throws UserException;

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

    /**
     * 根据手机号码生成默认账号。
     *
     * 默认密码123456
     * 默认昵称为手机号码
     *
     * // FIXME 生成默认账号之后应该能够短信通知到用户。
     *
     *
     * @param phone 手机号码
     * @return
     */
    public User registerUserByMobile(String phone);

    /**
     *
     * 判断是否为管理员
     *
     * @param user
     * @return
     */
    public boolean isAdmin(User user);
}
