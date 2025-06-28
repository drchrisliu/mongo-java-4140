import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.mongodb.MongoException;
import com.mongodb.client.*;
import org.bson.Document;

import java.io.Console;
import java.util.Properties;

public class MongoSSHExplorer {

    public static void main(String[] args) {
        Session session = null;

        try {
            // Read SSH password from terminal (masked)
            Console console = System.console();
            if (console == null) {
                System.err.println("No console available. Run from a terminal, not inside an IDE.");
                System.exit(1);
            }

            char[] sshPasswordChars = console.readPassword("Enter SSH password for chris@timberlea.cs.dal.ca: ");
            String sshPassword = new String(sshPasswordChars);

            // Set up SSH tunnel
            JSch jsch = new JSch();
            session = jsch.getSession("chris", "timberlea.cs.dal.ca", 22);
            session.setPassword(sshPassword);

            Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);

            session.connect();
            System.out.println("✅ SSH Tunnel established.");

            // Port forward from localhost:27018 to db.cs.dal.ca:27017
            int localPort = 27018;
            session.setPortForwardingL(localPort, "db.cs.dal.ca", 27017);

            // MongoDB URI
            String mongoUri = "mongodb://gang:B00415613@localhost:" + localPort + "/?authSource=gang";

            // Connect to MongoDB
            try (MongoClient mongoClient = MongoClients.create(mongoUri)) {
                System.out.println("✅ Connected to MongoDB.");

                // List all databases
                MongoIterable<String> databases = mongoClient.listDatabaseNames();
                for (String dbName : databases) {
                    System.out.println("\n📂 Database: " + dbName);
                    MongoDatabase db = mongoClient.getDatabase(dbName);

                    // List all collections in the database
                    for (String collectionName : db.listCollectionNames()) {
                        System.out.println("  📄 Collection: " + collectionName);
                        MongoCollection<Document> collection = db.getCollection(collectionName);

                        // Print all documents in the collection
                        try (MongoCursor<Document> cursor = collection.find().iterator()) {
                            int docCount = 0;
                            while (cursor.hasNext()) {
                                Document doc = cursor.next();
                                System.out.println("    📑 Document: " + doc.toJson());
                                docCount++;
                                if (docCount >= 10) { // Optional limit for display
                                    System.out.println("    ... (showing first 10 documents)");
                                    break;
                                }
                            }
                        } catch (MongoException e) {
                            System.err.println("    ⚠️ Failed to read documents from " + collectionName);
                            e.printStackTrace();
                        }
                    }
                }

            }

        } catch (MongoException e) {
            System.err.println("❌ MongoDB operation failed:");
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("❌ SSH or tunnel setup failed:");
            e.printStackTrace();
        } finally {
            if (session != null && session.isConnected()) {
                session.disconnect();
                System.out.println("\n🔒 SSH session closed.");
            }
        }
    }
}