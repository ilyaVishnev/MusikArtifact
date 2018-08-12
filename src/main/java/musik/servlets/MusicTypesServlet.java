package musik.servlets;

import musik.dao.MusicTypeDao;
import musik.models.MusicType;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Iterator;

/**
 * Created by Пользователь on 27.07.2018.
 */
public class MusicTypesServlet extends HttpServlet {
    private MusicTypeDao musicType = new MusicTypeDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final JSONArray typesArray = new JSONArray();
        JSONObject jsonSend = new JSONObject();
        Iterator<MusicType> iterator = musicType.getAll().iterator();
        while (iterator.hasNext()) {
            JSONObject jsonObj = new JSONObject();
            MusicType musicType = iterator.next();
            jsonObj.put("id", musicType.getId());
            jsonObj.put("type", musicType.getType());
            typesArray.add(jsonObj);
        }
        jsonSend.put("typesArray", typesArray);
        resp.getWriter().print(jsonSend);
    }
}
