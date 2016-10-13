package mysecurityframework.annotation;

import java.lang.reflect.*;
/**
 * Dans la mythologie grecque, Cerbère est le chien à trois têtes gardant l'entrée des Enfers. 
 * @author lmercier
 *
 */
public class Cerbere {

    // Le nom de l'utilisateur connecté (simplifié pour l'exemple)
    private static String _utilisateur;

    public static synchronized void setUtilisateur(String utilisateur) {
        _utilisateur = utilisateur;
    }

    public static String getUtilisateur() {
        return _utilisateur;
    }

    // Vérifier si l'utilisateur a le droit d'appeler cette méthode
    // Cette méthode utilise une nouveauté du JDK 1.5, les varargs permettant de passer
    // un nombre variable de paramètres.
    public static void verifier(Object object, String methodName, Class<?>... paramTypes) {

        try {
            // Créer un tableau contenant les types des paramètres de la méthode
            Class<?> paramTypesArray[] = new Class[paramTypes.length];

            // Remplir le tableau à partir des parmaètres de la méthode
            int i = 0;

            // Ca aussi c'est une nouveauté du JDK 1.5!
            for (Class<?> paramType : paramTypes)
                paramTypesArray[i++] = paramType;

            // Récupérer la méthode appelée.
            Method method = object.getClass().getMethod(methodName, paramTypesArray);

            // S'il n'y a pas d'annotation, autoriser l'appel
            if( !method.isAnnotationPresent(CerbereInfo.class) ) return;

            // Récupérer l'annotation CerbèreInfo
            CerbereInfo info = method.getAnnotation(CerbereInfo.class);

            // Vérifier si l'utilisateur est autorisé à utiliser cette méthode
            if( verifier(info.autorise(), getUtilisateur()) ) {

                // Si l'audit est activé pour cette méthode, ajouter l'appel de la méthode
                if( info.audit() ) log("INFO: Utilisateur: " + getUtilisateur() + " est autorisé(e) à appeler la méthode " + methodName);
                return;
            }

        } catch (Exception e) {
            // Il y a un problème quelque part
            exception("FATAL: y a un problème quelque part avec la méthode " + methodName);
        }

        // L'utilisateur n'a pas le droit d'utiliser cette méthode
        exception("ALERT: Utilisateur: " +  getUtilisateur() + "  n'est pas autorisé(e) à appeler la méthode " + methodName);
    }

    // Vérifier si l'utilisateur fait partie des utilisateurs autorisés
    private static boolean verifier(String[] autorisés, String utilisateur) {

        // Une nouveauté bien sympathique du JDK 1.5
        for(String autorisé: autorisés) {

            // Si "*", alors tous les utilisateurs peuvent déclencher cette méthode 
            if( autorisé.equals("*") ) return true;
            if( utilisateur.equals(autorisé) ) return true;
            //Sauf Bob
        }

        // L'utilisateur ne fait pas partie des utilisateurs autorisés
        return false;
    }

    private static void exception(String msg) {

        // Logger le problème
        log(msg);

        // Envoyer une exception (RuntimeException pour simplifier l'exemple)
        throw new RuntimeException(msg);
    }

    private static void log(String msg) {
        // Un peut minimaliste comme système d'audit, mais cela permet de simplifier l'exemple
        System.err.println("[Cerbère] " + msg);
    }
}