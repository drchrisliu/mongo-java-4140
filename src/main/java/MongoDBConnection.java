import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;

public class MongoDBConnection {

    public static void main(String[] args) {

        // Connect to MongoDB
        MongoClient mongoClient = MongoClients.create("mongodb://csid:dbpassword@db.cs.dal.ca:27017");

        // Connect to the database
        MongoDatabase database = mongoClient.getDatabase("csid");

        System.out.println("Connected to the database successfully");
    }
}