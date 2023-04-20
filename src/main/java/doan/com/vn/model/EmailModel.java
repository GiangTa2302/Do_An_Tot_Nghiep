package doan.com.vn.model;

import lombok.Data;

@Data
public class EmailModel {
    private String fromEmail;
    private String subject;
    private String body;

    public EmailModel() {
        super();
    }

    public EmailModel(String fromEmail, String subject, String body) {
        super();
        this.fromEmail = fromEmail;
        this.subject = subject;
        this.body = body;
    }

}
