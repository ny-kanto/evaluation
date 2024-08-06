package race.team.race.controller;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import race.team.race.service.CoureurService;
import race.team.race.service.CsvService;
import race.team.race.service.DetailCoureurEtapeService;
import race.team.race.service.EquipeService;
import race.team.race.service.EtapeService;
import race.team.race.service.PointClassementService;

@Controller
public class ImportController {
    @Autowired
    private CsvService cs;

    @Autowired
    private EtapeService es;

    @Autowired
    private CoureurService cos;

    @Autowired
    private EquipeService eqs;

    @Autowired
    private DetailCoureurEtapeService dces;

    @Autowired
    private PointClassementService pcs;

    @GetMapping("/admin/form/import-data")
    public String getFormImport(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session.getAttribute("id_admin") == null) {
            return "redirect:/admin/signin";
        }
        return "admin/import/form";
    }

    @GetMapping("/admin/form/import-data-points")
    public String getFormImportPoints(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session.getAttribute("id_admin") == null) {
            return "redirect:/admin/signin";
        }
        return "admin/import/formPoints";
    }

    @PostMapping("/admin/import-data/save")
    public String importData(HttpServletRequest request, @RequestParam("etape") MultipartFile etapeFile,
            @RequestParam("resultat") MultipartFile resultatFile) {
                HttpSession session = request.getSession();
                if (session.getAttribute("id_admin") == null) {
                    return "redirect:/admin/signin";
                }
        if (etapeFile.isEmpty() || resultatFile.isEmpty()) {
            return "Veuillez sélectionner les fichiers à importer.";
        }

        String absolutePath = "D:\\JAVA PROJECT\\L3\\race\\data";

        // Importer les fichiers CSV dans la base de données

        es.importCsvEtape(absolutePath + File.separator + etapeFile.getOriginalFilename());
        cs.importCSV(absolutePath + File.separator + resultatFile.getOriginalFilename(), "resultat_temp");
        eqs.importCsv("resultat_temp");
        cos.importCsv("resultat_temp");
        dces.importCsv("resultat_temp");


        return "redirect:/admin/form/import-data";
    }

    @PostMapping("/admin/import-data-points/save")
    public String importData(HttpServletRequest request, @RequestParam("points") MultipartFile pointsFile) {
        HttpSession session = request.getSession();
        if (session.getAttribute("id_admin") == null) {
            return "redirect:/admin/signin";
        }
        if (pointsFile.isEmpty()) {
            return "Veuillez sélectionner les fichiers à importer.";
        }

        String absolutePath = "D:\\JAVA PROJECT\\L3\\race\\data";
        // Importer les fichiers CSV dans la base de données

        pcs.importCsvPoints(absolutePath + File.separator + pointsFile.getOriginalFilename());

        return "redirect:/admin/form/import-data-points";
    }
}
