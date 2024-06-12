import com.mongodb.client.MongoClients;
import com.mongodb.MongoException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;

public class MongoDBConnection {

    // Inside our main method, the magic begins:
    public static void main(String[] args) {

        try {
            // Making the Connection: We're dialing up MongoDB with: MongoClients.create(...). It's like calling up a friend and saying, "Hey, MongoDB! Let's hang out at my localhost!"
            MongoClient mongoClient = MongoClients
                    .create("mongodb://localhost:27017/?readPreference=primary&ssl=false&directConnection=true");

            // Entering the Database Lounge: We're stepping into the "chris" database with: mongoClient.getDatabase("chris"). Imagine walking into your favorite lounge and getting comfy.
            MongoDatabase database = mongoClient.getDatabase("local");

            System.out.println("Connected to the database successfully");

        } catch (MongoException e) {
            System.err.println("An error occurred while trying to perform a MongoDB operation");
            e.printStackTrace();
        }
    }
}