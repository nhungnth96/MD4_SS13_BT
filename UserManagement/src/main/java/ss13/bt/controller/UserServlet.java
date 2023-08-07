package ss13.bt.controller;

import ss13.bt.model.User;
import ss13.bt.service.UserService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "UserServlet", value = "/users")
public class UserServlet extends HttpServlet {
    UserService userService;

    @Override
    public void init() throws ServletException {
        userService = new UserService();
    }

    public void showAll(List<User> userList, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("userList", userList);
        request.getRequestDispatcher("/user/list.jsp").forward(request, response);
    }
    public void showView(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException, ClassNotFoundException {
        int id = Integer.parseInt(request.getParameter("id"));
        User user = userService.findByID(id);
        request.setAttribute("user",user);
        request.getRequestDispatcher("/user/view.jsp").forward(request, response);
    };
    public void showCreate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/user/create.jsp").forward(request, response);
    };
    public void create(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ClassNotFoundException {
        int id = 0;
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String country = request.getParameter("country");
        User user = new User(id,name,email,country);
        userService.save(user);
    };
    public void showEdit(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException, ClassNotFoundException {
        int id = Integer.parseInt(request.getParameter("id"));
        User user = userService.findByID(id);
        request.setAttribute("user",user);
        request.getRequestDispatcher("/user/edit.jsp").forward(request, response);
    };
    public void update(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ClassNotFoundException {
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String country = request.getParameter("country");
        User user = new User(id,name,email,country);
        userService.save(user);
    };
    public void showDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException, ClassNotFoundException {
        int id = Integer.parseInt(request.getParameter("id"));
        User user = userService.findByID(id);
        request.setAttribute("user",user);
        request.getRequestDispatcher("/user/delete.jsp").forward(request, response);
    };
    public void searchUserByCountry(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException, ClassNotFoundException {
        String keyword = request.getParameter("search");
        request.setAttribute("searchName",keyword);
        List<User> searchList = userService.searchByCountry(keyword);
        showAll(searchList,request,response);
    };
    public void sortByName(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException, ClassNotFoundException {
        String selectSort = request.getParameter("selectSort");
        List<User> sortList = userService.sortByName(selectSort);
        showAll(sortList,request,response);
    };


    public void delete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException, ClassNotFoundException {
        int id = Integer.parseInt(request.getParameter("id"));
        userService.delete(id);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        if(action==null){
            action = "";
        } else {
            try {
                switch (action){
                    case "ALL":
                        break;
                    case "VIEW":
                        showView(request, response);
                        break;
                    case "CREATE":
                        showCreate(request,response);
                        break;
                    case "SEARCH":
                        searchUserByCountry(request, response);
                        break;
                    case "SORT":
                        sortByName(request,response);
                    case "EDIT":
                        showEdit(request, response);
                        break;
                    case "DELETE":
                       showDelete(request, response);
                        break;
                    default:
                       break;
            }
                showAll(userService.getAll(),request,response);
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        if(action==null){
            action = "";
        } else {
            try {
                switch (action){
                    case "CREATE":
                        create(request, response);
                        break;
                    case "UPDATE":
                        update(request, response);
                        break;
                    case "DELETE":
                        delete(request, response);
                        break;
                    default:
                        break;
                }
                showAll(userService.getAll(),request,response);
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
}