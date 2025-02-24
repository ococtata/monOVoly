package controller.game.monovoly;

import java.util.InputMismatchException;
import java.util.List;

import config.BoardConfig;
import config.ColorConfig;
import config.GameConfig;
import manager.GameManager;
import model.block.CardBlock;
import model.block.GenericBlock;
import model.block.JailBlock;
import model.block.PropertyBlock;
import model.card.CardFirstTurn;
import model.entity.Enemy;
import model.entity.Entity;
import model.entity.Player;
import model.gacha.character.AinzOoalGown;
import model.gacha.character.BaseCharacter;
import model.gacha.character.Cocytus;
import model.gacha.character.Demiurge;
import model.gacha.character.ShalltearBloodfallen;
import model.game.Dice;
import model.game.GameBoard;
import utility.Random;
import utility.Scanner;
import utility.TextUtil;
import view.BaseView;
import view.game.map.MapSpawnView;
import view.game.monovoly.MonovolyMap;

public class MonovolyMapController implements Scanner, Random {
	private MonovolyMap monovolyMap;
	private int playerOddRollsLeft = 3;
	private int playerEvenRollsLeft = 3;
	private int enemyOddRollsLeft = 3;
	private int enemyEvenRollsLeft = 3;
	private boolean playerQuit = false;
	private boolean isGameOver = false;

	public MonovolyMapController(MonovolyMap monovolyMap) {
		this.monovolyMap = monovolyMap;
		Enemy enemy = new Enemy("Enemy", 0);
		GameManager.getInstance().setEnemy(enemy);
		GameManager.getInstance().getPlayer().setMoney(GameConfig.STARTING_MONEY);
		GameManager.getInstance().getEnemy().setMoney(GameConfig.STARTING_MONEY);

		GameManager.getInstance().getPlayer().updateTotalAssets();
		GameManager.getInstance().getEnemy().updateTotalAssets();

		isGameOver = false;
		playerQuit = false;
	}

	public void startGame() {
		if (GameManager.getInstance().isFirstTurn()) {
			firstTurn();
		} else {
			if (isGameOver) {
				return;
			}

			beginTurn();
			endTurn();
			switchTurn();
		}
	}

	private void switchTurn() {
		GameManager.getInstance().setPlayerTurn(!GameManager.getInstance().isPlayerTurn());
	}

	private void firstTurn() {
		CardFirstTurn cardFirstTurn = new CardFirstTurn();
		cardFirstTurn.selectFirst();
		GameManager.getInstance().setFirstTurn(false);
	}

	private void printGameInfo() {
		TextUtil.clearScreen();
		GameManager.getInstance().getGameBoard().printBoard();
		TextUtil.printHorizontalBorder(
				BoardConfig.BLOCK_WIDTH * BoardConfig.BOARD_WIDTH + (BoardConfig.BOARD_WIDTH - 1));
		showStats();
		TextUtil.printHorizontalBorder(
				BoardConfig.BLOCK_WIDTH * BoardConfig.BOARD_WIDTH + (BoardConfig.BOARD_WIDTH - 1));
	}

	private void beginTurn() {
	    Dice dice = new Dice();
	    boolean rollAgain;

	    do {
	        updateRank();
	        printGameInfo();

	        Entity currentEntity = GameManager.getInstance().isPlayerTurn() ? GameManager.getInstance().getPlayer()
	                : GameManager.getInstance().getEnemy();

	        removeEntityFromJailBlockIfNecessary(currentEntity);

	        if (currentEntity.isInJail()) {
	            rollAgain = handleJailTurn(currentEntity, dice);
	        } else {
	            rollAgain = GameManager.getInstance().isPlayerTurn() ? playerTurn(dice) : enemyTurn(dice);
	        }

	        if (isGameOver) {
	            break;
	        }

	    } while (rollAgain);
	}

