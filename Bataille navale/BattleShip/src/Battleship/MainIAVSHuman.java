package Battleship;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class MainIAVSHuman {
	
	static Scanner scan = new Scanner(System.in);
	
	public static void main(String[] args) {
		
		boolean res = false;
		
		Player J1 = null;
		Player J2 = null;
	
		J1 = initJoueur();		
		
		
		System.out.println("Choississez le mode du deuxième IA : ");
		System.out.println("      - 1 : Level Zero");
		System.out.println("      - 2 : Level One");
		System.out.println("      - 3 : Level Two");
		System.out.println();
		System.out.println("Votre choix : ");
		int choix = 0;
		boolean choixOk = false;
		while (choixOk == false) {
			try {
				choix = scan.nextInt();
				if (choix > 0 && choix < 4) {
					choixOk = true;
				}
			} catch (Exception e) {
				System.out.println("Entrez une coordonnée valide pour votre choix (1, 2 ou 3) !");
			}
		}
		if (choix == 1) {
			J2 = initIALevelZero("IALevelZero");
		}
		if (choix == 2) {
			J2 = initIALevelOne("IALevelOne");
		}
		if (choix == 3) {
			J2 = initIALevelTwo("IALevelTwo");
		}
		
		Player current = J2;
		Player enemy = J1;
		Player player;
		
		Coordinate coord = new Coordinate(null,false);
		ArrayList<String> listTouched = new ArrayList<String>();
		ArrayList<Coordinate> listAttack = new ArrayList<Coordinate>();
		ArrayList<String> listAttack2 = new ArrayList<String>();

		String resT;
		
		while (res == false) {
			
			player = current;
			current = enemy;
			enemy = player;
			
			System.out.println(" ");
			coord = current.missileCoord(enemy);
			if (current.getListAttack2().contains(coord.getCoord()) && current.getListTouched().contains(coord.getCoord())) {
				coord.setTouched(true);
			}
			resT = enemy.resultTir(coord);
			System.out.println(resT);
			if (resT.equals("TOUCHÉ")) {
				current.setScoreJoueur(J1.getScoreJoueur() + 1);
				listTouched = current.getListTouched();
				listTouched.add(coord.getCoord());
				current.setListTouched(listTouched);
				coord.setTouched(true);
			}
			listAttack = current.getListAttack();
			listAttack.add(coord);
			current.setListAttack(listAttack);
			listAttack2 = current.getListAttack2();
			listAttack2.add(coord.getCoord());
			current.setListAttack2(listAttack2);
			if (current == J1) {
				System.out.println("Score de " + current.getNomJoueur() + " : " + current.getScoreJoueur());
				System.out.println("Score de " + enemy.getNomJoueur() + " : " + enemy.getScoreJoueur());
				System.out.println(" ");
			} else {
				System.out.println("Score de " + enemy.getNomJoueur() + " : " + enemy.getScoreJoueur());
				System.out.println("Score de " + current.getNomJoueur() + " : " + current.getScoreJoueur());
				System.out.println(" ");
			}
			System.out.println("Grille d'attaque de " + enemy.getNomJoueur() + " :");
			System.out.println(" ");
			System.out.println("Les coordonnées de bâteau touchées -> X");
			System.out.println("Les coordonnées de bâteau non touchées -> O");
			System.out.println(" ");
			enemy.getGridAttack();
			System.out.println(" ");
			if (current.getScoreJoueur() == 17 || enemy.getScoreJoueur() == 17) {
				res = true;
			}
		}
		if (current.getScoreJoueur() == 17) {
			System.out.println(current.getNomJoueur() + " a gagné !");
			System.out.println(" ");
			System.out.println(current.getListTouched());
			System.out.println(" ");
		}
		if (enemy.getScoreJoueur() == 17){
			System.out.println(enemy.getNomJoueur() + " a gagné !");
			System.out.println(" ");
			System.out.println(enemy.getListTouched());
			System.out.println(" ");
		}
	}
	
	public static IALevelZero initIALevelZero(String nomJoueur) {
		int nbreShip = 0;
		System.out.println(" ");
		int scoreIA = 0;
		ArrayList<Ship> flotteIA = new ArrayList<Ship>();
		ArrayList<Coordinate> listAttack = new ArrayList<Coordinate>();
		ArrayList<String> listTouched = new ArrayList<String>();
		ArrayList<String> listAttack2 = new ArrayList<String>();
		ArrayList<String> listTouch = new ArrayList<String>();
		ArrayList<String> listNextMissile = new ArrayList<String>();
		boolean tirPrec = false;
		
		IALevelZero IA0 = new IALevelZero(nomJoueur, flotteIA, listAttack, listTouched, listTouch, scoreIA, listAttack2, listNextMissile, tirPrec);

		while (nbreShip<5) {
			boolean check;
			if (nbreShip == 0) {
				check = false;
				while (check == false) {
					System.out.println(" ");
					Ship a = initShipIALevelZero(IA0, 1);
					int b = a.shipSize(a.getStartCoord().getCoord(), a.getEndCoord().getCoord());
					if (IA0.rentreDansGrille(a) == true) {
						if (b == 2) {
							IA0.addShip(a);
							System.out.println(" ");
							IA0.getGridShip();
							System.out.println(" ");
							nbreShip++;
							check = true;
						}
					}
				}
			}
		    if (nbreShip == 1) {
		    		check = false;
		        while (check == false) {
		        	System.out.println(" ");
					Ship a = initShipIALevelZero(IA0, 2);
					System.out.println(a);
					int b = a.shipSize(a.getStartCoord().getCoord(), a.getEndCoord().getCoord());
					System.out.println(b);
					System.out.println(IA0.rentreDansGrille(a));
					if (IA0.rentreDansGrille(a) == true) {
						if (b == 3) {
							IA0.addShip(a);
							System.out.println(" ");
							IA0.getGridShip();
							System.out.println(" ");
							nbreShip++;
							check = true;
						}
					}	
		        }
		    }
		    if (nbreShip == 2) {
		    		check = false;
		        while (check == false) {
		        		System.out.println(" ");
					Ship a = initShipIALevelZero(IA0, 2);
					System.out.println(a);
					int b = a.shipSize(a.getStartCoord().getCoord(), a.getEndCoord().getCoord());
					System.out.println(b);
					if (IA0.rentreDansGrille(a) == true) {
						if (b == 3) {
							IA0.addShip(a);
							System.out.println(" ");
							IA0.getGridShip();
							System.out.println(" ");
							nbreShip++;
							check = true;
						}
					}
		        }
		    }
		    if (nbreShip == 3) {
		    		check = false;
		        while (check == false) {
		        		System.out.println(" ");
					Ship a = initShipIALevelZero(IA0, 3);
					System.out.println(a);
					int b = a.shipSize(a.getStartCoord().getCoord(), a.getEndCoord().getCoord());
					System.out.println(b);
					if (IA0.rentreDansGrille(a) == true) {
						if (b == 4) {
							IA0.addShip(a);
							System.out.println(" ");
							IA0.getGridShip();
							System.out.println(" ");
							nbreShip++;
							check = true;
						}
					}	
		        	}
		    	}
		    if (nbreShip == 4) {
		        check = false;
		        while (check == false) {
		        		System.out.println(" ");
					Ship a = initShipIALevelZero(IA0, 4);
					System.out.println(a);
					int b = a.shipSize(a.getStartCoord().getCoord(), a.getEndCoord().getCoord());
					System.out.println(b);
					if (IA0.rentreDansGrille(a) == true) {
						if (b == 5) {
							IA0.addShip(a);
							System.out.println(" ");
							IA0.getGridShip();
							System.out.println(" ");
							nbreShip++;
							check = true;
						}
					}
		        }
		    	}
		}

		return IA0;
	}
	
	public static Ship initShipIALevelZero(IALevelZero IA0, int size) {
		Random r = new Random();
		String x = IA0.generateCoord();
		while (IA0.allCoordinatedShip().contains(x) == true) {
			x = IA0.generateCoord();
		}
		
		Coordinate coord1 = new Coordinate(x,false);
		
		ArrayList<String> list = IA0.generateList(size, coord1);
		while (list.size() == 0) {
			x = IA0.generateCoord();
			while (IA0.allCoordinatedShip().contains(x) == true) {
				x = IA0.generateCoord();
			}
			coord1.setCoord(x);
			
			list = IA0.generateList(size, coord1);
		}
		System.out.println(x);
		System.out.println(list);
		int n = r.nextInt(list.size());
		String y = list.get(n);
		System.out.println(y);
		Coordinate coord2 = new Coordinate(y,false);
		
		Ship a = new Ship(coord1,coord2);
		return a;
	}
	
	public static IALevelOne initIALevelOne(String nomJoueur) {
		int nbreShip = 0;
		System.out.println(" ");
		int scoreIA = 0;
		ArrayList<Ship> flotteIA = new ArrayList<Ship>();
		ArrayList<Coordinate> listAttack = new ArrayList<Coordinate>();
		ArrayList<String> listTouched = new ArrayList<String>();
		ArrayList<String> listAttack2 = new ArrayList<String>();
		ArrayList<String> listTouch = new ArrayList<String>();
		ArrayList<String> listNextMissile = new ArrayList<String>();
		boolean tirPrec = false;
		
		IALevelOne IA1 = new IALevelOne(nomJoueur, flotteIA, listAttack, listTouched, listTouch, scoreIA, listAttack2, listNextMissile, tirPrec);

		while (nbreShip<5) {
			boolean check;
			if (nbreShip == 0) {
				check = false;
				while (check == false) {
					System.out.println(" ");
					Ship a = initShipIALevelOne(IA1, 1);
					int b = a.shipSize(a.getStartCoord().getCoord(), a.getEndCoord().getCoord());
					if (IA1.rentreDansGrille(a) == true) {
						if (b == 2) {
							IA1.addShip(a);
							System.out.println(" ");
							IA1.getGridShip();
							System.out.println(" ");
							nbreShip++;
							check = true;
						}
					}
				}
			}
		    if (nbreShip == 1) {
		    		check = false;
		        while (check == false) {
		        	System.out.println(" ");
					Ship a = initShipIALevelOne(IA1, 2);
					System.out.println(a);
					int b = a.shipSize(a.getStartCoord().getCoord(), a.getEndCoord().getCoord());
					System.out.println(b);
					System.out.println(IA1.rentreDansGrille(a));
					if (IA1.rentreDansGrille(a) == true) {
						if (b == 3) {
							IA1.addShip(a);
							System.out.println(" ");
							IA1.getGridShip();
							System.out.println(" ");
							nbreShip++;
							check = true;
						}
					}	
		        }
		    }
		    if (nbreShip == 2) {
		    		check = false;
		        while (check == false) {
		        		System.out.println(" ");
					Ship a = initShipIALevelOne(IA1, 2);
					System.out.println(a);
					int b = a.shipSize(a.getStartCoord().getCoord(), a.getEndCoord().getCoord());
					System.out.println(b);
					if (IA1.rentreDansGrille(a) == true) {
						if (b == 3) {
							IA1.addShip(a);
							System.out.println(" ");
							IA1.getGridShip();
							System.out.println(" ");
							nbreShip++;
							check = true;
						}
					}
		        }
		    }
		    if (nbreShip == 3) {
		    		check = false;
		        while (check == false) {
		        		System.out.println(" ");
					Ship a = initShipIALevelOne(IA1, 3);
					System.out.println(a);
					int b = a.shipSize(a.getStartCoord().getCoord(), a.getEndCoord().getCoord());
					System.out.println(b);
					if (IA1.rentreDansGrille(a) == true) {
						if (b == 4) {
							IA1.addShip(a);
							System.out.println(" ");
							IA1.getGridShip();
							System.out.println(" ");
							nbreShip++;
							check = true;
						}
					}	
		        	}
		    	}
		    if (nbreShip == 4) {
		        check = false;
		        while (check == false) {
		        		System.out.println(" ");
					Ship a = initShipIALevelOne(IA1, 4);
					System.out.println(a);
					int b = a.shipSize(a.getStartCoord().getCoord(), a.getEndCoord().getCoord());
					System.out.println(b);
					if (IA1.rentreDansGrille(a) == true) {
						if (b == 5) {
							IA1.addShip(a);
							System.out.println(" ");
							IA1.getGridShip();
							System.out.println(" ");
							nbreShip++;
							check = true;
						}
					}
		        }
		    	}
		}

		return IA1;
	}
	
	public static Ship initShipIALevelOne(IALevelOne IA1, int size) {
		Random r = new Random();
		String x = IA1.generateCoord();
		while (IA1.allCoordinatedShip().contains(x) == true) {
			x = IA1.generateCoord();
		}
		
		Coordinate coord1 = new Coordinate(x,false);
		
		ArrayList<String> list = IA1.generateList(size, coord1);
		while (list.size() == 0) {
			x = IA1.generateCoord();
			while (IA1.allCoordinatedShip().contains(x) == true) {
				x = IA1.generateCoord();
			}
			coord1.setCoord(x);
			
			list = IA1.generateList(size, coord1);
		}
		System.out.println(x);
		System.out.println(list);
		int n = r.nextInt(list.size());
		String y = list.get(n);
		System.out.println(y);
		Coordinate coord2 = new Coordinate(y,false);
		
		Ship a = new Ship(coord1,coord2);
		return a;
	}
	
	public static IALevelTwo initIALevelTwo(String nomJoueur) {
		int nbreShip = 0;
		System.out.println(" ");
		int scoreIA = 0;
		ArrayList<Ship> flotteIA = new ArrayList<Ship>();
		ArrayList<Coordinate> listAttack = new ArrayList<Coordinate>();
		ArrayList<String> listTouched = new ArrayList<String>();
		ArrayList<String> listAttack2 = new ArrayList<String>();
		ArrayList<String> listTouch = new ArrayList<String>();
		ArrayList<String> listNextMissile = new ArrayList<String>();
		boolean tirPrec = false;
		
		
		IALevelTwo IA2 = new IALevelTwo(nomJoueur, flotteIA, listAttack, listTouched, listTouch, scoreIA, listAttack2, listNextMissile, tirPrec);


		while (nbreShip<5) {
			boolean check;
			if (nbreShip == 0) {
				check = false;
				while (check == false) {
					System.out.println(" ");
					Ship a = initShipIALevelTwo(IA2, 1);
					int b = a.shipSize(a.getStartCoord().getCoord(), a.getEndCoord().getCoord());
					if (IA2.rentreDansGrille(a) == true) {
						if (b == 2) {
							IA2.addShip(a);
							System.out.println(" ");
							IA2.getGridShip();
							System.out.println(" ");
							nbreShip++;
							check = true;
						}
					}
				}
			}
		    if (nbreShip == 1) {
		    		check = false;
		        while (check == false) {
		        	System.out.println(" ");
					Ship a = initShipIALevelTwo(IA2, 2);
					int b = a.shipSize(a.getStartCoord().getCoord(), a.getEndCoord().getCoord());
					if (IA2.rentreDansGrille(a) == true) {
						if (b == 3) {
							IA2.addShip(a);
							System.out.println(" ");
							IA2.getGridShip();
							System.out.println(" ");
							nbreShip++;
							check = true;
						}
					}	
		        }
		    }
		    if (nbreShip == 2) {
		    		check = false;
		        while (check == false) {
		        		System.out.println(" ");
					Ship a = initShipIALevelTwo(IA2, 2);
					int b = a.shipSize(a.getStartCoord().getCoord(), a.getEndCoord().getCoord());
					if (IA2.rentreDansGrille(a) == true) {
						if (b == 3) {
							IA2.addShip(a);
							System.out.println(" ");
							IA2.getGridShip();
							System.out.println(" ");
							nbreShip++;
							check = true;
						}
					}
		        }
		    }
		    if (nbreShip == 3) {
		    		check = false;
		        while (check == false) {
		        		System.out.println(" ");
					Ship a = initShipIALevelTwo(IA2, 3);
					int b = a.shipSize(a.getStartCoord().getCoord(), a.getEndCoord().getCoord());
					if (IA2.rentreDansGrille(a) == true) {
						if (b == 4) {
							IA2.addShip(a);
							System.out.println(" ");
							IA2.getGridShip();
							System.out.println(" ");
							nbreShip++;
							check = true;
						}
					}	
		        	}
		    	}
		    if (nbreShip == 4) {
		        check = false;
		        while (check == false) {
		        		System.out.println(" ");
					Ship a = initShipIALevelTwo(IA2, 4);
					int b = a.shipSize(a.getStartCoord().getCoord(), a.getEndCoord().getCoord());
					if (IA2.rentreDansGrille(a) == true) {
						if (b == 5) {
							IA2.addShip(a);
							System.out.println(" ");
							IA2.getGridShip();
							System.out.println(" ");
							nbreShip++;
							check = true;
						}
					}
		        }
		    	}
		}

		return IA2;
	}
	
	public static Ship initShipIALevelTwo(IALevelTwo IA1, int size) {
		Random r = new Random();
		String x = IA1.generateCoord();
		while (IA1.allCoordinatedShip().contains(x) == true) {
			x = IA1.generateCoord();
		}
		
		Coordinate coord1 = new Coordinate(x,false);
		
		ArrayList<String> list = IA1.generateList(size, coord1);
		while (list.size() == 0) {
			x = IA1.generateCoord();
			while (IA1.allCoordinatedShip().contains(x) == true) {
				x = IA1.generateCoord();
			}
			coord1.setCoord(x);
			
			list = IA1.generateList(size, coord1);
		}
		System.out.println(x);
		System.out.println(list);
		int n = r.nextInt(list.size());
		String y = list.get(n);
		System.out.println(y);
		Coordinate coord2 = new Coordinate(y,false);
		
		Ship a = new Ship(coord1,coord2);
		return a;
	}
	
	public static Player initJoueur() {
		int nbreShip = 0;
		System.out.println(" ");
		int scoreJoueur = 0;
		ArrayList<Ship> flotteJoueur = new ArrayList<Ship>();
		ArrayList<Coordinate> listAttack = new ArrayList<Coordinate>();
		ArrayList<String> listTouched = new ArrayList<String>();
		ArrayList<String> listAttack2 = new ArrayList<String>();

		System.out.println("Prénom du joueur : ");
		String nomJoueur = scan.next();
		
		Player joueur = new Player(nomJoueur, flotteJoueur, listAttack, listTouched, listAttack2, scoreJoueur);


		while (nbreShip<5) {
			boolean check;
			if (nbreShip == 0) {
				check = false;
				while (check == false) {
		        		System.out.println(" ");
		        		System.out.println("Places your first boat: the Destroyer (on 2 consecutive squares) !");
					System.out.println(" ");
					Ship a = initShip(joueur);
					int b = a.shipSize(a.getStartCoord().getCoord(), a.getEndCoord().getCoord());
					if (joueur.rentreDansGrille(a) == true) {
						if (b == 2) {
							flotteJoueur.add(a);
							joueur.addShip(a);
							System.out.println(" ");
							joueur.getGridShip();
							System.out.println(" ");
							nbreShip++;
							check = true;
						} else {
							System.out.println("Mauvaises coordonnées, redonnes deux nouvelles coordonnées pour le Destroyer !");
						}
					} else {
		    			System.out.println("Mauvaises coordonnées, redonnes deux nouvelles coordonnées pour le Destroyer !");
					}
				}
			}
		    if (nbreShip == 1) {
		    		check = false;
		        while (check == false) {
					System.out.println(" ");
					System.out.println("Places your first boat: the Submarine (on 3 consecutive squares) !");
					System.out.println(" ");
					Ship a = initShip(joueur);
					int b = a.shipSize(a.getStartCoord().getCoord(), a.getEndCoord().getCoord());
					if (joueur.rentreDansGrille(a) == true) {
						if (b == 3) {
							flotteJoueur.add(a);
							joueur.addShip(a);
							System.out.println(" ");
							joueur.getGridShip();
							System.out.println(" ");
							nbreShip++;
							check = true;
						} else {
							System.out.println("Mauvaises coordonnées, redonnes deux nouvelles coordonnées pour le Submarine !");
						}
					} else {
						System.out.println("Mauvaises coordonnées, redonnes deux nouvelles coordonnées pour le Submarine !");
					}
		        }
		    }
		    if (nbreShip == 2) {
		    		check = false;
		        while (check == false) {
					System.out.println(" ");
					System.out.println("Places your first boat: the Cruiser (on 3 consecutive squares) !");
					System.out.println(" ");
					Ship a = initShip(joueur);
					int b = a.shipSize(a.getStartCoord().getCoord(), a.getEndCoord().getCoord());
					if (joueur.rentreDansGrille(a) == true) {
						if (b == 3) {
							flotteJoueur.add(a);
							joueur.addShip(a);
							System.out.println(" ");
							joueur.getGridShip();
							System.out.println(" ");
							nbreShip++;
							check = true;
						} else {
							System.out.println("Mauvaises coordonnées, redonnes deux nouvelles coordonnées pour le Cruiser !");
						}
					} else {
						System.out.println("Mauvaises coordonnées, redonnes deux nouvelles coordonnées pour le Cruiser !");
					}
		        }
		    }
		    if (nbreShip == 3) {
		    		check = false;
		        while (check == false) {
					System.out.println(" ");
					System.out.println("Places your first boat: the Battleship (on 4 consecutive squares) !");
					System.out.println(" ");
					Ship a = initShip(joueur);
					int b = a.shipSize(a.getStartCoord().getCoord(), a.getEndCoord().getCoord());
					if (joueur.rentreDansGrille(a) == true) {
						if (b == 4) {
							flotteJoueur.add(a);
							joueur.addShip(a);
							System.out.println(" ");
							joueur.getGridShip();
							System.out.println(" ");
							nbreShip++;
							check = true;
						} else {
							System.out.println("Mauvaises coordonnées, redonnes deux nouvelles coordonnées pour le Battleship !");
						}
					} else {
						System.out.println("Mauvaises coordonnées, redonnes deux nouvelles coordonnées pour le Battleship !");
					}	
		        	}
		    	}
		    if (nbreShip == 4) {
		        check = false;
		        while (check == false) {
		        		System.out.println(" ");
		        		System.out.println("Places your first boat: the Carrier (on 5 consecutive squares) !");
					System.out.println(" ");
					Ship a = initShip(joueur);
					int b = a.shipSize(a.getStartCoord().getCoord(), a.getEndCoord().getCoord());
					if (joueur.rentreDansGrille(a) == true) {
						if (b == 5) {
							flotteJoueur.add(a);
							joueur.addShip(a);
							System.out.println(" ");
							joueur.getGridShip();
							System.out.println(" ");
							nbreShip++;
							check = true;
						} else {
							System.out.println("Mauvaises coordonnées, redonnes deux nouvelles coordonnées pour le Carrier !");
						}
					} else {
						System.out.println("Mauvaises coordonnées, redonnes deux nouvelles coordonnées pour le Carrier !");
					}
		        }
		    	}
		}

		return joueur;


	}

	
	public static Ship initShip(Player player) {
		System.out.println("Entrez la coordonnée de départ de votre bateau : ");
		String x = scan.next();
		Coordinate coord1 = new Coordinate(x,false);
		while(coord1.validCoordinate(x) == false) {
			System.out.println("Mauvaise coordonnée. Entrez une nouvelle coordonnée de départ de votre bateau : ");
			x = scan.next();
			coord1.setCoord(x);
		}
		System.out.println("Entrez la coordonnée de fin de votre bateau : ");
		String y = scan.next();
		Coordinate coord2 = new Coordinate(y,false);
		while(coord2.validCoordinate(y) == false) {
			System.out.println("Mauvaise coordonnée. Entrez une nouvelle coordonnée de départ de votre bateau : ");
			y = scan.next();
			coord2.setCoord(y);
		}
		Ship a = new Ship(coord1,coord2);
		return a;
	}

}

