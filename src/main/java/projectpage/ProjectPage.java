package projectpage;

import bugs.SelectAllBugsProject;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ProjectPage {
    private static final Logger log = Logger.getLogger(ProjectPage.class);
    private String nameProject;
    private String keyProject;
    private String leaderName;

    private Connect connect = null;
    private Statement statement = null;
    private ResultSet resultSet = null;

    public ProjectPage(HttpServletRequest req, HttpServletResponse resp) {
        try {
            selectInfoProjects(req, resp);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void selectInfoProjects(HttpServletRequest req, HttpServletResponse resp) throws SQLException, ClassNotFoundException {
        String selectedNameProject = req.getParameter("nameproject");

        int idUserLead = 0;

        String querySelectInfoProject = "SELECT id_user_lead, name, key_name FROM projects WHERE name = '" + selectedNameProject + "'";

        try {
            connect = new Connect();
            statement = connect.getConnection().createStatement();
            resultSet = statement.executeQuery(querySelectInfoProject);
            log.info("Query: " + querySelectInfoProject);
            while (resultSet.next()) {
                idUserLead = resultSet.getInt(1);
                setNameProject(resultSet.getString(2));
                setKeyProject(resultSet.getString(3));
            }
            resultSet.close();
            String querySelectNameLead = "SELECT firstname, lastname FROM users WHERE id = " + idUserLead;
            resultSet = statement.executeQuery(querySelectNameLead);
            while (resultSet.next())
                setLeaderName(resultSet.getString(1) + " " + resultSet.getString(2));

        } finally {
            assert resultSet != null;
            resultSet.close();
            statement.close();
            connect.close();
        }
    }

    public String getNameProject() {
        return nameProject;
    }

    public void setNameProject(String nameProject) {
        this.nameProject = nameProject;
    }

    public String getKeyProject() {
        return keyProject;
    }

    public void setKeyProject(String keyProject) {
        this.keyProject = keyProject;
    }

    public String getLeaderName() {
        return leaderName;
    }

    public void setLeaderName(String leaderName) {
        this.leaderName = leaderName;
    }


}
