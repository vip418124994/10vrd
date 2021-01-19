package cn.tedu.controller;

import cn.tedu.dao.ProductDao;
import cn.tedu.entity.Product;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@MultipartConfig
@WebServlet(name = "SendServlet",urlPatterns = "/send")
public class SendServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String title = request.getParameter("title");
        String author = request.getParameter("author");
        String intro = request.getParameter("intro");
        String categoryId = request.getParameter("categoryId");

        System.out.println(title);
        System.out.println(author);
        System.out.println(intro);
        System.out.println(categoryId);

        Part filepart = request.getPart("file");
        String info = filepart.getHeader("content-disposition");
        String suffix = info.substring(info.lastIndexOf("."),info.length()-1);
        System.out.println(suffix);
        String fileName = UUID.randomUUID()+suffix;

        SimpleDateFormat f = new SimpleDateFormat("yyyy/MM/dd/");
        Date date = new Date();
        String datePath = f.format(date);
        System.out.println(datePath);

        String path = request.getServletContext().getRealPath("images/"+datePath);
        new File(path).mkdirs();
        filepart.write(path+fileName);

        //把参数封装到Product实体类中
        Product p = new Product(0,title,author,intro,
                "images/"+datePath+fileName,0,0,
                System.currentTimeMillis(),Integer.parseInt(categoryId));
        System.out.println(p);
        ProductDao dao = new ProductDao();
        dao.insert(p);
        response.sendRedirect("/home");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
