package race.team.race.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import race.team.race.entity.Equipe;
import race.team.race.entity.Etape;
import race.team.race.entity.Penalite;
import race.team.race.service.EquipeService;
import race.team.race.service.EtapeService;
import race.team.race.service.PenaliteService;

@Controller
public class PenaliteController {
    @Autowired
    private PenaliteService ps;

    @Autowired
    private EtapeService ets;

    @Autowired
    private EquipeService eqs;

    @GetMapping("/admin/penalite/list")
    public String getlistPenaliteAdmin(HttpServletRequest request,
            @RequestParam(name = "nbrParPage", defaultValue = "5") int nbrParPage,
            @RequestParam(name = "noPage", defaultValue = "1") int noPage,
            @RequestParam(name = "filtre_etape", defaultValue = "") String idEtape,
            @RequestParam(name = "filtre_equipe", defaultValue = "") String idEquipe,
            @RequestParam(name = "column", defaultValue = "id") String column,
            @RequestParam(name = "sort", defaultValue = "1") int sort, Model model) {
        HttpSession session = request.getSession();
        if (session.getAttribute("id_admin") == null) {
            return "redirect:/admin/signin";
        }
        List<Penalite> penalite = new ArrayList<>();
        int totalPages = 0;
        if (idEtape.equals("") && idEtape.equals("")) {
            penalite = ps.getPenalitePerPage(noPage, nbrParPage, column, sort);
            totalPages = (int) Math.ceil((double) ps.countPenalite() / nbrParPage);
        } else {
            penalite = ps.getPenaliteFiltre(idEtape, idEquipe);
            totalPages = (int) Math.ceil((double) penalite.size() / nbrParPage);
        }
        List<Equipe> equipes = eqs.getEquipes();
        List<Etape> etapes = ets.getEtapes();

        model.addAttribute("penalite", penalite);
        model.addAttribute("etape", etapes);
        model.addAttribute("equipe", equipes);

        model.addAttribute("totalPages", totalPages);
        model.addAttribute("noPage", noPage);
        model.addAttribute("sort", sort);

        return "admin/penalite/list";
    }

    @PostMapping("/admin/penalite/save")
    public String savePenaliteAdmin(HttpServletRequest request, @RequestParam(name = "etape") String idEtape,
            @RequestParam(name = "equipe") String idEquipe, @RequestParam(name = "temps") String temps,
            Model model) {
        HttpSession session = request.getSession();
        if (session.getAttribute("id_admin") == null) {
            return "redirect:/admin/signin";
        }

        // Extraire les heures, les minutes et les secondes à partir de 'temps'
        String[] parts = temps.split(":");
        long heure = Long.parseLong(parts[0]);
        long minute = Long.parseLong(parts[1]);
        long seconde = Long.parseLong(parts[2]);

        // Utiliser 'insererPenalite' avec les valeurs extraites
        ps.insererPenalite(idEtape, idEquipe, heure, minute, seconde);

        return "redirect:/admin/penalite/list";
    }

    @GetMapping("/admin/penalite/delete")
    public String deleteLieu(HttpServletRequest request, @RequestParam("id_penalite") String id) {
        HttpSession session = request.getSession();
        if (session.getAttribute("id_admin") == null) {
            return "redirect:/admin/signin";
        }

        ps.deletePenalitesbyId(id);
        return "redirect:/admin/penalite/list";
    }
}
