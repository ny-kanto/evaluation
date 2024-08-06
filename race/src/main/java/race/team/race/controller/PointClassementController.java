package race.team.race.controller;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import race.team.race.entity.Categorie;
import race.team.race.entity.Equipe;
import race.team.race.entity.Etape;
import race.team.race.entity.VClassementGeneral;
import race.team.race.entity.VPointsCoureurEtape;
import race.team.race.service.CategorieService;
import race.team.race.service.EquipeService;
import race.team.race.service.EtapeService;
import race.team.race.service.PointClassementService;

@Controller
public class PointClassementController {
    @Autowired
    private PointClassementService pcs;

    @Autowired
    private EquipeService eqs;

    @Autowired
    private CategorieService cs;

    @Autowired
    private EtapeService es;

    @GetMapping("/admin/classement/list")
    public String getlistClassementAdmin(HttpServletRequest request,
            @RequestParam(name = "nbrParPage", defaultValue = "5") int nbrParPage,
            @RequestParam(name = "noPage", defaultValue = "1") int noPage,
            @RequestParam(name = "filtre_nom", defaultValue = "") String nom,
            @RequestParam(name = "filtre_rang_min", defaultValue = "0") int rangMin,
            @RequestParam(name = "filtre_rang_max", defaultValue = "0") int rangMax,
            @RequestParam(name = "filtre_equipe", defaultValue = "") String idEquipe,
            @RequestParam(name = "column", defaultValue = "rang") String column,
            @RequestParam(name = "sort", defaultValue = "1") int sort, Model model) {
        HttpSession session = request.getSession();
        if (session.getAttribute("id_admin") == null) {
            return "redirect:/admin/signin";
        }
        List<VClassementGeneral> classement = new ArrayList<>();
        int totalPages = 0;
        if (nom.equals("") && rangMin == 0 && rangMax == 0 && idEquipe.equals("")) {
            classement = pcs.getClassementPerPage(noPage, nbrParPage, column, sort);
            totalPages = (int) Math.ceil((double) pcs.countClassement() / nbrParPage);
        } else {
            classement = pcs.getClassementFiltre(nom, idEquipe, rangMin, rangMax);
            totalPages = (int) Math.ceil((double) classement.size() / nbrParPage);
        }
        List<Equipe> equipe = eqs.getEquipes();

        model.addAttribute("equipe", equipe);
        model.addAttribute("classement", classement);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("noPage", noPage);
        model.addAttribute("sort", sort);

        return "admin/classement/list";
    }

    @GetMapping("/admin/coureur-classement/list")
    public String getlistEquipeClassementAdmin(HttpServletRequest request, @RequestParam(name = "id_equipe") String idEquipe, Model model) {
        HttpSession session = request.getSession();
        if (session.getAttribute("id_admin") == null) {
            return "redirect:/admin/signin";
        }
        List<VClassementGeneral> classement = pcs.getClassementEtapeEquipe(idEquipe);
        Equipe e = eqs.getEquipeById(idEquipe);

        model.addAttribute("equipe", e);
        model.addAttribute("classement", classement);

        return "admin/classement/list-coureur";
    }

    @GetMapping("/admin/classement-etape/list")
    public String getlistClassementEtapeAdmin(HttpServletRequest request,
            @RequestParam(name = "etape", defaultValue = "ETA0001") String idEtape, Model model) {
        HttpSession session = request.getSession();
        if (session.getAttribute("id_admin") == null) {
            return "redirect:/admin/signin";
        }
        List<VPointsCoureurEtape> classementEtape = pcs.getClassementEtape(idEtape);
        List<Etape> etape = es.getEtapes();
        Etape e = es.getEtapeById(idEtape);

        model.addAttribute("etape", etape);
        model.addAttribute("e", e);
        model.addAttribute("classement_etape", classementEtape);

        return "admin/classement/list-etape";
    }

    @GetMapping("/user/classement/list")
    public String getlistClassement(HttpServletRequest request,
            @RequestParam(name = "nbrParPage", defaultValue = "5") int nbrParPage,
            @RequestParam(name = "noPage", defaultValue = "1") int noPage,
            @RequestParam(name = "filtre_nom", defaultValue = "") String nom,
            @RequestParam(name = "filtre_rang_min", defaultValue = "0") int rangMin,
            @RequestParam(name = "filtre_rang_max", defaultValue = "0") int rangMax,
            @RequestParam(name = "filtre_equipe", defaultValue = "") String idEquipe,
            @RequestParam(name = "column", defaultValue = "rang") String column,
            @RequestParam(name = "sort", defaultValue = "1") int sort, Model model) {
        HttpSession session = request.getSession();
        if (session.getAttribute("id_user") == null) {
            return "redirect:/user/signin";
        }
        List<VClassementGeneral> classement = new ArrayList<>();
        int totalPages = 0;
        if (nom.equals("") && rangMin == 0 && rangMax == 0 && idEquipe.equals("")) {
            classement = pcs.getClassementPerPage(noPage, nbrParPage, column, sort);
            totalPages = (int) Math.ceil((double) pcs.countClassement() / nbrParPage);
        } else {
            classement = pcs.getClassementFiltre(nom, idEquipe, rangMin, rangMax);
            totalPages = (int) Math.ceil((double) classement.size() / nbrParPage);
        }
        List<Equipe> equipe = eqs.getEquipes();

        model.addAttribute("equipe", equipe);
        model.addAttribute("classement", classement);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("noPage", noPage);
        model.addAttribute("sort", sort);

        return "user/classement/list";
    }

