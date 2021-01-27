package twp.game;

import arc.struct.Seq;
import twp.database.PD;
import twp.democracy.Hud;

import static twp.Main.config;
import static twp.tools.Text.secToTime;

public class Docks implements Hud.Displayable {
    public Seq<Ship> ships = new Seq<>();

    public boolean canUse() {
       return ships.size < config.shipLimit;
    }

    @Override
    public String getMessage(PD pd) {
        StringBuilder sb = new StringBuilder();
        ships.forEach(s -> {
            sb.append(s.string());
        });

        if(sb.length() == 0) {
            return null;
        }

        return sb.toString();
    }

    @Override
    public void tick() {
        ships.eachFilter(s -> {
            s.time--;
            if(s.time <= 0) {
                s.onDelivery.run();
                return false;
            }
            return true;
        });
    }

    public static class Ship {
        int time;
        String message;
        Runnable onDelivery;

        public static String
                itemsFromCore = "<--%s<--\uf851",
                itemsToCore = "-->%s-->\uf869";

        public Ship(String message, Runnable onDelivery, int time) {
            this.message = message;
            this.onDelivery = onDelivery;
            this.time = time;
        }

        public String string() {
            return String.format( "[gray]<>[]"+message+"[gray]<>[]", secToTime(time));
        }
    }
}
