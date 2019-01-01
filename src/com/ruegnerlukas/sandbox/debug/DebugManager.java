package com.ruegnerlukas.sandbox.debug;

import com.ruegnerlukas.simpleparser.systems.DotGraphBuilder;
import com.ruegnerlukas.simpleparser.tree.Node;

import java.util.ArrayList;
import java.util.List;

public class DebugManager {


	public static List<DebugData> data = new ArrayList<>();
	public static int index = 0;




	public static void nodeAdded(Node node) {
		Node root = node;
		while(root.getParent() != null) {
			root = root.getParent();
		}
		DebugData dataPoint = new DebugData();
		dataPoint.graph = DotGraphBuilder.build(root);
		data.add(dataPoint);
	}




	public static DebugData getNext() {
		if(data.isEmpty()) {
			return new DebugData();
		} else {
			if(index >= data.size()) {
				return data.get(data.size()-1);
			} else {
				return data.get(index++);
			}
		}
	}




	public static void reset() {
		index = 0;
	}

}
