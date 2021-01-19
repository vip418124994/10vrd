package cn.tedu.controller;

import cn.tedu.dao.BannerDao;
import cn.tedu.dao.CategoryDao;
import cn.tedu.dao.ProductDao;
import cn.tedu.entity.Banner;
import cn.tedu.entity.Category;
import cn.tedu.entity.Product;
import cn.tedu.entity.User;
import cn.tedu.utils.ThUtils;
import org.thymeleaf.context.Context;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "HomeServlet",urlPatterns = "/home")
public class HomeServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取传递过来的分类id
        String cid = request.getParameter("cid");
        //获取搜索关键字
        String keyword = request.getParameter("keyword");



        //显示首页页面
        Context context = new Context();
        CategoryDao dao = new CategoryDao();
        List<Category> list = dao.findAll();
        context.setVariable("list",list);


        BannerDao dao1 = new BannerDao();
        List<Banner> list1 = dao1.findAll();
        context.setVariable("list1",list1);

        //获取session对象
        HttpSession session = request.getSession();
        //取出保存的用户对象
        User user = (User) session.getAttribute("user");
//        if (user==null){
//            System.out.println("从未登录过");
//        }else {
//            System.out.println("已经登录过");
//        }
        //把用户对象装进容器
        context.setVariable("user",user);

        //查询出所有的作品并装进context容器
        ProductDao dao2 = new ProductDao();

        if (cid!=null) {//查询某个分类的作品
             List<Product> list2 = dao2.findByCID(cid);
             context.setVariable("list2", list2);
        }else if (keyword!=null){
             List<Product> list2 = dao2.findByKeyword(keyword);
             context.setVariable("list2",list2);
        }else {//查询所有作品
            List<Product> list2 = dao2.findAll();
            context.setVariable("list2",list2);
            System.out.println("所有作品:"+list2);
        }


        List<Product> list3 = dao2.findViewList();
        context.setVariable("list3",list3);

        List<Product> list4 = dao2.findLikeList();
        context.setVariable("list4",list4);


        ThUtils.print("home.html",context,response);

    }
}
