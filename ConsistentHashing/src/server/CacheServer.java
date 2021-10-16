package server;

public class CacheServer  implements Node{


    private String hostName;
    private String port;
    private String hostIP;

    @Override
    public String toString() {
        return "CacheServer{" +
                "hostName='" + hostName + '\'' +
                '}';
    }

    public CacheServer(String hostName, String port, String hostIP) {
        this.hostName = hostName;
        this.port = port;
        this.hostIP = hostIP;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getHostIP() {
        return hostIP;
    }

    public void setHostIP(String hostIP) {
        this.hostIP = hostIP;
    }

    @Override
    public String getKey() {
        return this.hostName;
    }
}
