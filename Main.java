import java.util.Scanner;

abstract class Character {
    private int health;
    protected int secondaryResource; 
    private boolean canUsePotion = true;

    public Character(int health, int resource) {
        this.health = health;
        this.secondaryResource = resource;
    }

    public void takeDamage(int damage) {
        health = Math.max(0, health - damage);
    }

    public int getHealth() {
        return health;
    }

    public void heal(int amount) {
        health = Math.min(100, health + amount); 
    }

    public void restoreResource(int amount) {
        secondaryResource = Math.min(getMaxResource(), secondaryResource + amount);
    }

    public boolean canUsePotion() {
        return canUsePotion;
    }

    public void usePotion() {
        if (canUsePotion) {
            heal(20); 
            canUsePotion = false; 
            System.out.println("Použil jsi lék, zdraví +20!");
        } else {
            System.out.println("Lék už jsi v tomto kole použil!");
        }
    }

    public void resetPotionUse() {
        canUsePotion = true; 
    }

    public boolean isAlive() {
        return health > 0; 
    }

    public abstract int getMaxResource();

    public abstract void ability1();
    public abstract void ability2();
    public abstract void ability3();
    public abstract void ability4();
    public abstract void ability5(); 
    public abstract void printSecondaryResource();
}

// Třída Warrior
class Warrior extends Character {
    public Warrior() {
        super(100, 50);  
    }

    public void ability1() {
        if (secondaryResource >= 10) {
            System.out.println("Válečník používá Útok mečem!");
            secondaryResource -= 10;
        } else {
            System.out.println("Nedostatek výdrže!");
        }
    }

    public void ability2() {
        if (secondaryResource >= 15) {
            System.out.println("Válečník používá Obranu!");
            secondaryResource -= 15;
        } else {
            System.out.println("Nedostatek výdrže!");
        }
    }

    public void ability3() {
        if (secondaryResource >= 20) {
            System.out.println("Válečník používá Silný úder!");
            secondaryResource -= 20;
        } else {
            System.out.println("Nedostatek výdrže!");
        }
    }

    public void ability4() {
        System.out.println("Válečník nemá schopnost obnovit výdrž.");
    }

    public void ability5() {
        System.out.println("Válečník vynechává kolo a obnovuje 15 výdrže.");
        restoreResource(15); 
    }

    public int getMaxResource() {
        return 50;
    }

    public void printSecondaryResource() {
        System.out.println("Výdrž: " + secondaryResource);
    }
}

// Třída Mage
class Mage extends Character {
    public Mage() {
        super(80, 100);  
    }

    public void ability1() {
        if (secondaryResource >= 20) {
            System.out.println("Mág používá Ohnivou kouli!");
            secondaryResource -= 20;
        } else {
            System.out.println("Nedostatek many!");
        }
    }

    public void ability2() {
        if (secondaryResource >= 25) {
            System.out.println("Mág používá Ledové kouzlo!");
            secondaryResource -= 25;
        } else {
            System.out.println("Nedostatek many!");
        }
    }

    public void ability3() {
        if (secondaryResource >= 30) {
            System.out.println("Mág používá Bleskový útok!");
            secondaryResource -= 30;
        } else {
            System.out.println("Nedostatek many!");
        }
    }

    public void ability4() {
        System.out.println("Mág používá Obnovení many!");
        restoreResource(30); 
    }

    public void ability5() {
        System.out.println("Mág vynechává kolo a obnovuje 15 many.");
        restoreResource(15); 
    }

    public int getMaxResource() {
        return 100;
    }

    public void printSecondaryResource() {
        System.out.println("Mana: " + secondaryResource);
    }
}

// Třída Archer
class Archer extends Character {
    public Archer() {
        super(90, 30);  
    }

    public void ability1() {
        if (secondaryResource >= 5) {
            System.out.println("Lučištník používá Rychlou střelu!");
            secondaryResource -= 5;
        } else {
            System.out.println("Nedostatek šípů!");
        }
    }

    public void ability2() {
        if (secondaryResource >= 10) {
            System.out.println("Lučištník používá Přesný zásah!");
            secondaryResource -= 10;
        } else {
            System.out.println("Nedostatek šípů!");
        }
    }

    public void ability3() {
        if (secondaryResource >= 15) {
            System.out.println("Lučištník používá Salvu šípů!");
            secondaryResource -= 15;
        } else {
            System.out.println("Nedostatek šípů!");
        }
    }

    public void ability4() {
        System.out.println("Lučištník nemá schopnost obnovit šípy.");
    }

