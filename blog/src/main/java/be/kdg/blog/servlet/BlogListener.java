package be.kdg.blog.servlet;

import be.kdg.blog.domain.Blog;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class BlogListener implements ServletContextListener {
    private Blog blog;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        blog = new Blog();
        blog.vulBlog();

        sce.getServletContext().setAttribute("blog", blog);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
