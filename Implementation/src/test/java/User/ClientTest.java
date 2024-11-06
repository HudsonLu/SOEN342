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
}
