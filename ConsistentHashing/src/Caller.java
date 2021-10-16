import hash.ConsistentHash;
import hash.HashFunction;
import hash.MDS5Hash;
import server.CacheServer;
import server.Node;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;

public class Caller {

    public static  void test(){

        List<Node> serverList = new ArrayList<>();
        serverList.add(new CacheServer("IndCache","192.10.2.1","8080"));
        serverList.add(new CacheServer("EUCache","172.10.2.1","8080"));
        serverList.add(new CacheServer("USCache","162.10.2.1","8080"));
        serverList.add(new CacheServer("BrazilCache","162.10.2.1","8080"));

        HashFunction function = new MDS5Hash();
        ConsistentHash<Node> consistentHash = new ConsistentHash<>(function,1,serverList);
        SortedMap<Integer, Node> circle= consistentHash.getMap();
        System.out.println(circle);

        System.out.println(consistentHash.get("Priyanshu"));
        System.out.println(consistentHash.get("khushboo-anand"));
        System.out.println(consistentHash.get("IndCache"));
        // after removing IndCache , IndCache will move to USCache

        consistentHash.remove(new CacheServer("IndCache","192.10.2.1","8080"));
        System.out.println(consistentHash.get("IndCache"));
    }

    public static  void main(String as[]){
        test();
    }
}
