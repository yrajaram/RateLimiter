Rate Limiter
============

I call this algorithm 'Karmic Ko-ko'. You can use this in accordance with Apache License. 


Karma /ˈkɑːmə/: 
	(Hinduism, Buddhism) the principle of retributive justice determining a person's state of life and the state of his or her reincarnations as the effect of past deeds
	(theosophy) the doctrine of inevitable consequence
	
	Karmic adjective
	
	
Ko-ko: Indian traditional tag game.  The objective is to tag all the opponents in the shortest time possible.
	http://www.traditionalgames.in/home/outdoor-games/kho-kho-ko-ko
	

Here is how I plan to enhance this:
1. Use an ExecutorService (like FixedThreadPool) instead of single thread so that this can be used where the volume of transactions are high.
2. Enhance this implementation for distributed JVMs so that all instances of your server logic can collaborate.