package iducs.javaweb.blog201912012.controller;

import iducs.javaweb.blog201912012.model.Blog;
import iducs.javaweb.blog201912012.model.Member;
import iducs.javaweb.blog201912012.repository.BlogDAO;
import iducs.javaweb.blog201912012.repository.BlogDAOImpl;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "BlogController", urlPatterns = { "/blogs/test.do", "/blogs/post-form.do", "/blogs/post.do",
        "/blogs/list.do", "/blogs/detail.do",
        "/blogs/updateForm.do", "/blogs/update.do","/blogs/delete.do"})

        public class BlogController extends HttpServlet {

    BlogDAOImpl repository = null;
    Blog blog = null;
    List<Blog> blogList = null;

    private static final String SAVE_DIR = "files";
    private String partName = null;
    private String partValue = null;


    protected void doService(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession(); // 세션 객체를 가져옮
        String uri = request.getRequestURI();
        String contextPath = request.getContextPath();
        String command = uri.substring(contextPath.length() + 1); // blogs/post.do, blog/list.do가 반환됨
        String action = command.substring(command.lastIndexOf("/") + 1); // post.do, list.do 반환

        BlogDAOImpl dao = new BlogDAOImpl();

        if (action.equals("post-form.do")) {

            Member member = new Member();
            member.setEmail((String) session.getAttribute("logined"));
            member.setName((String) session.getAttribute("name"));

            request.setAttribute("loginedEmail", member.getEmail()); //email로 조회한 회원 정보 객체를 member라는 키로 request에 attribute로 저장
            request.setAttribute("loginedName", member.getName());

            request.getRequestDispatcher("../blogs/blog-post-form.jsp").forward(request, response);

        }
        else if (action.equals("post.do")) {
            Blog blog = new Blog();
            blog.setName(request.getParameter("name"));
            blog.setEmail(request.getParameter("email"));
            blog.setTitle(request.getParameter("title"));
            blog.setContent(request.getParameter("content"));
            //데이터베이스 처리 요청 또는 서비스 요청 코드 추가
            if (dao.create(blog) > 0) {
                request.setAttribute("blog", blog);
                //처리 결과를 view에 전달
                request.setAttribute("message", "블로그 작성 ");
                request.getRequestDispatcher("../status/success.jsp").forward(request, response);
            } else {
                request.getRequestDispatcher("../status/error.jsp").forward(request, response);
            }
        }
        else if(action.equals("list.do") && request.getParameter("id") != null) {
            Blog ret = new Blog();
            Blog blog = new Blog();
            blog.setId(Long.parseLong(request.getParameter("id")));

            if((ret = dao.read(blog)) != null) {
                request.setAttribute("blog", ret);
                request.getRequestDispatcher("../blogs/detail-form.jsp").forward(request, response);
            }
            else {
                request.setAttribute("message", "블로그 조회 실패");
                request.getRequestDispatcher("../status/fail.jsp").forward(request, response);
            }
        }
        else if (action.equals("list.do")) {
            List<Blog> blogList = new ArrayList<Blog>();
            if ((blogList = dao.readList()) != null) {
                request.setAttribute("blogs", blogList);
                request.getRequestDispatcher("../blogs/list-view.jsp").forward(request, response);
            } else {
                request.setAttribute("message", "블로그 목록 가져오기 오류 - 불편을 드려 죄송합니다.");
                request.getRequestDispatcher("../errors/message.jsp").forward(request, response);
            }
        }
        else if (action.equals("read.do")) {
            Blog retBlog = null;
            blog = new Blog();
            blog.setId(Integer.parseInt(request.getParameter("id")));
            if ((retBlog = repository.read(blog)) != null) {
                request.setAttribute("blog", retBlog);
                request.getRequestDispatcher("read-view.jsp").forward(request, response);
            } else {
                request.setAttribute("message", "블로그 읽기 오류 - 불편을 드려 죄송합니다.");
                request.getRequestDispatcher("../error/message.jsp").forward(request, response);
            }
        }
        else if(action.equals("update.do")){
            int ret = 0;
            Blog b = new Blog();
            b.setId(Long.parseLong(request.getParameter("id")));
            b.setName(request.getParameter("name"));
            b.setEmail(request.getParameter("email"));
            b.setTitle(request.getParameter("title"));
            b.setContent(request.getParameter("content"));

            if((ret = dao.update(b)) > 0){
                request.setAttribute("blog", b);
                request.getRequestDispatcher("../blogs/detail-form.jsp").forward(request, response);
            } else {
                request.setAttribute("message", "회원정보 수정에 실패");
                request.getRequestDispatcher("../status/fail.jsp").forward(request, response);
            }
        }
        else if(action.equals("delete.do")){
            int ret = 0;
            Blog b = new Blog();
            b.setId(Long.parseLong(request.getParameter("id")));

            if((ret = dao.delete(b)) > 0){
                session.invalidate();
                request.setAttribute("message", "blog delete success");
                request.getRequestDispatcher("../status/success.jsp").forward(request, response);
            } else {
                request.setAttribute("message", "회원 탈퇴 실패");
                request.getRequestDispatcher("../status/fail.jsp").forward(request, response);
            }

        }
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doService(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doService(request, response);
    }



    //MemberDAO memberDAOImpl = new MemberDAOImpl();
    /*protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
        else if(action.equals("member-create")) { // member creating
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
        else if(action.equals("member-detail")) {
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
        else if(action.equals("member-login")) {
            String email = request.getParameter("email");
            String pw = request.getParameter("pw");
            Member member = new Member();
            member.setEmail(email);
            member.setPw(pw);
            Member retMember = null;
            if((retMember = memberDAOImpl.login(member)) != null) {
                session.setAttribute("logined", retMember.getEmail()); // 로그인 상태 유지
                request.setAttribute("message", retMember.getName() + "님 환영합니다."); // 출력 정보를 전달
                request.getRequestDispatcher("../status/success.jsp").forward(request, response);
            } else {
                request.getRequestDispatcher("../status/fail.jsp").forward(request, response);
            }
        }
        else if(action.equals("member-logout")) {
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
        } else if(action.equals("delete.do")){
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

    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doProcess(request, response);

    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doProcess(request, response);

    }*/
}
