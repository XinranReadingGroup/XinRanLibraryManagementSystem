package com.xinran.dao.mapper;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.xinran.pojo.User;
import com.xinran.util.DateUtil;

/**
 * @author 高海军 帝奇 Apr 11, 2015 11:28:57 AM
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/spring.xml" })
//如果在类上面使用该注解，这样所有的测试方案都会自动的 rollback
//@Transactional
// 再注意saveEmployee方法上的两个注解：
//这个注解表示使用事务
@Transactional
//这个表示方法执行完以后回滚事务，如果设置为false，则不回滚
public class TestUserMapper {

    @Autowired
    private UserMapper userMapper;

    @Test
    // @Rollback(false)
    public void testAddUserAndFindById() {
        Date date = DateUtil.getCurrentDate();
        String area = "testArea";
        String email = "test@test.com";
        String mobile = "testMobile";
        String nickName = "testNickName";
        String password = "testPassword";
        String resetPasswordToken = "testResetPasswordToken";
        String salt = "testSalt";
        Integer score = 1122;
        String signature = "testSignature";
        Integer signInCount = 2211;
        String userName = "testUserName";

        User testUser = new User();
        testUser.setArea(area);
        testUser.setCurrentSignInAt(date);
        testUser.setEmail(email);
        testUser.setLastSignInAt(date);
        testUser.setMobile(mobile);
        testUser.setNickName(nickName);
        testUser.setPassword(password);
        testUser.setRememberCreatedAt(date);
        testUser.setResetPasswordSentAt(date);
        testUser.setResetPasswordToken(resetPasswordToken);
        testUser.setSalt(salt);
        testUser.setScore(score);
        testUser.setSignature(signature);
        testUser.setSignInCount(signInCount);
        testUser.setUserName(userName);
        userMapper.addUser(testUser);

        User user = userMapper.findUserById(testUser.getId());
        Assert.assertNotNull(user.getCreatedAt());
        Assert.assertNotNull(user.getUpdatedAt());

        Assert.assertEquals(area, testUser.getArea());
        Assert.assertEquals(date, testUser.getCurrentSignInAt());
        Assert.assertEquals(email, testUser.getEmail());
        Assert.assertEquals(date, testUser.getLastSignInAt());
        Assert.assertEquals(mobile, testUser.getMobile());
        Assert.assertEquals(nickName, testUser.getNickName());
        Assert.assertEquals(password, testUser.getPassword());
        Assert.assertEquals(date, testUser.getRememberCreatedAt());
        Assert.assertEquals(date, testUser.getResetPasswordSentAt());
        Assert.assertEquals(resetPasswordToken, testUser.getResetPasswordToken());
        Assert.assertEquals(salt, testUser.getSalt());
        Assert.assertEquals(score, testUser.getScore());
        Assert.assertEquals(signature, testUser.getSignature());
        Assert.assertEquals(signInCount, testUser.getSignInCount());
        Assert.assertEquals(userName, testUser.getUserName());

    }

}
