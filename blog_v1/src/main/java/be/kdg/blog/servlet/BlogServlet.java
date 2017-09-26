package be.kdg.blog.servlet;

import be.kdg.blog.domain.Entry;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@WebServlet("/blog")
public class BlogServlet extends HttpServlet{

    private ArrayList<Entry> blog;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        if (blog == null) {
            this.vulBlog();
        }

        resp.setContentType("text/html");
        resp.getWriter().print("<html><head><title>Blog</title></head><body><h1>Mijn Blog</h1>");

        for (Entry entry:
             blog) {
            resp.getWriter().printf("<h2>%s</h2><p>%s</p>", entry.getSubject(), entry.getMessage());
        }

        resp.getWriter().print("<form method = \"POST\">" +
                "<label>Onderwerp</label> <input type = \"text\" name = \"onderwerp\" />" +
                "<br>" +
                "<label>Bericht</label> <input type = \"text\" name = \"bericht\" />" +
                "<br>" +
                "<input type = \"submit\" value = \"Verstuur\"/>" +
                "</form>");

        resp.getWriter().print("</body></html>");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Entry entry = new Entry(req.getParameter("onderwerp"), req.getParameter("bericht"));

        addToBlog(entry);

        this.doGet(req, resp);
    }

    private void vulBlog() {
        blog = new ArrayList<>();
        Entry e1 = new Entry("Naar de bakker geweest", "Was wel leuk...");
        Entry e2 = new Entry("Netflix gekeken", "Moet je ook eens doen!");
        Entry e3 = new Entry("Oefeningetjes Prog 3 gemaakt", "Ging vlotjes");
        blog.addAll(Arrays.asList(e1, e2, e3));
    }

    private void addToBlog(Entry entry) {
        if (blog == null) {
            blog = new ArrayList<>();
        }

        blog.add(entry);
    }
}


