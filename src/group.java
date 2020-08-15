import com.opencsv.CSVReader;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.jena.atlas.io.IO ;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.jena.datatypes.RDFDatatype;
import org.apache.jena.datatypes.TypeMapper;
import org.apache.jena.fuseki.main.FusekiLib;
import org.apache.jena.fuseki.main.FusekiServer;
import org.apache.jena.reasoner.Reasoner;
import org.apache.jena.reasoner.ReasonerRegistry;
import org.apache.jena.riot.RDFLanguages;
import org.apache.jena.riot.RDFParser;
import org.apache.jena.riot.system.ErrorHandlerFactory;
import org.apache.jena.sparql.core.DatasetGraph;
import org.apache.jena.tdb.base.file.Location;
import org.apache.jena.graph.Triple;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.*;
import org.apache.jena.tdb.TDB ;
import org.apache.jena.rdfconnection.RDFConnection;
import org.apache.jena.rdfconnection.RDFConnectionFactory;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.riot.RDFFormat;
import org.apache.jena.system.Txn;
import org.apache.jena.tdb.TDBFactory;
import org.apache.jena.tdb.TDBLoader;
import org.apache.jena.tdb.setup.DatasetBuilderStd;
import org.apache.jena.tdb.store.DatasetGraphTDB;
import org.apache.jena.tdb.sys.TDBInternal;
import org.apache.jena.util.FileManager;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.apache.jena.tdb.TDBFactory.createDatasetGraph;

