package controllers.game;

import controllers.game.ObjectTypeController.ObjectType;

public interface Drawable {
	
	int getXCoord();
	int getYCoord();
	
	ObjectType getObjectType();

}
