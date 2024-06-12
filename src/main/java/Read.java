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
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017/?readPreference=primary&ssl=false&directConnection=true");

        // Connect to the database
        MongoDatabase database = mongoClient.getDatabase("local");

        System.out.println("Connected to the database successfully");

        MongoCollection<Document> collection = database.getCollection("csci4140");

        //  focusing on reading data, or as we like to say, checking out who’s at the party!
        // Imagine you're trying to find That's what this line of code does: collection.find(eq("name", "John Doe")).first();. We're asking MongoDB to find the first person named John Doe at the party. If we find him, we shout out his details with System.out.println(...). But if something goes wrong, like we spill our drink while asking around (a.k.a an exception), we handle it gracefully, apologize, and clean up the mess (catch the exception and print an error message).
        try {
            Document myDoc = collection.find(eq("name", "John Doe")).first();
            System.out.println("Read name:" + myDoc.toJson());
        } catch (MongoException e) {
            System.err.println("An error occurred while trying to perform a MongoDB operation");
            e.printStackTrace();
        }
        // Next, we're finding John Doe by his spouse name Jennifer Doe. So, collection.find(eq("spouse.name", "Jennifer Doe")).first(); is like asking, "Who here is married to Jennifer Doe?" If we find them, we announce it, and if there’s a hiccup, we handle it with charm and grace (using our try-catch again).
        try {
            Document myDoc = collection.find(eq("spouse.name", "Jennifer Doe")).first();
            System.out.println("Read spouse name:" + myDoc.toJson());
        } catch (MongoException e) {
            System.err.println("An error occurred while trying to perform a MongoDB operation");
            e.printStackTrace();
        }
        // Now, we're getting a bit nosy and want to know who’s over 25. With collection.find(gt("age", 25));, we're mingling and asking, "How old are you?" to each guest. If they’re over 25, we chat and learn more about them, printing out their details. And of course, if we trip while mingling (encounter an exception), we laugh it off, apologize, and keep the party going (catch the exception and print an error message).
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
