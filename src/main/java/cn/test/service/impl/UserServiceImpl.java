package cn.test.service.impl;


import cn.test.dao.UserDao;
import cn.test.dao.impl.UserDaoImpl;
import cn.test.domain.User;
import cn.test.service.UserService;
import cn.test.util.MailUtils;
import cn.test.util.UuidUtil;

public class UserServiceImpl implements UserService {

    private UserDao userDao = new UserDaoImpl();
    /**
     * 注册用户
     * @param [user]
     * @return boolean
     */
    @Override
    public boolean regist(User user) {
        //1、根据用户名查询用户对象
        User u = userDao.findByUsername(user.getUsername());  //成员变量不能和局部变量同名
        //判断 u 是否为空
        if (u != null){
            return false;
        }
        //2、保存用户信息
        //2.1 设置激活码，唯一字符串
        user.setCode(UuidUtil.getUuid());
        //2.2 设置激活码 状态
        user.setStatus("N");

        userDao.save(user);

        //3、激活邮件发送，邮件正文？
        String content = "<a href='http://localhost/travel2/activeUserServlet?code="+user.getCode()+"'>点击激活【黑马旅游网】</a>";
        MailUtils.sendMail(user.getEmail(),content,"激活邮件");
        return true;
    }

    /**
     * 激活用户
     * @param [code]
     * @return boolean
     */
    @Override
    public boolean active(String code) {
        //1、根据 激活码 查询 用户对象
        User user = userDao.findByCode(code);
        if (user != null){
            //2、当有用户对象，调用dao的 修改激活状态 的方法
                userDao.updateStatus(user);
                return true;
        }else{
            return false;
        }

    }
    /**
     * 登录方法
     * @param [user]
     * @return cn.test.domain.User
     */
    @Override
    public User login(User user) {

       return userDao.findByUsernameAndPassword(user.getUsername(),user.getPassword());
    }


}
