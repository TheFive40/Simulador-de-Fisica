package util.animations;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import lombok.Getter;
import lombok.Setter;
import util.General;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import static org.simulador.es.data.LocalStorage.*;

@Setter
@Getter
public class MovCircularAnimation {
    private double radio = 100;
    private double velocidadAngular;
    private double velocidadTangencial;
    private double aceleracionCentripeta;
    private double aceleracionAngular;
    private double posicionAngular;
    private int angulo;
    private int tiempo;
    private Circle particula;
    private AnchorPane contenedorAnimacion;

    public MovCircularAnimation ( Circle particula, AnchorPane contenedorAnimacion ) {
        this.particula = particula;
        this.contenedorAnimacion = contenedorAnimacion;
    }

    public void establecerAnimacion ( double frecuenciaAngular ) {
        if (radio > 170 || radio < -170) {
            General.mostrarMensajeAlerta ( "Condiciones Iniciales ",
                    "¡Haz excedido el limite permitido!",
                    "¡Por favor asegurate de dar valores realistas \n" +
                            "Para una mejor experiencia del simulador!",
                    Alert.AlertType.WARNING );
            return;
        }
        AtomicInteger tiempo = new AtomicInteger ( );
        Timeline timeline = new Timeline ( new KeyFrame ( Duration.millis ( 16 ), e -> {

            tiempo.getAndIncrement ( );
            double CenterX = (contenedorAnimacion.getWidth ( ) / 2);
            double CenterY = (contenedorAnimacion.getHeight ( ) / 8);

            double angle = (frecuenciaAngular * tiempo.get ( ) % 360);
            double x = CenterX + radio * Math.cos ( Math.toRadians ( angle ) );
            double y = CenterY + radio * Math.sin ( Math.toRadians ( angle ) );
            //Calculos de velocidades  y aceleracion Centripeta y Angular


            velocidadAngular = angle / tiempo.get ( );
            velocidadTangencial = radio * velocidadAngular;
            aceleracionCentripeta = (Math.pow ( velocidadAngular, 2 )) / radio;
            aceleracionAngular = aceleracionCentripeta;
            posicionAngular = Math.toRadians ( velocidadAngular ) * tiempo.get ( );


            //Aplicamos formato a los valores Numericos
            BigDecimal formatoVelocidadTiempo = new BigDecimal ( velocidadAngular );
            BigDecimal formatoAceleracionTiempo = new BigDecimal ( aceleracionAngular );
            BigDecimal formatoPosicion = new BigDecimal ( posicionAngular );
            formatoVelocidadTiempo = formatoVelocidadTiempo.setScale ( 2, RoundingMode.DOWN );
            formatoAceleracionTiempo = formatoAceleracionTiempo.setScale ( 2, RoundingMode.DOWN );
            formatoPosicion = formatoPosicion.setScale ( 3,RoundingMode.DOWN );
            //Guardamos la informacion en el LocalStorage
            velocidadTiempoMovCircular.put ( tiempo.get ( ), formatoVelocidadTiempo.doubleValue ( ) );
            aceleracionTiempoMovCircular.put ( tiempo.get ( ), formatoAceleracionTiempo.doubleValue () );
            posicionTiempoMovCircular.put ( tiempo.get ( ), formatoPosicion.doubleValue () );
            //Colocamos la particula en un punto de nuestro eje de coordenadas
            particula.setLayoutX ( x );
            particula.setCenterY ( y );
        } ) );
        General.tiempoAnimacion = getTiempo ( );
        timeline.setOnFinished ( event -> {
            tiempo.set ( 0 );
            formatoTabla ( );
        } );
        timeline.setCycleCount ( getTiempo ( ) * 1000 / 16 );
        timeline.play ( );
    }

    void formatoTabla () {

        aceleracionTiempoMovCircular.get ( ).entrySet ( ).removeIf ( k -> k.getKey ( ) > General.tiempoAnimacion );
        posicionTiempoMovCircular.get ( ).entrySet ( ).removeIf ( k -> k.getKey ( ) > General.tiempoAnimacion );
        velocidadTiempoMovCircular.get ( ).entrySet ( ).removeIf ( k -> k.getKey ( ) > General.tiempoAnimacion );
        General.tablaValores = "Movimiento Circular Uniforme \n" +
                "Tiempo VS Velocidad: " + velocidadTiempoMovCircular + "\n" +
                "Tiempo VS Aceleracion: " + aceleracionTiempoMovCircular + "\n" +
                "Tiempo VS Posicion: " + posicionTiempoMovCircular;
    }
}
