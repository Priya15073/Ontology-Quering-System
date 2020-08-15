
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.riot.RDFFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.topbraid.shacl.validation.ValidationUtil;
import org.topbraid.shacl.vocabulary.SH;
import org.topbraid.jenax.util.JenaUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

public class shacl {
    private static Logger logger = LoggerFactory.getLogger(shacl.class);
    // Why This Failure marker
    private static final Marker WTF_MARKER = MarkerFactory.getMarker("WTF");



    public static void main(String[] args) throws Exception{
        try {

            String data = "C:\\Users\\priya\\Desktop\\sem6\\Sweb\\Project\\Submission1_2015073\\output1.ttl";
            String shape = "C:\\Users\\priya\\Desktop\\sem6\\Sweb\\Project\\Submission1_2015073\\shape2.ttl";


            Model shapeModel = JenaUtil.createDefaultModel();
            shapeModel.read(shape);
            System.out.println(shapeModel);
            Model dataModel = JenaUtil.createDefaultModel();
            dataModel.read(data);
            System.out.println(dataModel);


            Resource reportResource = ValidationUtil.validateModel(dataModel, shapeModel, true);
            System.out.println("hello1");
            boolean conforms  = reportResource.getProperty(SH.conforms).getBoolean();
            System.out.println("hello21");
            logger.trace("Conforms = " + conforms);
           // System.out.println("hello2");
           System.out.println(conforms);

            //if (!conforms) {
                String report = "C:\\Users\\priya\\Desktop\\sem6\\Sweb\\Project\\Submission1_2015073\\report2.ttl";
                File reportFile = new File(report);
                reportFile.createNewFile();
                OutputStream reportOutputStream = new FileOutputStream(reportFile);

                RDFDataMgr.write(reportOutputStream, reportResource.getModel(), RDFFormat.TTL);
           // System.out.println("hello3");
           // }
            //System.out.println("hello4");
        } catch (Throwable t) {
            logger.error(WTF_MARKER, t.getMessage(), t);
        }
    }
}
