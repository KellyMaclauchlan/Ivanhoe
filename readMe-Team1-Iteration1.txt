Read me for the first iteration of Ivanhoe by :
Katherine Beltran 100939080
Kelly Maclauchlan 100927176
Brittany Veinot	

In our zipped file, you will find a 'client.jar' file that you will be able to run in any terminal window. Our server will have to be run in eclipse. 
Once the server has started, type 'run' to start the server. Once the server is running, in command prompt/terminal, in the directory where the 'client.jar'
file is found, type 'java -jar client.jar' to run the client game.

You will first be prompted for the ip and port of the machine the server is running.
The first player in the game will then be prompted the number of players and then asked to enter their name. 
If you are not the first player you will just be prompted the server ip and port and your name.

Once a sufficient amount of players has joined the game, the game engine will randomly generate who has picked a purple token and then will 
notify every client who picked purple and who the first player is. 
The first player will then be able to choose the tournament colour.

To play a card:
- click the card that you will would like to play 
- after you have selected your card, press the 'play card' button 
- after playing all the cards you would like in that round, click 'end turn' to announce to all other players that you have finished your turn 
- you will then see your played cards as well as the number of points that you have accumulated 

There is an option to withdraw at your end turn if you do not have valid cards for that tournament and/or round.
The game engine also accounts for cards that are unplayable or whether you play cards and your end turn however, your accumulated points aren't higher
than the last player. Essentially, the game engine accounts for all possible cases while playing a card. 

At the end of each tournament, all players are notified of the winner and the winner can choose the next the tournament colour.
The same is made for the end of the game as well. 