    @GetMapping("/user/classement-etape/list")
    public String getlistClassementEtape(HttpServletRequest request,
            @RequestParam(name = "etape", defaultValue = "ETA0001") String idEtape, Model model) {
        HttpSession session = request.getSession();
        if (session.getAttribute("id_user") == null) {
            return "redirect:/user/signin";
        }
        List<VPointsCoureurEtape> classementEtape = pcs.getClassementEtape(idEtape);
        List<Etape> etape = es.getEtapes();
        Etape e = es.getEtapeById(idEtape);

        model.addAttribute("etape", etape);
        model.addAttribute("e", e);
        model.addAttribute("classement_etape", classementEtape);

        return "user/classement/list-etape";
    }

    @GetMapping("/user/classement-equipe/list")
    public String getlistClassementEquipe(HttpServletRequest request,
            @RequestParam(name = "categorie", defaultValue = "") String idCategorie, Model model) {
        HttpSession session = request.getSession();
        if (session.getAttribute("id_user") == null) {
            return "redirect:/user/signin";
        }
        List<VPointsCoureurEtape> classementEquipe = new ArrayList<>();
        Categorie c = new Categorie();
        if (idCategorie.equals("")) {
            classementEquipe = pcs.getClassementEquipe();
            c.setNom("Tous Categories");
        } else {
            classementEquipe = pcs.getClassementEquipeCategorie(idCategorie);
            
            c = cs.getCategorieById(idCategorie);
        }


        List<Categorie> categorie = cs.getCategories();

        model.addAttribute("categorie", categorie);
        model.addAttribute("c", c);
        model.addAttribute("classement_equipe", classementEquipe);

        return "user/classement/list-equipe";
    }

    @GetMapping("/admin/classement-equipe/list")
    public String getlistClassementEquipeAdmin(HttpServletRequest request,
            @RequestParam(name = "categorie", defaultValue = "") String idCategorie, Model model) {
        HttpSession session = request.getSession();
        if (session.getAttribute("id_admin") == null) {
            return "redirect:/admin/signin";
        }
        List<VPointsCoureurEtape> classementEquipe = new ArrayList<>();
        Categorie c = new Categorie();
        List<Integer> newRang = new ArrayList<>();
        List<Integer> listRang = new ArrayList<>();
        if (idCategorie.equals("")) {
            classementEquipe = pcs.getClassementEquipe();
            c.setNom("Tous Categories");
        } else {
            classementEquipe = pcs.getClassementEquipeCategorie(idCategorie);
            for (int i = 0; i < classementEquipe.size(); i++) {
                for (int j = i + 1; j < classementEquipe.size(); j++) {
                    if (classementEquipe.get(i).getRang() == classementEquipe.get(j).getRang()) {
                        listRang.add(classementEquipe.get(i).getRang());
                        // break;
                    }
                }
            }
            Set<Integer> listRangNew = new LinkedHashSet<>(listRang);
    
            newRang = new ArrayList<>(listRangNew);
            model.addAttribute("rang", newRang);
            c = cs.getCategorieById(idCategorie);
        }

        List<Categorie> categorie = cs.getCategories();

        model.addAttribute("categorie", categorie);
        model.addAttribute("c", c);
        model.addAttribute("classement_equipe", classementEquipe);

        return "admin/classement/list-equipe";
    }

    @GetMapping("/admin/classement-equipe/certificat")
    public String getCertificat(HttpServletRequest request, @RequestParam(name = "id_equipe", defaultValue = "") String idEquipe, Model model) {
        HttpSession session = request.getSession();
        if (session.getAttribute("id_admin") == null) {
            return "redirect:/admin/signin";
        }
        // List<VPointsCoureurEtape> classementEquipe = pcs.getClassementEquipe();
        Equipe e = eqs.getEquipeById(idEquipe);

        model.addAttribute("equipe", e);
        // model.addAttribute("points", classementEquipe.get(0).pointForm());
        
        return "admin/export/certificat";
    }
}
