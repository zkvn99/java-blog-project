package iducs.javaweb.blog201912012.controller;

import iducs.javaweb.blog201912012.model.Member;
import iducs.javaweb.blog201912012.repository.MemberDAO;
import iducs.javaweb.blog201912012.repository.MemberDAOImpl;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "MemberController", urlPatterns = { "/members/detail.do", "/members/login-form.do", "/members/login.do", "/members/post-form.do",
        "/members/create.do", "/members/logout.do", "/members/list.do", "/members/update-form.do", "/members/update.do", "/members/delete.do" }) //urlPatterns : 다수의 url 을 기술할 수 있다.
public class MemberController extends HttpServlet {
    MemberDAO memberDAOImpl = new MemberDAOImpl();
    protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8"); // request 객체의 인코딩을 설정, ISO-8859-1 기본
        HttpSession session = request.getSession(); // 세션 객체를 가져옮

        String uri = request.getRequestURI();
        String action = uri.substring(uri.lastIndexOf('/') + 1);
        if(action.equals("members")) { // member listing
            List<Member> memberList = new ArrayList<Member>();
            if((memberList = memberDAOImpl.readList()) != null) {
                request.setAttribute("members", memberList);
                request.getRequestDispatcher("result.jsp").forward(request, response); //정상
            }
            else
                request.getRequestDispatcher("error.jsp").forward(request, response);
            // response.sendRedirect("error.jsp");             // 비정상
            request.setAttribute("list", memberList);
        }
        else if(action.equals("create.do")) { // member creating
            int ret = 0;
            Member m = new Member();
            m.setEmail(request.getParameter("email"));
            m.setPw(request.getParameter("pw"));
            m.setName(request.getParameter("name"));
            m.setPhone(request.getParameter("phone"));
            m.setAddress(request.getParameter("address"));

            if((ret = memberDAOImpl.create(m)) > 0) {
                request.getRequestDispatcher("../status/success.jsp").forward(request, response);
            }
            else
                request.getRequestDispatcher("../status/fail.jsp").forward(request, response);
        }
        else if(action.equals("detail.do")) {
            String email = request.getParameter("email");
            //String pw = request.getParameter("pw");
            Member member = new Member();
            member.setEmail(email);
            //member.setPw(pw);
            Member retMember = null;
            if((retMember = memberDAOImpl.read(member)) != null) {
                request.setAttribute("member", retMember); // DB로 부터 가져온 정보를 Member 객체로 반환 후 속성으로 설정
                request.getRequestDispatcher("../members/member-detail-form.jsp").forward(request, response);
            } else {
                request.getRequestDispatcher("../status/fail.jsp").forward(request, response);
            }

        }
        else if(action.equals("login.do")) {
            String email = request.getParameter("email");
            String pw = request.getParameter("pw");
            Member member = new Member();
            member.setEmail(email);
            member.setPw(pw);
            Member retMember = null;
            if((retMember = memberDAOImpl.login(member)) != null) {
                session.setAttribute("logined", retMember.getEmail()); // 로그인 상태 유지
                session.setAttribute("name", retMember.getName()); // 로그인 상태 유지
                request.setAttribute("message", retMember.getName() + "님 환영합니다."); // 출력 정보를 전달
                request.getRequestDispatcher("../status/success.jsp").forward(request, response);
            } else {
                request.getRequestDispatcher("../status/fail.jsp").forward(request, response);
            }
        }
        else if(action.equals("logout.do")) {
            session.invalidate(); // session 객체에 저장된 모든 속성들을 무효화됨
            response.sendRedirect("../main/index.jsp"); // request, response 객체를 전달하지 않고 페이지 이동
        }
        else if(action.equals("update.do")){
            int ret = 0;
            Member m = new Member();
            m.setEmail(request.getParameter("email"));
            m.setName(request.getParameter("name"));
            m.setPhone(request.getParameter("phone"));
            m.setAddress(request.getParameter("address"));

            if((ret = memberDAOImpl.update(m)) > 0){
                request.setAttribute("member", m);
                request.getRequestDispatcher("../members/member-detail-form.jsp").forward(request, response);
            } else {
                request.setAttribute("message", "회원정보 수정에 실패");
                request.getRequestDispatcher("../status/fail.jsp").forward(request, response);
            }
        }
        else if(action.equals("update-form.do")){
            Member member = new Member();
            member.setEmail(request.getParameter("email"));
            Member retMember = null;
            if((retMember = memberDAOImpl.read(member)) != null) {
                request.setAttribute("member", retMember); //email로 조회한 회원 정보 객체를 member라는 키로 request에 attribute로 저장
                // ${requestScope.member}
                request.getRequestDispatcher("../members/member-update-form.jsp").forward(request, response);
            } else {
                request.setAttribute("errMsg", "정보 조회를");
                request.getRequestDispatcher("../status/fail.jsp").forward(request, response); // 오류
            }
        }
        else if(action.equals("delete.do")){
            int ret = 0;
            Member m = new Member();
            m.setEmail(request.getParameter("email"));
            m.setPw(request.getParameter("pw"));

            if((ret = memberDAOImpl.delete(m)) > 0){
                session.invalidate();
                request.getRequestDispatcher("../main/index.jsp").forward(request, response);
            } else {
                request.setAttribute("message", "회원 탈퇴 실패");
                request.getRequestDispatcher("../status/fail.jsp").forward(request, response);
            }

        }
        else if(action.equals("list.do")){
            List<Member> memberList = new ArrayList<Member>();
            if((memberList = memberDAOImpl.readList()) != null) {
                request.setAttribute("members", memberList);
                request.getRequestDispatcher("../members/member-list-view.jsp").forward(request, response);
            }
            else {
                request.setAttribute("message", "회원 목록 조회 실패");
                request.getRequestDispatcher("../status/fail.jsp").forward(request, response);
            }
            request.setAttribute("list", memberList);
        }
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doProcess(request, response);

    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doProcess(request, response);

    }
}
