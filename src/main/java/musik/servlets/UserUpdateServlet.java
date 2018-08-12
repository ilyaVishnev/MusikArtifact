package musik.servlets;

import musik.dao.RoleDao;
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

public class UserUpdateServlet extends HttpServlet {

    private UserDao userDao = new UserDao();
    private RoleDao roleDao = new RoleDao();

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("text/html;charset=Windows-1251");
        User user = userDao.getUserById(Integer.parseInt(req.getParameter("id")));
        req.setAttribute("user", user);
        User observer = (User) req.getSession().getAttribute("myuser");
        req.setAttribute("myuser", roleDao.getRoleById(observer.getId_role()).getRole());
        req.setAttribute("role", roleDao.getRoleById(user.getId_role()).getRole());
        int[] arrayOftypes=new int [user.getId_musicType().toArray().length];
        int index=0;
        for (Object object:user.getId_musicType().toArray()){
            arrayOftypes[index++]=(Integer)object;
        }
        req.setAttribute("arrayOftypes", arrayOftypes);
        req.getRequestDispatcher("/WEB-INF/views/updateUser.jsp").forward(req, res);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        User user = userDao.getUserById(Integer.parseInt(req.getParameter("id")));
        user.setName(req.getParameter("name"));
        user.setId_role(Integer.parseInt(req.getParameter("role")));
        user.setLogin(req.getParameter("login"));
        user.setPassword(req.getParameter("password"));
        Address address = new Address();
        address.setAddress(req.getParameter("adress"));
        user.setAddress(address);
        Integer[]arrayIdTypes= new Integer[req.getParameterValues("types").length];
        int index=0;
        for(String type:req.getParameterValues("types")){
            arrayIdTypes[index++]=Integer.parseInt(type);
        }
        user.setId_musicType(Arrays.asList(arrayIdTypes));
        userDao.edit(user);
        doGet(req, res);
    }
}