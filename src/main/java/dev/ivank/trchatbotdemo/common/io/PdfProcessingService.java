package dev.ivank.trchatbotdemo.common.io;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class PdfProcessingService {
    @Value("${file.upload.directory}")
    private String uploadDir;

    private static final String THUMBNAIL_PREFIX = "_thumbnail.png";

    public void generateThumbnail(String filePath) {
        Path pdfPath = Paths.get(filePath);
        Path thumbnailPath = pdfPath.resolveSibling(pdfPath.getFileName().toString().replace(".pdf", THUMBNAIL_PREFIX));

        if (!filePath.toLowerCase().endsWith(".pdf")) {
            throw new RuntimeException("error.fileNotPdf");
        }

        try (PDDocument document = Loader.loadPDF(pdfPath.toFile())) {
            PDFRenderer renderer = new PDFRenderer(document);
            BufferedImage image = renderer.renderImageWithDPI(0, 100);
            ImageIO.write(image, "png", thumbnailPath.toFile());
        } catch (IOException e) {
            throw new RuntimeException("error.pdfThumbnailFail", e);
        }
    }

    public Path getThumbnailPath(String fileName) {
        return Paths.get(uploadDir).resolve(fileName.replace(".pdf", THUMBNAIL_PREFIX));
    }
}

