README

Any picture that is going to rotate and any given degree (0 -> 360)
needs...
   -to be Facing left, (zero degrees)
   -to be centered in a square where the circle circumscribed in the square
can contain the picture rotated in at any degree
	-> PRETTY MUCH, make the picture like normal, then resize canvas so that
	new width = new height = Math.ceil((sqrt(width/2 + width/2)) * 2)