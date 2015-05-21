package com.lancer.robotgame.framework;

import java.util.ArrayList;
import java.util.List;

import android.util.FloatMath;

public class SpatialHashGrid {

	/**
	 * What we want to do is figure out to which cell(s) an object belongs. Ideally, we want to calculate
	 * the IDs of the cells in which the object is contained.
	 */
	List<GameObject>[] dynamicCells;
	/**
	 * each cell is a list of game objects. The spatial hash grid itself is just
	 * composed of an array of lists of GameObjects
	 */
	List<GameObject>[] staticCells;
	
	/**
	 * We also store the cells per row and column, so that we can later
	 * decide whether a point we check is inside or outside the world
	 */
	int cellsPerRow;
	int cellsPerCol;
	float cellSize;
	/**
	 * The cellIds array is a working array that we can use to store the four cell
	 * IDs the GameObject in question is contained in temporarily. If it is
	 * contained in only one cell, then only the first element of the array will be set to the cell ID of the
	 * cell that contains the object entirely. If the object is contained in two cells, then the first two
	 * elements of that array will hold the cell ID, and so on. To indicate the number of cell IDs, we set
	 * all “empty” elements of the array to –1.
	 * Contains indices of the cells in which the object belongs to.
	 * Dynamic objects can be in a maximum of 4 cells 'coz we will set each cell as 5 (arbitrary, heuristic) times bigger than the biggest dynamic object.
	 */
	int[] cellIds = new int[4];
	
	/**
	 * The foundObjects list is also a working list, which we can
	 * return upon a call to getPotentialColliders(). Why do we keep those two members instead
	 * of instantiating a new array and list each time one is needed? Remember the garbage collector monster.
	 * List of potential colliders to our object in question.
	 */
	List<GameObject> foundObjects;
	
	public SpatialHashGrid( float worldWidth, float worldHeight, float cellSize ) {
		this.cellSize = cellSize;
		this.cellsPerRow = (int)FloatMath.ceil(worldWidth/cellSize);
		this.cellsPerCol = (int)FloatMath.ceil(worldHeight/cellSize);
		int numCells = cellsPerRow * cellsPerCol;
		dynamicCells = new List[numCells]; // array of list, 'coz each cell is a list of game objects
		staticCells = new List[numCells]; // numCells 'coz each index corresponds to a cell
		/*
		 * All the ArrayListinstances we create will have an initial capacity of 10 GameObject instances.
		 * We do this to avoid memory allocations. The assumption is that it is unlikely that one single cell
		 * will contain more than ten GameObject instances.
		 * As long as that is true, the array lists don’t need to be resized.
		 */
		for( int i = 0; i < numCells; i++ ) {
			dynamicCells[i] = new ArrayList<GameObject>(10);
			staticCells[i] = new ArrayList<GameObject>(10);
		}
		foundObjects = new ArrayList<GameObject>(10);
		cellIds[0] = -1;
		cellIds[1] = -1;
		cellIds[2] = -1;
		cellIds[3] = -1;
	} // constructor
	
	/**
	 * They calculate the IDs of the cells in which the object is contained,
	 * via a call to getCellIds(), and insert the
	 * object into the appropriate list (in cell array index) accordingly.
	 */
	public void insertStaticObject( GameObject obj ) {
		int[] cellIds = getCellIds( obj );
		int i = 0;
		int cellId = -1;
		while( i <= 3 && (cellId = cellIds[i++]) != -1 ) {
			System.out.println("cellId: " + cellId);
			staticCells[cellId].add(obj);
		}
	}
	
	public void insertDynamicObject( GameObject obj ) {
		int[] cellIds = getCellIds( obj );
		int i = 0;
		int cellId = -1;
		while( i <= 3 && (cellId = cellIds[i++]) != -1 ) {
			dynamicCells[cellId].add( obj );
		}
	}
	
