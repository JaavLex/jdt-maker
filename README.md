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

### Save Button

Not working now, but normally saves without having to click prev or next.

### Finish Button

Creates the .md file with all your actions formatted into MarkDown, Converts it to PDF, and sends it via email to recipient.

### Recipient Field

Input the mail address you want to send your .pdf file.

## How does sending your mail work ?
(WIP)

## How do I run it ?
.jar files will be given later in order to execute it easily. For the moment, you either have to run it in netbeans or a similar IDE, or compile the code into a .jar file.
