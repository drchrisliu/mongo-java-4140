import com.mongodb.client.MongoClients;
import com.mongodb.MongoException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;

import org.bson.Document;
import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Updates.*;

import java.util.ArrayList;

public class Create {

    public static void main(String[] args) {

        // make a connection just like mongodbconnection class does...
        // Connect to MongoDB
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017/?readPreference=primary&ssl=false&directConnection=true");

        // Connect to the database
        MongoDatabase database = mongoClient.getDatabase("local");

        System.out.println("Connected to the database successfully");

        // Setting Up the Party Room: We're checking if we already have a room (or collection) named "csci4140". If not, we're setting one up with: database.createCollection("csci4140").
        // Create a collection
        if (!database.listCollectionNames().into(new ArrayList<String>()).contains("csci4140")) {
            database.createCollection("csci4140");
        }

        MongoCollection<Document> collection = database.getCollection("csci4140");

        // Inviting Our First Guest: We're creating a guest list, and our first guest is "John Doe". We've got all his details down in a neat little card (or Document). And then, we're inviting him in with: collection.insertOne(doc). But hey, sometimes things don't go as planned. So, we've got a safety net (a try-catch) just in case there's a hiccup.
        Document doc = new Document("name", "John Doe")
                .append("age", 25)
                .append("email", "john@example.com")
                .append("spouse", new Document("name", "Jennifer Doe"));
        // add try catch common exception of insertOne for folowing code
        try {
            collection.insertOne(doc);
        } catch (MongoException e) {
            System.err.println("An error occurred while trying to perform a MongoDB operation");
            e.printStackTrace();
        }
        System.out.println("Insert a single document to the database successfully");

        // The Bigger Party: Now, we're thinking bigger! We're inviting a bunch of friends over. "Jane Smith" and "Bob Jones" are on the list. We're adding them to our bigger guest list (ArrayList) and then inviting them all in with: collection.insertMany(documents). Again, we've got that safety net in place, just in case.
        ArrayList<Document> documents = new ArrayList<Document>();
        documents.add(new Document("name", "Jane Smith")
                .append("age", 28)
                .append("email", "jane@example.com"));
        documents.add(new Document("name", "Bob Jones")
                .append("age", 30)
                .append("email", "bob@example.com"));
        try {
            collection.insertMany(documents);
        } catch (MongoException e) {
            System.err.println("An error occurred while trying to perform a MongoDB operation");
            e.printStackTrace();
        }
        System.out.println("Insert a many documents to the database successfully");

    }
}
