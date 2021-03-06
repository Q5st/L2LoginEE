package academy.prog;

import jakarta.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

public class LoginServlet extends HttpServlet {

    static final String LOGIN = "admin";


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        String ageStr = request.getParameter("age");
        int ageInt = Integer.parseInt(ageStr);
        char [] passwordChar = password.toCharArray();


        if (LOGIN.equals(login) && passwordCheck(password) && ageInt>=18) {
            HttpSession session = request.getSession(true);
            session.setAttribute("user_login", login);
            response.sendRedirect("index.jsp");
        }else if (LOGIN.equals(login) && passwordCheck(password) && ageInt<18) {
            PrintWriter pw = response.getWriter();
            pw.println("<html><head><title>Age </title></head>");
            pw.println("<body><h1>You are under eighteen</h1></body></html>");
            pw.println("<br>Click this link to <a href=\"/login?a=exit\".> return </a></body></html>");
        }else {
            PrintWriter pw = response.getWriter();
            pw.println("<html><head><title>Wrong </title></head>");
            pw.println("<body><h1>Wrong login or password, password must contain: upper and lower case letters, digits and symbols !@#$%^&?_</h1>");
            pw.println("<br>Click this link to <a href=\"/login?a=exit\"> return </a></body></html>");
        }
    }

    private boolean passwordCheck(String psw){
        if(psw.length()>10 && psw.matches(".*\\d+.*") && psw.matches(".*[A-Z]+.*") && psw.matches(".*[a-z]+.*") && psw.matches(".*[!@#$%^&?_]+.*")){
            return true;
        }else {
            return false;
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String a = request.getParameter("a");
        HttpSession session = request.getSession(false);

        if ("exit".equals(a) && (session != null))
            session.removeAttribute("user_login");

        response.sendRedirect("index.jsp");
    }
}