    public void ability5() {
        System.out.println("Lučištník vynechává kolo a obnovuje 15 šípů.");
        restoreResource(15); 
    }

    public int getMaxResource() {
        return 30;
    }

    public void printSecondaryResource() {
        System.out.println("Šípy: " + secondaryResource);
    }
}

// Třída Enemy
class Enemy {
    private int health;

    public Enemy(int health) {
        this.health = health;
    }

    public void takeDamage(int damage) {
        health = Math.max(0, health - damage);
    }

    public boolean isAlive() {
        return health > 0;
    }

    public int getHealth() {
        return health;
    }

    public void takeTurn() {
        System.out.println("Nepřítel útočí!");
    }
}

// Třída GameState
class GameState {
    private Character player;
    private int enemyCount = 1;
    private Enemy enemy;
    private boolean playerTurn;
    private int defeatedEnemies = 0; 

    public GameState(Character player) {
        this.player = player;
        spawnEnemy();
        this.playerTurn = true;
    }

    public void spawnEnemy() {
        int enemyHealth = 50 + (enemyCount - 1) * 10;  
        this.enemy = new Enemy(enemyHealth);
        System.out.println("Objevil se nový nepřítel s " + enemyHealth + " životy!");
    }

    public void nextTurn() {
        if (playerTurn) {
            System.out.println("Je řada hráče.");
            player.resetPotionUse(); 
        } else {
            System.out.println("Je řada nepřítele.");
            enemy.takeTurn();
            player.takeDamage(10);  
        }

        playerTurn = !playerTurn;  
    }

    public boolean isGameOver() {
        return !player.isAlive();
    }

    public boolean isEnemyDefeated() {
        return !enemy.isAlive();
    }

    public void printStatus() {
        System.out.println("Životy hráče: " + player.getHealth());
        System.out.println("Životy nepřítele: " + enemy.getHealth());
        player.printSecondaryResource();
    }

    public void defeatEnemy() {
        if (isEnemyDefeated()) {
            System.out.println("Nepřítel byl poražen!");
            defeatedEnemies++; 
            enemyCount++;
            spawnEnemy();  
        }
    }

    public int getDefeatedEnemies() {
        return defeatedEnemies; 
    }

    public Enemy getCurrentEnemy() {
        return enemy; 
    }
}

// Hlavní třída Main
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Character player = null;

        System.out.println("Vyber si postavu:");
        System.out.println("1 - Válečník");
        System.out.println("2 - Mág");
        System.out.println("3 - Lučištník");

        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                player = new Warrior();
                System.out.println("Vybral sis Válečníka.");
                break;
            case 2:
                player = new Mage();
                System.out.println("Vybral sis Mága.");
                break;
            case 3:
                player = new Archer();
                System.out.println("Vybral sis Lučištníka.");
                break;
            default:
                System.out.println("Neplatná volba, hra končí.");
                return;
        }

        GameState gameState = new GameState(player);

        System.out.println("Hra začíná!");

        
        while (!gameState.isGameOver()) {
            gameState.printStatus();
            System.out.println("1 - První schopnost");
            System.out.println("2 - Druhá schopnost");
            System.out.println("3 - Třetí schopnost");
            System.out.println("4 - Obnovit sekundární zdroj (jen pro Mága)");
            System.out.println("5 - Použít lék (+20 zdraví)");
            System.out.println("6 - Vynechat kolo a dobít výdrž");
            System.out.print("Vyber akci: ");

            int actionChoice = scanner.nextInt();

            
            switch (actionChoice) {
                case 1:
                    player.ability1();
                    break;
                case 2:
                    player.ability2();
                    break;
                case 3:
                    player.ability3();
                    break;
                case 4:
                    player.ability4();
                    break;
                case 5:
                    player.usePotion();
                    break;
                case 6:
                    player.ability5(); 
                    continue; 
                default:
                    System.out.println("Neplatná volba, zkuste to znovu.");
                    continue;
            }

            
            if (!gameState.isEnemyDefeated()) {
                gameState.getCurrentEnemy().takeDamage(20); 
            }

            
            gameState.nextTurn();

            
            if (gameState.isEnemyDefeated()) {
                gameState.defeatEnemy();
            }
        }

        System.out.println("Hra skončila!");
        if (player.isAlive()) {
            System.out.println("Vyhrál jsi!");
        } else {
            System.out.println("Prohrál jsi.");
        }

        
        System.out.println("Počet poražených nepřátel: " + gameState.getDefeatedEnemies());

        scanner.close();
    }
}
