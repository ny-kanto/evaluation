package race.team.race.controller;

import java.util.ArrayList;
import java.util.List;
import java.sql.Date;
import java.sql.Time;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import race.team.race.entity.Etape;
import race.team.race.entity.VPointsCoureurEtape;
import race.team.race.exception.MyException;
import race.team.race.service.EtapeService;
import race.team.race.service.PointClassementService;
import race.team.race.utils.IdGenerator;

@Controller
public class EtapeController {
    @Autowired
    private EtapeService es;

    @Autowired
    private PointClassementService pcs;

    @Autowired
    private IdGenerator idGenerator;

    @GetMapping("/user/etape/list")
    public String getlistEtape(HttpServletRequest request,
            @RequestParam(name = "nbrParPage", defaultValue = "5") int nbrParPage,
            @RequestParam(name = "noPage", defaultValue = "1") int noPage,
            @RequestParam(name = "filtre_nom", defaultValue = "") String nom,
            @RequestParam(name = "filtre_longueur_min", defaultValue = "0.0") double longueurMin,
            @RequestParam(name = "filtre_longueur_max", defaultValue = "0.0") double longueurMax,
            @RequestParam(name = "filtre_nb_coureur_min", defaultValue = "0") int nbCoureurMin,
            @RequestParam(name = "filtre_nb_coureur_max", defaultValue = "0") int nbCoureurMax,
            @RequestParam(name = "column", defaultValue = "id") String column,
            @RequestParam(name = "sort", defaultValue = "1") int sort, Model model) {
        HttpSession session = request.getSession();
        if (session.getAttribute("id_user") == null) {
            return "redirect:/user/signin";
        }
        List<Etape> etape = new ArrayList<>();
        int totalPages = 0;
        if (nom.equals("") && longueurMin == 0.0 && longueurMax == 0.0 && nbCoureurMin == 0 && nbCoureurMax == 0) {
            etape = es.getEtapePerPage(noPage, nbrParPage, column, sort);
            totalPages = (int) Math.ceil((double) es.countEtape() / nbrParPage);
        } else {
            etape = es.getEtapeFiltre(nom, longueurMin, longueurMax, nbCoureurMin, nbCoureurMax);
            totalPages = (int) Math.ceil((double) etape.size() / nbrParPage);
        }

        model.addAttribute("etape", etape);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("noPage", noPage);
        model.addAttribute("sort", sort);

        return "user/etape/list";
    }

    @GetMapping("/admin/etape/list")
    public String getlistEtapeAdmin(HttpServletRequest request,
            @RequestParam(name = "nbrParPage", defaultValue = "5") int nbrParPage,
            @RequestParam(name = "noPage", defaultValue = "1") int noPage,
            @RequestParam(name = "filtre_nom", defaultValue = "") String nom,
            @RequestParam(name = "filtre_longueur_min", defaultValue = "0.0") double longueurMin,
            @RequestParam(name = "filtre_longueur_max", defaultValue = "0.0") double longueurMax,
            @RequestParam(name = "filtre_nb_coureur_min", defaultValue = "0") int nbCoureurMin,
            @RequestParam(name = "filtre_nb_coureur_max", defaultValue = "0") int nbCoureurMax,
            @RequestParam(name = "column", defaultValue = "id") String column,
            @RequestParam(name = "sort", defaultValue = "1") int sort, Model model) {
        HttpSession session = request.getSession();
        if (session.getAttribute("id_admin") == null) {
            return "redirect:/admin/signin";
        }
        List<Etape> etape = new ArrayList<>();
        int totalPages = 0;
        if (nom.equals("") && longueurMin == 0.0 && longueurMax == 0.0 && nbCoureurMin == 0 && nbCoureurMax == 0) {
            etape = es.getEtapePerPage(noPage, nbrParPage, column, sort);
            totalPages = (int) Math.ceil((double) es.countEtape() / nbrParPage);
        } else {
            etape = es.getEtapeFiltre(nom, longueurMin, longueurMax, nbCoureurMin, nbCoureurMax);
            totalPages = (int) Math.ceil((double) etape.size() / nbrParPage);
        }

        model.addAttribute("etape", etape);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("noPage", noPage);
        model.addAttribute("sort", sort);

        return "admin/etape/list";
    }

    @PostMapping("/admin/etape/save")
    public String saveEtapeAdmin(HttpServletRequest request, @RequestParam(name = "nom") String nom,
            @RequestParam(name = "longueur") String longueur, @RequestParam(name = "nb_coureur") int nbCoureur,
            @RequestParam(name = "rang") int rang,
            @RequestParam(name = "date_depart") Date dateDepart,
            @RequestParam(name = "heure_depart") Time heureDepart,
            Model model) {
        HttpSession session = request.getSession();
        if (session.getAttribute("id_admin") == null) {
            return "redirect:/admin/signin";
        }
        Etape e = new Etape();

        try {
            e.setLongueur(longueur);
        } catch (MyException e1) {
            e1.printStackTrace();
        }
        e.setId(idGenerator);
        e.setNbCoureurEquipe(nbCoureur);
        e.setRang(rang);
        e.setNom(nom);
        e.setDateDepart(dateDepart);
        e.setHeureDepart(heureDepart);

        es.insererEtape(e);

        return "redirect:/admin/etape/list";
    }

    @GetMapping("/admin/etape/list-classement")
    public String getlistClassementEtapeJ(HttpServletRequest request,
            @RequestParam(name = "id_etape") String idEtape, Model model) {
        HttpSession session = request.getSession();
        if (session.getAttribute("id_admin") == null) {
            return "redirect:/admin/signin";
        }
        List<VPointsCoureurEtape> classementEtape = pcs.getClassementEtapeJ(idEtape);
        List<Etape> etape = es.getEtapes();
        Etape e = es.getEtapeById(idEtape);

        model.addAttribute("etape", etape);
        model.addAttribute("e", e);
        model.addAttribute("classement_etape", classementEtape);

        return "admin/etape/list-classement";
    }
}
