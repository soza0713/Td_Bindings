package com.example.td_bindings;

import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.Initializable;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.StringConverter;
import javafx.util.converter.DoubleStringConverter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    public TextField hauteur;
    public TextField largeur;
    public TextField perimetre;
    public TextField surface;
    public Slider sliderHauteur;
    public Slider sliderLargeur;
    public Rectangle rectangle;



    StringConverter sc = new DoubleStringConverter(){

        @Override
        public Double fromString(String value) {
            value = value.replace(",", ".");
            value = value.replace("m","").trim();
            BigDecimal bd = new BigDecimal(value).setScale(2, RoundingMode.HALF_UP);
            return bd.doubleValue();
        }
        @Override
        public String toString(Double value) {
            DecimalFormat df = new DecimalFormat("#.00 m");
            return df.format(value);
        }
    };
    RectangleProperty monRectangle = new RectangleProperty();
    Double SEUIL_P = 1500.0, SEUIL_S = 5000.0;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Bindings.bindBidirectional(hauteur.textProperty(), monRectangle.hauteurProperty(), sc);
        Bindings.bindBidirectional(largeur.textProperty(), monRectangle.largeurProperty(), sc);
        perimetre.textProperty().bind(monRectangle.perimetreProperty().asString("%.2f m"));
        surface.textProperty().bind(monRectangle.surfaceProperty().asString("%.2f m²"));
        Bindings.bindBidirectional(hauteur.textProperty(),sliderHauteur.valueProperty(),sc);
        sliderHauteur.visibleProperty().bind(Bindings.when(monRectangle.hauteurProperty().greaterThan(100))
                .then(false).otherwise(true));
        Bindings.bindBidirectional(largeur.textProperty(),sliderLargeur.valueProperty(),sc);
        sliderLargeur.visibleProperty().bind(Bindings.when(monRectangle.largeurProperty().greaterThan(100))
                .then(false).otherwise(true));
        perimetre.backgroundProperty().bind(
                Bindings.when(monRectangle.perimetreProperty().greaterThan(SEUIL_P))
                        .then(new Background(new BackgroundFill(Color.RED, null, null)))
                        .otherwise(new Background(new BackgroundFill(Color.AQUA, null, null)))
        );
        surface.backgroundProperty().bind(
                Bindings.when(monRectangle.surfaceProperty().greaterThan(SEUIL_S))
                        .then(new Background(new BackgroundFill(Color.RED, null, null)))
                        .otherwise(new Background(new BackgroundFill(Color.AQUA, null, null)))
        );
        rectangle.widthProperty().bind(monRectangle.largeurProperty());
        rectangle.heightProperty().bind(monRectangle.hauteurProperty());

    }
}
