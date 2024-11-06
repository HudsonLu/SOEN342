package User;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ClientTest {

    private Client client;

    @Before
    public void setUp() {
        client = new Client("Alice", "123-456-7890");
    }

    @Test
    public void testClientInitialization() {
        assertEquals("Alice", client.getName());
        assertEquals("123-456-7890", client.getPhoneNumber());
    }

    @Test
    public void testUserIdAutoIncrement() {
        // Create first Client instance
        Client client1 = new Client("Alice", "123-456-7890");
        long id1 = client1.getUser_id();

        // Create second Client instance
        Client client2 = new Client("Bob", "234-567-8901");
        long id2 = client2.getUser_id();

        // Create third Client instance
        Client client3 = new Client("Charlie", "345-678-9012");
        long id3 = client3.getUser_id();

        // Check that each client has a unique, incrementing user_id
        assertEquals(id1 + 1, id2);
        assertEquals(id2 + 1, id3);

        // Ensure IDs are unique and incrementing
        assertTrue(id1 < id2 && id2 < id3);
        System.out.println(id1);
        System.out.println(id2);
        System.out.println(id3);
    }

}
