package race.team.race.controller;

import java.sql.Timestamp;
import java.util.Enumeration;
import java.util.List;
import java.util.stream.Collectors;

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
public class DetailCoureurEtapeController {
    @Autowired
    private DetailCoureurEtapeService dces;

    @Autowired
    private EtapeService es;

    @Autowired
    private EquipeService eqs;

    @Autowired
    private CoureurService cs;

    @Autowired
    private IdGenerator idGenerator;

    @GetMapping("/user/etape-coureur/define")
    public String getEtapeCoureur(HttpServletRequest request, @RequestParam(name = "id_etape") String idEtape,
            Model model) {
        HttpSession session = request.getSession();
        if (session.getAttribute("id_user") == null) {
            return "redirect:/user/signin";
        }

        Etape etape = es.getEtapeById(idEtape);
        List<DetailCoureurEtape> dce = dces.getDetailCoureurEtapesByEtape(etape);

        if (dce.size() > 0) {
            return "redirect:/user/etape/list";
        }

        List<Equipe> equipe = eqs.getEquipes();
        List<Coureur> coureur = cs.getCoureurs();

        model.addAttribute("etape", etape);
        model.addAttribute("equipe", equipe);
        model.addAttribute("coureur", coureur);

        return "user/etape/form";
    }

    @GetMapping("/admin/etape-coureur/define")
    public String getEtapeCoureurAdmin(HttpServletRequest request, @RequestParam(name = "id_etape") String idEtape,
            Model model) {
        HttpSession session = request.getSession();
        if (session.getAttribute("id_admin") == null) {
            return "redirect:/admin/signin";
        }

        Etape etape = es.getEtapeById(idEtape);
        List<DetailCoureurEtape> dce = dces.getDetailCoureurEtapesByEtape(etape);

        if (dce.size() == 0) {
            return "redirect:/admin/etape/list";
        }
        int count = 0;
        for (int i = 0; i < dce.size(); i++) {
            if (dce.get(i).getDateHeureArrive() != null) {
                count++;
            }
        }

        if (count == dce.size()) {
            return "redirect:/admin/etape/list";
        }

        List<DetailCoureurEtape> dceWithNullDate = dce.stream().filter(detail -> detail.getDateHeureArrive() == null).collect(Collectors.toList());
        List<Equipe> equipe = eqs.getEquipes();
        List<Coureur> coureur = cs.getCoureurs();

        // Récupérer le message d'exception de la session
        String dateError = (String) session.getAttribute("dateError");
        String etapeError = (String) session.getAttribute("etapeError");
        if (dateError != null && etapeError != null) {
            model.addAttribute("dateError", dateError);
            model.addAttribute("etapeError", etapeError);

            session.removeAttribute("dateError");
            session.removeAttribute("etapeError");
        }

        model.addAttribute("etape", etape);
        model.addAttribute("detail", dceWithNullDate);
        model.addAttribute("equipe", equipe);
        model.addAttribute("coureur", coureur);

        return "admin/etape/form";
    }

    @PostMapping("/user/etape-coureur/save")
    public String saveCoureurs(HttpServletRequest request, @RequestParam("id_etape") String idEtape) {
        HttpSession session = request.getSession();
        if (session.getAttribute("id_user") == null) {
            return "redirect:/user/signin";
        }

        Enumeration<String> parameterNames = request.getParameterNames();
        String paramName = "";
        String idCoureur = "";
        Coureur coureur;
        Etape etape = es.getEtapeById(idEtape);
        DetailCoureurEtape dce;
        while (parameterNames.hasMoreElements()) {
            paramName = parameterNames.nextElement();
            if (paramName.startsWith("equipe")) {
                idCoureur = request.getParameter(paramName);
                System.out.println("Parameter Name - " + paramName + ", Value - " + idCoureur);
                coureur = cs.getCoureurById(idCoureur);
                dce = new DetailCoureurEtape();
                dce.setCoureur(coureur);
                dce.setEtape(etape);
                dce.setId(idGenerator);

                dces.insererDetailCoureurEtape(dce);
            }
        }

        return "redirect:/user/etape/list";
    }

