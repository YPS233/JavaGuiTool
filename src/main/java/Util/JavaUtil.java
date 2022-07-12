package Util;

import jadx.api.JadxArgs;
import jadx.api.JadxDecompiler;

import java.io.*;
import java.util.*;

public class JavaUtil {
    public static Map base64Tojava(String base64Data) throws IOException {
        byte[] b = Base64.getDecoder().decode(base64Data);
        String tempPath = System.getProperty("java.io.tmpdir")+File.separator;
        String outputPath = tempPath + "output/";
        writeFile(tempPath + "temp.class", b);

        JadxArgs jadxArgs = new JadxArgs();
        jadxArgs.setInputFile(new File(tempPath + "temp.class"));
        jadxArgs.setOutDir(new File(outputPath));
        try (JadxDecompiler jadx = new JadxDecompiler(jadxArgs)) {
            jadx.load();
            jadx.save();
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<File> fileList = new ArrayList<>();
        findJava(new File(outputPath), fileList);

        HashMap map = new HashMap();
        getJava(fileList, map);
        return map;
    }


    public static void writeFile(String filepath, byte[] content) throws IOException {
        File f = new File(filepath);
        FileOutputStream fos = new FileOutputStream(f);
        fos.write(content);
        fos.close();
    }

    public static void findJava(File outputFile, List<File> fileList) {
        File[] files = outputFile.listFiles();
        for (File i : files) {
            if (i.isDirectory()) {
                findJava(i, fileList);
            }
            if (i.isFile()) {
                if (i.getName().endsWith(".java")) {//判断名
                    fileList.add(i);
                }
            }

        }
    }

    public static void getJava(List<File> Files, HashMap map) throws IOException {
        for(File f: Files){
            String content = getContent(f.getPath());
            map.put(f.getName(),content);
        }
    }

    public static String getContent(String filePath) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        StringBuilder stringBuilder = new StringBuilder();
        String line = null;
        String ls = System.getProperty("line.separator");
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line);
            stringBuilder.append(ls);
        }

        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        reader.close();

        String content = stringBuilder.toString();
        return content;
    }

    public static void main(String[] args) throws IOException {
        String base64data = "yv66vgAAADQAZgoAFQA1CgA2ADcHADgKAAMAOQgAOgoACAA7CAA8BwA9BwA+CgAIAD8KAEAAQQcAQggAQwkARABFCgBGAEcKAAkASAoAQABJBwBKCgAVAEsHAEwHAE0BAAY8aW5pdD4BAAMoKVYBAARDb2RlAQAPTGluZU51bWJlclRhYmxlAQASTG9jYWxWYXJpYWJsZVRhYmxlAQAEdGhpcwEANExvcmcvc3UxOC9tZW1zaGVsbC90ZXN0L2dsYXNzZmlzaC9UZXN0R3JpenpseUZpbHRlcjsBAApoYW5kbGVSZWFkAQBmKExvcmcvZ2xhc3NmaXNoL2dyaXp6bHkvZmlsdGVyY2hhaW4vRmlsdGVyQ2hhaW5Db250ZXh0OylMb3JnL2dsYXNzZmlzaC9ncml6emx5L2ZpbHRlcmNoYWluL05leHRBY3Rpb247AQABYwEAEUxqYXZhL2xhbmcvQ2xhc3M7AQABbQEAGkxqYXZhL2xhbmcvcmVmbGVjdC9NZXRob2Q7AQADY3R4AQA2TG9yZy9nbGFzc2Zpc2gvZ3JpenpseS9maWx0ZXJjaGFpbi9GaWx0ZXJDaGFpbkNvbnRleHQ7AQAKY29ubmVjdGlvbgEANkxvcmcvZ2xhc3NmaXNoL2dyaXp6bHkvbmlvL3RyYW5zcG9ydC9UQ1BOSU9Db25uZWN0aW9uOwEAB2NoYW5uZWwBACVMamF2YS9uaW8vY2hhbm5lbHMvU2VsZWN0YWJsZUNoYW5uZWw7AQAWTG9jYWxWYXJpYWJsZVR5cGVUYWJsZQEAFExqYXZhL2xhbmcvQ2xhc3M8Kj47AQANU3RhY2tNYXBUYWJsZQcATAcATgcAOAcATwcASgEACkV4Y2VwdGlvbnMHAFABAApTb3VyY2VGaWxlAQAWVGVzdEdyaXp6bHlGaWx0ZXIuamF2YQwAFgAXBwBODABRAFIBADRvcmcvZ2xhc3NmaXNoL2dyaXp6bHkvbmlvL3RyYW5zcG9ydC9UQ1BOSU9Db25uZWN0aW9uDABTAFQBABxzdW4ubmlvLmNoLlNvY2tldENoYW5uZWxJbXBsDABVAFYBAAV3cml0ZQEAD2phdmEvbGFuZy9DbGFzcwEAE2phdmEvbmlvL0J5dGVCdWZmZXIMAFcAWAcAWQwAWgBbAQAQamF2YS9sYW5nL09iamVjdAEAGUhUVFAvMS4xIDIwMCBPSyBzdTE4IHl5ZHMHAFwMAF0AXgcAXwwAYABhDABiAGMMAGQAZQEAE2phdmEvbGFuZy9FeGNlcHRpb24MAB0AHgEAMm9yZy9zdTE4L21lbXNoZWxsL3Rlc3QvZ2xhc3NmaXNoL1Rlc3RHcml6emx5RmlsdGVyAQAsb3JnL2dsYXNzZmlzaC9ncml6emx5L2ZpbHRlcmNoYWluL0Jhc2VGaWx0ZXIBADRvcmcvZ2xhc3NmaXNoL2dyaXp6bHkvZmlsdGVyY2hhaW4vRmlsdGVyQ2hhaW5Db250ZXh0AQAjamF2YS9uaW8vY2hhbm5lbHMvU2VsZWN0YWJsZUNoYW5uZWwBABNqYXZhL2lvL0lPRXhjZXB0aW9uAQAMZ2V0Q2xvc2VhYmxlAQAjKClMb3JnL2dsYXNzZmlzaC9ncml6emx5L0Nsb3NlYWJsZTsBAApnZXRDaGFubmVsAQAnKClMamF2YS9uaW8vY2hhbm5lbHMvU2VsZWN0YWJsZUNoYW5uZWw7AQAHZm9yTmFtZQEAJShMamF2YS9sYW5nL1N0cmluZzspTGphdmEvbGFuZy9DbGFzczsBABFnZXREZWNsYXJlZE1ldGhvZAEAQChMamF2YS9sYW5nL1N0cmluZztbTGphdmEvbGFuZy9DbGFzczspTGphdmEvbGFuZy9yZWZsZWN0L01ldGhvZDsBABhqYXZhL2xhbmcvcmVmbGVjdC9NZXRob2QBAA1zZXRBY2Nlc3NpYmxlAQAEKFopVgEAIWphdmEvbmlvL2NoYXJzZXQvU3RhbmRhcmRDaGFyc2V0cwEABVVURl84AQAaTGphdmEvbmlvL2NoYXJzZXQvQ2hhcnNldDsBABBqYXZhL2xhbmcvU3RyaW5nAQAIZ2V0Qnl0ZXMBAB4oTGphdmEvbmlvL2NoYXJzZXQvQ2hhcnNldDspW0IBAAR3cmFwAQAZKFtCKUxqYXZhL25pby9CeXRlQnVmZmVyOwEABmludm9rZQEAOShMamF2YS9sYW5nL09iamVjdDtbTGphdmEvbGFuZy9PYmplY3Q7KUxqYXZhL2xhbmcvT2JqZWN0OwAhABQAFQAAAAAAAgABABYAFwABABgAAAAvAAEAAQAAAAUqtwABsQAAAAIAGQAAAAYAAQAAABMAGgAAAAwAAQAAAAUAGwAcAAAAAQAdAB4AAgAYAAABBQAHAAYAAABQK7YAAsAAA00stgAEThIFuAAGOgQZBBIHBL0ACFkDEglTtgAKOgUZBQS2AAsZBS0EvQAMWQMSDbIADrYAD7gAEFO2ABFXpwAFOgQqK7cAE7AAAQANAEUASAASAAQAGQAAACYACQAAABcACAAYAA0AGwAUABwAJgAdACwAHgBFACIASAAgAEoAIwAaAAAAPgAGABQAMQAfACAABAAmAB8AIQAiAAUAAABQABsAHAAAAAAAUAAjACQAAQAIAEgAJQAmAAIADQBDACcAKAADACkAAAAMAAEAFAAxAB8AKgAEACsAAAAZAAL/AEgABAcALAcALQcALgcALwABBwAwAQAxAAAABAABADIAAQAzAAAAAgA0";
        Map m = base64Tojava(base64data);
        System.out.println(m);
    }
}
