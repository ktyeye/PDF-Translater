import PDFBox.PdfBoxService;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.fit.pdfdom.PDFDomTree;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

@Service
public class PDF2dom {

    @Autowired
    PdfBoxService pdfbox;

    String sourceDir = System.getProperty("user.dir") + File.separator + "sample_PDF" + File.separator + "test.pdf"; //저장된 dir
    String targetDir = System.getProperty("user.dir") + File.separator + "result_PDF"; // 저장할 dir
    String fileName = "test"; // 저장할 이름.

    @Test
    public void pdf2domTest() throws Exception {
//        File pdfFile = pdfbox.createPdf(sourceDir, savePdfFileName);
        PDDocument doc = PDDocument.load(new File(sourceDir));

        PDFDomTree parser = new PDFDomTree();
//		Document dom = parser.createDOM(pdf);
        File htmlFile = new File(targetDir + File.separator + fileName + ".html");
        parser.writeText(doc, new OutputStreamWriter(new FileOutputStream(htmlFile)));
    }


    @Test
    public void XmlWorkerTest() throws Exception {

    }

}
