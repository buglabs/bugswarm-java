package simpleswarmexample;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import org.osgi.service.log.LogService;

import com.buglabs.application.ServiceTrackerHelper.ManagedRunnable;
import com.buglabs.bug.swarm.client.ISwarmJsonMessageListener;
import com.buglabs.bug.swarm.client.ISwarmSession;
import com.buglabs.bug.swarm.client.SwarmClientFactory;
import com.sun.xml.internal.ws.api.model.ExceptionType;
/**
 * This class represents the running application when all service dependencies are fulfilled.
 * 
 * The run() method will be called with a map containing all the services specified in ServiceTrackerHelper.openServiceTracker().
 * The application will run in a separate thread than the caller of start() in the Activator.  See 
 * ManagedInlineRunnable for a thread-less application.
 * 
 * By default, the application will only be started when all service dependencies are fulfilled.  For 
 * finer grained service binding logic, see ServiceTrackerHelper.openServiceTracker(BundleContext context, String[] services, Filter filter, ServiceTrackerCustomizer customizer)
 */
public class SimpleSwarmExampleApplication implements ManagedRunnable {
	
	String hostname =       "api.bugswarm.net";
    String participation_key =  "63a0e565521670b1cf32040bf179e2f904146e81";
    String resource_id =        "6dc1eca12cf6461515fb863a925ef5bfa8278c48";
    String[] swarms = new String[]{ "6c67d6493656d32698ec9f820feca33c38d9abf6"  };	
	private LogService ls;
	private Thread myThread;
	private ISwarmSession producersession;

	@Override
	public void run(Map<Object, Object> services) {			
		ls = (LogService) services.get(LogService.class.getName());
		ilog("starting...");
		myThread = Thread.currentThread();
		
		try {
	        //create a session
	        producersession = SwarmClientFactory.createProductionSession(hostname, participation_key, resource_id, true, true, swarms);

	        //add a listener for incoming messages
	        producersession.addListener(new ISwarmJsonMessageListener() {                   
	            @Override
	            public void presenceEvent(String fromSwarm, String fromResource, boolean isAvailable) {
	                //print out messages regarding what resources are present in our swarm
	                System.out.println("fromswarm: "+fromSwarm+" fromresource: "+fromResource+ "isavailable "+isAvailable);
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
	        ilog("Connected, sending messages");

	        //send some messages
	        HashMap<String, String> map = new HashMap<String, String>();
	        map.put("message", "Java client hi");
	        try {
	        	while(true){
	        		if (producersession != null && producersession.isConnected()){
	        			producersession.send(map);
	        		}
		            Thread.sleep(1000);
		        }
	        } catch (InterruptedException e){
	        	ilog("closing session");
	        	producersession.close();
	        	return;
	        }
	    } catch (UnknownHostException e) {
	        e.printStackTrace();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
		
	}

	@Override
	public void shutdown() {
		// TODO Add shutdown code here if necessary.
		myThread.interrupt();
	}
	
	//Wrappers for the log service (to standardize logged messages)
	void ilog(String message){  ls.log(LogService.LOG_INFO, "["+this.getClass().getPackage().getName()+"] "+message);	}
	void dlog(String message){  ls.log(LogService.LOG_DEBUG, "["+this.getClass().getPackage().getName()+"] "+message);	}
	void elog(String message){  ls.log(LogService.LOG_ERROR, "["+this.getClass().getPackage().getName()+"] "+message);	}
	void wlog(String message){  ls.log(LogService.LOG_WARNING, "["+this.getClass().getPackage().getName()+"] "+message);	}
}