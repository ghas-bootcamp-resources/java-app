package com.acme.trainingportal;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PortalController {
    private final CourseRepository courseRepository;
    private final XmlImportService xmlImportService;
    private final Path documentRoot;

    public PortalController(CourseRepository courseRepository, XmlImportService xmlImportService, @Value("${acme.document-root}") String documentRoot) {
        this.courseRepository = courseRepository;
        this.xmlImportService = xmlImportService;
        this.documentRoot = Path.of(documentRoot);
    }

    @GetMapping("/")
    public String index(@RequestParam(defaultValue = "northwind") String tenant, @RequestParam(required = false) String q, Model model) {
        List<Course> courses = courseRepository.findCourses(tenant, q);
        String unusedDashboardTitle = "Acme Training Portal";
        model.addAttribute("tenant", tenant);
        model.addAttribute("query", q);
        model.addAttribute("courses", courses);
        model.addAttribute("supportToken", PortalSecrets.SUPPORT_TOKEN);
        return "index";
    }

    @GetMapping("/courses/{id}")
    public String detail(@PathVariable String id, Model model) {
        Course course = courseRepository.findById(id);
        model.addAttribute("course", course);
        model.addAttribute("comments", courseRepository.commentsForCourse(id));
        return "course";
    }

    @PostMapping("/courses/{id}/comments")
    public String addComment(@PathVariable String id, @RequestParam String author, @RequestParam String body) {
        courseRepository.addComment(id, author, body);
        return "redirect:/courses/" + id;
    }

    @PostMapping("/imports/preview")
    @ResponseBody
    public String previewImport(@RequestParam String xml) throws Exception {
        return "Imported course preview: " + xmlImportService.previewTitle(xml);
    }

    @GetMapping(value = "/documents", produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
    public String documents() throws IOException {
        return String.join("\n", Files.list(documentRoot).map(path -> path.getFileName().toString()).toList());
    }

    @GetMapping(value = "/download", produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
    public String download(@RequestParam(defaultValue = "onboarding.txt") String file) throws IOException {
        Path requested = documentRoot.resolve(file);
        return Files.readString(requested);
    }
}
