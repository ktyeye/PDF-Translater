package PDFBox;

    import org.apache.fontbox.ttf.OTFParser;
import org.apache.fontbox.ttf.OpenTypeFont;
import org.apache.pdfbox.contentstream.operator.Operator;
import org.apache.pdfbox.cos.COSArray;
    import org.apache.pdfbox.cos.COSFloat;
    import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.cos.COSString;
import org.apache.pdfbox.pdfparser.PDFStreamParser;
import org.apache.pdfbox.pdfwriter.ContentStreamWriter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
    import org.junit.jupiter.api.Test;

    import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
    import java.nio.charset.StandardCharsets;
    import java.util.List;

public class ReplaceText2 {

    String sourceDir = System.getProperty("user.dir") + File.separator + "sample_PDF" + File.separator + "test.pdf"; //저장된 dir
    String targetDir = System.getProperty("user.dir") + File.separator + "result_PDF"; // 저장할 dir
    String fileName = "converted"; // 저장할 이름.

    @Test
    public void replaceTest2() throws IOException {
            PDDocument document = null;
            OTFParser otfParser = new OTFParser();
//            OpenTypeFont otf = otfParser.parse(new File("NotoSansKR-Regular.otf"));

            try {
                document = PDDocument.load(new File(sourceDir));
//                PDFont font = PDType0Font.load(document, new File("NotoSansKR-Regular.ttf"));

                String searchString = "This is a test PDF document";
                String replacement = "My name is Max";

                for (PDPage page: document.getPages()) {
                    PDFStreamParser parser = new PDFStreamParser(page);
                    parser.parse();
                    List tokens = parser.getTokens();

                    for (int j = 0; j < tokens.size(); j++) {
                        Object next = tokens.get(j);

                        if (next instanceof PDFont) {
                            System.out.println(next);
                        }

                        if (next instanceof Operator) {
                            Operator op = (Operator) next;
                            String pstring = "";
                            int prej = 0;


                            //Tj and TJ are the two operators that display strings in a PDF
                            if (op.getName().equals("Tj"))
                            {
                                // Tj takes one operator and that is the string to display so lets update that operator
                                COSString previous = (COSString) tokens.get(j - 1);
                                String string = previous.getString();
                                string = string.replaceFirst(searchString, replacement);
                                previous.setValue(string.getBytes());

                            } else
                            if (op.getName().equals("TJ"))
                            {
                                COSArray previous = (COSArray) tokens.get(j - 1);
                                for (int k = 0; k < previous.size(); k++)
                                {
                                    Object arrElement = previous.getObject(k);

                                    if (arrElement instanceof COSString)
                                    {
                                        COSString cosString = (COSString) arrElement;
                                        String string = cosString.getString();


                                        if (j == prej) {
                                            pstring += string;
                                        } else {
                                            prej = j;
                                            pstring = string;
                                        }
                                    }
                                }

//
//                                int total = previous.size() -1;
//                                String replacedStr = pstring.replaceAll(searchString, replacement);
//                                int tmpIndex = 0;
//                                int strlen = 0;
//                                for (int index = 0; index < total; index++) {
//                                    Object arrElement = previous.getObject(index);
//
//                                    if (arrElement instanceof COSString) {
//                                        COSString cosElement = (COSString) arrElement;
//                                        String cosStr = cosElement.getString();
//                                        strlen = cosStr.length();
//
//                                        //maybe korean.
////                                        cosElement.setValue(cosStr.substring(tmpIndex, strlen).getBytes(StandardCharsets.UTF_8));
//
//                                        cosElement.setValue(cosStr.substring(tmpIndex, strlen).getBytes());
//                                        tmpIndex += strlen;
//                                        if (tmpIndex > cosStr.length()) {
//                                            break;
//                                        }
//                                    }
//                                }

                                COSString cosString2 = (COSString) previous.getObject(0);
                                //previous String parsing.

                                System.out.println(pstring.replaceAll(searchString, replacement));
                                cosString2.setValue(pstring.replaceAll(searchString, replacement).getBytes());
                                System.out.println(cosString2);


                                int total = previous.size()-1;
                                for (int k = total; k > 0; k--) {
                                    //seperate class
                                    System.out.println(previous.get(k).getCOSObject());
                                    previous.remove(k);
                                }
                            }
                        }
                    }
                    PDStream updatedStream = new PDStream(document);
                    OutputStream out = updatedStream.createOutputStream(COSName.FLATE_DECODE);
                    ContentStreamWriter tokenWriter = new ContentStreamWriter(out);
                    tokenWriter.writeTokens(tokens);
                    out.close();
//                    page.getResources().add(font);
                    page.setContents(updatedStream);
                }

                document.save(targetDir + File.separator + fileName + ".pdf");
            } finally {
                if (document != null) {
                    document.close();
                }
            }
        }

}
