package org.simulador.es.model;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import lombok.Getter;
import lombok.Setter;
import java.util.concurrent.atomic.AtomicInteger;
@Setter
@Getter
public class MovCircular {
    private double radio = 100;
    private double velocidadAngular;
    private double velocidadTangencial;
    private double aceleracionCentripeta;
    private double aceleracionAngular;
    private int angulo;
    private int tiempo;
    private Circle particula;
    private AnchorPane contenedorAnimacion;

    public MovCircular(Circle particula, AnchorPane contenedorAnimacion) {
        this.particula = particula;
        this.contenedorAnimacion = contenedorAnimacion;
    }

    public void establecerAnimacion(double frecuenciaAngular) {
        AtomicInteger tiempo = new AtomicInteger();
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(16), e -> {

            tiempo.getAndIncrement();
            double CenterX = (contenedorAnimacion.getWidth() / 2);
            double CenterY = (contenedorAnimacion.getHeight() / 8);

            double angle = (frecuenciaAngular * tiempo.get()) % 360;
            double x = CenterX + radio * Math.cos(Math.toRadians(angle));
            double y = CenterY + radio * Math.sin(Math.toRadians(angle));

            //Calculos de velocidades  y aceleracion Centripeta y Angular
            velocidadAngular = angle / tiempo.get();
            velocidadTangencial = radio * velocidadAngular;
            aceleracionCentripeta = Math.pow(velocidadTangencial,2)/radio;
            aceleracionAngular = velocidadAngular/tiempo.get();

            //Colocamos la particula en un punto de nuestro eje de coordenadas
            particula.setLayoutX(x);
            particula.setCenterY(y);
        }));
        timeline.setOnFinished(event -> tiempo.set(0));
        timeline.setCycleCount(getTiempo()*1000/16);
        timeline.play();
    }

}