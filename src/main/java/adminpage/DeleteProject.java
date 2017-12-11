package adminpage;

import database.Connect;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Statement;

@WebServlet(urlPatterns = "/deleteproject")
public class DeleteProject extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        String keyId = req.getParameter("id");

        String id = keyId.substring(4);

        System.out.println(id);

        try {
            Connect connect = new Connect();
            Statement statement = connect.getConnection().createStatement();
            String queryDeleteProject = "DELETE FROM projects WHERE id = " + Integer.parseInt(id);
            String queryDeleteUserFromProject = "DELETE FROM user_project WHERE id_project = " + Integer.parseInt(id);
            String queryDeleteBugs = "DELETE FROM bugs WHERE id_project = " + Integer.parseInt(id);
            statement.execute(queryDeleteBugs);
            statement.execute(queryDeleteUserFromProject);
            statement.execute(queryDeleteProject);

            resp.sendRedirect("/adminpage.jsp");

        } catch (SQLException | ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
    }
}
