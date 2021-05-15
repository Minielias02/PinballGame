package segundoEjercicio;

import java.util.Scanner;

public class CodeClass {

	public void theGameWillStart() {
		System.out.println(
				"Estas en el Pinball de Elias, cada vez que pierdas una vida tienes una bola salvadora que te salvara de una caida,");
		System.out.println("Esta se puede reponer si tienes suficiente suerte, mucha suerte :)\n");
		Scanner op = new Scanner(System.in);
		System.out.print("Introduzca su nombre: ");
		Pinball pin = new Pinball(op.next());
		byte coin;
		boolean startGame;
		boolean firstShot;
		boolean ballOut = false;
		boolean extraBall10000 = true;
		boolean extraBall50000 = true;
		// Necesitas introducir 5 centimos para avanzar
		do {
			System.out.print("Introduzca una moneda de 5 centimos: ");
			coin = op.nextByte();
			startGame = coin == PinballConstants.COIN ? (pin.gameStart(true)) : (pin.gameStart(false));
		} while (startGame == false);

		// Cuando metes la moneda de 5 centimos empieza el juego
		if (startGame) {
			System.out.println(
					"\nSuerte jugador, si consigues dar a 10, bumpers, tu puntuacion se multiplicara por dos, si consigues dar a 30, tu puntuacion su multiplicara por 3");
			// Tienes 3 vidas, si llegan a 0 acaba el juego
			while (Pinball.getLife() >= 0) {
				ballOut = false;
				firstShot = true;
				pin.newSafeBall();
				System.out.println("\nTienes una bola salvadora");
				// Fuerza con la que quieres lanzar la bola
				Double shoot = null;
				while ((shoot == null)) {
					System.out.println(
							"\nToca lanzar la bola, elija con que fuerza quiere lanzar la bola, 200, 400 o 600 de fuerza");
					shoot = pin.ballShoot(op.nextInt());
				}
				// Mientras la bola siga dentro del campo de Pinball
				while (ballOut == false) {
					// Va al metodo randomMovementPrincipal
					if (firstShot) {
						ballOut = randomMovementPrincipal(pin, ballOut, shoot, true);
						firstShot = false;
					} else {
						ballOut = randomMovementPrincipal(pin, ballOut, shoot, false);
					}

					// Si la bola cae fuera entonces pierde una vida
					if (ballOut) {
						pin.looseLife();
						System.out.println("Has perdido una vida, te quedan: " + Pinball.getLife() + " vidas");
					}

				}
				// Si llegas a 10000 de puntuacion, consigues una bola extra
				if (extraBall10000) {
					if (pin.getScore() > 10000) {
						pin.gainLife();
						extraBall10000 = false;
						System.out.println(
								"\n Enhorabuena, has llegado a 10.000 puntos asi que tienes una vida extra, ahora tienes :"
										+ Pinball.getLife() + " vidas");
					}
				}
				// Si llegas a 50000 de puntuacion, consigues una bola extra
				if (extraBall50000) {
					if (pin.getScore() > 50000) {
						pin.gainLife();
						extraBall50000 = false;
						System.out.println(
								"\n Enhorabuena, has llegado a 50.000 puntos asi que tienes una vida extra, ahora tienes :"
										+ Pinball.getLife() + " vidas");
					}
				}
				// Si te quedas sin vidas, pierdes
				if (Pinball.getLife() < 0) {
					System.out.println("\nGAME OVER");
				}

//			System.out.println(pin instanceof Pinball); No se me ocurria ningun lugar donde entrase el InstanceOff asi que lo puse asi

			}
			op.close();
			// Si consigues dar a los 10 bumpers entonces multiplicas puntuacion por 2
			if (Pinball.getBumpersCounter() > 10 && Pinball.getBumpersCounter() < 30) {
				pin.tenBumpers();
				System.out
						.println("Enhorabuena, has conseguido dar a diez bumpers, por lo que tu puntuacion se duplica");
			}
			// Si consigues dar 30 entonces se multiplica por 3
			else if (Pinball.getBumpersCounter() > 30) {
				pin.thirtyBumpers();
				System.out.println(
						"Madre mia, estas en racha, has conseguido dar a treinta bumpers, por lo que tu puntuacion se triplica");
			}
			// Muestra los resultados
			System.out.println("\nEl jugador: " + pin.getPlayerName() + ", Ha conseguido una puntuacion de: "
					+ pin.getScore() + ", Y ha golpeado a: " + Pinball.getBumpersCounter() + " Bumpers");

		}
	}

