# NBAScrapper
A command line Java app that returns the three point averages for a given player's season statistics.

# How to use it

   scrappers.App [playerName] [playerSurname]
 
 The two arguments are then used to determine the url for the stat page, since each player has his own unique id assigns to them.
 
 
# How it works

  First it converts both parameters to lowercase since it is standard to use lowercase in the websites url/directory traversal.

  Then it takes the first letter of the surname to use as the alphabetical letter used in the player ordering system the website uses.
  
  And finally it assembles the id used for the player. The id is comprised of the first 5 letters of their surname, then followed by the first 2 letters of the players name and a   number in case an id of that sort already exists.
  In this case we take a name and a surname then iterate through the number until we get a 404 error. Returning 3 point average stats for each player with that id.
  In order to avoid confusion the player's full name is printed at the top of the table, since a person might type a single name and expect a single table.
  
  The url ends with ".html" because the players don't each have their own directory. It's basically a folder full of html files starting with a certain letter, had each player had   their own folder with an index.html within that folder it would only show their id in the url.
  
  So a full url would be https://www.basketball-reference.com/players/w/willijo01.html
  
  When the page is exhausted of players with that id, the app prints a "Print complete" message. Informing the user of the finished task.
  
 # Reminder
 Program ne podpira šumnikov
 The app does not support letters outside of unicode standards, therefore using letters like Č,Š,Ž,Ć, will return an exception, since the sorting algorithm for the site is also without this, I decided to omit support for them.
  
  
  
 
 
