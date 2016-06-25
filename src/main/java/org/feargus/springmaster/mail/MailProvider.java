package org.feargus.springmaster.mail;

interface MailProvider {
    public void sendMail(String to, String from, String subject, String body) throws Exception;
}
