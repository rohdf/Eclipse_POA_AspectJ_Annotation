package mysecurityframework.annotation;

import java.lang.annotation.*;

// L'annotation peut être utilisée au moment de l'exécution
@Retention(RetentionPolicy.RUNTIME)

// L'annotation peut être placée sur une méthode
@Target(ElementType.METHOD)

public @interface CerbereInfo {
    // Nom de l'utilisateur autorisé ou * pour tout le monde
    String[] autorise() default {"*"};

    // true: les accès à cette méthodes sont placés dans un fichier de log
    boolean audit() default false;
}