package PDFBox;

import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.io.RandomAccessRead;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.AccessPermission;
import org.apache.pdfbox.text.PDFTextStripper;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class ExtractText {


    String sourceDir = System.getProperty("user.dir") + File.separator + "sample_PDF" + File.separator + "test.pdf"; //저장된 dir
    String targetDir = System.getProperty("user.dir") + File.separator + "result_PDF"; // 저장할 dir
    String fileName = "converted"; // 저장할 이름.

    @Test
    public void extractTextTest() throws IOException {

        PDDocument document = PDDocument.load(new File(sourceDir));
        AccessPermission ap = document.getCurrentAccessPermission();
        if (!ap.canExtractContent()) {
            throw new IOException("You do not have permission to extract text");
        }

        PDFTextStripper stripper = new PDFTextStripper();

        stripper.setSortByPosition(true);

        for (int p = 1; p <= document.getNumberOfPages(); ++p) {
            // Set the page interval to extract. If you don't, then all pages would be extracted.
            stripper.setStartPage(p);
            stripper.setEndPage(p);

            // let the magic happen
            String text = stripper.getText(document);

            // do some nice output with a header
            String pageStr = String.format("page %d:", p);
            System.out.println(pageStr);
            for (int i = 0; i < pageStr.length(); ++i) {
                System.out.print("-");
            }
            System.out.println();
            System.out.println(text.trim());
            System.out.println();

            // If the extracted text is empty or gibberish, please try extracting text
            // with Adobe Reader first before asking for help. Also read the FAQ
            // on the website:
            // https://pdfbox.apache.org/2.0/faq.html#text-extraction
        }
    }
}