public class group extends Object {
    private static final String PathtoCSV = "C:\\Users\\priya\\Desktop\\sem6\\Sweb\\Project\\Submission1_2015073\\groups_final.csv";
    private static final String MappingFile = "C:\\Users\\priya\\Desktop\\sem6\\Sweb\\Project\\Submission1_2015073\\map-Sheet2.csv";
    private static final String PathtoOutput = "C:\\Users\\priya\\Desktop\\sem6\\Sweb\\Project\\Submission1_2015073\\output2.ttl";
    private static final String PathtoTDB = "C:\\Users\\priya\\Desktop\\sem6\\Sweb\\Project\\Submission1_2015073\\";
    private static final String OntURI = "http://www.iiitd.ac.in/meetupgroups" + "#";
    private static final String xsd="http://www.w3.org/2001/XMLSchema#";
    private static final String rdfs="http://www.w3.org/2000/01/rdf-schema#";
    private static final String rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#";
    public static void main(String args[]) throws IOException {


        Reader read = new BufferedReader(new FileReader(PathtoCSV));
        CSVParser parse = new CSVParser(read, CSVFormat.DEFAULT.withFirstRecordAsHeader());
        Reader read2 = new BufferedReader(new FileReader(MappingFile));
        //CSVParser parse2=new CSVParser(read2, CSVFormat.DEFAULT);



        CSVReader csvReader = new CSVReader(read2);
        String[] nextRecord;


        List<String[]> rows = new ArrayList<>();
        // we are going to read data line by line
        while ((nextRecord = csvReader.readNext()) != null) {
            List<String> values = new ArrayList<>();
            for (String value : nextRecord) {
                values.add(value);

                System.out.print(value + "\t");
            }

            rows.add(nextRecord);

            System.out.println();

        }

        String[] column_names = {"group_id", "category_id", "category.name", "category.shortname", "city_id", "city", "country", "created", "description", "group_photo.base_url", "group_photo.highres_link", "group_photo.photo_id", "group_photo.photo_link", "group_photo.thumb_link", "group_photo.type", "join_mode", "link", "members", "group_name", "organizer.member_id", "organizer.name", "rating", "state", "lat", "lon", "urlname", "visibility", "timezone", "utc_offset", "who"};




        Dataset dset = TDBFactory.createDataset(PathtoTDB);
        dset.begin(ReadWrite.WRITE);

        Model mod = dset.getDefaultModel();

        try {
            for (String[] current : rows) {
                if (current[0].equals("Class")) {
                    Resource S = mod.createResource(OntURI + current[1]);
                    Property P = mod.createProperty(rdf + "type");
                    RDFNode O = mod.createResource(rdfs + "Class");

                    mod.add(mod.createStatement(S, P, O));
                    System.out.println(mod.createStatement(S, P, O));
                } else if (current[0] .equals("SubClass")) {
                    Resource S = mod.createResource(OntURI + current[1]);
                    Property P = mod.createProperty(rdfs + "subClassOf");
                    RDFNode O = mod.createResource(OntURI + current[2]);

//                    Resource S1 = mod.createResource(OntURI + current[1]);
//                    Property P1 = mod.createProperty(rdf + "type");
//                    RDFNode O1= mod.createResource(rdfs + "Class");
//
//                    mod.add(mod.createStatement(S1, P1, O1));
//
//                    System.out.println(mod.createStatement(S1, P1, O1));

                    mod.add(mod.createStatement(S, P, O));

                    System.out.println(mod.createStatement(S, P, O));
                } else if (current[0] .equals("Data Property")){
                    Resource S = mod.createResource(OntURI + current[1]);

                    Property P = mod.createProperty(rdf + "type");
                    RDFNode O = mod.createResource(rdf + "Property");


                    mod.add(mod.createStatement(S, P, O));
                    System.out.println(mod.createStatement(S, P, O));

                    Property P1 = mod.createProperty(rdfs + "domain");
                    RDFNode O1 = mod.createResource(OntURI + current[2]);
                    mod.add(mod.createStatement(S, P1, O1));
                    System.out.println(mod.createStatement(S, P1, O1));




                } else if (current[0] .equals("Object Property")) {
                    Resource S = mod.createResource(OntURI + current[1]);

                    Property P = mod.createProperty(rdf + "type");
                    RDFNode O = mod.createResource(rdf + "Property");


                    mod.add(mod.createStatement(S, P, O));
                    System.out.println(mod.createStatement(S, P, O));

                    Property P1 = mod.createProperty(rdfs + "domain");
                    RDFNode O1 = mod.createResource(OntURI + current[2]);
                    mod.add(mod.createStatement(S, P1, O1));
                    System.out.println(mod.createStatement(S, P1, O1));

                    Property P2 = mod.createProperty(rdfs + "range");
                    RDFNode O2 = mod.createResource(OntURI + current[3]);
                    mod.add(mod.createStatement(S, P2, O2));
                    System.out.println(mod.createStatement(S, P2, O2));
                }
            }
            int counter=1;
            for (CSVRecord rec : parse) {
                for (String[] current : rows) {
                    if (current[0] .equals("Data Property")) {
                        Resource S = mod.createResource(OntURI + current[2]+"_"+counter);
                        Property P = mod.createProperty(OntURI+current[1]);

                        String s1=current[4];
                        String uri = " ";

                        {
                            if(s1.equals("xsd:unsignedLong"))
                            {
                                uri="http://www.w3.org/2001/XMLSchema#unsignedLong";
                            }
                            if(s1.equals("rdfs:Literal"))
                            {
                                uri="http://www.w3.org/2000/01/rdf-schema#Literal";
                            }
                            if(s1.equals("xsd:dateTime"))
                            {
                                uri="http://www.w3.org/2001/XMLSchema#dateTime";
                            }
                            if(s1.equals("xsd:anyURI"))
                            {
                                uri="http://www.w3.org/2001/XMLSchema#anyURI";
                            }
                            if(s1.equals("xsd:float"))
                            {
                                uri="http://www.w3.org/2001/XMLSchema#float";
                            }
                            if(s1.equals("xsd:double"))
                            {
                                uri="http://www.w3.org/2001/XMLSchema#double";
                            }
                            if(s1.equals("xsd:int"))
                            {
                                uri="http://www.w3.org/2001/XMLSchema#int";
                            }
                            if(s1.equals("xsd:integer"))
                            {
                                uri="http://www.w3.org/2001/XMLSchema#integer";
                            }

                        }

                        RDFDatatype dtype= new TypeMapper().getSafeTypeByName(uri);
                        Literal L= mod.createTypedLiteral(rec.get(column_names[Integer.parseInt(current[3])-1]),dtype);


                        mod.add(mod.createStatement(S, P, L));
                        System.out.println(mod.createStatement(S, P, L));
                    }
                    if (current[0] .equals("Object Property")) {
                        Resource S = mod.createResource(OntURI + current[2] + "_" + counter);
                        Property P = mod.createProperty(OntURI + current[1]);
                        Resource O=mod.createResource(OntURI+ current[3]+"_"+counter);
                        mod.add(mod.createStatement(S, P, O));
                        System.out.println(mod.createStatement(S, P, O));

                        Property P1=mod.createProperty(rdf+"type");
                        RDFNode O1=mod.createResource(OntURI+current[2]);
                        mod.add(mod.createStatement(S, P1, O1));
                        System.out.println(mod.createStatement(S, P1, O1));

                        Property P2=mod.createProperty(rdf+"type");
                        RDFNode O2=mod.createResource(OntURI+current[3]);
                        mod.add(mod.createStatement(O, P2, O2));
                        System.out.println(mod.createStatement(O,P2,O2));


                    }









//

                }
                counter++;
                if(counter>10000)
                {
                    break;
                }
            }


            //mod.write(System.out);
            Reasoner reasoner = ReasonerRegistry.getRDFSReasoner();
            InfModel infmodel = ModelFactory.createInfModel(reasoner,mod);
//}
            FileWriter o1 = new FileWriter(PathtoOutput);

            RDFDataMgr.write(o1, infmodel, RDFFormat.TURTLE);



        /*try (QueryExecution query = QueryExecutionFactory.create("SELECT ?s ?p ?o WHERE { ?s ?p ?o } LIMIT 10", dset)) {
            ResultSet reset = query.execSelect();
            ResultSetFormatter.out(reset);
        }*/
            dset.commit();
            dset.begin(ReadWrite.READ) ;




//            String qs = "SELECT ?s ?p1 ?o1 ?p2 ?o2 ?p3 ?o3 { ?s ?p1 ?o1;?p2 ?o2;?p3 ?o3. } GROUP BY ?s ?p1 ?o1 ?p2 ?o2 ?p3 ?o3  LIMIT 10" ;
//            Query query = QueryFactory.create(qs) ;
//            QueryExecution qexec = QueryExecutionFactory.create(query, dsg) ;
//            ResultSet rs = qexec.execSelect() ;
//            ResultSetFormatter.out(rs);
          /*  Dataset dataset = TDBFactory.createDataset(PathtoTDB);
            Model tdb = dataset.getDefaultModel();
           // FileManager.get().readModel( tdb, PathtoOutput, "N-TRIPLES" );
            dataset.begin(ReadWrite.READ);
            String q = "SELECT ?s ?p1 ?o1 ?p2 ?o2 ?p3 ?o3 { ?s ?p1 ?o1;?p2 ?o2;?p3 ?o3. } GROUP BY ?s ?p1 ?o1 ?p2 ?o2 ?p3 ?o3  LIMIT 10";
            Query query1 = QueryFactory.create(q);
            QueryExecution qexec1 = QueryExecutionFactory.create(query1, tdb);
            ResultSet results = qexec1.execSelect();
            ResultSetFormatter.out(results);*/


////           Query query = QueryFactory.create("SELECT ?s ?p1 ?o1 ?p2 ?o2 ?p3 ?o3 { ?s ?p1 ?o1;?p2 ?o2;?p3 ?o3. } GROUP BY ?s ?p1 ?o1 ?p2 ?o2 ?p3 ?o3  LIMIT 10");
//            Dataset ds = DatasetFactory.createTxnMem();
//               FusekiServer server = FusekiServer.create()
//                    .setPort(3330)
//                    .add("/rdf", ds);
//                  .build() ;
//            server.start() ;
            //System.out.println("hello");
//            DatasetGraphTDB dsg = DatasetBuilderStd.create(Location.mem()) ;
//           TDBLoader.load(dsg, PathtoOutput, true) ;
//           // TDBLoader.load(dsg,"http://localhost:3030/ds",true);
//            QueryExecution qe = QueryExecutionFactory.create("SELECT ?s ?p1 ?o1 ?p2 ?o2 ?p3 ?o3 { ?s ?p1 ?o1;?p2 ?o2;?p3 ?o3. } GROUP BY ?s ?p1 ?o1 ?p2 ?o2 ?p3 ?o3  LIMIT 10",ds);
//            ResultSet results = qe.execSelect();
//            ResultSetFormatter.out(System.out, results);
//            qe.close();
            // server.stop();
//            RDFConnection conn = RDFConnectionFactory.connect(ds);
//
//            Txn.executeWrite(conn, () ->{
//                System.out.println("Load a file");
//                conn.load(PathtoTDB+"output1.ttl");
//                //conn.load("http://example/g0", "data.ttl");
//                System.out.println("In write transaction");
//                conn.queryResultSet(query, ResultSetFormatter::out);
//            });
//            // And again - implicit READ transaction.
//            System.out.println("After write transaction");
//            conn.queryResultSet(query, ResultSetFormatter::out);



        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            dset.end();

        }

    }
}

