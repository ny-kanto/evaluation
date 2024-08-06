package race.team.race.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import race.team.race.entity.Coureur;
import race.team.race.entity.DetailCoureurEtape;
import race.team.race.entity.Equipe;
import race.team.race.entity.Etape;
import race.team.race.exception.MyException;
import race.team.race.service.CoureurService;
import race.team.race.service.DetailCoureurEtapeService;
import race.team.race.service.EquipeService;
import race.team.race.service.EtapeService;
import race.team.race.utils.IdGenerator;

@Controller
public class EquipeController {
    @Autowired
    private EquipeService es;

    @Autowired
    private EtapeService ets;

    @Autowired
    private CoureurService cs;

    @Autowired
    private DetailCoureurEtapeService dces;

    @Autowired
    private IdGenerator idGenerator;

    @GetMapping("/user/signin")
    public String getUserFormLogin() {
        return "user/auth/signin";
    }

    @GetMapping("/user/signup")
    public String getUserFormSignup() {
        return "user/auth/signup";
    }

    @PostMapping("/user/checkLogin")
    public String checkLogin(@RequestParam("email") String email, @RequestParam("mdp") String mdp,
            HttpServletRequest request) {
        Equipe equipe = es.getEquipe(email, mdp);
        if (equipe == null) {
            return "redirect:/user/signin";
        } else {
            HttpSession session = request.getSession();
            session.setAttribute("id_user", equipe.getId());
            System.out.println("id user " + session.getAttribute("id_user"));
            return "redirect:/user/equipe-etape/list";
        }
    }

    @PostMapping("/user/save")
    public String saveUser(@RequestParam("nom") String nom, @RequestParam("email") String email,
            @RequestParam("mdp") String mdp,
            HttpServletRequest request) {
        Equipe equipe = new Equipe();
        equipe.setId(idGenerator);
        equipe.setNom(nom);
        equipe.setEmail(email);
        equipe.setMdp(mdp);
        es.insererEquipe(equipe);

        HttpSession session = request.getSession();
        session.setAttribute("id_user", equipe.getId());
        System.out.println("id user " + session.getAttribute("id_user"));
        return "redirect:/user/etape/list";
    }

    @GetMapping("/user/logout")
    public String checkLogout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.invalidate();
        return "redirect:/user/signin";
    }

    @GetMapping("/user/equipe-etape/list")
    public String getEquipeEtape(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        if (session.getAttribute("id_user") == null) {
            return "redirect:/user/signin";
        }

        String idEquipe = (String) session.getAttribute("id_user");
        List<DetailCoureurEtape> dce = dces.getListDetailByEquipe(idEquipe);
        List<Etape> etape = ets.getEtapes();
        Equipe e = es.getEquipeById(idEquipe);

        // Récupérer le message d'exception de la session
        String exceptionMessage = (String) session.getAttribute("exceptionMessage");
        String etapeErreur = (String) session.getAttribute("etapeErreur");
        if (exceptionMessage != null && etapeErreur != null) {
            model.addAttribute("exceptionMessage", exceptionMessage);
            model.addAttribute("etapeErreur", etapeErreur);

            System.out.println("etape erreur : " + etapeErreur);
            session.removeAttribute("exceptionMessage");
            session.removeAttribute("etapeErreur");
        }

        model.addAttribute("list_detail_equipe", dce);
        model.addAttribute("equipe", e);
        model.addAttribute("etape", etape);

        return "user/equipe/list-etape";
    }

    @GetMapping("/user/equipe-etape/form")
    public String formEquipeEtape(HttpServletRequest request, @RequestParam("id_etape") String idEtape, Model model) {
        HttpSession session = request.getSession();
        if (session.getAttribute("id_user") == null) {
            return "redirect:/user/signin";
        }

        String idEquipe = (String) session.getAttribute("id_user");
        Etape etape = ets.getEtapeById(idEtape);
        // Equipe e = es.getEquipeById(idEquipe);
        List<Coureur> coureur = cs.getCoureurByEquipeNotInEtape(idEquipe, idEtape);

        // Récupérer le message d'exception de la session
        String exceptionMessageNombre = (String) session.getAttribute("exceptionMessageNombre");
        if (exceptionMessageNombre != null) {
            model.addAttribute("exceptionMessageNombre", exceptionMessageNombre);

            session.removeAttribute("exceptionMessageNombre");
            session.removeAttribute("etapeErreur");
        }

        try {
            etape.verifNbCoureur(dces.countDetail(idEtape, idEquipe));
        } catch (MyException e1) {
            session.setAttribute("exceptionMessage", e1.getMessage());
            session.setAttribute("etapeErreur", idEtape);
            return "redirect:/user/equipe-etape/list";
        }

        // model.addAttribute("equipe", e);
        model.addAttribute("etape", etape);
        model.addAttribute("coureur", coureur);

        return "user/equipe/form";
    }

    @PostMapping("/user/equipe-etape/save")
    public String saveEquipeEtape(HttpServletRequest request, @RequestParam("coureur") String[] idCoureur,
            @RequestParam("etape") String idEtape, Model model) {
        HttpSession session = request.getSession();
        if (session.getAttribute("id_user") == null) {
            return "redirect:/user/signin";
        }

        Etape etape = ets.getEtapeById(idEtape);

        DetailCoureurEtape dce = new DetailCoureurEtape();

        dce.setEtape(etape);
        Coureur coureur;

        String idEquipe = (String) session.getAttribute("id_user");
        try {
            etape.verifNbCoureurInsert(dces.countDetail(idEtape, idEquipe), idCoureur.length);
        } catch (MyException e1) {
            session.setAttribute("exceptionMessageNombre", e1.getMessage());
            return "redirect:/user/equipe-etape/form?id_etape=" + idEtape;
        }

        for (int i = 0; i < idCoureur.length; i++) {
            coureur = cs.getCoureurById(idCoureur[i]);
            dce.setCoureur(coureur);

            dce.setId(idGenerator);

            dces.insererDetailCoureurEtape(dce);
        }

        return "redirect:/user/equipe-etape/list";
    }
}
