package ss13.bt.service;

import ss13.bt.model.User;
import ss13.bt.util.ConnectDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class UserService implements IGenericService<User,Integer>{
    // ====== QUERY SQL ======
    private final String GET_ALL = "select * from demojdbc.users";
    private final String FIND_BY_ID = "select * from demojdbc.users where id = ?";
    private final String DELETE = "delete from demojdbc.users where id = ?";
    private final String INSERT = "insert into demojdbc.users(name,email,country) values (?,?,?)";
    private final String UPDATE = "update demojdbc.users set name=?,email=?,country=? where id=?";
    private final String SEARCH_BY_COUNTRY = "select * from demojdbc.users where country like concat('%', ?, '%') or country like concat(?, '%')";
    private final String SORT_BY_NAME = "select * from demojdbc.users order by name";
    // ======= END SQL ======
    @Override
    public List<User> getAll() throws SQLException, ClassNotFoundException {
        List<User> userList = new ArrayList<>();
        Connection con = ConnectDB.openConnection();
        PreparedStatement preSt = con.prepareStatement(GET_ALL);
        ResultSet rs = preSt.executeQuery();
        while (rs.next()){
            User user = new User();
            user.setId(rs.getInt("id"));
            user.setName(rs.getString("name"));
            user.setEmail(rs.getString("email"));
            user.setCountry(rs.getString("country"));
            userList.add(user);
        }
        ConnectDB.closeConnection(con);
        return userList;
    }

    @Override
    public User findByID(Integer id) throws SQLException, ClassNotFoundException {
        User user = null;
        Connection con = ConnectDB.openConnection();
        PreparedStatement preSt = con.prepareStatement(FIND_BY_ID);
        preSt.setInt(1,id);
        ResultSet rs = preSt.executeQuery();
        while (rs.next()){
            user = new User();
            user.setId(rs.getInt("id"));
            user.setName(rs.getString("name"));
            user.setEmail(rs.getString("email"));
            user.setCountry(rs.getString("country"));
        }
        ConnectDB.closeConnection(con);
        return user;
    }

    @Override
    public void save(User user) throws SQLException, ClassNotFoundException {
        Connection con = ConnectDB.openConnection();
        PreparedStatement preSt;
        // id trong DB luôn bắt đầu từ 1
        // nếu id = 0 -> mặc định thêm mới, id tự tăng theo DB
        // -> ADD
        if(user.getId()==0){
            preSt = con.prepareStatement(INSERT);
            preSt.setString(1, user.getName());
            preSt.setString(2, user.getEmail());
            preSt.setString(3, user.getCountry());
            preSt.executeUpdate();
        }
        else
        // UPDATE
        {
            preSt = con.prepareStatement(UPDATE);
            preSt.setString(1, user.getName());
            preSt.setString(2, user.getEmail());
            preSt.setString(3, user.getCountry());
            preSt.setInt(4, user.getId());
            preSt.executeUpdate();
        }
        ConnectDB.closeConnection(con);
    }

    @Override
    public void delete(Integer id) throws SQLException, ClassNotFoundException {
        Connection con = ConnectDB.openConnection();
        PreparedStatement preSt = con.prepareStatement(DELETE);
        preSt.setInt(1,id);
        preSt.executeUpdate();
        ConnectDB.closeConnection(con);
    }
    public List<User> searchByCountry(String country) throws SQLException, ClassNotFoundException {
        List<User> searchList = new ArrayList<>();
        Connection con = ConnectDB.openConnection();
        PreparedStatement preSt = con.prepareStatement(SEARCH_BY_COUNTRY);
        preSt.setString(1,country);
        preSt.setString(2,country);
        ResultSet rs = preSt.executeQuery();
        while (rs.next()){
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setCountry(rs.getString("country"));
                searchList.add(user);
        }
        ConnectDB.closeConnection(con);
        return searchList;
    }
    public List<User> sortByName(String selectSort) throws SQLException, ClassNotFoundException {
        List<User> sortList = new ArrayList<>();
        Connection con = ConnectDB.openConnection();
        PreparedStatement preSt = con.prepareStatement(SORT_BY_NAME);
        ResultSet rs = preSt.executeQuery();
                while (rs.next()) {
                    User user = new User();
                    user.setId(rs.getInt("id"));
                    user.setName(rs.getString("name"));
                    user.setEmail(rs.getString("email"));
                    user.setCountry(rs.getString("country"));
                    sortList.add(user);
                }
           if (selectSort.equals("DESC")){
               sortList.sort(Comparator.comparing(User::getName).reversed());
           }
        ConnectDB.closeConnection(con);
        return sortList;
    }
}
