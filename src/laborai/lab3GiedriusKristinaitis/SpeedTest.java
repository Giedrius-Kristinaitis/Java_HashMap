package laborai.lab3GiedriusKristinaitis;

import laborai.gui.MyException;
import laborai.studijosktu.HashType;
import laborai.studijosktu.MapKTU;
import laborai.studijosktu.MapKTUx;
import laborai.studijosktu.Timekeeper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.SynchronousQueue;

public class SpeedTest {

    public static final String FINISH_COMMAND = "finishCommand";
    private static final ResourceBundle MESSAGES = ResourceBundle.getBundle("laborai.gui.messages");

    private final BlockingQueue resultsLogger = new SynchronousQueue();
    private final Semaphore semaphore = new Semaphore(-1);
    private Timekeeper tk;

    private final String[] TEST_NAMES = {"add0.75", "add0.25", "rem0.75", "rem0.25", "get0.75", "get0.25", "putKTUOA", "putKTU", "remKTUOA", "remHashS", "zodAdd1", "zodAdd2", "zodRem1", "zodRem2"};
    private int[] TEST_QUANTITIES;

    private final MapKTUx<String, House> houses1
            = new MapKTUx(new String(), new House(), 10, 0.75f, HashType.DIVISION);
    private final MapKTUx<String, House> houses2
            = new MapKTUx(new String(), new House(), 10, 0.25f, HashType.DIVISION);
    private final Queue<String> chainsSizes = new LinkedList<>();

    private final MapKTUOA<String, String> data1 = new MapKTUOA<>();
    private final MapKTU<String, String> data2 = new MapKTU<>();
    private final HashSet<String> data3 = new HashSet<>();

    private final MapKTUOA<String, String> data4 = new MapKTUOA<>();
    private final MapKTU<String, String> data5 = new MapKTU<>();

    private List<String> words;

    public SpeedTest() {
        semaphore.release();

        readFile();

        TEST_QUANTITIES = new int[] {10000, 20000, 40000, 80000, words.size()};

        tk = new Timekeeper(TEST_QUANTITIES, resultsLogger, semaphore);
    }

    private void readFile(){
        try{
            words = Files.readAllLines(Paths.get("zodynas.txt"));
        }catch (IOException ex){
            ex.printStackTrace();
        }
    }

    public void startTest() {
        try {
            test();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
        }
    }

    public void test() throws InterruptedException {
        try {
            chainsSizes.add(MESSAGES.getString("msg4"));
            chainsSizes.add("   kiekis      " + TEST_NAMES[0] + "   " + TEST_NAMES[1]);

            data4.clear();
            data5.clear();

            for (int j = 0; j < TEST_QUANTITIES.length - 1; j++) {
                int k = TEST_QUANTITIES[j];

                House[] houseArray = HouseFactory.generateHouses(k);
                String[] keyArray = HouseFactory.generateHouseKeys(k);

                houses1.clear();
                houses2.clear();
                data1.clear();
                data2.clear();
                data3.clear();

                for (String s: keyArray) {
                    data3.add(s);
                }

                tk.startAfterPause();
                tk.start();

                for (int i = 0; i < k; i++) {
                    houses1.put(keyArray[i], houseArray[i]);
                }

                tk.finish(TEST_NAMES[0]);

                String str = "   " + k + "          " + houses1.getMaxChainSize();

                for (int i = 0; i < k; i++) {
                    houses2.put(keyArray[i], houseArray[i]);
                }

                tk.finish(TEST_NAMES[1]);

                str += "         " + houses2.getMaxChainSize();
                chainsSizes.add(str);

                for (String s : keyArray) {
                    houses1.remove(s);
                }

                tk.finish(TEST_NAMES[2]);

                for (String s : keyArray) {
                    houses2.remove(s);
                }

                tk.finish(TEST_NAMES[3]);

                for (String s : keyArray) {
                    houses2.get(s);
                }

                tk.finish(TEST_NAMES[4]);

                for (String s : keyArray) {
                    houses2.get(s);
                }

                tk.finish(TEST_NAMES[5]);

                for (String s: keyArray) {
                    data1.put(s, s);
                }

                tk.finish(TEST_NAMES[6]);

                for (String s: keyArray) {
                    data2.put(s, s);
                }

                tk.finish(TEST_NAMES[7]);

                for (String s: keyArray) {
                    data1.remove(s);
                }

                tk.finish(TEST_NAMES[8]);

                for (String s: keyArray) {
                    data3.remove(s);
                }

                tk.finish(TEST_NAMES[9]);
                tk.seriesFinish();
            }

            tk.startAfterPause();

            // test words
            for(String s: words){
                data4.put(s, s);
            }

            tk.finish(TEST_NAMES[10]);

            for(String s: words){
                data5.put(s, s);
            }

            tk.finish(TEST_NAMES[11]);

            for(String s: words){
                data4.remove(s);
            }

            tk.finish(TEST_NAMES[12]);

            for(String s: words){
                data5.remove(s);
            }

            tk.finish(TEST_NAMES[13]);
            tk.seriesFinish();

            StringBuilder sb = new StringBuilder();
            chainsSizes.stream().forEach(p -> sb.append(p).append(System.lineSeparator()));
            tk.logResult(sb.toString());
            tk.logResult(FINISH_COMMAND);
        } catch (MyException e) {
            tk.logResult(e.getMessage());
        }
    }

    public BlockingQueue<String> getResultsLogger() {
        return resultsLogger;
    }

    public Semaphore getSemaphore() {
        return semaphore;
    }
}
