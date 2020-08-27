# jdt-maker

v1.0.1-beta.1

---

## About

jdt-maker is a GUI Java app which purpose is to input informations about the work
you've done in specific time-spans. All the informations is then well-formatted
into a MarkDown file, converted into a .pdf file, and sent via a mail.

---

## What are the buttons and fields ?

### Date

Auto-filled by default, this will be the name of the document and the date shown in the title.

### Start Time

This is the time you began to do an action.

### End Time

This is the time you finished to do an action.

### Action Text Field

This is the description of your action, type what you did there (MarkDown formatting will be shown in the final result).

### Prev Button

This is to go to the previous action in order to edit it for instance. It will also save all the modification you did on the action you were before clicking the button.

### Next Button

This is to create a new action or naviguate to the next action. It will also save all the modification you did on the action you were before clicking the button.

### Finish Button

Creates the .md file with all your actions formatted into MarkDown, Converts it to PDF, and sends it via email to recipient.

### Recipient Field

Input the mail address you want to send your .pdf file.

## How does sending your mail work ?
**(ONLY WORKS ON LINUX AND OSX FOR NOW)**

- sudo apt install mailutils
- sudo apt install msmtp msmtp-mta
- touch ~/.msmtprc
- chmod 600 ~/.msmtprc

- nano ~/.msmtprc

**Copy paste the following :**

```
# Default values for all accounts
defaults
auth           on
tls            on
tls_starttls   on
tls_trust_file /etc/ssl/certs/ca-certificates.crt
logfile        ~/.msmtp.log

# Example of a setup for a gmail account
account        gmail
auth           plain
host           smtp.gmail.com
port           587
from           example@gmail.com
user           example@gmail.com
password       password

# Define a default account
account default : gmail
```
- Replace `from` and `user` field with your gmail account.
- Go to your [google account security page](https://myaccount.google.com/security)
- Activate double factor authentification
- A new option will appear : "Application Passwords", click on it
- Create a new application password and copy it
- Go back into ~/.msmtprc
- Replace the `password` field with your application password

**You should be now good to go !**

## How do I run it ?
.jar files will be given later in order to execute it easily. For the moment, you either have to run it in netbeans or a similar IDE, or compile the code into a .jar file.
