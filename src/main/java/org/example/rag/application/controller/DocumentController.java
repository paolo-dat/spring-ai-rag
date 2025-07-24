package org.example.rag.application.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.core.io.InputStreamResource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class DocumentController {

  private final static Logger log = LoggerFactory.getLogger(DocumentController.class);
  private final VectorStore vectorStore;

  public DocumentController(VectorStore vectorStore) {
    this.vectorStore = vectorStore;
  }

  @PostMapping("/upload")
  public String uploadPdf(@RequestParam("file") MultipartFile file) throws IOException {
    try {
      var pdfResource = new InputStreamResource(file.getInputStream());
      var pagePdfDocumentReader = new PagePdfDocumentReader(pdfResource);
      var tokenTextSplitter = new TokenTextSplitter();
      vectorStore.accept(tokenTextSplitter.apply(pagePdfDocumentReader.get()));
      log.info("PDF content split and stored in vector store.");
      return "File stored";
    } catch (Exception e) {
      log.error("Something went wrong", e);
      return "Something went wrong";
    }
  }
}
