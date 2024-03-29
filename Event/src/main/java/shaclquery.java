
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
import org.topbraid.spin.util.JenaUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

public class shaclquery {
    private static Logger logger = LoggerFactory.getLogger(shaclquery.class);
    // Why This Failure marker
    private static final Marker WTF_MARKER = MarkerFactory.getMarker("WTF");



    public static void main(String[] args) {
        try {
            Path path = Paths.get(".").toAbsolutePath().normalize();
            String data = "C:\\Users\\priya\\Desktop\\sem6\\Sweb\\Project\\Submission1_2015073\\output1.ttl";
            String shape = "C:\\Users\\priya\\Desktop\\sem6\\Sweb\\Project\\Submission1_2015073\\shape.ttl";;


            Model dataModel = JenaUtil.createDefaultModel();
            dataModel.read(data);
            Model shapeModel = JenaUtil.createDefaultModel();
            shapeModel.read(shape);

            Resource reportResource = ValidationUtil.validateModel(dataModel, shapeModel, true);
            boolean conforms  = reportResource.getProperty(SH.conforms).getBoolean();
            logger.trace("Conforms = " + conforms);

            if (!conforms) {
                String report = "C:\\Users\\priya\\Desktop\\sem6\\Sweb\\Project\\Submission1_2015073\\report.ttl";
                File reportFile = new File(report);
                reportFile.createNewFile();
                OutputStream reportOutputStream = new FileOutputStream(reportFile);

                RDFDataMgr.write(reportOutputStream, reportResource.getModel(), RDFFormat.TTL);
            }

        } catch (Throwable t) {
            logger.error(WTF_MARKER, t.getMessage(), t);
        }
    }
}
