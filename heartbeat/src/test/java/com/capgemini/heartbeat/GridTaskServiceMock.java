package com.capgemini.heartbeat;

public class GridTaskServiceMock extends GridTaskService {

	public GridTaskServiceMock(TaskProperties gridPrperties,GridConnectionTesterMock mockedConnection ) {
		super(gridPrperties);
		super.connection = mockedConnection;
	}

}
