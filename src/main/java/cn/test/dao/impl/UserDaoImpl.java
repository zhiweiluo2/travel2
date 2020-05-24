package cn.test.dao.impl;

import cn.test.dao.UserDao;
import cn.test.domain.User;
import cn.test.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class UserDaoImpl implements UserDao {

    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public User findByUsername(String username) {
        User user = null;
        try {   //若传递的用户名没有查询到一个user对象，或者 new BeanPropertyRowMapper 没有封装成功，不会返回null，会直接报异常，所以try catch
            //1、定义sql
            String sql = "select * from tab_user where username = ?";
            //2、执行sql  用户名查询只能查询出一个对象，所以用queryForObject()
            user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), username);
        } catch (Exception e) {   //不管出什么异常
            //先外头声明一个 默认值null 的 user,里面给 user 赋值，如果将来出现异常，证明user没有赋上值，然后就直接返回一个 默认值null，避免将来保存失败
        }
        return user;
    }

    @Override
    public void save(User user) {
        //1、定义sql
        String  sql = "insert into tab_user(username,password,name,birthday,sex,telephone,email,status,code) values(?,?,?,?,?,?,?,?,?)";
        //2、执行sql
        template.update(sql,user.getUsername(),
                user.getPassword(),
                user.getName(),
                user.getBirthday(),
                user.getSex(),
                user.getTelephone(),
                user.getEmail(),
                user.getStatus(),
                user.getCode()
                );
    }

    /**
     * 根据激活码查询用户对象
     * @param [code]
     * @return cn.test.domain.User
     */
    @Override
    public User findByCode(String code) {
        User user = null;
        try {
            //1、定义sql
            String sql = "select * from tab_user where code = ?";
            //2、执行sql  public <T> T queryForObject(String sql,@NotNull org.springframework.jdbc.core.RowMapper<T> rowMapper,Object... args)
            user = template.queryForObject(sql,new BeanPropertyRowMapper<User>(User.class),code);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }

        return user;
    }

    /**
     * 修改指定用户激活状态
     * @param [user]
     * @return void
     */
    @Override
    public void updateStatus(User user) {
        //1、定义sql
        String sql = "update tab_user set status = 'Y' where uid = ?";
        //2、执行sql
        template.update(sql,user.getUid());
    }

    /**
     * 根据用户名和密码查询用户的方法
     * @param [username, password]
     * @return cn.test.domain.User
     */
    @Override
    public User findByUsernameAndPassword(String username, String password) {
        User user = null;
        try {   //若传递的用户名没有查询到一个user对象，或者 new BeanPropertyRowMapper 没有封装成功，不会返回null，会直接报异常，所以try catch
            //1、定义sql
            String sql = "select * from tab_user where username = ? and password = ?";
            //2、执行sql  用户名查询只能查询出一个对象，所以用queryForObject()
            user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), username,password);
        } catch (Exception e) {   //不管出什么异常
            //先外头声明一个 默认值null 的 user,里面给 user 赋值，如果将来出现异常，证明user没有赋上值，然后就直接返回一个 默认值null，避免将来保存失败
        }
        return user;
    }

}
