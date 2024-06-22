import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

class Toy {
    private int id;
    private String name;
    private int weight;

    public Toy(int id, String name, int weight) {
        this.id = id;
        this.name = name;
        this.weight = weight;
    }

    public int getId() {
        return id;
    }

    public int getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        return id + " " + name + " " + weight;
    }
}

class ToyLottery {
    private PriorityQueue<Toy> toyQueue;

    public ToyLottery() {
        toyQueue = new PriorityQueue<>((t1, t2) -> Integer.compare(t2.getWeight(), t1.getWeight()));
    }

    public void put(String toyInfo) {
        String[] parts = toyInfo.split(" ");
        int id = Integer.parseInt(parts[0]);
        int weight = Integer.parseInt(parts[2]);
        String name = parts[1];
        
        toyQueue.add(new Toy(id, name, weight));
    }

    public int getRandomToyId() {
        int totalWeight = toyQueue.stream().mapToInt(Toy::getWeight).sum();
        int randomIndex = new Random().nextInt(totalWeight);
        int currentWeight = 0;
        for (Toy toy : toyQueue) {
            currentWeight += toy.getWeight();
            if (randomIndex < currentWeight) {
                return toy.getId();
            }
        }
        return -1; 
    }

    public void performLottery(int times) {
        try {
            File file = new File("lottery_results.txt");
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter writer = new FileWriter(file, true); 
            for (int i = 0; i < times; i++) {
                int toyId = getRandomToyId();
                writer.write(toyId + "\n");
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }    
}

public class Main {
    public static void main(String[] args) {
        ToyLottery lottery = new ToyLottery();
        lottery.put("1 constructor 2");
        lottery.put("2 robot 2");
        lottery.put("3 doll 6");

        lottery.performLottery(10);
    }
}
