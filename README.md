# heartbeat 

Heartbeat is an application that checks if instance of both Jenkins and Selenium-Grid are avaliable to use 

### Configuration


Configuration of Jenkins, Selenium-Grid heartbeat service:

	- unzip heartbeat.zip archive 
	- inside there are files:
		- heartbeat.jar 		=> application that checks wheather Jenkins or Selenium-Grid are working
		- jenkins.config.json 	=> configuration file with json list with separated entries for each Jenkins instance (url, username, pass)
		- grid.config.json 		=> configuration file with json list with separated entries for each Selenium-Grid instance (url)
		- haertbeat.properties 	=> application configuration file with path to the jenkins.config, grid.config, report.csv
		- Heartbeat.xml 		=> Windows task for TaskScheduler
	
	- open jenkins.config.json. There are two sample entreis to get the feel how to add new instance of Jenkis:
		- url = it's the IP of machine where Jenkins works remeber to add proper port
		- username = username 
		- password = password; this two are now not used so there is no need to enter any
	
	- open grid.config.json. There is one sample entry. Modify this one and add new one like you did in previous file (jenkins.config.json)
		- url = it's the IP of machine where Selenium-Grid is registered remeber to add propert port
	
	- if you want you can configure the placeholder for jenkins.config.json, grid.config.json, report.csv in heartbeat.properties. To that simply
	  open heartbeat.properties and modify path with absolute context. REMBER DO NOT CHANGE LOCATION OF heartbeat.properties. 
	  
	- open TaskScheduler:
		- go to StartMenu pass TaskScheduler in search box and click enter
		- click on Actions -> Import Task
		- go to catalog where you've downloaded heartbeat archive and pick Heartbeat.xml file
		- navigate to Actions tab:
			- click edit
			- pick "Start a program"
			- in "Program/script" box write your Java path "path/to/java/jre/bin/java.exe"
			- in "Add arguments" box write "- jar path/to/heartbeat/heartbeat.jar"
			- in "Start in" box write path to heartbeat "path/to/heartbeat/"
			- click ok
		- click ok
		- on a dialog that pops up choose your user and pass your creadentials
		- click ok
		- go back to the the list of task
		- on the lower list you should now see your new task "Heartbeat" (if not click "Refresh" on the right side)
		- in the upper list you should see status of your new "Heartbeat" task (it will be updated after first run)
		