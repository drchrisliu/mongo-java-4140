import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;

import org.bson.Document;
import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Updates.*;

import java.util.ArrayList;

public class CRUD {

    public static void main(String[] args) {

        // Connect to MongoDB
        MongoClient mongoClient = MongoClients.create("mongodb://csid:dbpassword@db.cs.dal.ca:27017/?authSource=csid");

        // Connect to the database
        MongoDatabase database = mongoClient.getDatabase("csid");

        System.out.println("Connected to the database successfully");

        // Create a collection
        if (!database.listCollectionNames().into(new ArrayList<String>()).contains("csci4140")) {
            database.createCollection("csci4140");
        }

        MongoCollection<Document> collection = database.getCollection("csci4140");

        // Create
        Document doc = new Document("name", "John Doe")
                .append("age", 25)
                .append("email", "john@example.com");
        collection.insertOne(doc);

        // // Read
        // Document myDoc = collection.find(eq("name", "John Doe")).first();
        // System.out.println(myDoc.toJson());

        // // Update
        // collection.updateOne(eq("name", "John Doe"), set("age", 30));

        // // Delete
        // collection.deleteOne(eq("name", "John Doe"));
    }
}
