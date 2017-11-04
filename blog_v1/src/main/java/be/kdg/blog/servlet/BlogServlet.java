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
public class BlogServlet extends HttpServlet {

    /*
    De gegevens van de blog dienen in de servlet context opgeslagen te worden. Zorg ervoor
    dat bij het aanmaken van de servlet de blog wordt opgevuld met een drietal test-entries

    Deze “blog” wordt bijgehouden in memory en elke entry die je hebt toegevoegd zal na het
    herstarten van de server verdwenen zijn.
     */
    private ArrayList<Entry> blog; //als een atribuut van de servlet te maken wordt het application scope
    // (stopt je de app dan worden de blog items verwijderd)

    /*
    DEEL1:
        GET op /blog​:
            Je servlet stuurt HTML terug naar de client. In de HTML pagina vinden
            we een lijstje terug van alle entries in de blog
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if (blog == null) {
            this.vulBlog();
        }

        resp.setContentType("text/html");
        resp.getWriter().print("<html><head><title>Blog</title></head><body><h1>Mijn Blog</h1>");

        for (Entry entry :
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

    /*
    DEEL1:
        POST op /blog​:
            - Werk met twee request parameters:
                subject en message.
            - Voeg een entry toe aan de blog met de gegeven subject en message.
            - Na het toevoegen van de nieuwe entry moet er opnieuw een lijstje van alle entries getoond worden. (deze code heb je al geschreven!)
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Entry entry = new Entry(req.getParameter("onderwerp"), req.getParameter("bericht"));

        addToBlog(entry);

        this.doGet(req, resp);
    }

    //vult de blog met entries
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


