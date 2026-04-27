import java.util.HashMap;
import java.util.Map;

// 1. The Strategy Interface
interface NotificationProvider {
    void send(String recipient, String message);
    String getProviderName();
}

// 2. Concrete Implementations
class EmailProvider implements NotificationProvider {
    public void send(String recipient, String message) {
        System.out.println("Sending Email to " + recipient + ": " + message);
    }
    public String getProviderName() { return "Email"; }
}

class SmsProvider implements NotificationProvider {
    public void send(String recipient, String message) {
        System.out.println("Sending SMS to " + recipient + ": " + message);
    }
    public String getProviderName() { return "SMS"; }
}

// 3. The Singleton Manager
class NotificationManager {
    private final Map<String, NotificationProvider> providers = new HashMap<>();

    private NotificationManager() {}

    private static class Holder {
        private static final NotificationManager INSTANCE = new NotificationManager();
    }

    public static NotificationManager getInstance() {
        return Holder.INSTANCE;
    }

    public void registerProvider(NotificationProvider provider) {
        providers.put(provider.getProviderName().toLowerCase(), provider);
    }

    public void notify(String providerName, String recipient, String message) {
        NotificationProvider provider = providers.get(providerName.toLowerCase());
        if (provider != null) {
            provider.send(recipient, message);
        } else {
            System.out.println("Error: Provider [" + providerName + "] not found.");
        }
    }
}

// 4. The Entry Point
public class Main {
    public static void main(String[] args) {
        NotificationManager manager = NotificationManager.getInstance();

        manager.registerProvider(new EmailProvider());
        manager.registerProvider(new SmsProvider());

        manager.notify("Email", "dev@example.com", "Hello via Singleton!");
        manager.notify("SMS", "+123456789", "Your OTP is 1234");
    }
}