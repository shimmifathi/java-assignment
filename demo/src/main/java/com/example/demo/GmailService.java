package com.example.demo;

import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.ListMessagesResponse;
import com.google.api.services.gmail.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class GmailService {

    private final Gmail gmail;

    @Autowired
    public GmailService(Gmail gmail) {
        this.gmail = gmail;
    }

    // Method to search for emails based on a keyword and retrieve email subjects
    public List<String> searchEmails(String keyword) throws IOException {
        List<String> emailSubjects = new ArrayList<>();

        // Use the Gmail API to search for emails
        ListMessagesResponse response = gmail.users().messages()
                .list("me")
                .setQ(keyword)
                .execute();

        // Iterate through the list of messages and retrieve email subjects
        for (Message message : response.getMessages()) {
            Message email = gmail.users().messages().get("me", message.getId()).execute();
            String subject = getEmailSubject(email);
            emailSubjects.add(subject);
        }

        return emailSubjects;
    }

    // Helper method to retrieve the email subject from a Gmail message
    private String getEmailSubject(Message message) {
        for (com.google.api.services.gmail.model.MessagePartHeader header : message.getPayload().getHeaders()) {
            if ("Subject".equals(header.getName())) {
                return header.getValue();
            }
        }
        return "No subject";
    }
}
