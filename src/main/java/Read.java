import com.mongodb.client.MongoClients;
import com.mongodb.MongoException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;

import org.bson.Document;
import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Updates.*;

import java.util.ArrayList;

public class Read {

    public static void main(String[] args) {

        // Connect to MongoDB
        MongoClient mongoClient = MongoClients.create("mongodb://csid:dbpassword@db.cs.dal.ca:27017/?authSource=csid");

        // Connect to the database
        MongoDatabase database = mongoClient.getDatabase("csid");

        System.out.println("Connected to the database successfully");

        MongoCollection<Document> collection = database.getCollection("csci4140");
        // Read
        try {
            Document myDoc = collection.find(eq("name", "John Doe")).first();
            System.out.println("Read name:" + myDoc.toJson());
        } catch (MongoException e) {
            System.err.println("An error occurred while trying to perform a MongoDB operation");
            e.printStackTrace();
        }
        // Read spouse name
        try {
            Document myDoc = collection.find(eq("spouse.name", "Jennifer Doe")).first();
            System.out.println("Read spouse name:" + myDoc.toJson());
        } catch (MongoException e) {
            System.err.println("An error occurred while trying to perform a MongoDB operation");
            e.printStackTrace();
        }
        // Read documents
        FindIterable<Document> myDocs = collection.find(gt("age", 25));
        try {
            for (Document doc : myDocs) {
                System.out.println("Read documents:" + doc.toJson());
            }
        } catch (MongoException e) {
            System.err.println("An error occurred while trying to perform a MongoDB operation");
            e.printStackTrace();
        }
    }
}
