# BUGswarm Java Library

Connect your resources to BUGswarm and share their information with this java client library.  It 
is currently based on using a bidirectional chunked http connection, though will likely be moving
[Websockets](http://code.google.com/p/jwebsocket/) soon.

### Dependencies

Note, before using this library, binary dependencies must be resolved.  As of Bug Rootfs 2.1.1, the necessary dependencies have already been deployed to ```/usr/share/java/bundle/```  The dependencies also need to be resolved in eclipse.  One technique would be to embed the additional .jar files into your eclipse project, however this produces large .jar files that can take a while to deploy to the bug.  We suggest resolving the dependencies locally:

1. Clone this repository to a folder outside of your eclipse workspace:  ```git clone git@github.com:buglabs/bugswarm-java.git```
1. In Eclipse, click on ```Preferences -> Target Platform -> Running Platform -> Edit -> Add```
1. In the dialog select Directory and then navigate to the swarm-libs folder of this repository.
1. The Target Content dialog should now show '4 plug-ins available' for swarm-libs.
1. Finish the dialog and restart eclipse.

Note that com.buglabs.bug.swarm.client is included as a binary dependency.  As a result, you should not import the source into eclipse unless you need to make changes to the swarm library itself.  The copy of com.buglabs.bug.swarm.client included in this repository is the same version already deployed to the R2.1.1 rootfs.  If you have an older rootfs, simply copy the .jar files from the swarm-libs folder over the the directory ```/usr/share/java/bundle``` on the rootfs.

### API Usage

The following is a snippet demonstrating how to use the bug swarm library.  The following code has also been included in the SimpleSwarmExample bug application in this repository.  To try out the following code on your bug, you can write your own application from scratch or

1. select ```file -> import -> Existing Projects into Workspace -> Select Root Directory -> Browse
1. Select the SimpleSwarmExample folder from this repository
1. Click on Finish
1. Deploy the application to your bug
1. On your bug serial console, execute ```tail -f /var/log/felix.log``` and notice the verbose bug swarm output
1. Additionally, save this html file to your workstation PC: ```https://raw.github.com/buglabs/bugswarm-js/master/examples/helloworld.html``` and open the local file in your google chrome web browser
1. Right click in the empty white space and select ```inspect element```
1. Click on the Console tab
1. You should see presence messages from multiple resources, as well as two different messages, ```{"test":"foo"}``` and ```{"message":"Java client hi"}```

```java

	String hostname = 		"api.bugswarm.net";
	String participation_key = 	"3e98ac6ca152f9bc279d0ef6e6bc9877e1508fd8";
	String resource_id = 		"0da7ce672f5a2e067ee8a0e050ca3e363283ea39";
	String[] swarms = new String[]{ "0da7ce672f5a2e067ee8a0e050ca3e363283ea39"	};

	try {
		//create a session
		producersession = SwarmClientFactory.createProductionSession(hostname, participation_key, resource_id, swarms);

		//add a listener for incoming messages
		producersession.addListener(new ISwarmJsonMessageListener() {					
			@Override
			public void presenceEvent(String fromSwarm, String fromResource, boolean isAvailable) {
				//print out messages regarding what resources are present in our swarm
				System.out.println("fromswarm: "+fromSwarm+" fromresource: "+ fromResource+ "isavailable "+ 
			}

			@Override
			public void exceptionOccurred(ExceptionType type, String message) {
				//handle some authentication or connection issues
				System.out.println("exception occured: "+type+ " message "+ message);
			}

			@Override
			public void messageRecieved(Map<String, ?> payload, String fromSwarm, String fromResource, boolean isPublic) {
				//print messages as they're received from the swarm
				for (Map.Entry<String, ?> entry : payload.entrySet())
				{
				    System.out.println(entry.getKey() + "/" + entry.getValue());
				}
			}
		});

		//send some messages
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("key", "value");
		while(true){
			producersession.send(map);
		}
				
	} catch (UnknownHostException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	}

```

### Fork it, improve it and send us pull requests.
```shell
git clone git@github.com:buglabs/bugswarm-java.git && cd bugswarm-java/com.buglabs.bug.swarm.client
```

## License
(The MIT License)

Copyright 2011 BugLabs. All rights reserved.

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to
deal in the Software without restriction, including without limitation the
rights to use, copy, modify, merge, publish, distribute, sublicense, and/or
sell copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS
IN THE SOFTWARE.


