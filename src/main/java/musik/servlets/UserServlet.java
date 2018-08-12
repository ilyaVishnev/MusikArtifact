package musik.servlets;

import musik.dao.DBStore;
import musik.dao.RoleDao;
import musik.dao.UserDao;
import musik.models.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class UserServlet extends HttpServlet {

    private UserDao userDao = new UserDao();
    private RoleDao roleDao = new RoleDao();

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("text/html;charset=Windows-1251");
        HttpSession session = req.getSession();
        req.setAttribute("myusers", userDao.getAll());
        req.setAttribute("myuser", req.getSession().getAttribute("myuser"));
        req.setAttribute("role", roleDao.getRoleById(((User) req.getSession().getAttribute("myuser")).getId_role()));
        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/listView.jsp");
        dispatcher.forward(req, res);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        String delete = req.getParameter("del");
        if (delete != null) {
            userDao.delete(userDao.getUserById(Integer.parseInt(req.getParameter("id"))));
        }
        res.sendRedirect(String.format("%s/users", req.getContextPath()));
    }
}
