package com.capgemini.heartbeat;

import java.util.ArrayList;

public class GridProperty extends Property {

	private ArrayList<GridNode> nodesList = new ArrayList<GridNode>();

	public ArrayList<GridNode> getNodesList() {
		return nodesList;
	}

	public void setNodesList(ArrayList<GridNode> nodesList) {
		this.nodesList = nodesList;
	}

}
