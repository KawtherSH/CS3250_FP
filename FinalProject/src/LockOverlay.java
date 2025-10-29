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

public class LockOverlay {

    public static void show(Stage owner,
                            String lockImagePath,
                            Consumer<String> onSubmit) {

        ImageView icon = new ImageView(new Image(lockImagePath));
        icon.setFitWidth(96);
        icon.setFitHeight(96);

        Label prompt = new Label("Enter the code to unlock:");
        PasswordField code = new PasswordField();

        Label feedback = new Label();
        feedback.setStyle("-fx-text-fill: firebrick; -fx-font-size: 12px;");

        Button ok = new Button("Unlock");
        Button cancel = new Button("Cancel");

        ok.setDefaultButton(true);
        cancel.setCancelButton(true);

        ok.setOnAction(e -> {
            String entered = code.getText() == null ? "" : code.getText().trim();
            if (entered.isEmpty()) {
                feedback.setText("Please enter a code.");
                code.requestFocus();
                return;
            }
            onSubmit.accept(entered);
            ((Stage) ok.getScene().getWindow()).close();
        });

        cancel.setOnAction(e -> {
            ((Stage) cancel.getScene().getWindow()).close();
        });

        VBox card = new VBox(12, icon, prompt, code, feedback, ok, cancel);
        card.setAlignment(Pos.CENTER);
        card.setPadding(new Insets(20));
        card.setStyle("-fx-background-color: white; -fx-border-color: #333; -fx-border-radius: 12; -fx-background-radius: 12;");

        StackPane root = new StackPane(card);
        root.setPadding(new Insets(20));
        root.setStyle("-fx-background-color: rgba(0,0,0,0.4);");

        Stage dialog = new Stage();
        dialog.initOwner(owner);
        dialog.initModality(Modality.WINDOW_MODAL);
        dialog.setTitle("Locked");
        dialog.setScene(new Scene(root, 360, 300));
        dialog.showAndWait();
    }
}