	/**
	 * We also have a removeObject() method, which we can use to figure out which cells the object
	 * is in, and then delete it from the dynamic or static lists accordingly.
	 * This will be needed when a game object dies, for example.
	 */
	public void removeObject( GameObject obj ) {
		int[] cellIds = getCellIds( obj );
		int i = 0;
		int cellId = -1;
		while( i <= 3 && (cellId = cellIds[i++]) != -1 ) {
			dynamicCells[cellId].remove(obj);
			staticCells[cellId].remove(obj);
		}
	}
	
	/**
	 * The clearDynamicCells() method will be used to clear all dynamic cell lists.
	 * We need to call this each frame before we reinsert the dynamic objects.
	 */
	public void clearDynamicCells( GameObject obj ) {
		int len = dynamicCells.length;
		for( int i = 0; i < len; i++ ) {
			dynamicCells[i].clear();
		}
	}
	
	public List<GameObject> getPotentialColliders( GameObject obj ) {
		foundObjects.clear();
		int[] cellIds = getCellIds( obj );
		int i = 0;
		int cellId = -1;
		while( i <=  3 && (cellId = cellIds[i++]) != -1 ) {
			int len = dynamicCells[cellId].size(); // size of list where we're currently in
			// add all potential dynamic colliders
			for( int j = 0; j < len; j++ ) {
				GameObject collider = dynamicCells[cellId].get(j);
				if( !foundObjects.contains(collider) ) {
					foundObjects.add(collider);
				}
			}
			
			// add all potential static colliders
			len = staticCells[cellId].size();
			for( int j = 0; j < len; j++ ) {
				GameObject collider = staticCells[cellId].get(j);
				if( !foundObjects.contains(collider) ) {
					foundObjects.add(collider);
				}
			}
		} // while
		return foundObjects;
	} // getPotentialColliders
	
	/**
	 * return array of cell indices (IDs) where obj belongs to
	 */
	public int[] getCellIds( GameObject obj ) {
		// get indices of cells containing lowerleft and upperright corners of obj
		// x1y1=lowerleft, x2y2=upperright, x1y2=upperleft, x2y1=lowerright 
		int x1 = (int)FloatMath.floor(obj.bounds.lowerLeft.x / cellSize );
		int y1 = (int)FloatMath.floor(obj.bounds.lowerLeft.y / cellSize );
		int x2 = (int)FloatMath.floor(obj.bounds.lowerLeft.x + obj.bounds.width / cellSize );
		int y2 = (int)FloatMath.floor(obj.bounds.lowerLeft.y + obj.bounds.height / cellSize );
		
		// if obj is within 1 cell
		if( x1 == x2 && y1 == y2 ) {
			// if obj is within the world
			if( x1 >= 0 && x1 < cellsPerRow && y1 >= 0 && y1 < cellsPerCol ) {
				// get cellId (cell index) store in array
				cellIds[0] = x1 + y1 * cellsPerRow;
			} else {
				cellIds[0] = -1;
				cellIds[1] = -1;
				cellIds[2] = -1;
				cellIds[3] = -1;
			}
			// check if obj overlaps two cells vertically
		} else if( x1 == x2 ) {
			int i = 0;
			if( x1 >= 0 && x1 < cellsPerRow ) {
				if( y1 >= 0 && y1 < cellsPerCol ) {
					cellIds[i++] = x1 + y1 * cellsPerRow;
				}
				if( y2 >= 0 && y2 < cellsPerCol ) {
					cellIds[i++] = x1 + y2 * cellsPerRow;
				}
			}
			while( i <= 3 ) {
				cellIds[i++] = -1;
			}
			// check if obj overlaps two cells horizontally
		} else if( y1 == y2 ) {
			int i = 0;
			if( y1 >= 0 && y1 < cellsPerCol ) {
				if( x1 >= 0 && x1 < cellsPerRow ) {
					cellIds[i++] = x1 + y1 * cellsPerRow;
				}
				if( x2 >= 0 && x2 < cellsPerRow ) {
					cellIds[i++] = x2 + y1 * cellsPerRow;
				}
			}
			while( i <= 3 ) {
				cellIds[i++] = -1;
			}
			// obj overlaps four cells
		} else {
			int i = 0;
			int y1CellsPerRow = y1 * cellsPerRow;
			int y2CellsPerRow = y2 * cellsPerRow;
			if( x1 >= 0 && x1 < cellsPerRow && y1 >= 0 && y1 < cellsPerCol ) {
				cellIds[i++] = x1 + y1CellsPerRow;
			}
			if( x2 >= 0 && x2 < cellsPerRow && y1 >= 0 && y1 < cellsPerCol ) {
				cellIds[i++] = x2 + y1CellsPerRow;
			}
			if( x2 >= 0 && x2 < cellsPerRow && y2 >= 0 && y2 < cellsPerCol ) {
				cellIds[i++] = x2 + y2CellsPerRow;
			}
			if( x1 >= 0 && x1 < cellsPerRow && y2 >= 0 && y2 < cellsPerCol ) {
				cellIds[i++] = x1 + y2CellsPerRow;
			}
			while( i <= 3 ) {
				cellIds[i++] = -1;
			}
		} // else
		return cellIds;
	} // getCellIds
	
} // SpatialHashGrid

