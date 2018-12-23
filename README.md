<p align="center"><img align="center" src="res/StargateMinecraft.png" width=500><p>
  
---

<a href="https://spigotmc.org"><img src="https://img.shields.io/badge/Minecraft-1.13.2-orange.svg"/></a> <a class="badge-align" href="https://www.codacy.com/app/Glarity/Stargate?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=Glarity/Stargate&amp;utm_campaign=Badge_Grade"><img src="https://api.codacy.com/project/badge/Grade/7613709496404781ad51f1cfacb70a12"/></a>

This Bukkit/Spigot plugin adds the Stargate and DHD into Minecraft

## Features

There are currently 2 supported gates types in mossy cobblestone, a basic sqaure portal and a 7x7 round gate.

Build a DHD 3-10 blocks away from the gate by placing a button ontop of a mossy cobblestone block. If you press on the button, a menu listing all avaiable Stargates will show. Click on a gate to teleport to it.

If a Stargate is broken after creation it will disable any travel to and from the gate. Other DHDs will be able to see the gate, but will be sent a message notifying of the damaged gate.

## Commands

### /createstargate (stargate name)

Run this command while in a built Stargate to formally create it.

### /deletestargate (stargate name)

Run this command while in the referenced Stargate to delete it from the config.

## Todo

* Check for Stargate on both North/South and East/West axes
* Teleport when walking through portal instead of using button
* Customizable Stargate portal icon
  
## Screenshots

<img src="res/Screenshot.png" width=600>
