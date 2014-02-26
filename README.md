# Build a Heroku App to support importing a Bundle from Google Drive into SFDC- CLOUDSPOKES CHALL 3327

This is the submission for Challenge Build a Heroku App to support importing a Bundle from Google Drive into SFDC

This provides a simple rest service that takes file id as parameter and downloads the contents of that file google drive

The service can be accessed /services/downloadfile/{fileId}
    
## Running the application locally

First build with:

    $mvn clean install

Then run it with:

    $ java -cp target/classes:target/dependency/* com.example.Main

