/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.web;

import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dao.*;
import com.model.*;

@WebServlet(name = "home", urlPatterns = {"/home"})
public class home extends HttpServlet {

    private BookDAO bookDAO;
    private CategoryDAO categoryDAO;
    private LanguageDAO languageDAO;
    private BookCoverDAO bookCoverDAO;
    private BookTypeDAO bookTypeDAO;
    private UsersDAO userDAO;

    public void init() {
        bookDAO = new BookDAO();
        categoryDAO = new CategoryDAO();
        languageDAO = new LanguageDAO();
        bookCoverDAO = new BookCoverDAO();
        bookTypeDAO = new BookTypeDAO();
        userDAO = new UsersDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
//        String path = request.getServletPath();
        String path = request.getParameter("action");
        if (path == null) {
            path = "";
        }
        System.out.println("servlet path = " + path);
        try {
            switch (path) {
                case ("book-detail"):
                    showBookDetail(request, response);
                    break;
//                case ("/insert"):
//                    insertUser(request, response);
//                    break;
//                case ("/delete"):
//                    deleteUser(request, response);
//                    break;
//                case ("/edit"):
//                    showEditForm(request, response);
//                    break;
//                case ("/update"):
//                    updateUser(request, response);
//                    break;
                default:
                    showHome(request, response);
                    break;
            }
        } catch (Exception ex) {
            System.out.println("catch exception page");
            System.out.println(ex);
            throw new ServletException(ex);
        }
    }

    public void showBookDetail(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String bookno = request.getParameter("id");
        if (bookno.equals("")) {
            bookno = "" + 0;
        }
        int id = Integer.parseInt(bookno);
        Books bookDetail = bookDAO.selectBook(id);
        Category category = categoryDAO.selectCategory(bookDetail.getCategory());
        Language language = languageDAO.selectLanguage(bookDetail.getLanguage());
        BookCover coverType = bookCoverDAO.selectBookCover(bookDetail.getCover_type());
        BookType bookType = bookTypeDAO.selectBookType(bookDetail.getType());
        Users vendor = userDAO.selectUser(bookDetail.getVendor_id());
        RequestDispatcher dispatcher = request.getRequestDispatcher("book-detail.jsp");
        request.setAttribute("book", bookDetail);
        request.setAttribute("category", category);
        request.setAttribute("language", language);
        request.setAttribute("coverType", coverType);
        request.setAttribute("bookType", bookType);
        request.setAttribute("vendor", vendor);
        dispatcher.forward(request, response);
    }

    public void showHome(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
//        System.out.println("Session id: -->"+request.getSession(false).getAttribute("user_type"));
        List<Books> booklist = bookDAO.selectAllBooks();
        List<Category> category = categoryDAO.selectAllCategory();
        RequestDispatcher dispatcher = request.getRequestDispatcher("home.jsp");
        request.setAttribute("booklist", booklist);
        request.setAttribute("category", category);
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
