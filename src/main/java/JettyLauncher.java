import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

import java.net.URL;
import java.security.ProtectionDomain;

public class JettyLauncher {
    public static void main(String[] args) throws Exception {
        int port = 8080;
        String contextPath = "/";

        for(String arg: args){
            if(arg.startsWith("--") && arg.contains("=")){
                String[] dim = arg.split("=");
                if(dim.length >= 2){
                    if(dim[0].equals("--port")){
                        port = Integer.parseInt(dim[1]);
                    } else if(dim[0].equals("--prefix")){
                        contextPath = dim[1];
                    }
                }
            }
        }

        Server server = new Server(port);
        WebAppContext context  = new WebAppContext();
        ProtectionDomain domain   = JettyLauncher.class.getProtectionDomain();
        URL location = domain.getCodeSource().getLocation();

        context.setContextPath(contextPath);
        context.setDescriptor(location.toExternalForm() + "/WEB-INF/web.xml");
        context.setServer(server);
        context.setWar(location.toExternalForm());

        server.setHandler(context);
        server.start();
        server.join();
    }
}
