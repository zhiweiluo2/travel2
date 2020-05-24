package cn.test.web.servlet;

import cn.test.service.UserService;
import cn.test.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/activeUserServlet")
public class ActiveUserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1、获取激活码
        String code = request.getParameter("code");
        //2、判断是否有激活码
        if (code != null){
            UserService service = new UserServiceImpl(); //当有激活码，调用service完成激活
            boolean flag = service.active(code);  //激活成功如否，都返回一个布尔值   生成方法到service

            //3、判断标记
            String msg;
            if (flag){
                //激活成功
                msg = "激活成功，请<a href='login.html'>登录</a>";
            }else{
                //激活失败
                msg = "激活失败，请联系管理员！";
            }
            response.setContentType("text/html;charset=utf-8");  //以防乱码
            response.getWriter().write(msg);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
