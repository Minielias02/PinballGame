package segundoEjercicio;

/**
 * 
 * @author elias
 *
 */
public class Pinball {

	private boolean ballOutOfTable;;
	private int score;
	private String playerName;
	private short firstShootStrong;
	private static boolean ballSafe;
	private static byte life;
	private static int bumpersCounter;

	/**
	 * Constructor
	 * 
	 * @param playerName
	 */
	public Pinball(String playerName) {
		ballOutOfTable = false;
		score = 0;
		this.playerName = playerName;
		life = PinballConstants.LIFES;
		bumpersCounter = 0;
		ballSafe = true;
	}

	/**
	 * si te quedan bolas salvadores, devuelve false, sino, devuelve true y pierdes
	 * una vida
	 * 
	 * @param ballOutOfTable
	 * @return ballOutOfTable
	 */
	public boolean ballOutOfTable(boolean ballOutOfTable) {
		if (ballSafe == true) {
			this.ballOutOfTable = false;
			ballSafe = false;
			System.out.println("Has perdido la bola salvadora\n");
		} else {
			this.ballOutOfTable = ballOutOfTable;
		}
		return this.ballOutOfTable;
	}

	/**
	 * Aumenta la bola salvadora en 1
	 */
	public void newSafeBall() {
		ballSafe = true;
	}

	/**
	 * Controla si el juego puede comenzar
	 * 
	 * @param coin
	 * @return coin
	 */
	public boolean gameStart(boolean coin) {

		return coin;
	}

	/**
	 * 
	 * @param firstShootStrong
	 * @return devuelve un numero aleatorio entre 0 y 5 , o nulo si la fuerza
	 *         introducida es diferente a 200/400/600
	 */
	public Double ballShoot(double firstShootStrong) {
		if (firstShootStrong == 200) {
			return (Math.random() * (2));
		} else if (firstShootStrong == 400) {
			return (Math.random() * (2 + 2));
		} else if (firstShootStrong == 600) {
			return (Math.random() * (2 + 4));
		} else {
			System.out.println("No puedes lanzar con esta fuerza");
			return null;
		}
	}

	/**
	 * Metodo para quitar una vida
	 */
	public void looseLife() {
		life += PinballConstants.LIFELOST;
	}

	/**
	 * Metodo para ganar una vida
	 */
	public void gainLife() {
		life += PinballConstants.EXTRABALL;
	}

	/**
	 * Bumper, tambien llamado rebotador, cuando la bola da en uno, aumenta la
	 * puntuacion en 500, y ademas cuenta a cuantos golpea
	 */
	public void bumper() {
		this.score += 500;
		bumpersCounter++;
	}
	
	public void superBumper() {
		this.score +=1500;
		bumpersCounter++;
	}

	/**
	 * Cuando la bola sube una rampa, rebota en las paredes, eso le hace conseguir
	 * puntos en funcion de cuantos rebotes
	 * 
	 * @param rebounds
	 */
	public void rampCombo(double rebounds) {
		for (int i = 0; i < rebounds; i++) {
			this.score += 750;
		}
	}

	/**
	 * El jackpot se consigue al realizar un tiro arriesgado, se consiguen muchos
	 * puntos con esto
	 */
	public void jackpot() {
		this.score += 3000;
	}

	/**
	 * El superJackpot, no recomendable cuando no te quedan vidas, una posibilidad
	 * de 4 para ganar 10.000 puntos o perder vidas
	 */
	public void superJackpot() {
		this.score += 10000;
	}

	/**
	 * Que te voy a decir Tellez, has tenido mala suerte al caer en la trampa y
	 * perder puntos
	 */
	public void trap() {
		this.score -= 600;
	}
	
	/**
	 * Trampa que te va a hacer odiar las trampas
	 */
	public void youWillHateMeTheTrap() {
		this.score -=5.000;
	}
	
	/**
	 * 
	 * @param probability
	 * @return numero aleatorio de movimientos que realiza la bola
	 */
	public Double randomMove(double probability) {
		return Math.random() * (probability);
	}

	/**
	 * Si consigues que la bola rebote en 10 bumpers, entonces tu puntuacion se
	 * multiplica por 2
	 */
	public void tenBumpers() {
		this.score *= 2;
	}

	/**
	 * Si consigue que rebote en 30 bumpers, entonces, se multiplica por 3
	 */
	public void thirtyBumpers() {
		this.score *= 3;
	}

	// A partir de aqui son todo getters y setters

	/**
	 * @return the ballOutOfTable
	 */
	public boolean isBallOutOfTable() {
		return ballOutOfTable;
	}

	/**
	 * @param ballOutOfTable the ballOutOfTable to set
	 */
	public void setBallOutOfTable(boolean ballOutOfTable) {
		this.ballOutOfTable = ballOutOfTable;
	}

	/**
	 * @return the score
	 */
	public int getScore() {
		return score;
	}

	/**
	 * @param score the score to set
	 */
	public void setScore(int score) {
		this.score = score;
	}

	/**
	 * @return the playerName
	 */
	public String getPlayerName() {
		return playerName;
	}

	/**
	 * @param playerName the playerName to set
	 */
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	/**
	 * @return the firstShootStrong
	 */
	public short getFirstShootStrong() {
		return firstShootStrong;
	}

	/**
	 * @param firstShootStrong the firstShootStrong to set
	 */
	public void setFirstShootStrong(short firstShootStrong) {
		this.firstShootStrong = firstShootStrong;
	}

	/**
	 * @return the ballSafe
	 */
	public static boolean isBallSafe() {
		return ballSafe;
	}

	/**
	 * @param ballSafe the ballSafe to set
	 */
	public static void setBallSafe(boolean ballSafe) {
		Pinball.ballSafe = ballSafe;
	}

	/**
	 * @return the life
	 */
	public static byte getLife() {
		return life;
	}

	/**
	 * @param life the life to set
	 */
	public static void setLife(byte life) {
		Pinball.life = life;
	}

	/**
	 * @return the bumpersCounter
	 */
	public static int getBumpersCounter() {
		return bumpersCounter;
	}

	/**
	 * @param bumpersCounter the bumpersCounter to set
	 */
	public static void setBumpersCounter(int bumpersCounter) {
		Pinball.bumpersCounter = bumpersCounter;
	}
}
