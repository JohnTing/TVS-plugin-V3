package main.database;

import com.mongodb.DB;
import mindustry.gen.Player;

// This is basically absolutely useless peace of shit that is only needed because
// player class is cannot be constructed
public class DBPlayer {
    public String name = "noname", uuid = "", usid = "", ip = "127.0.0.1";
    public boolean admin;
    public int id;
    public Player p;

    public DBPlayer() {}

    public DBPlayer(Player player) {
        if (player != null) {
            name = player.name;
            uuid = player.uuid();
            usid = player.usid();
            ip = player.con.address;
            id = player.id;
            admin = player.admin;
            p = player;
        }
    }

}
