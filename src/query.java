import org.apache.jena.query.*;
import org.apache.jena.rdfconnection.RDFConnection;
import org.apache.jena.rdfconnection.RDFConnectionFactory;
import org.apache.jena.system.Txn;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class query {


    static public void  main(String[] args)throws Exception{

        String data = readFileAsString("C:\\Users\\priya\\Desktop\\sem6\\Sweb\\Project\\Submission1_2015073\\query_1.txt");
        System.out.println(data);

        Query query = QueryFactory.create(data);
        Dataset ds = DatasetFactory.createTxnMem();
        RDFConnection conn = RDFConnectionFactory.connect(ds);

        Txn.executeWrite(conn, () ->{
            conn.load("C:\\Users\\priya\\Desktop\\sem6\\Sweb\\Project\\Submission1_2015073\\output1.ttl");
            //conn.queryResultSet(query, ResultSetFormatter::out);
            OutputStream out= null;
            try {
                out = new FileOutputStream("C:\\Users\\priya\\Desktop\\sem6\\Sweb\\Project\\Submission1_2015073\\res_1.csv");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            QueryExecution qExec = conn.query(query);
                ResultSet rs = qExec.execSelect();
                ResultSetFormatter.outputAsCSV(out,rs);
        });
//        System.out.println("After write transaction");
//        OutputStream out= new FileOutputStream("C:\\Users\\priya\\Desktop\\sem6\\Sweb\\Project\\Submission1_2015073\\res_1.csv");
//        conn.queryResultSet(query,ResultSetFormatter::outputAsCSV);
//


    }
    public static String readFileAsString(String fileName)throws Exception
    {
        String data = "";
        data = new String(Files.readAllBytes(Paths.get(fileName)));
        return data;
    }

}