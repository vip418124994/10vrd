package cn.tedu.filter;

import cn.tedu.entity.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
/*urlPatterns设置处理路径 通过{}方式设置多个路径*/
@WebFilter(filterName = "MyFilter",urlPatterns = {"/showsend","/showbanner"})
public class MyFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        //如果执行过滤后,允许访问后面的资源则调用下面的代码 不允许则不调用
        //chain.doFilter(req, resp);
        //过滤器中的请求和响应对象为Servlet库暖其你去响应对象的父类
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user==null){
            System.out.println("没有登录 拦截!");
            response.sendRedirect("/showlogin");
        }else {
            System.out.println("登陆过 放行!");
            chain.doFilter(req,resp);//放行
        }



    }

    public void init(FilterConfig config) throws ServletException {

    }

}