/**
 * To calculate the cell IDs for an object, we simply take the four corner points of the bounding
 * rectangle and check which cell each corner point is in. Determining the cell that a point is in is
 * easy—we just need to divide its coordinates by the cell width. Say you have a point at (3,4) and
 * a cell size of 2.5×2.5 m: the point would be in the cell with ID 5.
 * 
 * We can divide each of the point’s coordinates by the cell size to get 2D integer coordinates, as follows:
 * cellX = floor ( point.x / cellSize ) = floor ( 3 / 2.5 ) = 1
 * cellY = floor ( point.y / cellSize ) = floor ( 4 / 2.5 ) = 1
 * 
 * And from these cell coordinates, we can easily get the cell ID:
 * cellId = cellX + cellY × cellsPerRow = 1 + 1 × 4 = 5
 * 
 * The constant cellsPerRow is simply the number of cells we need to cover our world with cells on the x axis:
 * cellsPerRow = ceil ( worldWidth / cellSize ) = ceil ( 9.6 / 2.5 ) = 4
 * 
 * We can calculate the number of cells needed per column like this:
 * cellsPerColumn = ceil ( worldHeight / cellSize ) = ceil ( 6.4 / 2.5 ) = 3
 * 
 * Based on this, we can implement the spatial hash grid rather easily. We set it up by giving it the
 * world’s size and the desired cell size. We assume that all the action is happening in the positive
 * quadrant of the world. This means that all the x and y coordinates of the points in the world will
 * be positive. This is a constraint we can accept.
 * 
 * From the parameters, the spatial hash grid can figure out how many cells it needs (cellsPerRow × cellsPerColumn).
 * We can also add a simple method to insert an object into the grid that will use
 * the object’s boundaries to determine the cells in which it is contained. The object will then be
 * added to each cell’s list of the objects that it contains. If one of the corner points of the bounding
 * shape of the object is outside the grid, we can just ignore that corner point.
 * In each frame, we reinsert every object into the spatial hash grid, after we update its position.
 * However, there are objects in our cannon world that don’t move, so inserting them anew for each
 * frame is very wasteful. We make a distinction between dynamic objects and static objects by
 * storing two lists per cell. One list will be updated each frame and will hold only moving objects,
 * and the other list will be static and will be modified only when a new static object is inserted.
 * Finally, we need a method that returns a list of objects in the cells of the object we’d like to have
 * collide with other objects. All this method does is check which cells the object in question is in,
 * retrieve the list of dynamic and static objects in those cells, and return the list to the caller. Of
 * course, we have to make sure that we don’t return any duplicates, which can happen if an object
 * is in multiple cells.
 */