	private void removeEntityFromJailBlockIfNecessary(Entity entity) {
		List<GenericBlock> blockList = GameManager.getInstance().getGameBoard().getBlockList();
	    if (!entity.isInJail()) {
	    	JailBlock jailBlock = null;
	    	for(GenericBlock block : blockList) {
	    		if(block instanceof JailBlock) {
	    			jailBlock = (JailBlock) block;
	    			break;
	    		}
	    	}
	    	
	    	if(jailBlock.getPiecesOnBlock().contains(entity)) {
	    		jailBlock.removePiece(entity);
	    	}
	    }
	}

	private boolean handleJailTurn(Entity entity, Dice dice) {
		int choice = -1;
		int jailTax = (int) (GameConfig.JAIL_TAX_MODIFIER
				* (entity.getTotalAssest() * GameConfig.BLOCK_TAX_BASE_DEDUCT_PERCENTAGE));

		if (entity instanceof Player) {
			do {
				System.out.println(" " + entity.getName() + " is in jail. What do you want to do?");
				System.out.println(" 1. Roll doubles to get out.");
				System.out.println(" 2. Pay $" + jailTax + " to get out.");
				System.out.print(" >> ");

				try {
					choice = scan.nextInt();
					scan.nextLine();

					if (choice < 1 || choice > 2) {
						System.out.println(" Invalid choice!");
						TextUtil.pressEnter();
					}
				} catch (InputMismatchException e) {
					scan.nextLine();
					System.out.println(" Invalid input!");
					TextUtil.pressEnter();
					choice = -1;
				}
			} while (choice < 1 || choice > 2);
		} 
		else {
			choice = rand.nextInt(2) + 1;
			System.out.println(" " + entity.getName() + " chose to " + (choice == 1 ? "roll dice!" : "pay!"));
		}

		if (choice == 1) {
			int[] rollResult = { -1, -1 };
			System.out.println(" " + entity.getName() + " is trying to get out of jail!");

			System.out.println();
			TextUtil.pressEnter();

			rollResult = dice.roll("ANY");
			System.out.println(" " + entity.getName() + " rolled " + rollResult[0] + " and " + rollResult[1] + ".");

			if (rollResult[0] == rollResult[1]) {
				entity.setInJail(false);
				System.out.println(" " + entity.getName() + " successfully got out of jail!");
				System.out.println();

				return false;
			} else {
				System.out.println(" " + entity.getName() + " failed to get out of jail.");
				return false;
			}
		} else {
			entity.pay(null, jailTax);
			entity.setInJail(false);
			System.out.println();
			System.out.println(" " + entity.getName() + " paid $" + jailTax + " and is free!");
			System.out.println();

			GameBoard gameBoard = GameManager.getInstance().getGameBoard();
			return false;
		}
	}

	private boolean playerTurn(Dice dice) {
		gameMenu();

		try {
			int choice = scan.nextInt();
			scan.nextLine();

			if (choice >= 1 && choice <= 3) {
				return handlePlayerRoll(choice, dice);
			} else if (choice == 4) {
				TextUtil.clearScreen();
				showProperties();
				return true;
			} else if (choice == 5) {
				playerQuit = true;
				gameOver(null);
				return false;
			}
		} catch (InputMismatchException e) {
			System.out.println(" Invalid input. Please enter a number.");
			scan.nextLine();
			TextUtil.pressEnter();
			return true;
		}

		return true;
	}

	private boolean handlePlayerRoll(int choice, Dice dice) {
		int[] rollResult = { -1, -1 };

		switch (choice) {
		case 1:
			if (playerOddRollsLeft > 0) {
				rollResult = dice.roll("ODD");
				playerOddRollsLeft--;
			} else {
				System.out.println(" You have used all 3 ODD rolls!");
				TextUtil.pressEnter();
				return false;
			}
			break;
		case 2:
			rollResult = dice.roll("ANY");
			break;
		case 3:
			if (playerEvenRollsLeft > 0) {
				rollResult = dice.roll("EVEN");
				playerEvenRollsLeft--;
			} else {
				System.out.println(" You have used all 3 EVEN rolls!");
				TextUtil.pressEnter();
				return false;
			}
			break;
		default:
			System.out.println(" Input must be 1-3!");
			TextUtil.pressEnter();
			return false;
		}

		return processRoll(rollResult, GameManager.getInstance().getPlayer());
	}

