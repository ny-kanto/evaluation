package race.team.race.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import race.team.race.entity.Admin;
import race.team.race.service.AdminService;

@Controller
public class AdminController {
    @Autowired
    private AdminService adminService;

    @GetMapping("/admin/signin")
    public String getFormLogin() {
        return "admin/auth/signin";
    }

    @GetMapping("/admin/signup")
    public String getFormSignup() {
        return "admin/auth/signup";
    }

    @PostMapping("/admin/checkLogin")
    public String checkLogin(@RequestParam("email") String email, @RequestParam("mdp") String mdp, HttpServletRequest request) {
        Admin admin = adminService.getAdmin(email, mdp);
        if (admin == null) {
            return "redirect:/admin/signin";
        } else {
            HttpSession session = request.getSession();
            session.setAttribute("id_admin", admin.getId());
            System.out.println("id admin " + session.getAttribute("id_admin"));
            return "redirect:/admin/classement-equipe/list";
        }
    }

    @GetMapping("/admin/logout")
    public String checkLogout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.invalidate();
        return "redirect:/admin/signin";
    }

    @GetMapping("/admin/categorie/generate")
    public String generateCategory(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session.getAttribute("id_admin") == null) {
            return "redirect:/admin/signin";
        }
        adminService.generateCategory();
        return "redirect:/admin/etape/list";
    }
    
}
