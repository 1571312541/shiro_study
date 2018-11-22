package com.zhangchao.shiro.dao.impl;

import com.zhangchao.shiro.dao.IUserDAO;
import com.zhangchao.shiro.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class UserDAOImpl implements IUserDAO {

    private JdbcTemplate template;

    @Autowired
    private void setDataSource(DataSource dataSource){
        this.template = new JdbcTemplate(dataSource);
    }

    @Override
    public User getUserByUsername(String username) {
        try {
            String sql = "select * from user where username = ? ";
            User users = template.queryForObject(sql, new RowMapper<User>() {
                @Override
                public User mapRow(ResultSet resultSet, int i) throws SQLException {
                    User user = new User();
                    user.setUsername(resultSet.getString("username"));
                    user.setId(resultSet.getLong("id"));
                    user.setPassword(resultSet.getString("password"));
                    return user;
                }
            }, username);

            if (users != null) {
                return users;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;

    }
}