	private boolean enemyTurn(Dice dice) {
		System.out.println(" Enemy's turn");
		try {
			Thread.sleep(1200);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int[] rollResult = handleEnemyRoll(dice);
		return processRoll(rollResult, GameManager.getInstance().getEnemy());
	}

	private int[] handleEnemyRoll(Dice dice) {
		while (true) {
			int choice = rand.nextInt(3);

			switch (choice) {
			case 0:
				if (enemyOddRollsLeft > 0) {
					enemyOddRollsLeft--;
					return dice.roll("ODD");
				}
				break;
			case 1:
				if (enemyEvenRollsLeft > 0) {
					enemyEvenRollsLeft--;
					return dice.roll("EVEN");
				}
				break;
			case 2:
				return dice.roll("ANY");

			}
		}
	}

	private boolean processRoll(int[] rollResult, Entity entity) {
		System.out.println(" " + entity.getName() + " rolled: " + (rollResult[0] + rollResult[1]));
		TextUtil.pressEnter();
		moveWithAnimation(entity, rollResult[0] + rollResult[1]);
		TextUtil.printHorizontalBorder(
				BoardConfig.BLOCK_WIDTH * BoardConfig.BOARD_WIDTH + (BoardConfig.BOARD_WIDTH - 1));

		if (rollResult[0] == rollResult[1]) {
			TextUtil.pressEnter();
			TextUtil.clearScreen();
			GameManager.getInstance().getGameBoard().printBoard();
			TextUtil.printHorizontalBorder(
					BoardConfig.BLOCK_WIDTH * BoardConfig.BOARD_WIDTH + (BoardConfig.BOARD_WIDTH - 1));
			System.out.println(" " + entity.getName() + " rolled doubles! Rolling again!");
			TextUtil.pressEnter();
			return true;
		}

		return false;
	}

	private void gameMenu() {
		System.out.println();
		System.out.println(" 1. Roll dice (ODD) [" + playerOddRollsLeft + "/3]");
		System.out.println(" 2. Roll dice (ANY)");
		System.out.println(" 3. Roll dice (EVEN) [" + playerEvenRollsLeft + "/3]");
		System.out.println(" 4. View My Properties");
		System.out.println(" 5. Exit Game");
		System.out.print(" >> ");
	}

	private void showProperties() {
		List<PropertyBlock> ownedProperties = GameManager.getInstance().getPlayer().getOwnedProperties();

		if (ownedProperties.isEmpty()) {
			System.out.println(" You don't own any properties yet.");
			TextUtil.pressEnter();
			return;
		}

		int pageSize = 3;
		int currentPage = 0;

		while (true) {
			TextUtil.clearScreen();
			int startIndex = currentPage * pageSize;
			int endIndex = Math.min((currentPage + 1) * pageSize, ownedProperties.size());

			System.out.println(" Your Properties (Page " + (currentPage + 1) + " of "
					+ (int) Math.ceil((double) ownedProperties.size() / pageSize) + "):");

			for (int i = startIndex; i < endIndex; i++) {
				PropertyBlock property = ownedProperties.get(i);
				TextUtil.printHorizontalBorder(
						BoardConfig.BLOCK_WIDTH * BoardConfig.BOARD_WIDTH + (BoardConfig.BOARD_WIDTH - 1));
				System.out.println(" " + (i + 1) + ". " + property.getName());
				TextUtil.printHorizontalBorder(
						BoardConfig.BLOCK_WIDTH * BoardConfig.BOARD_WIDTH + (BoardConfig.BOARD_WIDTH - 1));
				System.out.println("   Value: $" + property.getPrice());
				System.out.println("   Toll: $" + property.calculateToll(property));
				System.out.println("   Building Level: " + property.getBuildingLevel());
				System.out.println("   Has Landmark: " + (property.hasLandmark() ? "Yes" : "No"));
				System.out.println("   Has Festival: " + (property.isFestival() ? "Yes" : "No"));
				System.out.println();
			}
			TextUtil.printHorizontalBorder(
					BoardConfig.BLOCK_WIDTH * BoardConfig.BOARD_WIDTH + (BoardConfig.BOARD_WIDTH - 1));

			if (endIndex < ownedProperties.size()) {
				System.out.println(" 1. Previous Page");
				System.out.println(" 2. Next Page");
				System.out.println(" 3. Back to Game Menu");
			} else if (currentPage > 0) {
				System.out.println(" 1. Previous Page");
				System.out.println(" 3. Back to Game Menu");
			} else {
				System.out.println(" 3. Back to Game Menu");
			}

			System.out.print(" >> ");

			int choice;
			try {
				choice = scan.nextInt();
				scan.nextLine();
			} catch (InputMismatchException e) {
				scan.nextLine();
				System.out.println(" Invalid input. Please enter a number.");
				TextUtil.pressEnter();
				continue;
			}

			switch (choice) {
			case 1:
				if (currentPage > 0) {
					currentPage--;
				} else {
					System.out.println(" You are already on the first page.");
					TextUtil.pressEnter();
				}
				break;
			case 2:
				if (endIndex < ownedProperties.size()) {
					currentPage++;
				} else {
					System.out.println(" You are already on the last page.");
					TextUtil.pressEnter();
				}
				break;
			case 3:
				return;
			default:
				System.out.println(" Invalid choice.");
				TextUtil.pressEnter();
			}
		}
	}

	private void moveWithAnimation(Entity entity, int steps) {
	    GameBoard gameBoard = GameManager.getInstance().getGameBoard();
	    int currentIndex = entity.getBoardIndex();

	    for (int i = 0; i < steps; i++) {
	        gameBoard.getBlockList().get(currentIndex).removePiece(entity);
	        int nextIndex = (currentIndex + 1) % gameBoard.getBlockList().size();
	        gameBoard.getBlockList().get(nextIndex).addPiece(entity);

	        entity.setBoardIndex(nextIndex);

	        printGameInfo();

	        try {
	            Thread.sleep(350);
	        } 
	        catch (InterruptedException e) {
	            Thread.currentThread().interrupt();
	        }

	        currentIndex = nextIndex;

	        Entity opponent = entity.getEnemy();
	        if (opponent != null && opponent.getBoardIndex() == currentIndex && entity.getEquippedCharacter() instanceof ShalltearBloodfallen) {
	            ShalltearBloodfallen shalltear = (ShalltearBloodfallen) entity.getEquippedCharacter();
	            shalltear.useSkill(entity, opponent);
	        }
	        else if (opponent != null && opponent.getBoardIndex() == currentIndex && entity.getEquippedCharacter() instanceof Cocytus) {
	            Cocytus cocytus = (Cocytus) entity.getEquippedCharacter();
	            cocytus.useSkill(entity, opponent);
	        }
	    }

	    GenericBlock landedBlock = gameBoard.getBlockList().get(currentIndex);
	    printGameInfo();

	    try {
	        Thread.sleep(700);
	    } catch (InterruptedException e) {
	        Thread.currentThread().interrupt();
	    }
	    showBlockInfo(entity, landedBlock);
	    landedBlock.onLand(entity);
	}

	private void showBlockInfo(Entity piece, GenericBlock block) {
		System.out.println(" " + piece.getName() + " landed on a '" + block.getType() + "' block!");
		System.out.println();

		if (!(block instanceof CardBlock)) {
			System.out.println(" Name: " + block.getName());
			System.out.println(" Desc: " + block.getDesc());
			System.out.println();

			if (block instanceof PropertyBlock) {
				PropertyBlock propertyBlock = (PropertyBlock) block;
				Entity owner = propertyBlock.getOwner();

				if (owner == null) {
					System.out.println(" Owner\t\t: None");
					System.out.println(" Price\t\t: $" + propertyBlock.getPrice());
				} else {
					System.out.println(" Owner\t\t: " + owner.getName());
					System.out.println(" Price\t\t: $" + propertyBlock.getPrice());
					System.out.println(" Building Level\t: " + propertyBlock.getBuildingLevel());

					if (propertyBlock.hasLandmark()) {
						System.out.print(" Landmark\t: ");
						showLandmarkInfo(propertyBlock);
					} else {
						System.out.println(" Landmark\t: (NO)");
					}
				}
			}
		}
		TextUtil.printHorizontalBorder(
				BoardConfig.BLOCK_WIDTH * BoardConfig.BOARD_WIDTH + (BoardConfig.BOARD_WIDTH - 1));
	}

	private void showLandmarkInfo(PropertyBlock property) {
		System.out.println(property.getLandmarkName());
		System.out.println("   " + property.getLandmarkDesc());
	}

	public static void showStats() {
		Entity entity = GameManager.getInstance().isPlayerTurn() ? GameManager.getInstance().getPlayer()
				: GameManager.getInstance().getEnemy();
		printStatsWithCharacter(entity);
	}

	private static void printStatsWithCharacter(Entity entity) {
		System.out.print(" " + (entity.getRank() == 1 ? "(1st)" : "(2nd)"));
		System.out.print("\t " + entity.getName());
		String money = ColorConfig.GREEN + "$ "  + entity.getMoney() + ColorConfig.RESET;
		String total = ColorConfig.GOLD + entity.getTotalAssest() + ColorConfig.RESET;
				System.out.println("\t\t\t " + money + "\t\t Total: " + total);

		BaseCharacter equippedCharacter = entity.getEquippedCharacter();
		if (equippedCharacter != null) {
			System.out.println(" \t\t\t Character: \t " + equippedCharacter.getNameColor() + equippedCharacter.getName()
					+ ColorConfig.RESET);
		} else {
			System.out.println(" \t\t\t Character: \t None");
		}
	}

	private void updateRank() {
		Enemy enemy = GameManager.getInstance().getEnemy();
		Player player = GameManager.getInstance().getPlayer();

		if (player.getTotalAssest() > enemy.getTotalAssest()) {
			player.setRank(1);
			enemy.setRank(2);
		} else {
			player.setRank(2);
			enemy.setRank(1);
		}
	}

	public void endTurn() {
		Entity piece = (GameManager.getInstance().isPlayerTurn() ? GameManager.getInstance().getPlayer()
				: GameManager.getInstance().getEnemy());

		for (PropertyBlock property : piece.getOwnedProperties()) {
			property.decrementFestivalDuration();
		}
	}

	private void gameOver(Entity loser) {
		if (loser instanceof Enemy) {
			GameManager.getInstance().setEnemy(null);
		}

		if (!playerQuit) {
			if (loser instanceof Player) {
				System.out.println(" Enemy wins!");
			} else {
				System.out.println(" Player wins!");
				playerWin(GameManager.getInstance().getPlayer());
			}
			TextUtil.pressEnter();
		}

		isGameOver = true;

		MapSpawnView previousView = new MapSpawnView();
		GameManager.getInstance().setCurrentView(previousView);

		if (previousView != null) {
			previousView.show();
		}
	}

	private void playerWin(Player player) {
		player.setGems(player.getGems() + 1000);
		player.addExp(10);
		System.out.println(" " + player.getName() + " earned 1000 gems and 10 XP!");
		System.out.println();
		TextUtil.pressEnter();
	}
}