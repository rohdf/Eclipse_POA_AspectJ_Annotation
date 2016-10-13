package mysecurityframework.annotation;

public class SecurityTest {
 public static void main(String[] args) {
  Cerbere.setUtilisateur("b");
  
  Entreprise uneEntreprise = new Entreprise();  
  uneEntreprise.getNom();
  uneEntreprise.setNom("NsiServices");
 }
}