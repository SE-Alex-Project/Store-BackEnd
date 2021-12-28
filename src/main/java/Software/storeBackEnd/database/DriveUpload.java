package Software.storeBackEnd.database;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DriveUpload {
    private static final String APPLICATION_NAME = "Google Drive API";
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "tokens";
    

    private static final List<String> SCOPES = Collections.singletonList(DriveScopes.DRIVE_FILE);
    private static final String CREDENTIALS_FILE_PATH = "/client_secret.json";
    private static final String PARENT_FOLDER = "1kJ_H1Hn8bjYTuASAUZNZgsZ6npjFFZ3R";
    private Drive driveService;

    private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
        // Load client secrets.
        InputStream in = DriveUpload.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        if (in == null) {
            throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
        }
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));
        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        Credential credential = new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
        //returns an authorized Credential object.
        return credential;
    }
    
    public static Drive initDriveService() throws GeneralSecurityException, IOException {
        // Build a new authorized API client service.
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Drive service = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();
        return service;
    }
    
    public String[] uploadFiles(FileToUpload[] files) throws GeneralSecurityException, IOException {
    	// Build a new authorized API client service.
        if (driveService == null) 
        	driveService = initDriveService();
        
        String[] ids = new String[files.length];
        
        int i = 0;
        for (FileToUpload fileInfo : files) {
            File fileMetadata = new File();
            fileMetadata.setName(fileInfo.name);
            fileMetadata.setParents(Arrays.asList(PARENT_FOLDER));
            FileContent mediaContent = new FileContent(fileInfo.content_type, fileInfo.file);
            File file = driveService.files().create(fileMetadata, mediaContent)
                .setFields("id")
                .execute();
            
            ids[i++] = file.getId();
        }
        
        System.out.println(Arrays.asList(ids));
    	return ids;
    }
    
    public static void main(String... args) throws IOException, GeneralSecurityException {
    	FileToUpload[] files = new FileToUpload[1];
    	files[0] = new FileToUpload();
    	files[0].file = new java.io.File("photo.png");
    	files[0].name = "photo.png";
    	files[0].content_type = "image/png";
    	
    	DriveUpload drive = new DriveUpload();
    	drive.uploadFiles(files);
    }
}
