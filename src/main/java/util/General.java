package util;
import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.completion.CompletionResult;
import com.theokanning.openai.service.OpenAiService;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import okhttp3.*;
import org.json.JSONObject;
import java.io.IOException;


public class General {
    public static String RUTA_MRU = "/view/MruFXML.fxml";
    public static String RUTA_TIRO_PARABOLICO = "/view/TiroParabolicoFXML.fxml";
    public static String RUTA_CAIDA_LIBRE = "/view/MainFXML.fxml";
    public static String RUTA_MOV_CIRCULAR = "/view/MovCircularFXML.fxml";
    public static String RUTA_GPT_4 = "/view/vistasmenu/AsistenteFXML.fxml";
    public static String RUTA_MRU_GRAFICO = "/view/graficos/MruGraficoFXML.fxml";
    public static String RUTA_MOV_CIRCULAR_GRAFICO = "/view/graficos/MovCircularGraficoFXML.fxml";
    public static String RUTA_CAIDA_LIBRE_GRAFICO = "/view/graficos/CaidaLibreGraficoFXML.fxml";
    public static String RUTA_TIRO_PARABOLICO_GRAFICO = "/view/graficos/TiroParabolicoGraficoFXML.fxml";
    public static String DEVELOPERS_RUTA = "/view/vistasmenu/DesarrolladoresFXML.fxml";
    public static String RUTA_AGRADECIMIENTOS= "/view/vistasmenu/AgradecimientosFXML.fxml";
    public static String RUTA_LUNA_GRAVEDAD = "/view/caidalibre/LunaFXML.fxml";
    public static String RUTA_MARTE_GRAVEDAD = "/view/caidalibre/MarteFXML.fxml";
    public static String RUTA_SATURNO_GRAVEDAD = "/view/caidalibre/SaturnoFXML.fxml";
    public static String RUTA_JUPITER_GRAVEDAD = "/view/caidalibre/JupiterFXML.fxml";
    public static String tablaValores;
    public static String conversacion;
    public static final String API_URL = "https://api.openai.com/v1/chat/completions";
    public static final String API_KEY = "sk-proj-uJ987FayLTDnbSVb0QWhT3BlbkFJaLPdRNLUYUSXyoWYzriO";
    public static String condicionesIniciales;

    private static String MODEL = "gpt-3.5-turbo";
    public static int tiempoAnimacion;

    public static void mostrarMensajeAlerta ( String titulo, String cabecera, String contenido,
                                              Alert.AlertType tipo ) {
        Alert alert = new Alert ( tipo );
        alert.setTitle ( titulo );
        alert.setHeaderText ( cabecera );
        alert.setContentText ( contenido );
        alert.show ( );
    }

