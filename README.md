# BUGswarm Java Library

Connect your resources to BUGswarm and share their information with this java client library.  It 
is currently based on using a bidirectional chunked http connection, though will likely be moving
[Websockets](http://code.google.com/p/jwebsocket/) soon.

### API Usage

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


