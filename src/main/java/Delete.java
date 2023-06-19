import com.mongodb.client.MongoClients;
import com.mongodb.MongoException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;

import org.bson.Document;
import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Updates.*;

import java.util.ArrayList;

public class Delete {

    public static void main(String[] args) {

        // Connect to MongoDB
        MongoClient mongoClient = MongoClients.create("mongodb://csid:dbpassword@db.cs.dal.ca:27017/?authSource=csid");

        // Connect to the database
        MongoDatabase database = mongoClient.getDatabase("csid");

        System.out.println("Connected to the database successfully");

        MongoCollection<Document> collection = database.getCollection("csci4140");

        // Delete one
        try {
            collection.deleteOne(eq("name", "John Doe"));
        } catch (MongoException e) {
            System.err.println("An error occurred while trying to perform a MongoDB operation");
            e.printStackTrace();
        }
        // Delete many
        try {
            collection.deleteMany(lt("age", 30));
        } catch (MongoException e) {
            System.err.println("An error occurred while trying to perform a MongoDB operation");
            e.printStackTrace();
        }
    }
}
