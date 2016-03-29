Read me for the second iteration of Ivanhoe by :
Katherine Beltran 100939080
Kelly Maclauchlan 100927176
Brittany Veinot	100954695

In our zipped file, you will find a 'client.jar' file and a ‘server.jar’ file that you will be able to run in any terminal window.

Run the server file from a terminal window by typing <java -jar server.jar> 

Once the server is running, run the client file from a terminal window by typing <java -jar client.jar> 

Do this from multiple terminal windows to use multiple clients

You will first be prompted for the ip and port of the machine the server is running. (ie: localhost 3000)

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
- Click on a player’s display to see their played cards

There is an option to withdraw at your end turn if you do not have enough valid cards for that tournament and/or round.

The game engine also accounts for cards that are unplayable, and will automatically withdraw a player who’s points are lower than any of the other players.

Essentially, the game engine accounts for all possible cases while playing a card. 

At the end of each tournament, all players are notified of the winner and the winner can choose the next the tournament colour.

At the end of a game, the winner is announced to all players.

Known issues:



AI Strategies:
	1) Withdraw AI : this AI essentially withdraws after every play. The AI, if it is the first player, will choose the tournament colour based on 
					 its first coloured card in its hand and play that card. After it will withdraw after every turn
	2) Play All AI : this AI will choose the tournament colour based on the number of coloured cards it has or by which token it needs.
					 the AI will play all of its cards that it can possibly play and then end its turn
					 if the AI has no cards to play it will withdraw
					 Sometimes, the AI will choose the tournament colour based on the tokens it will need, however does not have the cards needed and will then withdraw. This is one of the bugs with the AI that was unsolvable. 


