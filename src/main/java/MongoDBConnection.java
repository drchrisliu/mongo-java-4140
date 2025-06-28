import com.mongodb.MongoException;
import com.mongodb.client.*;
import org.bson.Document;

public class MongoDBConnection {

    public static void main(String[] args) {
        try (MongoClient mongoClient = MongoClients.create(
                "mongodb://localhost:27017/?readPreference=primary&ssl=false&directConnection=true")) {

            System.out.println("✅ Connected to MongoDB successfully.");

            // List all databases
            for (String dbName : mongoClient.listDatabaseNames()) {
                System.out.println("\n📂 Database: " + dbName);
                MongoDatabase database = mongoClient.getDatabase(dbName);

                // List collections in each database
                for (String collectionName : database.listCollectionNames()) {
                    System.out.println("  📄 Collection: " + collectionName);
                    MongoCollection<Document> collection = database.getCollection(collectionName);

                    // Show up to 10 documents
                    try (MongoCursor<Document> cursor = collection.find().limit(10).iterator()) {
                        int count = 0;
                        while (cursor.hasNext()) {
                            Document doc = cursor.next();
                            System.out.println("    📑 Document " + (++count) + ": " + doc.toJson());
                        }
                        if (count == 10) {
                            System.out.println("    ... (showing first 10 documents only)");
                        } else if (count == 0) {
                            System.out.println("    (no documents)");
                        }
                    } catch (MongoException e) {
                        System.err.println("    ⚠️ Failed to read documents from collection: " + collectionName);
                        e.printStackTrace();
                    }
                }
            }

        } catch (MongoException e) {
            System.err.println("❌ An error occurred while trying to perform a MongoDB operation:");
            e.printStackTrace();
        }
    }
}