
package com.xinran.service;

import com.xinran.exception.SignInValidationException;
import com.xinran.exception.SignOutValidationException;
import com.xinran.exception.SignUpValidationException;

/**
 * @author 高海军 帝奇 Apr 6, 2015 5:07:09 PM
 */
public interface UserService {

    /**
     * @param identifier
     * @param password
     * @return
     */
    public Long signUp(String identifier, String password) throws SignUpValidationException;

    public Long signIn(String identifier, String password) throws SignInValidationException;

    /**
     * @param identifier
     * @param password
     * @return
     */
    public void signOut(String accessToken) throws SignOutValidationException;
}
