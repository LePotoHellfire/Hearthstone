package joueur;

import java.util.ArrayList;
import java.util.Random;

import carte.ICarte;
import carte.Serviteur;
import exception.HearthstoneException;
import exception.InvalidArgumentException;
import jeu.IPlateau;

public class Joueur implements IJoueur {
	
	public String pseudo; //Nom du joueur
	public Heros heros; //Classe du h�ros
	public int mana; //Mana maximum du personnage
	public int stockMana; //Stock de mana durant le tour
	public ArrayList<ICarte> jeu = new ArrayList<ICarte>(); //Carte pos�e sur le plateau du joueur
	public ArrayList<ICarte> main = new ArrayList<ICarte>();
	public ArrayList<ICarte> deck = new ArrayList<ICarte>() ; //Deck du joueur

	
	
	public Joueur(String pseudo, Heros heros) /*throws InvalidArgumentException*/ {
		//			Controles
		/*if(pseudo == null || pseudo.equals(""))	
			throw new InvalidArgumentException();
		if(heros == null)
			throw new InvalidArgumentException();*/
		//				Set
		this.heros = heros;
		this.pseudo = pseudo;
		this.mana = 0;
		this.stockMana = 0;
	}

	@Override
	public Heros getHeros() {
		return this.heros;
	}

	@Override
	public String getPseudo() {
		return this.pseudo;
	}

	@Override
	public int getMana() {
		return this.mana;
	}
	


	public ArrayList<ICarte> getDeck() {
		return deck;
	}

	@Override
	public int getStockMana() {
		return this.stockMana;
	}

	@Override
	public ArrayList<ICarte> getMain() {
		return this.main;
	}

	@Override
	public ArrayList<ICarte> getJeu() {
		return this.jeu;
	}

	@Override
	public ICarte getCarteEnJeu(String nomCarte) { //Renvoie la premiere carte dans le jeu du joueur qui contient nomCarte
		for(ICarte c : this.jeu){
			if(c.getNom().contains(nomCarte)){
				return c;
			}
		}
		return null;	//Si il n'y a pas de carte : On renvoie null
	}

	@Override
	public ICarte getCarteEnMain(String nomCarteMain) {	//M�me principe dans la main du joueur
		for(ICarte c : this.main){
			if(c.getNom().contains(nomCarteMain)){
				return c;
			}
		}
		return null;
	}

	@Override
	public void prendreTour() throws HearthstoneException {	//Appell�e au d�but du tour d'un joueur : Augmente la mana si elle n'est pas � son seuil, restore les stocks, et rend les monstres endormis jouables 
		if(this.mana < MAX_MANA){
			this.mana = this.mana + 1;
		}
		this.stockMana = this.mana;
		for(ICarte c : this.jeu){
			if (!((Serviteur) c).isJouable()){
				((Serviteur) c).setJouable(true);
			}
		}
		
	}

	@Override
	public void finirTour() throws HearthstoneException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void piocher() throws HearthstoneException {
		if(this.deck.size() == 0){	//Si le deck est vide on appelle l'exception
			throw new HearthstoneException("Le deck est vide");
		}
		Random rand = new Random();	//Mise en place du randomizer
	    int randomIndex = rand.nextInt(this.deck.size()-1);  //G�n�ration d'un nombre pas plus grand que la taille du deck
	    ICarte randomCarte = this.deck.get(randomIndex);	//Selection de la carte � l'index g�n�r�
	    this.deck.remove(randomIndex);	//On retire la carte du deck
	    this.main.add(randomCarte);	//On la rajoute sur le terrain
	    
		
	}

	@Override
	public void jouerCarte(ICarte carte) throws HearthstoneException {
		if(carte == null || !this.main.contains(carte)) //Si la carte demand�e n'est pas initialis�e ou ne fais pas partie des cartes en mains
			throw new HearthstoneException("Cette carte n'est pas dans votre main");
		if(this.stockMana >= carte.getCout()){	//Si le joueur � un stock de mana suffisant
			this.stockMana = this.stockMana - carte.getCout(); 	//On retire le mana necessaire a l'invocation
			this.jeu.add(carte);	//On ajoute la carte au jeu
			this.main.remove(carte);	//On retire la carte de la main
		}
		else{
			throw new HearthstoneException("Mana insufisant");
		}
		
	}

	@Override
	public void jouerCarte(ICarte carte, Object cible) throws HearthstoneException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void utiliserCarte(ICarte carte, Object cible) throws HearthstoneException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void utiliserPouvoir(Object cible) throws HearthstoneException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void perdreCarte(ICarte carte) throws HearthstoneException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return super.equals(obj);
	}

	@Override
	public String toString() {
		return "Pseudo [ " + this.pseudo + " ]\n Heros [ " + this.heros.getNom() + " ]\n Mana [ " + this.mana + " ], StockMana [ " + this.stockMana + " ]\n Jeu [ " + this.jeu + " ]\n Main [ " + this.main + " ]\n Deck [ " + this.deck + " ]";
	}
	
	

}
