package cn.tedu.controller;

import cn.tedu.dao.CategoryDao;
import cn.tedu.dao.ProductDao;
import cn.tedu.entity.Category;
import cn.tedu.entity.Product;
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

@WebServlet(name = "DetailServlet",urlPatterns = "/detail")
public class DetailServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        System.out.println("详情id:"+id);
        ProductDao dao2 = new ProductDao();

        HttpSession session = request.getSession();
        String viewId = (String) session.getAttribute("view"+id);
        if (viewId==null){
            //浏览量+1
            dao2.viewById(id);
            session.setAttribute("view"+id,id);
        }


        Product p = dao2.findById(id);
        Context context = new Context();
        context.setVariable("p",p);

        CategoryDao cdao = new CategoryDao();
        List<Category> list = cdao.findAll();
        context.setVariable("list",list);

        List<Product> list3 = dao2.findViewList();
        context.setVariable("list3",list3);

        List<Product> list4 = dao2.findLikeList();
        context.setVariable("list4",list4);
        //把登录的用户对象添加到容器中
        context.setVariable("user",
                request.getSession().getAttribute("user"));


        ThUtils.print("detail.html",context,response);
    }
}
