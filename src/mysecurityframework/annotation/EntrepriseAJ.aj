package mysecurityframework.annotation;

public aspect EntrepriseAJ {
	//Pointcut
	pointcut getNomCall() : execution(public String getNom()) && this(Entreprise);
	pointcut setNomCall() : execution(public void setNom(String)) && this(Entreprise);
	
	//Advice 
	before(Entreprise e): getNomCall() && target(e){
		Cerbere.verifier(e, "getNom");
	}
	
	before(Entreprise e): setNomCall() && target(e){
		Cerbere.verifier(e, "setNom", String.class);
	}
	
}
