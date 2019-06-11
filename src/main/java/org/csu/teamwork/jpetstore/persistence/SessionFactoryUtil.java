package org.csu.teamwork.jpetstore.persistence;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

/**
 * @email 1694522669@qq.com
 * @author: A
 * @date: 2019/5/23 10:49
 */
public class SessionFactoryUtil {
    private static String resource = "mybatis-config.xml";
    public static SqlSessionFactory getSqlSessionFactory() throws IOException {
        SqlSessionFactory sqlSessionFactory;
        InputStream inputStream = Resources.getResourceAsStream(resource);
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        return sqlSessionFactory;
    }
}