package com.capgemini.heartbeat;

import java.util.ArrayList;
import java.util.Iterator;

public class JenkinsBuildList {

	private ArrayList<JenkinsBuild> buildList;

	public JenkinsBuildList() {
		buildList = new ArrayList<JenkinsBuild>();
	}

	public void add(JenkinsBuild jb) {
		buildList.add(jb);
	}

	public void delete(JenkinsBuild jb) {
		buildList.remove(jb);
	}

	public ArrayList<JenkinsBuild> getBuildList() {
		return buildList;
	}

	public boolean contains(String url) {
		Iterator<JenkinsBuild> iter = buildList.iterator();
		while (iter.hasNext()) {
			JenkinsBuild jb = iter.next();
			if (jb.getJenkinsProperty().getUrl().equals(url)) {
				return true;
			}
		}
		return false;
	}

}
