package dev.ivank.trchatbotdemo.report;

import dev.ivank.trchatbotdemo.common.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ReportController extends BaseController {

    @GetMapping("${controller.paths.report}")
    public String reportPage() {
        return properties.getPage("report");
    }

    /*@PostMapping("${controller.paths.report}/download")
    public ResponseEntity<?> downloadReport(@RequestParam("date")LocalDate date) {
        // Download in CSV
    }*/
}
