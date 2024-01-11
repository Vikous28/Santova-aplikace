package com.example.pepe;

import com.example.pepe.filters.*;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static com.example.pepe.ImageUtils.LoadImage;
import static com.example.pepe.ImageUtils.makeColoredImage;

public class ImageController {

    @FXML
    private SplitPane mainSplitPane;

    @FXML
    private AnchorPane rightMainAnchorPane;

    @FXML private RadioButton modifiedImageRadioButton;
    @FXML RadioButton originalImageRadioButton;

    private final History history = new History();

    private MyImage myImage;

    @FXML
    private ImageView imageView;


    @FXML
    private void initialize() {
        mainSplitPane.setDividerPositions(0.7);
        rightMainAnchorPane.setMinWidth(275);
    }

    @FXML
    public void exitMenuClick(ActionEvent event) { Platform.exit(); }

    @FXML
    private void browseImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif", "*.bmp", "*.jpeg")
        );
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {

            String imagePath = selectedFile.getAbsolutePath();
            myImage = new MyImage(LoadImage(imagePath), null, imagePath);
            history.save(imagePath);
            imagePath = selectedFile.getAbsolutePath();
            displayImage(imagePath);
        }
    }

    @FXML
    private void saveImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif", "*.bmp", "*.jpeg")
        );
        File selectedFile = fileChooser.showSaveDialog(null);
        if (selectedFile != null) {
            try {
                ImageIO.write(SwingFXUtils.fromFXImage(imageView.getImage(), null), "png", selectedFile);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @FXML
    private void generateImage() {
        BufferedImage image = makeColoredImage();
        imageView.setImage(SwingFXUtils.toFXImage(image, null));
        myImage = new MyImage(image, null, null);
    }

    private void applyFilter(IFilter filter) {
        try {
            if (myImage == null) {
                browseImage(null);
            }
            myImage.modifiedImage = filter.applyFilter(myImage.getOriginalImage());
            modifiedImageRadioButton.setDisable(false);
            modifiedImageRadioButton.setSelected(true);

            displayImage(myImage.modifiedImage);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    private void applyNegative() {
        applyFilter(new Negative());
        displayImage(myImage.modifiedImage);
    }

    @FXML
    private void applyGrayscale() {
        applyFilter(new GrayScale());
        displayImage(myImage.modifiedImage);
    }

    @FXML
    private void applyThresholding() {
        applyFilter(new Thresholding());
        displayImage(myImage.modifiedImage);
    }

    @FXML
    private void applyConvolution() {
        applyFilter(new Conv());
        displayImage(myImage.modifiedImage);
    }


    private void displayImage(BufferedImage image) {
        imageView.setImage(SwingFXUtils.toFXImage(image, null));
    }

    private void displayImage(String url) {
        if (url != null) {
            BufferedImage image = LoadImage(url);
            myImage = new MyImage(image, null, url);
            imageView.setImage(SwingFXUtils.toFXImage(myImage.getOriginalImage(), null));
            imageView.setPreserveRatio(true);
        }
    }

    @FXML
    private void showAbout() {
        VBox vbox = new VBox();
        vbox.setPrefWidth(300);
        vbox.setPrefHeight(250);
        vbox.setStyle("-fx-background-color: #F3F4F6; -fx-border-color: #CCCCCC; -fx-border-radius: 5;");
        vbox.setSpacing(10);
        vbox.setPadding(new javafx.geometry.Insets(15, 15, 15, 15));
        vbox.setAlignment(Pos.CENTER);

        Label title = new Label("Program pro Pepiho");
        title.setFont(new Font("Arial", 16));
        title.setStyle("-fx-font-weight: bold; -fx-text-fill: #333333;");


        String[] names = {"Jakub Šikula", "Viktor Vaculík", "Valon Mavriqi", "Mikuláš Čadeni"};
        for (String name : names) {
            Label nameLabel = new Label(name);
            nameLabel.setStyle("-fx-text-fill: #555555;");
            vbox.getChildren().add(nameLabel);
        }

        Label versionLabel = new Label("Version 1.0");
        versionLabel.setStyle("-fx-text-fill: #888888;");


        Button okButton = new Button("OK");
        okButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        okButton.setPrefWidth(100);
        okButton.setOnAction(e -> ((Stage) okButton.getScene().getWindow()).close());

        vbox.getChildren().addAll(title, versionLabel, new Label(""), okButton); // Add components to VBox

        Stage stage = new Stage();
        stage.setTitle("About");
        stage.setScene(new Scene(vbox));
        stage.setResizable(false); // Make the window not resizable
        stage.show();
    }



    @FXML
    private void showOriginalImage() {
        if (myImage != null) {
            imageView.setImage(SwingFXUtils.toFXImage(myImage.getOriginalImage(), null));
        }
    }

    @FXML
    private void showModifiedImage() {
        if (myImage != null) {
            imageView.setImage(SwingFXUtils.toFXImage(myImage.modifiedImage, null));
        }
    }
}
