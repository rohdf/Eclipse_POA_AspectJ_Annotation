package mysecurityframework.annotation;


public class Entreprise {
    private String nom;


    // Tous le monde peut utiliser cette méthode, mais les accès sont enregistrés (audit)
    @CerbereInfo (
            autorise={"*"},
            audit=true
    )
    public String getNom() {
        //Cerbere.verifier(this, "getNom");
        return nom;
    }

    // Seul admin ou bob peut utiliser cette méthode, et les accès sont enregistrés (audit)
    @CerbereInfo (
            autorise={"admin", "bob"},
            audit=true
    )
    public void setNom(String nom) {
        //Cerbere.verifier(this, "setNom", String.class);
        this.nom = nom;
    }
}