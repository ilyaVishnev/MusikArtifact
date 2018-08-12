package musik.servlets;

import musik.dao.UserDao;
import musik.models.Address;
import musik.models.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

public class UserCreateServlet extends HttpServlet {
    private static Logger log = Logger.getLogger(UserCreateServlet.class.getName());
    private UserDao userDao = new UserDao();

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        res.setContentType("text/html");
        req.getRequestDispatcher("/WEB-INF/views/createUser.jsp").forward(req, res);

    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        User user = new User();
        user.setName(req.getParameter("name"));
        user.setId_role(Integer.parseInt(req.getParameter("role")));
        user.setLogin(req.getParameter("login"));
        user.setPassword(req.getParameter("password"));
        Address address = new Address();
        address.setAddress(req.getParameter("adress"));
        user.setAddress(address);
        Integer[] arrayIdTypes = new Integer[req.getParameterValues("types").length];
        int index = 0;
        for (String type : req.getParameterValues("types")) {
            arrayIdTypes[index++] = Integer.parseInt(type);
        }
        user.setId_musicType(Arrays.asList(arrayIdTypes));
        userDao.create(user);
        doGet(req, res);
    }
}
