# Presentation

This project can be used by minecraft developers and minecraft players in order to create chats room. It proposes three commands : <code>./chatconfig</code> in order to create new chats room, <code>./opMsg</code> to send a message to a specific chat as operator, and finally <code>./chatMsg</code> to send a message to a chat in which the player is registered.

# Download

On Windows there is a restriction regarding the maximal length of a path, that's why it is preferable to clone the [minecraft-platform](https://github.com/Pierre-Emmanuel41/minecraft-game-plateform/blob/1.0_MC_1.16.5-SNAPSHOT/README.md) project and run its <code>deploy.bat</code> file before cloning this project.

Then, according to the Minecraft API version there is on the server, you should download this project by specifying the branch associated to the associated version if supported. To do so, you can use the following command line :

```git
git clone -b 1.0_MC_1.16.5-SNAPSHOT https://github.com/Pierre-Emmanuel41/minecraft-chat.git
```

Finally, to add the project as maven dependency on your maven project :

```xml
<dependency>
	<groupId>fr.pederobien.minecraft</groupId>
	<artifactId>game</artifactId>
	<version>1.0_MC_1.16.5-SNAPSHOT</version>
	<scope<provided</scope>
</dependency>
<dependency>
	<groupId>fr.pederobien.minecraft</groupId>
	<artifactId>platform</artifactId>
	<version>1.0_MC_1.16.5-SNAPSHOT</version>
	<scope<provided</scope>
</dependency>
<dependency>
	<groupId>fr.pederobien.minecraft</groupId>
	<artifactId>chat</artifactId>
	<version>1.0_MC_1.16.5-SNAPSHOT</version>
	<scope>provided</scope>
</dependency>
```

This plugins depends on the minecraft-platform and minecraft-game plugins, that is why they should be present on the server plugins folder. Moreover, like them, this plugin should also be present on the minecraft server in order to be used by several other plugins. That is why it is declared as <code>provided</code> for the dependency scope.

To see the functionalities provided by this plugin, please have a look to [this presentation](https://github.com/Pierre-Emmanuel41/minecraft-chat/blob/1.0_MC_1.16.5-SNAPSHOT/Presentation.md)
