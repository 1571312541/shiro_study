package com.zhangchao.shiro;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.junit.Test;

/**
 * 〈MD5加密〉
 *
 * @author 22902
 * @create 2018/11/21
 */
public class MD5Test {

    @Test
    public void md5Test() {
        //密码  明文
        String password = "666";
        //加密md5  fae0b27c451c728867a567e8c1bb4e53
        Md5Hash md5Hash = new Md5Hash(password);
        System.out.println(md5Hash);
        //加密md5 + 盐  8ab1db3f57dfd36248b1dcd7adc12ab0
        md5Hash = new Md5Hash(password,"zhangchao");
        System.out.println(md5Hash);
        //加密md5 + 盐 + 散列次数  e925117800d6e0b60098b681809b3789
        md5Hash = new Md5Hash(password,"zhangchao",5);
        System.out.println(md5Hash);


    }
}