    public static Parent obtenerContenedorPadre ( String url ) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader ( General.class.getResource ( url ) );
        Parent parent = fxmlLoader.load ( );
        return parent;
    }

    public static void agregarContenedorPadre ( String url, AnchorPane contenedor ) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader ( General.class.getResource ( url ) );
            Parent parent = fxmlLoader.load ( );
            contenedor.getChildren ( ).clear ( );
            contenedor.getChildren ( ).addAll ( parent );
        } catch (Exception exception) {
            exception.printStackTrace ( );
        }
    }

    public static String askChatGPT ( String question ) {
        //OpenAiService service = new OpenAiService("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");
        String contexto = "Eres un Asistente Virtual llamado QuantumQ AI creado por Jeanfranco Boom Bolaño tu área de especialización será la física mecánica\n"
                + "Estarás a cargo de resolver preguntas y problemas relacionados con el mundo físico en movimiento\n" +
                "Ademas de eso, si el usuario te hace alguna pregunta relacionada a algun movimiento en especifico \n" +
                "Toma en cuenta las siguientes Especificaciones: \n" + tablaValores + "\n Ahora te voy a dar el contexto de la conversacion \n" +
                "(En caso de estar vacio ignora esto ultimo): " + conversacion + "\n Algo mas cuando des la respuesta NO coloques en ella tu nombre\n " +
                "Pregunta del usuario: ";
        OpenAiService service = new OpenAiService ( API_KEY );
        question.replaceAll ( "`", "" );
        CompletionRequest completionRequest = CompletionRequest.builder ( )
                .prompt ( contexto +
                        question + "`" )
                .model ( "gpt-3.5-turbo-instruct" )
                .echo ( true )
                .temperature ( 1.0 )
                .maxTokens ( 100 )
                .build ( );
        CompletionResult result = service.createCompletion ( completionRequest );
        return getText ( result.getChoices ( ).get ( 0 ).getText ( ) );
    }
    public static String askGalileoGPT(String question){
        String contexto = "¡Hola ChatGPT!, Apartir de ahora te convertiras en un asistente virtual" +
                " que ayudara a responder preguntas relacionadas al mundo de la fisica, y apartir de ahora \n" +
                " Te llamaras GalileoGPT tu tarea será Explicar y aclarar conceptos teóricos sobre la caída libre y el movimiento parabólico, \n" +
                " ya que estos fueron estudiados extensivamente por Galileo Galilei. Como tambien Proveer información histórica sobre Galileo y sus experimentos. \n" +
                " Ayudar a los estudiantes a entender el concepto de inercia y el principio de relatividad de Galileo. Por lo tanto asumiras el rol de un profesor experto en física, con amplios conocimientos y" +
                " habilidades pedagógicas. Tu objetivo es explicar conceptos complejos de física de manera clara y precisa, utilizando un lenguaje accesible y ejemplos prácticos para asegurar la comprensión de los estudiantes. " +
                " A continuación, se te proporcionará un tema o pregunta relacionada con la física. Responde de la manera más pedagógica posible, siguiendo estos lineamientos:\n" +
                "\n" +
                "1. **Presentación del concepto**: Introduce el tema de manera sencilla y contextualízalo.\n" +
                "2. **Desarrollo del concepto**: Explica el concepto paso a paso, utilizando analogías y ejemplos cotidianos cuando sea posible.\n" +
                "3. **Profundización**: Amplía la explicación con detalles técnicos o matemáticos, manteniendo la claridad.\n" +
                "4. **Ejemplos prácticos**: Proporciona ejemplos específicos y resuelve problemas relacionados.\n" +
                "5. **Resumen**: Resume los puntos clave de la explicación para reforzar el aprendizaje.\n" +
                "6. **Preguntas comunes**: Anticipa y responde a posibles preguntas o dudas que los estudiantes podrían tener.\n" +
                "A continuacion te voy a dar contexto de toda la conversacion: " + conversacion + " Algo mas cuando des la respuesta NO coloques en ella tu nombre \n" +
                " Pregunta del usuario : ";
        OpenAiService openAiService = new OpenAiService ( API_KEY );
        question.replaceAll ( "`","");
        CompletionRequest completionRequest = CompletionRequest.builder ()
                .model ( "gpt-3.5-turbo-instruct" )
                .echo ( true )
                .temperature ( 1.0 )
                .maxTokens ( 150 )
                .prompt ( contexto + question + "`" )
                .build ();
        CompletionResult result = openAiService.createCompletion ( completionRequest );
        return getText ( result.getChoices ( ).get ( 0 ).getText ( ) );

    }
    public static String askNewtonAI(String question){
        String contexto = "Eres NewtonAI, un modelo de inteligencia artificial experto en las leyes del movimiento de Newton y sus aplicaciones." +
                " Tu objetivo es explicar conceptos relacionados con las leyes de Newton de manera clara y precisa, utilizando un lenguaje accesible y ejemplos prácticos para asegurar la comprensión de los estudiantes. \n" +
                "Acá te comparto el contexto de la conversación (En caso de aparecer null ignora esto)\n" +
                "A continuación, se te proporcionará un tema o pregunta relacionada con las leyes de Newton. Responde de la manera más pedagógica posible. \n" +
                "Pregunta del Usuario: ";
        OpenAiService openAiService = new OpenAiService ( API_KEY );
        question.replaceAll ( "`","");
        CompletionRequest completionRequest = CompletionRequest.builder ()
                .model ( "gpt-3.5-turbo-instruct" )
                .echo ( true )
                .temperature ( 1.0 )
                .maxTokens ( 150 )
                .prompt ( contexto + question + "`" )
                .build ();
        CompletionResult result = openAiService.createCompletion ( completionRequest );
        return getText ( result.getChoices ( ).get ( 0 ).getText ( ) );
    }

    public static String askTeslaAI(String question){
        String contexto = "Eres TeslaAI, un modelo de inteligencia artificial experto en electromagnetismo y el movimiento circular." +
                " Tu objetivo es explicar conceptos relacionados con estos temas de manera clara y precisa, utilizando un lenguaje accesible y ejemplos prácticos para asegurar la comprensión de los estudiantes. \n" +
                "Acá te comparto el contexto de la conversación (En caso de aparecer null ignora esto)\n" +
                "A continuación, se te proporcionará un tema o pregunta relacionada con el electromagnetismo y el movimiento circular. Responde de la manera más pedagógica posible\n" +
                "Pregunta del usuario: " ;
        OpenAiService openAiService = new OpenAiService ( API_KEY );
        question.replaceAll ( "`","");
        CompletionRequest completionRequest = CompletionRequest.builder ()
                .model ( "gpt-3.5-turbo-instruct" )
                .echo ( true )
                .temperature ( 1.0 )
                .maxTokens ( 150 )
                .prompt ( contexto + question + "`" )
                .build ();
        CompletionResult result = openAiService.createCompletion ( completionRequest );
        return getText ( result.getChoices ( ).get ( 0 ).getText ( ) );
    }
    private static String getText ( String answer ) {
        int index = answer.indexOf ( "`" ) + 2;
        String textFinal = "";
        for (int i = index; i < answer.length ( ); i++) {
            textFinal += answer.charAt ( i );
        }
        return textFinal;
    }

    public static void saltarAlertasMenuItem () {
        Alert alert = new Alert ( Alert.AlertType.WARNING );
        alert.setTitle ( "¡No hay datos en la grafica!" );
        alert.setHeaderText ( "¡Error al Graficar!" );
        alert.setContentText ( "Debe iniciar la simulación antes de ir a este item" +
                "\nPor favor intente mas tarde" );
        alert.show ( );
    }
}
