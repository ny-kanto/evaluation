package race.team.race.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import race.team.race.service.BaseService;


@Controller
public class BaseController {
    @Autowired
    private BaseService baseService;

    @GetMapping("/base/init")
    public String initBase(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session.getAttribute("id_admin") == null) {
            return "redirect:/admin/signin";
        }
        baseService.initBase();
        return "redirect:/admin/etape/list";
    }
}
