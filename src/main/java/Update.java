import com.mongodb.client.MongoClients;
import com.mongodb.MongoException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;

import org.bson.Document;
import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Updates.*;

import java.util.ArrayList;

public class Update {

    public static void main(String[] args) {

        // Connect to MongoDB
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017/?readPreference=primary&ssl=false&directConnection=true");

        // Connect to the database
        MongoDatabase database = mongoClient.getDatabase("local");

        System.out.println("Connected to the database successfully");

        MongoCollection<Document> collection = database.getCollection("csci4140");

        // Update one
        try {
            collection.updateOne(eq("name", "John Doe"), set("age", 30));
        } catch (MongoException e) {
            System.err.println("An error occurred while trying to perform a MongoDB operation");
            e.printStackTrace();
        }
        // Update spouse
        try {
            collection.updateOne(eq("name", "John Doe"), set("spouse.name", "Jennifer S. Doe"));
        } catch (MongoException e) {
            System.err.println("An error occurred while trying to perform a MongoDB operation");
            e.printStackTrace();
        }
        // Update many
        try {
            collection.updateMany(lt("age", 30), inc("age", 1));
        } catch (MongoException e) {
            System.err.println("An error occurred while trying to perform a MongoDB operation");
            e.printStackTrace();
        }
    }
}
