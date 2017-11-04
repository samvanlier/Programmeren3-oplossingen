/*
source of info : http://adndevblog.typepad.com/cloud_and_mobile/2015/08/become-a-java-ee-developer-part-ii-basic-restful-api-from-a-servlet.html
laatst bekenen op 21/09/2017
 */

package be.kdg.blog.servlet;


import be.kdg.blog.domain.Blog;
import be.kdg.blog.domain.Entry;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

//DEEL 2
@WebServlet("/api/blog/posts/*")
public class BlogApiServlet extends HttpServlet {

    private final Gson gson = new Gson();
    private Blog blog;

    //helper methode
    private void sendAsJson(
            HttpServletResponse resp,
            Object obj
    ) throws IOException {
        resp.setContentType("application/json");

        String res = gson.toJson(obj);

        resp.getWriter().write(res);
    }

    /*
    get methode: stuurt data terug in JSON
     */
    // GET/api/blog/posts/id
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        blog = (Blog) this.getServletContext().getAttribute("blog");

        String pathInfo = req.getPathInfo();


        if (pathInfo == null || pathInfo.equals("/")) {
            //send all entries and end
            sendAsJson(resp, blog);
            return;
        }

        String[] splits = pathInfo.split("/");

        if (splits.length != 2) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST); //stuurt error
            return;
        }

        //controle doen of entry bestaat
        int id = Integer.parseInt(splits[1]);
        if (blog.getEntry(id) == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        //stuur entry
        sendAsJson(resp, blog.getEntry(id));
    }

    // POST/api/blog/posts
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        blog = (Blog) this.getServletContext().getAttribute("blog");

        if (pathInfo == null || pathInfo.equals("/")) {
            StringBuilder buffer = new StringBuilder();
            BufferedReader reader = req.getReader();

            String line;

            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }

            String data = buffer.toString();

            Entry entry = gson.fromJson(data, Entry.class);

            blog.addEntry(entry.getSubject(), entry.getMessage());

            sendAsJson(resp, entry);
        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            //return;
        }
    }
}
