package dev.ivank.trchatbotdemo.kb;

import dev.ivank.trchatbotdemo.common.BaseController;
import dev.ivank.trchatbotdemo.common.form.RestForm;
import dev.ivank.trchatbotdemo.common.io.FileStorageService;
import dev.ivank.trchatbotdemo.common.io.PdfProcessingService;
import dev.ivank.trchatbotdemo.kb.ctx.KnowledgeBaseOperationContext;
import dev.ivank.trchatbotdemo.kb.dto.PdfResponseDto;
import dev.ivank.trchatbotdemo.kb.dto.QAResponseDto;
import dev.ivank.trchatbotdemo.kb.dto.QAUpdateDto;
import dev.ivank.trchatbotdemo.kb.dto.QAsDto;
import dev.ivank.trchatbotdemo.kb.service.KnowledgeBaseOperationManager;
import dev.ivank.trchatbotdemo.kb.service.KnowledgeBaseOperationType;
import dev.ivank.trchatbotdemo.kb.service.PdfDtoClientService;
import dev.ivank.trchatbotdemo.kb.service.QADtoClientService;
import dev.ivank.trchatbotdemo.kb.service.form.KnowledgeBaseFormOptionsDto;
import dev.ivank.trchatbotdemo.kb.service.form.KnowledgeBaseFormOptionsService;
import dev.ivank.trchatbotdemo.kb.service.i18n.KnowledgeBaseLocalizationService;
import dev.ivank.trchatbotdemo.kb.service.pdf.PdfKnowledgeBaseOperationContext;
import dev.ivank.trchatbotdemo.security.auth.EmployeeOnly;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

@Controller
@EmployeeOnly
public class KnowledgeBaseController extends BaseController {
    private final KnowledgeBaseFormOptionsService formOptionsService;
    private final KnowledgeBaseLocalizationService localizationService;
    private final KnowledgeBaseOperationManager knowledgeBaseOperationManager;
    private final PdfProcessingService pdfProcessingService;
    private final FileStorageService fileStorageService;
    private final PdfDtoClientService pdfDtoClientService;
    private final QADtoClientService qaDtoClientService;

    @Autowired
    public KnowledgeBaseController(KnowledgeBaseFormOptionsService formOptionsService,
                                   KnowledgeBaseLocalizationService localizationService,
                                   KnowledgeBaseOperationManager knowledgeBaseOperationManager,
                                   PdfProcessingService pdfProcessingService,
                                   FileStorageService fileStorageService,
                                   PdfDtoClientService pdfDtoClientService,
                                   QADtoClientService qaDtoClientService) {
        this.formOptionsService = formOptionsService;
        this.localizationService = localizationService;
        this.knowledgeBaseOperationManager = knowledgeBaseOperationManager;
        this.pdfProcessingService = pdfProcessingService;
        this.fileStorageService = fileStorageService;
        this.pdfDtoClientService = pdfDtoClientService;
        this.qaDtoClientService = qaDtoClientService;
    }

    @GetMapping("${controller.paths.knowledgeBase}")
    public String knowledgeBasePage(Model model) {
        model.addAttribute(
                "pageTitle",
                localizationService.getMessage("page.title")
        );
        return properties.getPage("knowledgeBase");
    }

    @GetMapping("${controller.paths.knowledgeBaseApi}/init")
    public ResponseEntity<KnowledgeBaseFormOptionsDto> init(Authentication authentication) {
        RestForm<KnowledgeBaseFormOptionsDto> form = new RestForm<>(
                new KnowledgeBaseFormOptionsDto(),
                authentication
        );
        formOptionsService.prepareFormOptions(form);
        return ResponseEntity.ok(form.getDto());
    }

    // TODO: QA validator
    @PostMapping("${controller.paths.knowledgeBaseUploadJson}")
    public ResponseEntity<?> uploadJson(@RequestBody QAsDto dto) {
        KnowledgeBaseOperationContext<QAUpdateDto> context = new KnowledgeBaseOperationContext<>(
                dto.getQas(),
                dto.isTranslateToSupported()
        );
        knowledgeBaseOperationManager.execute(KnowledgeBaseOperationType.QA_UPDATE, context);
        return ResponseEntity.noContent().build();
    }

    // TODO: file validator
    @PostMapping(value = "${controller.paths.knowledgeBaseUploadPdf}"
            /*,consumes = MediaType.MULTIPART_FORM_DATA_VALUE*/)
    public ResponseEntity<?> uploadPdf(@RequestParam("files") List<MultipartFile> files,
                                       @RequestParam("translate") Optional<Boolean> translate) {
        PdfKnowledgeBaseOperationContext context = new PdfKnowledgeBaseOperationContext(files, translate.orElse(false));
        knowledgeBaseOperationManager.execute(KnowledgeBaseOperationType.PDF_UPDATE, context);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("${controller.paths.knowledgeBaseGetQAs}")
    @ResponseBody
    public Page<QAResponseDto> getQAs(@RequestParam("p") Optional<Integer> p,
                                      @RequestParam("size") Optional<Integer> size) {
        return qaDtoClientService.getDtoPage(p.orElse(0), size.orElse(15));
    }

    @GetMapping("${controller.paths.knowledgeBaseGetPdfs}")
    @ResponseBody
    public Page<PdfResponseDto> getPdfs(@RequestParam("p") Optional<Integer> p,
                                        @RequestParam("size") Optional<Integer> size) {
        return pdfDtoClientService.getDtoPage(p.orElse(0), size.orElse(15));
    }

    @GetMapping("${controller.paths.knowledgeBasePdfPreview}/{name}")
    public ResponseEntity<Resource> previewPdf(@PathVariable String name) throws IOException {
        Path thumbnailPath = pdfProcessingService.getThumbnailPath(name);
        if (!Files.exists(thumbnailPath)) {
            return ResponseEntity.notFound().build();
        }
        Resource fileResource = new UrlResource(thumbnailPath.toUri());
        return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(fileResource);
    }

    @GetMapping("${controller.paths.knowledgeBasePdfDownload}/{name}")
    public ResponseEntity<Resource> downloadPdf(@PathVariable String name) {
        Resource resource = fileStorageService.loadFile(name);
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF).body(resource);
    }
}
