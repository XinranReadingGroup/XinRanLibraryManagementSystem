package com.xinran.json.example;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import com.xinran.pojo.User;

public class URLTest {

    @Test
    public void test() {
        // URL url = null;
        // try {
        // url = new URL("fs2://XXXX.jpg");
        // } catch (MalformedURLException e) {
        // e.printStackTrace();
        // Assert.fail();
        // }
        // Assert.assertNotNull(url);

        String abc = "ssss";
        String np = null;
        String xx = abc + np;
        System.out.println(xx);
        System.out.println(Thread.currentThread().getContextClassLoader());
        
//        String utf8Test = "严";
      String utf8Test = "中国";

        utf8Test.codePoints().forEach(c->{
        	int x = c;
        	System.out.println(x);
        });

        System.out.println(new Date().getTime());
        System.out.println(System.currentTimeMillis());

        List<User> ul = new ArrayList();
        ul.add(new User());
        ul.add(new User());
        
        Long i =0L;
        ul.stream().forEach(user -> {
            
            user.setId(i);    
        });
    }

}