	/**
	 * Metodo para realizar movimientos aleatorios
	 * 
	 * @param pin
	 * @param ballOut
	 * @param shoot
	 * @param start
	 * @return
	 */
	private static boolean randomMovementPrincipal(Pinball pin, boolean ballOut, Double shoot, boolean start) {
		Double movement;
		Double probability = 0.0;
		// Si no es un movimiento de comienzo entonces genera un movimiento con fuerza
		// aleatoria
		if (start == false) {
			Double selector = pin.randomMove(3);
			if (selector < 1) {
				shoot = pin.ballShoot(PinballConstants.TWOHUNDRED);
			} else if (selector >= 1 && selector < 2) {
				shoot = pin.ballShoot(PinballConstants.FOURHUNDRED);
			} else {
				shoot = pin.ballShoot(PinballConstants.SIXRHUNDRED);
			}
		}
		// Listado de movimientos aleatorios aqui iria cuando la fuerza es de 200
		if (shoot >= 0 && shoot <= 1) {
			// cantidad de movimientos que la bola va a realizar
			movement = pin.randomMove(6);
			// Esto de las I, son los movimientos, en este ejemplo puede haber hasta 6
			// movimientos, lo explico todo en el primero para no tener que explicarlo luego
			for (int i = 0; i < movement && ballOut == false; i++) {
				// Esto serian los 3 primeros movimientos, i=0, i=1,i=2
				if (i < 3) {
					// probabilidad de que algo suceda
					probability = chanceToSucess();
					// Si la probabilidad esta entre uno y tres
					if (probability >= 1 && probability < 3) {
						System.out.println("Bien ha dado en un Bumper, +500 puntos");
						pin.bumper();
						// Si la probabilidad esta entre cero y uno
					} else if (probability >= 0 && probability < 1) {
						System.out.println("La bola no para de fallar y no golpea nada");
					} else {
						System.out.println("Mala suerte, tu bola ha caido fuera");
						ballOut = pin.ballOutOfTable(true);
					}
					// Este seria el cuarto movimiento y quinto movimiento
				} else if (i < 5) {
					probability = chanceToSucess();
					// Esta seria la probabilidad entre cero y 2
					if (probability >= 0 && probability <= 2) {
						System.out.println("Estas encandenando un superCombo +750 puntos por combo");
						pin.rampCombo(Math.random() * (4 + 1));
						// Si tienes mala suerte y no t da la probabilidad pues fuera la bola
					} else {
						System.out.println("Esta teniendo mala suerte, La bola acaba de caer fuera");
						ballOut = pin.ballOutOfTable(true);
					}
					// Este seria el sexto movimiento
				} else if (i == 5) {
					probability = chanceToSucess();
					// Probabilidad de que suceda si 2 o 3 la bola se te va fuera
					if (probability >= 2 && probability <= 3) {
						System.out.println("Mala suerte, tu bola ha caido fuera");
						ballOut = pin.ballOutOfTable(true);
						// Sino, se salva y no pasa nada
					} else {
						System.out.println("Justo la bola rebota en el flipper y se salva");
					}
				}
			}
			// Listado de movimientos aleatorios aqui iria cuando la fuerza es de 200
		} else if (shoot >= 1 && shoot <= 2) {
			// cantidad de movimientos que la bola va a realizar
			movement = pin.randomMove(3);
			for (int i = 0; i < movement && ballOut == false; i++) {
				// Esto sucede en el primer movimiento, si el jugador tiene 3 vidas
				if (i == 0 && Pinball.getLife() == 3) {
					// probailidad de que algo suceda, numeros entre 0 y 3 (4 posibilidades)
					probability = chanceToSucess();
					// si la probabilidad es 1, no sucede nada
					if (probability < 1) {
						System.out.println("No ha sucedido nada");
					} else if (probability >= 1 && probability < 2) {
						System.out.println(
								"El jugador, viendo que tiene tres vidas, ha decidido hacer un tiro arriesgado y ha dado a un Jackpot y consigue 3.000 puntos");
						pin.jackpot();
					} else if (probability >= 2 && probability < 3) {
						System.out.println(
								"El jugador, viendo que tiene tres vidas, ha decidido hacer un tiro arriesgado y cae sobre 2 trampas, por lo que pierde 600 puntos dos veces");
						pin.trap();
						pin.trap();
					} else if (probability >= 3 && probability < 4) {
						System.out.println(
								"El jugador, viendo que tiene tres vidas, ha decidido hacer un tiro arriesgado y, oh no, que mala suerte, la bola ha ido directa hacia fuera");
						ballOut = pin.ballOutOfTable(true);
					} else {
						System.out.println("La bola con consigue dar en ningun sitio");
					}
				} else if (i == 1 || i == 2) {
					probability = chanceToSucess();
					if (probability >= 1 && probability <= 2) {
						System.out.println("Bien ha dado en tres Bumpers, +1500 puntos");
						for (int j = 0; j < 3; j++) {
							pin.bumper();
						}
					} else if (probability >= 3 && probability < 4 && Pinball.getLife() == 2) {
						System.out.println(
								"El jugador intenta realizar un buen combo para conseguir puntos y, lo consiguee!, encandena un combo de 3, + 2250 puntos");
						pin.rampCombo(3);
					}

					else {
						System.out.println("Esta teniendo mala suerte, no consigues encadenar combos");
					}
				}
			}
			// Listado de movimientos aleatorios aqui iria cuando la fuerza es de 400
		} else if (shoot >= 2 && shoot <= 3) {
			// cantidad de movimientos que la bola va a realizar
			movement = pin.randomMove(4);
			for (int i = 0; i < movement && ballOut == false; i++) {
				// Esto sucede en el primer movimiento, si el jugador tiene 0 vidas
				if (i == 0 && Pinball.getLife() == 0) {
					probability = chanceToSucess();
					if (probability >= 0 && probability < 3) {
						System.out.println(
								"El jugador decide realizar un tiro muy arriesgado para conseguir una vida extra y no perder y... Lo consigue!!!");
						pin.gainLife();
						System.out.println("El jugador ahora tiene: " + Pinball.getLife() + " vidas");
					} else {
						System.out.println(
								"El jugador decide realizar un tiro muy arriesgado para conseguir una vida extra y no perder y... No lo consigue, es más, falla y cae fuera :c");
						ballOut = pin.ballOutOfTable(true);
					}
				} else if (i == 1 || i == 3) {
					probability = chanceToSucess();
					if (probability >= 0 && probability < 1) {
						System.out.println("Que suerte tienes, has conseguido un Jackpot, +3.000 puntos");
						pin.jackpot();
					} else if (probability >= 1 && probability < 3) {
						System.out.println("La bola acaba de rebotar en 4 bumpers, + 2.000 puntos");
						for (int j = 0; j < 4; j++) {
							pin.bumper();
						}
					} else {
						System.out.println("No puede ser, la bola ha caido fuera");
						ballOut = pin.ballOutOfTable(true);
					}
				} else if (i == 2) {
					probability = chanceToSucess();
					if (probability >= 0 && probability < 2) {
						System.out.println("Que mala suerte tienes, has caido en tres trampas, -1.800 puntos");
						for (int j = 0; j < 3; j++) {
							pin.trap();
						}
					} else if (probability >= 2 && probability < 3) {
						System.out.println("La bola acaba de rebotar en un bumpers, + 500 puntos");
						pin.bumper();
					} else {
						System.out.println("Si no tenias bola salvadora has conseguido una ahora");
						pin.newSafeBall();
					}
				}
			}
			// Listado de movimientos aleatorios aqui iria cuando la fuerza es de 400
		} else if (shoot >= 3 && shoot <= 4) {
			// cantidad de movimientos que la bola va a realizar
			movement = pin.randomMove(5);
			for (int i = 0; i < movement && ballOut == false; i++) {
				switch (i) {
				case 0:
					probability = chanceToSucess();
					if (probability >= 0 && probability < 1) {
						System.out.println(
								"Que mala suerte tienes,te has arriesgado y has caido en la trampa VasAOdiarme, -5.000 puntos");
						pin.youWillHateMeTheTrap();
					} else if (probability >= 1 && probability < 2) {
						System.out.println(
								"Te la has jugado y lo has conseguido!!!, has conseguido un Jackpot y dos bumpers, +4.000 puntos");
						pin.jackpot();
						pin.bumper();
						pin.bumper();
					} else if (probability >= 2 && probability < 3) {
						System.out.println("No puede ser, la bola ha caido fuera");
						ballOut = pin.ballOutOfTable(true);
					} else {
						System.out.println(
								"Vaya Tellez, te noto con suerte, sino tenias una bola salvadora se te renueva");
						pin.newSafeBall();
					}
					break;
				case 1:
					probability = chanceToSucess();
					if (probability >= 0 && probability < 1) {
						System.out.println(
								"No puede ser, ha ido con todo lo que tenia y HA conseguido el superjackpot!! + 10.000 puntos");
						pin.superJackpot();
					} else if (probability >= 1 && probability < 3) {
						System.out.println(
								"Se arriego a por el jackpot, pero en el camino cayo en 3 trampas, -1.800 puntos");
						for (int j = 0; j < 3; j++) {
							pin.trap();
						}
					} else {
						System.out.println(
								"No puede ser, intento llegar al Superjackpot, pero se fue la bola hacia fuera");
						ballOut = pin.ballOutOfTable(true);
					}
					break;
				case 2:
					probability = chanceToSucess();
					if (probability >= 0 && probability < 2 && Pinball.getBumpersCounter() >= 10) {
						System.out.print(
								"Tellez ha conseguido dar a 10 bumpers, por lo que desbloquea durante 30 segundos los superBumpers ");
						System.out.print("Intenta darle a la mayor cantidad de superBumpers que pueda: ");
						for (int j = 0; j < (Math.random() * (3 + 1)); j++) {
							pin.superBumper();
							System.out.println("+1500 puntos");
						}
					} else if (probability >= 2 && probability < 3) {
						System.out.println("Mala suerte, caes en una trampa, -600 puntos");
						pin.trap();
					} else if (probability >= 3 && Pinball.getLife() == 1) {
						System.out.println(
								"Intenta conseguir muchos puntos, pero que mala suerte, ha caido en la trampa MeVasAOdiar, -5.000 puntos");
						pin.youWillHateMeTheTrap();
					} else {
						System.out.println("Consigue dar en un bumper, +500 puntos");
						pin.bumper();
					}
					break;
				case 3:
					probability = chanceToSucess();
					if (probability >= 0 && probability < 2) {
						System.out.println("Consigues desencadenar un combo de 2, +1.500 puntos");
						pin.rampCombo(2);
					} else if (probability >= 2 && probability < 3) {
						System.out.println("Consigues dar en 2 bumpers");
						pin.bumper();
						pin.bumper();
					} else {
						System.out.println("Que mala suerte, has caido fuera");
						ballOut = pin.ballOutOfTable(true);
					}
					break;
				case 4:
					probability = chanceToSucess();
					if (probability >= 0 && probability < 1) {
						System.out.println("Caes en 2 trampas, -1.200 puntos");
						pin.trap();
						pin.trap();
					} else if (probability >= 1 && probability < 2) {
						System.out.println("Que mala suerte,la bola ha caido fuera");
						ballOut = pin.ballOutOfTable(true);
					} else {
						System.out.println(
								"Que suerte tienes, consigues una bola de salvacion, sino tenias y das en un bumper +500 puntos");
						pin.newSafeBall();
						pin.bumper();
					}
				}
			}
			// Listado de movimientos aleatorios aqui iria cuando la fuerza es de 600
		} else if (shoot >= 4 && shoot <= 5) {
			movement = pin.randomMove(2);
			for (int i = 0; i < movement && ballOut == false; i++) {
				if (i == 0) {
					probability = chanceToSucess();
					if (probability >= 0 && probability < 1) {
						System.out.println(
								"Que suerte, has dado en 2 bumpers y has subido un poco la rampa + 1.750 puntos");
						pin.bumper();
						pin.bumper();
						pin.rampCombo(1);
					} else if (probability >= 1 && probability < 2) {
						System.out.println("Mala suerte, has caido en la trampa MeVasAOdiar, - 5.000 puntos");
						pin.youWillHateMeTheTrap();
					} else {
						System.out.println("Estas teniendo suerte, has conseguido dar al jackpot, +3.000 puntos");
						pin.jackpot();
					}
				} else if (i == 1) {
					probability = chanceToSucess();
					if (probability >= 0 && probability < 1 && Pinball.getLife() == 1) {
						System.out.println(
								"El jugador decide realizar un tiro muy arriesgado para conseguir una vida extra y no perder y... Lo consigue!!!");
						pin.gainLife();
						System.out.println("El jugador ahora tiene: " + Pinball.getLife() + " vidas");
					} else if (probability >= 1 && probability < 2) {
						System.out.println("Que suerte, consigues dar a dos bumpers");
						pin.bumper();
						pin.bumper();
					} else if (probability >= 2 && probability < 3) {
						System.out.println(
								"Despues de realizar un tiro arriesgado para conseguir la vida extra... apunto de llegar cae en una trampa, - 600 puntos");
						pin.trap();
					} else if (probability > 3) {
						System.out.println(
								"El jugador decide realizar un tiro muy arriesgado para conseguir una vida extra y no perder y... No lo consigue, es más, falla y cae fuera :c");
						ballOut = pin.ballOutOfTable(true);
					}
				}
			}
			// Listado de movimientos aleatorios aqui iria cuando la fuerza es de 200
		} else if (shoot >= 5 && shoot <= 6) {
			movement = pin.randomMove(3);
			for (int i = 0; i < movement && ballOut == false; i++) {
				if (i == 0) {
					probability = chanceToSucess();
					if (probability >= 0 && probability < 1) {
						System.out.println("La bola se vuelve loca y da a 5 bumpers seguidos, +2.500 puntos");
						for (int j = 0; j < 5; j++) {
							pin.bumper();
						}
					} else if (probability >= 1 && probability < 2) {
						System.out.println(
								"Esta vez tuviste mala suerte, la bola no para de caer en una trampa tras otra, cuatro trampas seguidas, -2.400 puntos");
						for (int j = 0; j < 4; j++) {
							pin.trap();
						}
					} else if (probability >= 2 && probability < 3) {
						System.out.println("Tuviste suerte, sino te quedaban bolas salvadoras se te renueva");
						pin.newSafeBall();
					} else {
						System.out.println("No puede ser, la bola ha caido fuera");
						ballOut = pin.ballOutOfTable(true);
					}
				} else if (i == 1) {
					probability = chanceToSucess();
					System.out.println("Te la juegas a superJackpot o la trampa MeVasAOdiar");
					if (probability >= 0 && probability < 2) {
						System.out.println("Lo conseguiste, la suerte estaba de tu lado, + 10.000 puntos");
					} else {
						System.out.println(
								"Tienes mucha mala suerte, la bola cae en la trampa MeVasAOdiar y ademas otras 2 trampas normales, -6.200 puntos");
						pin.youWillHateMeTheTrap();
						pin.trap();
						pin.trap();
					}
				} else if (i == 2) {
					probability = chanceToSucess();
					if (probability >= 0 && probability < 1) {
						System.out.println("La bola consigue un combo de 3 en la rampa, +2.250 puntos");
						pin.rampCombo(3);
					} else if (probability >= 1 && probability < 2 && Pinball.getLife() == 2) {
						System.out.println("La bola va con tanta fuerza que sale hacia fuera");
						ballOut = pin.ballOutOfTable(true);
					} else if (probability >= 2 && probability < 3) {
						System.out.println("Una desgracia tras otra, la bola sale fuera");
						ballOut = pin.ballOutOfTable(true);
					} else {
						System.out.println("Tienes suerte y la bola golpea 2 bumpers");
						pin.bumper();
						pin.bumper();
					}
				}
			}
		}
		// Final del movimiento
		return ballOut;
	}

	/**
	 * probailidad de que algo suceda, numeros entre 0 y 3 (4 posibilidades)
	 * 
	 * @return probability;
	 */
	private static Double chanceToSucess() {
		double probability;
		probability = Math.random() * (4);
		return probability;
	}

}
