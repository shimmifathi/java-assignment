package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/gmail")
public class GmailController {

    private final GmailService gmailService;

    @Autowired
    public GmailController(GmailService gmailService) {
        this.gmailService = gmailService;
    }

    @GetMapping("/search")
    public List<String> searchEmails(@RequestParam("keyword") String keyword) throws IOException {
        return gmailService.searchEmails(keyword);
    }
}
