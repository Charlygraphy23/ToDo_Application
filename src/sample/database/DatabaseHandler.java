package sample.database;

import sample.controller.CellController;
import sample.controller.LoginController;
import sample.model.Task;
import sample.model.User;
import java.sql.*;

public class DatabaseHandler {
    private Connection connection;
    private PreparedStatement preparedStatement;

    public Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        connection= DriverManager.getConnection("jdbc:mysql://localhost:3306/todoo","root","root");
        return connection;
    }

    public void setUserData(User user) throws SQLException, ClassNotFoundException {

        preparedStatement=getConnection().prepareStatement("INSERT INTO users(firstname,lastname,gender,username,password,mail,mobileno,image,lastupdate) VALUES (?,?,?,?,?,?,?,?,?)");
        preparedStatement.setString(1,user.getFirstName());
        preparedStatement.setString(2,user.getLastName());
        preparedStatement.setString(3,user.getGender());
        preparedStatement.setString(4,user.getUserName());
        preparedStatement.setString(5,user.getPassword());
        preparedStatement.setString(6,user.getMail());
        preparedStatement.setString(7,user.getMobile_no());
        preparedStatement.setBinaryStream(8,user.getImage());
        preparedStatement.setTimestamp(9,user.getTimestamp());
        preparedStatement.executeUpdate();
    }

    public ResultSet getUserdata(User user) throws SQLException, ClassNotFoundException {
        ResultSet resultSet=null;
        preparedStatement=getConnection().prepareStatement("SELECT * FROM users WHERE username=? AND password=?");
        preparedStatement.setString(1,user.getUserName());
        preparedStatement.setString(2,user.getPassword());
        resultSet=preparedStatement.executeQuery();
        return resultSet;
    }

    public ResultSet getUserdetailsPage() throws SQLException, ClassNotFoundException {
        ResultSet resultSet=null;
        preparedStatement=getConnection().prepareStatement("SELECT * FROM users WHERE id=?");
        preparedStatement.setInt(1,LoginController.id);
        resultSet=preparedStatement.executeQuery();
        return resultSet;
    }

    public void addTask(Task task) throws SQLException, ClassNotFoundException {
        preparedStatement=getConnection().prepareStatement("INSERT INTO tasks(id,task,description,datecreated) VALUES (?,?,?,?)");
        preparedStatement.setInt(1,LoginController.id);
        preparedStatement.setString(2,task.getTask());
        preparedStatement.setString(3,task.getDescription());
        preparedStatement.setTimestamp(4,task.getDatecreated());
        preparedStatement.executeUpdate();

    }

    public ResultSet getTask() throws SQLException, ClassNotFoundException {
        ResultSet resultSet=null;
        preparedStatement=getConnection().prepareStatement("SELECT * FROM tasks WHERE id=?");
        preparedStatement.setInt(1,LoginController.id);
        resultSet=preparedStatement.executeQuery();
        return resultSet;
    }

    public ResultSet getCountTasks() throws SQLException, ClassNotFoundException {
        ResultSet resultSet=null;
        preparedStatement=getConnection().prepareStatement("SELECT COUNT(*) FROM tasks WHERE id=?");
        preparedStatement.setInt(1,LoginController.id);
        resultSet=preparedStatement.executeQuery();
        return resultSet;
    }

    public void deleteTask(Task task) throws SQLException, ClassNotFoundException {
        preparedStatement=getConnection().prepareStatement("DELETE FROM tasks WHERE taskid=? AND id=?");
        preparedStatement.setInt(1,task.getTaskid());
        preparedStatement.setInt(2,LoginController.id);
        preparedStatement.executeUpdate();
    }

    public void updateTask(Task task) throws SQLException, ClassNotFoundException {
        preparedStatement=getConnection().prepareStatement("UPDATE tasks SET task=?,description=?,datecreated=? WHERE taskid=? AND id=?");
        preparedStatement.setString(1,task.getTask());
        preparedStatement.setString(2,task.getDescription());
        preparedStatement.setTimestamp(3,task.getDatecreated());
        preparedStatement.setInt(4,task.getTaskid());
        preparedStatement.setInt(5,LoginController.id);
        preparedStatement.executeUpdate();
    }

    public ResultSet getTaskbyId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet=null;
        preparedStatement=getConnection().prepareStatement("SELECT * FROM tasks WHERE id=? AND taskid=?");
        preparedStatement.setInt(1,LoginController.id);
        preparedStatement.setInt(2, CellController.tskId);
        resultSet=preparedStatement.executeQuery();
        return resultSet;
    }

}
