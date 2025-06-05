package Service;

import java.util.HashSet;
import java.util.Set;

public class EmailService {
    private Set<String> sentEmails = new HashSet<>();
    
    public boolean sendEmail(String email, String subject, String body) {
        if (email != null && !email.isEmpty()) {
            sentEmails.add(email);
            return true;
        }
        return false;
    }
    
    public Set<String> getSentEmails() {
        return new HashSet<>(sentEmails);
    }
}