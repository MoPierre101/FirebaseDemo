package aydin.firebasedemo;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class TertiaryControlller {
   @FXML
   private Button registerButton;
   @FXML
   private TextField nameField;
   @FXML
   private TextField emailField;
   @FXML
   private PasswordField passwordField;
   @FXML
   private Label statusLabel;




    public boolean registerUser(ActionEvent event) throws IOException {
        UserRecord.CreateRequest request = new UserRecord.CreateRequest();
                request.setEmail(emailField.getText());
               // .setEmailVerified(false);
                request.setPassword(passwordField.getText());
                //.setPhoneNumber("+11234567890");
                //.setDisplayName(nameField.getText());
               // .setDisabled(false);

        UserRecord userRecord;
        try {
            userRecord = DemoApp.fauth.createUser(request);
            System.out.println("Successfully created new user with Firebase Uid: " + userRecord.getUid()
                    + " check Firebase > Authentication > Users tab");
            return true;

        } catch (FirebaseAuthException ex) {
            // Logger.getLogger(FirestoreContext.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error creating a new user in the firebase");
            return false;
        }

    }

    public void loginUser(ActionEvent event) throws IOException {
            String email = emailField.getText();
            String password = passwordField.getText();

            try {
                UserRecord userRecord = FirebaseAuth.getInstance().getUserByEmail(email);

                // In a real-world scenario, you would verify the password here
                // For simplicity, we're just checking if the user exists

                statusLabel.setText("Login successful!");
                Parent homeParent = FXMLLoader.load(getClass().getResource("primary.fxml"));
                Scene signUpScene = new Scene(homeParent);

                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

                window.setScene(signUpScene);
                window.centerOnScreen();
                window.show();

            } catch (FirebaseAuthException e) {
                statusLabel.setText("Login failed");
            }
    }
}