    @PostMapping("/admin/etape-coureur/save")
    public String saveCoureursAdmin(HttpServletRequest request, @RequestParam("id_etape") String idEtape) {
        HttpSession session = request.getSession();
        if (session.getAttribute("id_admin") == null) {
            return "redirect:/admin/signin";
        }

        Enumeration<String> parameterNames = request.getParameterNames();
        String paramName;
        String dateArrivee, heureArrivee;
        String idCoureur;
        Etape etape = es.getEtapeById(idEtape);
        List<DetailCoureurEtape> dceList = dces.getDetailCoureurEtapesByEtape(etape);
        String dateTimeArrivee ="";
        Timestamp dateHeureDepart = Timestamp.valueOf(etape.getDateDepart() + " " + etape.getHeureDepart());
        
        while (parameterNames.hasMoreElements()) {
            paramName = parameterNames.nextElement();
            if (paramName.startsWith("date_arrive_")) {
                idCoureur = paramName.substring("date_arrive_".length());
                dateArrivee = request.getParameter(paramName);
                heureArrivee = request.getParameter("heure_arrive_" + idCoureur);

                if (dateArrivee != null && heureArrivee != null) {
                    dateTimeArrivee = dateArrivee + " " + heureArrivee;

                    for (DetailCoureurEtape detail : dceList) {
                        if (detail.getCoureur().getId().equals(idCoureur)) {
                            detail.setDateHeureArrive(Timestamp.valueOf(dateTimeArrivee));
                            try {
                                detail.verifDate(dateHeureDepart);
                            } catch (MyException e) {
                                session.setAttribute("dateError", e.getMessage());
                                session.setAttribute("etapeError", idEtape);
                                return "redirect:/admin/etape-coureur/define?id_etape=" + idEtape;
                            }
                            // dces.insererDetailCoureurEtape(detail);
                            break;
                        }
                    }
                }
            }
        }

        while (parameterNames.hasMoreElements()) {
            paramName = parameterNames.nextElement();
            if (paramName.startsWith("date_arrive_")) {
                idCoureur = paramName.substring("date_arrive_".length());
                dateArrivee = request.getParameter(paramName);
                heureArrivee = request.getParameter("heure_arrive_" + idCoureur);

                if (dateArrivee != null && heureArrivee != null) {
                    dateTimeArrivee = dateArrivee + " " + heureArrivee;

                    for (DetailCoureurEtape detail : dceList) {
                        if (detail.getCoureur().getId().equals(idCoureur)) {
                            detail.setDateHeureArrive(Timestamp.valueOf(dateTimeArrivee));
                            dces.insererDetailCoureurEtape(detail);
                            break;
                        }
                    }
                }
            }
        }

        return "redirect:/admin/etape/list";
    }

    @GetMapping("/user/etape/details")
    public String getDetailsEtape(HttpServletRequest request, @RequestParam(name = "id_etape") String idEtape,
            Model model) {
        HttpSession session = request.getSession();
        if (session.getAttribute("id_user") == null) {
            return "redirect:/user/signin";
        }

        Etape etape = es.getEtapeById(idEtape);
        List<DetailCoureurEtape> dce = dces.getDetailCoureurEtapesByEtape(etape);
        List<Equipe> equipe = eqs.getEquipes();

        model.addAttribute("etape", etape);
        model.addAttribute("detail", dce);
        model.addAttribute("equipe", equipe);

        return "user/etape/detail";
    }

    @GetMapping("/admin/etape/details")
    public String getDetailsEtapeAdmin(HttpServletRequest request, @RequestParam(name = "id_etape") String idEtape,
            Model model) {
        HttpSession session = request.getSession();
        if (session.getAttribute("id_admin") == null) {
            return "redirect:/admin/signin";
        }

        Etape etape = es.getEtapeById(idEtape);
        List<DetailCoureurEtape> dce = dces.getDetailCoureurEtapesByEtape(etape);
        List<Equipe> equipe = eqs.getEquipes();

        model.addAttribute("etape", etape);
        model.addAttribute("detail", dce);
        model.addAttribute("equipe", equipe);

        return "admin/etape/detail";
    }
}
