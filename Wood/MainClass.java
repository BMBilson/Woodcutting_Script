package BasicWoodcutter;
/**This is code that I created for educational purposes. I do not own RuneScape, nor do I own the client that this code
 * is running from. This falls under fair use, as I am not monetizing this script. This is the code I created for my
 * APCSP Create Task, and this does not (to my understanding) break any copyright laws. This is a clear case of fair use.
 */

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.filter.Filter;
import org.dreambot.api.methods.map.Area;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.wrappers.interactive.GameObject;
import org.dreambot.api.wrappers.interactive.NPC;
/** I do not own the above APIs used in this project. These APIs provide the commands that make this code function
 *
 */
import java.awt.*;

@ScriptManifest(category = Category.WOODCUTTING, name = "Basic WoodCutter", author = "N/A", version = 1.0)

public class MainClass extends AbstractScript{

    Area bankArea = new Area(3092, 3240, 3097, 3246, 0);
    Area treeArea = new Area(3091, 3290, 3072, 3310, 0);
/** The above define a tree and bank area. The trees are in the defined coodinates, and so is the bank. */
    @Override
    public void onStart(){
        log("Hi"); /**this prints 'Hi' in the console log of the client.*/
    }

    @Override
    public int onLoop() {
        /**
         * Chopping trees: Time to chop some trees, our inventory isn't full. We want to fill it up.
         */
        if(!getInventory().isFull()){
            if(treeArea.contains(getLocalPlayer())){ /**Searches the area for the specified tree.*/
                chopTree("Tree"); /** change "Tree" to the name of your tree. */
            }else{
                if(getWalking().walk(treeArea.getRandomTile())){
                    sleep(Calculations.random(3000, 5500));
                    /**The 'sleep' function basically means a delay in an action. The delay is, as defined, between 3
                     * and 5.5 seconds (the numbers are in milliseconds). If the area does not contain the specific tree,
                     * this function just allows the bot to roam to the defined treeArea.*/
                }
            }
        }

        /**
         * Banking: Time to bank our logs. Our inventory is full, we want to empty it.
         */
        if(getInventory().isFull()){ /**Time to bank */
            if(bankArea.contains(getLocalPlayer())){
                bank();
                /** Checks if the inventory is full. */
            }else{
                if(getWalking().walk(bankArea.getRandomTile())){
                    sleep(Calculations.random(3000, 6000));
                    /**The bot will roam back to the bank. Same idea as the code above.*//
                }
            }
        }

        return 600; /** This is time in milliseconds. This loops the above code given this time interval */
    }

    @Override
    public void onExit() {
        log("Bye"); /** Logs "Bye" in the console of the client.*/
    }

    @Override
    public void onPaint(Graphics graphics) {

    }

    private void chopTree(String nameOfTree){ /**Chops trees according to the nameofTree variable. This correlates to
     the ingame name of the tree.*/
        GameObject tree = getGameObjects().closest(gameObject -> gameObject != null && gameObject.getName().equals(nameOfTree));
        if(tree != null && tree.interact("Chop down")){
            int countLog = getInventory().count("Logs");
            sleepUntil(() -> getInventory().count("Logs") > countLog, 12000);
        }
    }

    private void bank(){
        NPC banker = getNpcs().closest(npc -> npc != null && npc.hasAction("Bank"));
        if(banker != null && banker.interact("Bank")){ /** Interacts with the banker. The null is there to ensure that
         the bot interacts with a banker, and not with nothing. Null = nothing *//
            if(sleepUntil(() -> getBank().isOpen(), 9000)){ /**Bank tab is open, waits for 9 seconds if this fails.*/
                if(getBank().depositAllExcept(item -> item != null && item.getName().contains("axe"))){
                    /**Deposits every item except for the axe the bot is using. The code does a check for any item
                     * with the name 'axe'.*/
                    if(sleepUntil(() -> !getInventory().isFull(), 8000)){
                        if(getBank().close()){
                            sleepUntil(() -> !getBank().isOpen(), 8000); /**Waits for the bank to close after banking
                             items.*//
        /** This interacts with the Banker NPCS in RuneScape. Essentially, the script is banking
         * the logs cut. The script searches for an NPC with the 'Bank' action, then it proceeds to bank for the user.
         */
        }
        }
        }
            }
        }
    }
