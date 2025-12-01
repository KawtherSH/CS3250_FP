import java.util.function.Consumer;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Overlay {


    public static void showRiddle(Stage owner, String paperImagePath, String riddleText) {

        // Image View
        ImageView paper = new ImageView(loadImage(paperImagePath));
        paper.setFitWidth(560);
        paper.setFitHeight(740);
        paper.setPreserveRatio(true);

        // Text
        Label text = new Label(riddleText);
        text.setWrapText(true);
        text.setAlignment(Pos.CENTER);
        text.setMaxWidth(paper.getFitWidth() * 0.7); // Fit text
        
        // StackPane for text in top of the image
        StackPane riddleContent = new StackPane(paper, text);
        riddleContent.setAlignment(Pos.CENTER);
        
        // Button for when finish reading.
        Button close = new Button("Got it!");
        close.setDefaultButton(true);
        close.setOnAction(e -> ((Stage) close.getScene().getWindow()).close()); // close riddle

        // VBox to hold content
        VBox card = new VBox(20, riddleContent, close);
        card.setAlignment(Pos.CENTER);
        card.setPadding(new Insets(20));

        StackPane backdrop = new StackPane(card);
        backdrop.setPadding(new Insets(20));

        Stage dialog = new Stage();
        if (owner != null) dialog.initOwner(owner);
        dialog.initModality(Modality.WINDOW_MODAL);
        dialog.setTitle("Letter");
        dialog.setScene(new Scene(backdrop, 600, 650));
        dialog.showAndWait();
    }
    
    
    // Lock Overlay
    public static void showLock(Stage owner, Consumer<String> onSubmit) {


        Label prompt = new Label("Enter the code to unlock:");
        PasswordField code = new PasswordField();

        Label feedback = new Label();

        Button ok = new Button("Unlock");
        Button cancel = new Button("Cancel");

        ok.setDefaultButton(true);
        cancel.setCancelButton(true);

        ok.setOnAction(e -> {
            // The stage/dialog to close
            Stage dialogToClose = (Stage) ok.getScene().getWindow();

            String entered = code.getText() == null ? "" : code.getText().trim();
            if (entered.isEmpty()) {
                feedback.setText("Please enter a code.");
                code.requestFocus();
                return;
            }
            onSubmit.accept(entered);
            dialogToClose.close();
        });

        cancel.setOnAction(e -> {
            ((Stage) cancel.getScene().getWindow()).close();
        });

        VBox card = new VBox(12, prompt, code, feedback, ok, cancel);
        card.setAlignment(Pos.CENTER);
        card.setPadding(new Insets(20));

        StackPane root = new StackPane(card);
        root.setPadding(new Insets(20));

        Stage dialog = new Stage();
        dialog.initOwner(owner);
        dialog.initModality(Modality.WINDOW_MODAL);
        dialog.setTitle("Locked");
        dialog.setScene(new Scene(root, 360, 300));
        dialog.showAndWait();
    }
    
    
    
    
    // Helper methods
    
    private static Image loadImage(String path) {
        try {
            var is = Overlay.class.getResourceAsStream("/" + path);
            if (is != null) return new Image(is);
        } catch (Exception ignore) {
        }
        return new Image(path);
    }
}