package chris.tests;

import com.google.common.collect.Lists;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import pl.edu.icm.cermine.ContentExtractor;
import pl.edu.icm.cermine.ContentExtractorLoopTest;
import pl.edu.icm.cermine.exception.AnalysisException;
import pl.edu.icm.cermine.structure.model.BxDocument;
import pl.edu.icm.cermine.structure.transformers.BxDocumentToJSONWriter;

public class CerminJsonTest {

    private ContentExtractor extractor;
    
    @Before
    public void setUp() throws AnalysisException, IOException {
        extractor = new ContentExtractor();
        
    }

    @Test
    public void extractionLoopTest() throws Exception {
        List<String> titles = new ArrayList<String>();
        File baseDir = new File ("/home/murrman/cermine/CERMINE/cermine-impl/target/test_pdfs/");
        File[] fileList = baseDir.listFiles();
        for (File file : fileList) {
            String fileName = file.getName();
            String filePath = file.getAbsolutePath();
            String filePathBase = filePath.substring(0,filePath.length() -4);
            System.out.println("fileName is " + fileName);
        	int fileLength = fileName.length();
        	String fileNameBase =  fileName.substring(0, fileLength-4);
            InputStream testStream = new FileInputStream(file);
            extractor.setPDF(testStream);
            
            BxDocument doc = extractor.getBxDocumentWithSpecificLabels();
            BxDocumentToJSONWriter writer = new BxDocumentToJSONWriter();
            Writer fw = new OutputStreamWriter(new FileOutputStream(filePathBase + ".json"), "UTF-8");
            writer.write(fw, Lists.newArrayList(doc), "UTF-8");
            //extractor.getContentAsNLM();
            //titles.add(extractor.getMetadata().getTitle());
        }

        assertEquals(Lists.newArrayList(
                "Complications related to deep venous thrombosis prophylaxis in trauma: a systematic review of the literature",
                "Patient Experiences of Structured Heart Failure Programmes",
                "Phytochemical and Biological investigations of Phoenix paludosa Roxb.",
                "The four Zn fingers of MBNL1 provide a flexible platform for recognition of its RNA binding elements",
                "Iron deficiency anaemia can be improved after eradication of Helicobacter pylori",
                "VESPA: Very large-scale Evolutionary and Selective Pressure Analyses"),
            titles); 
    }
}
