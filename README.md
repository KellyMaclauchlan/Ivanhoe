# Ivanhoe
Ivanhoe card game
Video of game play: 
https://www.youtube.com/watch?v=xGhA-HlHu4M&feature=youtu.be
Game description: 
https://boardgamegeek.com/boardgame/883/ivanhoe
This was our semester long project which we got the opportunity to work in a group of 3 people

Features:

Waiting for More Players Notification
Tournament Colour Options (depending on the cards in your hand and if you are choosen to be the first player)
Background colour (changes to the current tournament colour)
Action Log (all players are logged so opponents can know what is going on)
Inactive Player's Cards are Greyed Out
Card Description When Clicked
Example of All Action Cards and Supporter Cards
Token Choice When Winning a Purple Tournament

Read me for the second iteration of Ivanhoe by :
Katherine Beltran 
Kelly Maclauchlan 
Brittany Veinot	

In our zipped file, you will find a 'client.jar' file and a ‘server.jar’ file that you will be able to run in any terminal window.

Run the server file from a terminal window by typing <java -jar server.jar> 

Once the server is running, run the client file from a terminal window by typing <java -jar client.jar> 

Do this from multiple terminal windows to use multiple clients

You will first be prompted for the ip of the machine the server is running. (ie: localhost)

The first player in the game will then be prompted the number of players, number of AIs and then asked to enter their name. 

If you are not the first player you will just be prompted the server ip and your name.

Once a sufficient amount of players has joined the game, the game engine will randomly generate who has picked a purple token and then will 
notify every client who picked purple and who the first player is. 
The first player will then be able to choose the tournament colour.

To play a card:
- click the card that you will would like to play 
- after you have selected your card, press the 'play card' button 
- after playing all the cards you would like in that round, click 'end turn' to announce to all other players that you have finished your turn 
- you will then see your played cards as well as the number of points that you have accumulated 
- click on a player’s display to see their played cards

There is an option to withdraw at your end turn if you do not have enough valid cards for that tournament and/or round.

The game engine also accounts for cards that are unplayable, and will automatically withdraw a player who’s points are lower than any of the other players.

Essentially, the game engine accounts for all possible cases while playing a card. 

At the end of each tournament, all players are notified of the winner and the winner can choose the next the tournament colour.

At the end of a game, the winner is announced to all players.

AI Strategies:
	1) Withdraw AI : this AI essentially withdraws after every play. The AI, if it is the first player, will choose the tournament colour based on 
					 its first coloured card in its hand and play that card. After it will withdraw after every turn. If the AI happens to win a tournament
					 it will again choose the tournament colour based on it's first coloured card, play that particular card and end its turn. It will then
					 withdraw when it becomes its turn again. 
	2) Play All AI : this AI will choose the tournament colour based on the number of coloured cards it has or by which token it needs.
					 the AI will play all of its cards that it can possibly play and then end its turn
					 if the AI has no cards to play it will withdraw
					 Sometimes, the AI will choose the tournament colour based on the tokens it will need, however does not have the cards needed and will then withdraw. This is one of the bugs with the AI that was unsolvable. 
***in regards to action cards, the AI do not play any action cards 

Known issues:
- when playing with more than 1 AI, the AI will play correctly except when the player gets to choose the tournament colour
		-> for some reason only one of the AIs receives the right cards and the others do not 
- also when playing with more than 1 AI, occassionally, the game will return an infinite loop of messages that renders the game unplayable

** All functionality works perfectly with 1 player and 1 AI
