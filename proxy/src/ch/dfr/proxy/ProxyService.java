//package ch.dfr.proxy;
//
//import java.io.*;
//import java.util.*;
//import ch.dfr.proxy.ProxyThread;
////import com.ms.service.*;
////import com.ms.wfc.*;
//
///*
//  Java Transparent Proxy
//  Copyright (C) 1999 by Didier Frick (http://www.dfr.ch/)
//  This software is provided under the GNU general public license (http://www.gnu.org/copyleft/gpl.html).
//*/
//
//public class ProxyService extends com.ms.service.Service {
//
//    private java.util.Vector threadVector;
//
//    public ProxyService(String[] args) {
//	System.out.println( com.ms.wfc.app.Application.getStartupPath() );
//	String allArguments = "";
//	if(args.length==0) {
//		args=new String[]{com.ms.wfc.app.Application.getStartupPath()+"\\proxy.properties"};
//	}
//	for (int i=0; i < args.length; i++){
//	    allArguments = allArguments + "|" + args[i];
//	}
//	out.println("Proxy service starting");
//	out.println("Arguments:"+allArguments);
//	CheckPoint(5000);
//	try {
//	    threadVector=ProxyThread.parseArguments(args,out,err);
//	    setRunning(ACCEPT_SHUTDOWN |  ACCEPT_STOP);
//	} catch(Exception xc) {
//	    err.println("Error starting service");
//	    err.println("Exception:"+xc.getClass()+":"+xc.getMessage());
//	}
//    }
//
//    protected boolean handleStop () {
//	setStopping(10000);
//	for(java.util.Enumeration e=threadVector.elements(); e.hasMoreElements(); ){
//	    Thread t=(Thread)e.nextElement();
//	    CheckPoint();
//	    t.interrupt();
//	    try {
//		t.join(1000);
//	    } catch(InterruptedException xc) {
//		;
//	    }
//	}
//	out.println("Proxy service stopped");
//	out.flush();
//	err.flush();
//	return true;
//    }
//    
//    protected boolean handleShutdown () {
//        return handleStop();
//    }
//
//    protected boolean handleInterrogate () {
//        setServiceStatus(getServiceStatus());
//	return false;
//    }
//}
