package com.capgemini.heartbeat;

import java.util.ArrayList;

public class JenkinsBuildList {

	private static ArrayList<JenkinsBuild> buildList;

	public JenkinsBuildList() {
		buildList = new ArrayList<JenkinsBuild>();
	}

	public void add(JenkinsBuild jb) {
		buildList.add(jb);
	}

	public void delete(JenkinsBuild jb) {
		buildList.remove(jb);
	}

	public static ArrayList<JenkinsBuild> getBuildList() {
		return buildList;
	}

}
