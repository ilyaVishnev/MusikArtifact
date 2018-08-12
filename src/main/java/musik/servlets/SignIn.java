package musik.servlets;

import musik.dao.DBStore;
import musik.dao.UserDao;
import musik.models.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class SignIn extends HttpServlet {
    private UserDao userDao = new UserDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        session.setAttribute("myuser", null);
        req.getRequestDispatcher("/WEB-INF/views/signInView.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        User user = userDao.isCredential(login, password);
        if (user == null) {
            req.setAttribute("error", "dont have such user");
            doGet(req, resp);
        } else {
            HttpSession session = req.getSession();
            session.setAttribute("myuser", user);
            resp.sendRedirect(String.format("%s/users", req.getContextPath()));
        }
    }
}